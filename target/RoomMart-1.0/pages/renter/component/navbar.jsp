<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav>
<div class="navbar">
    <div class="navbar-logo">
        <img src="logo.png" alt="Logo">
    </div>
    <div class="navbar-center"></div>
    <div class="navbar-icons">
        <div class="notification-icon">
            <img src="notification.png" alt="Notification" class="icon">
            <div class="notification-popup" id="notificationPopup">
                <!-- Popup content for notifications -->
                <p>You have 3 new notifications</p>
            </div>
        </div>
        <div class="settings-icon">
            <img src="settings.png" alt="Settings" class="icon">
            <div class="settings-popup" id="settingsPopup">
                <!-- Popup content for settings -->
                <p>Here are your settings</p>
            </div>
        </div>
    </div>
</div>
<div class="dropdown">
    <button class="nut_dropdown"><i class="fa-solid fa-bars"></i></button>
    <div class="noidung_dropdown">
        <a href="HostelRenterPage">Thông tin phòng</a>
        <a href="get-roommate-infor">Xem thành viên</a>
        <a href="AddRenterRoommatePage">Thêm thành viên</a>
        <a href="ContractPage">Hợp đồng</a>
        <a href="renter-invoice">Hóa đơn</a>
        <a href="Renter-report">Gửi báo cáo</a>
        <a href="Get-report">Xem báo cáo</a>
        <a href="RenterNotificationPage">Thông báo</a>
        <a href="HostelRenterProfilePage?<%--<%=account.getAccId()%>--%>">
            Thông tin cá nhân
        </a>
        <a href="logout">Đăng xuất</a>
    </div>
</div>
</nav>