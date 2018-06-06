package vvitsp.model.util;

import java.io.IOException;

import java.net.InetAddress;

import java.net.UnknownHostException;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailSender {

    private String userEmail;       // Don't include @gmail.com 
    private String userPassword;

    public EmailSender() {
    }

    public void sendEmail(String emailFromAddress, String emailToAddress,
                          String subject, String emailBody)
    throws MessagingException, UnknownHostException {
        MimeMessage message = this.createMessage();
        message.setFrom(new InternetAddress(emailFromAddress));
        Address[] toAddress = InternetAddress.parse(emailToAddress);
        message.addRecipients(Message.RecipientType.TO, toAddress);
        message.setSubject(subject);
        message.setContent(emailBody, "text/html");
        Transport.send(message);
    }

    private MimeMessage createMessage() throws UnknownHostException {
        // Get system properties
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

//        properties.put("mail.smtp.user", "...@gmail.com");
//        properties.put("mail.smtp.socketFactory.port", 465);
//        properties.put("mail.smtp.socketFactory.class",
//                       "javax.net.ssl.SSLSocketFactory");
//        properties.put("mail.smtp.socketFactory.fallback", "false");

        Session session =
            Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userEmail, userPassword);
                }
            });
        
        return new MimeMessage(session);
    }

    // In this main method you will get an idea to call this functionality
    public static void main(String[] args) {
        EmailSender sender = new EmailSender();
        try {
            sender.sendEmail("...@gmail.com", "...@gmail.com", 
                             "VVITSP Student Portal password reset",
                             "Your password to login has been reset. <br/>Thanks, <br/>VVITSP Admin.");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
