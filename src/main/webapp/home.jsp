<%@ page import="com.codebrew.roommart.utils.EncodeUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="vi_VN"/>
<!DOCTYPE html>
<html lang="vi">
<head>

        <!-- Basic -->
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="icon" href="./assets/images/favicon/favicon.png" type="image/x-icon" />


        <title> ROOMMART </title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">


<%--    <link rel="stylesheet" href="assets/sys-css/staticfile.batdongsan.com.vn/css/web/filestatic.ver3a77c7a9.msvbds.layout.min.css" />--%>
    <link rel="stylesheet" href="assets/sys-css/staticfile.batdongsan.com.vn/css/web/support.css" />

    <link rel="stylesheet" href="assets/sys-css/staticfile.batdongsan.com.vn/css/web/filestatic.ver3a77c7a9.msvbds.home.min.css" />
    <link rel="stylesheet" href="assets/sys-css/staticfile.batdongsan.com.vn/css/web/filestatic.ver3a77c7a9.msvbds.card-compact.min.css">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <!-- Link Bootstrap !important -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <!-- Core CSS -->
    <link rel="stylesheet" href="./assets/css/core_style/core.css">

    <!-- Link your own CSS here -->
    <link rel="stylesheet" href="./assets/css/hostel_owner_style/dashboard/style.css">



    <!-- CSS Push Notification -->
    <link rel="stylesheet" href="./assets/css/push_notification_style/style.css">

</head>

<body class="re__body re__body-home over-flow-hidden">



<%--<div style="display: none" id="loading-overlay">--%>
<%--    <div class="dot"></div>--%>
<%--    <div class="dot"></div>--%>
<%--    <div class="dot"></div>--%>
<%--</div>--%>
<div id="preloader">
    <div class="dots">
        <div></div>
        <div></div>
        <div></div>
    </div>
</div>





<div class="form-content">

    <div class="re__bg-header">
<%--        <header class="re__full-menu re__header re__hover-menu re__tablet-menu  js__menu-bar">--%>
<%--            <div class="re__container-sm">--%>
<%--                <div class="re__nav" id="naga">--%>







<%--                </div>--%>
<%--                <div class="re__bg-pushmenu"></div>--%>
<%--            </div>--%>
<%--            <div class="re__menu-bar re__pushmenu re__pushmenu-right floating--right">--%>

<%--                <div class="re__control-menu">--%>
<%--                    <c:if test = "${sessionScope.USER != null}">--%>
<%--                        <!-- Navbar -->--%>
<%--                        <%@include file="./pages/owner/components/navbar.jsp"%>--%>
<%--                    </c:if>--%>
<%--                    <c:if test = "${sessionScope.USER == null}">--%>
<%--                        <div id="divUserStt" data-notification-library-url="https://static.batdongsan.com.vn/assets/bds-notification.js">--%>
<%--                            <a href="login" class="re__btn re__btn-se-ghost--md">Login</a>--%>
<%--                            <span class="re__line"></span>--%>
<%--                            <a href="register" class="re__btn  re__btn-se-ghost--md" rel="nofollow" >Register</a>--%>
<%--                        </div>--%>
<%--                    </c:if>--%>


<%--                </div>--%>
<%--                <!-- icon -->--%>
<%--                <div class="re__drop-menu">--%>
<%--                    <div class="re__left-menu">--%>
<%--                        <h1>--%>
<%--                            <a href="home" >--%>
<%--                                <img style="align-content: center; height: 60px; object-fit: contain ;" src="./assets/images/logos/logo.png" error-image-src="./assets/images/logos/logo.png" alt="Roomart" title="Roomart">--%>
<%--                            </a>--%>
<%--                        </h1>--%>
<%--                    </div>--%>

<%--                    <div class="re__right-menu">--%>
<%--                        <!--Header menu-->--%>
<%--                        <div class="re__home-header-menu">--%>
<%--                            <div class="re__home-header-menu">--%>
<%--                                <ul class="re__dropdown-no-art--sm re__dropdown-navigative-menu">--%>



<%--                                    <li class="lv0 ">--%>
<%--                                        <a href="home">--%>

<%--                                            <span class="text">Home</span>--%>
<%--                                        </a><div class="re__arrrow"></div>--%>

<%--                                    </li>--%>
<%--                                    <li class="lv0 ">--%>
<%--                                        <a href="https://github.com/dwxcod62/RoomMart">--%>

<%--                                            <span class="text">About us</span>--%>
<%--                                        </a><div class="re__arrrow"></div>--%>

<%--                                    </li>--%>
<%--                                    <li class="lv0 ">--%>
<%--                                        <a href="https://blogs.mtdv.me/blog/posts/very-mysterious">--%>

<%--                                            <span class="text">More</span>--%>
<%--                                        </a><div class="re__arrrow"></div>--%>

<%--                                    </li>--%>
<%--                                </ul>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            --%>
<%--            --%>
<%--        </header>--%>

    <!-- Navbar -->
    <%@include file="./pages/owner/components/navbar.jsp"%>
    </div>
</div>
<div class="re__main">
<div class="re__home">
        <!-- ok -->
        <div class="re__content-block re__home__head-block">
            <div class="re__home-search-box">
                <!-- form -->
                <form action="search" method="get" id="boxSearchForm" class="re__home-search-box js__home-search-box" data-home-search="true">
                    <div class="re__search-box-container">

                        <div class="re__search-box-content js__search-box-content">
                            <div class="re__input-group--sm re__search-box-row js__search-row-location">

                                <div class="re__search-location-select-header js_search-location-select-header" tabindex="0">
                                    <div class="re__search-location-row re__search-location-select-header-item js_search-location-select-header-item">
                                        <i class="js__selected-icon re__icon-search re__city-icon-search fa-solid fa-magnifying-glass" ></i>

                                        <input value="${requestScope.key}" type="text" id="textInput" name="key" title="Enter address follow pattern: province,(district),(ward)" class="w3-input w3-animate-input re__city-code-select js__listing-search-select-container js__city-code-select">

                                        <button type="submit" class="re__btn re__btn-pr-solid--sm re__btn-icon-left--sm re__btn-search" id="btnSearch">
                                            <span>Tìm kiếm</span>
                                        </button>
                                    </div>
                                    <div class="re__search-location-select-header-item-no-city js_search-location-select-header-item-no-city">
                                        <div class="re__header-item-no-city">
                                            <div class="re__city-search-select-header-title">Bạn muốn tìm Room tại tỉnh thành nào?</div>
                                            <a class="re__city-search-select-button-close js__listing-search-no-city-button-close">
                                                <i class="re__icon-close-no-circle no-city-button-close"></i>
                                            </a>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="home-filter home-filter-1">

                                <div class="search-filter" >

                                    <select id="city" name="city" class="select-text" style="color: #0a0d13">
                                        <option value="all" selected>Chọn tỉnh thành</option>
                                    </select>

                                </div>

                                <div class="search-filter">


                                    <select id="district" name="district" class="select-text" style="color: #0a0d13">
                                        <option value="all" selected>Chọn quận huyện</option>
                                    </select>

                                </div>

                                <div class="search-filter">

                                    <select id="ward" name="ward" class="select-text" style="color: #0a0d13">
                                        <option value="all" selected>Chọn phường xã</option>
                                    </select>


                                </div>


                            </div>
                        </div>
                    </div>
                </form>


            </div>
            <div class="re__home__head__banner-container re__banner-container re__banner-container-no-style js__gam__home__head__banner-container" style="height: 260px;">


            </div>
        </div>


        <div class="re__content-block re__home__bds-for-you-block re__bg-grey-50">
            <div class="re__content-container">


                <h2 class="re__content-container-label">For you </h2>


                <div id="interestedProductsBinnovaContent" class="re__product-container">

                    <!-- list img -->
                    <div class="home-product product-4-you">
                        <div class="js__interested-product re__interested-product-cards">
                            <c:if test="${not empty requestScope.rooms}">
                            <c:forEach items="${requestScope.rooms}" var="r">
                                <c:set var="address" value="${r.roomInformation.city} - ${r.roomInformation.district} - ${r.roomInformation.ward}" />
                                <c:set var="hostelName" value="${r.roomInformation.hostelName}" />

                                <div class="re__product-item re__interested-product-card js__product-item" style="display: block">

                                    <div class="js__card js__card-compact-web
     pr-container re__card-compact re__vip-normal">

                                        <c:set var="encodedRoomId" value="${EncodeUtils.encodeString(r.roomId)}" />
                                        <c:set var="encodedHostelId" value="${EncodeUtils.encodeString(r.hostelId)}" />
                                        <a class="js__product-link-for-product-id" href="roomDetail?hostelId=${encodedHostelId}&rid=${encodedRoomId}" title="Roommart" previewlistener="true">
                                            <div class="re__card-image">
                                                <img class="pr-img ls-is-cached lazyloaded" src="${not empty r.imgUrl ? r.imgUrl[0] : 'https://media.licdn.com/dms/image/C5112AQEw1fXuabCTyQ/article-inline_image-shrink_1500_2232/0/1581099611064?e=1710374400&v=beta&t=LKfE3ie3occM50NiiYBq9mIgdJMjkeGnaiuREah4wEE'}" alt="room Image">

                                                <div class="re__card-image-feature">
                                                    <i class="bi bi-image"></i>
                                                    <span>${not empty r.hostelId ? r.hostelId : 0}</span>
                                                </div>

                                            </div>
                                            <div class="re__card-info">
                                                <div class="re__card-info-content">
                                                    <div class="re__card-title">
                                                        <h3 class="js__card-title">
                                                                ${r.roomId} - ${hostelName} - ${r.roomNumber}
                                                        </h3>
                                                    </div>
                                                    <div class="re__card-config">
                                                        <span class="re__card-config-price">${r.capacity} Người</span>

                                                        <span class="re__card-config-area">
                                                        <span class="re__card-config-dot">·</span>
                                                    </span>

                                                        <span class="re__card-config-price"> ${r.roomArea}m<sup>2</sup></span>

                                                        <span class="re__card-config-area">
                                                        <span class="re__card-config-dot">·</span>
                                                    </span>

                                                        <span class="re__card-config-price">Gác xếp: ${r.hasAttic==1?'<i class="fa-solid fa-check"></i>':'<i class="fa-solid fa-xmark"></i>'}</span>

                                                        <div class="re__clear"></div>
                                                        <div class="re__clear"></div>
                                                    </div>
                                                    <div class="re__card-location">
                                                        <i class="bi bi-house-fill"></i>
                                                        <span>${address}</span>
                                                    </div>
                                                    <div class="re__clearfix"></div>
                                                    <div class="re__card-contact">
                                                        <div class="re__card-published-info">
                                <span class="re__card-published-info-published-at" data-microtip-position="right" role="tooltip">
                                        ${r.roomStatus==0?"Có thể thuê":r.roomStatus==2?"Đã Thuê":"Đang duyệt"}
                                </span>

                                                        </div>

                                                        <div class="re__clear-both"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="re__clearfix"></div>
                                        </a>
                                    </div>
                                </div>
                            </c:forEach>

                            </c:if>
                            <c:if test="${empty requestScope.rooms}">
                            <h2>Không tìm thấy phòng phù hợp</h2>
                            </c:if>

                        </div>

                    </div>

                </div>



            </div>

        </div>

    <%--                                pagination--%>
    <div class="pagination justify-content-center" style="display: flex;text-align: center;margin-top: 20px;margin-bottom: 20px;">
        <nav aria-label="Page navigation example">
            <ul class="pagination">
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
    <%--                                end pagination--%>
    </div>
</div>



    <!-- food section -->



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

<!-- Push notification -->
<script src="./assets/js/push-notification-alert.js"></script>
<!-- Web socket -->
<script src="./assets/js/receiveWebsocket.js"></script>
<script src="./assets/js/loading-handler.js"></script>


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>
<script>

    var citis = document.getElementById("city");
    var districts = document.getElementById("district");
    var wards = document.getElementById("ward");
    var Parameter = {
        url: "https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json",
        method: "GET",
        responseType: "application/json",
    };
    var promise = axios(Parameter);
    promise.then(function (result) {
        renderCity(result.data);
    });

    function renderCity(data) {
        for (const x of data) {
            var opt = document.createElement('option');
            opt.value = x.Name;
            opt.text = x.Name;
            opt.setAttribute('data-id', x.Id);
            citis.options.add(opt);
        }
        citis.onchange = function () {
            districts.length = 1;
            wards.length = 1;
            if(this.options[this.selectedIndex].dataset.id != ""){
                const result = data.filter(n => n.Id === this.options[this.selectedIndex].dataset.id);

                for (const k of result[0].Districts) {
                    var opt = document.createElement('option');
                    opt.value = k.Name;
                    opt.text = k.Name;
                    opt.setAttribute('data-id', k.Id);
                    districts.options.add(opt);
                }
            }
        };
        districts.onchange = function () {
            wards.length = 1;
            const dataCity = data.filter((n) => n.Id === citis.options[citis.selectedIndex].dataset.id);
            if (this.options[this.selectedIndex].dataset.id != "") {
                const dataWards = dataCity[0].Districts.filter(n => n.Id === this.options[this.selectedIndex].dataset.id)[0].Wards;

                for (const w of dataWards) {
                    var opt = document.createElement('option');
                    opt.value = w.Name;
                    opt.text = w.Name;
                    opt.setAttribute('data-id', w.Id);
                    wards.options.add(opt);
                }
            }
        };
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




</script>
<!--Script-->
</body>
</html>
