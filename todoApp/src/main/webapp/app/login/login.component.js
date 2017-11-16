'use strict';

// Register 'login module along with controller and template
angular.
    module('login').
    component('login', {
        templateUrl: 'app/login/login.template.html',
        controller: ['$scope', 'loginService', 'localStorageService',
            function LoginController($scope, loginService, localStorageService) {
                $scope.user = {};

                console.log("LoginController called");

                // call login service on form validation
                $scope.submit = function () {
                    console.log($scope.user);

                    // call the login service
                    $scope.login = loginService.query($scope.user,

                        // save the tokens in local storage on success
                        function (response) {                            
                            var accessToken = response.accessToken.value;
                            var refreshToken = response.refreshToken.value;
                            console.log("Got the accessToken as:" + accessToken);
                            console.log("Got the refreshToken as:" + refreshToken);

                            localStorageService.set('accessToken', accessToken);
                            localStorageService.set('refreshToken', refreshToken);

                        },
                        function (error) {
                            console.log("Got an error " + error.data.responseStatusCode);                            
                        })
                }
            }
        ]
    });