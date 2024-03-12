<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
    rel="stylesheet"
    integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
    crossorigin="anonymous"
/>

<link rel="stylesheet" href="./assets/css/system_style/confirm-room_style/style.css" />
<link rel="stylesheet" href="./assets/css/core_style/core.css" />

<style>
    .popup {
        display: none;
        position: fixed;
        z-index: 99999;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgba(0,0,0,0.4); /* Màu đen với độ mờ */
    }
    .form-control1 {
        font-size: 1.2rem;
        height: 40px;
        margin: 4px 0 16px 0;
        border-radius: 4px;
    }
    .popup .container {
        background-color: transparent;
    }
</style>


<div id="popup-contract" class="popup">
    <div class="container mt-5 pt-5 mb-5 pb-5 content" style="z-index: 9999;">
        <div class="row">
            <div class="col-12 col-lg-10 col-xl-8 col-xxl-7 m-auto content__wrapper">
                <div class="content__infor">
                    <h2 class="content__infor-title">Đăng kí thuê phòng</h2>
                    <p class="content__subheader">
                        Yêu cầu của bạn sẽ được gửi cho chủ phòng, chúng tôi sẽ gửi kết quả cho bạn sau khi chủ phòng duyệt/từ chối yêu cầu này
                    </p>
                    <div class="row">
                        <div class="col-12 col-md-6">
                            <p class="content__infor-item">Khu trọ: <span>Kaviex</span></p>
                            <p class="content__infor-item">Giá phòng: <span> 2,500,000 / Tháng</span></p>
                            <p class="content__infor-item">
                                Địa chỉ:
                                <span> 123 Lê Duẫn, Thanh Khê Tây, Thanh Khê, Tp. Đà Nẵng </span>
                            </p>
                            <p class="content__infor-item">Chủ trọ: <span>Nguyễn Anh Đức</span></p>
                            <p class="content__infor-item">Phòng số: <span>101</span></p>
                        </div>
                        <div class="col-12 col-sm-6">
                            <p class="content__infor-item">
                                Diện tích: <span>300 m<sup>2</sup> </span>
                            </p>
                            <p class="content__infor-item">Gác: <span>Có</span></p>
                            <p class="content__infor-item">Số lượng thành viên tối đa: <span>5</span></p>

                            <form action="TenantContractServlet" method="post" id="akshd" class="row">
                                <input type="hidden" name="room_id" value="${room.roomId}" />
                                <input type="hidden" name="id1" value="${param.hostelId}" />
                                <input type="hidden" name="id2" value="${param.rid}" />
                                <div class="col-12 col-md-6">
                                    <div class="row">
                                        <div class="col-12 d-flex align-items-center">
                                            <span style="font-weight: bold">Ngày bắt đầu thuê:</span>
                                        </div>
                                        <div class="col-12">
                                            <input type="date" class="form-control m-0" id="input_from" name="room-startdate" value="" required />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 col-md-6 mt-3 mt-md-0">
                                    <div class="row">
                                        <div class="col-12 d-flex align-items-center">
                                            <span style="font-weight: bold">Ngày kết thúc :</span>
                                        </div>
                                        <div class="col-12">
                                            <input type="date" class="form-control m-0" id="input_end" name="room-enddate" value="" required />
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="content__spacer"></div>
                <div class="row">
                    <div class="col-12 col-md-6 content__table">
                        <h2 class="content__infor-title">Phí dịch vụ hàng tháng</h2>
                        <table class="table table-bordered content__infor-table">
                            <thead>
                            <tr class="text-center">
                                <th>Tên</th>
                                <th>Giá</th>
                                <th>Đơn vị tính</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="s" items="${requestScope.serviceList}">
                                <tr class="text-center">
                                    <td>${s.serviceName}</td>
                                    <td>${s.servicePrice}</td>
                                    <td>${s.unit}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-12 col-md-6 content__table">
                        <h2 class="content__infor-title">Cơ sở vật chất</h2>
                        <table class="table table-bordered content__infor-table">
                            <thead>
                            <tr class="text-center">
                                <th>Tên</th>
                                <th>Trạng thái</th>
                            </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="s" items="${requestScope.infrasList}">
                                    <tr class="text-center">
                                        <td>${s.name}</td>
                                        <td>${s.status==1?"Tốt":"Đang Bảo Trì"}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="content__spacer"></div>
                <div class="form-group">
                    <div class="d-flex">
                        <input type="checkbox" id="content__form-confirm" class="content__form-confirm" name="action" value="continue" required />
                        <label for="content__form-confirm" class="content__form-label">Tôi đã đọc kỹ và xác nhận mọi thông tin trên đều đúng như đã thỏa thuận</label>
                    </div>
                    <span class="form-message"></span>
                </div>

                <div class="form-actions">
                    <button type="button" onclick="validateAndSubmit()" class="btn btn-primary fs-3">Tiếp tục</button>
                    <button type="button" onclick="closePopup()" class="btn btn-outline-danger fs-3">Hủy bỏ</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    function validateAndSubmit() {
        if (!document.getElementById("content__form-confirm").checked) {
            alert("Bạn phải đồng ý với các điều khoản.");
            return ;
        }

        if (document.getElementById("input_from").value === "" || document.getElementById("input_end").value === "") {
            alert("Vui lòng nhập đầy đủ ngày bắt đầu và ngày kết thúc.");
            return ;
        }

        var startDate = new Date(document.getElementById("input_from").value);
        var endDate = new Date(document.getElementById("input_end").value);
        var today = new Date();

        if (startDate <= today) {
            alert("Ngày bắt đầu thuê phải lớn hơn ngày hiện tại.");
            return ;
        }

        var threeMonthsLater = new Date(startDate);
        threeMonthsLater.setMonth(threeMonthsLater.getMonth() + 3);
        if (endDate < threeMonthsLater) {
            alert("Ngày kết thúc phải cách ngày bắt đầu ít nhất 3 tháng.");
            return ;
        }
        document.getElementById("akshd").submit();
    }
</script>


