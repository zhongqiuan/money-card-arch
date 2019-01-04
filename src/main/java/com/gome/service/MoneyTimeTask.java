package com.gome.service;

import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.gome.bean.MailBean;
import com.gome.util.mail.MailUtil;

/**
 *@author azq
 *@createtime 2019年1月2日
*/
public class MoneyTimeTask extends TimerTask {
	private static final Logger log = Logger.getLogger(MoneyTimeTask.class);
	private static final CalcuMoneyService cms  = new CalcuMoneyService();

	@Override
	public void run() {
		
		//检查渠道商是否有无代理商
		log.info("开始检查  返档号码是否都具备返档条件");
		cms.checkPhoneNumIsOk();
		log.info("开始检查  渠道商是否有代理商");
		List<Object[]> list= cms.checkChnl(null);
		if(list.isEmpty()){
			//检查返档明细表中是否符合计算佣金条件
			//计算佣金
			log.info("开始计算佣金");
			cms.CalcuMoney();
			log.info("**********佣金计算结束*********");
		}else{
			MailBean md = new MailBean();
			StringBuffer sb  = new  StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				sb.append((String)list.get(i)[0]);
			}
			md.setContentText(sb.toString());
			md.setSubJect("次日结渠道商缺少对应代理商");
			md.setToPeople(new String[]{"azq930@126.com"});
			try {
				MailUtil.sendMail(md);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
	}

}
