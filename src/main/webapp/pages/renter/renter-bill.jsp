<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xem hoá đơn</title>
    <link rel="icon" href="./assets/images/favicon/favicon.png" type="image/x-icon"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
          integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="./assets/css/core_style/core.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="./assets/css/renter_page/Renter-view-list-invoice.css">

    <!-- CSS Push Notification -->
    <link rel="stylesheet" href="./assets/css/push_notification_style/style.css">

    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>

<body>
<%--<%--%>
<%--  Account account = (Account) session.getAttribute("USER");--%>
<%--%>--%>
<div>
    <%@include file="component/navbar.jsp"%>

    <div class="row" style="padding: 0;margin: 0;">
        <%@include file="component/sidebar.jsp"%>

        <div class="content">
            <div class="table-content">
                <h2>Danh Sách Hoá Đơn</h2>
                <table class="table table-bordered">
                    <thead class="table-head">
                    <tr>
                        <th>Mã Hoá Đơn</th>
                        <th>Ngày Tạo Hoá Đơn</th>
                        <th>Tổng Tiền(VND)</th>
                        <th>Trạng Thái</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach var="iL" items="${INVOICE_LIST}">
                        <fmt:parseDate pattern="yyyy-MM-dd" value="${iL.createdDate}" var="createdDate"/>
                        <tr>
                            <td style="height: 22px;"><a href="InvoiceList?invoiceID=${iL.invoiceID}"> #${iL.invoiceID} </a></td>
                            <td style="height: 22px;"><fmt:formatDate value="${createdDate}" type="Date"
                                                                      pattern="dd-MM-yyyy"/></td>
                            <td style="height: 22px;"><fmt:setLocale value="vi_VN"/>
                                <fmt:formatNumber value="${iL.totalMoney}" type="currency" currencySymbol="VNĐ"/></td>
                            <td style="height: 22px;"><a>
                                <c:choose>
                                    <c:when test="${iL.status}">
                                        <p style="color: green">Đã thanh toán</p>
                                    </c:when>
                                    <c:otherwise>
                                        <p style="color: red">Chưa thanh toán</p>
                                    </c:otherwise>
                                </c:choose>
                            </a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%@include file="component/footer.jsp"%>

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
<script src="./assets/js/renter/Renter-view-list-invoice.js"></script>
<!-- Push notification -->
<script src="./assets/js/push-notification-alert.js"></script>
<!-- Web socket -->
<script src="./assets/js/receiveWebsocket.js"></script>


<script type="text/javascript">
    // Receive
    receiveWebsocket(alertPushNoti);
    // Close when leave
    window.onbeforeunload = function(){
        receiveWebsocket.disconnectWebSocket();
    };
</script>

</body>
</html>
