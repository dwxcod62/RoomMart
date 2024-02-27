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
    <div class="min-h-screen bg-gray-50 flex flex-col justify-center py-12 sm:px-6 lg:px-8 px-6">
        <div class="sm:mx-auto sm:w-full sm:max-w-md">
            <img class="mx-auto h-10 w-auto" src="https://www.svgrepo.com/show/301692/login.svg" alt="Workflow" />
            <h2 class="mt-6 text-center text-3xl leading-9 font-extrabold text-gray-900">Sign in to your account</h2>
            <p class="mt-2 text-center text-sm leading-5 text-blue-500 max-w">
                Or
                <a
                        href="register"
                        class="font-medium text-blue-500 hover:text-blue-500 focus:outline-none focus:underline transition ease-in-out duration-150"
                >
                    create a new acccount
                </a>
            </p>
        </div>

        <div class="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
            <div class="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
                <form action="log" method="post">
                    <div>
                        <label for="email" class="block text-sm font-medium leading-5 text-gray-700">Email address</label>
                        <div class="mt-1 relative rounded-md shadow-sm">
                            <input
                                    id="email"
                                    name="email"
                                    placeholder="user@example.com"
                                    type="email"
                                    class="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md placeholder-gray-400 focus:outline-none focus:shadow-outline-blue focus:border-blue-300 transition duration-150 ease-in-out sm:text-sm sm:leading-5"
                            />
                        </div>
                    </div>

                    <div class="mt-6">
                        <label for="password" class="block text-sm font-medium leading-5 text-gray-700">Password</label>
                        <div class="mt-1 rounded-md shadow-sm">
                            <input
                                    id="password"
                                    name="pass"
                                    type="password"
                                    class="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md placeholder-gray-400 focus:outline-none focus:shadow-outline-blue focus:border-blue-300 transition duration-150 ease-in-out sm:text-sm sm:leading-5"
                            />
                        </div>
                    </div>

                    <div class="mt-6 flex items-center justify-between">
                        <div class="flex items-center">
                            <input
                                    id="remember_me"
                                    name="remember"
                                    type="checkbox"
                                    value="1"
                                    class="form-checkbox h-4 w-4 text-indigo-600 transition duration-150 ease-in-out"
                            />
                            <label for="remember_me" class="ml-2 block text-sm leading-5 text-gray-900">Remember me</label>
                        </div>

                        <div class="text-sm leading-5">
                            <a
                                    href="forgot-password-page"
                                    class="font-medium text-blue-500 hover:text-blue-500 focus:outline-none focus:underline transition ease-in-out duration-150"
                            >
                                Forgot your password?
                            </a>
                        </div>
                    </div>

                    <div class="mt-6">
                        <span class="block w-full rounded-md shadow-sm">
                            <button
                                    type="submit"
                                    class="w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-blue-500 hover:bg-blue-500 focus:outline-none focus:border-indigo-700 focus:shadow-outline-indigo active:bg-indigo-700 transition duration-150 ease-in-out"
                            >
                                Sign in
                            </button>
                        </span>
                    </div>
                </form>
            </div>
        </div>
    </div>



    <c:if test="${not empty requestScope.RESPONSE_MSG and requestScope.RESPONSE_MSG.status eq false}">
        <div id="toast-danger" class="fixed flex items-center w-full max-w-xs p-4 space-x-4 text-white bg-[#54ac84] divide-x rtl:divide-x-reverse  rounded-lg shadow right-5 bottom-5" role="alert"
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

    <c:if test="${not empty requestScope.RESPONSE_MSG and requestScope.RESPONSE_MSG.status eq true}">
        <div class="fixed z-10 inset-0 overflow-y-auto">
            <div class="flex items-center justify-center min-h-screen px-4 pt-4 pb-20 text-center sm:block sm:p-0">
                <div class="fixed inset-0 transition-opacity">
                    <div class="absolute inset-0 bg-gray-500 opacity-75"></div>
                </div>
                <span class="hidden sm:inline-block sm:align-middle sm:h-screen"></span>
                <div
                        class="inline-block align-bottom bg-white rounded-lg px-4 pt-5 pb-4 text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full sm:p-6 animate__animated animate__fadeInUp"
                >
                    <div class="sm:flex sm:items-start">
                        <div
                                class="mx-auto flex-shrink-0 flex items-center justify-center h-12 w-12 rounded-full bg-green-100 sm:mx-0 sm:h-10 sm:w-10"
                        >
                            <svg class="h-6 w-6 text-green-600" stroke="currentColor" fill="none" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
                            </svg>
                        </div>
                        <div class="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                            <h3 class="text-lg leading-6 font-medium text-gray-900">Notification</h3>
                            <div class="mt-2">
                                <p class="text-sm leading-5 text-gray-500">
                                        ${requestScope.RESPONSE_MSG.content}
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="mt-5 sm:mt-4 sm:flex sm:flex-row-reverse">
                        <span class="flex w-full rounded-md shadow-sm sm:ml-3 sm:w-auto">
                            <button
                                    id="acceptButton"
                                    type="button"
                                    class="inline-flex justify-center w-full rounded-md border border-transparent px-4 py-2 bg-green-600 text-base leading-6 font-medium text-white shadow-sm hover:bg-green-500 focus:outline-none focus:shadow-outline-green transition ease-in-out duration-150 sm:text-sm sm:leading-5"
                            >
                                Accept
                            </button>
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </c:if>


    <script src="./assets/js/system/login-system.js"></script>
    <script>
        document.getElementById("acceptButton").addEventListener("click", function () {
            document.querySelector(".fixed").style.display = "none";
        });
    </script>
</body>
</html>
