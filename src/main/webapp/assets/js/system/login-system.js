let container = document.getElementById("container");

toggle = () => {
    container.classList.toggle("sign-up");
    container.classList.toggle("sign-in");
};

setTimeout(() => {
    container.classList.add("sign-in");
}, 200);
