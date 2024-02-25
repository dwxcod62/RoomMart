package com.codebrew.roommart.servlets.AccountServlet;

import com.codebrew.roommart.dao.SystemDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Status;
import com.codebrew.roommart.utils.Decorations;
import com.codebrew.roommart.utils.EncodeUtils;
import com.codebrew.roommart.utils.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            login(request, response);
            return null;
        }, "LoginServlet");
    }

    protected void login(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = EncodeUtils.hashSHA256(request.getParameter("pass"));
        String remember = request.getParameter("remember");
        String redirectUrl = "login"; //

        try {
            Account account = new SystemDao().getAccountByUsernameAndPassword(email, password);
            if (account != null) {
                if (account.getStatus() == 1) {
                    redirectUrl = "success";
                    HttpSession session = request.getSession(true);
                    session.setAttribute("USER", account);
                    if (remember != null) {
                        String token = RandomUtils.randomToken(30, email);
                        int twoDaysInSeconds = 60 * 60 * 24 * 2;
                        Cookie cookie = new Cookie("selector", token);
                        cookie.setMaxAge(twoDaysInSeconds);
                        response.addCookie(cookie);
                    }

                    System.out.println("User " + account.getEmail() + " login success!");

                    session.setAttribute("CURRENT_PAGES", "dashboard");
                } else if (account.getStatus() == -1) {
                    System.out.println("User " + account.getEmail() + " login fail!");

                    request.setAttribute("RESPONSE_MSG", Status.builder().status(false).content("Your account has been locked!").build());
                } else if (account.getStatus() == 0) {
                    System.out.println("User " + account.getEmail() + " login fail!");

                    request.setAttribute("RESPONSE_MSG", Status.builder().status(false).content("Please verify your account in the email!").build());
                }
            } else {
                System.out.println("User " + account.getEmail() + " login fail!");

                request.setAttribute("RESPONSE_MSG", Status.builder().status(false).content("Incorrect username or password!").build());
            }
        } catch (Exception e) {
            logger.error("Error at LoginServlet: {}", e.getMessage());
        } finally {
            try {
                response.sendRedirect(redirectUrl);
            } catch (IOException e) {
                logger.error("Error redirecting to {}: {}", redirectUrl, e.getMessage());
            }
        }
    }
}
