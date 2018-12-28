package com.gome.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.gome.dao.MoneyDao;
import com.gome.util.DBUtil;

/**
 * @author azq
 * @createtime 2018年12月25日
 */

public class CalcuMoneyService {

	private static final Logger log = Logger.getLogger(CalcuMoneyService.class);
	public final MoneyDao md = new MoneyDao();

	/**
	 * 校验号码是否是可以计算佣金的
	 */
	public void checkPhoneNumIsOk() {
	
		// 获取当日所有返档号码信息
		List<Object[]> list = md.queryAllDataByDay(DBUtil.getConnection());

		if (!list.isEmpty()) {
			log.info("today return sevice_num is " + list.size() + " 个");
			for (Object[] obj : list) {
				// flag 计算佣金标识 1 为已计算  2为未计算 
				int flag = 1;
				// 检测开机没
				if (Integer.parseInt( (String)obj[1] )!= 1) {
					obj[5] = obj[5] + ",处于停机状态";
					flag = 2;
				}
				
				// 校验政策是否失效
				if (!belongCalendar(ObjToDate( obj[2]), ObjToDate( obj[3]),  ObjToDate(obj[4]))) {

					obj[5] = obj[5] + ",政策失效";
					flag = 2;
				}
				int nums = md.queryCountByPhone((String) obj[0], DBUtil.getConnection());
				if (nums > 1) {
					obj[5] = obj[5] + ",已经计算佣金";
					flag = 2;
				}
				//更新备注信息
				md.updateDataByOne((String)obj[5], (String)obj[0], (String)obj[6],flag, DBUtil.getConnection());

			}
		}

	}
	
	public static void main(String[] args) {
		new CalcuMoneyService().checkPhoneNumIsOk();

	}
	/**
	 * 计算佣金
	 */
	public void CalcuMoney(){
		
	}
	
	
	private static Date ObjToDate(Object obj){
		
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date date= null;
        try {
        	 date = sdf.parse(((String)obj).substring(0,18));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return date;
	}
	

	/**
	 * 内部使用
	 * 检查政策是否过期
	 * 
	 * @param nowTime
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	private static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);
		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		if (date.after(begin) && date.before(end)) {
			return true;
		} else if (nowTime.compareTo(beginTime) == 0 || nowTime.compareTo(endTime) == 0) {
			return true;
		} else {
			return false;
		}
	}

	

}
