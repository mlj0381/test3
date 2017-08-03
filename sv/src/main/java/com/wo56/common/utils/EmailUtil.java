package com.wo56.common.utils;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.statistic.vo.EMailAuthenticator;
import com.wo56.business.statistic.vo.EMailInfo;
import com.wo56.common.consts.LogBIConstant;

public class EmailUtil {
	
	public static boolean sendStatisticHtmlMail(String toAddress, String toName, String subject, String content) throws Exception {
		List<SysStaticData> list = SysStaticDataUtil.getSysStaticDataList(LogBIConstant.StatisticConstant.STATISTIC_EMAIL_SENDER);
		if (null == list || list.size() == 0)
			throw new Exception("未配置邮件发送者信息");
		SysStaticData emailStaticData = list.get(0);
		String smtp = emailStaticData.getCodeValue();
		String from = emailStaticData.getCodeName();
		String username = emailStaticData.getCodeName();
		String password = emailStaticData.getCodeTypeAlias();
		EMailInfo mailInfo = new EMailInfo();
		mailInfo.setMailServerPort(String.valueOf(emailStaticData.getCodeId()));
		mailInfo.setMailServerHost(smtp);
		mailInfo.setValidate(true);
		mailInfo.setUsername(username);
		mailInfo.setPassword(password);// 您的邮箱密码
		mailInfo.setFromAddress(from);
        mailInfo.setToAddress(toAddress);  
        mailInfo.setToName(toName);
		mailInfo.setSubject(subject);  
		mailInfo.setContent(content);  
		return sendHtmlMail(mailInfo);
	}
	
	// 以HTML格式发送邮件
	public static boolean sendHtmlMail(EMailInfo mailInfo) {
		// 判断是否需要身份认证
		EMailAuthenticator authenticator = null;
		Properties properties = mailInfo.getProperties();
		if (mailInfo.isValidate()) {// 如果需要身份认证，则创建一个密码验证器
			authenticator = new EMailAuthenticator(mailInfo.getUsername(), mailInfo.getPassword());
		}

		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(properties, authenticator);
		try {
			Message mailMessage = new MimeMessage(sendMailSession);// 根据session创建一个邮件消息
			Address from = new InternetAddress(mailInfo.getFromAddress());// 创建邮件发送者地址
			mailMessage.setFrom(from);// 设置邮件消息的发送者
			
			Address to = new InternetAddress(mailInfo.getToAddress(), mailInfo.getToName());// 创建邮件的接收者地址
			mailMessage.setRecipient(Message.RecipientType.TO, to);// 设置邮件消息的接收者
			mailMessage.setSubject(mailInfo.getSubject());// 设置邮件消息的主题
			mailMessage.setSentDate(CommonUtil.getCurrentDate());// 设置邮件消息发送的时间

			// MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			MimeBodyPart messageBodyPart = new MimeBodyPart();// 创建一个包含HTML内容的MimeBodyPart
			// 设置HTML内容
			messageBodyPart.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(messageBodyPart);

			// 存在附件
			String[] filePaths = mailInfo.getAttachFileNames();
			if (filePaths != null && filePaths.length > 0) {
				for (String filePath : filePaths) {
					messageBodyPart = new MimeBodyPart();
					File file = new File(filePath);
					if (file.exists()) {// 附件存在磁盘中
						FileDataSource fds = new FileDataSource(file);// 得到数据源
						messageBodyPart.setDataHandler(new DataHandler(fds));// 得到附件本身并至入BodyPart
						messageBodyPart.setFileName(file.getName());// 得到文件名同样至入BodyPart
						mainPart.addBodyPart(messageBodyPart);
					}
				}
			}

			// 将MimeMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);
			Transport.send(mailMessage);// 发送邮件
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
