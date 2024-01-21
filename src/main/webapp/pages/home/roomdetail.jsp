
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

    <!-- bootstrap core css -->
    <link rel="stylesheet" type="text/css" href="../../assets/css/bootstrap.css"/>


    <!-- font awesome style -->
<%--    <link href="../../assets/css/font-awesome.min.css" rel="stylesheet"/>--%>



    <!-- responsive style -->
    <link rel="stylesheet" href="../../assets/css/style.scss">


    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">

    <style>
        .carousel-item img {
            width: 100%;  /* chiều rộng bằng với container */
            height: 500px; /* chiều cao cố định */
            object-fit: cover; /* giữ tỷ lệ hình ảnh khi thay đổi kích thước */
        }
        #carouselWrapper{
            background-color: rgba(255, 255, 255, 0.5); /* nền trắng mờ */
            padding: 10px; /* padding xung quanh các hình ảnh */
            display: flex; /* sử dụng flexbox để sắp xếp các hình ảnh */
            justify-content: center; /* căn giữa các hình ảnh */
            align-items: center; /* căn giữa các hình ảnh theo trục dọc */
            margin: 0;
        }
        #carouselWrapper img {
            margin: 0 5px; /* khoảng cách 5px giữa các hình ảnh */
            cursor: pointer; /* con trỏ chuột dạng pointer khi di chuyển qua hình ảnh */
            width: 80px; /* chiều rộng cố định */
            height: 50px; /* chiều cao cố định */
            object-fit: cover; /* giữ tỷ lệ hình ảnh khi thay đổi kích thước */
        }
    </style>



</head>

<body>
<!--Header-->

<!--Header-->


<!-- food section -->
<section class="bg-light">
    <div class="container pb-5 m-auto">
        <div class="row">
            <div class="col-lg-9 mt-5">
                <div class="card mb-3">
                    <div id="myCarousel" class="carousel slide" data-ride="carousel" data-interval="false">
                        <!-- Indicators -->
                        <ol class="carousel-indicators">
                            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                            <li data-target="#myCarousel" data-slide-to="1"></li>
                            <li data-target="#myCarousel" data-slide-to="2"></li>
                        </ol>

                        <!-- Wrapper for slides -->
                        <div class="carousel-inner card-img img-fluid">
                            <div class="carousel-item active">
                                <img src="https://media.designcafe.com/wp-content/uploads/2023/07/05141750/aesthetic-room-decor.jpg" alt="Los Angeles">
                            </div>

                            <div class="carousel-item card-img img-fluid">
                                <img src="https://www.shutterstock.com/shutterstock/photos/2157520005/display_1500/stock-photo-empty-interior-room-d-illustration-2157520005.jpg" alt="Chicago">
                            </div>

                            <div class="carousel-item card-img img-fluid">
                                <img src="https://www.usnews.com/object/image/00000167-32b5-dff7-a56f-7bb53c640000/181120-bedroom-stock.jpg?update-time=1542744061266&size=responsive640" alt="New York">
                            </div>
                        </div>

                        <!-- Left and right controls -->
                        <a class="carousel-control-prev" href="#myCarousel" role="button" data-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="carousel-control-next" href="#myCarousel" role="button" data-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>

                    </div>

                    <div id="carouselWrapper" class="carousel-indicators">
                        <img src="https://media.designcafe.com/wp-content/uploads/2023/07/05141750/aesthetic-room-decor.jpg" data-target="#myCarousel" data-slide-to="0" class="active">
                        <img src="https://www.shutterstock.com/shutterstock/photos/2157520005/display_1500/stock-photo-empty-interior-room-d-illustration-2157520005.jpg" data-target="#myCarousel" data-slide-to="1">
                        <img src="https://www.usnews.com/object/image/00000167-32b5-dff7-a56f-7bb53c640000/181120-bedroom-stock.jpg?update-time=1542744061266&size=responsive640" data-target="#myCarousel" data-slide-to="2">

                        <!-- Thêm các thumbnail khác ở đây -->
                    </div>
                </div>
                <div class="row card m-5">
                    <div class="card-body">
                    <!--Start Carousel Wrapper-->
                    <h1 id="sc">style css</h1>
                        <li class="list-inline-item">
                            <h6>Adress: ${requestScope.roomInfor.address}</h6>
                            <h6>city : ${requestScope.roomInfor.city}</h6>
                            <h6>district : ${requestScope.roomInfor.district}</h6>
                            <h6>ward : ${requestScope.roomInfor.ward}</h6>
                        </li>
                    <!--End Carousel Wrapper-->
                    </div>
                </div>
                <div class="row card m-5">
                    <div class="card-body">
                    <!--Start Carousel Wrapper-->
                    <h1 >Services Information</h1>
                        <li class="list-inline-item">
                            <h6>Adress: ${requestScope.roomInfor.address}</h6>
                            <h6>city : ${requestScope.roomInfor.city}</h6>
                            <h6>district : ${requestScope.roomInfor.district}</h6>
                            <h6>ward : ${requestScope.roomInfor.ward}</h6>
                        </li>
                    <!--End Carousel Wrapper-->
                    </div>
                </div>
                <div class="row card m-5">
                    <div class="card-body">
                    <!--Start Carousel Wrapper-->
                    <h1>Infrastures Information</h1>
                        <li class="list-inline-item">
                            <h6>Adress: ${requestScope.roomInfor.address}</h6>
                            <h6>city : ${requestScope.roomInfor.city}</h6>
                            <h6>district : ${requestScope.roomInfor.district}</h6>
                            <h6>ward : ${requestScope.roomInfor.ward}</h6>
                        </li>
                    <!--End Carousel Wrapper-->
                    </div>
                </div>
            </div>
            <!-- col end -->
            <div class="col-lg-3 mt-5 " >
                <div class="card" style="height: auto">
                    <div class="card-body">
                        <h1 class="h2">Room : ${requestScope.room.roomNumber}</h1>
                        <ul class="list-inline">
                            <li class="list-inline-item">
                                <h6>Genres:</h6>
                            </li>
                            <li class="list-inline-item">
                                <p class="text-muted">
                                    <strong>${requestScope.room.roomArea}</strong>
                                </p>
                            </li>
                        </ul>

                        <h6>Description:</h6>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod temp incididunt ut labore et dolore magna aliqua. Quis ipsum suspendisse. Donec condimentum elementum convallis. Nunc sed orci a diam ultrices aliquet interdum quis nulla.</p>

                        <p></p>
                        <ul class="list-inline">
                            <li class="list-inline-item">
                                <h6>Property:  ${requestScope.room.roomInformation.property_name}</h6>

                            </li>
                            <li class="list-inline-item">
                                <p class="text-muted"><strong>${requestScope.roomInfor.address}</strong></p>
                            </li>
                        </ul>

                        <ul class="list-inline d-flex">
                            <li class="list-inline-item">
                                <h6>Adress: ${requestScope.roomInfor.address}</h6>
                                <h6>city : ${requestScope.roomInfor.city}</h6>
                                <h6>district : ${requestScope.roomInfor.district}</h6>
                                <h6>ward : ${requestScope.roomInfor.ward}</h6>
                            </li>
                            <li class="list-inline-item">
                                <p class="text-muted">
                                    <strong> </strong>
                                </p>
                            </li>
                        </ul>
                        <h6>Services: ${requestScope.roomInfor.address} minute</h6>

                        <form class="mt-5" action="#" method="GET">
                            <div class="row pb-3">
                                <div class="col d-grid">
                                    <input hidden type="text" value="#" name="filmId"/>
                                    <button type="submit" class="btn btn-success btn-lg">
                                        Contact with property owner
                                    </button>
                                </div>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- end food section -->

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
