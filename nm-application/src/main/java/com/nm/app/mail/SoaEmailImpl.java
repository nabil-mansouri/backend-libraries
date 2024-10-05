package com.nm.app.mail;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nm.config.SoaAppConfig;
import com.nm.config.constants.AppConfigKeyDefault;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class SoaEmailImpl implements SoaEmail {

	private SoaAppConfig configService;
	private Boolean enable;

	public void setConfigService(SoaAppConfig configService) {
		this.configService = configService;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	protected Log log = LogFactory.getLog(getClass());

	// TODO history of emails
	public void send(EmailBuilderContract builder) throws EmailException {
		try {
			if (!enable) {
				log.warn("Email service is not enable!");
				return;
			}
			String smtp = configService.getText(AppConfigKeyDefault.AdminSmtp);
			//
			String froms = configService.getText(AppConfigKeyDefault.AdminEmailFrom);
			Properties properties = System.getProperties();
			properties.setProperty("mail.from", builder.from(froms));
			properties.setProperty("mail.smtp.host", smtp);

			try {
				log.warn(String.format("Using name service : %s", InetAddress.getLocalHost().getHostName()));
			} catch (Exception e) {
				log.error(String.format("Cannot use local name service : %s", e.getMessage()));
			}
			// Get the default Session object.
			Session session = Session.getDefaultInstance(properties);
			MimeMessage message = new MimeMessage(session);
			for (String to : builder.to()) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			}
			for (String to : builder.cc()) {
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(to));
			}
			for (String to : builder.cci()) {
				message.addRecipient(Message.RecipientType.BCC, new InternetAddress(to));
			}
			if (builder.hasSubject()) {
				message.setSubject(builder.subject());
			}
			Multipart multiPart = new MimeMultipart();
			{
				// TODO ATTACHING IMAGE to html : <img src='cid:some_image_id'>
				// MimeBodyPart imagePart = new MimeBodyPart();
				// FileDataSource fds = new
				// FileDataSource("THE_IMAGE_FILE_NAME");
				// imagePart.setDataHandler(new DataHandler(fds));
				// imagePart.setHeader("Content-ID", "some_image_id");
				if (builder.hasBody()) {
					MimeBodyPart part = new MimeBodyPart();
					part.setText(builder.body(), builder.charset(), builder.type());
					multiPart.addBodyPart(part);
				}
			}
			for (File b : builder.joined()) {
				MimeBodyPart part = new MimeBodyPart();
				part.setDataHandler(new DataHandler(new FileDataSource(b)));
				part.setFileName(b.getName());
				multiPart.addBodyPart(part);
			}
			message.setContent(multiPart);
			Transport.send(message);
		} catch (Exception e) {
			throw new EmailException(e);
		}
	}

	public static void main(String[] args) throws UnknownHostException {
		System.out.println(InetAddress.getLocalHost().getHostName());
	}
}
