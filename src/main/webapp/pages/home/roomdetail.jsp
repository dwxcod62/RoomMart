
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
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <!-- Site Metas -->
    <title> ROOMMART </title>

    <link rel="stylesheet" href="./assets/css/core_style/core.css">

    <!-- responsive style -->
    <%--    <link rel="stylesheet" href="assets/css/style.css">--%>
    <!-- Option 1: Include in HTML -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">


<%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">--%>
    <link rel="stylesheet" href="assets/sys-css/staticfile.batdongsan.com.vn/css/web/filestatic.ver3a77c7a9.msvbds.layout.min.css" />

    <link rel="stylesheet" href="assets/sys-css/staticfile.batdongsan.com.vn/lib/jquery-swiper/css/swiper.min.css" />

    <link rel="stylesheet" href="assets/sys-css/staticfile.batdongsan.com.vn/js/lightGallery1.2.21/css/lightgallery.min.css" />

    <link rel="stylesheet" href="assets/sys-css/staticfile.batdongsan.com.vn/lib/slick/slick.min.css" />

    <link rel="stylesheet" href="assets/sys-css/staticfile.batdongsan.com.vn/css/web/filestatic.ver3a77c7a9.msvbds.ldp.min.css" />

    <script type="text/javascript" src="assets/sys-css/staticfile.batdongsan.com.vn/js/filestatic.ver7.msvbds.speedup.min.js" async="" data-cfasync="false"></script>

    <script type="text/javascript" src="assets/sys-css/staticfile.batdongsan.com.vn/js/Common/Global/filestatic.ver7.msvbds.Extensions.min.js" async="" data-cfasync="false"></script>
    <script async src="src/main/webapp/assets/sys-css/securepubads.g.doubleclick.net/tag/js/gpt.js"></script>
    <script type="text/javascript" src="assets/sys-css/staticfile.batdongsan.com.vn/js/Origins/Binnova/filestatic.ver3a77c7a9.msvbds.FrontEnd.GAMBannerViewer.min.js" async="" data-cfasync="false"></script>
    <script type="text/javascript" src="assets/sys-css/staticfile.batdongsan.com.vn/js/Origins/Binnova/filestatic.ver3.msvbds.FrontEnd.GAMBannerScript.min.js" async="" data-cfasync="false"></script>




</head>

<body class="re__body re__body-ldp re__new-search-location-focus ${requestScope.RESPONSE_MSG eq null ? "over-flow-hidden" : ""}">


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
                    <%--remove--%>
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



<c:if test="${not empty requestScope.room}" >
<div class="re__main" itemprop="about">


    <!-- Product-detail-->
    <div  class="re__ldp re__main-content-layout js__main-container ">

        <div class="re__main-content">


            <div class="re__pr-container  cplus-4940_report-button pr-details pr-container vip-diamond" prid="38582357" prav="https://file4.batdongsan.com.vn/crop/200x140/2023/11/16/20231116202228-6c00_wm.jpg">

                <div class="re__pr-media-slide js__pr-media-slide" tabindex="0">
                    <div class="re__media-preview js__media-preview  swiper-container">
                        <ul class="swiper-wrapper">
                            <!-- for loop here -->

                            <c:forEach var="image" items="${requestScope.roomImg}" varStatus="loopStatus">
                                <li class="swiper-slide js__media-item-container" data-filter="image" data-index="${loopStatus.index}">
                                    <div class="re__overlay js__overlay">
                                        <img src="${image}" alt="Roommart" title="Roommart" class="pr-img" />
                                    </div>
                                    <div class="re__pr-image-cover js__pr-image-cover" title="" style="background-image: url('${image}')"></div>
                                </li>
                            </c:forEach>
                            <%--  loop end here--%>

                        </ul>


                        <!-- Add Pagination -->
                        <div class="swiper-pagination swiper-pagination-fraction"><span class="swiper-pagination-current">1</span>&nbsp;/&nbsp;<span class="swiper-pagination-total">16</span></div>
                        <!-- Add Arrows -->
<%--                        <i class="re__icon-chevron-right"></i>--%>
<%--                        <i class="re__icon-chevron-left"></i>--%>
                        <div action="go-right"><a class="re__btn re__btn-se-border--sm re__btn-icon--sm"> <i class="bi bi-caret-right"></i> </a></div>
                        <div action="go-left"><a class="re__btn re__btn-se-border--sm re__btn-icon--sm"> <i class="bi bi-caret-left"></i> </a></div>
                    </div>
                    <div class="re__media-thumbs js__media-thumbs ">
                        <div class="js__slick-thumb">
                            <c:forEach var="image" items="${requestScope.roomImg}" varStatus="loopStatus">
                                <div class="re__media-thumb-item js__media-thumbs-item" data-filter="image">
                                    <img data-src="${image}" alt="Roommart"
                                         title="Roommart" class="lazyload" />
                                </div>
                            </c:forEach>



                        </div>
                    </div>
                </div>
                <script type="text/javascript" src="assets/sys-css/staticfile.batdongsan.com.vn/js/Product/Binnova/Details/filestatic.ver2.msvbds.FrontEnd.Product.Details.MediaSlide.Psi.min.js" async="" data-cfasync="false"></script>

                <div class="re__pr-info pr-info js__product-detail-web" id="product-detail-web" uid="522997" prid="38582357">
                    <h1 class="re__pr-title pr-title js__pr-title">${room.roomInformation.hostelName} - ${room.roomNumber}</h1>
                    <span class="re__pr-short-description js__pr-address">${room.roomInformation.address}, ${room.roomInformation.ward}, ${room.roomInformation.district}, ${room.roomInformation.city}</span>



                    <div class="re__pr-short-info js__pr-short-info">
                        <div class="re__pr-short-info-item js__pr-short-info-item">
                            <span class="title">Mức giá</span>
                            <span class="value">Contact</span>
                        </div>
                        <div class="re__pr-short-info-item js__pr-short-info-item">
                            <span class="title">Diện tích</span>
                            <span class="value"> ${room.roomArea} m&#xB2;</span>
                        </div>
                        <div class="re__pr-short-info-item js__pr-short-info-item">
                            <span class="title">Phòng ngủ</span>
                            <span class="value">${room.capacity} PN</span>
                        </div>


                    </div>


                    <div class="re__section re__pr-description js__section js__li-description">
                        <span class="re__section-title">Thông tin mô tả</span>
                        <div class="re__section-body re__detail-content js__section-body js__pr-description js__tracking" trackingid="lead-phone-ldp" trackinglabel="loc=Rent-Listing Details-body,prid=38582357">
                            Xin chào quý anh/chị . Trân trọng cảm ơn!
                            <h2>Services: </h2>
                            <ul>
                                <c:forEach var="s" items="${requestScope.serviceList}">
                                    <li>
                                        <h6>${s.serviceName} : ${s.servicePrice} / ${s.unit}</h6>

                                    </li>
                                </c:forEach>
                            </ul>
                            <h2>Infrastures: </h2>
                            <ul>
                                <c:forEach var="f" items="${requestScope.infrasList}">
                                    <li>
                                        <h6>${s.infrastructureName}</h6>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div class="re__section re__pr-specs re__pr-specs-v1 js__section js__li-specs">
                        <span class="re__section-title">Đặc điểm bất động sản</span>
                        <div class="re__section-body re__border--std js__section-body">
                            <div class="re__pr-specs-content js__other-info">

                                <div class="re__pr-specs-content-item">
                                    <span class="re__pr-specs-content-item-icon"><i class="bi bi-bounding-box-circles"></i></span>
                                    <span class="re__pr-specs-content-item-title">Diện tích</span>
                                    <span class="re__pr-specs-content-item-value">${room.roomArea} m&#xB2;</span>
                                </div>
                                <div class="re__pr-specs-content-item">
                                    <span class="re__pr-specs-content-item-icon"><i class="bi bi-cash"></i></span>
                                    <span class="re__pr-specs-content-item-title">Mức giá</span>
                                    <span class="re__pr-specs-content-item-value">Price tri&#x1EC7;u/th&#xE1;ng</span>
                                </div>
                                <div class="re__pr-specs-content-item">
                                    <span class="re__pr-specs-content-item-icon"><i class="bi bi-border-all"></i></span>
                                    <span class="re__pr-specs-content-item-title">Số phòng ngủ</span>
                                    <span class="re__pr-specs-content-item-value">${room.capacity} phòng</span>
                                </div>
                                <div class="re__pr-specs-content-item">
                                    <span class="re__pr-specs-content-item-icon"><i class="bi bi-chevron-up"></i></span>
                                    <span class="re__pr-specs-content-item-title">Số Attic</span>
                                    <span class="re__pr-specs-content-item-value">${room.hasAttic} phòng</span>
                                </div>
                                <div class="re__pr-specs-content-item">
                                    <span class="re__pr-specs-content-item-icon"><i class="bi bi-tv"></i></span>
                                    <span class="re__pr-specs-content-item-title">Nội thất</span>
                                    <span class="re__pr-specs-content-item-value">Contact</span>
                                </div>
                            </div>
                        </div>
                    </div>


                </div>

            </div>

            <div class="re__divide-8"></div>
            <div class="re__section re__pr-more-listing re__similar-listing js__section js__similar-listing lazyload" data-script="assets/sys-css/staticfile.batdongsan.com.vn/js/Product/Binnova/Details/filestatic.ver3a77c7a9.msvbds.FrontEnd.Product.Details.SimilarListing.Lazy.min.js">
                    <span tracking-id="same-area-title-ldp" class="re__section-title">
        Bất động sản dành cho bạn

    </span>

                <div class="re__section-body js__section-body">
                    <div class="swiper-container re__swiper-container">
                        <div class="js__similar-listing-recommendations" style="transform: translate3d(0px, 0px, 0px);">
                            <!-- loop here -->
                            <c:forEach var="r" items="${requestScope.list}">
                                <div class="swiper-slide" style="display: inline-block; width: unset;margin-top: 20px; " >

                                    <div class="js__card js__card-compact-web
     pr-container re__card-compact re__vip-normal">
                                        <c:set var="encodedRoomId" value="${EncodeUtils.encodeString(r.roomId)}" />
                                        <a class="js__product-link-for-product-id" href="roomDetail?rid=${encodedRoomId}" >
                                            <div class="re__card-image
            ">
                                                <img alt="Ảnh đại diện" class="pr-img lazyloaded" src="${not empty r.imgUrl ? r.imgUrl[0] : 'https://media.licdn.com/dms/image/C5112AQEw1fXuabCTyQ/article-inline_image-shrink_1500_2232/0/1581099611064?e=1710374400&v=beta&t=LKfE3ie3occM50NiiYBq9mIgdJMjkeGnaiuREah4wEE'}">
                                                <div class="re__card-image-feature">
                                                    <i class="bi bi-image"></i>
                                                    <span>${r.imgUrl.size()}</span>
                                                </div>

                                            </div>
                                            <div class="re__card-info">
                                                <div class="re__card-info-content">
                                                    <div class="re__card-title">
                                                        <h3 class="js__card-title">
                                                                ${r.roomInformation.hostelName} - ${r.roomNumber}
                                                        </h3>
                                                    </div>
                                                    <div class="re__card-config">
                                                        <span class="re__card-config-price">Price</span>
                                                        <span class="re__card-config-area"><span class="re__card-config-dot">·</span>${r.roomArea} m²</span>
                                                        <div class="re__clear"></div>
                                                    </div>
                                                    <div class="re__card-location">
                                                        <i class="bi bi-house-fill"></i>
                                                        <span>${r.roomInformation.district}, ${r.roomInformation.city}</span>
                                                    </div>
                                                    <div class="re__clearfix"></div>
                                                    <div class="re__card-contact">
                                                        <div class="re__card-published-info">


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
                            <!-- end loop here -->






                        </div>
                    </div>
                </div>
            </div>

            <div class="re__divide-8">remove</div>


            <!-- slide  -->
            <script type="text/javascript">
                (function initMediaSlide() {
                    if (window.FrontEnd && window.FrontEnd.Product && window.FrontEnd.Product.Details && window.FrontEnd.Product.Details.MediaSlide) {
                        new FrontEnd.Product.Details.MediaSlide({})
                    } else {
                        setTimeout(initMediaSlide, 100);
                    }
                })();
                (function initProductDetails() {
                    if (window.FrontEnd && window.FrontEnd.Product && window.FrontEnd.Product.Details && window.FrontEnd.Product.Details.Details && window.FrontEnd.BadReportProductsServices) {

                        window.FrontEnd_Product_Details_DetailsBinnova = new FrontEnd.Product.Details.Details({
                            urlSubmitFeedback: '/Product/ProductDetail/SendFeedback',
                            productId: 38582357,
                            adbutlerConfig: {
                                MID: parseInt("183272"),
                                zoneId: parseInt("542226")
                            },
                            adbutlerAPI: "https://promote.batdongsan.com.vn/",
                        });

                        (new window.FrontEnd.BadReportProductsServices()).BadReportDetailsProduct();
                    } else {
                        setTimeout(initProductDetails, 100);
                    }
                })();
            </script>


            <div class="re__divide"></div>
            <div class="re__divide-8"></div>

        </div>
        <div class="re__main-sidebar">

            <!-- contact -->
            <div class="re__sidebar-box re__contact-box js__contact-box">
                <div class="re__contact-name js_contact-name" title="Hi&#x1EBF;u Hu&#x1EF3;nh &amp; C&#x1ED9;ng S&#x1EF1;">
                    <a tracking-id="navigate-agent-profile" tracking-label="source=name" href="#information">
                        Owner name
                    </a>
                </div>


<%--                <a data-href="https://zalo.me/${sdt}" data-qrcode="" tracking-id="zalo-chat-ldp" tracking-label="prid=38582357" data-uid="522997" class="re__btn re__btn-se-border--md js__zalo-chat js__zalo-chat-qr">--%>
<%--                    <div class="re__btn-icon-left--md re__btn-icon-text-center">--%>
<%--                        <i class="bi bi-chat"></i>--%>
<%--                        <span>Chat qua Zalo</span>--%>
<%--                    </div>--%>
<%--                </a>--%>

<%--                <a class="re__btn re__btn-se-border--md js__btnSendContact js__btn-send-contact-from-contact-box" href="chat?hostelId=${requestScope.room.hostelId}">Chat</a>--%>
            <form action="chat" method="post">
                <input type="hidden" name="hostelId" value="${requestScope.room.hostelId}"/>
                <input type="hidden" name="roomId" value="${requestScope.room.roomId}"/>

                <button class="re__btn re__btn-se-border--md js__btnSendContact js__btn-send-contact-from-contact-box" type="submit"><i class="bi bi-chat"></i> Chat with owner</button>
            </form>

            </div>



        </div>
    </div>
    <!-- end product-detail-->
    <script src="assets/sys-css/staticfile.batdongsan.com.vn/js/Common/Services/filestatic.ver3a77c7a9.msvbds.FrontEnd.UserListingViewedService.min.js" defer></script>
    <script src="assets/sys-css/staticfile.batdongsan.com.vn/js/Product/filestatic.ver3a77c7a9.msvbds.FrontEnd.Product.UserListingViewed.min.js" defer></script>




</div>
</c:if>
<c:if test="${empty requestScope.room}" >
    <div style="text-align: center;" class="re__ldp re__main-content-layout js__main-container">
        <img  src="https://res.cloudinary.com/dqp6vdayn/image/upload/v1707647165/What-is-a-404-error-code_lu1xgy.png" style="display: block; margin: auto;">


    </div>
</c:if>




<script type="text/javascript" src="assets/sys-css/staticfile.batdongsan.com.vn/js/jquery/jquery.common.min.js"></script>

<script type="text/javascript" src="assets/sys-css/staticfile.batdongsan.com.vn/lib/jquery-swiper/js/filestatic.ver1.msvbds.swiper.min.js"></script>
<script type="text/javascript" src="assets/sys-css/staticfile.batdongsan.com.vn/lib/jquery-swiper/js/jquery.touchSwipe.min.js"></script>

<script type="text/javascript" src="assets/sys-css/staticfile.batdongsan.com.vn/lib/jquery-img360/pannellum.min.js"></script>
<script type="text/javascript" src="assets/sys-css/staticfile.batdongsan.com.vn/lib/jquery-img360/img360.min.js"></script>
<script type="text/javascript" src="assets/sys-css/staticfile.batdongsan.com.vn/lib/slick/slick.min.js"></script>


<script type="text/javascript" src="assets/sys-css/staticfile.batdongsan.com.vn/js/Product/Binnova/Details/filestatic.ver3a77c7a9.msvbds.FrontEnd.Product.Details.MediaSlide.min.js"></script>

    <!-- Loader -->
    <script src="assets/js/loading-handler.js"></script>

</body>

</html>
