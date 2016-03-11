package com.supinfo.suplink.util;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Mail {
	public void send(String from, String to, String subject, String txt){
		
		// identifier gmail account
		final String username = "urlshortenerjava@gmail.com	";
		final String password = "coucou123";
	
		// Configuration for the smtp server (gmail)
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
	
		// Create a session with an authentication  
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);}
			});
	
		try {
			// Configuration Message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(txt);
			// Send message
			Transport.send(message);
		}
		catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
		return;
	}
}
