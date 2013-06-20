package com.tra;

import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailUtil {
	public static void sendEmail(Locale locale) throws MessagingException{
		ResourceBundle rb = ResourceBundle.getBundle("com.resource.MessageResources", locale);
		String host = "mail.transerainc.com";
		String from = "vinaya.nayak@transerainc.com";
		String to = "vinaya.nayak@transerainc.com";
	// Set properties
	// Get system properties
    Properties properties = System.getProperties();

    // Setup mail server
    properties.setProperty("mail.smtp.host", host);
	// Get session
	Session session = Session.getInstance(properties);
	 
	try {
	    // Instantiate a message
	    MimeMessage msg = new MimeMessage(session);
	 
	    // Set the FROM message
	    msg.setFrom(new InternetAddress(from));
	 
	    // The recipients can be more than one so we use an array but you can
	    // use 'new InternetAddress(to)' for only one address.
	    InternetAddress[] address = {new InternetAddress(to)};
	    msg.setRecipients(Message.RecipientType.TO, address);
	 
	    // Set the message subject and date we sent it.
	    msg.setSubject(rb.getString("locale.emailsubject"),"UTF-8");
	    msg.setSentDate(new Date());
	 
	    // Set message content
	    msg.setText(rb.getString("locale.emailcontent"),"UTF-8");
	 
	    // Send the message
	    Transport.send(msg);
	}
	catch (MessagingException mex) {
	    mex.printStackTrace();
	}
	}
}

 

