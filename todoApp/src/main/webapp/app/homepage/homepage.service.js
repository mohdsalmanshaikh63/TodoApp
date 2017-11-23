angular.module('homepage').
factory('homepageService', ['$resource', 'localStorageService',
        function ($resource, localStorageService) {

                console.log("Tokens are "+localStorageService.get('accessToken')+localStorageService.get('refreshToken'));

                function getAccessToken() {
                        return localStorageService.get('accessToken');
                }

                function getRefreshToken() {
                        return localStorageService.get('refreshToken');
                }
                        

                // this is for transforming response to get status and correct response format
                var transformResponse = function (data, headers, statusCode) {
                        console.log(statusCode); // prints 200 if nothing went
                        // wrong
                        var finalResponse = {
                                data: angular.fromJson(data),
                                responseStatusCode: statusCode
                        };
                        console.log("Final response is " + finalResponse);
                        return finalResponse;
                }

                return $resource('notes/create', {}, {
                        createNewNote: {
                                method: 'PUT',
                                headers: {
                                        'Content-Type': 'application/json',
                                        'accessToken' : localStorageService.get('accessToken'),
                                        'refreshToken' : localStorageService.get('refreshToken'),
                                },                              
                                transformResponse: transformResponse,

                        }
                });
        }
]);