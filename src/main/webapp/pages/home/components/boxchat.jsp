<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chat</title>
    <link rel="stylesheet" href="assets/css/chat_style/template.min.css">
    <!-- Add any additional CSS for styling the chat container div -->
    <style>
        /* Style for the container div */
        #chatContainer {
            position: absolute;
            bottom: 0;
            right: 20px; /* Adjust as needed */
            width: 300px; /* Adjust width as needed */
            height: auto; /* Adjust height as needed */
            max-height: 80vh; /* Adjust max-height as needed */
            background-color: #fff; /* Adjust background color as needed */
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Add shadow for better visibility */
            border-radius: 8px; /* Add border radius for better appearance */
            overflow-y: auto; /* Enable vertical scrolling if content exceeds container height */
            z-index: 9999; /* Ensure it appears above other content */
        }
        /* Adjustments for smaller screens */
        @media (max-width: 576px) {
            /* Add any specific styles for smaller screens here */
            #chatContainer {
                width: 100%; /* Adjust width for smaller screens */
                left: 0;
                right: 0;
                border-radius: 0; /* Remove border radius for full width */
            }
        }
    </style>
</head>
<body>

<div id="chatContainer">
    <!-- Include chat.jsp content here -->
    <jsp:include page="../chat.jsp" />
</div>

<!-- Scripts -->
<script src="assets/js/chat/libs/jquery.min.js"></script>
<script src="assets/js/chat/bootstrap/bootstrap.bundle.min.js"></script>
<script src="assets/js/chat/plugins/plugins.bundle.js"></script>
<script src="assets/js/chat/template.js"></script>
<script src="https://www.gstatic.com/firebasejs/8.10.1/firebase-app.js"></script>
<script src="https://www.gstatic.com/firebasejs/8.10.1/firebase-database.js"></script>
<script src="assets/js/chat/chat.js"></script>
<script src="assets/js/sendWebsocket.js"></script>
<script src="./assets/js/loading-handler.js"></script>
<!-- Scripts -->

</body>
</html>
