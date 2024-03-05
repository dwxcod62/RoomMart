<%@ page import="com.codebrew.roommart.utils.EncodeUtils" %>
<%@ page import="com.codebrew.roommart.dao.RoomDao" %>

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


    <!-- Bootstrap 5.1 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <!-- Vendor CSS Files -->
    <link href="./assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="./assets/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
    <link href="./assets/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">
    <link href="./assets/vendor/aos/aos.css" rel="stylesheet">

    <%-- link local css   --%>
    <link rel="stylesheet" href="./assets/css/push_notification_style/style.css">
    <link rel="stylesheet" href="./assets/css/system_style/home_style/home.css">
    <link rel="stylesheet" href="./assets/css/system_style/home_style/home2.css">
    <link rel="stylesheet" href="./assets/css/core_style/core.css">
    <link rel="stylesheet" href="./assets/css/hostel_owner_style/dashboard/style.css">


</head>

<body class="re__body re__body-home over-flow-hidden">



<div id="preloader">
    <div class="dots">
        <div></div>
        <div></div>
        <div></div>
    </div>
</div>



<div class="form-content">

    <div class="re__bg-header">
        <!-- Navbar -->

        <!-- End Navbar -->
        <%@include file="pages/home/components/navbar.jsp"%>
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

                                    <select id="city" name="city" class="select-text">
                                        <option style="color:#f0f0f0" value="all" selected>Chọn tỉnh thành</option>
                                    </select>

                                </div>

                                <div class="search-filter">


                                    <select id="district" name="district" class="select-text" >
                                        <option style="color:#f0f0f0" value="all" selected>Chọn quận huyện</option>
                                    </select>

                                </div>

                                <div class="search-filter">

                                    <select id="ward" name="ward" class="select-text" >
                                        <option style="color:#f0f0f0" value="all" selected>Chọn phường xã</option>
                                    </select>


                                </div>



                            </div>
                            <div class="home-filter home-filter-1">

                                <div class="search-filter" >

                                    <select title="Chọn diện tích phòng mong muốn"  name="area" class="select-text">

                                        <option style="color:black" value="0" selected>Diện tích</option>
                                        <c:forEach var="area" items="${listRoomArea}">
                                            <option style="color:black" value="${area}">${area}</option>
                                        </c:forEach>
                                    </select>

                                </div>

                                <div class="search-filter dropdown">
<%--select-text--%>
<%--dropdown-menu--%>
   <div title="Chọn mức giá phòng mong muốn" class="select-text select-custom js__listing-search-select-button js__listing-search-select-tooltip dropdown-toggle" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">

    <span  class="select-text-content js__listing-search-select-button-current-text" style="font-size: inherit">Mức Giá</span>
<%--        <div class="dropdown-menu re__listing-search-select-dropdown " aria-labelledby="dropdownMenuButton">--%>
<%--            <a class="dropdown-item " href="#">Action</a>--%>
<%--            <a class="dropdown-item" href="#">Another action</a>--%>
<%--            <a class="dropdown-item" href="#">Something else here</a>--%>
<%--        </div>--%>
      <jsp:include page="pages/home/drop-down-price.jsp"></jsp:include>

   </div>
                                </div>

                                <div class="search-filter">
                                    <input title="Chọn ngày muốn thuê phòng" name="expiration" class="select-text" type="date" style="font-size: inherit; color: #f0f0f0">
<%--                                    <select  name="hostelID" class="select-text" >--%>
<%--&lt;%&ndash;                                        for each&ndash;%&gt;--%>
<%--    <option style="color:black" value="0" selected>Khu trọ</option>--%>
<%--    <c:forEach var="hostel" items="${listHostel}">--%>
<%--        <option style="color:black" value="${hostel.hostelID}">${hostel.hostelName}</option>--%>
<%--    </c:forEach>--%>
<%--                                    </select>--%>


                                </div>



                            </div>
                        </div>
                    </div>
                </form>


            </div>
            <div class="re__home__head__banner-container re__banner-container re__banner-container-no-style js__gam__home__head__banner-container" style="height: 260px;margin-top: 5%">


            </div>
        </div>


        <div class="re__content-block re__home__bds-for-you-block re__bg-grey-50">
            <div class="re__content-container">


                <h2 class="re__content-container-label">Phòng dành cho bạn</h2>


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
                                            <a class="js__product-link-for-product-id" href="roomDetailH?hostelId=${encodedHostelId}&rid=${encodedRoomId}" title="Roommart" previewlistener="true">
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
                                                            <span class="re__card-config-price">${r.price >= 1000000 ? r.price/1000000 : r.price} ${r.price >= 1000000 ? "Triệu":""} VNĐ</span>

                                                            <span class="re__card-config-area">
                                                        <span class="re__card-config-dot">·</span>
                                                    </span>

                                                            <span class="re__card-config-price"> ${r.roomArea}m2</span>





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
                                                                <c:set var="endDate" value="${RoomDao.get_end_date_by_RoomId(r.roomId).toString()}"></c:set>
                                                                <c:set var="startDate" value="${RoomDao.get_start_date_by_RoomId(r.roomId).toString()}"></c:set>
                                                                <c:set var="formattedEndDate" value="${endDate.substring(8, 10)}/${endDate.substring(5, 7)}/${endDate.substring(0, 4)}" />
                                                                <c:set var="formattedStartDate" value="${startDate.substring(8, 10)}/${startDate.substring(5, 7)}/${startDate.substring(0, 4)}" />
                                                                <c:if test="${r.roomStatus==1}">
                                                                    <span style="color: green" class="re__card-published-info-published-at" data-microtip-position="right" role="tooltip">
                                                                        Có thể thuê
                                                                    </span>
                                                                </c:if>
                                                                <c:if test="${r.roomStatus==0}">
                                                                    <span class="re__card-published-info-published-at" data-microtip-position="right" role="tooltip">
                                                                        Đã Thuê
                                                                        <br>
                                                                       Từ ${formattedStartDate} đến ${formattedEndDate}
                                                                    </span>

                                                                </c:if>
                                                                <c:if test="${r.roomStatus==-1}">
                                                                    <span style="color:orange;" class="re__card-published-info-published-at" data-microtip-position="right" role="tooltip">
                                                                        Đang duyệt
                                                                        <br>
                                                                        Thuê từ ${formattedStartDate} đến ${formattedEndDate}
                                                                    </span>
                                                                </c:if>


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
<jsp:include page="pages/home/components/footer.jsp"></jsp:include>
<!-- Push notification element -->
<jsp:include page="./pages/home/components/boxchat.jsp" />
<div id="push-noti"></div>

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

    sendToWebSocket("hostel_owner", "hostel_renter", null, 4, null," chat chat chat chat chat chat chat chat chat chat");

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
            opt.style.color = 'black';
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
                    opt.style.color = 'black';

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
                    opt.style.color = 'black';

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
<script>

</script>
<!--Script-->
</body>
</html>
