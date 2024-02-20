$(document).ready(function(){
    $("#checkButton").click(function(){
        var userEmail = $("#room-email").val();
        var $formGroup = $(this).closest('.form-group');
        var $formMessage = $formGroup.find(".form-message"); // Define $formMessage here

        if(userEmail.trim() === '') {
            $formMessage.text("Email không được để trống"); // Display error message within the same form-group
            return;
        }

        $.ajax({
            url: "check-email-user",
            type: "GET",
            data: {email: userEmail},
            success: function(response){
                if (response === 'true') {
                    $formMessage.text("Email hợp lệ").css("color", "green");
                } else {
                    $formMessage.text("Email không hợp lệ hoặc không tồn tại").css("color", "red");
                }
            },
            error: function(xhr, status, error){
                console.error(xhr, status, error);
                isValidEmail = false;
            }
        });
    });

    // Datepicker initialization
    $(".datepicker").datepicker({
        format: "yyyy-mm-dd",
        autoclose: true,
    });

    // Toggle advanced form
    $(".advanced-form").hide();
    $("#toggle-checkbox").change(function () {
        if ($(this).is(":checked")) {
            $(".advanced-form").show();
        } else {
            $(".advanced-form").hide();
            $("input[id='fixed-years']").val("");
            $("input[id='percentage-increase']").val("");
        }
    });
});
