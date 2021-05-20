/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
//import java.util.Properties;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author turki
 */
public class mailingReclamation {
    public static void sendEmail(String toEmail, String subject, String body) {
        
        final String user="HYTACOCAMPII@gmail.com";
        final String password="hytaco123";


        String to="mohamedamine.tlili.1@esprit.tn";
        // session object
        Properties props = new Properties();
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user,password);
                    }
                });

        //My message :
        try {
            MimeMessage message = new MimeMessage(session);
            try {
                message.setFrom(new InternetAddress(user,"Campi"));
            } catch (UnsupportedEncodingException ex) {
             //   Logger.getLogger(mailing.class.getName()).log(Level.SEVERE, null, ex);
            }
             
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(subject);
            //Text in email :
            message.setText(body);
            //send the message
            Transport.send(message);
            System.out.println("message sent successfully via mail ... !!! ");
            
        } catch (MessagingException e) {e.printStackTrace();}

    }
}