<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="./assets/sys-css/toast.css" rel="stylesheet">

</head>
<body class="bg-[#2c3e50] flex items-center justify-center min-h-screen">
    <c:if test="${not empty requestScope.RESPONSE_MSG}">
        <c:choose>
            <c:when test="${sessionScope.RESPONSE_MSG.status}">
                <div id="toast-danger" class="fixed flex items-center w-fit max-w-xs p-4 space-x-4 text-white bg-[#54ac84] divide-x rtl:divide-x-reverse  rounded-lg shadow right-5 bottom-5" role="alert"
                >
            </c:when>
            <c:otherwise>
                <div id="toast-danger" class="fixed flex items-center w-fit max-w-xs p-4 space-x-4 text-white bg-[#c0392b] divide-x rtl:divide-x-reverse  rounded-lg shadow right-5 bottom-5" role="alert"
                >
            </c:otherwise>
        </c:choose>
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

    <div class="w-5/6 sm:w-1/3 bg-white overflow-hidden shadow rounded-lg border">
        <div class="px-4 py-5 sm:px-6">
            <h3 class="text-lg leading-6 font-medium text-gray-900">User Profile</h3>
        </div>
        <form action="update" method="post" id="profile_form" class="border-t border-gray-200 px-4 py-5 sm:p-0">
            <dl class="sm:divide-y sm:divide-gray-200">
                <div class="py-3 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">Full name</dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                        <input name="full_name" type="text" class="profile-input" value="${sessionScope.USER.accountInfo.fullname}" readonly />
                    </dd>
                </div>
                <div class="py-3 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">Email address</dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                        <input type="email" class="profile-input" value="${sessionScope.USER.email}" readonly />
                    </dd>
                </div>
                <div class="py-3 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">Birthday</dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                        <input name="birthday" type="date" class="profile-input" value="${sessionScope.USER.accountInfo.birthday}" readonly />
                    </dd>
                </div>
                <div class="py-3 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">Role</dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                        <c:choose>
                            <c:when test="${sessionScope.USER.role eq 1}">
                                Renter
                            </c:when>
                            <c:when test="${sessionScope.USER.role eq 2}">
                                Owner
                            </c:when>
                        </c:choose>
                    </dd>
                </div>

                <div class="py-3 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">Phone number</dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                        <input name="phone" type="text" class="profile-input" value="${sessionScope.USER.accountInfo.phone}" readonly />
                    </dd>
                </div>
                <div class="py-3 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">CCCD number</dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                        <input name="cccd" type="text" class="profile-input" value="${sessionScope.USER.accountInfo.cccd}" readonly />
                    </dd>
                </div>
                <div class="py-3 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">Address</dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                        <input name="address" type="text" class="profile-input" value="${sessionScope.USER.accountInfo.address}" readonly />
                    </dd>
                </div>
                <button
                        type="button"
                        onclick="unlockInput()"
                        id="editButton"
                        class="sm:m-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                >
                    Edit
                </button>
                <button type="button" id="logoutButton" class="m:m-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
                    Logout
                </button>
                <button type="submit" id="saveButton" class="hidden sm:m-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
                    Save
                </button>
            </dl>
        </form>
        <form id="logoutForm" class="hidden" action="logout" method="post">
        </form>
    </div>
    <script>
        document.getElementById("logoutButton").addEventListener("click", function () {
            document.getElementById("logoutForm").submit();
        });
    </script>
    <script>
        function unlockInput() {
            var inputElements = document.querySelectorAll(".profile-input");
            inputElements.forEach(function (element) {
                element.removeAttribute("readonly");
            });
            document.getElementById("editButton").style.display = "none";
            document.getElementById("saveButton").style.display = "inline-block";
        }

    </script>
    <script src="./assets/js/system/toast.js"></script>
</body>
</html>
