function goToHomePage() {
    window.location.href = "RenterHome"; // Chuyển hướng trang về Renter-HomePage
}

document.addEventListener("DOMContentLoaded", function() {
    var nameElement = document.querySelector(".name");
    var emailElement = document.querySelector(".email");

    // Hàm cắt chuỗi và thêm title
    function shortenAndAddTooltip(element, maxLength) {
        var text = element.textContent.trim();
        if (text.length > maxLength) {
            element.setAttribute("title", text);
            element.textContent = text.slice(0, maxLength) + "...";
        }
    }

    function getFirstName(fullName) {
        var parts = fullName.split(" "); // Tách chuỗi thành mảng các từ
        if (parts.length > 1) {
            // Nếu có ít nhất 2 từ, lấy từ đầu tiên và từ cuối cùng
            return parts[parts.length - 1]  + " " + parts[0];
        } else {
            // Nếu chỉ có một từ, trả về chuỗi ban đầu
            return fullName;
        }
    }

    // Số ký tự tối đa bạn muốn hiển thị trước khi cắt
    var maxLength = 22;

    // Kiểm tra và cắt chuỗi nếu cần
    shortenAndAddTooltip(emailElement, maxLength);

    if (nameElement) {
        var fullName = nameElement.textContent.trim();
        var firstName = getFirstName(fullName);
        nameElement.textContent = firstName;
        nameElement.setAttribute("title", fullName); // Thêm title để hiển thị tên đầy đủ khi di chuột vào
    }
});



