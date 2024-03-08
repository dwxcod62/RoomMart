<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.codebrew.roommart.utils.EncodeUtils" %>
<%@ page import="com.codebrew.roommart.dao.RoomDao" %>

<%--
  Created by IntelliJ IDEA.
  User: Thanh
  Date: 08/03/2024
  Time: 11:29 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Popup Rooms</title>
    <!-- Bootstrap CSS -->
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
            rel="stylesheet"
    />
    <style>
        /* CSS để căn giữa popup */
        .modal {
            text-align: center;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .modal-dialog {
            text-align: center;
            display: flex;
            justify-content: center;
            align-items: center;
            max-width: 1000px;
        }
        .room-image {
            width: 100%;
            height: 150px; /* Điều chỉnh kích thước theo nhu cầu */
            object-fit: cover;
        }
        .card-body a {
            text-decoration: none !important; /* Loại bỏ gạch chân mặc định */
            color: black; /* Đổi màu chữ thành đen */
        }
        .card-body a:hover,
        .card-body a:focus {
            text-decoration: none; /* Loại bỏ gạch chân khi hover hoặc focus */
        }
    </style>
</head>
<body>
<!-- Modal -->
<div
        class="modal fade"
        id="exampleModalCenter"
        tabindex="-1"
        aria-labelledby="exampleModalCenterTitle"
        aria-hidden="true"
>
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalCenterTitle">
                    Đề xuất phòng dành cho riêng bạn
                </h5>

                <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                        onclick="closeModal()"
                ></button>
            </div>
            <div class="modal-body">
                <div
                        class="row row-cols-1 row-cols-md-3 g-4 justify-content-center"
                >
                    <c:if test="${sessionScope.mostView != null}">
                        <c:set var="mostView" value="${sessionScope.mostView}"/>
                        <!-- Room 1 -->
                        <div class="col">
                            <div class="card h-100">
                                <c:set var="encodedRoomIdmostView" value="${EncodeUtils.encodeString(mostView.roomId)}" />
                                <c:set var="encodedHostelIdmostView" value="${EncodeUtils.encodeString(mostView.hostelId)}" />
                                <a href="roomDetailH?hostelId=${encodedHostelIdmostView}&rid=${encodedRoomIdmostView}" class="text-decoration-none">
                                    <!-- Thêm lớp để loại bỏ gạch chân -->
                                    <img
                                            src="${mostView.imgUrl[0]}"
                                            class="card-img-top room-image"
                                            alt="Room 1"
                                    />

                                    <div class="card-body" style="color: black">
                                        <h5 class="card-title">Nhiều lượt xem</h5>
                                        <p class="card-text">
                                                ${mostView.roomView}
                                        </p>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${sessionScope.recently != null}">
                        <c:set var="recently" value="${sessionScope.recently}"/>
                        <!-- Room 2 -->
                        <div class="col">
                            <div class="card h-100">
                                <c:set var="encodedRoomIdrecently" value="${EncodeUtils.encodeString(recently.roomId)}" />
                                <c:set var="encodedHostelIdrecently" value="${EncodeUtils.encodeString(recently.hostelId)}" />
                                <a href="roomDetailH?hostelId=${encodedHostelIdrecently}&rid=${encodedRoomIdrecently}" class="text-decoration-none">
                                    <!-- Thêm lớp để loại bỏ gạch chân -->
                                    <img
                                            src="${recently.imgUrl[0]}"

                                            class="card-img-top room-image"
                                            alt="Room 2"
                                    />
                                    <div class="card-body" style="color: black">
                                        <h5 class="card-title">Đã xem gần đây</h5>
                                        <p class="card-text">

                                        </p>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${sessionScope.budget != null}">
                        <c:set var="budget" value="${sessionScope.budget}"/>
                        <!-- Room 3 -->
                        <div class="col">
                            <div class="card h-100">
                                <c:set var="encodedRoomIdbudget" value="${EncodeUtils.encodeString(budget.roomId)}" />
                                <c:set var="encodedHostelIdbudget" value="${EncodeUtils.encodeString(budget.hostelId)}" />
                                <a href="roomDetailH?hostelId=${encodedHostelIdbudget}&rid=${encodedRoomIdbudget}" class="text-decoration-none">
                                    <!-- Thêm lớp để loại bỏ gạch chân -->
                                    <img
                                            src="${budget.imgUrl[0]}"

                                            class="card-img-top room-image"
                                            alt="Room 3"
                                    />
                                    <div class="card-body" style="color: black">
                                        <h5 class="card-title">Hợp túi tiền</h5>
                                        <p class="card-text">
                                            ${sessionScope.budget.price}
                                        </p>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </c:if>

                </div>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Wait for the DOM to be fully loaded
    document.addEventListener("DOMContentLoaded", function () {
        // Show the modal
        var myModal = new bootstrap.Modal(
            document.getElementById("exampleModalCenter")
        );
        myModal.show();
    });
    function closeModal() {
        var modal = document.getElementById('exampleModalCenter');
        modal.classList.remove('show');
        modal.style.display = 'none';
        document.body.classList.remove('modal-open');
        var backdrop = document.getElementsByClassName('modal-backdrop')[0];
        backdrop.remove();
    }
</script>

</body>
</html>

