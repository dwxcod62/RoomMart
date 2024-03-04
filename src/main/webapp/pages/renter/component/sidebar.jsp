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
                    <a href="RenterHome" class="nav-link">
                        <i class="bx bx-home-alt icon"></i>
                        <span class="link">Thông tin phòng</span>
                    </a>
                </li>
                <li class="list">
                    <a href="RenterRoommate" class="nav-link">
                        <i class="bx bx-group icon"></i>
                        <span class="link">Xem thành viên</span>
                    </a>
                </li>
                <li class="list">
                    <a href="RenterContract" class="nav-link" id="RenterContract">
                        <i class="bx bx-copy-alt icon"></i>
                        <span class="link">Hợp đồng</span>
                    </a>
                </li>
                <li class="list">
                    <a href="RenterBill" class="nav-link" id="RenterBill">
                        <i class="bx bx-wallet-alt icon"></i>
                        <span class="link">Hóa đơn</span>
                    </a>
                </li>
                <li class="list">
                    <a href="RenterReport" class="nav-link" id="RenterReport">
                        <i class="bx bx-comment-dots icon"></i>
                        <span class="link">Báo cáo</span>
                    </a>
                </li>
                <li class="list">
                    <a href="RenterNoti" class="nav-link" id="RenterNoti">
                        <i class="bx bx-bell icon"></i>
                        <span class="link">Thông báo</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>