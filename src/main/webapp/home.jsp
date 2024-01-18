

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
        <link rel="stylesheet" href="assets/css/style.css">
                <link href="assets/css/menu.css" rel="stylesheet" />

        <!-- responsive style -->
        <link rel="stylesheet" href="assets/css/style.css">


        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">



</head>
<body>


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
                <select id="city" name="city">
                    <option value="" selected>Chọn tỉnh thành</option>
                </select>

                <select id="district" name="district">
                    <option value="" selected>Chọn quận huyện</option>
                </select>

                <select id="ward" name="ward">
                    <option value="" selected>Chọn phường xã</option>
                </select>
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
                                        <img src="https://media.designcafe.com/wp-content/uploads/2023/07/05141750/aesthetic-room-decor.jpg" alt="Film Image" style="max-height: none; height: 100%">
                                    </div>
                                    <div class="detail-box" style="background: #f1f2f3;">
                                        <h5 style="text-transform: uppercase; text-align: center;">
                                            <a href="roomdetail?rid=${r.roomId}" class="" style="color: black; text-decoration: none">
                                                Number: ${r.roomNumber} <br>Area: ${r.roomArea} <br> Attic: ${r.attic}
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

        let row = '<option value="" selected>Choose..</option>';
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
