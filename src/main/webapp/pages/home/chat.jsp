<%@ page import="com.codebrew.roommart.utils.EncodeUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="com.codebrew.roommart.utils.EncodeUtils" %>
<html lang="en">

<!-- Head -->
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, maximum-scale=1, shrink-to-fit=no">
    <title>Chat</title>

    <!-- Template core CSS -->
    <link href="assets/css/chat_style/template.min.css" rel="stylesheet">
    <%--        <link rel="stylesheet" href="assets/scss/chat_style/chat.css">--%>

</head>
<!-- Head -->

<body>
<div id="preloader">
    <div class="dots">
        <div></div>
        <div></div>
        <div></div>
    </div>
</div>
<div class="layout">

    <!-- Navigation -->
    <div class="navigation navbar navbar-light justify-content-center py-xl-7">

        <!-- Brand -->
        <a href="home" class="d-none d-xl-block mb-6">
            <img src="./assets/images/logos/logo.png" class="mx-auto fill-primary" data-inject-svg="" alt="" style="height: 46px;">
        </a>

        <!-- Menu -->
        <ul class="nav navbar-nav flex-row flex-xl-column flex-grow-1 justify-content-between justify-content-xl-center py-3 py-lg-0" role="tablist">

            <!-- Invisible item to center nav vertically -->
            <li class="nav-item d-none d-xl-block invisible flex-xl-grow-1">
                <a class="nav-link position-relative p-0 py-xl-3" href="#" title="">
                    <i class="icon-lg fe-x"></i>
                </a>
            </li>


            <li class="nav-item mt-xl-9">
                <c:set var="encodedRoomId" value="${EncodeUtils.encodeString(requestScope.roomId)}" />
                <c:set var="encodedHostelId" value="${EncodeUtils.encodeString(requestScope.hostelId)}" />

                <%--                <a id="link-room-detail" class="nav-link position-relative p-0 py-xl-3" href="roomDetail?hostelId=${encodedHostelId}&rid=${encodedRoomId}" title="Back" >--%>
                <%--                    <i class="icon-lg fe-chevron-left"></i>--%>

                <%--                </a>--%>
                <a id="link-room-detail" class="nav-link position-relative p-0 py-xl-3" href="javascript:history.back()" title="Back" >
                    <i class="icon-lg fe-chevron-left"></i>

                </a>
                <%--                <a href="javascript:history.back()" class="btn btn-warning"> <i class="fas fa-arrow-left"></i> Go Back</a>--%>
            </li>
            <!-- Chats -->
            <li class="nav-item mt-xl-9">
                <a class="nav-link position-relative p-0 py-xl-3 active" ${sessionScope.role!=1?"onclick=hideSidebar()":""} data-toggle="tab" href="#tab-content-dialogs" title="Chats" role="tab">
                    <i class="icon-lg fe-message-square"></i>
                    <div class="badge badge-dot badge-primary badge-bottom-center"></div>
                </a>
            </li>

            <!-- Profile -->
            <li class="nav-item mt-xl-9">
                <a class="nav-link position-relative p-0 py-xl-3" onclick="showSidebar()" data-toggle="tab" href="#tab-content-user" title="User" role="tab">
                    <i class="icon-lg fe-user"></i>
                </a>
            </li>



        </ul>
        <!-- Menu -->

    </div>
    <!-- Navigation -->

    <!-- Sidebar -->
    <div id="sidebarList" style="display: none" class="sidebar">
        <div class="tab-content h-100" role="tablist">


            <div class="tab-pane fade h-100 show active" id="tab-content-dialogs" role="tabpanel">
                <div class="d-flex flex-column h-100">

                    <div class="hide-scrollbar">
                        <div class="container-fluid py-6">

                            <!-- Title -->
                            <h2 class="font-bold mb-6">Chats for ${sessionScope.role==1? "Owner":"User"} </h2>
                            <!-- Title -->





                            <!-- Chats List -->
                            <nav id="user-list" class="nav d-block list-discussions-js mb-n6">
                                <!-- loop here -->
                                <!-- Chat link -->
                                <!-- <a class="text-reset nav-link p-0 mb-6" href="chat-1.html">
                                    <div class="card card-active-listener">
                                        <div class="card-body">

                                            <div class="media">


                                                <div class="avatar mr-5">
                                                    <img class="avatar-img" src="assets\images\avatars\11.jpg" alt="Bootstrap Themes">
                                                </div>

                                                <div class="media-body overflow-hidden">
                                                    <div class="d-flex align-items-center mb-1">
                                                        <h6 class="text-truncate mb-0 mr-auto">Bootstrap Themes</h6>
                                                        <p class="small text-muted text-nowrap ml-4">10:42 am</p>
                                                    </div>
                                                    <div class="text-truncate">Anna Bridges: Hey, Maher! How are you? The weather is great isn't it?</div>
                                                </div>
                                            </div>

                                        </div>


                                        <div class="badge badge-circle badge-primary badge-border-light badge-top-right">
                                            <span>3</span>
                                        </div>

                                    </div>
                                </a> -->

                                <!-- end loop -->
                            </nav>
                            <!-- Chats List-->

                        </div>
                    </div>

                </div>
            </div>



            <div class="tab-pane fade h-100" id="tab-content-user" role="tabpanel">
                <div class="d-flex flex-column h-100">

                    <div class="hide-scrollbar">
                        <div class="container-fluid py-6">

                            <!-- Title -->
                            <h2 class="font-bold mb-6">Profile</h2>
                            <!-- Title -->



                            <!-- Card -->
                            <div class="card mb-6">
                                <div class="card-body">
                                    <div class="text-center py-6">
                                        <!-- Photo -->
                                        <div class="avatar avatar-xl mb-5">
                                            <img class="avatar-img" src="https://cdn4.iconfinder.com/data/icons/avatars-xmas-giveaway/128/sheep_mutton_animal_avatar-512.png" alt="">
                                        </div>

                                        <h5>${requestScope.infor.fullname}</h5>
                                        <%--                                        <p class="text-muted">Bootstrap is an open source toolkit for developing web with HTML.</p>--%>
                                    </div>
                                </div>
                            </div>
                            <!-- Card -->

                            <!-- Card -->
                            <div class="card mb-6">
                                <div class="card-body">
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item px-0 py-6">
                                            <div class="media align-items-center">
                                                <div class="media-body">
                                                    <p class="small text-muted mb-0">Country</p>
                                                    <p>${requestScope.infor.address}</p>
                                                </div>
                                                <i class="text-muted icon-sm fe-globe"></i>
                                            </div>
                                        </li>

                                        <li class="list-group-item px-0 py-6">
                                            <div class="media align-items-center">
                                                <div class="media-body">
                                                    <p class="small text-muted mb-0">Phone</p>
                                                    <p>${requestScope.infor.phone}</p>
                                                </div>
                                                <i class="text-muted icon-sm fe-mic"></i>
                                            </div>
                                        </li>

                                        <li class="list-group-item px-0 py-6">
                                            <div class="media align-items-center">
                                                <div class="media-body">
                                                    <p class="small text-muted mb-0">Email</p>
                                                    <p>${requestScope.infor.email}</p>
                                                </div>
                                                <i class="text-muted icon-sm fe-mail"></i>
                                            </div>
                                        </li>


                                    </ul>
                                </div>
                            </div>
                            <!-- Card -->





                        </div>
                    </div>

                </div>




            </div>
        </div>
    </div>
    <!-- Sidebar -->

    <!-- Main Content -->
    <div class="main main-visible" data-mobile-height="">

        <!-- Chat -->
        <div id="chat-1" class="chat dropzone-form-js" data-dz-url="some.php">

            <!-- Chat: body -->
            <div class="chat-body">

                <!-- Chat: Header -->
                <div class="chat-header border-bottom py-4 py-lg-6 px-lg-8">
                    <div class="container-xxl">

                        <div class="row align-items-center">

                            <!-- Close chat(mobile) -->
                            <div class="col-3 d-xl-none">
                                <ul class="list-inline mb-0">
                                    <li class="list-inline-item">
                                        <a class="text-muted px-0" href="#" data-chat="open">
                                            <i class="icon-md fe-chevron-left"></i>
                                        </a>
                                    </li>
                                </ul>
                            </div>

                            <!-- Chat photo -->
                            <div class="col-6 col-xl-6">
                                <div id="chatHeader" class="media text-center text-xl-left">
                                    <div class="avatar avatar-sm d-none d-xl-inline-block mr-5">
                                        <img src="https://animalcharityevaluators.org/wp-content/uploads/2016/09/animals-now-logo-icon-only.png" class="avatar-img" alt="">
                                    </div>

                                    <div class="media-body align-self-center text-truncate">
                                        <h6 class="text-truncate mb-n1">${requestScope.infor2.fullname} ${sessionScope.role==1? "(User)":(sessionScope.role==2? "(User)":(sessionScope.role==3? "(Owner)":"(Staff)"))} ${sessionScope.role!=1? " - Hostel ":""} ${sessionScope.role!=1?(requestScope.hostelId):""}
                                            <c:set var="encodedRoomId" value="${EncodeUtils.encodeString(requestScope.roomId)}" />
                                            <c:set var="encodedHostelId" value="${EncodeUtils.encodeString(requestScope.hostelId)}" />
                                        </h6>
                                        <div id="showRoom"></div>
                                        <button id="read" class="text-uppercase btn-secondary" onclick="showChat()">Connect</button>
                                        <!-- <small class="text-muted">35 members</small>
                                        <small class="text-muted mx-2"> • </small>
                                        <small class="text-muted">HTML, CSS, and Javascript Help</small> -->
                                    </div>
                                </div>
                            </div>



                        </div><!-- .row -->

                    </div>
                </div>
                <!-- Chat: Header -->



                <!-- Chat: Content-->
                <div  class="chat-content px-lg-8" >
                    <div  class="container-xxl py-6 py-lg-10">




                        <!-- Message -->
                        <div id="messages" hidden></div>
                        <!-- Message -->

                    </div>
                    <div class="end-of-chat"></div>

                    <!-- Scroll to end -->
                </div>
                <!-- Chat: Content -->



                <!-- Chat: Footer -->
                <div class="chat-footer border-top py-4 py-lg-6 px-lg-8">
                    <div class="container-xxl">

                        <form hidden id="chat-id-1-form" action="#" data-emoji-form="">
                            <div class="form-row align-items-center">
                                <div class="col">
                                    <div class="input-group">

                                        <!-- Textarea -->
                                        <input id="chat-id-1-input" class="form-control bg-transparent border-0" placeholder="Type your message..." rows="1" data-emoji-input="" data-autosize="true"></input>



                                    </div>

                                </div>

                                <!-- Submit button -->
                                <div class="col-auto">
                                    <button class="btn btn-ico btn-primary rounded-circle" type="submit">
                                        <span class="fe-send"></span>
                                    </button>
                                </div>

                            </div>

                        </form>

                    </div>
                </div>
                <!-- Chat: Footer -->
            </div>
            <!-- Chat: body -->

            <!-- Chat Details -->
            <div id="chat-1-info" class="chat-sidebar">
                <div class="d-flex h-100 flex-column">

                    <!-- Header -->
                    <div class="border-bottom py-4 py-lg-6">
                        <div class="container-fluid">

                            <ul class="nav justify-content-between align-items-center">
                                <!-- Close sidebar -->
                                <li class="nav-item list-inline-item">
                                    <a class="nav-link text-muted px-0" href="#" data-chat-sidebar-close="">
                                        <i class="icon-md fe-chevron-left"></i>
                                    </a>
                                </li>

                                <!-- Title(mobile) -->
                                <li class="text-center d-block d-lg-none">
                                    <h6 class="mb-n2">${sessionScope.role==1||sessionScope.role==2 ?"User":"Owner"} ${sessionScope.role==1||sessionScope.role==2?(sessionScope.renterId):sessionScope.ownerId} ${sessionScope.role!=1? " - Hostel ":""} ${sessionScope.role!=1?(requestScope.hostelId):""}</h6>
                                    <small class="text-muted">Chat Details</small>
                                </li>

                                <!-- Dropdown -->

                            </ul>

                        </div>
                    </div>
                    <!-- Header -->

                    <!-- Body -->
                    <div class="hide-scrollbar flex-fill">

                        <div class="border-bottom text-center py-9 px-10">
                            <!-- Photo -->
                            <div class="avatar avatar-xl mx-5 mb-5">
                                <img class="avatar-img" src="https://animalcharityevaluators.org/wp-content/uploads/2016/09/animals-now-logo-icon-only.pngg" alt="">
                            </div>
                            <h5>other name 2</h5>
                            <p class="text-muted">Bootstrap is an open source toolkit for developing web with HTML, CSS, and JS.</p>
                        </div>

                        <!-- Navs -->
                        <ul class="nav nav-tabs nav-justified bg-light rounded-0" role="tablist">
                            <li class="nav-item">
                                <a href="#chat-id-1-members" class="nav-link active" data-toggle="tab" role="tab" aria-selected="true">Members</a>
                            </li>
                            <li class="nav-item">
                                <a href="#chat-id-1-files" class="nav-link" data-toggle="tab" role="tab">Files</a>
                            </li>
                        </ul>
                        <!-- Navs -->

                        <div class="tab-content">



                        </div>
                    </div>
                    <!-- Body -->

                </div>
            </div>
            <!-- Chat Details -->



            <!-- User's details -->
            <div id="chat-1-user-profile" class="" style="display: none">
                <div class="d-flex h-100 flex-column">

                    <!-- Header -->
                    <div class="border-bottom py-4 py-lg-6">
                        <div class="container-fluid">

                            <ul class="nav justify-content-between align-items-center">
                                <!-- Close sidebar -->
                                <li class="nav-item list-inline-item">
                                    <a class="nav-link text-muted px-0" href="#" data-chat-sidebar-close="" onclick="hideProfileSidebar()">
                                        <i class="icon-md fe-chevron-left"></i>
                                    </a>
                                </li>

                                <!-- Title(mobile) -->
                                <li class="text-center d-block d-lg-none">
                                    <h6 class="mb-n2">User name</h6>
                                    <small class="text-muted">User Details</small>
                                </li>


                            </ul>

                        </div>
                    </div>
                    <!-- Header -->

                    <!-- Body -->
                    <div class="hide-scrollbar flex-fill">

                        <div class="border-bottom text-center py-9 px-10">
                            <!-- Photo -->
                            <div class="avatar avatar-xl mx-5 mb-5">
                                <img class="avatar-img" src="https://animalcharityevaluators.org/wp-content/uploads/2016/09/animals-now-logo-icon-only.png" alt="">
                                <div class="badge badge-sm badge-pill badge-primary badge-border-basic badge-top-right">
                                    <span class="text-uppercase">${sessionScope.role!=1?"Owner":"Renter"}</span>
                                </div>
                            </div>
                            <h5>${requestScope.infor2.fullname}</h5>
                            <p class="text-muted">${requestScope.infor2.sex==1?"male":"female"}</p>
                        </div>

                        <ul class="list-group list-group-flush mb-8">
                            <li class="list-group-item py-6">
                                <div class="media align-items-center">
                                    <div class="media-body">
                                        <p class="small text-muted mb-0">Birthday</p>
                                        <p>${requestScope.infor2.birthday}</p>
                                    </div>
                                    <i class="text-muted icon-sm fe-globe"></i>
                                </div>
                            </li>

                            <li class="list-group-item py-6">
                                <div class="media align-items-center">
                                    <div class="media-body">
                                        <p class="small text-muted mb-0">Phone</p>
                                        <p>${requestScope.infor2.phone}</p>
                                    </div>
                                    <i class="text-muted icon-sm fe-mic"></i>
                                </div>
                            </li>

                            <li class="list-group-item py-6">
                                <div class="media align-items-center">
                                    <div class="media-body">
                                        <p class="small text-muted mb-0">Email</p>
                                        <p>${requestScope.infor2.email}</p>
                                    </div>
                                    <i class="text-muted icon-sm fe-mail"></i>
                                </div>
                            </li>

                            <li class="list-group-item py-6">
                                <div class="media align-items-center">
                                    <div class="media-body">
                                        <p class="small text-muted mb-0">Address</p>
                                        <p>${requestScope.infor2.address}</p>
                                    </div>
                                    <i class="text-muted icon-sm fe-clock"></i>
                                </div>
                            </li>
                        </ul>


                    </div>
                    <!-- Body -->

                    <!-- Button -->


                </div>
            </div>
            <!-- User's details -->

        </div>
        <!-- Chat -->

    </div>
    <!-- Main Content -->



</div>
<!-- Layout -->





<!-- Scripts -->
<script>
    const ownerId = "${sessionScope.ownerId !=null ? sessionScope.ownerId: "null"}";

    const renterId = "${sessionScope.renterId !=null ? sessionScope.renterId: "null"}";
    const role = "${sessionScope.role !=null ? sessionScope.role: "null"}";
    const acc = "${sessionScope.USER !=null ? sessionScope.USER: "null"}";
    const username = "${requestScope.infor.fullname!=null ? requestScope.infor.fullname: "null"}";
    const hostelID = "${requestScope.hostelId !=null ? EncodeUtils.encodeString(requestScope.hostelId): "null"}";
    const roomID = "${requestScope.roomId !=null ? EncodeUtils.encodeString(requestScope.roomId): "null"}";
    const accId = "${sessionScope.USER.accId !=null ? sessionScope.USER.accId: "null"}";


    console.log("accId" + accId);
    console.log("userid : "+renterId);
    console.log("ownerid : "+ownerId);
    console.log("role: "+role)
    console.log("hostelID:"+hostelID);
    console.log("roomId:"+roomID);
</script>
<script src="assets/js/chat/libs/jquery.min.js"></script>
<script src="assets/js/chat/bootstrap/bootstrap.bundle.min.js"></script>

<script src="assets/js/chat/plugins/plugins.bundle.js"></script>
<script src="assets/js/chat/template.js"></script>


<script src="https://www.gstatic.com/firebasejs/8.10.1/firebase-app.js"></script>
<script src="https://www.gstatic.com/firebasejs/8.10.1/firebase-database.js"></script>
<%--<script src="index.js"></script>--%>
<script src="assets/js/chat/chat.js"></script>
<script src="assets/js/sendWebsocket.js"></script>

<script src="./assets/js/loading-handler.js"></script>

<!-- Scripts -->

</body>
<div id="chatContainer">
    <!-- Include chat.jsp content here -->
    <jsp:include page="chat.jsp" />
</div>
</html>