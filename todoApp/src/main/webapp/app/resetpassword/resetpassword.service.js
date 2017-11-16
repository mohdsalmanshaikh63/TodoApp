angular.module('resetpassword').
factory('resetpasswordService', ['$resource',
        function ($resource) {

                return {

                        reset: function(token) {

                return $resource('user/reset', {}, {

                        post: {
                                method: 'POST',
                                headers: {
                                        'Content-Type': 'application/json',
                                        'forgotToken':token
                                }
                        }
                });
        }
        }
        }
]);