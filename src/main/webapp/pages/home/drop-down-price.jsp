<%--
  Created by IntelliJ IDEA.
  User: Thanh
  Date: 02/03/2024
  Time: 10:28 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>


    ::selection {
        color: #fff;
        background: #17a2b8;
    }
    .wrapper2 {
        width: 400px;
        background: #fff;
        border-radius: 10px;
        padding: 20px 25px 40px;
        box-shadow: 0 12px 35px rgba(0, 0, 0, 0.1);
    }
    header h2 {
        font-size: 24px;
        font-weight: 600;
    }
    header p {
        margin-top: 5px;
        font-size: 16px;
    }
    .price-input {
        width: 100%;
        display: flex;
        margin: 30px 0 35px;
    }
    .price-input .field {
        display: flex;
        width: 100%;
        height: 45px;
        align-items: center;
    }
    .field input {
        width: 100%;
        height: 100%;
        outline: none;
        font-size: 19px;
        margin-left: 12px;
        border-radius: 5px;
        text-align: center;
        border: 1px solid #999;
        -moz-appearance: textfield;
    }
    input[type="number"]::-webkit-outer-spin-button,
    input[type="number"]::-webkit-inner-spin-button {
        -webkit-appearance: none;
    }
    .price-input .separator {
        width: 130px;
        display: flex;
        font-size: 19px;
        align-items: center;
        justify-content: center;
    }
    .slider {
        height: 5px;
        position: relative;
        background: #ddd;
        border-radius: 5px;
    }
    .slider .progress {
        height: 100%;
        left: 25%;
        right: 25%;
        position: absolute;
        border-radius: 5px;
        background: #17a2b8;
    }
    .range-input {
        position: relative;
    }
    .range-input input {
        position: absolute;
        width: 100%;
        height: 5px;
        top: -5px;
        background: none;
        pointer-events: none;
        -webkit-appearance: none;
        -moz-appearance: none;
    }
    input[type="range"]::-webkit-slider-thumb {
        height: 17px;
        width: 17px;
        border-radius: 50%;
        background: #17a2b8;
        pointer-events: auto;
        -webkit-appearance: none;
        box-shadow: 0 0 6px rgba(0, 0, 0, 0.05);
    }
    input[type="range"]::-moz-range-thumb {
        height: 17px;
        width: 17px;
        border: none;
        border-radius: 50%;
        background: #17a2b8;
        pointer-events: auto;
        -moz-appearance: none;
        box-shadow: 0 0 6px rgba(0, 0, 0, 0.05);
    }

</style>
<div  class="wrapper2 dropdown-menu" style="padding: 50px" id="dropdownPricev2" aria-labelledby="dropdownMenuButton">
    <header>
        <h2>Mức Giá</h2>
        <p>Kéo hoặc nhập mức giá mong muốn</p>
    </header>
    <div class="price-input">
        <div class="field">
            <span>Min</span>
            <input type="number" id="input-min" name="input-min" class="input-min" value="1000000" />
        </div>
        <div class="separator">-</div>
        <div class="field">
            <span>Max</span>
            <input type="number" id="input-max" name="input-max" class="input-max" value="5000000" />
        </div>
    </div>
    <div class="slider">
        <div class="progress" style="left: 0%;right: 0%;"></div>
    </div>
    <div class="range-input">
        <input
                type="range"
                class="range-min"
                min="0"
                max="5000000"
                value="0"
                step="100000"
        />
        <input
                type="range"
                class="range-max"
                min="0"
                max="5000000"
                value="5000000"
                step="100000"
        />
    </div>
</div>
<script>

    const rangeInput = document.querySelectorAll(".range-input input"),
        priceInput = document.querySelectorAll(".price-input input"),
        range = document.querySelector(".slider .progress");
    let priceGap = 1000;

    priceInput.forEach((input) => {
        input.addEventListener("input", (e) => {
            let minPrice = parseInt(priceInput[0].value),
                maxPrice = parseInt(priceInput[1].value);

            if (maxPrice - minPrice >= priceGap && maxPrice <= rangeInput[1].max) {
                if (e.target.className === "input-min") {
                    rangeInput[0].value = minPrice;
                    range.style.left = (minPrice / rangeInput[0].max) * 100 + "%";
                } else {
                    rangeInput[1].value = maxPrice;
                    range.style.right = 100 - (maxPrice / rangeInput[1].max) * 100 + "%";
                }
            }
        });
    });

    rangeInput.forEach((input) => {
        input.addEventListener("input", (e) => {
            let minVal = parseInt(rangeInput[0].value),
                maxVal = parseInt(rangeInput[1].value);

            if (maxVal - minVal < priceGap) {
                if (e.target.className === "range-min") {
                    rangeInput[0].value = maxVal - priceGap;
                } else {
                    rangeInput[1].value = minVal + priceGap;
                }
            } else {
                priceInput[0].value = minVal;
                priceInput[1].value = maxVal;
                range.style.left = (minVal / rangeInput[0].max) * 100 + "%";
                range.style.right = 100 - (maxVal / rangeInput[1].max) * 100 + "%";
            }
        });
    });

</script>
</html>
