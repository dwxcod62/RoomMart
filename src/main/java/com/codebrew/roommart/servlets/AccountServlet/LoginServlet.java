package com.codebrew.roommart.servlets.AccountServlet;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.utils.Decorations;
import com.codebrew.roommart.utils.EncodeUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                login(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "LoginServlet.doPost");
    }

    protected void login(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String[] url_temp = req.getHeader("referer").split("/");
        String url = "login-page";
        AccountDao accountDAO = new AccountDao();
        String username = req.getParameter("txtemail");
        String password = new EncodeUtils().hashMd5(req.getParameter("txtpassword"));
        String save = req.getParameter("savelogin");
        Account account = null;
        try {
            account = accountDAO.getAccountByUsernameAndPassword(username, password);
            int checkRenterHasRoom = accountDAO.getRoomOfRenter(account.getAccountInfo().getInformation().getEmail());
            if (account != null && account.getStatus() == 1) {
                String temp = url_temp[url_temp.length-1];
                System.out.println(temp);
                if ( temp.contains("roomDetailH") && (checkRenterHasRoom < 1)) {
                    url = url_temp[url_temp.length-1];
                } else {
                    url = "success";
                }

                System.out.println(url);
                HttpSession session = req.getSession(true);
                if (session != null) {
                    session.setAttribute("USER", account);
                    if (save != null) {
                        System.out.println("save");
//                        String token = RandomStringGenerator.randomToken(25, username);
                        //DAO add cookie
//                        Cookie cookie = new Cookie("selector", token);
//                        cookie.setMaxAge(60 * 60 * 24 * 2);
//                        res.addCookie(cookie);
//                        accountDAO.updateTokenByUserName(token, username);
                    }
                }

                session.setAttribute("CURRENT_PAGE", "dashboard");
            }else if (account != null && account.getStatus() == -1){
                req.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(false)
                        .content("Tài khoản của bạn đã bị khóa! Vui lòng liên hệ quản trị viên để biết thêm chi tiết.").build());
            }else if (account != null && account.getStatus() == 0) {
                req.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(false)
                        .content("Tài khoản của bạn chưa được kích hoạt! Vui lòng liên hệ quản trị viên để biết thêm chi tiết.").build());
            } else {
                req.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(false)
                        .content("Sai tài khoản hoặc mật khẩu. Vui lòng kiểm tra lại!").build());
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (account != null && account.getStatus() == 1) {
                res.sendRedirect(url);
            } else {
                req.getRequestDispatcher(url).forward(req, res);
            }
        }
    }
}