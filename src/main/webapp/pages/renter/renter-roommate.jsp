<%@ page import="com.codebrew.roommart.dto.RoommateInfo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.codebrew.roommart.dto.Account" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xem thành viên</title>
    <link rel="icon" href="./assets/images/favicon/favicon.png" type="image/x-icon"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
          integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="./assets/css/core_style/core.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="./assets/css/renter_page/Renter-roommate.css">

    <!-- CSS Push Notification -->
    <link rel="stylesheet" href="./assets/css/push_notification_style/style.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

</head>

<body>
<div>
    <%
        ArrayList<RoommateInfo> listRoommateInfor = (ArrayList<RoommateInfo>) request.getAttribute("listRoommateInfor");
        Account account = (Account)session.getAttribute("USER");
    %>
    <!-- navbar -->
    <%@include file="component/navbar.jsp" %>

    <!-- content -->
    <div class="main-body row" style="padding: 0;margin: 0;">
        <%@include file="component/sidebar.jsp" %>

        <div class="content">
            <c:if test="${listRoommateInfor.size() == 0}">
                <h1 style="color: red; font-size: 26px; text-align: center; margin-top: 20px">Chưa Có Thành Viên</h1>
            </c:if>
            <c:if test="${listRoommateInfor.size() != 0}">
                <h1 style="font-size: 25px; text-align: center">Danh sách thành viên</h1>
                <table class="table">
                    <thead class="bg-light">
                    <tr style="font-size: 16px">
                        <th>STT</th>
                        <th>Họ và tên</th>
                        <th colspan="3"></th>
                    </tr>
                    </thead>
                    <%
                        int x = 1;
                    %>
                    <c:forEach items="${listRoommateInfor}" var="roommateinfor">
                    <tbody>
                    <tr style="font-size: 16px">
                        <td><%=x%></td>
                        <td class="align-middle">
                            <div>
                                <div class="font-weight-normal">
                                    ${roommateinfor.getInformation().getFullname()}
                                </div>
                                <div class="text-muted"></div>
                            </div>
                        </td>
                        <td class="align-middle">
                            <button type="button" class="btnAction" data-bs-toggle="modal"
                                data-bs-target="#staticBackdrop<%=x%>">Chi tiết</button>
                        </td>
                    </tr>
                    </tbody>
                    <%
                        x += 1;
                    %>
                    </c:forEach>
                </table>

                <%
                   for (int y = 1; y < x; y++) {
                %>
                <div class="modal fade" id="staticBackdrop<%=y%>" data-bs-backdrop="static" data-bs-keyboard="false"
                     tabindex="-1"
                     aria-labelledby="staticBackdropLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="staticBackdropLabel">Thông Tin Chi Tiết
                                </h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                            </div>
                            <div class="modal-body" style="text-align: left">
                                <div class="row roommate-details">
                                    <div class="col-sm-4">
                                        <div class="mb-0">Họ và Tên:</div>
                                    </div>
                                    <div class="col-sm-8">
                                        <%=listRoommateInfor.get(y - 1).getInformation().getFullname()%>
                                    </div>
                                </div>
                                <div class="row roommate-details">
                                    <div class="col-sm-4">
                                        <div class="mb-0">Email:</div>
                                    </div>
                                    <div class="col-sm-8">
                                        <%=listRoommateInfor.get(y - 1).getInformation().getEmail()%>
                                    </div>
                                </div>
                                <div class="row roommate-details">
                                    <div class="col-sm-4">
                                        <div class="mb-0">Ngày Sinh:</div>
                                    </div>
                                    <div class="col-sm-8">
                                        <fmt:parseDate pattern="yyyy-MM-dd" value="<%=listRoommateInfor.get(y - 1).getInformation().getBirthday()%>" var="birthday"/>
                                        <fmt:formatDate value="${birthday}" type="Date" pattern="dd-MM-yyyy"/>
                                    </div>
                                </div>
                                <div class="row roommate-details">
                                    <div class="col-sm-4">
                                        <div class="mb-0">Giới Tính:</div>
                                    </div>
                                    <div class="col-sm-8">
                                        <%
                                            String sex = String.valueOf(listRoommateInfor.get(y - 1).getInformation().getSex());
                                            if (sex.equals("1")) {
                                                out.print("Nam");
                                            } else {
                                                out.print("Nữ");
                                            }
                                        %>
                                    </div>
                                </div>
                                <div class="row roommate-details">
                                    <div class="col-sm-4">
                                        <div class="mb-0">Số Điện Thoại:</div>
                                    </div>
                                    <div class="col-sm-8">
                                        <%=listRoommateInfor.get(y - 1).getInformation().getPhone()%>
                                    </div>
                                </div>
                                <div class="row roommate-details">
                                    <div class="col-sm-4">
                                        <div class="mb-0">Địa Chỉ:</div>
                                    </div>
                                    <div class="col-sm-8">
                                        <%=listRoommateInfor.get(y - 1).getInformation().getAddress()%>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary btn_custom" data-bs-dismiss="modal">Thoát</button>
                            </div>
                        </div>
                    </div>
                </div>
                <%
                    }
                %>
            </c:if>
        </div>

    </div>

    <!-- footer -->

    <%@include file="component/footer.jsp" %>

    <!-- Push notification element -->
    <div id="push-noti"></div>

    <script src="./assets/js/renter/Renter-navbar.js"></script>

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
            integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
            crossorigin="anonymous"></script>
    <script src="./assets/js/renter/Renter-roommate.js"></script>

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

    <script>
        var currentPage = window.location.pathname.split("/").pop().split(".")[0];
        document.getElementById(currentPage).classList.add("active");
    </script>

</body>
</html>