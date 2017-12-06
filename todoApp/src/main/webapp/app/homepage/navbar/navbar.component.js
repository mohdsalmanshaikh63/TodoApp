'use strict';

angular.
module('navbar').
component('navbar', {
    templateUrl: 'app/homepage/navbar/navbar.template.html',
    controller: ['$rootScope', '$scope', 'homepageService', '$state', 'localStorageService',
        function NavbarController($rootScope, $scope, homepageService, $state, localStorageService) {
            
            
            $scope.toggleNav = function() {
                console.log("Inside toggleNav"+$rootScope.toggled);
                if($rootScope.toggled == true) {
                $rootScope.toggled = false;
                console.log("toggled rootscope to"+$rootScope.toggled);
                } else {                    
                    $rootScope.toggled = true;
                    console.log("toggled rootscope to"+$rootScope.toggled);
                }
                $rootScope.$broadcast('toggled', $rootScope.toggled);
            }

            $rootScope.user = {};
            $scope.user = {}

            var getCurrentUser = homepageService.xyz();
            getCurrentUser.then(function (response) {

                    $scope.user = response.data;
                    $rootScope.user = $scope.user;

                    console.log("Navbar user is " + $scope.user);
                },
                function (error) {

                    console.log("Could not get user!");
                });

            $scope.logout = function () {
                var logoutRequest = homepageService.logoutUser();
                logoutRequest.then(function (response) {

                        $state.go('login');
                        clearLocalStorage();

                    },
                    function (error) {

                        console.log("Could not logout!!");
                    });
            }

            var clearLocalStorage = function () {

                localStorageService.clearAll();
            }

        }
    ]

});