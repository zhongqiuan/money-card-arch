package com.gome.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.gome.bean.DayCalcuMoney;
import com.gome.dao.MoneyDao;
import com.gome.util.ArithUtil;
import com.gome.util.DBUtil;
import com.gome.util.GetOrderId;

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
				obj[5]="";
				// flag 计算佣金标识 1 为已计算 2为未计算
				int flag = 1;
				// 检测开机没
				
				if (Integer.parseInt((String) obj[1]) != 1) {
					obj[5] = obj[5] + ",处于停机状态";
					flag = 2;
				}

				// 校验政策是否失效
				if (!belongCalendar(ObjToDate(obj[2]), ObjToDate(obj[3]), ObjToDate(obj[4]))) {

					obj[5] = obj[5] + ",政策失效";
					flag = 2;
				}
				int nums = md.queryCountByPhone((String) obj[0], DBUtil.getConnection());
				if (nums > 1) {
					obj[5] = obj[5] + ",已经计算佣金";
					flag = 2;
				}
				// 更新备注信息
				String beizhu = (String)obj[5];
				md.updateDataByOne(beizhu, (String) obj[0], (String) obj[6], flag, DBUtil.getConnection());

			}
		}

	}

	public static void main(String[] args) {
		new CalcuMoneyService().CalcuMoney();
	}

	/**
	 * 检查日返档照片中有无渠道没有代理商
	 * 
	 * @param params
	 * @return
	 */
	public List<Object[]> checkChnl(String params) {
		String sql = "select distinct chnl_id from arch_detail_comm where chnl_id not in (select chnl_id from agent_chnl_mapper) and state =1 ";
		return md.queryModelByOne(params, sql, DBUtil.getConnection());

	}

	/**
	 * 查询当日返档佣金计算
	 * 
	 * @return
	 */
	private List<DayCalcuMoney> queryMoneySerioysByDay() {
		List<DayCalcuMoney> list =
		md.queryMoneySerioysByDayDao();
		return list;
	}

	/**
	 * 计算佣金
	 */
	public void CalcuMoney() {
		//得到每日佣金的信息
		 List<DayCalcuMoney> list = queryMoneySerioysByDay();
		 for (DayCalcuMoney day : list) {
			 //循环每条信息，计算
			 double agentFee=  day.getAgentFee();
			 int ruleFee= day.getRuleFee();
			 int archNumber= day.getArchNumber();
			 double totalFee=ArithUtil.mul(ruleFee, archNumber);
			 double paidFee= ArithUtil.mul(totalFee, agentFee/100);
			 double unpaidFee= ArithUtil.sub(totalFee, paidFee);
			 day.setDaySreialNmber(GetOrderId.getOrderId());
			 day.setTotalFee(totalFee/1000);
			 day.setPaidFee(paidFee/1000);
			 day.setUnPaidFee(unpaidFee/1000);
			 day.setCheckState("0");
			 //将记录保存到表里
			 log.info("保存记录---"+day.toString());
			 md.updateDayDetail(day);
		}
	}

	private static Date ObjToDate(Object obj) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(((String) obj).substring(0, 18));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 内部使用 检查政策是否过期
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
