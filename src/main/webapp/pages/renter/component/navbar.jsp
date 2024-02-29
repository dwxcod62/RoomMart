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
                <i class="bx bx-bell icon"></i>
            </div>
            <div class="user-icon">
                <i class="bx bx-user-circle icon" onclick="toggleUserMenu()"></i>
            </div>
        </div>
    </div>

    <!-- User menu popup -->
    <div id="userMenu" class="user-menu">
        <div class="arrow-up"></div>
        <a href="" class="close" onclick="hideUserMenu(event)">
            <i class='bx bx-x bx-lg' style="color: rgba(0, 0, 0, 0.8);"></i>
        </a>
        <div class="user-info d-flex align-items-center">
            <div class="avatar">
                <div class="alternate">
                </div>
                <img class="photo" src="./assets/images/logos/logo.png" alt="">
            </div>
            <div class="info">
                <h4 class="name heading m-b-0"> Lê Bạch Ngọc Khôi Nguyên
<%--                    ${sessionScope.USER.getAccountInfo().getFullname()}--%>
                </h4> <!-- User name -->
<%--                <div class="email small">${sessionScope.USER.getEmail()}</div>--%>
                <div class="email small">nguyenle19032003@gmail.com</div>
            </div>
        </div>
        <div class="user-menu-items">
            <a href="ProfileRenter" class="user-menu-item">
                <i class="bx bx-id-card icon"></i>
                <span class="link">Thông tin cá nhân</span>
            </a>
            <a href="logout" class="user-menu-item">
                <i class="bx bx-exit icon"></i>
                <span class="link">Đăng xuất</span>
            </a>
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