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
            <div class="sidebar-item ${requestScope.uri eq "/HappyHostel/GetHostelInforServlet" ? "active" : ""}">
                <i class="fa-solid fa-person-shelter"></i>
                <a href="HomeRenterPage">Thông tin phòng</a>
            </div>
            <div class="sidebar-item ${requestScope.uri eq "/HappyHostel/GetRoomateAccountServlet" ? "active": ""}">
                <i class="fa-solid fa-user-group"></i>
                <a href="RoommatePage">Xem thành viên</a>
            </div>
            <div class="sidebar-item ${requestScope.uri eq "/HappyHostel/GetContractServlet" ? "active": ""}">
                <i class="fa-solid fa-file-contract"></i>
                <a href="ContractPage">Hợp đồng</a>
            </div>
            <div class="sidebar-item ${requestScope.uri eq "/HappyHostel/GetRenterBillServlet" ? "active": ""}">
                <i class="fa-solid fa-file-invoice-dollar"></i>
                <a href="InvoicePage">Hóa đơn</a>
            </div>
            <div class="sidebar-item ${requestScope.uri eq "/HappyHostel/AddReportServlet" ? "active": ""}" >
                <i class="fa-solid fa-file-import"></i>
                <a href="ReportPage">Gửi báo cáo</a>
            </div>
            <div class="sidebar-item ${requestScope.uri eq "/HappyHostel/GetReportServlet" ? "active": ""}">
                <i class="fa-solid fa-flag"></i>
                <a href="ReportView">Xem báo cáo</a>
            </div>
            <div class="sidebar-item ${requestScope.uri eq "/HappyHostel/GetNotificationServlet" ? "active": ""}">
                <i class="fa-solid fa-envelope-open-text"></i>
                <a href="Notification">Thông báo</a>
            </div>
            <div class="sidebar-item ${requestScope.uri eq "/HappyHostel/GetRenterInforServlet" ? "active": ""}" id="sidebaritem">
                <i class="fa-solid fa-id-card"></i>
                <a href="ProfileRenter<%--?<%=account.getAccId()%>--%>">Thông tin cá nhân</a>
            </div>
            <div class="sidebar-item">
                <i class="fa-solid fa-right-from-bracket"></i>
                <a href="logout">Đăng xuất</a>
            </div>
        </div>
    </div>
</div>