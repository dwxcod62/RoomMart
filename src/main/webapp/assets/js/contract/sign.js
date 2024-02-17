(function () {
    window.requestAnimFrame = (function (callback) {
        return (
            window.requestAnimationFrame ||
            window.webkitRequestAnimationFrame ||
            window.mozRequestAnimationFrame ||
            window.oRequestAnimationFrame ||
            window.msRequestAnimaitonFrame ||
            function (callback) {
                window.setTimeout(callback, 1000 / 60);
            }
        );
    })();

    var canvas = document.getElementById("sig-canvas");
    var ctx = canvas.getContext("2d");
    ctx.strokeStyle = "#222222";
    ctx.lineWidth = 4;

    var drawing = false;
    var mousePos = {
        x: 0,
        y: 0,
    };
    var lastPos = mousePos;

    canvas.addEventListener(
        "mousedown",
        function (e) {
            drawing = true;
            lastPos = getMousePos(canvas, e);
        },
        false
    );

    canvas.addEventListener(
        "mouseup",
        function (e) {
            drawing = false;
        },
        false
    );

    canvas.addEventListener(
        "mousemove",
        function (e) {
            mousePos = getMousePos(canvas, e);
        },
        false
    );

    // Add touch event support for mobile
    canvas.addEventListener("touchstart", function (e) {}, false);

    canvas.addEventListener(
        "touchmove",
        function (e) {
            var touch = e.touches[0];
            var me = new MouseEvent("mousemove", {
                clientX: touch.clientX,
                clientY: touch.clientY,
            });
            canvas.dispatchEvent(me);
        },
        false
    );

    canvas.addEventListener(
        "touchstart",
        function (e) {
            mousePos = getTouchPos(canvas, e);
            var touch = e.touches[0];
            var me = new MouseEvent("mousedown", {
                clientX: touch.clientX,
                clientY: touch.clientY,
            });
            canvas.dispatchEvent(me);
        },
        false
    );

    canvas.addEventListener(
        "touchend",
        function (e) {
            var me = new MouseEvent("mouseup", {});
            canvas.dispatchEvent(me);
        },
        false
    );

    function getMousePos(canvasDom, mouseEvent) {
        var rect = canvasDom.getBoundingClientRect();
        return {
            x: mouseEvent.clientX - rect.left,
            y: mouseEvent.clientY - rect.top,
        };
    }

    function getTouchPos(canvasDom, touchEvent) {
        var rect = canvasDom.getBoundingClientRect();
        return {
            x: touchEvent.touches[0].clientX - rect.left,
            y: touchEvent.touches[0].clientY - rect.top,
        };
    }

    function renderCanvas() {
        if (drawing) {
            ctx.moveTo(lastPos.x, lastPos.y);
            ctx.lineTo(mousePos.x, mousePos.y);
            ctx.stroke();
            lastPos = mousePos;
        }
    }

    // Prevent scrolling when touching the canvas
    document.body.addEventListener(
        "touchstart",
        function (e) {
            if (e.target == canvas) {
                e.preventDefault();
            }
        },
        false
    );
    document.body.addEventListener(
        "touchend",
        function (e) {
            if (e.target == canvas) {
                e.preventDefault();
            }
        },
        false
    );
    document.body.addEventListener(
        "touchmove",
        function (e) {
            if (e.target == canvas) {
                e.preventDefault();
            }
        },
        false
    );

    (function drawLoop() {
        requestAnimFrame(drawLoop);
        renderCanvas();
    })();

    function clearCanvas() {
        canvas.width = canvas.width;
    }

    // Set up the UI
    // var sigText = document.getElementById("sig-dataUrl");
    var sigImage = document.getElementById("sig-image");
    var inputHidden = document.getElementById("asdfgh");
    var clearBtn = document.getElementById("sig-clearBtn");
    var submitBtn = document.getElementById("sig-submitBtn");
    clearBtn.addEventListener(
        "click",
        function (e) {
            clearCanvas();
            // sigText.innerHTML = "Data URL for your signature will go here!";
            sigImage.setAttribute("src", "");
        },
        false
    );
    submitBtn.addEventListener(
        "click",
        function (e) {
            var dataUrl = canvas.toDataURL();

            var halfWidth = canvas.width / 2;
            var halfHeight = canvas.height / 2;

            var resizedCanvas = document.createElement("canvas");
            resizedCanvas.width = halfWidth;
            resizedCanvas.height = halfHeight;
            var resizedCtx = resizedCanvas.getContext("2d");

            var img = new Image();
            img.onload = function () {
                resizedCtx.drawImage(img, 0, 0, halfWidth, halfHeight);

                var resizedDataUrl = resizedCanvas.toDataURL();

                sigImage.setAttribute("src", resizedDataUrl);
                inputHidden.setAttribute("value", resizedDataUrl);
            };
            img.src = dataUrl;
        },
        false
    );
})();

const sigImage = document.getElementById("sig-image");
const popup = document.getElementById("popup");

const cancelBtn = document.getElementById("sig-cancelBtn");

sigImage.addEventListener("click", function () {
    if (popup.style.display === "none") {
        popup.style.display = "block";
    } else {
        popup.style.display = "none";
    }
});

cancelBtn.addEventListener("click", function () {
    if (popup.style.display != "none") {
        popup.style.display = "none";
    }
});
