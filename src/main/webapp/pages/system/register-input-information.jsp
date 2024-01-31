<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
<c:choose>
    <c:when test="${empty requestScope.EMAIL}">
        <% response.sendRedirect("register"); %>
    </c:when>
    <c:otherwise>
        <div class="min-h-screen bg-gray-50 flex flex-col justify-center py-12 sm:px-6 lg:px-8">
            <div class="sm:mx-auto sm:w-full sm:max-w-md">
                <img class="mx-auto h-10 w-auto" src="https://www.svgrepo.com/show/301692/login.svg" alt="Workflow" />
                <h2 class="mt-6 text-center text-3xl leading-9 font-extrabold text-gray-900">Create a new account</h2>
                <p class="mt-2 text-center text-sm leading-5 text-gray-500 max-w">
                    Or
                    <a
                            href="#"
                            class="font-medium text-blue-600 hover:text-blue-500 focus:outline-none focus:underline transition ease-in-out duration-150"
                    >
                        login to your account
                    </a>
                </p>
            </div>

            <div class="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
                <div class="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
                    <form method="POST" action="reg-info" novalidate>
                        <div>
                            <label class="block text-sm font-medium leading-5 text-gray-700">Name</label>
                            <div class="mt-1 relative rounded-md shadow-sm">
                                <input
                                        id="name"
                                        name="name"
                                        placeholder="John Doe"
                                        type="text"
                                        class="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md placeholder-gray-400 focus:outline-none focus:shadow-outline-blue focus:border-blue-300 transition duration-150 ease-in-out sm:text-sm sm:leading-5"
                                />
                            </div>
                            <span id="error-name" class="text-[#d63031]"></span>
                        </div>

                        <div class="mt-6 grid grid-cols-2 gap-2">
                            <div class="mb-4">
                                <label for="birthdate" class="block text-gray-700 font-medium mb-2">Birthday</label>
                                <input
                                        type="date"
                                        id="birthdate"
                                        name="birthdate"
                                        class="border border-gray-400 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400"
                                        required
                                />
                                <span id="error-birthdate" class="text-[#d63031]"></span>
                            </div>
                            <div class="mb-4">
                                <label for="gender" class="block text-gray-700 font-medium mb-2">Gender</label>
                                <select
                                        id="gender"
                                        name="gender"
                                        class="border border-gray-400 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400"
                                        required
                                >
                                    <option value="male">Male</option>
                                    <option value="female">Female</option>
                                    <option selected value="other">Banana</option>
                                </select>
                                <span id="error-gender" class="text-[#d63031]"></span>
                            </div>
                        </div>

                        <div class="mt-6 grid grid-cols-2 gap-2">
                            <div>
                                <label class="block text-sm font-medium leading-5 text-gray-700"> Phone-number </label>
                                <div class="mt-1 rounded-md shadow-sm">
                                    <input
                                            id="phone-number"
                                            name="phone-number"
                                            type="text"
                                            required=""
                                            class="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md placeholder-gray-400 focus:outline-none focus:shadow-outline-blue focus:border-blue-300 transition duration-150 ease-in-out sm:text-sm sm:leading-5"
                                    />
                                </div>
                                <span id="error-phone-number" class="text-[#d63031]"></span>
                            </div>
                            <div>
                                <label class="block text-sm font-medium leading-5 text-gray-700"> CCCD/CMND </label>
                                <div class="mt-1 rounded-md shadow-sm">
                                    <input
                                            id="cccd"
                                            name="cccd"
                                            type="text"
                                            required=""
                                            class="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md placeholder-gray-400 focus:outline-none focus:shadow-outline-blue focus:border-blue-300 transition duration-150 ease-in-out sm:text-sm sm:leading-5"
                                    />
                                </div>
                                <span id="error-cccd" class="text-[#d63031]"></span>

                            </div>
                        </div>

                        <div class="mt-6">
                            <label class="block text-sm font-medium leading-5 text-gray-700"> Password </label>
                            <div class="mt-1 rounded-md shadow-sm">
                                <input
                                        id="password"
                                        name="password"
                                        type="password"
                                        required=""
                                        class="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md placeholder-gray-400 focus:outline-none focus:shadow-outline-blue focus:border-blue-300 transition duration-150 ease-in-out sm:text-sm sm:leading-5"
                                />
                            </div>
                            <span id="error-password" class="text-[#d63031]"></span>
                        </div>

                        <div class="mt-6">
                            <label  class="block text-sm font-medium leading-5 text-gray-700"> Confirm Password </label>
                            <div class="mt-1 rounded-md shadow-sm">
                                <input
                                        id="confirm-password"
                                        name="password_confirmation"
                                        type="password"
                                        required=""
                                        class="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md placeholder-gray-400 focus:outline-none focus:shadow-outline-blue focus:border-blue-300 transition duration-150 ease-in-out sm:text-sm sm:leading-5"
                                />
                            </div>
                            <span id="error-confirm-password" class="text-[#d63031]"></span>
                        </div>

                        <div class="mt-6">
                            <span class="block w-full rounded-md shadow-sm">
                                <button
                                        type="submit"
                                        class="w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-500 focus:outline-none focus:border-indigo-700 focus:shadow-outline-indigo active:bg-indigo-700 transition duration-150 ease-in-out"
                                >
                                    Create account
                                </button>
                            </span>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>

<script
        src="https://code.jquery.com/jquery-3.7.1.js"
        integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
        crossorigin="anonymous"
></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/validate.js/0.13.1/validate.min.js"></script>
<script src="./assets/js/validate-form-duc.js"></script>

</body>
</html>
