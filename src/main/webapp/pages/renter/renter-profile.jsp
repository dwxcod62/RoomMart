<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <h2 class="text-center">Thông tin cá nhân</h2>
                    <div class="card-Info__body">
                        <div class="row cardBody-inside">
                            <div class="col-sm-4 card-Info__body--title">
                                <div class="mb-0">Họ và tên:</div>
                            </div>
                            <div class="col-sm-8 text-secondary">
                                ${sessionScope.USER.accountInfo.information.fullname}
                            </div>
                        </div>
                        <div class="row cardBody-inside">
                            <div class="col-sm-4 card-Info__body--title">
                                <div class="mb-0">Email:</div>
                            </div>
                            <div class="col-sm-8 text-secondary">
                                ${sessionScope.USER.accountInfo.information.email}
                            </div>
                        </div>
                        <div class="row cardBody-inside">
                            <div class="col-sm-4 card-Info__body--title">
                                <div class="mb-0">Ngày sinh:</div>
                            </div>
                            <div class="col-sm-8 text-secondary">
                                <fmt:parseDate pattern="yyyy-MM-dd" value="${sessionScope.USER.accountInfo.information.birthday}" var="birthday"/>
                                <fmt:formatDate value="${birthday}" type="Date" pattern="dd-MM-yyyy"/>
                            </div>
                        </div>
                        <div class="row cardBody-inside">
                            <div class="col-sm-4 card-Info__body--title">
                                <div class="mb-0">Giới tính:</div>
                            </div>
                            <div class="col-sm-8 text-secondary">
                                <c:choose>
                                    <c:when test="${sessionScope.USER.accountInfo.information.sex == 0}">
                                        Nam
                                    </c:when>
                                    <c:otherwise>
                                        Nữ
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="row cardBody-inside">
                            <div class="col-sm-4 card-Info__body--title">
                                <div class="mb-0">Số điện thoại:</div>
                            </div>
                            <div class="col-sm-8 text-secondary">
                                ${sessionScope.USER.accountInfo.information.phone}
                            </div>
                        </div>
                        <div class="row cardBody-inside">
                            <div class="col-sm-4 card-Info__body--title">
                                <div class="mb-0">Căn cước công dân:</div>
                            </div>
                            <div class="col-sm-8 text-secondary">
                                ${sessionScope.USER.accountInfo.information.cccd}
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <a href="RenterProfileUpdate" class="a_custom">
                                    Chỉnh sửa
                                </a>
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