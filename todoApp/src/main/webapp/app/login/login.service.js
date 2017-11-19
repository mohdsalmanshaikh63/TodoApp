angular.module('login').factory('loginService',
		[ '$resource', function($resource) {
			return $resource('user/login', {}, {
				query : {
					method : 'POST',
					transformResponse : function(data, headers, statusCode) {
						console.log(statusCode);// prints 200 if nothing went
												// wrong
						var finalResponse = {
							data : data,
							responseStatusCode : statusCode
						};
						
						console.log("Final response is "+finalResponse);
						return finalResponse;
					}
				}
			});
		} ]);