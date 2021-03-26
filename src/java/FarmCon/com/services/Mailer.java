/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FarmCon.com.services;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Sourav
 */
public class Mailer {
    synchronized public static boolean send(String to, String cc, String subject, int invID) {
        final String user = "farmconofficial@yahoo.com";
        final String pass = "mmbffbbznwbxyykx";
        
//        final String user = "preclient127@gmail.com";
//        final String pass = "9832068948";
        
        final String host = "smtp.mail.yahoo.com";
//        final String host = "smtp.gmail.com";
//1st step) Get the session object    
        Properties props = new Properties();
        props.put("mail.smtp.host", host);//change accordingly  
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator(){
             @Override
             protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });
//2nd step)compose message  
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setRecipient(Message.RecipientType.CC, new InternetAddress(cc));
            message.setSubject(subject);
            message.setText("Please find invoice no "+invID+" attached.");
            MimeBodyPart attachment=new MimeBodyPart();
            String attachedFile="C:\\Users\\Sourav\\Documents\\NetBeansProjects\\FARMCON2\\src\\java\\invoices\\Invoice_"+invID+".pdf";
            FileDataSource source=new FileDataSource(attachedFile);
            attachment.setDataHandler(new DataHandler(source));
            attachment.setFileName("Invoice_"+invID+".pdf");
            MimeMultipart multipart=new MimeMultipart();
            multipart.addBodyPart(attachment);
            message.setContent(multipart);
            //3rd step)send message  
            System.out.println("Sending in progress...");
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.out.println(e);
        }
        return true;
    }
}
    
