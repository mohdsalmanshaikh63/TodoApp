'use strict';

angular.
module('admindashboard').
component('admindashboard', {
    templateUrl: 'app/admindashboard/admindashboard.template.html',
    controller: ['$scope', 'admindashboardService', '$state', '$timeout',
        function admindashboardController($scope, admindashboardService, $state, $timeout) {

            console.log("Inside adminDashboardController");

            $scope.notesLogs = [];
            var getLog = admindashboardService.getAllNoteLogs();
            getLog.then(function(response) {
                console.log(response.data);
            }, function(error) {
                console.log("Did not get log");
            });
            
            $scope.labels1 = [new Date(), "In-Store Sales", "Mail-Order Sales"];
            $scope.data1 = [300, 500, 100];

            $scope.labels2 = ['2006', '2007', '2008', '2009', '2010', '2011', '2012'];
            $scope.series2 = ['Series A', 'Series B'];
        
            $scope.data2 = [
              [65, 59, 80, 81, 56, 55, 40],
              [28, 48, 40, 19, 86, 27, 90]
            ];
        }
    ]
});