<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                                    <span class="fw-medium">Hóa đơn số: </span>
                                </p>
                                <p class="mb-2">
                                    <span class="fw-medium">Phòng số: </span>
                                </p>
                                <p class="mb-2">
                                    <span class="fw-medium">Tình trạng: </span>
                                </p>
                            </div>
                            <div class="float-right">
                                <p class="mb-2">
                                    <span class="fw-medium">Ngày tạo hóa đơn: </span>
                                </p>
                                <p class="mb-2">
                                    <span class="fw-medium">Hạn thanh toán: </span>
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
                            <tr>
                                <td>Điện</td>
                                <td>3.500</td>
                                <td>Kwh</td>
                                <td>150</td>
                                <td>350</td>
                                <td>350.000</td>
                            </tr>
                            <tr>
                                <td>Tổng tiền</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td>5.350.000</td>
                            </tr>
                            </tbody>
                        </table>
                        <a href="" class="btn btn-outline-success btn-lg float-left mt-4">
                            Xuất hóa đơn ra file Excel
                        </a>
                        <a href="" class="btn btn-outline-danger btn-lg float-right mt-4">
                            Thanh toán
                        </a>
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
</body>
</html>