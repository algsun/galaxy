package com.microwise.common.service.email;

import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.ConfigFactory.Configs;
import com.microwise.common.sys.Constants.Emails;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

/**
 * 发送邮件服务
 * 
 * @author zhangpeng
 * @date 2012-10-30
 * 
 * @check 2012-11-27 xubaoji svn:381
 */
public class EmailService {

	/**
	 * 邮件发送对象
	 */
	private static MailSender mailSender;

	/** 批量发送邮件对象 */
	private static MailSender batchMailSender;

	private static ConfigFactory configFactory;
	private static Configs config;

	public EmailService() {
		configFactory = ConfigFactory.getInstance();
		config = configFactory.getConfig(Emails.EMAIL_CONFIG_PATH);
	}

	/**
	 * 通过spring发送邮件
	 * 
	 * @param recipient
	 *            目标email地址
	 * @param title
	 *            邮件标题
	 * @param emailText
	 *            邮件正文
	 * 
	 * @author zhangpeng
	 * @date 2012-10-10
	 * 
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public static void sendEmail(String recipient, String title,
			String emailText) throws MessagingException,
			UnsupportedEncodingException {
		MimeMessage mimeMessage = ((JavaMailSender) mailSender).createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		String smtpFrom = config.get(Emails.SMTP_FROM);
		String smtpAuthor = config.get(Emails.SMTP_AUTHOR);
		helper.setFrom(smtpFrom, smtpAuthor);
		helper.setTo(recipient);
		helper.setSubject(title);
		helper.setText(emailText, true);
		((JavaMailSender) mailSender).send(mimeMessage);
	}

	/**
	 * 通过spring 批量发送邮件
	 * 
	 * @param recipient
	 *            目标email地址
	 * @param title
	 *            邮件标题
	 * @param emailText
	 *            邮件正文
	 * 
	 * @author zhangpeng
	 * @date 2012-10-10
	 * 
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public static void sendBatchEmail(String title, String emailText,
			String... recipient) throws MessagingException,
			UnsupportedEncodingException {
		MimeMessage mimeMessage = ((JavaMailSender) batchMailSender).createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		String smtpFrom = config.get(Emails.SMTP_FROM);
		String smtpAuthor = config.get(Emails.SMTP_AUTHOR);
		helper.setFrom(smtpFrom, smtpAuthor);
		helper.setTo(recipient);
		helper.setSubject(title);
		helper.setText(emailText, true);
		((JavaMailSender) batchMailSender).send(mimeMessage);
	}

	public static MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		EmailService.mailSender = mailSender;
	}

	public static MailSender getBatchMailSender() {
		return batchMailSender;
	}

	//TODO 这个set目测不需要静态的，结合spring里的配置看一下
    public static void setBatchMailSender(MailSender batchMailSender) {
		EmailService.batchMailSender = batchMailSender;
	}
	
	

}
