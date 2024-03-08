<%@ page import="com.codebrew.roommart.dto.Account" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cập nhật thông tin</title>
    <!-- Import các thư viện CSS cần thiết -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
          integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">
    <!-- Import các file CSS tùy chỉnh của bạn -->
    <link rel="stylesheet" href="./assets/css/core_style/core.css">
    <link rel="stylesheet" href="./assets/css/renter_page/Renter-profile.css">
</head>
<body>
<%
    Account account = (Account) session.getAttribute("USER");
%>
<div>
    <%@include file="component/navbar.jsp" %>
    <div class="row main-body">
        <%@include file="component/sidebar.jsp" %>
        <div class="content">
            <div class="col-6">
                <div class="card-info">
                    <div class="card-Info__body">
                        <div class="row">
                            <div class="col-sm-3 mb-4 card-Info__body--title">
                                <h6 class="mb-0">Full Name</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                Kenneth Valdez
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-3 mb-4 card-Info__body--title">
                                <h6 class="mb-0">Email</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                fip@jukmuh.al
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-3 mb-4 card-Info__body--title">
                                <h6 class="mb-0">Phone</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                (239) 816-9029
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-3 mb-4 card-Info__body--title">
                                <h6 class="mb-0">Mobile</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                (320) 380-4539
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-3 mb-4 card-Info__body--title">
                                <h6 class="mb-0">Address</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                Bay Area, San Francisco, CA
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <a class="btn btn-info " target="__blank" href="https://www.bootdey.com/snippets/view/profile-edit-data-and-skills">Edit</a>
                            </div>
                        </div>
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

<script src="./assets/js/renter/Renter-navbar.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap5.min.js"></script>
</body>

</html>