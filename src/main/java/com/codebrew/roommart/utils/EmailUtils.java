package com.codebrew.roommart.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtils {
    private static final String email = "ar.snowyy@gmail.com";
    private static final String pswd = "samx mhzb eaww ymgz";
    private static final String host = "localhost";

    private boolean send(String receiverEmail, String subject, String content) {
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

    public boolean sendToken(String receiverEmail, String otp, String data) {
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

    public boolean sendRecoverPass(String receiverEmail, String data) {
        String subject = "Recover Password!";
        String recoveryLink = "http://" + host +":8080/RoomMart_war_exploded/password-reset?data=" + data;
        String emailContent = "<div style=\"font-family: Helvetica, Arial, sans-serif; min-width: 1000px; overflow: auto; line-height: 2\">"
                + "<div style=\"margin: 50px auto; width: 70%; padding: 20px 0\">"
                + "<div style=\"border-bottom: 1px solid #eee\">"
                + "<a href=\"\" style=\"font-size: 1.4em; color: #00466a; text-decoration: none; font-weight: 600\">Room Mart+</a>"
                + "</div>"
                + "<p style=\"font-size: 1.1em\">Hi,</p>"
                + "<p>We received a request to recover your password. Please click the following link to reset your password. This link is valid for 5 minutes.</p>"
                + "<a href=\"" + recoveryLink + "\" style=\"background: #00466a; margin: 0 auto; width: max-content; padding: 10px 10px; color: #fff; border-radius: 4px\">Click here to reset your password</a>"
                + "<p>Or copy and paste the following URL in your browser:<br><a href=\"" + recoveryLink + "\">" + "👉 here 👈" + "</a></p>"
                + "<p style=\"font-size: 0.9em\">If you did not request this password reset or have any concerns, please ignore this email.</p>"
                + "<p style=\"font-size: 0.9em\">Regards,<br />RoomMart+</p>"
                + "<hr style=\"border: none; border-top: 1px solid #eee\" />"
                + "</div>"
                + "</div>";
        return send(receiverEmail, subject, emailContent);
    }

    public boolean sendPasswordChangeSuccessEmail(String receiverEmail) {
        String subject = "Password Change Successful";
        String emailContent = "<div style=\"font-family: Helvetica, Arial, sans-serif; min-width: 1000px; overflow: auto; line-height: 2\">"
                + "<div style=\"margin: 50px auto; width: 70%; padding: 20px 0\">"
                + "<div style=\"border-bottom: 1px solid #eee\">"
                + "<a href=\"\" style=\"font-size: 1.4em; color: #00466a; text-decoration: none; font-weight: 600\">Room Mart+</a>"
                + "</div>"
                + "<p style=\"font-size: 1.1em\">Hi,</p>"
                + "<p>Your password has been successfully changed.</p>"
                + "<p>If you did not make this change, please contact us immediately.</p>"
                + "<p style=\"font-size: 0.9em\">Regards,<br />RoomMart+</p>"
                + "<hr style=\"border: none; border-top: 1px solid #eee\" />"
                + "</div>"
                + "</div>";
        return send(receiverEmail, subject, emailContent);
    }

    public boolean sendContractConfirmationEmail(String receiverEmail, int contractID) {
        String subject = "Xác nhận Hợp đồng Thuê";
        String contractLink = "http://" + host + ":8080/RoomMart_war_exploded/createContract?id=" + contractID;
        String emailContent = "<div style=\"font-family: Helvetica, Arial, sans-serif; min-width: 1000px; overflow: auto; line-height: 2\">"
                + "<div style=\"margin: 50px auto; width: 70%; padding: 20px 0\">"
                + "<div style=\"border-bottom: 1px solid #eee\">"
                + "<a href=\"\" style=\"font-size: 1.4em; color: #00466a; text-decoration: none; font-weight: 600\">Room Mart+</a>"
                + "</div>"
                + "<p style=\"font-size: 1.1em\">Xin chào,</p>"
                + "<p>Bạn đã nhận được một hợp đồng thuê từ Room Mart+. Vui lòng xác nhận hợp đồng bằng cách nhấp vào liên kết dưới đây:</p>"
                + "<a href=\"" + contractLink + "\" style=\"background: #00466a; margin: 0 auto; width: max-content; padding: 10px 10px; color: #fff; border-radius: 4px\">Nhấp vào đây để xác nhận hợp đồng</a>"
                + "<p>Hoặc bạn có thể sao chép và dán liên kết sau vào trình duyệt của mình:<br><a href=\"" + contractLink + "\">" + contractLink + "</a></p>"
                + "<p style=\"font-size: 0.9em\">Nếu bạn không thực hiện yêu cầu này hoặc có bất kỳ thắc mắc nào, vui lòng bỏ qua email này.</p>"
                + "<p style=\"font-size: 0.9em\">Trân trọng,<br />RoomMart+</p>"
                + "<hr style=\"border: none; border-top: 1px solid #eee\" />"
                + "</div>"
                + "</div>";
        return send(receiverEmail, subject, emailContent);
    }
}
