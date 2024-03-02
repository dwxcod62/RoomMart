<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông báo</title>
    <!-- Favicon -->
    <link rel="icon" href="./assets/images/favicon/favicon.png" type="image/x-icon"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
          integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <link rel="stylesheet" href="./assets/css/core_style/core.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <link rel="stylesheet" href="./assets/css/renter_page/Renter-report.css">

    <!-- CSS Push Notification -->
    <link rel="stylesheet" href="./assets/css/push_notification_style/style.css">

    <!-- New-->
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>

<body>
<div>
<!-- navbar -->
<%@include file="component/navbar.jsp" %>
<div class="row main-body">
    <%@include file="component/sidebar.jsp" %>
    <div class="content">
        <div class="col-10">
            <!-- Tab menu -->
            <div class="tabs">
                <div class="tabs-item active-tab" onclick="showSentReports()">
                    <i class="tabs-icon fa-solid fa-check-to-slot"></i> Báo cáo đã gửi
                </div>
                <div class="tabs-item" onclick="showSendReportForm()">
                    <i class="tabs-icon fa-solid fa-envelope-open-text"></i> Gửi báo cáo
                </div>
                <div class="line"></div>
            </div>
            <div id="list-notifications-container" class="content__body view_report">
                <table id="notification-table" class="content__table table table-bordered table-striped dataTable no-footer"
                       aria-describedby="notification-table_info">
                    <thead class="content__thead">
                    <tr>
                        <th class="text-center sorting" tabindex="0" aria-controls="notification-table" rowspan="1"
                            colspan="1" aria-label="STT: activate to sort column ascending" style="width: 60px;">STT
                        </th>
                        <th class="text-center sorting" tabindex="0" aria-controls="notification-table" rowspan="1"
                            colspan="1" aria-label="Tiêu đề: activate to sort column ascending"
                            style="width: 399.32px;">Tiêu đề</th>
                        <th class="text-center sorting" tabindex="0" aria-controls="notification-table" rowspan="1"
                            colspan="1" aria-label="Ngày gửi: activate to sort column ascending"
                            style="width: 110px;">Ngày gửi</th>
                        <th class="text-center sorting" tabindex="0" aria-controls="notification-table" rowspan="1"
                            colspan="1" aria-label="Trạng thái: activate to sort column ascending"
                            style="width: 150px;">Trạng thái</th>
                    </tr>
                    </thead>
                    <tbody class="content__tbody">
                    <c:set var="index" value="0" />
                    <c:forEach var="rp" items="${REPORT_LIST}">
                        <c:set var="index" value="${index + 1}" />
                        <tr style="text-align: center">
                            <td>${index}</td>
                            <td>${rp.content}</td>
                            <td>
                                <fmt:parseDate var="sendDate" value="${rp.sendDate}" pattern="yy-MM-dd"/>
                                <fmt:formatDate value="${sendDate}" pattern="dd/MM/yy"/>
                            </td>
                            <td>
                                <c:if test="${rp.status == 0}">
                                    <p style="color: #b12ce0">Chưa tiếp nhận</p>
                                </c:if>
                                <c:if test="${rp.status == 1}">
                                    <p style="color: red">Đang xử lí</p>
                                </c:if>
                                <c:if test="${rp.status == 2}">
                                    <p style="color: green">Đã phản hồi</p>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="content__body send_report">
                <form id="add-notification-form" action="add-notification" method="post" class="custom-form">
                    <div class="form-header">
                        <h1 class="form-title">Gửi thông báo mới</h1>
                    </div>
                    <div class="spacer"></div>
                    <div class="row">
                        <div class="col-6">
                            <div class="form-group">
                                <label for="noti-title" class="form-label">Tiêu đề: <span>*</span></label>
                                <input type="text" id="noti-title" name="noti-title" placeholder="Nhập tiêu đề"
                                       class="form-control" style="font-size: 15px">
                                <span class="form-message"></span>
                            </div>
                        </div>
<%--                        <div class="col-md-6">--%>
<%--                            <div class="form-group">--%>
<%--                                <label for="noti-hostel-id" class="form-label">Khu trọ:--%>
<%--                                    <span>*</span></label>--%>
<%--                                <select name="noti-hostel-id" id="noti-hostel-id" class="form-control">--%>
<%--                                    <option value="">Chọn khu trọ nhận thông báo</option>--%>
<%--                                    <c:forEach var="hostel" items="${sessionScope.HOSTEL_LIST}">--%>
<%--                                        <option value="${hostel.hostelID}">${hostel.hostelName}</option>--%>
<%--                                    </c:forEach>--%>
<%--                                </select>--%>
<%--                                <span class="form-message"></span>--%>
<%--                            </div>--%>
<%--                        </div>--%>
                        <div class="col-12">
                            <div class="form-group">
                                <label for="noti-content" class="form-label">Nội dung:
                                    <span>*</span></label>
                                <textarea name="noti-content" id="noti-content"
                                          class="form-control textarea" style="font-size: 15px"></textarea>
                                <span class="form-message mt-4 mb-0"></span>
                            </div>
                        </div>
                    </div>
                    <div class="spacer"></div>
                    <div class="form-action d-flex justify-content-end">
                        <button class="form-submit">Gửi</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
    <%@include file="component/footer.jsp" %>
    <script src="./assets/js/renter/Renter-navbar.js"></script>


    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap5.min.js"></script>

    <script>
        $(document).ready(function () {
            $('#notification-table').DataTable({
                "language": {
                    "search": "Tìm kiếm:", // Đổi từ "Search" thành "Tìm kiếm"
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
                "pageLength": 10 // Đặt số lượng hàng trên mỗi trang là 10
            });
        });

        function showSentReports() {
            document.getElementById('list-notifications-container').style.display = 'block';
            document.querySelector('.send_report').style.display = 'none';
            document.querySelector('.tabs-item.active-tab')?.classList.remove('active-tab'); // Sử dụng "?." để xử lý trường hợp không có class "active-tab"
            document.querySelector('.tabs-item:nth-child(1)').classList.add('active-tab');
        }

        function showSendReportForm() {
            document.getElementById('list-notifications-container').style.display = 'none';
            document.querySelector('.send_report').style.display = 'block';
            document.querySelector('.tabs-item.active-tab')?.classList.remove('active-tab'); // Sử dụng "?." để xử lý trường hợp không có class "active-tab"
            document.querySelector('.tabs-item:nth-child(2)').classList.add('active-tab');
        }

    </script>
</body>
</html>
