<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.codebrew.roommart.dto.Account" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thanh toán</title>
    <link rel="icon" href="./assets/images/favicon/favicon.png" type="image/x-icon"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
          integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="./assets/css/core_style/core.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="./assets/css/renter_page/Renter-payment.css">

    <!-- CSS Push Notification -->
    <link rel="stylesheet" href="./assets/css/push_notification_style/style.css">

    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
    <%
    Account account = (Account) session.getAttribute("USER");
    %>
<div>
    <%@include file="component/navbar.jsp"%>
    <div class="row" style="padding: 0;margin: 0;">
        <%@include file="component/sidebar.jsp"%>
        <div class="content">
            <div class="col-10">
                <div class="content-inside row justify-content-center">
                    <h1 class="text-center">Hóa đơn</h1>
                    <div class="invoice-board">
                        <div class="invoice-board_Detail">
                            <div class="float-left">
                                <p class="mb-2">
                                    <span class="fw-medium">Hóa đơn số:
                                        <span class="fw-normal">
                                            #${BILL.billID}
                                        </span>
                                    </span>
                                </p>
                                <p class="mb-2">
                                    <span class="fw-medium">Phòng số:
                                        <span class="fw-normal">
                                            ${RoomInfor.roomNumber}
                                        </span>
                                    </span>
                                </p>
                                <p class="mb-2">
                                    <span class="fw-medium">Tình trạng:
                                        <c:if test="${BILL.status != 1}">
                                            <span style="color: red">Chưa thanh toán</span>
                                        </c:if>
                                        <c:if test="${BILL.status == 1}">
                                            <span style="color: green">Đã thanh toán</span>
                                        </c:if>
                                    </span>
                                </p>
                            </div>
                            <div class="float-right">
                                <p class="mb-2">
                                    <span class="fw-medium">
                                        Ngày tạo hóa đơn:
                                        <span class="fw-normal">
                                            <fmt:parseDate pattern="yyyy-MM-dd" value="${BILL.createdDate}" var="createdDate"/>
                                            <fmt:formatDate value="${createdDate}" type="Date" pattern="dd-MM-yyyy"/>
                                        </span>
                                    </span>
                                </p>
                                <p class="mb-2">
                                    <span class="fw-medium">Hạn thanh toán:
                                         <span class="fw-normal">
                                             <fmt:parseDate pattern="yyyy-MM-dd" value="${BILL.expiredPaymentDate}" var="expiredPaymentDate"/>
                                            <fmt:formatDate value="${expiredPaymentDate}" type="Date" pattern="dd-MM-yyyy"/>
                                        </span>
                                    </span>
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="col-11">
                        <table class="table table-centered">
                            <thead>
                            <tr class="table-success">
                                <th scope="col" style="width: 23%;">Mô tả</th>
                                <th scope="col" style="width: 15%;">Giá (vnd)</th>
                                <th scope="col" style="width: 15%;">Đơn vị</th>
                                <th scope="col" style="width: 12%;">Số cũ</th>
                                <th scope="col" style="width: 12%;">Số mới</th>
                                <th scope="col" style="width: 23%;">Thành tiền</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="service" items="${LIST_SERVICES}">
                                <tr>
                                    <td>${service.serviceName}</td>
                                    <td>
                                        <fmt:setLocale value="vi_VN"/>
                                        <fmt:formatNumber value="${service.servicePrice}"/>
                                    </td>
                                    <td>${service.unit}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${service.serviceName=='Điện'}">
                                                ${CONSUME_START.numberElectric}
                                            </c:when>
                                            <c:when test="${service.serviceName=='Nước'}">
                                                ${CONSUME_START.numberWater}
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${service.serviceName=='Điện'}">
                                                ${CONSUME_END.numberElectric}
                                            </c:when>
                                            <c:when test="${service.serviceName=='Nước'}">
                                                ${CONSUME_END.numberWater}
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${service.serviceName=='Điện'}">
                                                <fmt:setLocale value="vi_VN"/>
                                                <fmt:formatNumber
                                                        value="${(CONSUME_END.numberElectric - CONSUME_START.numberElectric)*service.servicePrice}"/>
                                            </c:when>
                                            <c:when test="${service.serviceName=='Nước'}">
                                                <fmt:setLocale value="vi_VN"/>
                                                <fmt:formatNumber
                                                        value="${(CONSUME_END.numberWater - CONSUME_START.numberWater)*service.servicePrice}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:setLocale value="vi_VN"/>
                                                <fmt:formatNumber
                                                        value="${service.servicePrice}"/>
                                            </c:otherwise>
                                        </c:choose>
                                   </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td>Tổng tiền</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td><fmt:formatNumber value="${BILL.totalMoney}"/></td>
                            </tr>
                            </tbody>
                        </table>
                        <a href="" class="btn btn-outline-success btn-lg float-left mt-4">
                            Xuất hóa đơn ra file Excel
                        </a>
                        <c:if test="${BILL.payment.paymentID == 0}">
                            <form>
                                <button type="submit" id="payment-button"
                                        class="btn btn-outline-danger btn-lg float-right mt-4">
                                    Thanh Toán
                                </button>
                            </form>
                        </c:if>
                        <div class="clearfix"></div>
                        <a href="RenterBill" class="btn btn-outline-dark btn-lg float-left mt-3 mb-5">
                            Quay lại
                        </a>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
    <%@include file="component/footer.jsp"%>
    <!-- Push notification element -->
    <div id="push-noti"></div>
    <!-- Push notification -->
    <script src="./assets/js/push-notification-alert.js"></script>

    <!-- Web socket -->
    <script src="./assets/js/receiveWebsocket.js"></script>

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