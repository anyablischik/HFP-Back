package com.craut.project.craut.service;

import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Verification
{
     public static void Verification( String email, String token){
        String SMTP_AUTH_USER = "authcrautproject";
        String SMTP_AUTH_PWD = "11223344a";

        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtp.sendpartial", "true");

        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);
         Transport transport = null;
         try {
                transport = session.getTransport();
                transport.connect("smtp.gmail.com", 465, SMTP_AUTH_USER, SMTP_AUTH_PWD);
                MimeMessage message = new MimeMessage(session);
                MimeMessageHelper helper = new MimeMessageHelper(message);
                message.setSubject("Auth");
                message.setContent("<a href=\"http://localhost:8080/verification?token="+token+
                        "\">clikc to activate profile</a>", "text/html");
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                message.setSentDate(new Date());
                helper.setFrom("authcrautproject@gmail.com");
                transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
         }
         catch (MessagingException e) {
             e.printStackTrace();
         }
    }
}
