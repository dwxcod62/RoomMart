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

    <link rel="stylesheet" href="./assets/css/renter_page/Renter-notification-detail.css">

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
                <div class="content__body">
                    <div class="noti-content">
                        <span>
                            ${NOTI-DETAIL}
                        </span>
                        <p class="noti-detail-1">
                            Post by binhbna on 02/03/24 11:02
                        </p>
                        <hr>
                        <p class="noti-detail-2">
                            Phòng Khảo thí thông báo đến các em sinh viên danh sách phòng thi Retake Block 5 (03/03/2024) (VNR202 HCM202 MLN111)

                            Chi tiết ở tệp đính kèm.

                            Khi đi thi các em sinh viên chuẩn bị thẻ sinh viên, bút, tai nghe có dây và dây sạc laptop; kiểm tra phần mềm và máy tính cá nhân trước ngày thi; đọc kỹ nội quy phòng thi tại đây.

                            Academic Examination Department would like to inform students about the updated room list of  Retake Block 5 (03/03/2024) (VNR202 HCM202 MLN111). Details in the attached file.

                            Students come to campus to take exam.

                            Remember to bring ID card, pen, wired earphones and laptop charger with you; check the testing software and personal laptop before the exam date; read the Examination room regulations carefully
                        </p>
                    </div>
                    <a href="">Trở lại</a>
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

<script src="./assets/js/renter/Renter-navbar.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap5.min.js"></script>

</body>
</html>