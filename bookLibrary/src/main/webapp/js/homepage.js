// refactor both edit and viewBook functions by placing common code in one function later
//function viewBook(bookName) {
//
//	
//			console.log('Inside viewBook');
//			console.log('BookName:'+bookName);
//	
//			// hide the bookTable first
//			$('#bookTable').hide();
//			//$('#updateButton').show();
//	
//			// first get the book details using ajax
//			// Using the core $.ajax() method
//			$.ajax({
//	
//				// The URL for the request
//				url: "LibraryController",
//	
//				// The data to send (will be converted to a query string)
//				data: {
//					command:'load',
//					bookName:bookName
//				},
//	
//				// Whether this is a POST or GET request
//				type: "POST",
//	
//				// The type of data we expect back
//				dataType: "json",
//			})
//				// Code to run if the request succeeds (is done);
//				// The response is passed to the function
//				.done(function (json) {
//					console.log('Inside done function');
//					console.log(json['bookDescription']);
//					$('#updateForm :input').prop('disabled', false);
//					$("#uBookName").val(json['bookName']);
//					$("#uBookAuthor").val(json['bookAuthor']);
//					$("#uBookDescription").val(json['bookDescription']);
//	
//					var category = json['bookCategory'];
//					if(category === 'arts') {
//						$('#updateForm>select>option:eq(0)').prop('selected', true);
//	
//					} else if(category === 'science') {
//						$('#updateForm>select>option:eq(1)').prop('selected', true);
//	
//					} else if(category === 'commerce') {
//						$('#updateForm>select>option:eq(2)').prop('selected', true);
//	
//					}
//					
//					$('#updateForm :input').prop('disabled', true);
//	
//					// now show edit form
//					$('#updateForm').show();
//					console.log('Done function complete');
//	
//				})
//				// Code to run if the request fails; the raw request and
//				// status codes are passed to the function
//				.fail(function (xhr, status, errorThrown) {
//					alert("Sorry, there was a problem!");
//					console.log("Error: " + errorThrown);
//					console.log("Status: " + status);
//					console.dir(xhr);
//				})
//				// Code to run regardless of success or failure;
//				.always(function (xhr, status) {
//					alert("The request is complete!");
//				});
//	
//		}
function backFunction() {
	$('#updateForm').hide();
	$('#viewBookForm').hide();
	$('#updateButton').hide();
	$('#bookTable').show();
}


function viewBook(bookName) {

	
			console.log('Inside viewBook');
			console.log('BookName:'+bookName);
	
			// hide the bookTable and updateForm first
			$('#bookTable').hide();
			$('#updateForm').hide();			
			$('#updateButton').show();
			$('#viewBookForm :input').prop('disabled', false);
	
			// first get the book details using ajax
			// Using the core $.ajax() method
			$.ajax({
	
				// The URL for the request
				url: "LibraryController",
	
				// The data to send (will be converted to a query string)
				data: {
					command:'load',
					bookName:bookName
				},
	
				// Whether this is a POST or GET request
				type: "POST",
	
				// The type of data we expect back
				dataType: "json",
			})
				// Code to run if the request succeeds (is done);
				// The response is passed to the function
				.done(function (json) {
					console.log('Inside done function');
					console.log(json['bookCategory']);
					//$('#updateForm').trigger('reset');
					$("#vBookName").val(json['bookName']);
					$("#vBookAuthor").val(json['bookAuthor']);
					$("#vCategory").val(json['bookCategory']);
					$("#vBookDescription").val(json['bookDescription']);
					
					// make the inputs read only
					$('#viewBookForm :input').prop('disabled', true);
	
					// now show edit form
					$('#viewBookForm').show();
					console.log('Done function complete');
	
				})
				// Code to run if the request fails; the raw request and
				// status codes are passed to the function
				.fail(function (xhr, status, errorThrown) {
					alert("Sorry, there was a problem!");
					console.log("Error: " + errorThrown);
					console.log("Status: " + status);
					console.dir(xhr);
				})
				// Code to run regardless of success or failure;
//				.always(function (xhr, status) {
//					alert("The request is complete!");
//				});
	
		}



function editBook(bookName) {

	
			console.log('Inside editBook');
			console.log('BookName:'+bookName);
	
			// hide the bookTable first
			$('#bookTable').hide();
			$('#updateButton').show();
//			$('option:not(:selected)').prop('disabled', true);
//			$('#updateForm :input').prop('disabled', false);
	
			// first get the book details using ajax
			// Using the core $.ajax() method
			$.ajax({
	
				// The URL for the request
				url: "LibraryController",
	
				// The data to send (will be converted to a query string)
				data: {
					command:'load',
					bookName:bookName
				},
	
				// Whether this is a POST or GET request
				type: "POST",
	
				// The type of data we expect back
				dataType: "json",
			})
				// Code to run if the request succeeds (is done);
				// The response is passed to the function
				.done(function (json) {
					console.log('Inside done function');
					console.log(json['bookCategory']);
					//$('#updateForm').trigger('reset');
					$("#uBookName").val(json['bookName']);
					$("#uBookAuthor").val(json['bookAuthor']);
					$("#uBookDescription").val(json['bookDescription']);
	
					var category = json['bookCategory'];
					if(category === 'Arts') {
						console.log('Category Arts');
						$("#uCategory option[value='Arts']").prop('selected', 'selected');
	
					} else if(category === 'Science') {
						console.log('Category Science');
						$("#uCategory option[value='Science']").prop('selected', 'selected');
	
					} else if(category === 'Commerce') {
						console.log('Category Commerce');
						$("#uCategory option[value='Commerce']").prop('selected', 'selected');
	
					}
					
					$('#oldBookName').val(bookName);
	
					// now show edit form
					$('#updateForm').show();
					console.log('Done function complete');
	
				})
				// Code to run if the request fails; the raw request and
				// status codes are passed to the function
				.fail(function (xhr, status, errorThrown) {
					alert("Sorry, there was a problem!");
					console.log("Error: " + errorThrown);
					console.log("Status: " + status);
					console.dir(xhr);
				})
				// Code to run regardless of success or failure;
//				.always(function (xhr, status) {
//					alert("The request is complete!");
//				});
	
		}

function confirmDelete(bookName) {
	
	var button = $(this);
	
	console.log("Inside confirmDelete()");
	
	if ((confirm('Are you sure you want to delete?'))) {
		
		// Using the core $.ajax() method
		$
			.ajax({

				// The URL for the request
				url: "LibraryController",

				// The data to send (will be converted to a
				// query string)
				data: {

					command: 'delete',
					bookName: bookName,					
				},

				// Whether this is a POST or GET request
				type: "POST",

				// The type of data we expect back
				dataType: "text",
			})
			// Code to run if the request succeeds (is
			// done);
			// The response is passed to the function
			.done(function (text) {
				console.log('Inside done');
				if (text === 'success') {
					alert('Book deleted successfully');
				    var rowIndex = $(button).closest('tr').index();
				    console.log("Row no to be deleted"+rowIndex);
				    $("tr").eq(rowIndex).remove();
				    console.log("Row deleted");
					//$('#'+bookName+'Row').hide();
				} else if (text === 'error') {
					alert('Error while deleting book');
				}

			})
			// Code to run if the request fails; the raw
			// request and
			// status codes are passed to the function
			.fail(function (xhr, status, errorThrown) {
				alert("Sorry, there was a problem!");
				console.log("Error: " + errorThrown);
				console.log("Status: " + status);
				console.dir(xhr);
				return false;
			})
			// Code to run regardless of success or failure;
//			.always(
//			function (xhr, status) {
//				alert("I will keep annoying you until you don't remove me!");
//			});

		console.log('After ajax');

		
	} else {
		
		return false;
		
			}
}


$(function () {
	// js code for bringing data when user clicks on any bookcategory	
$('#viewBookModal')
.on(
'show.bs.modal',
function (event) {

//	$('#updateForm :input').prop('disabled', false);
	//$('#updateForm').trigger('reset');
	$('#viewBookForm').hide();
	$('#updateForm').hide();
	$('#updateButton').hide();

	console.log('yipekayee2');
	var button = $(event.relatedTarget); // Button that
	// triggered
	// the modal
	var bookCategory = button.data('whatever'); // Extract info
	// from data-*
	// attributes
	var modal = $(this);
	modal.find('.modal-title').text(
		'My ' + bookCategory + ' books');
	console.log('Before ajax');

	// Using the core $.ajax() method
	$
		.ajax({

			// The URL for the request
			url: "LibraryController",

			// The data to send (will be converted to a
			// query string)
			data: {
				category: bookCategory,
				command: 'list'
			},

			// Whether this is a POST or GET request
			type: "GET",

			// The type of data we expect back
			dataType: "json",
		})
		// Code to run if the request succeeds (is done);
		// The response is passed to the function
		.done(
		function (responseJson) {
			console.log(responseJson);
			if (responseJson != null) {
				console.log("Inside done function"
					+ responseJson);
				$("#bookTable").html("");
				var html = "";
				html += "<tr><td>Book </td><td>Action</td></tr>";

				$
					.each(
					responseJson,
					function (key, value) {
						// remove spaces from this and use it as id
						var bookName = value['bookName'];
						console.log(bookName);

						html += "<tr><td>"
							+"<button type='button' class='btn btn-outline-info' onclick='viewBook(\""+bookName+"\");'>"
							+ bookName
							+ "</button></td><td>"
							+ "<button type='button' onclick='editBook(\""+bookName+"\");' class='btn btn-secondary'><i class='fa fa-pencil-square-o' aria-hidden='true'></i></button>   "
							+ "<button type='button' onclick='confirmDelete(\""+bookName+"\");' class='btn btn-success'><i class='fa fa-trash-o' aria-hidden='true'></i></button ></td></tr>";

					});

				$("#bookTable").html(html+"<h1>I am testing</h1>");
				$('#bookTable').show();
			}

		})
		// Code to run if the request fails; the raw request
		// and
		// status codes are passed to the function
		.fail(function (xhr, status, errorThrown) {
			$("#bookTable").html("");
			alert("No books found!");
			console.log("Error: " + errorThrown);
			console.log("Status: " + status);
			console.dir(xhr);
		});
	// Code to run regardless of success or failure;
	// .always(function (xhr, status) {
	// alert("The request is complete!");
	// });

	console.log('After ajax');
});


	// On trying to add new book
	$('#addForm')
		.submit(
		function (event) {

			event.preventDefault();
			console.log("inside submit;");
			var bookName = $('#bookName').val();
			var bookAuthor = $('#bookAuthor').val();
			var bookCategory = $('#category').val();
			var bookDescription = $('#bookDescription').val();

			console.log('bookname ' + bookName + ' bookAuthor '
				+ bookAuthor + ' category ' + category
				+ ' bookDescription ' + bookDescription);
			// Using the core $.ajax() method
			$
				.ajax({

					// The URL for the request
					url: "LibraryController",

					// The data to send (will be converted to a
					// query string)
					data: {

						command: 'add',
						bookName: bookName,
						bookAuthor: bookAuthor,
						category: bookCategory,
						bookDescription: bookDescription
					},

					// Whether this is a POST or GET request
					type: "POST",

					// The type of data we expect back
					dataType: "text",
				})
				// Code to run if the request succeeds (is
				// done);
				// The response is passed to the function
				.done(function (text) {
					console.log('Inside done');
					if (text === 'success') {
						alert('Book added successfully');
						$('#addForm').trigger('reset');
						$('#formModal').modal('toggle');
					} else if (text === 'duplicate') {
						alert('Book already exists');
					}

				})
				// Code to run if the request fails; the raw
				// request and
				// status codes are passed to the function
				.fail(function (xhr, status, errorThrown) {
					alert("Sorry, there was a problem!");
					console.log("Error: " + errorThrown);
					console.log("Status: " + status);
					console.dir(xhr);
					return false;
				})
				// Code to run regardless of success or failure;
//				.always(
//				function (xhr, status) {
//					alert("I will keep annoying you until you don't remove me!");
//				});

			console.log('After ajax');
			return;
		});

	// On trying to update book
	$('#updateForm')
		.submit(
		function (event) {

			event.preventDefault();
			console.log("inside submit of update;");
			var oldBookName = $('#oldBookName').val();
			var bookName = $('#uBookName').val();
			var bookAuthor = $('#uBookAuthor').val();
			var bookCategory = $('#uCategory').val();
			var bookDescription = $('#uBookDescription').val();

			console.log('oldBookName '+oldBookName+' bookname ' + bookName + ' bookAuthor '
				+ bookAuthor + ' category ' + category
				+ ' bookDescription ' + bookDescription);
			// Using the core $.ajax() method
			$
				.ajax({

					// The URL for the request
					url: "LibraryController",

					// The data to send (will be converted to a
					// query string)
					data: {

						command: 'update',
						oldBookName:oldBookName,
						bookName: bookName,
						bookAuthor: bookAuthor,
						category: bookCategory,
						bookDescription: bookDescription
					},

					// Whether this is a POST or GET request
					type: "POST",

					// The type of data we expect back
					dataType: "text",
				})
				// Code to run if the request succeeds (is
				// done);
				// The response is passed to the function
				.done(function (text) {
					console.log('Inside done');
					if (text === 'success') {
						alert('Book updated successfully');
						$('#updateForm').trigger('reset');						
						$('#oldBookName').val('');
						
						$('#viewBookForm').hide();
						$('#updateForm').hide();
						$('#updateButton').hide();

						
						console.log('Before ajax');

						// Using the core $.ajax() method
						$
							.ajax({

								// The URL for the request
								url: "LibraryController",

								// The data to send (will be converted to a
								// query string)
								data: {
									category: bookCategory,
									command: 'list'
								},

								// Whether this is a POST or GET request
								type: "GET",

								// The type of data we expect back
								dataType: "json",
							})
							// Code to run if the request succeeds (is done);
							// The response is passed to the function
							.done(
							function (responseJson) {
								console.log(responseJson);
								if (responseJson != null) {
									console.log("Inside done function"
										+ responseJson);
									$("#bookTable").html("");
									var html = "";
									html += "<tr><td>Book </td><td>Action</td></tr>";

									$
										.each(
										responseJson,
										function (key, value) {
											// remove spaces from this and use it as id
											var bookName = value['bookName'];
											console.log(bookName);

											html += "<tr><td>"
												+"<button type='button' class='btn btn-outline-info' style='word-wrap:break-word' onclick='viewBook(\""+bookName+"\");'>"
												+ bookName
												+ "</button></td><td>"
												+ "<button type='button' onclick='editBook(\""+bookName+"\");' class='btn btn-secondary'><i class='fa fa-pencil-square-o' aria-hidden='true'></i></button>   "
												+ "<button type='button' onclick='confirmDelete(\""+bookName+"\");' class='btn btn-success'><i class='fa fa-trash-o' aria-hidden='true'></i></button ></td></tr>";

										});

									$("#bookTable").html(html+"<h1>I am testing</h1>");
									$('#bookTable').show();
								}

							})
							// Code to run if the request fails; the raw request
							// and
							// status codes are passed to the function
							.fail(function (xhr, status, errorThrown) {
								$("#bookTable").html("");
								alert("No books found!");
								console.log("Error: " + errorThrown);
								console.log("Status: " + status);
								console.dir(xhr);
							});
						
						
						
						
					} else if (text === 'duplicate') {
						alert('Book already exists');
					}

				})
				// Code to run if the request fails; the raw
				// request and
				// status codes are passed to the function
				.fail(function (xhr, status, errorThrown) {
					alert("Sorry, there was a problem!");
					console.log("Error: " + errorThrown);
					console.log("Status: " + status);
					console.dir(xhr);
					return false;
				})
				// Code to run regardless of success or failure;
//				.always(
//				function (xhr, status) {
//					alert("Ajax running");
//				});

			console.log('After ajax');
			return;
		});


});
