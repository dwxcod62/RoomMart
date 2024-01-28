

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

        <!-- bootstrap core css -->
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css" />


        <link href="assets/css/font-awesome.min.css" rel="stylesheet" />

        <!-- Custom styles for this template -->

                <link href="assets/css/menu.css" rel="stylesheet" />

        <!-- responsive style -->
<%--        <link rel="stylesheet" href="assets/css/style.css">--%>


        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">

    <link rel="stylesheet" href="https://staticfile.batdongsan.com.vn/css/web/filestatic.ver3a77c7a9.msvbds.layout.min.css" />
    <link rel="stylesheet" href="https://staticfile.batdongsan.com.vn/css/web/filestatic.ver3a77c7a9.msvbds.home.min.css" />
    <link rel="stylesheet" href="https://staticfile.batdongsan.com.vn/css/web/filestatic.ver3a77c7a9.msvbds.card-compact.min.css">

</head>
<body class="re__body re__body-home">

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

                    <div id="divUserStt" data-notification-library-url="https://static.batdongsan.com.vn/assets/bds-notification.js">
                        <a href="#login" class="re__btn re__btn-se-ghost--md">Login</a>
                        <span class="re__line"></span>
                        <a href="#register" class="re__btn  re__btn-se-ghost--md" rel="nofollow" id="kct_username" tracking-id="sign-up-button-seller" tracking-label="loc=Header">Register</a>
                    </div>

                </div>
                <!-- icon -->
                <div class="re__drop-menu">
                    <div class="re__left-menu">
                        <h1>
                            <a href="#home" >
                                <img style="height: 70px; object-fit: cover;" src="https://i.imgur.com/a8AWgbF.png" error-image-src="https://i.imgur.com/a8AWgbF.png" alt="Roomart" title="Roomart">
                            </a>
                        </h1>
                    </div>

                    <div class="re__right-menu">
                        <!--Header menu-->
                        <div class="re__home-header-menu">
                            <div class="re__home-header-menu">
                                <ul class="re__dropdown-no-art--sm re__dropdown-navigative-menu">



                                    <li class="lv0 ">
                                        <a href="#home">

                                            <span class="text">Home</span>
                                        </a><div class="re__arrrow"></div>

                                    </li>
                                    <li class="lv0 ">
                                        <a href="#home">

                                            <span class="text">About us</span>
                                        </a><div class="re__arrrow"></div>

                                    </li>
                                    <li class="lv0 ">
                                        <a href="#home">

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
                <form action="homeS" method="post" id="boxSearchForm" class="re__home-search-box js__home-search-box" data-home-search="true">
                    <div class="re__search-box-container">

                        <input data-val="true" data-val-required="The ProductType field is required." id="ProductType" name="ProductType" type="hidden" value="38" />
                        <div class="re__search-box-content js__search-box-content">
                            <div class="re__input-group--sm re__search-box-row js__search-row-location">

                                <div class="re__search-location-select-header js_search-location-select-header" tabindex="0">
                                    <div class="re__search-location-row re__search-location-select-header-item js_search-location-select-header-item">
                                        <div class="re__city-code-select js__listing-search-select-container js__city-code-select" data-multiple="false" data-default-value="Trên toàn quốc" data-type="" tracking-id="open-search-location" tracking-label="loc=Home">
                                            <i class="js__selected-icon re__icon-search re__city-icon-search"></i>
                                            <divc class="re__listing-search-select-button-current-text">
<%--                                                <select id="city" name="city">--%>
<%--                                                    <option value="" selected>Chọn tỉnh thành</option>--%>
<%--                                                </select>--%>
                                            </divc>


                                        </div>

                                        <div class="js__location-select re__location-select re__location-input" data-microtip-position="bottom" role="tooltip">
                                            <ul id="LocationTags" class="js__location-tags re__location-tags">
                                            </ul>
                                            <input id="LocationSearch" class="location-search__field valid" type="search" tabindex="0" autocomplete="off" placeholder="Nhập tối đa 5 địa điểm, dự án. Ví dụ: Quận Hoàn Kiếm, Quận Đống Đa">
                                        </div>

                                        <button type="submit" class="re__btn re__btn-pr-solid--sm re__btn-icon-left--sm re__btn-search" id="btnSearch">
                                            <span>Tìm kiếm</span>
                                        </button>
                                    </div>
                                    <div class="re__search-location-select-header-item-no-city js_search-location-select-header-item-no-city">
                                        <div class="re__header-item-no-city">
                                            <div class="re__city-search-select-header-title">Bạn muốn tìm bất động sản tại tỉnh thành nào?</div>
                                            <a class="re__city-search-select-button-close js__listing-search-no-city-button-close">
                                                <i class="re__icon-close-no-circle no-city-button-close"></i>
                                            </a>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="home-filter home-filter-1">

                                <div class="search-filter ">

                                    <select id="city" name="city">
                                        <option value="" selected>Chọn tỉnh thành</option>
                                    </select>

                                </div>

                                <div class="search-filter">


                                    <select id="district" name="district" class="select-text">
                                        <option value="" selected>Chọn quận huyện</option>
                                    </select>

                                </div>

                                <div class="search-filter">

                                    <select id="ward" name="ward" class="select-text">
                                        <option value="" selected>Chọn phường xã</option>
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


                <h2 class="re__content-container-label">Recommend for you</h2>


                <div id="interestedProductsBinnovaContent" class="re__product-container">

                    <!-- list img -->
                    <div class="home-product product-4-you">
<c:forEach items="${requestScope.rooms}" var="r">
                        <div class="js__interested-product re__interested-product-cards">
                            <div class="re__product-item re__interested-product-card js__product-item">

                                <div class="js__card js__card-compact-web pr-container re__card-compact re__vip-normal">
                                    <a class="js__product-link-for-product-id" href="#">
                                        <div class="re__card-image">
                                            <img class="pr-img ls-is-cached lazyloaded" src="${not empty r.imgUrl ? r.imgUrl[0] : 'https://media.licdn.com/dms/image/C5112AQEw1fXuabCTyQ/article-inline_image-shrink_1500_2232/0/1581099611064?e=1710374400&v=beta&t=LKfE3ie3occM50NiiYBq9mIgdJMjkeGnaiuREah4wEE'}" alt="room Image">
                                            <div class="re__card-image-feature">
                                                <i class="re__icon-image"></i>
                                                <span>${not empty r.imgUrl ? r.imgUrl.size : 0}</span>
                                            </div>

                                        </div>
                                        <div class="re__card-info">
                                            <div class="re__card-info-content">
                                                <div class="re__card-title">
                                                    <h3 class="js__card-title">
                                                        Title
                                                    </h3>
                                                </div>
                                                <div class="re__card-config">
                                                    <span class="re__card-config-price">Price</span>
                                                    <span class="re__card-config-area"><span class="re__card-config-dot">·</span>Square</span>
                                                    <div class="re__clear"></div>
                                                </div>
                                                <div class="re__card-location">
                                                    <i class="re__icon-location--sm"></i>
                                                    <span>Location</span>
                                                </div>
                                                <div class="re__clearfix"></div>
                                                <div class="re__card-contact">
                                                    <div class="re__card-published-info">


                                                    </div>


                                                </div>
                                            </div>
                                        </div>
                                        <div class="re__clearfix"></div>
                                    </a>
                                </div>
                            </div>

                        </div>
</c:forEach>
                    </div>
                </div>


            </div>
        </div>
    </div>
</div>



    <!-- food section -->

<section class="food_section layout_padding col-md-10">
    <div class="container">
        <div class="heading_container heading_center">
            <h2 style="font-family: sans-serif; margin: 15px;">
                WELCOME TO ROOMMART
            </h2>
        </div>
        <form method="get" action="homeS" class=row>


            <div class="container mt-5">
<%--                <select id="city" name="city">--%>
<%--                    <option value="" selected>Chọn tỉnh thành</option>--%>
<%--                </select>--%>

<%--                <select id="district" name="district">--%>
<%--                    <option value="" selected>Chọn quận huyện</option>--%>
<%--                </select>--%>

<%--                <select id="ward" name="ward">--%>
<%--                    <option value="" selected>Chọn phường xã</option>--%>
<%--                </select>--%>
                <div class="input-group">

                    <input type="text" class="form-control" id="searchInput" placeholder="Nhập địa chỉ...">
                    <div class="input-group-append">
                        <button class="btn btn-primary" type="submit">Tìm kiếm</button>
                    </div>
                </div>
            </div>

        </form>

        <div>


        </div>
        <ul class="filters_menu">

            <li class="${requestScope.citySelected != null ? 'active' : ''}" >
                <a href="#">City :${requestScope.citySelected != "all" ? requestScope.citySelected : ' All city'} </a>
            </li>

            <li class="${requestScope.districtSelected != null ? 'active' : ''}">
                <a  href="#">District :${requestScope.districtSelected != "all" ? requestScope.districtSelected : ' All District'}</a>

            </li>
            <li class="${requestScope.wardSelected != null ? 'active' : ': '}">
                <a  href="#">Ward :${requestScope.wardSelected != "all" ? requestScope.wardSelected : ' All ward'}</a>

            </li>
<%--            <c:forEach items="${requestScope.genres}" var="g">--%>
<%--                <li class="">--%>
<%--                    <a  href="#">type 3</a>--%>

<%--                </li>--%>
<%--            </c:forEach>--%>

        </ul>

        <div class="filters-content">
            <div class="row grid" >
                <c:if test="${not empty requestScope.rooms}">
                    <c:forEach items="${requestScope.rooms}" var="r">
                        <div class="col-sm-6 col-md-4 all">
                            <div class="box" style="box-shadow: 0 0 5px 5px lightgray;">
                                <div>
                                    <div class="img-box" style="height: 400px !important; border-radius: 0px !important;">
                                        <img src="${not empty r.imgUrl ? r.imgUrl[0] : 'https://media.licdn.com/dms/image/C5112AQEw1fXuabCTyQ/article-inline_image-shrink_1500_2232/0/1581099611064?e=1710374400&v=beta&t=LKfE3ie3occM50NiiYBq9mIgdJMjkeGnaiuREah4wEE'}" alt="Film Image" style="max-height: none; height: 100%">
                                    </div>
                                    <div class="detail-box" style="background: #f1f2f3;">
                                        <h5 style="text-transform: uppercase; text-align: center;">
                                            <a href="roomdetail?rid=${r.roomId}" class="" style="color: black; text-decoration: none">
                                                Number: ${r.roomNumber} <br>Area: ${r.roomArea} <br> Attic: ${r.hasAttic} <br> Capacity : ${r.capacity}
                                            </a>
                                        </h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${empty requestScope.rooms}">
                    <!-- Handle the case when requestScope.rooms is null or empty -->
                    <p>No rooms available.</p>
                </c:if>

            </div>
        </div>
    </div>

</section>

<!-- end food section -->

<%--<!--Script-->--%>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>
<script>
    var citySelect = ${requestScope.citySelected};
    var dis = ${requestScope.districtSelected};
    var w = ${requestScope.wardSelected};

    const host = "https://provinces.open-api.vn/api/";
    var callAPI = (api) => {
        return axios.get(api)
            .then((response) => {
                renderData(response.data, "city");
            });
    }
    callAPI('https://provinces.open-api.vn/api/?depth=1');
    var callApiDistrict = (api) => {
        return axios.get(api)
            .then((response) => {
                renderData(response.data.districts, "district");
            });
    }
    var callApiWard = (api) => {
        return axios.get(api)
            .then((response) => {
                renderData(response.data.wards, "ward");
            });
    }

    var renderData = function(array, select) {
        let row = "";
        if (select == "city"){
            row = '<option value="" selected>citySelect</option>';
        }else if (select == "district"){
            row = '<option value="" selected>dis</option>';
        }else if (select == "ward"){
            row = '<option value="" selected>w</option>';
        }

        array.forEach(function(element) {
            row += '<option data-id="' + element.code + '" value="' + element.name + '">' + element.name + '</option>';
        });
        document.querySelector("#" + select).innerHTML = row;
    }
    $("#city").change(() => {
        callApiDistrict(host + "p/" + $("#city").find(':selected').data('id') + "?depth=2");

    });
    $("#district").change(() => {
        callApiWard(host + "d/" + $("#district").find(':selected').data('id') + "?depth=2");

    });
    $("#ward").change(() => {

    });

</script>
<%--<!--Script-->--%>
</body>
</html>
