<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Favicon -->
    <link rel="icon" href="./assets/images/favicon/favicon.png" type="image/x-icon"/>

    <!-- Title -->
    <title>Room Mart - Tính tiền phòng</title>

    <!-- Link Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <!-- Link your CSS here -->
    <link rel="stylesheet" href="./assets/css/core_style/core.css">
    <link rel="stylesheet" href="./assets/css/hostel_owner_style/room-calculate-fee_style/style.css">
    <link rel="stylesheet" href="./assets/css/push_notification_style/style.css">
</head>
    <body>
        <%@include file="../components/navbar.jsp" %>

        <div class="container min-height">
            <div class="row position-relative">
                <!-- Side bar -->
                <div class="col-12 col-lg-3 col-xl-3 col-xxl-2"><%@include file="../components/sidebar.jsp" %></div>

                <!-- Content -->
                <div class="col-12 col-lg-9 col-xl-9 col-xxl-10 content-group">
                    <div class="content-history pt-5 pb-5">
                        <a href="list-hostels" class="history-link">Danh sách khu trọ</a>
                        <i class="fa-solid fa-chevron-right"></i>
                        <a href="detailHostel?hostelID=${sessionScope.hostel.hostelID}" class="history-link">${sessionScope.hostel.hostelName}</a>
                        <i class="fa-solid fa-chevron-right"></i>
                        <a href="roomDetail?roomID=${sessionScope.room.roomId}" class="history-link">Phòng ${sessionScope.room.roomNumber}</a>
                        <i class="fa-solid fa-chevron-right"></i>
                        <div class="current">Tính tiền phòng</div>
                    </div>
                    <div class="row mb-5">
                        <div class="content-body col-12 col-lg-12 col-xxl-9 m-auto">
                            <form action="calculateTotalCost" method="POST" class="bill__form d-flex justify-content-end">
                                <div class="bill">
                                    <c:set var="consumeThisMonth" value="${requestScope.consumeListThisMonth}" />
                                    <c:set var="consumeBeginMonth" value="${consumeThisMonth.get(consumeThisMonth.size() - 1)}" />
                                    <c:set var="consumeEndMonth" value="${consumeThisMonth.get(0)}" />
                                    <h1 class="bill__title">Hóa đơn tháng ${requestScope.billTitle}</h1>
                                    <div class="row">
                                        <div class="col-12 col-sm-6">
                                            <p class="bill__item">Khu trọ: <span>${sessionScope.hostel.hostelName}</span></p>
                                            <p class="bill__item">Phòng số: <span>${sessionScope.room.roomNumber}</span></p>
                                            <p class="bill__item">
                                                Địa chỉ:
                                                <span
                                                >${sessionScope.hostel.address}, ${sessionScope.hostel.ward},
                                                    ${sessionScope.hostel.district}, ${sessionScope.hostel.city}</span
                                                >
                                            </p>
                                            <div class="bill__consume">
                                                <div class="bill__consume-name">Điện</div>
                                                <c:set
                                                    var="consumeElectricNumber"
                                                    value="${consumeEndMonth.numberElectric - consumeBeginMonth.numberElectric}"
                                                />
                                                <div class="bill__consume-number">
                                                    Số cũ: <span>${consumeBeginMonth.numberElectric}</span>, Số mới:
                                                    <span>${consumeEndMonth.numberElectric}</span>, Tiêu thụ:
                                                    <span>${consumeElectricNumber}</span>
                                                </div>
                                            </div>
                                            <div class="bill__consume">
                                                <div class="bill__consume-name">Nước</div>
                                                <c:set
                                                    var="consumeWaterNumber"
                                                    value="${consumeEndMonth.numberWater - consumeBeginMonth.numberWater}"
                                                />
                                                <div class="bill__consume-number">
                                                    Số cũ: <span>${consumeBeginMonth.numberWater}</span>, Số mới:
                                                    <span>${consumeEndMonth.numberWater}</span>, Tiêu thụ:
                                                    <span>${consumeWaterNumber}</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-12 col-sm-6">
                                            <p class="bill__item">
                                                Ngày tạo hóa đơn:
                                                <jsp:useBean id="now" class="java.util.Date" />
                                                <fmt:formatDate var="dateCreate" value="${now}" pattern="dd/MM/yyyy" />
                                                <fmt:formatDate var="dateCreateValue" value="${now}" pattern="yyyy/MM/dd" />
                                                <span>${dateCreate}</span>
                                            </p>
                                            <p class="bill__item">
                                                Ngày tới hạn thanh toán:
                                                <span>
                                                    <select name="expiredDate">
                                                        <option value="${dateCreateValue}">${dateCreate}</option>
                                                        <c:forEach begin="1" end="10" varStatus="loop">
                                                            <c:set target="${now}" property="time" value="${now.time + 86400000}" />
                                                            <fmt:formatDate var="expiredDateOption" value="${now}" pattern="dd/MM/yyyy" />
                                                            <fmt:formatDate var="expiredDateValue" value="${now}" pattern="yyyy/MM/dd" />
                                                            <option value="${expiredDateValue}">${expiredDateOption}</option>
                                                        </c:forEach>
                                                    </select>
                                                </span>
                                            </p>
                                        </div>
                                    </div>
                                    <div class="bill__table">
                                        <table class="table table-success table-striped table-bordered">
                                            <thead>
                                            <tr class="text-center">
                                                <th>STT</th>
                                                <th>Tên</th>
                                                <th>Đơn vị tính</th>
                                                <th>Số lượng</th>
                                                <th>Đơn giá</th>
                                                <th>Thành tiền</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:set var="count" value="0" />
                                            <c:set var="totalCost" value="0" />
                                            <c:forEach var="service" items="${requestScope.serviceInfo}">
                                                <c:set var="count" value="${count+1}" />
                                                <c:set var="quantity" value="1" />
                                                <tr>
                                                    <td>${count}</td>
                                                    <td>${service.serviceName}</td>
                                                    <td>${service.unit}</td>
                                                    <c:choose>
                                                        <c:when test="${service.serviceName eq 'Điện'}">
                                                            <c:set var="quantity" value="${consumeElectricNumber}" />
                                                            <td>${consumeElectricNumber}</td>
                                                        </c:when>
                                                        <c:when test="${service.serviceName eq 'Nước'}">
                                                            <c:set var="quantity" value="${consumeWaterNumber}" />
                                                            <td>${consumeWaterNumber}</td>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:set var="quantity" value="1" />
                                                            <td>1</td>
                                                        </c:otherwise>
                                                    </c:choose>

                                                    <td>
                                                        <fmt:formatNumber value="${service.servicePrice}" type="currency" currencySymbol="VNĐ" />
                                                    </td>

                                                    <c:set var="totalCost" value="${totalCost + service.servicePrice * quantity}" />
                                                    <td>
                                                        <fmt:formatNumber
                                                            value="${service.servicePrice * quantity}"
                                                            type="currency"
                                                            currencySymbol="VNĐ"
                                                        />
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td>${count+1}</td>
                                                <td>Tiền phòng</td>
                                                <td>phòng</td>
                                                <td>1</td>
                                                <td>${requestScope.contractRoom.price}</td>
                                                <c:set var="totalCost" value="${totalCost + requestScope.contractRoom.price}" />
                                                <td>
                                                    <fmt:formatNumber
                                                        value="${requestScope.contractRoom.price}"
                                                        type="currency"
                                                        currencySymbol="VNĐ"
                                                    />
                                                </td>
                                            </tr>
                                            <!-- Total -->
                                            <td colspan="5" class="text-end total">Tổng tiền:</td>
                                            <td><fmt:formatNumber value="${totalCost}" type="currency" currencySymbol="VNĐ" /></td>
                                            </tbody>
                                        </table>
                                    </div>

                                    <div class="bill__sign">
                                        <div class="row">
                                            <div class="col-6">
                                                <div class="bill__sign-label">Người lập hóa đơn</div>
                                                <div class="bill__sign-name">${sessionScope.USER.accountInfo.information.fullname}</div>
                                            </div>
                                            <div class="col-6">
                                                <div class="bill__sign-label">Người thanh toán</div>
                                                <div class="bill__sign-name">${requestScope.renterName}</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="bill__spacer"></div>
                                    <!-- Direct to room detail -->

                                    <input type="hidden" name="totalCost" value="${totalCost}" />
                                    <input type="hidden" name="consumeStartID" value="${consumeBeginMonth}" />
                                    <input type="hidden" name="consumeEndID" value="${consumeEndMonth}" />
                                    <input type="hidden" name="billTitle" value="${requestScope.billTitle}" />
                                    <div class="bill__form d-flex justify-content-between">
                                        <button class="btn btn-primary fs-2" data-bs-toggle="modal" data-bs-target="#confrim-bill-modal" type="button">Xác nhận</button>
                                        <div class="modal fade" id="confrim-bill-modal" tabindex="-1" aria-labelledby="confrim-bill-modal" aria-hidden="true">
                                            <div class="modal-dialog modal-dialog-centered">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title text-warning" id="change-room-status-modalLabel">
                                                            Xác nhận
                                                        </h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="form-group" style="margin: 0px 10px">
                                                        <label for="bank-list" class="form-label">Chọn ngân hàng: <span>*</span></label>
                                                        <select name="bank-list" id="bank-list" class="form-control form-select bank-list">
                                                            <option value="all" selected>Chọn ngân hàng</option>
                                                        </select>
                                                        <span class="form-message"></span>
                                                    </div>

                                                    <div class="form-group" style="margin: 0px 10px">
                                                        <label for="bank-list" class="form-label">Chọn ngân hàng: <span>*</span></label>
                                                        <input id="stk" name="Stk"  type="text" placeholder="VD: 0123456789"
                                                               class="form-control">
                                                        <span class="form-message"></span>
                                                    </div>

                                                    <div class="modal-footer justify-content-between">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy bỏ</button>
                                                        <button type="submit" class="btn btn-danger">Đồng ý</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <a href="ownerRoomDetail?roomID=${sessionScope.room.roomId}&hostelID=${sessionScope.hostel.hostelID}" class="btn btn-secondary fs-2"> Hủy bỏ </a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <%@include file="../components/footer.jsp" %>

        <div id="push-noti"></div>
        <%-- Script  --%>
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"
        ></script>
        <script src="./assets/js/toast-alert.js"></script>
        <script src="./assets/js/jquery-3.5.1.min.js"></script>
        <script src="./assets/js/handle-main-navbar.js"></script>
        <script src="./assets/js/push-notification-alert.js"></script>
        <script src="./assets/js/receiveWebsocket.js"></script>
        <script src="./assets/js/jquery.dataTables.min.js" type="text/javascript"></script>
        <script type="text/javascript">
            // Receive
            receiveWebsocket(alertPushNoti);

            // Close when leave
            window.onbeforeunload = function () {
                receiveWebsocket.disconnectWebSocket();
            };
        </script>

        <script>
            var bankSelect = document.getElementById("bank-list");
            var request = new XMLHttpRequest();
            request.open('GET', 'https://api.vietqr.io/v2/banks', true);

            request.onload = function() {
                if (request.status >= 200 && request.status < 400) {
                    var data = JSON.parse(request.responseText);

                    data.data.forEach(function(bank) {
                        var option = document.createElement("option");
                        option.value = bank.id;
                        option.setAttribute('data-thumbnail', bank.logo);
                        option.appendChild(document.createTextNode(bank.name));
                        bankSelect.add(option);
                    });
                } else {
                    console.error("Không thể lấy dữ liệu từ API.");
                }
            };
            request.send();
        </script>
    </body>
</html>
