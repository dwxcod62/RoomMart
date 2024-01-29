var closeButton = document.querySelector('[data-dismiss-target="#toast-danger"]');
var toastDanger = document.getElementById("toast-danger");
closeButton.addEventListener("click", function () {
toastDanger.style.display = "none";
});
