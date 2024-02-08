(function () {
    validate.extend(validate.validators.datetime, {
        parse: function (value, options) {
            return +moment.utc(value);
        },
        format: function (value, options) {
            var format = options.dateOnly ? "YYYY-MM-DD" : "YYYY-MM-DD hh:mm:ss";
            return moment.utc(value).format(format);
        },
    });
    const constraints = {
        name: {
            presence: true,
            length: {
                minimum: 3,
                maximum: 20,
            },
            format: {
                pattern: "[a-z0-9 ]+",
                flags: "i",
                message: " can only contain a-z and 0-9",
            },
        },
        password: {
            presence: true,
            length: {
                minimum: 8,
            },
        },
        phone: {
            presence: true,
            length: {
                minimum: 10,
                maximum: 10,
            },
            format: {
                pattern: "[0-9]+",
                flags: "i",
                message: "can only contain a-z and 0-9",
            },
        },
        cccd: {
            presence: true,
            length: {
                minimum: 3,
            },
            format: {
                pattern: "[a-z0-9]+",
                flags: "i",
                message: " can only contain a-z and 0-9",
            },
        },
    };

    $("#reg-form").submit((event) => {
        event.preventDefault();

        let nameValue = $('#name').val();
        let passwordValue = $("#password").val();
        let confirmPasswordValue = $("#confirm-password").val();
        let phoneValue = $('#phone-number').val();
        let cccdValue = $('#cccd').val();

        let nameError = validate.single(nameValue, constraints.name) || {};
        let passwordError = validate.single(passwordValue, constraints.password) || {};
        let confirmPasswordError = validate.single(confirmPasswordValue, constraints.password) || {};
        let phoneError = validate.single(phoneValue, constraints.phone) || {};
        let cccdError = validate.single(cccdValue, constraints.cccd) || {};

        if (passwordValue !== confirmPasswordValue) {
            confirmPasswordError = {
                message: "The passwords do not match",
            };
        }

        $('#error-name').html(nameError);
        $('#error-password').html(passwordError);
        $('#error-confirm-password').html(confirmPasswordError);
        $('#error-phone-number').html(phoneError);
        $('#error-cccd').html(cccdError);

        if (!jQuery.isEmptyObject(nameError) || !jQuery.isEmptyObject(passwordError) || !jQuery.isEmptyObject(confirmPasswordError) || !jQuery.isEmptyObject(phoneError) || !jQuery.isEmptyObject(cccdError)) {
            console.log("Form has validation errors. Please fix them.");
        } else {
            console.log("Form submitted successfully!");
            $("#reg-form")[0].submit();
        }
    });
})();
