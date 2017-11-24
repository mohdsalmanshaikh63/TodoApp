'use strict';

// Register 'login module along with controller and template
angular.module('login').component(
		'login',
		{
			templateUrl : 'app/login/login.template.html',
			controller : [
					'$scope',
					'loginService',
					'localStorageService',
					'$state',
					function LoginController($scope, loginService,
							localStorageService, $state) {
						$scope.user = {};

						console.log("LoginController called");

						// call login service on form validation
						$scope.submit = function() {
							console.log($scope.user);

							// call the login service
							$scope.login = loginService.query($scope.user,

							// save the tokens in local storage on success
							function(response) {
								console.log("Got the response data as "
										+ JSON.stringify(response.data));

								// this is needed since we used transformRequest
								// in service and it transforms data in shitty
								// format as
								// "{\"accessToken\":{\"tokenType\":\"accessToken\",\"value\":\"8baccd8cda46430ebe4768bdf9e486f6\",\"uid\":23},
								// \"refreshToken\":{\"tokenType\":\"refreshToken\",\"value\":\"075568ee7661407c8f32473118eb5d9a\",\"uid\":23}}"
								var obj = angular.fromJson(response.data)
								var accessToken = obj.accessToken.value;
								var refreshToken = obj.refreshToken.value;
								console.log("Got the accessToken as:"
										+ accessToken);
								console.log("Got the refreshToken as:"
										+ refreshToken);

								localStorageService.set('accessToken',
										accessToken);
								localStorageService.set('refreshToken',
										refreshToken);
								
								$state.go('homepage');

							}, function(error) {
								console.log("Got an error "
										+ JSON.stringify(error));
							})
						}
					} ]
		});