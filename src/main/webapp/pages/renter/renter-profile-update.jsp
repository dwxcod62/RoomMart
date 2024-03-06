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
                        <form action="RenterProfileUpdate" method="post" style="padding: 0 40px" id="updateForm">
                            <div class="form-group">
                                <label for="fullname">Họ và tên:</label>
                                <input type="text" id="fullname" name="new-name" class="form-control"
                                       value="${sessionScope.USER.accountInfo.information.fullname}" required>
                            </div>
                            <div class="form-group">
                                <label for="email">Email:</label>
                                <input type="email" id="email" name="new-email" class="form-control"
                                       value="${sessionScope.USER.accountInfo.information.email}" readonly>
                            </div>
                            <div class="form-group">
                                <label for="birthday">Ngày sinh:</label>
                                <input type="date" id="birthday" name="new-birthday" class="form-control"
                                       value="${sessionScope.USER.accountInfo.information.birthday}">
                            </div>
                            <div class="form-group">
                                <label for="sex" class="form-label">Giới tính:</label>
                                <div class="input-group">
                                    <select id="sex" name="new-sex" class="form-select">
                                        <option value="0" ${sessionScope.USER.accountInfo.information.sex == 0 ? 'selected' : ''}>Nam</option>
                                        <option value="1" ${sessionScope.USER.accountInfo.information.sex == 1 ? 'selected' : ''}>Nữ</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="phone">Số điện thoại:</label>
                                <input type="tel" id="phone" name="new-phone" class="form-control"
                                       value="${sessionScope.USER.accountInfo.information.phone}" required>
                            </div>
                            <div class="form-group">
                                <label for="cccd">Căn cước công dân:</label>
                                <input type="text" id="cccd" name="new-cccd" class="form-control"
                                       value="${sessionScope.USER.accountInfo.information.cccd}">
                            </div>
                            <button type="submit" class="btn btn-primary btn_save" onclick="confirmUpdate()">Lưu</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="component/footer.jsp" %>
<script src="./assets/js/renter/Renter-navbar.js"></script>

<script>
    function confirmUpdate() {
        // Kiểm tra giá trị của các ô input
        var fullname = document.getElementById("fullname").value;
        var phone = document.getElementById("phone").value;

        if (fullname === "" || phone === "") {
            alert("Vui lòng điền đầy đủ thông tin.");
            return false;
        }

        if (confirm("Bạn có muốn lưu thông tin mới không?")) {
            document.getElementById("updateForm").submit();
        } else {
        }
    }
</script>
</body>
</html>