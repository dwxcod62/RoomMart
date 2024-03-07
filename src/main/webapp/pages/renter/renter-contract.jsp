<%@ page import="com.codebrew.roommart.dto.Account" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hợp đồng</title>
    <link rel="icon" href="./assets/images/favicon/favicon.png" type="image/x-icon"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
          integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="./assets/css/core_style/core.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="./assets/css/renter_page/Renter-contract.css">

    <!-- CSS Push Notification -->
    <link rel="stylesheet" href="./assets/css/push_notification_style/style.css">

    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>

<body>
<%
    Account account = (Account) session.getAttribute("USER");
%>
<div>
    <!-- navbar -->
    <%@include file="component/navbar.jsp" %>
    <!-- content -->
    <div class="main-body row" style="padding: 0;margin: 0;">
        <%@include file="component/sidebar.jsp" %>

        <div class="content row">
            <div class="col-10">
                <div class="contract-content">
                    <div class="content__infor">
                        <h2 class="content__infor-title">Thông tin khu trọ</h2>
                        <div class="row">
                            <div class="col-12 col-md-6">
                                <p class="content__infor-item">Khu trọ: <span>${HOSTEL.hostelName}</span></p>
                                <p class="content__infor-item">Địa chỉ:
                                    <span>
                                ${HOSTEL.address},
                                ${HOSTEL.ward},
                                ${HOSTEL.district},
                                ${HOSTEL.city}
                            </span></p>
                                <p class="content__infor-item">Chủ trọ: <span>${OWNER_INFO.fullname}</span></p>
                                <p class="content__infor-item">Số Điện Thoại: <span>${OWNER_INFO.phone}</span></p>
                            </div>
                            <div class="col-12 col-md-6">
                                <p class="content__infor-item">Người thuê: <span>${RENTER_INFO.fullname}</span></p>
                                <p class="content__infor-item">Năm sinh:
                                    <span>
                                        <fmt:parseDate pattern="yyyy-MM-dd" value="${RENTER_INFO.birthday}" var="birthday"/>
                                        <fmt:formatDate value="${birthday}" type="Date" pattern="dd-MM-yyyy"/>
                                    </span>
                                </p>
                                <p class="content__infor-item">Số Điện Thoại: <span>${RENTER_INFO.phone}</span></p>
                                <p class="content__infor-item">CCCD: <span>${RENTER_INFO.cccd}</span></p>
                            </div>
                        </div>
                    </div>

                    <div class="content__spacer"></div>
                    <div class="content__infor">
                        <h2 class="content__infor-title">Thông tin phòng trọ</h2>
                        <div class="row">
                            <div class="col-12 col-sm-6">
                                <p class="content__infor-item">Phòng số: <span>${ROOM.roomNumber}</span></p>
                                <p class="content__infor-item">Diện tích: <span>${ROOM.roomArea} m2</span></p>
                                <p class="content__infor-item">Gác: <span>${ROOM.hasAttic eq 0 ? "Không" : "Có"}</span></p>
                                <p class="content__infor-item">Số lượng thành viên tối đa: <span>${ROOM.capacity}</span></p>
                            </div>
                            <div class="col-12 col-sm-6">
                                <fmt:parseDate pattern="yyyy-MM-dd" value="${CONTRACT.startDate}" var="startDate"/>
                                <p class="content__infor-item">Ngày bắt đầu thuê: <span><fmt:formatDate pattern="dd/MM/yyyy" value="${startDate}"/></span></p>
                                <fmt:parseDate pattern="yyyy-MM-dd" value="${CONTRACT.expiration}" var="endDate"/>
                                <p class="content__infor-item">Ngày kết thúc thuê: <span><fmt:formatDate pattern="dd/MM/yyyy" value="${endDate}"/></span></p>
                                <p class="content__infor-item">Tiền cọc:
                                    <span>
                                        <fmt:formatNumber value="${CONTRACT.deposit}" type="currency" currencySymbol="VNĐ"/>
                                    </span></p>
                                <p class="content__infor-item">Tiền phòng:
                                    <span>
                                        <fmt:formatNumber value="${CONTRACT.price}" type="currency" currencySymbol="VNĐ"/>
                                    </span>
                                </p>
                            </div>
                        </div>
                    </div>

                    <div class="content__spacer"></div>

                    <div class="row">
                        <div class="col-12 col-md-6 content__table">
                            <h2 class="content__infor-title">Phí dịch vụ hàng tháng</h2>
                            <table class="table table-bordered content__infor-table">
                                <thead>
                                <tr class="text-center">
                                    <th>Tên</th>
                                    <th>Giá</th>
                                    <th>Đơn vị tính</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="listServices" items="${SERVICES}">
                                    <tr>
                                        <td>${listServices.serviceName}</td>
                                        <td>
                                            <fmt:formatNumber value="${listServices.servicePrice}" type="currency" currencySymbol="VNĐ"/>
                                        </td>
                                        <td>1 ${listServices.unit}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="col-12 col-md-6 content__table">
                            <h2 class="content__infor-title">Cơ sở vật chất</h2>
                            <table class="table table-bordered content__infor-table">
                                <thead>
                                <tr class="text-center">
                                    <th>Tên</th>
                                    <th>Trạng thái</th>
                                </tr>
                                </thead>
                                <c:forEach var="infrastructure" items="${INFRASTRUCTURES}">
                                    <tbody>
                                    <tr>
                                        <td>${infrastructure.name}</td>
                                        <c:choose>
                                            <c:when test="${infrastructure.status == 1}">
                                                <td class="good">Sử dụng tốt</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td class="break">Hư hỏng</td>
                                            </c:otherwise>
                                        </c:choose>
                                    </tr>
                                    </tbody>
                                </c:forEach>
                            </table>
                        </div>
                    </div>

                    <div class="content__spacer"></div>

                    <div class="content__infor">
                        <div class="row">
                            <!-- Người cho thuê -->
                            <div class="col-12 col-sm-6">
                                <h2 class="content__infor-title">Người cho thuê</h2>
                                <div class="user-sign">
                                    <img class="image-sign" src="${CONTRACT.owner_sign}"/>
                                    <p>${OWNER_INFO.fullname}</p>
                                </div>
                            </div>
                            <!-- Người thuê -->
                            <div class="col-12 col-sm-6">
                                <h2 class="content__infor-title">Người thuê</h2>
                                    <div class="user-sign">
                                        <img class="image-sign" src="${CONTRACT.renter_sign}"/>
                                        <p>${RENTER_INFO.fullname}</p>
                                    </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- footer -->

<%@include file="component/footer.jsp" %>

<!-- Push notification element -->
<div id="push-noti"></div>

<script src="./assets/js/renter/Renter-navbar.js"></script>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
        integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
        crossorigin="anonymous"></script>
<!-- Push notification -->
<script src="./assets/js/push-notification-alert.js"></script>
<!-- Web socket -->
<script src="./assets/js/receiveWebsocket.js"></script>

<script>
    var currentPage = window.location.pathname.split("/").pop().split(".")[0];
    document.getElementById(currentPage).classList.add("active");
</script>

<script type="text/javascript">
    // Receive
    receiveWebsocket(alertPushNoti);
    // Close when leave
    window.onbeforeunload = function () {
        receiveWebsocket.disconnectWebSocket();
    };
</script>
</body>
</html>
