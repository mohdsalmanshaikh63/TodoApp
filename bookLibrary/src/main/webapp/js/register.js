var emailRegex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
var digit = /\d/;
var error = false;

function isEmail(email) {

	return emailRegex.test(email);
}

function toggler(groupName, inputElement, warningElement, message) {
	$(groupName).toggleClass("has-danger");
	$(inputElement).toggleClass("form-control-danger");
	$(warningElement).toggleClass("hidden-xs-up");
	$(warningElement).html(message);
	error = true;
}

$(function() {

	$("#myform").submit(
			function(event) {

				error = false;

				$(".form-group").removeClass("has-danger");
				$(".form-control").removeClass("form-control-danger");
				$(".form-control-feedback").addClass("hidden-xs-up");

				if ($("#fullname").val() === "") {
					toggler("#name-group", "#fullname", "#name-warning",
							"Fullname required");
				} else if (/\d/.test($("#fullname").val())) {
					toggler("#name-group", "#fullname", "#name-warning",
							"No numbers allowed");
				} else {
					var fullname = $("#fullname").val();
					var words = $.trim(fullname).split(" ");
					if (words.length < 2) {
						toggler("#name-group", "#fullname", "#name-warning",
								"Please enter fullname correctly");
					}

				}

				if ($("#email").val() === "") {
					toggler("#email-group", "#email", "#email-warning",
							"Email required");
				} else if (!(isEmail($("#email").val()))) {
					toggler("#email-group", "#email", "#email-warning",
							"Please enter correct email");
				}

				if ($("#mobile").val() === "") {
					toggler("#mobile-group", "#mobile", "#mobile-warning",
							"Mobile no required");
				} else if ($.isNumeric($("#mobile").val()) == false) {
					toggler("#mobile-group", "#mobile", "#mobile-warning",
							"Please enter numbers only");
				} else if ($("#mobile").val().length != 10) {
					toggler("#mobile-group", "#mobile", "#mobile-warning",
							"Only 10 digits required");
				}

				if ($("#password").val() === "") {
					toggler("#password-group", "#password",
							"#password-warning", "Password cannot be empty");
				} else if ($("#password").val().length < 5) {
					toggler("#password-group", "#password",
							"#password-warning",
							"Minimum 5 characters required");
				}

				if ($("#password").val() != $("#confirmPassword").val()) {
					toggler("#confirmPassword-group", "#confirmPassword",
							"#confirmPassword-warning",
							"Passwords do not match");
				}

				console.log("error" + error);

				if (error == true) {
					window.scrollTo(0, 0);
					return false;
				} else {
					return;
				}

			});
});