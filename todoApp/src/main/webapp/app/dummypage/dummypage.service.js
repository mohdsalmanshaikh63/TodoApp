angular.module('dummypage').
factory('dummypageService', ['$http',
        function ($http) {
                    
                var token = {}
                
                token.getTokens = function (socialToken) {

                        console.log("Getting tokens");
                        return $http({
                                method: 'GET',
                                url: 'getTokens',                                
                                headers: {
                                        'Content-Type': 'application/json',
                                        'socialToken': socialToken
                                }
                        });
                }

                return token;

        }
         
]);