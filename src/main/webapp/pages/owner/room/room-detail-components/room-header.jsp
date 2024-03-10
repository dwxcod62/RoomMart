<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN"/>

<style>
    /* CSS cho nút khi ở trạng thái disabled */
    button[disabled] {
        pointer-events: none; /* Không cho phép sự kiện click */
        background-color: grey; /* Màu nền xám */

        opacity: 0.5; /* Độ mờ */
    }

    /* Không cho phép hover khi disabled */
    button[disabled]:hover {
        background-color: grey; /* Giữ màu nền xám */
        color: #666; /* Giữ màu chữ tương phản */
        cursor: default; /* Không hiển thị icon chuột */
    }
    #loading-overlay {
        display: none;
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(255, 255, 255, 0.8); /* Một lớp mờ */
        z-index: 9999; /* Đảm bảo nó hiển thị trên tất cả các phần tử khác */
    }

    #loading-overlay img {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }
</style>
<div class="room-header">
    <h2 class="room-name">Phòng ${sessionScope.room.roomNumber}</h2>

    <%--     --%>
    <div class="room-actions">
        <c:if test="${sessionScope.room.roomStatus == 1}">
            <!-- Start update room information button -->
            <button class="action-update-btn" data-bs-toggle="modal"
                    data-bs-target="#update-room-infor-modal">Cập nhật
            </button>

            <!-- Start update room modal -->
            <div class="modal fade" id="update-room-infor-modal" tabindex="-1"
                 aria-labelledby="update-room-infor-modal-label" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="update-room-infor-modal-label">
                                Cập nhật thông tin phòng
                            </h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div class="form-warning">
                            <p><span>*</span> Nếu cập nhật ảnh của phòng thì tất cả ảnh cũ sẽ bị xoá và thay bằng ảnh mới! </p>
                            <p><span>*</span> Nếu không muốn cập nhật ảnh thì không cần tải ảnh lên</p>
                        </div>
                        <div class="spacer"></div>
                        <!-- Form update room -->
                        <form action="UpdateRoom" method="POST" enctype="multipart/form-data"
                              id="update-room-infor-form"
                              class="custom-form update-room-infor-form">
                            <div class="modal-body">
                                <!-- Room number -->
                                <div class="form-group">
                                    <div class="row align-items-center">
                                        <div class="col-6">
                                            <label for="update-room-infor__room-number"
                                                   class="form-label">
                                                Số phòng
                                            </label>
                                        </div>
                                        <div class="col-6">
                                            <input id="room-name" type="number" name="room-name"
                                                   id="update-room-infor__room-number"
                                                   value="${sessionScope.room.roomNumber}"
                                                   class="form-control m-0">
                                        </div>
                                    </div>
                                    <span id="form-mess-roomNumber" class="form-message mt-3"></span>
                                </div>
                                <!-- Room fileImage -->
                                <div class="form-group">
                                    <div class="row align-items-center">
                                        <div class="col-6">
                                            <label for="update-room-infor__fileImage"
                                                   class="form-label">
                                                Ảnh phòng
                                            </label>
                                        </div>
                                        <div class="col-6">
                                            <input type="file" name="fileImage"
                                                   id="update-room-infor__fileImage"
                                                   value="${sessionScope.room.roomNumber}"
                                                   class="form-control m-0" multiple onchange="validateFiles(this)" accept=".png, .jpg">

<%--                                            <input id="room-img" type="file" name="fileImage" multiple class="form-control">--%>

                                        </div>
                                    </div>
                                    <span class="form-message mt-3"></span>
                                </div>
                                <!-- Room price -->
                                <div class="form-group">
                                    <div class="row align-items-center">
                                        <div class="col-6">
                                            <label for="update-room-infor__room-price"
                                                   class="form-label">
                                                Mức giá
                                            </label>
                                        </div>
                                        <div class="col-6">
                                            <input type="number" name="room-price"
                                                   id="update-room-infor__room-price"
                                                   value="${sessionScope.room.price}"
                                                   class="form-control m-0">
                                        </div>
                                    </div>
                                    <span class="form-message mt-3"></span>
                                </div>
                                <!-- Room capacity -->
                                <div class="form-group">
                                    <div class="row align-items-center">
                                        <div class="col-6">
                                            <label for="update-room-infor__room-capacity"
                                                   class="form-label">
                                                Số lượng thành viên tối đa
                                            </label>
                                        </div>
                                        <div class="col-6">
                                            <input type="number" name="room-capacity"
                                                   id="update-room-infor__room-capacity"
                                                   value="${sessionScope.room.capacity}"
                                                   class="form-control m-0">
                                        </div>
                                    </div>
                                    <span class="form-message mt-3"></span>
                                </div>
                                <!-- Room area -->
                                <div class="form-group">
                                    <div class="row align-items-center">
                                        <div class="col-6">
                                            <label for="update-room-infor__room-area"
                                                   class="form-label">
                                                Diện tích
                                            </label>
                                        </div>
                                        <div class="col-4">
                                            <input type="number" name="room-area"
                                                   id="update-room-infor__room-area"
                                                   value="${sessionScope.room.roomArea}"
                                                   class="form-control m-0">
                                        </div>
                                        <div class="col-2 text-center">
                                            m2
                                        </div>
                                    </div>
                                    <span class="form-message mt-3"></span>
                                </div>
                                <!-- Room attic -->
                                <div class="form-group">
                                    <div class="row align-items-center">
                                        <div class="col-6">
                                            <label for="update-room-infor__room-attic"
                                                   class="form-label">
                                                Gác
                                            </label>
                                        </div>
                                        <div class="col-4">
                                            <select name="room-attic"
                                                    id="update-room-infor__room-attic"
                                                    class="form-control m-0">
                                                <option value="1" ${sessionScope.room.hasAttic eq 1 ? "selected" : ""}>
                                                    Có
                                                </option>
                                                <option value="0" ${sessionScope.room.hasAttic eq 0 ? "selected" : ""}>
                                                    Không
                                                </option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer justify-content-between">
                                <button type="button" class="btn btn-danger"
                                        data-bs-dismiss="modal">Hủy bỏ
                                </button>
                                <input type="hidden" name="roomID"
                                       value="${sessionScope.room.roomId}">
                                <button id="submitBTN" type="submit" class="btn btn-primary">Cập nhật</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <!-- End update room modal -->
            <!-- End update room information button -->
        </c:if>
        <c:if test="${sessionScope.room.roomStatus == 1}">
            <!-- Start update room information button -->
            <button style="color: red; margin-right:8px " class="action-update-btn btn-danger" data-bs-toggle="modal"
                    data-bs-target="#delete-room-infor-modal">Xoá
            </button>

            <!-- Start update room modal -->
            <div class="modal fade" id="delete-room-infor-modal" tabindex="-1"
                 aria-labelledby="update-room-infor-modal-label" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="delete-room-infor-modal-label">
                                Xoá phòng
                            </h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <!-- Form update room -->
                        <form action="DeleteRoom" method="POST"
                              id="delete-room-infor-form"
                              class="custom-form update-room-infor-form">
                             <div class="modal-body">
                                 Bạn chắc chắn muốn xoá phòng này ?
                             </div>
                            <div class="modal-footer justify-content-between">
                                <button type="button" class="btn btn-danger"
                                        data-bs-dismiss="modal">Hủy bỏ
                                </button>
                                <input type="hidden" name="roomID"
                                       value="${sessionScope.room.roomId}">
                                <button type="submit" class="btn btn-primary">Xoá</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <!-- End update room modal -->
            <!-- End update room information button -->
        </c:if>

        <c:set var="consumeThisMonth" value="${requestScope.consumeListThisMonth}"/>
        <c:set var="consumeBeginMonth"
               value="${consumeThisMonth.get(consumeThisMonth.size() - 1)}"/>
        <c:set var="consumeEndMonth" value="${consumeThisMonth.get(0)}"/>

        <c:choose>
            <c:when test="${sessionScope.room.roomStatus eq 1}">
                <!-- Start create account button -->
                <a href="create-contract-page" class="action-create-account-link">Tạo hợp đồng</a>
                <!-- End create account button -->
            </c:when>
            <c:when test="${sessionScope.room.roomStatus eq 0}">
                <c:choose>
                    <c:when test="${consumeEndMonth.numberElectric - consumeBeginMonth.numberElectric ne 0 &&
                                    consumeEndMonth.numberWater - consumeBeginMonth.numberWater ne 0}">
                        <form action="calculateTotalCost" method="get">
                            <input type="hidden" name="roomID" value="${sessionScope.room.roomId}">
                            <button type="submit" class="action-calculate-btn">
                                Tính tiền phòng
                            </button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <button class="action-calculate-btn" data-bs-toggle="modal"
                                data-bs-target="#calculateRoomPriceModel" style="margin-right: 0;">Tính tiền phòng
                        </button>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:when test="${sessionScope.room.roomStatus eq -1}">
                <!-- Start view QR Code button -->

                <!-- End view QR Code button -->
            </c:when>
        </c:choose>
    </div>
</div>

<!-- Modal toggle warning update consume -->
<c:if test="${sessionScope.room.roomStatus eq 0 && consumeEndMonth.numberElectric - consumeBeginMonth.numberElectric eq 0 &&
              consumeEndMonth.numberWater - consumeBeginMonth.numberWater eq 0}">
    <div class="modal fade" id="calculateRoomPriceModel" tabindex="-1" aria-labelledby="updateServicesModelLabel"
         aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title updateServicesModel-label" id="updateServicesModelLabel">Cảnh báo</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body updateServicesModel-content mt-5 mb-5 text-danger">
                    Hệ thống nhận thấy bạn chưa cập nhật số điện và số nước của tháng này! Vui lòng nhấn nút "Cập nhật"
                    để cập nhật số điện và số nước trước. Sau đó thực hiện tính tiền phòng lại sau!
                </div>

                <div class="modal-footer updateServicesModel-action justify-content-between">
                    <button type="button" class="btn btn-secondary back-btn" data-bs-dismiss="modal">
                        Quay lại
                    </button>
                    <input id="hostID" type="hidden" name="hid" value="${sessionScope.hostel.hostelID}">

                    <button data-bs-toggle="modal"
                            data-bs-target="#update-consume-modal"
                            class="btn btn-primary continue-btn">
                        Cập nhật
                    </button>
                </div>
            </div>
        </div>
    </div>
</c:if>
<script>


    var hostelId = ${sessionScope.hostel.hostelID};
    var updateRoomNumber = ${sessionScope.room.roomNumber};
    console.log("roomNumber:"+ roomNumber)

    var submitBtn = document.getElementById("submitBTN");
    console.log(submitBtn.innerText);
    var fm = document.getElementById("form-mess-roomNumber");

    console.log("hostelid:"+hostelId);
    var roomNumber = document.getElementById("room-name");

        roomNumber.onblur = function(){
            console.log("Người dùng đã thoát khỏi trường nhập liệu");
            console.log("room enter: " + roomNumber.value);
            $.ajax({
                url: "check-room-exist",
                type: "GET",
                data: { roomNum: roomNumber.value, hostelID: hostelId,updateRoomNumber:updateRoomNumber },
                success: function (response) {
                    console.log("repsonse: "+response)
                    if (response === "true") {
                        submitBtn.setAttribute("disabled", "true");;
                        fm.innerText = "Số phòng đã tồn tại";
                    } else {

                        submitBtn.removeAttribute("disabled");
                        fm.innerText = "";
                    }
                },
                error: function (xhr, status, error) {
                    console.error(xhr, status, error);

                },
            });
        };



</script>
<script>
    function validateFiles(input) {
        console.log("validateFiles")
        const files = input.files;
        const allowedExtensions = /(\.png|\.jpg)$/i;
        const maxSize = 2097152; // 10 MB
        var totalSize = 0;


        for (let i = 0; i < files.length; i++) {
            const file = files[i];

            if (!allowedExtensions.exec(file.name)) {
                alert('Chỉ chấp nhận ảnh .png và .jpg.');
                input.value = '';
                return false;
            }

            if (file.size > maxSize) {
                alert('Kích thước file tối đa là 2 MB.');
                input.value = '';
                return false;
            }
        }
        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            totalSize += file.size
            console.log(i+": "+file.size);
        }
        console.log(totalSize)
        console.log(maxSize)
        if (totalSize > 2097152) {
            alert('Tổng Kích thước tất cả file tối đa là 2 MB.');
            input.value = '';
            return false;
        }
    }
</script>
<script>
    Validator({
        form: "#update-room-infor-form",
        formGroupSelector: ".form-group",
        errorSelector: ".form-message",
        rules: [
            Validator.isRequired("#update-room-infor__room-number"),
            Validator.isInteger("#update-room-infor__room-number"),
            Validator.minNumber(
                "#update-room-infor__room-number",
                1,
                `Vui lòng nhập số phòng tối thiểu là ${1}`
            ),
            Validator.isRequired("#update-room-infor__room-price"),
            Validator.isInteger("#update-room-infor__room-price"),
            Validator.minNumber(
                "#update-room-infor__room-price",
                1,
                `Vui lòng nhập số phòng tối thiểu là ${1}`
            ),
            Validator.isRequired("#update-room-infor__room-capacity"),
            Validator.isInteger("#update-room-infor__room-capacity"),
            Validator.minNumber(
                "#update-room-infor__room-capacity",
                1,
                `Vui lòng nhập số lượng tối thiểu là ${1}`
            ),
            Validator.isRequired("#update-room-infor__room-area"),
            Validator.minNumber(
                "#update-room-infor__room-area",
                1,
                `Vui lòng nhập diện tích tối thiểu là ${1}`
            ),
        ],
    });
</script>