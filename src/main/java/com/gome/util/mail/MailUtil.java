package com.gome.util.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.gome.bean.MailBean;

/**
 * @author azq
 * @createtime 2019年1月3日
 */
public class MailUtil {

	// 发件人地址
	public static String senderAddress = "azq930@126.com";
	// 发件人账户名
	public static String senderAccount = "azq930";
	// 发件人账户密码
	public static String senderPassword = "azq0930.com";

	public static String sendMail(MailBean mailBean) throws Exception {
		// 1、连接邮件服务器的参数配置
		Properties props = new Properties();
		// 设置用户的认证方式
		props.setProperty("mail.smtp.auth", "true");
		// 设置传输协议
		props.setProperty("mail.transport.protocol", "smtp");
		// 设置发件人的SMTP服务器地址 IDC1CASArray01S.gome.inc
		props.setProperty("mail.smtp.host", "smtp.126.com");
		// 2、创建定义整个应用程序所需的环境信息的 Session 对象
		Session session = Session.getInstance(props);
		// 设置调试信息在控制台打印出来
		session.setDebug(true);
		// 3、创建邮件的实例对象
		Message msg = getMimeMessage(session, mailBean);
		// 4、根据session对象获取邮件传输对象Transport
		Transport transport = session.getTransport();
		// 设置发件人的账户名和密码
		transport.connect("smtp.126.com",senderAccount,"azq123");
		
		// 发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人,
		// 抄送人, 密送人
		transport.sendMessage(msg, msg.getAllRecipients());

		// 如果只想发送给指定的人，可以如下写法
		// transport.sendMessage(msg, new Address[]{new
		// InternetAddress("xxx@qq.com")});

		// 5、关闭邮件连接
		transport.close();

		return "";
	}

	/**
	 * 获得创建一封邮件的实例对象
	 * 
	 * @param session
	 * @return
	 * @throws MessagingException
	 * @throws AddressException
	 */
	public static MimeMessage getMimeMessage(Session session, MailBean mailBean) throws Exception {
		// 创建一封邮件的实例对象
		MimeMessage msg = new MimeMessage(session);
		// 设置发件人地址
		msg.setFrom(new InternetAddress(senderAddress));
		/**
		 * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行 MimeMessage.RecipientType.TO:发送
		 * MimeMessage.RecipientType.CC：抄送 MimeMessage.RecipientType.BCC：密送
		 */
		msg.setRecipients(MimeMessage.RecipientType.TO, swithArray(mailBean.getToPeople()));
		// 设置邮件主题
		msg.setSubject(mailBean.getSubJect(), "UTF-8");
		// 设置邮件正文
		msg.setContent(mailBean.getContentText(), "text/html;charset=UTF-8");
		// 设置邮件的发送时间,默认立即发送
		msg.setSentDate(new Date());

		return msg;
	}

	private static InternetAddress[] swithArray(String[] str) {
		if (str.length != 0) {
			int lenth =str.length;
			InternetAddress[] add = new InternetAddress[lenth];
			
			for (int i = 0; i <lenth; i++) {
				InternetAddress ia = new InternetAddress();
				ia.setAddress(str[i]);
				add[i]=ia;
				
			}
			return add;
		}

		return null;
	}
	
	public static void main(String[] args) {
		MailBean mb = new MailBean();
		mb.setSubJect("安肖会 女士 中奖信息");
		mb.setContentText("安肖会 女士，恭喜你中了新年大奖");
		mb.setToPeople(new String[]{"944317944@qq.com"});
		try {
			for (int i = 0; i < 10; i++) {
				
				sendMail(mb );
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}