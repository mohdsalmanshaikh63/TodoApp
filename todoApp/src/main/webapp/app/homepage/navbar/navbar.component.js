'use strict';

angular.
module('navbar').
component('navbar', {
    templateUrl: 'app/homepage/navbar/navbar.template.html',
    controller: ['$rootScope', '$scope', 'homepageService', '$state',
        function NavbarController($rootScope, $scope, homepageService, $state) {

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

        }
    ]

});