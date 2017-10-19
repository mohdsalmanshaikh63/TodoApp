<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Login Page</title>

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css"
	integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ"
	crossorigin="anonymous">

<link rel="stylesheet" href="css/login.css" />

</head>

<body>
	<div class="container justify-content-center col-6" id="maincontainer">


		<div class="row">

			<div class="col">
				<h2 class="form-signin-heading">Sign in</h2>
				<hr />
			</div>

		</div>

		<div class="row" id="login-content">

			<div class="col-md-6" id="left-div">

				<div class="row">
					<div class="col">
						<h5>Use other Accounts</h5>
					</div>
				</div>

				<div class="row">
					<div class="col hidden-sm-down">
						<p>You can also sign in using your Facebook or Google Account.</p>
					</div>
				</div>

				<div class="row">
					<div class="col col-md-12">
						<p>
							<button type="button"
								class="btn btn-primary text-white social btn-block btn-responsive"
								id="facebook">Login with Facebook</button>
						</p>
					</div>
				</div>

				<div class="row">
					<div class="col col-md-12">
						<p>
							<button type="button"
								class="btn bg-inverse text-white social btn-block btn-responsive">Login
								with Google</button>
						</p>
					</div>
				</div>

			</div>

			<div class="col-md-6">



				<div class="row">
					<div class="col text-center">
						<h5>Use Your Account</h5>
					</div>
				</div>




				<div class="row">
					<div class="col">
						<form class="form-signin" action="LoginController" method="post">
							

							<div class="row hidden-xs-up container" id="warning-message">
								<div class="col-12 alert alert-danger" role="alert">
									<strong>Incorrect Username/Password</strong>
								</div>
							</div>
							<div class="row hidden-xs-up container"
								id="authorization-message">
								<div class="col alert alert-warning container" role="alert">
									<strong>You need to login to access this webpage!</strong>
								</div>
							</div>
							<div class="row hidden-xs-up container" id="logout-message">
								<div class="col alert alert-success" role="alert">
									<strong>You have logged out successfully!</strong>
								</div>
							</div>

							<div class="row">
								<div class="col">
									<input id="inputEmail" name="email" class="form-control"
										placeholder="Email address" required="" autofocus=""
										type="email">
								</div>
							</div>

							<div class="row">
								<div class="col">
									<input id="inputPassword" name="password" class="form-control"
										placeholder="Password" required="" type="password">
								</div>
							</div>

							<button class="btn btn-lg btn-info text-white btn-block"
								id="signin-button" type="submit">Sign in</button>
						</form>
					</div>
				</div>

				<div class="row" id="bottom-links" style="margin: 2% auto;">
					<div class="col text-center col-md-auto">
						<a href="#">Forgot your password?</a>
					</div>

					<div class="col text-center">
						<a href="register.jsp">Register</a>
					</div>
				</div>
			</div>

		</div>

	</div>
	<!-- /container -->

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

	<c:choose>

		<c:when test="${param.message == 'INVALID'}">
			<script>
				$('#warning-message').toggleClass('hidden-xs-up');
			</script>
		</c:when>

		<c:when test="${param.message == 'LOGOUT'}">
			<script>
				$('#logout-message').toggleClass('hidden-xs-up');
			</script>
		</c:when>

		<c:when test="${param.message == 'NOACCESS'}">
			<script>
				$('#authorization-message').toggleClass('hidden-xs-up');
			</script>
		</c:when>


	</c:choose>


</body>
</html>