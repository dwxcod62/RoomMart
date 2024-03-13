
<%@ page import="com.codebrew.roommart.utils.EncodeUtils" %>
<%@ page import="com.codebrew.roommart.dao.RoomDao" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="vi_VN"/>

<!DOCTYPE html>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8" />
    <meta
            name="viewport"
            content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <link rel="icon" href="./assets/images/favicon.png" type="image/png" />
    <title>Room Mart</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="./assets/css/new_home_style/bootstrap.css" />
    <link rel="stylesheet" href="./assets/vendors/linericon/style.css" />
    <link rel="stylesheet" href="./assets/css/new_home_style/font-awesome.min.css" />
    <link rel="stylesheet" href="./assets/vendors/owl-carousel/owl.carousel.min.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <!-- main css -->
    <link rel="stylesheet" href="./assets/css/new_home_style/style.css" />
    <!-- CSS Push Notification -->
    <link rel="stylesheet" href="./assets/css/push_notification_style/style.css">
    <link rel="stylesheet" href="./assets/css/new_home_style/responsive.css" />


    <!-- [ADD]  thêm style -->
    <style>
        .image-container {
            width: 100%;
            max-width: 750px;
            height: 500px;
            overflow: hidden;
            position: relative;
        }

        .image-container img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

         .form-control1 {
             font-size: 1.2rem;
             height: 40px;
             margin: 4px 0 16px 0;
             border-radius: 4px;
         }
    </style>
</head>

<body class="over-flow-hidden">


<div id="preloader">
    <div class="dots">
        <div></div>
        <div></div>
        <div></div>
    </div>
</div>

<c:if test="${not empty requestScope.room}" >

    <!--================Header Area =================-->
    <header class="header_area">
        <div class="container">
            <nav class="navbar navbar-expand-lg navbar-light">
                <!-- Brand and toggle get grouped for better mobile display -->
                <a class="navbar-brand logo_h" href="home"
                ><img style="height: 70px" src="./assets/images/as.png" alt=""
                /></a>
                <button
                        class="navbar-toggler"
                        type="button"
                        data-toggle="collapse"
                        data-target="#navbarSupportedContent"
                        aria-controls="navbarSupportedContent"
                        aria-expanded="false"
                        aria-label="Toggle navigation"
                >
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <!-- Collect the nav links, forms, and other content for toggling -->
                <div
                        class="collapse navbar-collapse offset"
                        id="navbarSupportedContent"
                >
                    <ul class="nav navbar-nav menu_nav ml-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="index.html">Home</a>
                        </li>

                        <li class="nav-item submenu dropdown">
                            <c:if test = "${sessionScope.USER == null}">
                                <a
                                        href="profile"
                                        class="nav-link dropdown-toggle"
                                        data-toggle="dropdown"
                                        role="button"
                                        aria-haspopup="true"
                                        aria-expanded="false"
                                >Tài khoản</a>
                                <ul class="dropdown-menu">
                                    <li class="nav-item">
                                        <a class="nav-link" href="login-page">Đăng nhập</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="register-page">Đăng kí</a>
                                    </li>
                                </ul>
                            </c:if>
                            <c:if test = "${sessionScope.USER != null}">
                                <a
                                        href="profile"
                                        class="nav-link dropdown-toggle"
                                        data-toggle="dropdown"
                                        role="button"
                                        aria-haspopup="true"
                                        aria-expanded="false"
                                >${sessionScope.USER.accountInfo.information.fullname}</a>
                                <ul class="dropdown-menu">
                                    <li class="nav-item">
                                        <a class="nav-link" href="profile">Thông tin của bạn</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="GetContractUserServlet">Xem lịch sử đăng kí</a>
                                    </li>
                                    <c:if test="${sessionScope.st != 0}">
                                        <li class="nav-item">
                                            <a class="nav-link" href="RenterHome">Xem Phòng</a>
                                        </li>
                                    </c:if>

                                    <li class="nav-item">
                                        <a class="nav-link" href="home">Tìm Phòng</a>
                                    </li>
                                    <c:if test="${sessionScope.USER.role eq 1}">
                                        <li class="nav-item">
                                            <a class="nav-link" href="dashboard">Đăng kí</a>
                                        </li>
                                    </c:if>

                                    <li class="nav-item">
                                        <a class="nav-link" href="logout">Đăng xuất</a>
                                    </li>
                                </ul>
                            </c:if>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    </header>
    <!--================Header Area =================-->

    <!--================Breadcrumb Area =================-->
    <section class="breadcrumb_area blog_banner_two">
        <div
                class="overlay bg-parallax"
                data-stellar-ratio="0.8"
                data-stellar-vertical-offset="0"
                data-background=""
        ></div>
    </section>
    <!--================Breadcrumb Area =================-->

    <!--================Blog Area =================-->
    <section class="blog_area single-post-area">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 posts-list">
                    <div class="single-post row">
                        <div
                                id="carouselExampleControls"
                                class="carousel slide"
                                data-ride="carousel"
                        >
                            <div class="carousel-inner">
                                <div class="carousel-item active image-container">
                                    <img
                                            src="${requestScope.roomImg[0]}"
                                            class="d-block w-100"
                                            alt="..."
                                    />
                                </div>
                                <c:if test="${requestScope.roomImg.size() > 1}">
                                    <c:forEach var="image" items="${requestScope.roomImg}" begin="1" end="${requestScope.roomImg.size()-1}">
                                        <div class="carousel-item image-container">
                                            <img
                                                    src="${image}"
                                                    class="d-block w-100"
                                                    alt="..."
                                            />
                                        </div>
                                    </c:forEach>
                                </c:if>


                            </div>
                            <a
                                    class="carousel-control-prev"
                                    href="#carouselExampleControls"
                                    role="button"
                                    data-slide="prev"
                            >
                  <span
                          class="carousel-control-prev-icon"
                          aria-hidden="true"
                  ></span>
                                <span class="sr-only">Previous</span>
                            </a>
                            <a
                                    class="carousel-control-next"
                                    href="#carouselExampleControls"
                                    role="button"
                                    data-slide="next"
                            >
                  <span
                          class="carousel-control-next-icon"
                          aria-hidden="true"
                  ></span>
                                <span class="sr-only">Next</span>
                            </a>
                        </div>

                        <div class="col-lg-3 col-md-3">
                            <div class="blog_info text-right">
                                <ul class="blog_meta list_style">
<%--                                    <li>--%>
<%--                                        <a href="#"--%>
<%--                                        >12 Dec, 2017<i class="lnr lnr-calendar-full"></i--%>
<%--                                        ></a>--%>
<%--                                    </li>--%>
                                    <li>
                                        <a href="#">${room.roomView} Views <i class="lnr lnr-eye"></i></a>
                                    </li>
                                    <li></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-lg-9 col-md-9 blog_details">
                            <h2>Phòng ${room.roomNumber} - ${room.roomInformation.hostelName}</h2>
                            <p class="excert">
                                    ${room.roomInformation.address}, ${room.roomInformation.ward}, ${room.roomInformation.district}, ${room.roomInformation.city}
                            </p>
                        </div>
                        <div class="col-lg-12">
                            <div class="quotes" style="font-style: normal">
                                <div class="row">
                                    <div class="col">
                                        <h4>Mức giá</h4>
                                        <h4 class="text-info">${room.price >= 1000000 ? room.price/1000000 : room.price} ${room.price >= 1000000 ? "Triệu":""} VNĐ</h4>
                                    </div>
                                    <div class="col">
                                        <h4>Diện tích</h4>
                                        <h4 class="text-info">${room.roomArea} m<sup>2</sup></h4>
                                    </div>
                                    <div class="col">
                                        <h4>Phòng</h4>
                                        <h4 class="text-info">${room.capacity} phòng</h4>
                                    </div>
                                    <c:set var="endDate" value="${RoomDao.get_end_date_by_RoomId(room.roomId).toString()}"></c:set>
                                    <c:set var="startDate" value="${RoomDao.get_start_date_by_RoomId(room.roomId).toString()}"></c:set>
                                    <c:set var="formattedEndDate" value="${endDate.substring(8, 10)}/${endDate.substring(5, 7)}/${endDate.substring(0, 4)}" />
                                    <c:set var="formattedStartDate" value="${startDate.substring(8, 10)}/${startDate.substring(5, 7)}/${startDate.substring(0, 4)}" />

                                    <div class="col">
                                        <h4>Tình trạng</h4>
                                        <c:choose>
                                            <c:when test="${room.roomStatus==0}">
                                         <h4 class="text-danger">
                                            Đã Thuê
                                        <br>
                                        (${formattedStartDate} - ${formattedEndDate})
                                        </h4>

                                            </c:when>
                                            <c:when test="${room.roomStatus==-1}">
                                        <h4 style="color: yellow">
                                             Đang duyệt
                                              <br>
                                             Thuê từ ${formattedStartDate} đến ${formattedEndDate}
                                        </h4>

                                            </c:when>
                                            <c:when test="${room.roomStatus==1}">
                                                <h4 class="text-success">Có thể thuê</h4>
                                            </c:when>
                                        </c:choose>


                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <h3>Thông tin mô tả</h3>
                                <div class="">
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th scope="col">Loại dịch vụ</th>
                                            <th scope="col">Giá trị</th>
                                            <th scope="col">Đơn vị</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="s" items="${requestScope.serviceList}">
                                            <tr>

                                                <td>${s.serviceName}</td>
                                                <td>${s.servicePrice}</td>
                                                <td>${s.unit}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="">
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th scope="col">Loại cơ sở hạ tầng</th>
                                            <th scope="col">Số lượng</th>
                                            <th scope="col">Tình trạng</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:if test="${requestScope.infrasListItem ne null}">
                                            <c:forEach var="s" items="${requestScope.infrasListItem}">
                                                <tr>
                                                    <td>${s.infrastructureName}</td>
                                                    <td>1</td>
                                                    <td>Đang bảo trì</td>
                                                </tr>
                                            </c:forEach>
                                        </c:if>
                                        <c:forEach var="s" items="${requestScope.infrasList}">
                                            <tr>
                                                <td>${s.name}</td>
                                                <td>${s.quantity}</td>
                                                <td>${s.status==1?"Tốt":"Đang Bảo Trì"}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="row">
                                <div class="container">
                                    <div
                                            class="re__section re__pr-specs re__pr-specs-v1 js__section js__li-specs"
                                    >
                                        <h3 class="re__section-title">Đặc điểm bất động sản</h3>
                                        <div
                                                class="re__section-body re__border--std js__section-body"
                                        >
                                            <div class="re__pr-specs-content js__other-info">
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <table class="table table-striped">
                                                            <tbody>
                                                            <tr>
                                                                <th>
                                                                    <i class="bi bi-bounding-box-circles"></i>
                                                                    Diện tích
                                                                </th>
                                                                <td>${room.roomArea} m²</td>
                                                            </tr>
                                                            <tr>
                                                                <th><i class="bi bi-cash"></i> Mức giá</th>
                                                                <td>${room.price >= 1000000 ? room.price/1000000 : room.price} ${room.price >= 1000000 ? "Triệu":""} VNĐ</td>
                                                            </tr>
                                                            <tr>
                                                                <th>
                                                                    <i class="bi bi-border-all"></i> Số phòng
                                                                    ngủ
                                                                </th>
                                                                <td>${room.capacity} phòng</td>
                                                            </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <table class="table table-striped">
                                                            <tbody>
                                                            <tr>
                                                                <th>
                                                                    <i class="bi bi-chevron-up"></i> Gác xếp
                                                                </th>
                                                                <td>
                                                                        ${room.hasAttic==1?'<i class="fa-solid fa-check" style="font-family:FontAwesome !important;"></i>':'<i class="fa-solid fa-xmark" style="font-family:FontAwesome !important;"></i>'}
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th><i class="bi bi-tv"></i> Nội thất</th>
                                                                <td>Liên hệ</td>
                                                            </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="blog_right_sidebar">
                        <aside class="single_sidebar_widget author_widget">
                            <img
                                    class="author_img rounded-circle"
                                    src="https://i.pinimg.com/564x/41/a4/6c/41a46cdf8e3e0b67360f0905c56f3347.jpg"
                                    style="width: 100%; height: 100%; max-height: 100px; max-width: 100px; object-fit: cover; overflow: hidden"
                                    alt=""
                            />
                            <h4>Loi chua fix</h4>
                            <div class="social_icon">
                                <a href="#"><i class="fa fa-facebook"></i></a>
                            </div>
                        </aside>

                        <aside class="single-sidebar-widget newsletter_widget">
                            <div class="mt-3">
                                <jsp:useBean id="ContractDao" class="com.codebrew.roommart.dao.ContractDao" scope="application" />
                                <c:set var="count_contract" value="${ContractDao.countResgiterContractByRenterId(requestScope.USER.getAccId())}" />
                                <c:choose>
                                    <c:when test="${sessionScope.USER != null}">
                                        <c:choose>
                                            <c:when test="${count_contract > 3}">
                                                <button
                                                    type="button"
                                                    class="btn btn-outline-primary btn-block mb-3 btn-custom"
                                                    style="font-size: 20px"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#delete-room-infor-modal"
                                                >
                                                    Đăng kí thuê phòng
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                <button
                                                    type="button"
                                                    class="btn btn-outline-primary btn-block mb-3 btn-custom"
                                                    style="font-size: 20px"
                                                    onclick="openPopup()"
                                                >
                                                    Đăng kí thuê phòng
                                                </button>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <button
                                            type="button"
                                            class="btn btn-outline-primary btn-block mb-3 btn-custom"
                                            style="font-size: 20px"
                                            onclick="openLoginPopup()"
                                        >
                                            Đăng nhập để thuê phòng
                                        </button>
                                    </c:otherwise>
                                </c:choose>
<%--                                <button--%>
<%--                                        type="button"--%>
<%--                                        class="btn btn-outline-success btn-block btn-custom"--%>
<%--                                        style="font-size: 20px"--%>
<%--                                >--%>
<%--                                    Đề xuất giá phòng--%>
<%--                                </button>--%>
                            </div>

                            <div class="modal fade" id="delete-room-infor-modal" tabindex="-1"
                                 aria-labelledby="update-room-infor-modal-label" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="delete-room-infor-modal-label">
                                                Đăng kí thuê phòng
                                            </h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                        </div>
                                        <!-- Form update room -->
                                            <div class="modal-body">
                                                Bạn chỉ được đăng kí tối đa 3 đơn
                                            </div>
                                            <div class="modal-footer justify-content-between">
                                                <button type="button" class="btn btn-danger"
                                                        data-bs-dismiss="modal">Quay lại
                                                </button>
                                            </div>
                                    </div>
                                </div>
                            </div>
                            <div class="br"></div>
                        </aside>

                        <aside class="single_sidebar_widget ads_widget">
                            <a href="#"
                            ><img
                                    class="img-fluid"
                                    src="https://brandcom.vn/wp-content/uploads/2020/09/quang-cao-truyen-hinh-1-1080x675.jpg"
                                    alt=""
                            /></a>
                            <div class="br"></div>
                        </aside>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!--================Blog Area =================-->

    <%@include file="./components/pop-up-contract.jsp"%>
    <%@include file="./components/login-popup.jsp"%>

    <!--================ start footer Area  =================-->
    <footer class="footer-area" style="padding: 0px">
        <div class="container">
            <div
                    class="row footer-bottom d-flex justify-content-between align-items-center"
            >
                <p class="col-lg-8 col-sm-12 footer-text m-0">
                    Copyright &copy;
                    <script>
                        document.write(new Date().getFullYear());
                    </script>
                    All rights reserved | This template is made with
                    <i class="fa fa-heart-o" aria-hidden="true"></i> by
                    <a href="https://colorlib.com" target="_blank">Colorlib</a>
                </p>
                <div class="col-lg-4 col-sm-12 footer-social">
                    <a href="#"><i class="fa fa-facebook"></i></a>
                    <a href="#"><i class="fa fa-twitter"></i></a>
                </div>
            </div>
        </div>
    </footer>
    <!--================ End footer Area  =================-->

    <div id="toast">&nbsp;</div>
    <script src="./assets/js/toast-alert.js"></script>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="./assets/js/jquery-3.2.1.min.js"></script>
    <script src="./assets/js/popper.js"></script>
    <script src="./assets/js/bootstrap.min.js"></script>
    <script src="./assets/js/jquery.ajaxchimp.min.js"></script>
    <script src="./assets/js/stellar.js"></script>
    <script src="./assets/vendors/lightbox/simpleLightbox.min.js"></script>
    <script src="./assets/js/custom.js"></script>

    <script
            src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
            integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
            crossorigin="anonymous"
    ></script>
    <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
            integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
            crossorigin="anonymous"
    ></script>


<%--        <%@include file="../home/components/footer.jsp"%>--%>
        <!-- Push notification element -->
        <div id="push-noti"></div>
        <!-- end product-detail-->
            <%--    <script src="assets/js/chat/chat.js"></script>--%>

</c:if>
<c:if test="${empty requestScope.room}" >
    <div style="text-align: center;" class="re__ldp re__main-content-layout js__main-container">
        <img  src="https://res.cloudinary.com/dqp6vdayn/image/upload/v1707647165/What-is-a-404-error-code_lu1xgy.png" style="display: block; margin: auto;">


    </div>
</c:if>





<!-- Push notification -->
<script src="./assets/js/push-notification-alert.js"></script>
<script src="./assets/js/receiveWebsocket.js"></script>
<script src="./assets/js/sendWebsocket.js"></script>


<script>

    // sendToWebSocket("hostel_owner", "hostel_renter", null, 22, null,"Chức năng thông báo");


</script>



<script type="text/javascript">
    // Receive
    receiveWebsocket(alertPushNoti);

    // Close when leave
    window.onbeforeunload = function(){
        receiveWebsocket.disconnectWebSocket();
    };
</script>
<!-- Loader -->
<script src="assets/js/loading-handler.js"></script>
<script>
    document.addEventListener("click", function(event) {
        console.log("click"); // Log "click" to the console
        var navDropdown = document.getElementById("nav-profile-dropdown"); // Get the dropdown menu element
        navDropdown.style.textDecoration = "black";
        var button = document.getElementById("nav-profile-btn"); // Get the button element
        console.log("event target:", event.target);
        var target = event.target;
        var parent = target.parentNode.parentNode; // Get the parent element of the target
        var parent2 = parent.parentNode;
        console.log("Parent element:", parent);
        console.log(button);

        // Check if the clicked element is the button
        if (parent === button || parent2 == button) {
            console.log("click button"); // Log "click button" to the console
            // Toggle the display of the dropdown menu
            if (navDropdown.style.display == "block") {
                navDropdown.style.display = "none"; // If currently displayed, hide the dropdown menu
            } else {
                console.log("block")
                navDropdown.style.display = "block"; // If hidden, display the dropdown menu
            }
        }

        // Check if the clicked element is neither the button nor the dropdown menu
        if (parent !== button) {
            navDropdown.style.display = "none"; // Hide the dropdown menu
        }
    });

    function openPopup() {
        document.getElementById("popup-contract").style.display = "block";
        document.body.style.overflow = "hidden"; //
    }

    function openLoginPopup() {
        document.getElementById("popup-login").style.display = "block";
        document.body.style.overflow = "hidden"; //
    }

    function closePopup() {
        document.getElementById("popup-contract").style.display = "none";
        document.body.style.overflow = "auto"; //
    }
</script>

<script>
    <c:choose>
    <c:when test="${requestScope.RESPONSE_MSG1.status eq true}">
    toast({
        title: 'Thành công',
        message: '${requestScope.RESPONSE_MSG1.content}',
        type: 'success',
        duration: 5000
    });
    </c:when>
    <c:when test="${requestScope.RESPONSE_MSG1.status eq false}">
    toast({
        title: 'Lỗi',
        message: '${requestScope.RESPONSE_MSG1.content}',
        type: 'error',
        duration: 5000
    });
    </c:when>
    </c:choose>
</script>


</body>

</html>