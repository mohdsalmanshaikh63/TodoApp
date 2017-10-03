<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css"
	integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ"
	crossorigin="anonymous">

<link rel="stylesheet" href="css/register.css" />
</head>
<body>
	<div class="container">

		<c:if test="${param.msg == 'ERROR'}">
			<div class="row justify-content-center">
				<div class="col-4 text-center alert alert-danger" role="alert">
				
				${sessionScope.error}
				
				<% session.invalidate(); %>

				</div>
			</div>
		</c:if>


		<div class="row">
			<div class="col">
				<form class="form-signin" id="myform" action="RegisterController"
					method="post">
					<h2 class="form-signin-heading">Register</h2>

					<div class="form-group" id="name-group">
						<label for="fullname" class="form-control-label">Full Name</label>
						<input type="text" class="form-control" id="fullname"
							placeholder="John Doe" name="fullname">
						<div class="form-control-feedback hidden-xs-up" id="name-warning"></div>
					</div>

					<div class="form-group" id="email-group">
						<label for="email">Email address</label> <input type="email"
							class="form-control" id="email" aria-describedby="emailHelp"
							placeholder="Enter email" name="email">
						<div class="form-control-feedback hidden-xs-up" id="email-warning"></div>
					</div>

					<div class="form-group" id="mobile-group">
						<label for="mobile">Mobile</label> <input type="tel"
							class="form-control" id="mobile" placeholder="8963757247"
							name="mobile">
						<div class="form-control-feedback hidden-xs-up"
							id="mobile-warning"></div>
					</div>

					<div class="form-group" id="password-group">
						<label for="password">Password</label> <input type="password"
							class="form-control" id="password" placeholder="Password"
							name="password">
						<div class="form-control-feedback hidden-xs-up"
							id="password-warning"></div>
					</div>

					<div class="form-group" id="confirmPassword-group">
						<label for="confirmPassword">Confirm Password</label> <input
							type="password" class="form-control" id="confirmPassword"
							placeholder="Retype the Password" name="confirmPassword">
						<div class="form-control-feedback hidden-xs-up"
							id="confirmPassword-warning"></div>
					</div>

					<div class="form-group">
						<div class="row">
							<div class="col">
								<label for="gender">Gender</label>
							</div>
						</div>

						<div class="row">
							<div class="form-check form-check-inline col">
								<label class="form-check-label"> <input
									class="form-check-input" type="radio" name="gender" id="male"
									value="male" required> Male
								</label>
							</div>
							<div class="form-check form-check-inline col">
								<label class="form-check-label"> <input
									class="form-check-input" type="radio" name="gender" id="female"
									value="female" required> Female
								</label>
							</div>
						</div>
					</div>

					<div class="form-group">
						<p id="agreement">
							By Clicking on the "Create Account" Button below, you certify
							that you have read and agree to our <a href="#">terms of use</a>
							and <a href="">privacy policy</a>.
						</p>
					</div>

					<button type="submit" class="btn btn-success btn-block">Create
						an Account</button>

					<div class="form-group">
						<p id="signin" class="text-center">
							Already have an Account?<a href="login.jsp">Sign In</a>.
						</p>
					</div>
				</form>
			</div>
		</div>

	</div>

	<!-- jQuery first, then Tether, then Bootstrap JS. -->
	<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"
		integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"
		integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb"
		crossorigin="anonymous"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"
		integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn"
		crossorigin="anonymous"></script>

	<script type="text/javascript"
		src="//cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.14.0/jquery.validate.js"></script>

	<!-- Client side validation using jquery -->
	<script src="js/register.js"></script>

</body>
</html>