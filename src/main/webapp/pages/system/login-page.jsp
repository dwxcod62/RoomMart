<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>RoomMart - Login</title>

    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
    <link href="./assets/sys-css/login-style.css" rel="stylesheet">

    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
    <div id="container" class="container">
        <!-- FORM SECTION -->
        <div class="row">
            <!-- SIGN UP -->
            <div class="col align-items-center flex-col sign-up">
                <div class="form-wrapper align-items-center hidden">
                    <div class="form sign-up">
                        <div class="input-group">
                            <i class="bx bxs-user"></i>
                            <input type="text" placeholder="Full Name" />
                        </div>
                        <div class="input-group">
                            <i class="bx bxs-phone-call"></i>
                            <input type="text" placeholder="Phone number" />
                        </div>
                        <div class="input-group">
                            <i class="bx bxs-id-card"></i>
                            <input type="text" placeholder="CCCD/CMND" />
                        </div>
                        <div class="input-group">
                            <i class="bx bxs-lock-alt"></i>
                            <input type="password" placeholder="Password" />
                        </div>
                        <div class="input-group">
                            <i class="bx bxs-lock-alt"></i>
                            <input type="password" placeholder="Confirm password" />
                        </div>
                        <p>
                            <span> Already have an account? </span>
                            <b onclick="toggle()" class="pointer"> Sign in here </b>
                        </p>
                    </div>
                </div>
                <!-- Input Email -->
                <form action="res" method="post" class="form-wrapper align-items-center">
                    <div class="form sign-up">
                        <div class="input-group">
                            <i class="bx bxs-user"></i>
                            <input name="res_mail" type="text" placeholder="Your email!" />
                        </div>
                    </div>
                </form>
            </div>
            <!-- END SIGN UP -->
            <!-- SIGN IN -->
            <div class="col align-items-center flex-col sign-in">
                <form action="log" method="POST" class="form-wrapper align-items-center">
                    <div class="form sign-in">
                        <div class="input-group">
                            <i class="bx bxs-user"></i>
                            <input name="username" type="text" placeholder="Username" />
                        </div>
                        <div class="input-group">
                            <i class="bx bxs-lock-alt"></i>
                            <input name="password" type="password" placeholder="Password" />
                        </div>
                        <div class="mb-4 flex items-center">
                            <input  type="checkbox" id="remember" name="remember" class="text-blue-500" />
                            <label for="remember" class="text-gray-600 ml-2">Remember Me</label>
                        </div>
                        <button>Sign in</button>
                        <p>
                            <b> Forgot password? </b>
                        </p>
                        <p>
                            <span> Don't have an account? </span>
                            <b onclick="toggle()" class="pointer"> Sign up here </b>
                        </p>
                    </div>
                </form>
                <div class="form-wrapper"></div>
            </div>
            <!-- END SIGN IN -->
        </div>
        <!-- END FORM SECTION -->
        <!-- CONTENT SECTION -->
        <div class="row content-row">
            <!-- SIGN IN CONTENT -->
            <div class="col align-items-center flex-col">
                <div class="text sign-in">
                    <h2>Welcome</h2>
                </div>
                <div class="img sign-in"></div>
            </div>
            <!-- END SIGN IN CONTENT -->
            <!-- SIGN UP CONTENT -->
            <div class="col align-items-center flex-col">
                <div class="img sign-up"></div>
                <div class="text sign-up">
                    <h2>Join with us</h2>
                </div>
            </div>
            <!-- END SIGN UP CONTENT -->
        </div>
        <!-- END CONTENT SECTION -->
    </div>

    <div
            id="toast-danger"
            class="fixed flex items-center w-full max-w-xs p-4 space-x-4 text-white bg-[#54ac84] divide-x rtl:divide-x-reverse  rounded-lg shadow right-5 bottom-5"
            role="alert"
    >
        <div class="ms-3 text-white text-xl font-semibold">${requestScope.RESPONSE_MSG.content}</div>
        <button
                type="button"
                class="ms-auto -mx-1.5 -my-1.5 bg-white text-gray-400 hover:text-gray-900 rounded-lg focus:ring-2 focus:ring-gray-300 p-1.5 hover:bg-gray-100 inline-flex items-center justify-center h-8 w-8"
                data-dismiss-target="#toast-danger"
                aria-label="Close"
        >
            <span class="sr-only">Close</span>
            <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                <path
                        stroke="currentColor"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"
                />
            </svg>
        </button>
    </div>

    <script src="./assets/js/system/login-system.js"></script>
    <script>
        console.log("${requestScope.RESPONSE_MSG}");
        var closeButton = document.querySelector('[data-dismiss-target="#toast-danger"]');
        var toastDanger = document.getElementById("toast-danger");
        <c:choose>
            <c:when test="${requestScope.RESPONSE_MSG.status eq false}">
                    toastDanger.style.display = "flex";
            </c:when>
        </c:choose>

        closeButton.addEventListener("click", function () {
            toastDanger.style.display = "none";
        });
    </script>
</body>
</html>
