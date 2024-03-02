<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                    <div class="tabs-item active">
                        <i class="tabs-icon fa-solid fa-check-to-slot"></i> Báo cáo đã gửi
                    </div>
                    <div class="tabs-item active">
                        <i class="tabs-icon fa-solid fa-envelope-open-text"></i> Gửi báo cáo
                    </div>
                    <div class="line"></div>
                </div>
                    <div id="list-notifications-container" class="content__body mb-5">
                        <table id="notification-table" class="content__table table table-bordered table-striped dataTable no-footer"
                               aria-describedby="notification-table_info">
                            <thead class="content__thead">
                            <tr>
                                <th class="text-center sorting" tabindex="0" aria-controls="notification-table" rowspan="1"
                                    colspan="1" aria-label="Mã: activate to sort column ascending" style="width: 104.292px;">Mã
                                </th>
                                <th class="text-center sorting" tabindex="0" aria-controls="notification-table" rowspan="1"
                                    colspan="1" aria-label="Tiêu đề: activate to sort column ascending"
                                    style="width: 180.615px;">Tiêu đề</th>
                                <th class="text-center sorting" tabindex="0" aria-controls="notification-table" rowspan="1"
                                    colspan="1" aria-label="Ngày gửi: activate to sort column ascending"
                                    style="width: 203.542px;">Ngày gửi</th>
                                <th class="text-center sorting" tabindex="0" aria-controls="notification-table" rowspan="1"
                                    colspan="1" aria-label="Khu trọ: activate to sort column ascending"
                                    style="width: 175.552px;">Khu trọ</th>
                            </tr>
                            </thead>
                            <tbody class="content__tbody">
                            <tr>
                                <td>1</td>
                                <td>Tiêu đề 11</td>
                                <td>Ngày gửi 11</td>
                                <td>Khu trọ 11</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Tiêu đề 11</td>
                                <td>Ngày gửi 11</td>
                                <td>Khu trọ 11</td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td>Tiêu đề 11</td>
                                <td>Ngày gửi 11</td>
                                <td>Khu trọ 11</td>
                            </tr>
                            <tr>
                                <td>4</td>
                                <td>Tiêu đề 11</td>
                                <td>Ngày gửi 11</td>
                                <td>Khu trọ 11</td>
                            </tr>
                            <tr>
                                <td>5</td>
                                <td>Tiêu đề 11</td>
                                <td>Ngày gửi 11</td>
                                <td>Khu trọ 11</td>
                            </tr>
                            <tr>
                                <td>6</td>
                                <td>Tiêu đề 11</td>
                                <td>Ngày gửi 11</td>
                                <td>Khu trọ 11</td>
                            </tr>
                            <tr>
                                <td>7</td>
                                <td>Tiêu đề 11</td>
                                <td>Ngày gửi 11</td>
                                <td>aka</td>
                            </tr>
                            <tr>
                                <td>8</td>
                                <td>Tiêu đề 11</td>
                                <td>Ngày gửi 11</td>
                                <td>Khu trọ 11</td>
                            </tr>
                            <tr>
                                <td>9</td>
                                <td>Tiêu đề 11</td>
                                <td>Ngày gửi 11</td>
                                <td>Khu trọ 11</td>
                            </tr>
                            <tr>
                                <td>10</td>
                                <td>Tiêu đề 11</td>
                                <td>Ngày gửi 11</td>
                                <td>Khu trọ 11</td>
                            </tr>
                            <tr>
                                <td>11</td>
                                <td>Tiêu đề 11</td>
                                <td>Ngày gửi 11</td>
                                <td>Khu trọ 11</td>
                            </tr>
                            </tbody>
                        </table>
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
    </script>
</body>
</html>
