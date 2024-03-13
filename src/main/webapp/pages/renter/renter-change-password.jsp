<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đổi mật khẩu</title>

    <!-- Import các thư viện CSS cần thiết -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
          integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">
    <!-- Import các file CSS tùy chỉnh của bạn -->
    <link rel="stylesheet" href="./assets/css/core_style/core.css">
    <link rel="stylesheet" href="./assets/css/renter_page/Renter-change-password.css">
</head>
<body>
<%@include file="component/navbar.jsp" %>
<div class="row main-body">
    <%@include file="component/sidebar.jsp" %>
    <div class="content">
        <div class="container py-6">
            <div class="row">
                <div class="col-12">
                    <div class="col-6 offset-md-3">
                        <!-- form card change password -->
                        <div class="card card-outline-secondary custom-outline">
                            <div class="card-header">
                                <h2 class="m-2">Đổi mật khẩu</h2>
                            </div>
                            <div class="card-body">
                                <form action="RenterChangePass" class="form" role="form" autocomplete="off">
                                    <div class="form-group m-2">
                                        <label for="inputPasswordOld" class="titles">Mật khẩu hiện tại</label>
                                        <input type="password" class="form-control" id="inputPasswordOld" name="inputPasswordOld" required="">
                                    </div>
                                    <div class="form-group m-2">
                                        <label for="inputPasswordNew" class="titles">Mật khẩu mới</label>
                                        <input type="password" class="form-control" id="inputPasswordNew" name="inputPasswordNew" required="">
                                        <span class="form-text small text-muted fz-14">
                                            Mật khẩu phải có 6 ký tự và <em>không</em> chứa dấu cách.
                                        </span>
                                    </div>
                                    <div class="form-group m-2 mt-2">
                                        <label for="inputPasswordNewVerify" class="titles">Nhập lại mật khẩu</label>
                                        <input type="password" class="form-control" id="inputPasswordNewVerify" name="inputPasswordNewVerify" required="">
                                        <span class="form-text small text-muted fz-14">
                                            Để xác nhận, hãy nhập lại mật khẩu mới.
                                        </span>
                                    </div>
                                    <div class="form-group m-2 mt-4">
                                        <button type="submit" id="saveButton" class="btn btn-outline-dark btn-lg float-right" disabled>
                                            Lưu
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <!-- /form card change password -->
                    </div>
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
<script src="./assets/js/renter/Renter-navbar.js"></script>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        var form = document.querySelector('form');
        var newPassword = document.getElementById('inputPasswordNew');
        var newPasswordVerify = document.getElementById('inputPasswordNewVerify');
        var oldPassword = document.getElementById('inputPasswordOld');
        var saveButton = document.getElementById('saveButton');

        form.addEventListener('input', function(event) {
            // Kiểm tra xem tất cả các trường dữ liệu đã được điền đầy đủ chưa
            if (newPassword.value !== '' && newPasswordVerify.value !== '' && oldPassword.value !== '') {
                saveButton.removeAttribute('disabled');
            } else {
                saveButton.setAttribute('disabled', 'disabled');
            }
        });

        form.addEventListener('submit', function(event) {
            // Kiểm tra xem mật khẩu mới và mật khẩu xác nhận có trùng khớp không
            if (newPassword.value !== newPasswordVerify.value) {
                alert('Mật khẩu mới và mật khẩu xác nhận không trùng khớp!');
                event.preventDefault(); // Ngăn chặn gửi form nếu mật khẩu không trùng khớp
                return;
            }
        });
    });

    function showAlert() {
        if (confirm('Bạn có muốn đổi mật khẩu không?')) {
            alert('Đổi mật khẩu thành công!');
        }
    }
</script>
</body>
</html>