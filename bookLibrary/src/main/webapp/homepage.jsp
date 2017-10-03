<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Homepage</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css"
	integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">


<link rel="stylesheet" href="css/homepage.css" />
</head>

<body>

	<nav class="navbar navbar-toggleable-md navbar-inverse bg-inverse">
		<button class="navbar-toggler navbar-toggler-right" type="button"
			data-toggle="collapse" data-target="#navbarTogglerDemo02"
			aria-controls="navbarTogglerDemo02" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<a class="btn disabled" href="#"><i class="fa fa-book fa-lg"
			aria-hidden="true"></i></a>

		<div class="collapse navbar-collapse" id="navbarTogglerDemo02">
			<ul class="navbar-nav mx-auto">
				<li class="nav-item"><a class="nav-link disabled" href="#">Welcome
						${user.fullName} <span class="sr-only">(current)</span>
				</a></li>

			</ul>
			<form class="form-inline my-2 my-lg-0" action="LogoutController">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Logout</button>
			</form>
		</div>
	</nav>

	<div class="container" id="categories">

		<div class="row">
			<div class="col-md-4 col-sm-12">
				<div class="card">
					<div class="card-block">
						<h3 class="card-title">Arts</h3>
						<p class="card-text">
							<strong>Arts enables us to find ourselves and lose
								ourselves at the same time.</strong> - Thomas Merton
						</p>
						<button type="button" class="btn btn-primary" data-toggle="modal"
							data-target="#viewBookModal" data-whatever="Arts">View
							Books</button>
					</div>
				</div>
			</div>

			<div class="col-md-4 col-sm-12">
				<div class="card">
					<div class="card-block">
						<h3 class="card-title">Commerce</h3>
						<p class="card-text">
							<strong>Commerce changes the fate and genius of Nations</strong>
							- Thomas Grey
						</p>
						<button type="button" class="btn btn-primary" data-toggle="modal"
							data-target="#viewBookModal" data-whatever="Commerce">View
							Books</button>
					</div>
				</div>
			</div>

			<div class="col-md-4 col-12">
				<div class="card">
					<div class="card-block">
						<h3 class="card-title">Science</h3>
						<p class="card-text">
							<strong>Science is the poetry of Reality.</strong> - Richard
							Dawkins
						</p>
						<button type="button" class="btn btn-primary" data-toggle="modal"
							data-target="#viewBookModal" data-whatever="Science">View
							Books</button>
					</div>
				</div>
			</div>

		</div>

		<!-- Modal for viewing books in a particular category -->
		<div class="modal fade" id="viewBookModal" tabindex="-1" role="dialog"
			aria-labelledby="viewBookModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="viewBookModalLabel"></h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body" id="viewModalBody">
						<table id="bookTable" class="table">							
						</table>

						<form id="viewBookForm" class="text-left">

							<div class="form-group">
								<label for="vBookName">Book Name</label> <input type="text"
									class="form-control" id="vBookName" name="vBookName">
							</div>

							<div class="form-group">
								<label for="vBookAuthor">Book Author</label> <input type="text"
									class="form-control" id="vBookAuthor" name="vBookAuthor">
							</div>

							<div class="form-group">
								<label for="vCategory">Category</label> <input type="text"
									class="form-control" id="vCategory" name="vCategory">
							</div>

							<div class="form-group">
								<label for="vBookDescription">Book Description</label>
								<textarea class="form-control" id="vBookDescription"
									name="vBookDescription" rows="3"></textarea>
							</div>

						</form>

						<form id="updateForm" class="text-left">

							<input type="hidden" id="oldBookName" name="oldBookName" value="">


							<div class="form-group">
								<label for="uBookName">Book Name</label> <input type="text"
									class="form-control" id="uBookName" name="uBookName"
									placeholder="Applied Physics" required>
							</div>

							<div class="form-group">
								<label for="uBookAuthor">Book Author</label> <input type="text"
									class="form-control" id="uBookAuthor" name="uBookAuthor"
									placeholder="Robert Cornell" required>
							</div>

							<div class="form-group">
								<label for="uCategory">Category</label> <select
									class="custom-select" id="uCategory" name="uCategory">
									<option value="Arts">Arts</option>
									<option value="Science">Science</option>
									<option value="Commerce">Commerce</option>
								</select>
							</div>

							<div class="form-group">
								<label for="uBookDescription">Book Description</label>
								<textarea class="form-control" maxlength="500"
									id="uBookDescription" name="uBookDescription" rows="3"></textarea>
							</div>

						</form>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" id="updateButton"
							form="updateForm">Update</button>
						<button type="button" onclick="backFunction()" id="backButton"
							class="btn btn-info">Back</button>
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>

		<br />

		<div class="row" align="right">
			<div class="col">

				<button type="button" class="btn btn-primary" data-toggle="modal"
					data-target="#formModal">
					<i class="fa fa-plus" aria-hidden="true"></i>
				</button>
				<!-- Button trigger modal -->

				<!-- Modal for Adding/Updating Book -->
				<div class="modal fade" id="formModal" tabindex="-1" role="dialog"
					aria-labelledby="viewBookModalLabel" aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="viewBookModalLabel">Add Book</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								<form id="addForm" class="text-left">

									<div class="form-group">
										<label for="bookName">Book Name</label> <input type="text"
											class="form-control" id="bookName" name="bookName"
											placeholder="Applied Physics" required>
									</div>

									<div class="form-group">
										<label for="bookAuthor">Book Author</label> <input type="text"
											class="form-control" id="bookAuthor" name="bookAuthor"
											placeholder="Robert Cornell" required>
									</div>

									<div class="form-group">
										<label for="category">Category</label> <select
											class="custom-select" id="category" name="category">
											<option>Arts</option>
											<option>Science</option>
											<option>Commerce</option>
										</select>
									</div>

									<div class="form-group">
										<label for="bookDescription">Book Description</label>
										<textarea class="form-control" id="bookDescription"
											name="bookDescription" rows="3"></textarea>
									</div>

								</form>

							</div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-primary" id="addButton"
									form="addForm">Submit</button>
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal">Cancel</button>

							</div>
						</div>
					</div>
				</div>
			</div>


		</div>
	</div>




	<!-- jQuery first, then Tether, then Bootstrap JS. -->
	<!-- jQuery first, then Tether, then Bootstrap JS. -->
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"
		integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
		crossorigin="anonymous"></script>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"
		integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb"
		crossorigin="anonymous"></script>

	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"
		integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn"
		crossorigin="anonymous"></script>

	<script src="js/homepage.js">
		
	</script>


</body>

</html>