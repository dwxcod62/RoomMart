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
        String lik = "http://" + host +":8080/RoomMart/check-otp?data=" + data;
        String content = "<div style=\"font-family: Helvetica, Arial, sans-serif; min-width: 1000px; overflow: auto; line-height: 2\">"
                + "<div style=\"margin: 50px auto; width: 70%; padding: 20px 0\">"
                + "<div style=\"border-bottom: 1px solid #eee\">"
                + "<a href=\"\" style=\"font-size: 1.4em; color: #00466a; text-decoration: none; font-weight: 600\">Room Mart+</a>"
                + "</div>"
                + "<p style=\"font-size: 1.1em\">Hi,</p>"
                + "<p>Thank you for choosing Room Mart. Use the following OTP to complete your Sign Up procedures. OTP is valid for 5 minutes</p>"
                + "<h2 style=\"background: #00466a; margin: 0 auto; width: max-content; padding: 0 10px; color: #fff; border-radius: 4px\">" + otp + "</h2>"
//                + "<p>Or click here -><a href=\"" + lik + "\"> here </a></p>"
                + "<p style=\"font-size: 0.9em\">Regards,<br />RoomMart+</p>"
                + "<hr style=\"border: none; border-top: 1px solid #eee\" />"
                + "</div>"
                + "</div>";

        return send(receiverEmail, subject, content);
    }

    public boolean sendRecoverPass(String receiverEmail, String data) {
        String subject = "Recover Password!";
        String recoveryLink = "http://" + host +":8080/RoomMart/password-reset?data=" + data;
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

    public boolean sendContractConfirmationEmail(String receiverEmail, String data) {
        String subject = "Xác nhận Hợp đồng Thuê";
        String contractLink = "http://" + host + ":8080/RoomMart/confirm-contract?data=" + data;
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

    public boolean sendMailNewBill(String receiveMail, String billTitle){
        String domain = "http://" + host + ":8080/RoomMart/renter-invoice";
        String mailObject = "Bạn vừa nhận được 1 hóa đơn mới";
        String mailBody = "<!doctype html>\n" +
                "<html lang=\"vi\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\" />\n" +
                "    <title>Reset Password</title>\n" +
                "    <meta name=\"description\" content=\"Reset Password Email Template.\">\n" +
                "    <style type=\"text/css\">\n" +
                "        a:hover {\n" +
                "            text-decoration: underline !important;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body marginheight=\"0\" topmargin=\"0\" marginwidth=\"0\" style=\"margin: 0px; background-color: #f2f3f8;\" leftmargin=\"0\">\n" +
                "    <!--100% body table-->\n" +
                "    <table cellspacing=\"0\" border=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#f2f3f8\"\n" +
                "        style=\"@import url(https://fonts.googleapis.com/css?family=Rubik:300,400,500,700|Open+Sans:300,400,600,700); font-family: 'Open Sans', sans-serif;\">\n" +
                "        <tr>\n" +
                "            <td>\n" +
                "                <table style=\"background-color: #f2f3f8; \n" +
                "                max-width: 670px;  \n" +
                "                margin: 0 auto;\" width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                    <tr>\n" +
                "                        <td style=\"height:80px;\">&nbsp;</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"text-align: center;\">\n" +
                "                            <a href=\"\" title=\"logo\" target=\"_blank\">\n" +
                "                                <img width=\"150\" src=\"https://camo.githubusercontent.com/eda1d1f5b51e3eed064cedd85298c95e93aa6a34200b5c932e936b01bfab2c48/68747470733a2f2f73636f6e74656e742e66646164312d322e666e612e666263646e2e6e65742f762f74312e31353735322d392f3432383231353034345f3934333530343234363639363833355f333336323038363139323731303432313033315f6e2e706e673f5f6e635f6361743d313032266363623d312d37265f6e635f7369643d386364306132265f6e635f657569323d41654630754566763643737a704f6a3362676c526e794e444a505175713041612d62776b3943367251427235764643423034754c78716146724436617861707136775f35454e4a356a4d38504d625838476c7a4373322d34265f6e635f6f68633d496853786773364232434d41585f684473596f265f6e635f68743d73636f6e74656e742e66646164312d322e666e61266f683d30335f41645370454d3851306a4b4543565a437a345867316b5a69744e724964583251415069625376626e383477557641266f653d3635463938443831\" title=\"logo\" alt=\"logo\">\n" +
                "                            </a>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"height: 20px;\">&nbsp;</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td>\n" +
                "                            <table width=\"95%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width: 670px;\n" +
                "                                background:#fff; \n" +
                "                                border-radius: 3px;\n" +
                "                                text-align: center;\n" +
                "                                -webkit-box-shadow: 0 6px 18px 0 rgba(0, 0, 0, .06);\n" +
                "                                -moz-box-shadow: 0 6px 18px 0 rgba(0, 0, 0, .06);\n" +
                "                                box-shadow: 0 6px 18px 0 rgba(0, 0, 0, .06);\">\n" +
                "                                <tr>\n" +
                "                                    <td style=\"height: 40px;\">&nbsp;</td>\n" +
                "                                </tr>\n" +
                "                                <tr>\n" +
                "                                    <td style=\"padding:0 35px;\">\n" +
                "                                        <h1 style=\"color: #1e1e2d; \n" +
                "                                            font-weight: 500; margin: 0;\n" +
                "                                            font-size: 32px;\n" +
                "                                            font-family:'Rubik', sans-serif;\">\n" +
                "                                            Bạn đã nhận được hóa đơn phòng "+billTitle+" \n" +
                "                                        </h1>\n" +
                "                                        <span style=\"display: inline-block;\n" +
                "                                            vertical-align:middle;\n" +
                "                                            margin: 29px 0 26px; \n" +
                "                                            border-bottom: 1px solid #cecece;\n" +
                "                                            width: 100px;\">\n" +
                "                                        </span>\n" +
                "                                        <p style=\"color:#455056;\n" +
                "                                        font-size: 15px;\n" +
                "                                        line-height: 24px; \n" +
                "                                        margin: 0;\">\n" +
                "                                            Chủ trọ vừa gửi cho bạn 1 hóa đơn tháng này. Vui lòng thanh toán đúng hạn.</br>  Xin cảm ơn\n" +
                "                                        </p>\n" +
                "                                        <a href=\""+domain+"\" style=\"background: #20e277;\n" +
                "                                            text-decoration: none !important; \n" +
                "                                            font-weight: 600; \n" +
                "                                            margin-top: 35px; \n" +
                "                                            color: #fff;\n" +
                "                                            text-transform: uppercase;\n" +
                "                                            font-size: 14px;\n" +
                "                                            padding: 14px 24px;\n" +
                "                                            width: 200px;\n" +
                "                                            display: inline-block;\n" +
                "                                            border-radius:50px;\" onmouseout=\"this.style.background='#20e277'\"\n" +
                "                                            onmouseover=\"this.style.background='#0bb658'\">\n" +
                "                                            Xem hóa đơn\n" +
                "                                        </a>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                                <tr>\n" +
                "                                    <td style=\"height:40px;\">&nbsp;</td>\n" +
                "                                </tr>\n" +
                "                            </table>\n" +
                "                        </td>\n" +
                "                    <tr>\n" +
                "                        <td style=\"height:20px;\">&nbsp;</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"text-align:center;\">\n" +
                "                            <p\n" +
                "                                style=\"font-size:14px; color:rgba(69, 80, 86, 0.7411764705882353); line-height:18px; margin:0 0 0;\">\n" +
                "                                &copy; <strong>www.codebrew.com</strong></p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"height:80px;\">&nbsp;</td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "    <!--/100% body table-->\n" +
                "</body>\n" +
                "\n" +
                "</html>";
        return send(receiveMail, mailObject, mailBody);
    }

}
