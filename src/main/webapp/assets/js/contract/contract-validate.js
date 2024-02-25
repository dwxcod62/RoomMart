function Validator(options) {
    var formElement = document.querySelector(options.form);
    var selectorRules = {};

    if (formElement) {
        formElement.onsubmit = function (e) {
            e.preventDefault(); // chặn không cho form submit
            var isFormValid = true;

            var userEmail = $("#room-email").val();
            var $formGroup = $("#room-email").closest(".form-group");
            var $formMessage = $formGroup.find(".form-message");
            $.ajax({
                url: "check-email-user",
                type: "GET",
                data: { email: userEmail },
                success: function (response) {
                    if (response === "true") {
                        $formMessage.text("Email hợp lệ").css("color", "green");
                    } else {
                        $formMessage.text("Email không hợp lệ hoặc không tồn tại").css("color", "red");
                        isFormValid = false;
                    }
                },
                error: function (xhr, status, error) {
                    console.error(xhr, status, error);
                    isValidEmail = false;
                },
            });

            // Thực hiện check từng rule
            options.rules.forEach(function (rule) {
                var inputElements = formElement.querySelectorAll(rule.selector);
                Array.from(inputElements).forEach(function (inputElement) {
                    var isValid = validate(inputElement, rule);
                    if (!isValid) {
                        isFormValid = false;
                    }
                });
            });

            if (isFormValid) {
                if (typeof options.onSubmit === "function") {
                    var enableInputs = formElement.querySelectorAll("[name]:not([disabled])");
                    var formValues = Array.from(enableInputs).reduce(function (values, input) {
                        switch (input.type) {
                            case "radio":
                                values[input.name] = formElement.querySelector('input[name="' + input.name + '"]:checked').value;
                                break;
                            case "checkbox":
                                if (!input.matches(":checked")) {
                                    if (!values[input.name]) {
                                        values[input.name] = "";
                                    }
                                    return values;
                                }

                                if (!Array.isArray(values[input.name])) {
                                    values[input.name] = [];
                                }

                                values[input.name].push(input.value);
                                break;
                            case "file":
                                values[input.name] = input.files;
                                break;
                            default:
                                values[input.name] = input.value;
                                break;
                        }

                        return values;
                    }, {});
                    options.onSubmit(formValues);
                } else {
                    formElement.submit();
                }
            }
        };

        options.rules.forEach(function (rule) {
            if (Array.isArray(selectorRules[rule.selector])) {
                selectorRules[rule.selector].push(rule.test);
            } else {
                selectorRules[rule.selector] = [rule.test];
            }

            var inputElements = formElement.querySelectorAll(rule.selector);
            Array.from(inputElements).forEach(function (inputElement) {
                if (inputElement) {
                    inputElement.onblur = function () {
                        validate(inputElement, rule);
                    };

                    inputElement.oninput = function () {
                        var errorElement = getParent(inputElement, options.formGroupSelector).querySelector(options.errorSelector);
                        errorElement.innerText = "";
                        getParent(inputElement, options.formGroupSelector).classList.remove("invalid");
                    };
                }
            });
        });
    }

    function getParent(element, selector) {
        while (element.parentElement) {
            if (element.parentElement.matches(selector)) {
                return element.parentElement;
            }
            element = element.parentElement;
        }
    }

    function validate(inputElement, rule) {
        var errorMessage;
        var errorElement = getParent(inputElement, options.formGroupSelector).querySelector(options.errorSelector);
        var rules = selectorRules[rule.selector];

        var isValid = rule.test(inputElement.value);

        for (var i = 0; i < rules.length; ++i) {
            switch (inputElement.type) {
                case "checkbox":
                case "radio":
                    errorMessage = rules[i](formElement.querySelector(rule.selector + ":checked"));
                    break;
                default:
                    errorMessage = rules[i](inputElement.value);
                    break;
            }
            if (errorMessage) break;
        }

        if (errorMessage) {
            errorElement.innerText = errorMessage;
            getParent(inputElement, options.formGroupSelector).classList.add("invalid");
        } else {
            errorElement.innerText = "";
            getParent(inputElement, options.formGroupSelector).classList.remove("invalid");
        }

        return !errorMessage;
    }
}

Validator.isRequired = function (selector, message) {
    return {
        selector: selector,
        test: function (value) {
            return value ? undefined : message || "Vui lòng nhập trường này";
        },
    };
};

Validator.isViePhoneNumber = function (selector, message) {
    return {
        selector: selector,
        test: function (value) {
            var regex = /^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$/;
            return regex.test(value) ? undefined : message || "Vui lòng nhập đúng định dạng số điện thoại";
        },
    };
};


Validator.isInteger = function (selector, message) {
    return {
        selector: selector,
        test: function (value) {
            var regex = /^\d+$/;
            return regex.test(value) ? undefined : message || "Vui lòng nhập đúng định dạng số nguyên";
        },
    };
};

Validator.isEmail = function (selector, message) {
    return {
        selector: selector,
        test: function (value) {
            var regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
            return regex.test(value) ? undefined : message || "Vui lòng nhập email hợp lệ";
        },
    };
};

Validator.isGreaterDate = function (selector, getEndDateValue, message) {
    return {
        selector: selector,
        test: function (value) {
            var d1 = new Date(value);
            var d2 = new Date(getEndDateValue());
            return d1 >= d2 ? undefined : message || "Giá trị nhập vào không chính xác";
        },
    };
};


var validator = new Validator({
    form: "#create-room-account-form",
    formGroupSelector: ".form-group",
    errorSelector: ".form-message",
    rules: [Validator.isEmail("#room-email"), Validator.isInteger("#room-fee"),
        Validator.isInteger("#room-electric"), Validator.isInteger("#room-water"),
        Validator.isInteger("#room-deposit"), Validator.isInteger("#payment-term"),
        Validator.isGreaterDate( "#input_to" ,function () {
            return document.querySelector('#input_from').value;
        } )]
});