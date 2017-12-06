'use strict';

angular.
module('sidenav').
component('sidenav', {
    templateUrl: 'app/homepage/sidenav/sidenav.template.html',
    controller: ['$rootScope', '$scope', '$mdSidenav',
    function NavbarController($rootScope, $scope, $mdSidenav) {

        console.log("Got the rootScope as"+$rootScope.toggled);
        $scope.toggle = true;

        $scope.$on('toggled', function(event, data) {
            console.log("Inside broadcast event"+data);
            if(data == false) {
                $mdSidenav('left').close();
            } else {
                $mdSidenav('left').open();
            }
            $scope.toggle = data;
        });
    }
    ]});