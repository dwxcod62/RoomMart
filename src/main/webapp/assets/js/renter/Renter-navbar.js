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
    var leftPos = iconRect.left - (menuRect.width / 2) + (iconRect.width / 2) - 63; // Di chuyển về bên trái một chút

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

window.onload = function () {
    var maxLength = 25; // Độ dài tối đa cho email
    var elements = document.getElementsByClassName('email');

    for (var i = 0; i < elements.length; i++) {
        var email = elements[i].textContent;
        if (email.length > maxLength) {
            elements[i].textContent = email.substring(0, maxLength) + '...'; // Thêm dấu chấm chấm
        }
    }
};

