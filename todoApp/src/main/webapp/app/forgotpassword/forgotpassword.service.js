angular.module('forgotpassword').
factory('forgotService', ['$resource',
        function ($resource) {
                return $resource('user/forgotpassword', {}, {
                        sendMail: {
                                method: 'POST',                                                                
                        }
                });
        }
]);