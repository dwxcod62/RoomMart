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
        <div class="bg-white border rounded-lg px-8 py-6 mx-auto my-8 max-w-2xl">
            <h2 class="text-2xl font-medium mb-4">Infomation</h2>
            <form action="resinf" method="post">
                <input class="hidden" name="email" value="${requestScope.EMAIL}">
                <div class="grid grid-cols-3 gap-2">
                    <div class="mb-4 col-span-2">
                        <label class="block text-gray-700 font-medium mb-2">Name</label>
                        <input
                                type="text"
                                name="name"
                                class="col-span-2 border border-gray-400 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400"
                                required
                        />
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
                            <option selected  value="other">Banana</option>
                        </select>
                    </div>
                </div>

                <div class="grid grid-cols-3 gap-2">
                    <div class="mb-4 col-span-2">
                        <label  class="block text-gray-700 font-medium mb-2">Address</label>
                        <input
                                type="text"
                                name="address"
                                class="col-span-2 border border-gray-400 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400"
                                required
                        />
                    </div>
                    <div class="mb-4">
                        <label for="birthdate" class="block text-gray-700 font-medium mb-2">Birthday</label>
                        <input
                                type="date"
                                id="birthdate"
                                name="birthdate"
                                class="border border-gray-400 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400"
                                required
                        />
                    </div>
                </div>

                <div class="grid grid-cols-3 gap-2">
                    <div class="mb-4">
                        <label  class="block text-gray-700 font-medium mb-2">Số điện thoại</label>
                        <input
                                type="text"
                                name="phone"
                                class="col-span-2 border border-gray-400 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400"
                                required
                        />
                    </div>
                    <div class="mb-4">
                        <label class="block text-gray-700 font-medium mb-2">CCCD/CMND</label>
                        <input
                                type="text"
                                name="identify"
                                class="col-span-2 border border-gray-400 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400"
                                required
                        />
                    </div>
                    <div class="mb-4">
                        <label class="block text-gray-700 font-medium mb-2">Password</label>
                        <input
                                type="password"
                                name="password"
                                class="col-span-2 border border-gray-400 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400"
                        />
                    </div>
                </div>
                <div class="mb-4">
                    <label for="gender" class="block text-gray-700 font-medium mb-2">Role ( Delete later )</label>
                    <select
                            id="role"
                            name="role"
                            class="border border-gray-400 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400"
                            required
                    >
                        <option value="0">Admin</option>
                        <option value="1">Owner</option>
                        <option value="2">Staff</option>
                        <option selected  value="3">Renter</option>
                    </select>
                </div>

                <div>
                    <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600">Submit</button>
                </div>
            </form>
        </div>
    </c:otherwise>
</c:choose>
</body>
</html>
