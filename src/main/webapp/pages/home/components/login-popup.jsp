<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="./assets/css/system_style/login_style/login.css">

<style>
    .popup {
        display: none;
        position: fixed;
        z-index: 99999;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgba(0,0,0,0.4);
    }
    .popup .container {
        background-color: transparent;
    }
</style>

<div class="popup" id="popup-login">
    <div class="row container">
        <div class="col-xs-11 col-sm-10 col-md-7 col-lg-6 col-xl-5 col-xxl-4 m-auto">
            <form action="login" method="POST" id="login-form" class="custom-form login-form">
                <svg id="svgClose" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#d0021b" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"></circle><line x1="15" y1="9" x2="9" y2="15"></line><line x1="9" y1="9" x2="15" y2="15"></line></svg>
                <div class="form-header">
                    <h3 class="form-title">Đăng nhập</h3>
                    <div class="form-subtitle">Đăng nhập và bắt đầu sử dụng ứng dụng</div>
                </div>
                <div class="spacer"></div>
                <div class="form-group">
                    <label for="username" class="form-label">Tài khoản <span>*</span></label>
                    <input id="username" name="txtemail" type="text" value="" placeholder="Nhập tài khoản"
                           class="form-control">
                    <span class="form-message"></span>
                </div>
                <div class="form-group">
                    <label for="password" class="form-label">Mật khẩu <span>*</span></label>
                    <input id="password" name="txtpassword" type="password" placeholder="Nhập mật khẩu"
                           class="form-control">
                    <span class="form-message"></span>
                </div>
                <div class="row more-action">
                    <div class="col-6">
                        <div class="form-group">
                            <input id="remember" name="savelogin" type="checkbox" value="true"
                                   class="more-action__checkbox">
                            <label for="remember" class="form-label more-action__checkbox-title">Ghi nhớ
                                đăng nhập</label>
                        </div>
                    </div>
                    <div class="col-6">
                        <a href="" class="more-action__forgot-link" data-bs-toggle="modal"
                           data-bs-target="#forgot-password-modal">Quên mật khẩu</a>
                    </div>
                </div>
                <button class="form-submit">Đăng nhập</button>
                <div class="spacer"></div>
                <div class="form-other-link">Chưa có tài khoản? <a href="register-page">Đăng ký ngay!</a>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="./assets/js/system/login-handle.js"></script>
<script src="./assets/js/valid-form.js"></script>
<script>
    document.getElementById("svgClose").addEventListener("click", function() {
        document.getElementById("popup-login").style.display = "none";
        document.body.style.overflow = "auto";
    });
</script>