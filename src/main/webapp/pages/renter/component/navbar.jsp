<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

<nav>
    <div class="navbar">
        <div class="navbar-logo" onclick="goToHomePage()">
            <img src="./assets/images/logos/logo.png" alt="">
        </div>
        <div class="navbar-center"></div>
        <div class="navbar-icons">
            <div class="notification-icon">
<%--                <div class="number">!</div>--%>
<%--                <i class='bx bx-message-rounded-dots icon'></i>--%>
<%--                <div class="box-chat">--%>
<%--                        <div class="box-chat_content">--%>
<%--                            <h3>Đoạn chat</h3>--%>
<%--                            <div class="chat-section">--%>
<%--                                <a href="https://codepen.io/Golez/">--%>
<%--                                    <div class="chat-section_img">--%>
<%--                                        <img src="https://c1.staticflickr.com/5/4007/4626436851_5629a97f30_b.jpg">--%>
<%--                                    </div>--%>
<%--                                    <div class="chat-section_txt">--%>
<%--                                        <h5>James liked your post: "In Winslow's classic 1920 definition"</h5>--%>
<%--                                        <h6>11/7 - 2:30 pm</h6>--%>
<%--                                    </div>--%>
<%--                                </a>--%>
<%--                            </div>--%>
<%--                            <div class="chat-section">--%>
<%--                                <a href="https://codepen.io/Golez/">--%>
<%--                                    <div class="chat-section_img">--%>
<%--                                        <img src="https://c1.staticflickr.com/5/4007/4626436851_5629a97f30_b.jpg">--%>
<%--                                    </div>--%>
<%--                                    <div class="chat-section_txt">--%>
<%--                                        <h5>James liked your post: "In Winslow's classic 1920 definition"</h5>--%>
<%--                                        <h6>11/7 - 2:30 pm</h6>--%>
<%--                                    </div>--%>
<%--                                </a>--%>
<%--                            </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
            <div class="user-icon">
                <i class="bx bx-user-circle icon"></i>
                <div class="box-setting">
                    <div class="user-info d-flex align-items-center">
                        <div class="avatar">
                            <div class="alternate">
                            </div>
                            <c:choose>
                                <c:when test="${sessionScope.USER.accountInfo.information.sex == 0}">
                                    <img class="photo" src="./assets/images/avatars/male.png" alt="">
                                </c:when>
                                <c:otherwise>
                                    <img class="photo" src="./assets/images/avatars/female.png" alt="">
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="info">
                            <h3 class="name heading m-b-0">
                                ${sessionScope.USER.accountInfo.information.fullname}
                            </h3> <!-- User name -->
                            <h4 class="email small">
                                ${sessionScope.USER.getAccountInfo().getInformation().getEmail()}
                            </h4>
                        </div>
                    </div>

                    <div class="user-menu-items">
                        <a href="RenterProfile" class="user-menu-item">
                            <i class="bx bx-id-card box-setting_icon"></i>
                            <span class="box-setting_link">Thông tin cá nhân</span>
                        </a>
                        <a href="renter-Change-Pass" class="user-menu-item">
                            <i class="bx bx-cog box-setting_icon"></i>
                            <span class="box-setting_link">Đổi mật khẩu</span>
                        </a>
                        <a href="logout" class="user-menu-item">
                            <i class="bx bx-exit box-setting_icon"></i>
                            <span class="box-setting_link">Đăng xuất</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%--<div class="dropdown">--%>
    <%--    <button class="nut_dropdown"><i class="fa-solid fa-bars"></i></button>--%>
    <%--    <div class="noidung_dropdown">--%>
    <%--        <a href="HostelRenterPage">Thông tin phòng</a>--%>
    <%--        <a href="get-roommate-infor">Xem thành viên</a>--%>
    <%--        <a href="AddRenterRoommatePage">Thêm thành viên</a>--%>
    <%--        <a href="ContractPage">Hợp đồng</a>--%>
    <%--        <a href="renter-invoice">Hóa đơn</a>--%>
    <%--        <a href="Renter-report">Gửi báo cáo</a>--%>
    <%--        <a href="Get-report">Xem báo cáo</a>--%>
    <%--        <a href="RenterNotificationPage">Thông báo</a>--%>
    <%--        <a href="HostelRenterProfilePage?&lt;%&ndash;<%=account.getAccId()%>&ndash;%&gt;">--%>
    <%--            Thông tin cá nhân--%>
    <%--        </a>--%>
    <%--        <a href="logout">Đăng xuất</a>--%>
    <%--    </div>--%>
    <%--</div>--%>
</nav>