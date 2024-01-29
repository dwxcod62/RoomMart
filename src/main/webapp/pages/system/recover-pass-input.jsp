<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>OTP</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="./assets/sys-css/toast.css" rel="stylesheet">
</head>
<body class="bg-[#2c3e50]">
    <c:if test="${not empty requestScope.RESPONSE_MSG}">
        <div id="toast-danger" class="fixed flex items-center w-fit max-w-xs p-4 space-x-4 text-white bg-[#54ac84] divide-x rtl:divide-x-reverse  rounded-lg shadow right-5 bottom-5" role="alert"
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
                            d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"></path>
                </svg>
            </button>
        </div>
    </c:if>

    <div class="min-h-screen bg-gray-50 flex flex-col justify-center py-12 sm:px-6 lg:px-8">
        <div class="sm:mx-auto sm:w-full sm:max-w-md">
            <img class="mx-auto h-10 w-auto" src="https://www.svgrepo.com/show/301692/login.svg" alt="Workflow" />
            <h2 class="mt-6 text-center text-3xl leading-9 font-extrabold text-gray-900">Recover your password</h2>
        </div>

        <div class="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
            <div class="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
                <form id="rcvForm" method="POST" action="password-reset">
                    <input class="hidden" name="code" value="${requestScope.RECOVER_CODE}">
                    <div class="mt-6">
                        <label for="password" class="block text-sm font-medium leading-5 text-gray-700"> Password </label>
                        <div class="mt-1 rounded-md shadow-sm">
                            <input
                                id="password"
                                name="password"
                                type="password"
                                required=""
                                class="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md placeholder-gray-400 focus:outline-none focus:shadow-outline-blue focus:border-blue-300 transition duration-150 ease-in-out sm:text-sm sm:leading-5"
                            />
                        </div>
                    </div>

                    <div class="mt-6">
                        <label for="password_confirmation" class="block text-sm font-medium leading-5 text-gray-700"> Confirm Password </label>
                        <div class="mt-1 rounded-md shadow-sm">
                            <input
                                id="password_confirmation"
                                name="password_confirmation"
                                type="password"
                                required=""
                                class="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md placeholder-gray-400 focus:outline-none focus:shadow-outline-blue focus:border-blue-300 transition duration-150 ease-in-out sm:text-sm sm:leading-5"
                            />
                        </div>
                    </div>

                    <p class="hidden text-sm text-red-600 mt-2" id="pass-match-error">Passwords do not match</p>

                    <div class="mt-6">
                        <span class="block w-full rounded-md shadow-sm">
                            <button
                                    type="button"
                                    id="verifyButton"
                                    class="w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-500 focus:outline-none focus:border-indigo-700 focus:shadow-outline-indigo active:bg-indigo-700 transition duration-150 ease-in-out"
                            >
                                Done
                            </button>
                        </span>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        document.getElementById("verifyButton").addEventListener("click", function () {
            var password = document.getElementById("password").value;
            var confirmPassword = document.getElementById("password_confirmation").value;

            if (password === confirmPassword) {
                document.getElementById("rcvForm").submit();
            } else {
                var errorElement = document.getElementById("pass-match-error");
                errorElement.style.display = "block";
            }
        });
    </script>
    <script src="./assets/js/system/toast.js"></script>
</body>
</html>
