<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Register</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="./assets/sys-css/toast.css" rel="stylesheet">

</head>
<body class="bg-[#2c3e50]">
    <c:if test="${not empty requestScope.RESPONSE_MSG}">
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

    <main id="content" role="main" class="w-full max-w-md mx-auto p-6">
      <div class="mt-7 bg-white rounded-xl shadow-lg border-2 border-indigo-300">
        <div class="p-4 sm:p-7">
          <div class="text-center">
            <h1 class="block text-2xl font-bold text-gray-800">Register?</h1>
          </div>

          <div class="mt-5">
            <form action="res" method="post">
              <div class="grid gap-y-4">
                <div>
                  <label for="email" class="block text-sm font-bold ml-1 mb-2">Email address</label>
                  <div class="relative">
                    <input
                            type="email"
                            id="email"
                            name="email"
                            class="py-3 px-4 block w-full border-2 border-gray-200 rounded-md text-sm focus:border-blue-500 focus:ring-blue-500 shadow-sm"
                            required
                            aria-describedby="email-error"
                    />
                  </div>
                  <p class="hidden text-xs text-red-600 mt-2" id="email-error">
                    Please include a valid email address so we can get back to you
                  </p>
                </div>
                <button
                        type="submit"
                        class="py-3 px-4 inline-flex justify-center items-center gap-2 rounded-md border border-transparent font-semibold bg-blue-500 text-white hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-all text-sm dark:focus:ring-offset-gray-800"
                >
                  Send
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </main>
    <script src="./assets/js/system/toast.js"></script>
</body>
</html>
