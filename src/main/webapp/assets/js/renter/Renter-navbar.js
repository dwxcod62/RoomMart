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
    var settingsIcon = document.querySelector('.settings-icon');
    var iconRect = settingsIcon.getBoundingClientRect();
    var menuRect = userMenu.getBoundingClientRect();

    // Tính toán vị trí cho popup
    var topPos = iconRect.bottom + 15; // Cách icon 15px
    var leftPos = iconRect.left - (menuRect.width / 2) + (iconRect.width / 2) - 43; // Di chuyển về bên trái một chút

    // Đặt vị trí cho popup
    userMenu.style.top = topPos + 'px';
    userMenu.style.left = leftPos + 'px';

    userMenu.classList.add('active');
}

function hideUserMenu() {
    var userMenu = document.getElementById('userMenu');
    userMenu.classList.remove('active');
}
