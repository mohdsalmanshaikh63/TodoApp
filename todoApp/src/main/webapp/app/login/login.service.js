angular.module('login').factory('loginService',
		[ '$resource', function($resource) {
			return $resource('user/login', {}, {
				query : {
					method : 'POST',
					transformResponse : function(data, headers, statusCode) {
						console.log(statusCode);// prints 200 if nothing went
												// wrong
						var finalRsponse = {
							data : data,
							responseStatusCode : statusCode
						};
						return finalRsponse;
					}
				}
			});
		} ]);