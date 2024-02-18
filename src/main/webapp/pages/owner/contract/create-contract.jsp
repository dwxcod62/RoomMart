<%--
  Created by IntelliJ IDEA.
  User: Kudamii
  Date: 2/13/2024
  Time: 1:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet" />

    <!-- Bootstrap Datepicker CSS -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css" rel="stylesheet">


    <!-- Link your CSS here -->
    <link rel="stylesheet" href="./assets/css/core_style/core.css">
    <link rel="stylesheet" href="./assets/css/hostel_owner_style/room-create-account-style/style.css">
    <link rel="stylesheet" href="./assets/css/push_notification_style/style.css">

</head>
<body>
<%@include file="../components/navbar.jsp"%>
<div class="container min-height">
    <div class="row position-relative">
        <!-- Side bar -->
        <div class="col-12 col-lg-3 col-xl-3 col-xxl-2">
            <%@include file="../components/sidebar.jsp"%>
        </div>

        <!-- Content -->
        <div class="col-12 col-lg-9 col-xl-9 col-xxl-10 content-group">
            <div class="row my-5">
                <div class="content-body col-12 col-md-10 col-lg-9 col-xl-7 col-xxl-6 m-auto">
                    <form action="createContract" method="post" class="custom-form create-room-account-form" id="create-room-account-form">
                        <input type="hidden" name="room_id" value="${requestScope.ROOM.roomId}" />
                        <div class="form-header">
                            <div class="form-title main-title">Tạo hợp đồng</div>
                        </div>
                        <div class="spacer"></div>
                        <div>
                            <p><strong class="text-dark">Số phòng:</strong> <span class="text-muted">${requestScope.ROOM.roomNumber}</span></p>
                        </div>
                        <div class="form-group">
                            <div class="row align-items-center">
                                <div class="col-6">
                                    <label for="room-email" class="form-label">Email: <span>*</span></label>
                                </div>
                                <div class="col-6">
                                    <input
                                            id="room-email"
                                            name="room-email"
                                            type="text"
                                            value=""
                                            class="form-control m-0"
                                            placeholder="Nhập email của người thuê"
                                    />
                                </div>
                            </div>
                            <div class="row align-items-center my-2">
                                <div class="col-6">
                                    <span class="form-message mt-4"> </span>
                                </div>
                                <div class="col-6">
                                    <button type="button" class="btn btn-primary" id="checkButton">Check</button>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="row align-items-center">
                                <div class="col-6">
                                    <label for="input_from" class="form-label">Ngày bắt đầu hợp đồng: <span>*</span></label>
                                </div>
                                <div class="col-6">
                                    <input
                                            type="text"
                                            class="form-control m-0 datepicker"
                                            id="input_from"
                                            placeholder="Ngày bắt đầu"
                                            name="room-startdate"
                                            value=""
                                    />
                                </div>
                            </div>
                            <span class="form-message mt-4"></span>
                        </div>
                        <div class="form-group">
                            <div class="row align-items-center">
                                <div class="col-6">
                                    <label for="input_to" class="form-label">Ngày kết thúc hợp đồng: <span>*</span></label>
                                </div>
                                <div class="col-6">
                                    <input
                                            type="text"
                                            class="form-control m-0 datepicker"
                                            id="input_to"
                                            placeholder="Ngày kết thúc"
                                            name="room-enddate"
                                            value=""
                                    />
                                </div>
                            </div>
                            <span class="form-message mt-4"></span>
                        </div>
                        <div class="form-group">
                            <div class="row align-items-center">
                                <div class="col-6">
                                    <label for="room-electric" class="form-label">Số điện hiện tại: <span>*</span></label>
                                </div>
                                <div class="col-6">
                                    <input
                                            id="room-electric"
                                            name="room-electric"
                                            type="number"
                                            value=""
                                            class="form-control m-0"
                                            placeholder="Nhập số điện mới nhất của phòng"
                                    />
                                </div>
                            </div>
                            <span class="form-message mt-4"></span>
                        </div>
                        <div class="form-group">
                            <div class="row align-items-center">
                                <div class="col-6">
                                    <label for="room-water" class="form-label">Số nước hiện tại: <span>*</span></label>
                                </div>
                                <div class="col-6">
                                    <input
                                            id="room-water"
                                            name="room-water"
                                            type="number"
                                            value=""
                                            class="form-control m-0"
                                            placeholder="Nhập số nước mới nhất của phòng"
                                    />
                                </div>
                            </div>
                            <span class="form-message mt-4"></span>
                        </div>
                        <div class="form-group">
                            <div class="row align-items-center">
                                <div class="col-6">
                                    <label for="room-fee" class="form-label">Tiền phòng: <span>*</span></label>
                                </div>
                                <div class="col-6">
                                    <div class="input-group">
                                        <input
                                                id="room-fee"
                                                name="room-fee"
                                                type="number"
                                                class="form-control m-0"
                                                placeholder="Nhập số tiền phòng"
                                                value=""
                                        />
                                        <div class="input-group-append">
                                            <span class="input-group-text font-weight-bold"> / Tháng</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <span class="form-message mt-4"></span>
                        </div>
                        <div class="form-group">
                            <div class="row align-items-center">
                                <div class="col-6">
                                    <label for="payment-term" class="form-label">Kì hạn trả tiền: <span>*</span></label>
                                </div>
                                <div class="col-6">
                                    <div class="input-group">
                                        <input
                                                id="payment-term"
                                                name="payment-term"
                                                type="number"
                                                class="form-control m-0"
                                                placeholder="Nhập số tháng"
                                                value=""
                                                style="width: 60px"
                                        />
                                        <div class="input-group-append">
                                            <span class="input-group-text font-weight-bold">Tháng / Lần</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <span class="form-message mt-4"></span>
                        </div>

                        <div class="form-group">
                            <div class="row align-items-center">
                                <div class="col-6">
                                    <label for="room-deposit" class="form-label">Tiền cọc: <span>*</span></label>
                                </div>
                                <div class="col-6">
                                    <input
                                            id="room-deposit"
                                            name="room-deposit"
                                            type="number"
                                            value=""
                                            class="form-control m-0"
                                            placeholder="Nhập số tiền cọc cho phòng"
                                    />
                                </div>
                            </div>
                            <span class="form-message mt-4"></span>
                        </div>

                        <input type="checkbox" id="toggle-checkbox" /> Hiển thị các cài đặt tăng giá nhà<br /><br />

                        <div class="form-group advanced-form">
                            <div class="row align-items-center">
                                <div class="col-6">
                                    <label for="room-deposit" class="form-label">Giá nhà cố định trong: <span>*</span></label>
                                </div>
                                <div class="col-6">
                                    <div class="input-group">
                                        <input
                                                id="fixed-years"
                                                name="fixed-years"
                                                type="number"
                                                class="form-control m-0"
                                                placeholder="Nhập số năm"
                                                value=""
                                        />
                                        <div class="input-group-append">
                                            <span class="input-group-text font-weight-bold">Năm</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <span class="form-message mt-4"></span>
                        </div>

                        <div class="form-group advanced-form">
                            <div class="row align-items-center">
                                <div class="col-6">
                                    <label for="room-deposit" class="form-label">Giá tăng không vượt: <span>*</span></label>
                                </div>
                                <div class="col-6">
                                    <div class="input-group">
                                        <input
                                                id="percentage-increase"
                                                name="percentage-increase"
                                                type="number"
                                                class="form-control m-0"
                                                placeholder="Nhập phần trăm"
                                                value=""
                                        />
                                        <div class="input-group-append">
                                            <span class="input-group-text font-weight-bold">% / Năm</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <span class="form-message mt-4"></span>
                        </div>

                        <div class="spacer"></div>
                        <div class="create-room-account-actions">
                            <a href="roomDetail?roomID=${sessionScope.room.roomId}" class="form-submit">Hủy bỏ</a>
                            <button type="submit" class="form-submit">Xác nhận và gửi</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Footer -->
<%@include file="../components/footer.jsp"%>

<!-- Push notification element -->
<div id="push-noti"></div>

<!-- Toast element -->
<div id="toast">&nbsp;</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>

<script src="./assets/js/contract/create-contract.js"></script>

</body>
</html>
