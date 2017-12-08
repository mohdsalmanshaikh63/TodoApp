'use strict';

angular.
module('admindashboard').
component('admindashboard', {
    templateUrl: 'app/admindashboard/admindashboard.template.html',
    controller: ['$scope', 'admindashboardService', '$state', '$timeout',
        function admindashboardController($scope, admindashboardService, $state, $timeout) {

            console.log("Inside adminDashboardController");

            $scope.labels = ["Download Sales", "In-Store Sales", "Mail-Order Sales"];
            $scope.data = [300, 500, 100];

        }
    ]
});