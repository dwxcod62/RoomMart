package com.codebrew.roommart.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtils {
    private static String email = "ar.snowyy@gmail.com";
    private static String pswd = "samx mhzb eaww ymgz";
    private static String host = "localhost";

    public static boolean send(String receiverEmail, String subject, String content) {
        String senderEmail = email;
        String password = pswd;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=utf-8");

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean sendToken(String receiverEmail, String otp, String data) {
        String subject = "OTP from send-otp";
        String lik = "http://" + host +":8080/RoomMart_war_exploded/otp?data=" + data;
        String content = "<div style=\"font-family: Helvetica, Arial, sans-serif; min-width: 1000px; overflow: auto; line-height: 2\">"
                + "<div style=\"margin: 50px auto; width: 70%; padding: 20px 0\">"
                + "<div style=\"border-bottom: 1px solid #eee\">"
                + "<a href=\"\" style=\"font-size: 1.4em; color: #00466a; text-decoration: none; font-weight: 600\">Room Mart+</a>"
                + "</div>"
                + "<p style=\"font-size: 1.1em\">Hi,</p>"
                + "<p>Thank you for choosing Room Mart. Use the following OTP to complete your Sign Up procedures. OTP is valid for 5 minutes</p>"
                + "<h2 style=\"background: #00466a; margin: 0 auto; width: max-content; padding: 0 10px; color: #fff; border-radius: 4px\">" + otp + "</h2>"
                + "<p>Or click here -><a href=\"" + lik + "\"> here </a></p>"
                + "<p style=\"font-size: 0.9em\">Regards,<br />RoomMart+</p>"
                + "<hr style=\"border: none; border-top: 1px solid #eee\" />"
                + "</div>"
                + "</div>";

        return send(receiverEmail, subject, content);
    }

    public static boolean sendRecoverPass(String receiverEmail, String data) {
        String subject = "Recover Password!";
        String recoveryLink = "http://" + host +":8080/RoomMart_war_exploded/password-reset?data=" + data;
        String emailContent = "<div style=\"font-family: Helvetica, Arial, sans-serif; min-width: 1000px; overflow: auto; line-height: 2\">"
                + "<div style=\"margin: 50px auto; width: 70%; padding: 20px 0\">"
                + "<div style=\"border-bottom: 1px solid #eee\">"
                + "<a href=\"\" style=\"font-size: 1.4em; color: #00466a; text-decoration: none; font-weight: 600\">Room Mart+</a>"
                + "</div>"
                + "<p style=\"font-size: 1.1em\">Hi,</p>"
                + "<p>We received a request to recover your password. Please click the following link to reset your password. This link is valid for 5 minutes.</p>"
                + "<a href=\"" + recoveryLink + "\" style=\"background: #00466a; margin: 0 auto; width: max-content; padding: 0 10px; color: #fff; border-radius: 4px\">Click here to reset your password</a>"
                + "<p>Or copy and paste the following URL in your browser:<br><a href=\"" + recoveryLink + "\">" + "👉 here 👈" + "</a></p>"
                + "<p style=\"font-size: 0.9em\">If you did not request this password reset or have any concerns, please ignore this email.</p>"
                + "<p style=\"font-size: 0.9em\">Regards,<br />RoomMart+</p>"
                + "<hr style=\"border: none; border-top: 1px solid #eee\" />"
                + "</div>"
                + "</div>";
        return send(receiverEmail, subject, emailContent);
    }


    public static String shortEmail(String email) {
        int host_index = email.lastIndexOf("@");
        String host = email.substring(host_index + 1);
        return email.substring(0, 2) + "**" + host;
    }

    public static void main(String[] args) {
        boolean a = sendToken("hacker.zingme1@gmail.com", "123", "123");
        if (a){
            System.out.println("Send Success!");
        }
    }
}
