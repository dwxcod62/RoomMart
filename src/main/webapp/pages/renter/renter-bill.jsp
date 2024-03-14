<%@ page import="com.codebrew.roommart.dto.Account" %>
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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <link rel="stylesheet" href="./assets/css/core_style/core.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="./assets/css/renter_page/Renter-bill.css">

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

    <div class="row main-body" style="padding: 0;margin: 0;">
        <%@include file="component/sidebar.jsp"%>

        <div class="content">
            <div class="col-10">
                <div id="list-notifications-container" class="content__body view_report">
                    <h1 class="text-center">Danh sách hóa đơn</h1>

                    <table id="notification-table" class="content__table table table-bordered dataTable no-footer"
                           aria-describedby="notification-table_info">
                        <thead class="content__thead">
                        <tr class="table-Light">
                            <th class="text-center sorting" tabindex="0" aria-controls="notification-table" rowspan="1"
                                colspan="1" aria-label="Mã hoá đơn: activate to sort column ascending"
                                style="width: 16%; border-bottom: 1px solid #333;">Mã hoá đơn
                            </th>
                            <th class="text-center sorting" tabindex="0" aria-controls="notification-table" rowspan="1"
                                colspan="1" aria-label="Ngày tạo hoá đơn: activate to sort column ascending"
                                style="width: 22%; border-bottom: 1px solid #333;"
                                data-orderable="false">Ngày tạo hoá đơn</th>
                            <th class="text-center sorting" aria-controls="notification-table" rowspan="1" colspan="1"
                                aria-label="Tổng tiền (VND): activate to sort column ascending"
                                style="width: 24%; border-bottom: 1px solid #333;"
                                data-orderable="false">Tổng tiền (VND)</th>
                            <th class="text-center sorting" aria-controls="notification-table" rowspan="1" colspan="1"
                                aria-label="Trạng Thái: activate to sort column ascending"
                                style="width: 24%; border-bottom: 1px solid #333;"
                                data-orderable="false">Trạng Thái</th>
                            <th style="width: 14%; border-bottom: 1px solid #333;"></th>
                        </tr>
                        </thead>
                        <tbody class="content__tbody">
                            <c:forEach var="bL" items="${BILL_LIST}">
                            <fmt:parseDate pattern="yyyy-MM-dd" value="${bL.createdDate}" var="createdDate"/>
                            <tr style="text-align: center">
                                <td style="height: 22px;">#${bL.billID}</td>
                                <td style="height: 22px;"><fmt:formatDate value="${createdDate}" type="Date"
                                                                          pattern="dd-MM-yyyy"/></td>
                                <td style="height: 22px;"><fmt:setLocale value="vi_VN"/>
                                    <fmt:formatNumber value="${bL.totalMoney}" type="currency" currencySymbol="VNĐ"/></td>
                                <td style="height: 22px;">
                                    <c:choose>
                                        <c:when test="${bL.status eq 1}">
                                            <p style="color: green">Đã thanh toán</p>
                                        </c:when>
                                        <c:otherwise>
                                            <p style="color: red">Chưa thanh toán</p>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td style="height: 22px;">
                                    <a href="RenterPayment?billID=${bL.billID}">Chi tiết</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="component/footer.jsp"%>

<!-- Push notification element -->
<div id="push-noti"></div>

<script src="./assets/js/renter/Renter-navbar.js"></script>
<script src="./assets/js/renter/Renter-view-list-invoice.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap5.min.js"></script>

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

<script>
    $(document).ready(function () {
        $('#notification-table').DataTable({
            "language": {
                "search": "Tìm kiếm:",
                "lengthMenu": "Hiển thị _MENU_ mục",
                "info": "Hiển thị _START_ đến _END_ trong số _TOTAL_ mục",
                "infoEmpty": "Hiển thị 0 đến 0 trong số 0 mục",
                "infoFiltered": "(được lọc từ tổng số _MAX_ bản ghi)",
                "paginate": {
                    "first": "Đầu",
                    "last": "Cuối",
                    "next": "Tiếp",
                    "previous": "Trước"
                }
            },
            "pageLength": 10
        });
    });

    var currentPage = window.location.pathname.split("/").pop().split(".")[0];
    document.getElementById(currentPage).classList.add("active");
</script>

</body>
</html>
