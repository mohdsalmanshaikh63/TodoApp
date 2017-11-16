'use strict';

// Register 'login module along with controller and template
angular.
    module('resetpassword').
    component('resetpassword', {
        templateUrl: 'app/resetpassword/resetpassword.template.html',
        controller: ['$scope', 'resetpasswordService', 'localStorageService',
            '$state', '$stateParams',
            function ResetController($scope, resetpasswordService, localStorageService, $state, $stateParams) {
                $scope.user = {};

                console.log("ResetController called");

                var forgotCookie = localStorageService.cookie.get('forgotToken');

                console.log("Got the forgotCookie as " + forgotCookie);

                var forgotToken = $stateParams.token;

               // console.log("Got the forgotToken as " + forgotToken);

                // redirect to login page if no cookie exists
                if (forgotCookie == null || forgotCookie != forgotToken ) {
                    $state.go('login');
                }                                     
                // call reset service on form validation
                $scope.reset = function () {
                    console.log("Password and confirm password are "+$scope.user);

                    // call the login service
                    $scope.login = resetpasswordService.reset(forgotToken).post($scope.user,

                        function (response) {                            
                            console.log("Password reset successfully");
                            localStorageService.cookie.remove('forgotToken');

                        },
                        function (error) {
                            console.log("Got an error " + error);
                        })
                }
                

            }
        ]
    });