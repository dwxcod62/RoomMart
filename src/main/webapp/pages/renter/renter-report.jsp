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
    <!-- navbar -->
    <%@include file="component/navbar.jsp" %>
    <div>
        <div class="row main-body" style="padding: 0;margin: 0;">
            <%@include file="component/sidebar.jsp" %>
            <div class="col-12 col-lg-9 col-xl-9 col-xxl-10 col-xxl-10 content">
                <div class="col-xxl-9">
                    <div class="col-12 col-md-8 col-lg-8 col-xl-7 col-xxl-8">
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

<%--                        <div class="content__item active">--%>
                            <!-- filter bar -->
<%--                            <div class="filter__wrapper">--%>
<%--                                <table>--%>
<%--                                    <tr>--%>
<%--                                        <td></td>--%>
<%--                                        <td>Khu trọ</td>--%>
<%--                                    </tr>--%>
<%--                                    <tr>--%>
<%--                                        <td><i class="fa-solid fa-sliders"></i> Lọc</td>--%>
<%--                                        <form id="filter-form">--%>
<%--                                            <td>--%>
<%--                                                <select name="hostelId" id="filter__hostel-select">--%>
<%--                                                    <option value="">Tất cả</option>--%>
<%--                                                    <c:forEach var="hostel" items="${sessionScope.HOSTEL_LIST}">--%>
<%--                                                        <option value="${hostel.hostelID}">${hostel.hostelName}</option>--%>
<%--                                                    </c:forEach>--%>
<%--                                                </select>--%>
<%--                                            </td>--%>
<%--                                        </form>--%>
<%--                                    </tr>--%>
<%--                                </table>--%>
<%--                        </div>--%>
                            <!-- Notification list container -->
                            <div id="list-notifications-container" class="content__body mb-5">
                                <table id="notification-table" class="content__table table table-bordered table-striped">
                                    <thead class="content__thead">
                                    <th class="text-center">Mã</th>
                                    <th class="text-center">Tiêu đề</th>
                                    <th class="text-center">Ngày gửi</th>
                                    <th class="text-center">Khu trọ</th>
                                    </thead>
                                    <tbody class="content__tbody">
<%--                                    <c:forEach var="notification" items="${requestScope.NOTIFICATION_LIST}">--%>
                                        <tr>
                                            <td class="text-center">
<%--                                                <a href="ReviewNotificationServlet?action=view&notification_id=${notification.notification_id}">#NF${notification.notification_id}</a>--%>
                                            </td>
                                            <td class="text-center">
<%--                                                <a href="ReviewNotificationServlet?action=view&notification_id=${notification.notification_id}">${notification.title}</a>--%>
                                            </td>
                                            <td class="text-center">
<%--                                                <fmt:parseDate var="ParseDate" value="${notification.createDate}" pattern="yyyy-MM-dd" />--%>
<%--                                                <fmt:formatDate pattern = "dd/MM/yyyy" value="${ParseDate}" />--%>
                                            </td>
                                            <td class="text-center">
<%--                                                <c:forEach var="hostel" items="${sessionScope.HOSTEL_LIST}">--%>
<%--                                                    <c:if test="${hostel.hostelID eq notification.hostel_id}">--%>
<%--                                                        ${hostel.hostelName}--%>
<%--                                                    </c:if>--%>
<%--                                                </c:forEach>--%>
                                            </td>
                                        </tr>
<%--                                    </c:forEach>--%>
                                    </tbody>
                                </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="component/footer.jsp" %>
    <script src="./assets/js/renter/Renter-navbar.js"></script>
</body>
</html>
