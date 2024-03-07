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
                                <h2 class="m-2">Change Password</h2>
                            </div>
                            <div class="card-body">
                                <form class="form" role="form" autocomplete="off">
                                    <div class="form-group m-2">
                                        <label for="inputPasswordOld" class="titles">Current Password</label>
                                        <input type="password" class="form-control" id="inputPasswordOld" required="">
                                    </div>
                                    <div class="form-group m-2">
                                        <label for="inputPasswordNew" class="titles">New Password</label>
                                        <input type="password" class="form-control" id="inputPasswordNew" required="">
                                        <span class="form-text small text-muted fz-14">
                                            The password must be 8-20 characters, and must <em>not</em> contain spaces.
                                        </span>
                                    </div>
                                    <div class="form-group m-2 mt-2">
                                        <label for="inputPasswordNewVerify" class="titles">Verify</label>
                                        <input type="password" class="form-control" id="inputPasswordNewVerify" required="">
                                        <span class="form-text small text-muted fz-14">
                                            To confirm, type the new password again.
                                        </span>
                                    </div>
                                    <div class="form-group m-2 mt-4">
                                        <button type="submit" class="btn btn-outline-dark btn-lg float-right">Save</button>
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
<script src="./assets/js/renter/Renter-navbar.js"></script>

</body>
</html>