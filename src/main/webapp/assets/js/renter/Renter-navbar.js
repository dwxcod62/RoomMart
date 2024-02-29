function goToHomePage() {
    window.location.href = "Renter-HomePage"; // Chuyển hướng trang về Renter-HomePage
}

function toggleUserMenu() {
    var userMenu = document.getElementById('userMenu');
    if (userMenu.classList.contains('active')) {
        hideUserMenu();
    } else {
        showUserMenu();
    }
}

function showUserMenu() {
    var userMenu = document.getElementById('userMenu');
    var userIcon = document.querySelector('.user-icon');
    var iconRect = userIcon.getBoundingClientRect();
    var menuRect = userMenu.getBoundingClientRect();

    // Tính toán vị trí cho popup
    var topPos = iconRect.bottom + 15; // Cách icon 15px
    var leftPos = iconRect.left - (menuRect.width / 2) + (iconRect.width / 2) - 60; // Di chuyển về bên trái một chút

    // Đặt vị trí cho popup
    userMenu.style.top = topPos + 'px';
    userMenu.style.left = leftPos + 'px';

    userMenu.classList.add('active');
}

function hideUserMenu(event) {
    event.preventDefault();

    var userMenu = document.getElementById('userMenu');
    userMenu.classList.remove('active');
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
            return parts[0] + " " + parts[parts.length - 1];
        } else {
            // Nếu chỉ có một từ, trả về chuỗi ban đầu
            return fullName;
        }
    }

    // Số ký tự tối đa bạn muốn hiển thị trước khi cắt
    var maxLength = 20;

    // Kiểm tra và cắt chuỗi nếu cần
    shortenAndAddTooltip(emailElement, maxLength);

    if (nameElement) {
        var fullName = nameElement.textContent.trim();
        var firstName = getFirstName(fullName);
        nameElement.textContent = firstName;
        nameElement.setAttribute("title", fullName); // Thêm title để hiển thị tên đầy đủ khi di chuột vào
    }
});


