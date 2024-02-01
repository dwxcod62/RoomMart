<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Firebase RealTime Chat</title>
    <link rel="stylesheet" href="assets/scss/chat_style/chat.css">
</head>
<body>
<header>
    <h2>Firebase RealTime Chat</h2>
    <button id="read" onclick="showChat()" hidden>unread</button>
</header>

<div id="chat" style="display: none;">
    <!-- messages will display here -->
    <ul id="messages"></ul>

    <!-- form to send message -->
    <form id="message-form">
        <input id="message-input" type="text" />
        <button id="message-btn" type="submit">Send</button>
    </form>
</div>


<script>
    const userId = "${sessionScope.ownerId}";

    const ownerId = "${sessionScope.renterId}";
</script>
<!-- scripts -->
<script src="https://www.gstatic.com/firebasejs/8.10.1/firebase-app.js"></script>
<script src="https://www.gstatic.com/firebasejs/8.10.1/firebase-database.js"></script>
<script src="assets/js/chat/chat.js"></script>
</body>
</html>
