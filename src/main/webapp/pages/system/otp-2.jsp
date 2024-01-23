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

    <div class="bg-white max-w-md mx-auto border max-w-sm mt-20 rounded">
        <form action="otp" method="post" class="shadow-md px-4 py-6">
            <input class="hidden" name="token" value="${param.token}">
            <input class="hidden" name="act" value="page">
            <input class="hidden" name="mail" value="${param.mail}">
            <div class="flex flex-col items-center justify-center text-center space-y-2 mb-6">
                <div class="font-semibold text-2xl text-teal-500">
                    <p>Email Verification</p>
                </div>
                <div class="flex flex-row text-sm font-medium text-gray-400">
                    <p>We have sent a code to your email ${param.mail}</p>
                </div>
            </div>
            <div class="flex justify-center gap-2 mb-6">
                <input name="otp1" type="text" class="w-12 h-12 text-center border rounded-md shadow-sm focus:border-teal-500 focus:ring-teal-500" type="text" maxlength="1" pattern="[0-9]" inputmode="numeric" autocomplete="one-time-code" required>
                <input name="otp2" type="text" class="w-12 h-12 text-center border rounded-md shadow-sm focus:border-teal-500 focus:ring-teal-500" type="text" maxlength="1" pattern="[0-9]" inputmode="numeric" autocomplete="one-time-code" required>
                <input name="otp3" type="text" class="w-12 h-12 text-center border rounded-md shadow-sm focus:border-teal-500 focus:ring-teal-500" type="text" maxlength="1" pattern="[0-9]" inputmode="numeric" autocomplete="one-time-code" required>
                <input name="otp4" type="text" class="w-12 h-12 text-center border rounded-md shadow-sm focus:border-teal-500 focus:ring-teal-500" type="text" maxlength="1" pattern="[0-9]" inputmode="numeric" autocomplete="one-time-code" required>
            </div>
            <div class="flex items-center justify-center">
                <button
                    class="bg-teal-500 hover:bg-teal-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" type="submit">
                    Verify
                </button>
                <a class="inline-block align-baseline font-bold text-sm text-teal-500 hover:text-teal-800 ml-4" href="#">
                    Resend OTP
                </a>
            </div>
        </form>
    </div>
    <script>
        document.getElementById("verifyButton").addEventListener("click", function () {
            document.getElementById("otpForm").submit();
        });
    </script>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const digitInputs = document.querySelectorAll('input[type="text"]');
            digitInputs.forEach(function (input, index) {
                input.addEventListener("input", function () {
                    if (this.value.length === 1 && index < digitInputs.length - 1) {
                        digitInputs[index + 1].focus();
                    }
                });
            });

            digitInputs.forEach(function (input, index) {
                input.addEventListener("paste", function (e) {
                    e.preventDefault();
                    const pasteData = e.clipboardData.getData("text").trim().substring(0, 4);
                    for (let i = 0; i < Math.min(pasteData.length, digitInputs.length); i++) {
                        digitInputs[index + i].value = pasteData[i];
                    }
                });
            });
        });
    </script>
    <script src="./assets/js/system/toast.js"></script>
</body>
</html>
