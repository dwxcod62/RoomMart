function showSentReports() {
    document.getElementById('list-notifications-container').style.display = 'block';
    document.querySelector('.send_report').style.display = 'none';
    document.querySelector('.tabs-item.active-tab')?.classList.remove('active-tab'); // Sử dụng "?." để xử lý trường hợp không có class "active-tab"
    document.querySelector('.tabs-item:nth-child(1)').classList.add('active-tab');
}

function showSendReportForm() {
    document.getElementById('list-notifications-container').style.display = 'none';
    document.querySelector('.send_report').style.display = 'block';
    document.querySelector('.tabs-item.active-tab')?.classList.remove('active-tab'); // Sử dụng "?." để xử lý trường hợp không có class "active-tab"
    document.querySelector('.tabs-item:nth-child(2)').classList.add('active-tab');
}


