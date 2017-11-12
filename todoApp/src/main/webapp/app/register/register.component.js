'use strict';

// Register 'login module along with controller and template
angular.
    module('register').
    component('register', {
        templateUrl: 'app/register/register.template.html',
        controller: ['$scope', 'registerService',
            function RegisterController($scope, registerService) {
                console.log("Inside registerController");
                var self = this;

                // regex pattern for mobile no
                self.phoneNumber = /^\d{10}$/;
                self.name = /^[a-zA-Z]+$/;

                // call register service on form validation
                $scope.submit = function () {
                    console.log($scope.user);

                    $scope.register = registerService.register($scope.user,
                        function () {

                            // redirect to login page
                            console.log("Registration successful");
                        },
                        function () {
                            console.log("Got an error");
                        }
                    )
                }

            }
        ]
    });
