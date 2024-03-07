<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <title>Thông báo</title>
    <link rel="icon" href="./assets/images/favicon/favicon.png" type="image/x-icon"/>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
          integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <link rel="stylesheet" href="./assets/css/core_style/core.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <link rel="stylesheet" href="./assets/css/renter_page/Renter-notification.css">

    <!-- New-->
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>

<div>
    <%@include file="component/navbar.jsp" %>
    <div class="row main-body">
        <%@include file="component/sidebar.jsp" %>
        <div class="content">
            <div class="col-10">
                <div id="list-notifications-container" class="content__body view_report">
                    <table id="notification-table" class="content__table table table-bordered table-striped dataTable no-footer"
                           aria-describedby="notification-table_info">
                        <thead class="content__thead">
                        <tr>
                            <th class="text-center sorting" tabindex="0" aria-controls="notification-table" rowspan="1"
                                colspan="1" aria-label="STT: activate to sort column ascending" style="width: 70px;">STT
                            </th>
                            <th class="text-center sorting" tabindex="0" aria-controls="notification-table" rowspan="1"
                                colspan="1" aria-label="Nội dung: activate to sort column ascending"
                                data-orderable="false">Nội dung</th>
                            <th class="text-center sorting" aria-controls="notification-table" rowspan="1" colspan="1"
                                aria-label="Ngày gửi: activate to sort column ascending" style="width: 120px;"
                                data-orderable="false">Ngày gửi</th>
                        </tr>
                        </thead>
                        <tbody class="content__tbody">
                        <c:set var="index" value="0" />
                        <c:forEach var="nt" items="${NOTIFY}">
                            <c:set var="index" value="${index + 1}" />
                            <tr style="text-align: center">
                                <td>${index}</td>
                                <td style="text-align: left; padding-left: 1.5rem">
                                    <h3>${nt.title}</h3>
                                    <h4 style="font-weight: 400; font-size: 1.6rem">${nt.content}</h4>
                                </td>
                                <td>
                                    <fmt:parseDate var="createDate" value="${nt.createDate}" pattern="yy-MM-dd"/>
                                    <fmt:formatDate value="${createDate}" pattern="dd-MM-yy"/>
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
<%@include file="component/footer.jsp" %>
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
<script src="./assets/js/renter/Renter-navbar.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap5.min.js"></script>

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
