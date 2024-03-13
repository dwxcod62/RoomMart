<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Favicon -->
    <link rel="icon" href="./assets/images/favicon/favicon.png" type="image/x-icon" />

    <!-- Title -->
    <title>Xác nhận giấy phép</title>

    <!-- Link Bootstrap !important -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <!-- Core CSS -->
    <link rel="stylesheet" href="./assets/css/core_style/core.css">


    <!-- Link your own CSS here -->
    <link rel="stylesheet" href="./assets/css/admin_page/admin-list-accounts/style.css">

    <!-- Simple Datatable CSS -->
    <link href="https://cdn.datatables.net/1.12.0/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">

</head>

<body class="${ requestScope.RESPONSE_MSG eq null ? "over-flow-hidden" : ""}">

<c:if test="${requestScope.RESPONSE_MSG eq null}">
    <div id="preloader">
        <div class="dots">
            <div></div>
            <div></div>
            <div></div>
        </div>
    </div>
</c:if>

<div class="app">

    <!-- Navbar -->
    <%@include file="./components/navbar.jsp"%>

    <!-- Body -->
    <div class="container">
        <div class="row position-relative">

            <!-- Side bar -->
            <div class="col-12 col-lg-3 col-xl-3 col-xxl-2">
                <%@include file="./components/sidebar.jsp"%>
            </div>

            <!-- Content -->
            <div class="col-12 col-lg-9 col-xl-9 col-xxl-10 col-xxl-10 pb-5 content-group">
                <!-- Inactive Account -->
                <div class="row mt-5">
                    <div class="uncheck-acc col-12 col-xl-10 col-xxl-8 m-auto">
                        <div class="uncheck-acc__title">
                            Khu trọ đang chờ phê duyệt
                        </div>
                        <table id="uncheck-acc__table"
                               class="uncheck-acc__table mt-4 mb-4 table table-hover table-bordered table-striped">
                            <thead class="table-dark uncheck-acc__table-head">
                            <tr>
                                <th>STT</th>
                                <th>Tên khu trọ</th>
                                <th>Chi tiết</th>
                                <th>Trạng thái</th>
                                <th class="table-head__action">Hành động</th>
                            </tr>
                            </thead>
                            <tbody class="table-light uncheck-acc__table-body">
                            <c:set var="count" value="0" scope="page"/>
                            <c:forEach var="UncheckAccount" items="${requestScope.OWNER_LIST_LICENSE}">

                                <c:if test="${UncheckAccount.status eq 1}">
                                    <c:set var="count" value="${count + 1}" scope="page"/>
                                    <tr>
                                        <td>${count}</td>
                                        <td>${UncheckAccount.hostelName}</td>
                                        <td>
                                            <button class="btn btn-outline-info fs-5"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#uncheck-acc__modal-detail-${UncheckAccount.hostelID}"
                                            >
                                                Chi tiết
                                            </button>
                                            <input id="uncheck-hsid${UncheckAccount.hostelID}" type="text" value="${UncheckAccount.hostelID}" hidden>
                                            <input id="uncheck-htimg${UncheckAccount.hostelID}" type="text" value="${UncheckAccount.imgUrl[0]}" hidden>
                                            <!-- Modal detail -->
                                            <div class="modal fade" id="uncheck-acc__modal-detail-${UncheckAccount.hostelID}" tabindex="-1"
                                                 aria-labelledby="#uncheck-acc__modal-detail-label" aria-hidden="true">
                                                <div class="modal-dialog modal-dialog-centered">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title" id="#uncheck-acc__modal-detail-label-${UncheckAccount.hostelID}">
                                                                Thông tin chi tiết
                                                            </h5>
                                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                        </div>
                                                        <div class="modal-body ps-5 pe-5">
                                                            <div class="row pt-3 pb-3">
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-label-${UncheckAccount.hostelID}">
                                                                        Tên khu trọ:
                                                                    </div>
                                                                </div>
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-content">
                                                                            ${UncheckAccount.hostelName eq null ? "Trống" : UncheckAccount.hostelName}
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row pt-3 pb-3">
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-label">
                                                                        Địa chỉ:
                                                                    </div>
                                                                </div>
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-content">
                                                                            ${UncheckAccount.address eq null ? "Trống" : UncheckAccount.address}
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row pt-3 pb-3">
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-label">
                                                                        Phường:
                                                                    </div>
                                                                </div>
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-content">
                                                                            ${UncheckAccount.ward eq null ? "Trống" :UncheckAccount.ward }
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row pt-3 pb-3">
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-label">
                                                                        Quận:
                                                                    </div>
                                                                </div>
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-content">
                                                                            ${UncheckAccount.district eq null ? "Trống" : UncheckAccount.district}
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row pt-3 pb-3">
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-label">
                                                                        Thành phố:
                                                                    </div>
                                                                </div>
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-content">
                                                                            ${UncheckAccount.city eq null ? "Trống" : UncheckAccount.city}
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row pt-3 pb-3">
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-label">
                                                                        Ảnh giấy phép hoạt động:
                                                                    </div>
                                                                </div>
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-content" >

                                                                        <img  onclick="zoomimgli(${UncheckAccount.hostelID})" style="width: 100px; height: 100px;" src="${UncheckAccount.imgUrl[0]}" alt="Trong"/>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Đồng ý</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="text-danger">
                                            Chưa kích hoạt
                                        </td>
                                        <td>
                                            <button class="btn btn-primary fs-5" data-bs-toggle="modal"
                                                    data-bs-target="#uncheck-acc__modal-${UncheckAccount.hostelID}">Kích hoạt</button>
                                            <!-- Modal -->
                                            <div class="modal fade" id="uncheck-acc__modal-${UncheckAccount.hostelID}" tabindex="-1"
                                                 aria-labelledby="uncheck-acc__modal-label" aria-hidden="true">
                                                <div class="modal-dialog modal-dialog-centered">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title" id="uncheck-acc__modal-label">
                                                                Cảnh báo
                                                            </h5>
                                                            <button type="button" class="btn-close"
                                                                    data-bs-dismiss="modal" aria-label="Close"></button>
                                                        </div>
                                                        <div class="modal-body mt-5 mb-5">
                                                            Bạn đang thực hiện kích hoạt cho giấy phép hoạt động "${UncheckAccount.hostelName}"! Hãy
                                                            đảm bảo
                                                            rằng bạn đã thực hiện đủ các thao các kiểm tra!
                                                        </div>
                                                        <div class="modal-footer justify-content-between">
                                                            <button type="button" class="btn btn-danger fs-4"
                                                                    data-bs-dismiss="modal">Hủy bỏ</button>
                                                            <form action="updateHostelStatus" method="POST">
                                                                <input type="hidden" name="hostelIdup" value="${UncheckAccount.hostelID}" />
                                                                <input type="hidden" name="status" value="${UncheckAccount.status}" />
                                                                <button type="submit" class="btn btn-success fs-4">
                                                                    Kích hoạt
                                                                </button>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>


                <div class="row mt-5">
                    <div class="checked-acc col-12 col-xl-10 col-xxl-8 m-auto">
                        <div class="checked-acc__title">
                            Khu trọ đang hoạt động
                        </div>
                        <table id="checked-acc__table"
                               class="checked-acc__table mt-4 mb-4 table table-hover table-bordered table-striped">
                            <thead class="table-dark checked-acc__table-head">
                            <tr>
                                <th>STT</th>
                                <th>Tên khu trọ</th>
                                <th>Chi tiết</th>
                                <th>Trạng thái</th>
                                <th class="table-head__action">Hành động</th>
                            </tr>
                            </thead>
                            <tbody class="table-light checked-acc__table-body">
                            <c:set var="count" value="0" scope="page"/>
                            <c:forEach var="CheckedAccount" items="${requestScope.OWNER_LIST_LICENSE}">
                                <c:if test="${CheckedAccount.status eq 0}">
                                    <c:set var="count" value="${count + 1}" scope="page"/>
                                    <tr>
                                        <td>${count}</td>
                                        <td>${CheckedAccount.hostelName}</td>
                                        <td>
                                            <button class="btn btn-outline-info fs-5"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#checked-acc__modal-detail-${CheckedAccount.hostelID}"
                                            >
                                                Chi tiết
                                            </button>
                                            <input id="check-hsid${CheckedAccount.hostelID}" type="text" value="${CheckedAccount.hostelID}" hidden>
                                            <input id="check-htimg${CheckedAccount.hostelID}" type="text" value="${CheckedAccount.imgUrl[0]}" hidden>
                                            <!-- Modal detail -->
                                            <div class="modal fade" id="checked-acc__modal-detail-${CheckedAccount.hostelID}" tabindex="-1"
                                                 aria-labelledby="#checked-acc__modal-detail-label-${CheckedAccount.hostelID}" aria-hidden="true">
                                                <div class="modal-dialog modal-dialog-centered">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title" id="#checked-acc__modal-detail-label-${CheckedAccount.hostelID}">
                                                                Thông tin chi tiết
                                                            </h5>
                                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                        </div>
                                                        <div class="modal-body ps-5 pe-5">
                                                            <div class="row pt-3 pb-3">
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-label-${CheckedAccount.hostelID}">
                                                                        Tên khu trọ:
                                                                    </div>
                                                                </div>
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-content">
                                                                            ${CheckedAccount.hostelName eq null ? "Trống" : CheckedAccount.hostelName}
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row pt-3 pb-3">
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-label">
                                                                        Địa chỉ:
                                                                    </div>
                                                                </div>
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-content">
                                                                            ${CheckedAccount.address eq null ? "Trống" : CheckedAccount.address}
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row pt-3 pb-3">
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-label">
                                                                        Phường:
                                                                    </div>
                                                                </div>
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-content">
                                                                            ${CheckedAccount.ward eq null ? "Trống" :CheckedAccount.ward }
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row pt-3 pb-3">
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-label">
                                                                        Quận:
                                                                    </div>
                                                                </div>
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-content">
                                                                            ${CheckedAccount.district eq null ? "Trống" : CheckedAccount.district}
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row pt-3 pb-3">
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-label">
                                                                        Thành phố:
                                                                    </div>
                                                                </div>
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-content">
                                                                            ${CheckedAccount.city eq null ? "Trống" : CheckedAccount.city}
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row pt-3 pb-3">
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-label">
                                                                        Ảnh giấy phép hoạt động:
                                                                    </div>
                                                                </div>
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-content" >

                                                                        <img  onclick="zoomimgli1(${CheckedAccount.hostelID})" style="width: 100px; height: 100px;" src="${CheckedAccount.imgUrl[0]}" alt="Null"/>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Đồng ý</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="text-success">
                                            Đã kích hoạt
                                        </td>
                                        <td>
                                            <button class="btn btn-danger fs-5" data-bs-toggle="modal"
                                                    data-bs-target="#checked-acc__modal-${CheckedAccount.hostelID}">Khóa khu trọ</button>
                                            <!-- Modal -->
                                            <div class="modal fade" id="checked-acc__modal-${CheckedAccount.hostelID}" tabindex="-1"
                                                 aria-labelledby="checked-acc__modal-label" aria-hidden="true">
                                                <div class="modal-dialog modal-dialog-centered">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title" id="checked-acc__modal-label">
                                                                Cảnh báo
                                                            </h5>
                                                            <button type="button" class="btn-close"
                                                                    data-bs-dismiss="modal" aria-label="Close"></button>
                                                        </div>
                                                        <div class="modal-body mt-5 mb-5">
                                                            Bạn đang thực hiện khóa khu trọ "${CheckedAccount.hostelName}"! Hãy
                                                            đảm bảo bạn khóa khu trọ này với lý do rõ ràng!
                                                        </div>
                                                        <div class="modal-footer justify-content-between">
                                                            <button type="button" class="btn btn-danger fs-4"
                                                                    data-bs-dismiss="modal">Hủy bỏ</button>
                                                            <form action="updateHostelStatus" method="POST">
                                                                <input type="hidden" name="hostelIdup" value="${CheckedAccount.hostelID}" />
                                                                <input type="hidden" name="status" value="${CheckedAccount.status}" />
                                                                <button type="submit" class="btn btn-success fs-4">
                                                                    Khóa khu trọ
                                                                </button>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>




                <div class="row mt-5">
                    <div class="banned-acc col-12 col-xl-10 col-xxl-8 m-auto">
                        <div class="banned-acc__title">
                            Danh sách khu trọ bị khóa
                        </div>
                        <table id="banned-acc__table"
                               class="banned-acc__table mt-4 mb-4 table table-hover table-bordered table-striped">
                            <thead class="table-dark banned-acc__table-head">
                            <tr>
                                <th>STT</th>
                                <th>Tên khu trọ</th>
                                <th>Chi tiết</th>
                                <th>Trạng thái</th>
                                <th class="table-head__action">Hành động</th>
                            </tr>
                            </thead>
                            <tbody class="table-light banned-acc__table-body">
                            <c:set var="count" value="0" scope="page"/>
                            <c:forEach var="BannedAccount" items="${requestScope.OWNER_LIST_LICENSE}">
                                <c:if test="${BannedAccount.status eq -1}">
                                    <c:set var="count" value="${count + 1}" scope="page"/>
                                    <tr>
                                        <td>${count}</td>
                                        <td>${BannedAccount.hostelName}</td>
                                        <td>
                                            <button class="btn btn-outline-info fs-5"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#banned-acc__modal-detail-${BannedAccount.hostelID}"
                                            >
                                                Chi tiết
                                            </button>
                                            <input id="ban-hsid${BannedAccount.hostelID}" type="text" value="${BannedAccount.hostelID}" hidden>
                                            <input id="ban-htimg${BannedAccount.hostelID}" type="text" value="${BannedAccount.imgUrl[0]}" hidden>
                                            <!-- Modal detail -->
                                            <div class="modal fade" id="banned-acc__modal-detail-${BannedAccount.hostelID}" tabindex="-1"
                                                 aria-labelledby="#banned-acc__modal-detail-label-${BannedAccount.hostelID}" aria-hidden="true">
                                                <div class="modal-dialog modal-dialog-centered">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title" id="#banned-acc__modal-detail-label-${BannedAccount.hostelID}">
                                                                Thông tin chi tiết
                                                            </h5>
                                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                        </div>
                                                        <div class="modal-body ps-5 pe-5">
                                                            <div class="row pt-3 pb-3">
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-label-${BannedAccount.hostelID}">
                                                                        Tên khu trọ:
                                                                    </div>
                                                                </div>
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-content">
                                                                            ${BannedAccount.hostelName eq null ? "Trống" : BannedAccount.hostelName}
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row pt-3 pb-3">
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-label">
                                                                        Địa chỉ:
                                                                    </div>
                                                                </div>
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-content">
                                                                            ${BannedAccount.address eq null ? "Trống" : BannedAccount.address}
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row pt-3 pb-3">
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-label">
                                                                        Phường:
                                                                    </div>
                                                                </div>
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-content">
                                                                            ${BannedAccount.ward eq null ? "Trống" :BannedAccount.ward }
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row pt-3 pb-3">
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-label">
                                                                        Quận:
                                                                    </div>
                                                                </div>
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-content">
                                                                            ${BannedAccount.district eq null ? "Trống" : BannedAccount.district}
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row pt-3 pb-3">
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-label">
                                                                        Thành phố:
                                                                    </div>
                                                                </div>
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-content">
                                                                            ${BannedAccount.city eq null ? "Trống" : BannedAccount.city}
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row pt-3 pb-3">
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-label">
                                                                        Ảnh giấy phép hoạt động:
                                                                    </div>
                                                                </div>
                                                                <div class="col-6">
                                                                    <div class="member-detail-modal-content" >

                                                                        <img  onclick="zoomimgli2(${BannedAccount.hostelID})" style="width: 100px; height: 100px;" src="${BannedAccount.imgUrl[0]}" alt="Trong"/>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Đồng ý</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="text-warning">
                                            Đã bị khóa
                                        </td>
                                        <td>
                                            <button class="btn btn-success fs-5" data-bs-toggle="modal"
                                                    data-bs-target="#banned-acc__modal-${BannedAccount.hostelID}">Mở khóa khu trọ</button>
                                            <!-- Modal -->
                                            <div class="modal fade" id="banned-acc__modal-${BannedAccount.hostelID}" tabindex="-1"
                                                 aria-labelledby="banned-acc__modal-label" aria-hidden="true">
                                                <div class="modal-dialog modal-dialog-centered">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title" id="banned-acc__modal-label">
                                                                Cảnh báo
                                                            </h5>
                                                            <button type="button" class="btn-close"
                                                                    data-bs-dismiss="modal" aria-label="Close"></button>
                                                        </div>
                                                        <div class="modal-body mt-5 mb-5">
                                                            Bạn đang thực hiện kích hoạt cho giấy phép hoạt động "${BannedAccount.hostelName}"! Hãy
                                                            đảm bảo
                                                            rằng bạn đã thực hiện đủ các thao các kiểm tra!
                                                        </div>
                                                        <div class="modal-footer justify-content-between">
                                                            <button type="button" class="btn btn-danger fs-4"
                                                                    data-bs-dismiss="modal">Hủy bỏ</button>
                                                            <form action="updateHostelStatus" method="POST">
                                                                <input type="hidden" name="hostelIdup" value="${BannedAccount.hostelID}" />
                                                                <input type="hidden" name="status" value="${BannedAccount.status}" />
                                                                <button type="submit" class="btn btn-success fs-4">
                                                                    Mở khóa khu trọ
                                                                </button>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Footer -->
    <%@include file="./components/footer.jsp"%>

    <!-- Toast element -->
    <div id="toast">&nbsp;</div>

</div>

<!-- Script Bootstrap !important -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
<!-- JQuery -->
<script src="./assets/js/jquery-3.5.1.min.js" type="text/javascript"></script>
<!-- Link your script here -->
<script src="./assets/js/handle-main-navbar.js"></script>
<!-- Simple Datatable JS -->
<script src="./assets/js/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="./assets/js/toast-alert.js"></script>
<script>
    <c:choose>
    <c:when test="${requestScope.RESPONSE_MSG ne null && requestScope.RESPONSE_MSG.status eq true}">
    toast({
        title: 'Thành công',
        message: '${requestScope.RESPONSE_MSG.content}',
        type: 'success',
        duration: 5000
    });
    </c:when>
    <c:when test="${requestScope.RESPONSE_MSG ne null && requestScope.RESPONSE_MSG.status eq false}">
    toast({
        title: 'Lỗi',
        message: '${requestScope.RESPONSE_MSG.content}',
        type: 'error',
        duration: 5000
    });
    </c:when>
    </c:choose>
</script>
<script>
    $('#uncheck-acc__table').DataTable();
    $('#checked-acc__table').DataTable();
    $('#banned-acc__table').DataTable();


    function zoomimgli(hostelID) {
        // Đóng modal khi nhấn vào ảnh
        var check3 = "uncheck-htimg"+hostelID;
        var check4 = "uncheck-hsid"+hostelID;
        console.log(check3)
        var htimg = document.getElementById(check3).value;
        var hid = document.getElementById(check4).value;
        var modal = document.getElementById("uncheck-acc__modal-detail-"+hid);
        var modalInstance = bootstrap.Modal.getInstance(modal);
        if (modalInstance) {
            modalInstance.hide();
        }

        // Tạo một div overlay để chứa ảnh và nút tắt
        var overlay = document.createElement("div");
        overlay.style.position = "fixed";
        overlay.style.top = "0";
        overlay.style.left = "0";
        overlay.style.width = "100%";
        overlay.style.height = "100%";
        overlay.style.backgroundColor = "rgba(0, 0, 0, 0.7)"; // Màu nền của overlay, có thể điều chỉnh độ mờ tại đây
        overlay.style.zIndex = "1000"; // Đảm bảo rằng overlay hiển thị trên cùng của các phần tử khác
        overlay.style.display = "flex";
        overlay.style.justifyContent = "center";
        overlay.style.alignItems = "center";
        overlay.onclick = function() {
            document.body.removeChild(overlay);
        };

        // Tạo ảnh lớn
        var img = document.createElement("img");
        img.src = htimg; // Đường dẫn ảnh lớn
        img.style.maxWidth = "90%";
        img.style.maxHeight = "90%";
        img.style.borderRadius = "5px"; // Bo góc cho ảnh, tùy chỉnh theo ý muốn
        img.style.boxShadow = "0 0 20px rgba(0, 0, 0, 0.5)"; // Đổ bóng cho ảnh

        // Tạo nút đóng
        var closeButton = document.createElement("button");
        closeButton.innerHTML = "x";
        closeButton.style.position = "absolute";
        closeButton.style.top = "10px";
        closeButton.style.right = "10px";
        closeButton.style.backgroundColor = "transparent";
        closeButton.style.color = "#ffffff"; // Màu của nút đóng
        closeButton.style.border = "none";
        closeButton.style.fontSize = "20px";
        closeButton.style.cursor = "pointer";
        closeButton.onclick = function() {
            document.body.removeChild(overlay);
        };

        // Thêm ảnh và nút đóng vào overlay
        overlay.appendChild(img);
        overlay.appendChild(closeButton);

        // Thêm overlay vào body
        document.body.appendChild(overlay);
    }

    function zoomimgli1(hostelID) {
        // Đóng modal khi nhấn vào ảnh
        var check1 = "check-htimg"+hostelID;
        var check2 = "check-hsid"+hostelID;
        console.log(check1)
        var htimg = document.getElementById(check1).value;
        var hid = document.getElementById(check2).value;
        var modal = document.getElementById("checked-acc__modal-detail-"+hid);
        var modalInstance = bootstrap.Modal.getInstance(modal);
        if (modalInstance) {
            modalInstance.hide();
        }

        // Tạo một div overlay để chứa ảnh và nút tắt
        var overlay = document.createElement("div");
        overlay.style.position = "fixed";
        overlay.style.top = "0";
        overlay.style.left = "0";
        overlay.style.width = "100%";
        overlay.style.height = "100%";
        overlay.style.backgroundColor = "rgba(0, 0, 0, 0.7)"; // Màu nền của overlay, có thể điều chỉnh độ mờ tại đây
        overlay.style.zIndex = "1000"; // Đảm bảo rằng overlay hiển thị trên cùng của các phần tử khác
        overlay.style.display = "flex";
        overlay.style.justifyContent = "center";
        overlay.style.alignItems = "center";
        overlay.onclick = function() {
            document.body.removeChild(overlay);
        };

        // Tạo ảnh lớn
        var img = document.createElement("img");
        img.src = htimg; // Đường dẫn ảnh lớn
        img.style.maxWidth = "90%";
        img.style.maxHeight = "90%";
        img.style.borderRadius = "5px"; // Bo góc cho ảnh, tùy chỉnh theo ý muốn
        img.style.boxShadow = "0 0 20px rgba(0, 0, 0, 0.5)"; // Đổ bóng cho ảnh

        // Tạo nút đóng
        var closeButton = document.createElement("button");
        closeButton.innerHTML = "x";
        closeButton.style.position = "absolute";
        closeButton.style.top = "10px";
        closeButton.style.right = "10px";
        closeButton.style.backgroundColor = "transparent";
        closeButton.style.color = "#ffffff"; // Màu của nút đóng
        closeButton.style.border = "none";
        closeButton.style.fontSize = "20px";
        closeButton.style.cursor = "pointer";
        closeButton.onclick = function() {
            document.body.removeChild(overlay);
        };

        // Thêm ảnh và nút đóng vào overlay
        overlay.appendChild(img);
        overlay.appendChild(closeButton);

        // Thêm overlay vào body
        document.body.appendChild(overlay);
    }


    function zoomimgli2(hostelID) {
        // Đóng modal khi nhấn vào ảnh
        var check1 = "ban-htimg"+hostelID;
        var check2 = "ban-hsid"+hostelID;
        var htimg = document.getElementById(check1).value;
        var hid = document.getElementById(check2).value;
        var modal = document.getElementById("banned-acc__modal-detail-"+hid);
        var modalInstance = bootstrap.Modal.getInstance(modal);
        if (modalInstance) {
            modalInstance.hide();
        }

        // Tạo một div overlay để chứa ảnh và nút tắt
        var overlay = document.createElement("div");
        overlay.style.position = "fixed";
        overlay.style.top = "0";
        overlay.style.left = "0";
        overlay.style.width = "100%";
        overlay.style.height = "100%";
        overlay.style.backgroundColor = "rgba(0, 0, 0, 0.7)"; // Màu nền của overlay, có thể điều chỉnh độ mờ tại đây
        overlay.style.zIndex = "1000"; // Đảm bảo rằng overlay hiển thị trên cùng của các phần tử khác
        overlay.style.display = "flex";
        overlay.style.justifyContent = "center";
        overlay.style.alignItems = "center";
        overlay.onclick = function() {
            document.body.removeChild(overlay);
        };

        // Tạo ảnh lớn
        var img = document.createElement("img");
        img.src = htimg; // Đường dẫn ảnh lớn
        img.style.maxWidth = "90%";
        img.style.maxHeight = "90%";
        img.style.borderRadius = "5px"; // Bo góc cho ảnh, tùy chỉnh theo ý muốn
        img.style.boxShadow = "0 0 20px rgba(0, 0, 0, 0.5)"; // Đổ bóng cho ảnh

        // Tạo nút đóng
        var closeButton = document.createElement("button");
        closeButton.innerHTML = "x";
        closeButton.style.position = "absolute";
        closeButton.style.top = "10px";
        closeButton.style.right = "10px";
        closeButton.style.backgroundColor = "transparent";
        closeButton.style.color = "#ffffff"; // Màu của nút đóng
        closeButton.style.border = "none";
        closeButton.style.fontSize = "20px";
        closeButton.style.cursor = "pointer";
        closeButton.onclick = function() {
            document.body.removeChild(overlay);
        };

        // Thêm ảnh và nút đóng vào overlay
        overlay.appendChild(img);
        overlay.appendChild(closeButton);

        // Thêm overlay vào body
        document.body.appendChild(overlay);
    }



</script>

<c:if test="${requestScope.RESPONSE_MSG eq null}">
    <!-- Loader -->
    <script src="./assets/js/loading-handler.js"></script>
</c:if>
</body>

</html>
