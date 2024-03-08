<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <title>Góp ý</title>
    <link rel="icon" href="./assets/images/favicon/favicon.png" type="image/x-icon"/>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
          integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <link rel="stylesheet" href="./assets/css/core_style/core.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <link rel="stylesheet" href="./assets/css/renter_page/Renter-response.css">

    <!-- New-->
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
<div>
    <%@include file="component/navbar.jsp" %>
    <div class="row main-body">
        <%@include file="component/sidebar.jsp" %>
        <div class="content">
            <div class="col-6 container">
                <form action="RenterSendPropose" method="post" id="feedbackForm">
                    <h1>Góp ý tới hệ thống</h1>
                    <textarea name="form-input" id="feedbackTextarea"
                              placeholder="Vui lòng nhập nội dung..." onkeyup="checkContent()"></textarea>
                    <button type="button" class="btn btn-outline-danger btn-lg mt-2 form-submit" onclick="confirmSubmit()" id="submitButton" disabled>Gửi</button>
                </form>
            </div>
        </div>
    </div>
</div>
<%@include file="component/footer.jsp" %>

<script src="./assets/js/renter/Renter-navbar.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap5.min.js"></script>

<script>
    function confirmSubmit() {
        var feedback = document.getElementById("feedbackTextarea").value;
        if (confirm("Bạn có chắc chắn muốn gửi phản hồi này không?")) {
            document.getElementById("feedbackForm").submit();
        }
    }

    function checkContent() {
        var feedback = document.getElementById("feedbackTextarea").value;
        var submitButton = document.getElementById("submitButton");
        if (feedback.trim() === "") {
            submitButton.disabled = true;
        } else {
            submitButton.disabled = false;
        }
    }

    <% if (request.getAttribute("successMessage") != null) { %>
    alert('<%= request.getAttribute("successMessage") %>');
    <% } else if (request.getAttribute("errorMessage") != null) { %>
    alert('<%= request.getAttribute("errorMessage") %>');
    <% } %>
</script>
</body>

</html>
