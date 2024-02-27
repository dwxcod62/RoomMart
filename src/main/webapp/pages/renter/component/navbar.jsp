<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

<nav>
    <div class="navbar">
        <div class="navbar-logo">
            <img src="./assets/images/logos/logo.png" alt="">
        </div>
        <div class="navbar-center"></div>
        <div class="navbar-icons">
            <div class="notification-icon">
                <i class="bx bx-bell icon"></i>
            </div>
            <div class="settings-icon">
                <img src="./assets/images/favicon/checked.jpg" alt="" class="icon" onclick="toggleUserMenu()">
            </div>
        </div>
    </div>

    <!-- User menu popup -->
    <div id="userMenu" class="user-menu">
        <div class="arrow-up"></div>
        <a href="#" class="close" onclick="hideUserMenu()"> <!-- Change href="#" to your desired close action -->
            <svg class="ri ri-close" role="img" width="17" height="17">
<%--                <use xlink:href="/content/map.svg#ri-close"></use>--%>
            </svg>
        </a>
        <div class="user-info d-flex align-items-center">
            <div class="avatar">
                <div class="alternate">
                </div>
                <img class="photo" src="https://via.placeholder.com/150" alt=""> <!-- Avatar photo -->
            </div>
            <div class="info">
                <h4 class="name heading m-b-0">Finn Finn</h4> <!-- User name -->
                <div class="email small">chiakhoa712@gmail.com</div> <!-- User email -->
            </div>
        </div>
        <div class="user-menu-items">
            <a href="#/2113847/account/profile" class="user-menu-item">Account Settings</a>
            <!-- Account settings link -->
            <a href="/account/logout" class="user-menu-item">Sign Out</a> <!-- Sign out link -->
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