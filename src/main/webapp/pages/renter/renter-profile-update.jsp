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
    <link rel="stylesheet" href="./assets/css/renter_page/Renter-profile-update.css">
    <link rel="stylesheet" href="./assets/css/push_notification_style/style.css">
</head>
<body>
<%
    Account account = (Account) session.getAttribute("USER");
%>
<%@include file="component/navbar.jsp" %>
<div class="row main-body">
    <%@include file="component/sidebar.jsp" %>
    <div class="content">
        <div class="col-6">
            <div class="row cardInside">
                <div class="col-12">
                    <div class="card-blockInfo">
                        <div class="infoHeader">
                            <h2>Chỉnh sửa thông tin</h2>
                        </div>
                        <form action="RenterProfileUpdate" method="post"  style="padding: 0 40px">
                            <div class="form-group">
                                <label for="fullname">Họ và tên:</label>
                                <input type="text" id="fullname" name="new-name" class="form-control"
                                         value="${sessionScope.USER.accountInfo.information.fullname}" required>
                            </div>
                            <div class="form-group">
                                <label for="email">Email:</label>
                                <input type="email" id="email" name="new-email" class="form-control"
                                       value="${sessionScope.USER.accountInfo.information.email}" required>
                            </div>
                            <div class="form-group">
                                <label for="birthday">Ngày sinh:</label>
                                <input type="date" id="birthday" name="new-birthday" class="form-control"
                                       value="${sessionScope.USER.accountInfo.information.birthday}" required>
                            </div>
                            <div class="form-group">
                                <label for="sex" class="form-label">Giới tính:</label>
                                <div class="input-group">
                                    <select id="sex" name="new-sex" class="form-select" required>
                                        <option value="0" ${sessionScope.USER.accountInfo.information.sex == 0 ? 'selected' : ''}>Nam</option>
                                        <option value="1" ${sessionScope.USER.accountInfo.information.sex == 1 ? 'selected' : ''}>Nữ</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="phone">Số điện thoại:</label>
                                <input type="tel" id="phone" name="new-phone" class="form-control"
                                       value="${sessionScope.USER.accountInfo.information.phone}">
                            </div>
                            <div class="form-group">
                                <label for="cccd">Căn cước công dân:</label>
                                <input type="text" id="cccd" name="new-cccd" class="form-control"
                                       value="${sessionScope.USER.accountInfo.information.cccd}">
                            </div>
                            <div>
                                <button class="btn btn-outline-dark" data-bs-toggle="modal"
                                        data-bs-target="#change-room-status-modal"
                                        style="font-size: 1.6rem; font-weight: 600; padding: 8px 12px;">
                                    Luu
                                </button>
                                <div class="modal fade" id="change-room-status-modal" tabindex="-1"
                                     aria-labelledby="change-room-status-modalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title text-warning" id="change-room-status-modalLabel">
                                                    Cảnh báo
                                                </h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                        aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body mt-5 mb-5"
                                                 style="font-size: 1.8rem; line-height: 2.8rem;">
                                                Phòng này đang được tạo hợp đồng cho thuê, bạn có chắc là muốn cập nhật trạng thái về
                                                "<span style="font-weight: 600;">Sẵn sàng cho thuê</span>" không?
                                            </div>
                                            <div class="modal-footer justify-content-between">
                                                <button type="button" class="btn btn-secondary"
                                                        data-bs-dismiss="modal">Hủy bỏ
                                                </button>
                                                <form action="end-rental-contract" method="POST">
                                                    <input type="hidden" name="room-id" value="${sessionScope.room.roomId}" />
                                                    <input type="hidden" name="renter-account-id" value="${requestScope.renterAccount.accId}" />
                                                    <button type="submit" class="btn btn-danger">Đồng ý</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                            </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="component/footer.jsp" %>

<div id="push-noti"></div>

<!-- Toast element -->
<div id="toast">&nbsp;</div>

<script src="./assets/js/renter/Renter-navbar.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
<!-- JQuery -->
<script src="./assets/js/jquery-3.5.1.min.js" type="text/javascript"></script>
<!-- Link your script here -->
<script src="./assets/js/handle-main-navbar.js"></script>
<script src="./assets/js/valid-form.js"></script>
<script src="./assets/js/owner/room-detail/validate-input.js"></script>
<script src="./assets/js/toast-alert.js"></script>
<!-- Push notification -->
<script src="./assets/js/push-notification-alert.js"></script>
<!-- Web socket -->
<script src="./assets/js/sendWebsocket.js"></script>
<script src="./assets/js/receiveWebsocket.js"></script>
</body>
</html>
