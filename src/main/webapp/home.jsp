<%@ page import="com.codebrew.roommart.utils.EncodeUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

        <!-- Basic -->
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <!-- Mobile Metas -->
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <!-- Site Metas -->
        <meta name="keywords" content="" />
        <meta name="description" content="" />
        <meta name="author" content="" />


        <title> ROOMMART </title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">


    <link rel="stylesheet" href="assets/sys-css/staticfile.batdongsan.com.vn/css/web/filestatic.ver3a77c7a9.msvbds.layout.min.css" />
    <link rel="stylesheet" href="assets/sys-css/staticfile.batdongsan.com.vn/css/web/filestatic.ver3a77c7a9.msvbds.home.min.css" />
    <link rel="stylesheet" href="assets/sys-css/staticfile.batdongsan.com.vn/css/web/filestatic.ver3a77c7a9.msvbds.card-compact.min.css">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

</head>
<style>
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
<body class="re__body re__body-home ${requestScope.RESPONSE_MSG eq null ? "over-flow-hidden" : ""}">



<div id="loading-overlay">
    <div class="dot"></div>
    <div class="dot"></div>
    <div class="dot"></div>
</div>
<div id="preloader">
    <div class="dots">
        <div></div>
        <div></div>
        <div></div>
    </div>
</div>





<div class="form-content">

    <div class="re__bg-header">
        <header class="re__full-menu re__header re__hover-menu re__tablet-menu  js__menu-bar">
            <div class="re__container-sm">
                <div class="re__nav" id="naga">







                </div>
                <div class="re__bg-pushmenu"></div>
            </div>
            <div class="re__menu-bar re__pushmenu re__pushmenu-right floating--right">

                <div class="re__control-menu">
                    <c:if test = "${sessionScope.USER != null}">
                        <div id="divUserStt" data-notification-library-url="https://static.batdongsan.com.vn/assets/bds-notification.js">
                            <a href="#AccInformation" class="re__btn re__btn-se-ghost--md">
                                    ${sessionScope.USER.email.split("@")[0]}
                            </a>
                            <span class="re__line"></span>
                            <a href="logout" class="re__btn  re__btn-se-ghost--md" rel="nofollow" >logout</a>
                        </div>
                    </c:if>
                    <c:if test = "${sessionScope.USER == null}">
                        <div id="divUserStt" data-notification-library-url="https://static.batdongsan.com.vn/assets/bds-notification.js">
                            <a href="login" class="re__btn re__btn-se-ghost--md">Login</a>
                            <span class="re__line"></span>
                            <a href="register" class="re__btn  re__btn-se-ghost--md" rel="nofollow" >Register</a>
                        </div>
                    </c:if>


                </div>
                <!-- icon -->
                <div class="re__drop-menu">
                    <div class="re__left-menu">
                        <h1>
                            <a href="home" >
                                <img style="align-content: center; height: 60px; object-fit: contain ;" src="./assets/images/logos/logo.png" error-image-src="./assets/images/logos/logo.png" alt="Roomart" title="Roomart">
                            </a>
                        </h1>
                    </div>

                    <div class="re__right-menu">
                        <!--Header menu-->
                        <div class="re__home-header-menu">
                            <div class="re__home-header-menu">
                                <ul class="re__dropdown-no-art--sm re__dropdown-navigative-menu">



                                    <li class="lv0 ">
                                        <a href="home">

                                            <span class="text">Home</span>
                                        </a><div class="re__arrrow"></div>

                                    </li>
                                    <li class="lv0 ">
                                        <a href="home">

                                            <span class="text">About us</span>
                                        </a><div class="re__arrrow"></div>

                                    </li>
                                    <li class="lv0 ">
                                        <a href="home">

                                            <span class="text">More</span>
                                        </a><div class="re__arrrow"></div>

                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </header>
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
                                        <i class="js__selected-icon re__icon-search re__city-icon-search"></i>

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
                                        <a class="js__product-link-for-product-id" href="roomDetail?rid=${encodedRoomId}" title="Roommart" previewlistener="true">
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
                                                        <span class="re__card-config-price">${r.capacity} Peoples</span>

                                                        <span class="re__card-config-area">
                                                        <span class="re__card-config-dot">·</span>
                                                    </span>

                                                        <span class="re__card-config-price"> ${r.roomArea}m<sup>2</sup></span>

                                                        <span class="re__card-config-area">
                                                        <span class="re__card-config-dot">·</span>
                                                    </span>

                                                        <span class="re__card-config-price">${r.hasAttic} Attic</span>

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
                                        ${r.roomStatus==1?"Available":"Unavailable"}
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
                            <h2>No valid room</h2>
                            </c:if>
                        </div>

                    </div>
                </div>


            </div>
        </div>
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
            district.length = 1;
            ward.length = 1;
            if(this.options[this.selectedIndex].dataset.id != ""){
                const result = data.filter(n => n.Id === this.options[this.selectedIndex].dataset.id);

                for (const k of result[0].Districts) {
                    var opt = document.createElement('option');
                    opt.value = k.Name;
                    opt.text = k.Name;
                    opt.setAttribute('data-id', k.Id);
                    district.options.add(opt);
                }
            }
        };
        district.onchange = function () {
            ward.length = 1;
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
<script src="assets/js/loading-handler.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        var loadingOverlay = document.getElementById('loading-overlay');
        var loader = document.getElementById('preloader');
        loadingOverlay.style.display = 'block'; // Hiển thị biểu tượng loading

        // Bắt sự kiện load hoàn tất của trang và ẩn biểu tượng loading
        window.addEventListener("load", function() {
            loadingOverlay.style.display = 'none';
        });
        window.addEventListener('beforeunload', function() {
            loadingOverlay.style.display = 'block'; // Hiển thị biểu tượng loading khi bắt đầu chuyển trang
        });
    });




</script>
<!--Script-->
</body>
</html>
