'use strict';

// Register 'login module along with controller and template
angular.
    module('forgotpassword').
    component('forgotpassword', {
        templateUrl: 'app/forgotpassword/forgotpassword.template.html',
        controller: ['$scope', 'forgotService', 'localStorageService',
            function RegisterController($scope, forgotService, localStorageService) {
                console.log("Inside forgotPassword Controller");

                $scope.emailSuccess = false;

                $scope.user = {};              

                if(localStorageService.cookie.isSupported) {
                    console.log("Cookies are supported by this browser");
                  }

                // call register service on form validation
                $scope.submit = function () {
                    console.log($scope.user.email);

                    $scope.forgotService = forgotService.sendMail($scope.user,
                        function (response) {                            
                            console.log("Mail sent successfully"+JSON.stringify(response));

                            // save token in cookie
                            var forgotToken = response.value;
                            console.log("Got the forgotToken as:" + forgotToken);
                            
                            localStorageService.cookie.set('forgotToken', forgotToken, 1);                            

                            // redirect to login page
                        },
                        function () {
                            console.log("Got an error");
                        }
                    )
                }

            }
        ]
    });
