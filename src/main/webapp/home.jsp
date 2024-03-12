<%@ page import="com.codebrew.roommart.utils.EncodeUtils" %>
<%@ page import="com.codebrew.roommart.dao.RoomDao" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="vi_VN"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta
            name="viewport"
            content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <link rel="icon" href="assets/images/favicon.png" type="image/png" />
    <title>Royal Hotel</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="./assets/css/new_home_style/bootstrap.css" />
    <link rel="stylesheet" href="./assets/vendors/linericon/style.css" />
    <link rel="stylesheet" href="./assets/css/new_home_style/font-awesome.min.css" />
    <link rel="stylesheet" href="./assets/vendors/nice-select/css/nice-select.css" />
    <!-- main css -->
    <link rel="stylesheet" href="./assets/css/new_home_style/style.css" />
    <link rel="stylesheet" href="./assets/css/new_home_style/responsive.css" />
    <link rel="stylesheet" href="./assets/css/push_notification_style/style.css">

    <style>
        .image-container {
            width: 100%;
            max-width: 250px;
            height: 250px;
            overflow: hidden;
            position: relative;
        }

        .image-container img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        /* [ADD] them css text-limit */
        .text-limit {
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            max-width: 250px;
        }
        input[type="date"]::-webkit-calendar-picker-indicator {
            filter: invert(1) hue-rotate(180deg); /* Thay đổi màu sắc của biểu tượng */
            margin-right: 5px !important; /* Adjust the value as needed */
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
                        <a class="nav-link" href="home">Trang chủ</a>
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
                            >Tài khoản</a
                            >
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
                            >${sessionScope.USER.accountInfo.information.fullname}</a
                            >
                            <ul class="dropdown-menu">
                                <li class="nav-item">
                                    <a class="nav-link" href="profile">Xem trang cá nhân của bạn</a>
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

<!--================Banner Area =================-->
<section class="banner_area">
    <div class="booking_table d_flex align-items-center">
        <div
                class="overlay bg-parallax"
                data-stellar-ratio="0.9"
                data-stellar-vertical-offset="0"
                data-background=""
        ></div>
        <div class="container">
            <div class="banner_content text-center">
                <h6>Escape the Mundane with RoomMart</h6>
                <h2>Find Your Sanctuary</h2>
                <p>
                    Discover your perfect living space with RoomMart's seamless rental
                    and management services. Whether you seek urban elegance or
                    suburban tranquility, we've got you covered.
                </p>
            </div>
        </div>
    </div>
    <form action="search" method="get">
    <div class="hotel_booking_area position">
        <div class="container">
            <div class="hotel_booking_table">
                <div class="col-md-3">
                    <h2>
                        Get<br />
                        Your Room
                    </h2>
                </div>
                <div class="col-md-9">
                    <div class="boking_table">
                        <div class="row">

                            <!-- [ADD] Search -->
                            <div class="col-md-12">
                                <div class="book_tabel_item">
                                    <div class="input-group">
                                        <input value="${requestScope.key}" id="textInput" name="key" type="text" class="form-control" placeholder="Enter your search here..." />
                                    </div>
                                </div>
                            </div>
                            <!-- [ADD] Search -->
                            <div class="col-md-4">
                                <div class="book_tabel_item">


                                    <div class="input-group">
                                        <select id="city" name="city" class="wide">
                                            <option value="all" selected>Chọn tỉnh thành</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <div class="input-group date" id="datetimepicker1">
                                            <input title="Chọn ngày muốn thuê phòng" name="expiration" class="form-control" type="date">

<%--                                            <span class="input-group-addon">--%>
<%--                            <i class="fa fa-calendar" aria-hidden="true"></i>--%>
                          </span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="book_tabel_item">
                                    <div class="input-group">
                                        <select id="district" name="district" class="wide" >
                                            <option value="all" selected>Chọn quận huyện</option>
                                        </select>

                                    </div>
                                    <div class="input-group">
                                        <div onclick={showdropdown()} title="Chọn mức giá phòng mong muốn"  class="nice-select wide dropdown-toggle" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <span>Mức Giá</span>
                                            <jsp:include page="pages/home/drop-down-price.jsp"></jsp:include>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="book_tabel_item">
                                    <div class="input-group">
                                        <div class="input-group">
                                            <select id="ward" name="ward" class="wide" >
                                                <option value="all" selected>Chọn phường xã</option>
                                            </select>

                                        </div>
                                    </div>
                                    <button class="book_now_btn button_hover" type="submit">Tìm kiếm</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </form>
</section>
<!--================Banner Area =================-->

<!--================ Accomodation Area  =================-->
<section class="accomodation_area section_gap">
    <div class="container">
        <div class="section_title text-center"></div>
        <div class="row mb_30">

<c:if test="${not empty requestScope.rooms}">

    <c:forEach items="${requestScope.rooms}" var="r">
        <c:set var="address" value="${r.roomInformation.city} - ${r.roomInformation.district} - ${r.roomInformation.ward}" />
        <c:set var="hostelName" value="${r.roomInformation.hostelName}" />
        <div class="col-lg-3 col-sm-6">
            <div
                    class="accomodation_item text-center"
                    style="display: flex;
                flex-direction: column;
                align-items: flex-start;">
                <div class="hotel_img image-container">
                    <img src="${not empty r.imgUrl ? r.imgUrl[0] : 'https://media.licdn.com/dms/image/C5112AQEw1fXuabCTyQ/article-inline_image-shrink_1500_2232/0/1581099611064?e=1710374400&v=beta&t=LKfE3ie3occM50NiiYBq9mIgdJMjkeGnaiuREah4wEE'}" alt="" />
                </div>
                <c:set var="encodedRoomId" value="${EncodeUtils.encodeString(r.roomId)}" />
                <c:set var="encodedHostelId" value="${EncodeUtils.encodeString(r.hostelId)}" />

                <a href="roomDetailH?hostelId=${encodedHostelId}&rid=${encodedRoomId}"><h4 class="sec_h4">Phòng ${r.roomNumber} - ${hostelName}</h4></a>
                <h6>${r.price >= 1000000 ? r.price/1000000 : r.price} ${r.price >= 1000000 ? "Triệu":""}<small>/ tháng - </small> ${r.roomArea} m<sup>2</sup></h6>
                <h6 style="align-self: flex-start" class="text-limit">
                    ${address}
                </h6>

                <span>
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path><circle cx="12" cy="12" r="3"></circle></svg>

                        ${not empty r.roomView ? r.roomView : 0}</span>
                <c:set var="endDate" value="${RoomDao.get_end_date_by_RoomId(r.roomId).toString()}"></c:set>
                <c:set var="startDate" value="${RoomDao.get_start_date_by_RoomId(r.roomId).toString()}"></c:set>
                <c:set var="formattedEndDate" value="${endDate.substring(8, 10)}/${endDate.substring(5, 7)}/${endDate.substring(0, 4)}" />
                <c:set var="formattedStartDate" value="${startDate.substring(8, 10)}/${startDate.substring(5, 7)}/${startDate.substring(0, 4)}" />

                <c:if test="${r.roomStatus==1}">
                 <h6 style="color: green" >
                   Có thể thuê
                 </h6>
                </c:if>
                <c:if test="${r.roomStatus==0}">
                <h6>
                 Đã Thuê
                 <br>
                  Từ ${formattedStartDate} đến ${formattedEndDate}
                 </h6>

                </c:if>
                <c:if test="${r.roomStatus==-1}">
                 <h6 style="color:orange;">
                 Đang duyệt
                   <br>
                  Thuê từ ${formattedStartDate} đến ${formattedEndDate}
                 </h6>
                </c:if>

            </div>
        </div>
    </c:forEach>


</c:if>
<c:if test="${empty requestScope.rooms}">
    <h2>Không tìm thấy phòng phù hợp</h2>
</c:if>
</div>
    </div>
    <div class="container">
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">

                <c:forEach begin="1" end="${requestScope.total}" step="1" var="i">

                    <li class="page-item <c:if test="${i == requestScope.page}"> active </c:if> "  >
                        <a class="page-link" href="home?page=${i}&key=${requestScope.key}&city=${requestScope.citySelected}&dístrict=${requestScope.districtSelected}&ward=${requestScope.wardSelected}">
                                ${i}
                        </a>
                    </li>
                </c:forEach>

            </ul>
        </nav>
    </div>
</section>
<!--================ Accomodation Area  =================-->

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

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="./assets/js/jquery-3.2.1.min.js"></script>
<script src="./assets/js/bootstrap.min.js"></script>
<script src="./assets/vendors/owl-carousel/owl.carousel.min.js"></script>
<script src="./assets/js/jquery.ajaxchimp.min.js"></script>
<script src="./assets/vendors/nice-select/js/jquery.nice-select.js"></script>
<script src="./assets/js/stellar.js"></script>
<script src="./assets/vendors/lightbox/simpleLightbox.min.js"></script>
<script src="./assets/js/custom.js"></script>


<!-- food section -->
<%--<jsp:include page="pages/home/components/footer.jsp"></jsp:include>--%>
<!-- Push notification element -->
<jsp:include page="./pages/home/components/boxchat.jsp" />
<div id="push-noti"></div>
<c:if test="${sessionScope.mostView != null}">
    <jsp:include page="pages/home/popup.jsp"></jsp:include>
</c:if>

<!-- end food section -->
<%
    String citySelected = (String) request.getAttribute("citySelected");
    String districtSelected = (String) request.getAttribute("districtSelected");
    String wardSelected = (String) request.getAttribute("wardSelected");
%>
<%--<!--Script-->--%>



<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
<!-- JQuery -->
<script src="./assets/js/jquery-3.5.1.min.js" type="text/javascript"></script>
<!-- Link your script here -->
<script src="./assets/js/handle-main-navbar.js"></script>
<!-- Simple Datatable JS -->
<script src="./assets/js/jquery.dataTables.min.js" type="text/javascript"></script>

<!-- Push notification -->
<script src="./assets/js/push-notification-alert.js"></script>
<!-- Web socket -->
<script src="./assets/js/receiveWebsocket.js"></script>
<script src="./assets/js/loading-handler.js"></script>

<script src="./assets/js/sendWebsocket.js"></script>
<script>

    // sendToWebSocket("hostel_owner", "hostel_renter", null, 4, null," Chức năng thông báo");

</script>

<script type="text/javascript">
    // Receive
    receiveBoxChatWebsocket(showBoxChat);

    receiveWebsocket(alertPushNoti);


    // Close when leave
    window.onbeforeunload = function () {
        receiveWebsocket.disconnectWebSocket();
        receiveBoxChatWebsocket.disconnectWebSocket();
    };
</script>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>

<script>

<%--    Cai nay lay the ul--%>
    var citySelect = document.getElementById("city");
    var niceSelectUl = citySelect.nextElementSibling.querySelector("ul.list");
    niceSelectUl.style.maxHeight = "200px";
    niceSelectUl.style.overflowY = "auto";

    var districts = document.getElementById("district");
    var niceSelectUl2 = districts.nextElementSibling.querySelector("ul.list");
    niceSelectUl2.style.maxHeight = "200px";
    niceSelectUl2.style.overflowY = "auto";

    var wards = document.getElementById("ward");
var niceSelectUl3 = wards.nextElementSibling.querySelector("ul.list");
niceSelectUl3.style.maxHeight = "200px";
niceSelectUl3.style.overflowY = "auto";

    var Parameter = {
        url: "https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json",
        method: "GET",
    };
    var promise = axios(Parameter);

    promise.then(function (result) {
        renderCity(result.data);
    });

    function renderCity(data) {
        for (const x of data) {
            var li = document.createElement("li");
            li.setAttribute("data-value", x.Id);
            li.textContent = x.Name;
            li.classList.add("option");

            li.addEventListener("click", function () {
                for (const y of x.Districts){
                    var li_dis = document.createElement("li");
                    li_dis.setAttribute("data-value", y.Id);
                    li_dis.textContent = y.Name;
                    li_dis.classList.add("option");

                    li_dis.addEventListener("click", function () {
                        for (const z of y.Wards) {
                            var li_ward = document.createElement("li");
                            li_ward.setAttribute("data-value", z.Id);
                            li_ward.textContent = z.Name;
                            li_ward.classList.add("option");
                            niceSelectUl3.appendChild(li_ward)
                        }
                    });
                    niceSelectUl2.appendChild(li_dis);
                }
            });
            niceSelectUl.appendChild(li);
        }
    }
</script>

<script>

    document.addEventListener("DOMContentLoaded", function() {
        var loadingOverlay = document.getElementById('loading-overlay');


        // Bắt sự kiện load hoàn tất của trang và ẩn biểu tượng loading
        // window.addEventListener("load", function() {
        //     loadingOverlay.style.display = 'none';
        // });
        window.addEventListener('beforeunload', function() {
            loadingOverlay.style.display = 'block'; // Hiển thị biểu tượng loading khi bắt đầu chuyển trang
        });
    });

function showdropdown(){
    console.log("showdropdown");
    const dropdownElement = document.getElementById("dropdownPricev2");
    console.log(dropdownElement)
    if(dropdownElement.style.display == "block"){
        dropdownElement.style.display = "none";

    }else
    dropdownElement.style.display = "block";

}

</script>
<script>

</script>

</body>
</html>
