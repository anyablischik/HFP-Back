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
                message.setSubject("Verify your HFP account");
                message.setContent(
                        "<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"color: #333;background: #fff;padding: 0;margin: 0;width: 100%;font: 15px 'Helvetica Neue', Arial, Helvetica;\">" +
                        " <tbody><tr width=\"100%\"> <td valign=\"top\" align=\"left\" style=\"background: #f0f0f0;font: 15px 'Helvetica Neue', Arial, Helvetica;\"> " +
                        "<table style=\"border: none;padding: 0 18px;margin: 50px auto;width: 500px;\"> <tbody>\n" +
                        "\n" +
                        " <tr width=\"100%\" height=\"57\"> <td valign=\"top\" align=\"left\" style=\"border-top-left-radius: 4px;border-top-right-radius: 4px;background: #0079BF;padding: 12px 18px;text-align: center;\">\n" +
                        "\n" +
                        " <img height=\"37\" width=\"122\" title=\"HFP\" style=\"font-weight: bold;font-size: 18px;color: #fff;vertical-align: top;\"> </td> </tr>\n" +
                        "\n" +
                        " <tr width=\"100%\"> <td valign=\"top\" align=\"left\" style=\"border-bottom-left-radius: 4px;border-bottom-right-radius: 4px;background:#fff;padding: 18px;\"> " +
                                "<h1 style=\"font-size: 20px;margin: 0;color: #333;\"> Hey! </h1>\n" +
                        "\n" +
                        " <p style=\"font: 15px/1.25em 'Helvetica Neue', Arial, Helvetica;color: #333;\">We are ready to activate your account. </p>\n" +
                        "\n" +
                        " <p style=\"font: 15px/1.25em 'Helvetica Neue', Arial, Helvetica;color: #333;\">\n" +
                        "\n" +
                        " <a href=\"http://localhost:8080/verification?token=\"+token+\n" +
                        "\"\" style=\"border-radius: 3px;-moz-border-radius: 3px;-webkit-border-radius: 3px;background: #3AA54C;color: #fff;cursor: pointer;display: block;font-weight: 700;font-size: 16px;line-height: 1.25em;margin: 24px auto 24px;padding: 10px 18px;text-decoration: none;width: 180px;text-align: center;\" target=\"_blank\" rel=\" noopener noreferrer\">" +
                                " Verify HFP account </a>\n" + "\n" + " </p>\n" + "\n" +
                        " <p style=\"font: 15px/1.25em 'Helvetica Neue', Arial, Helvetica;color: #939393;margin-bottom: 0;\"> " +
                                "If you did not create a HFP account, just delete this email</p>\n" +
                        "\n" + " </td> </tr>\n" + "\n" +
                        " </tbody> </table> </td> </tr></tbody> </table>", "text/html");
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
