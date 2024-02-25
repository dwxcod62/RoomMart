<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="dashboard hidden" id="dashboard">
    <div class="infor-top">
<%--        <% String[] spilitName = account.getAccountInfo().getInformation().getFullname().split(" ");--%>
<%--            int size = spilitName.length;--%>
<%--        %>--%>
<%--        <img src="./assets/images/avatars/${sessionScope.USER.accountInfo.information.sex == 1 ? "male" : "female"}.jpg" alt="">--%>
<%--        <p>Người Thuê</p>--%>
<%--        <h3>Xin Chào, <%= spilitName[size-1] %></h3>--%>

    </div>
    <div class="card">
        <div class="card-body">
            <ul class="lists">
                <li class="list">
                    <a href="Renter-HomePage" class="nav-link">
                        <i class="bx bx-home-alt icon"></i>
                        <span class="link">Thông tin phòng</span>
                    </a>
                </li>
                <li class="list">
                    <a href="RoommatePage" class="nav-link">
                        <i class="bx bx-group icon"></i>
                        <span class="link">Xem thành viên</span>
                    </a>
                </li>
                <li class="list">
                    <a href="ContractPage" class="nav-link">
                        <i class="bx bx-copy-alt icon"></i>
                        <span class="link">Hợp đồng</span>
                    </a>
                </li>
                <li class="list">
                    <a href="InvoiceList" class="nav-link">
                        <i class="bx bx-wallet-alt icon"></i>
                        <span class="link">Hóa đơn</span>
                    </a>
                </li>
                <li class="list">
                    <a href="ReportView" class="nav-link">
                        <i class="bx bx-comment-dots icon"></i>
                        <span class="link">Báo cáo</span>
                    </a>
                </li>
                <li class="list">
                    <a href="Notification" class="nav-link">
                        <i class="bx bx-bell icon"></i>
                        <span class="link">Thông báo</span>
                    </a>
                </li>
                <li class="list">
                    <a href="ProfileRenter" class="nav-link">
                        <i class="bx bx-id-card icon"></i>
                        <span class="link">Thông tin cá nhân</span>
                    </a>
                </li>
                <li class="list">
                    <a href="logout" class="nav-link">
                        <i class="bx bx-id-card icon"></i>
                        <span class="link">Đăng xuất</span>
                    </a>
                </li>
            </ul>
<%--            <div class="sidebar-item ${requestScope.uri eq "/RoomMart/GetHostelInforServlet" ? "active" : ""}">--%>
<%--                <i class="fa-solid fa-person-shelter"></i>--%>
<%--                <a href="Renter-Home">Thông tin phòng</a>--%>
<%--            </div>--%>
<%--            <div class="sidebar-item ${requestScope.uri eq "/RoomMart/GetRoomateAccountServlet" ? "active": ""}">--%>
<%--                <i class="fa-solid fa-user-group"></i>--%>
<%--                <a href="RoommatePage">Xem thành viên</a>--%>
<%--            </div>--%>
<%--            <div class="sidebar-item ${requestScope.uri eq "/RoomMart/GetContractServlet" ? "active": ""}">--%>
<%--                <i class="fa-solid fa-file-contract"></i>--%>
<%--                <a href="ContractPage">Hợp đồng</a>--%>
<%--            </div>--%>
<%--            <div class="sidebar-item ${requestScope.uri eq "/RoomMart/GetRenterBillServlet" ? "active": ""}">--%>
<%--                <i class="fa-solid fa-file-invoice-dollar"></i>--%>
<%--                <a href="InvoiceList">Hóa đơn</a>--%>
<%--            </div>--%>
<%--            <div class="sidebar-item ${requestScope.uri eq "/RoomMart/AddReportServlet" ? "active": ""}" >--%>
<%--                <i class="fa-solid fa-file-import"></i>--%>
<%--                <a href="ReportPage">Gửi báo cáo</a>--%>
<%--            </div>--%>
<%--            <div class="sidebar-item ${requestScope.uri eq "/RoomMart/GetReportServlet" ? "active": ""}">--%>
<%--                <i class="fa-solid fa-flag"></i>--%>
<%--                <a href="ReportView">Xem báo cáo</a>--%>
<%--            </div>--%>
<%--            <div class="sidebar-item ${requestScope.uri eq "/RoomMart/GetNotificationServlet" ? "active": ""}">--%>
<%--                <i class="fa-solid fa-envelope-open-text"></i>--%>
<%--                <a href="Notification">Thông báo</a>--%>
<%--            </div>--%>
<%--            <div class="sidebar-item ${requestScope.uri eq "/RoomMart/GetProfileServlet" ? "active": ""}" id="sidebaritem">--%>
<%--                <i class="fa-solid fa-id-card"></i>--%>
<%--                <a href="ProfileRenter&lt;%&ndash;?<%=account.getAccId()%>&ndash;%&gt;">Thông tin cá nhân</a>--%>
<%--            </div>--%>
<%--            <div class="sidebar-item">--%>
<%--                <i class="fa-solid fa-right-from-bracket"></i>--%>
<%--                <a href="logout">Đăng xuất</a>--%>
<%--            </div>--%>
        </div>
    </div>
</div>