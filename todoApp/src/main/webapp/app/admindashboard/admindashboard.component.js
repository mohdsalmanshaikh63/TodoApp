'use strict';

angular.
module('admindashboard').
component('admindashboard', {
    templateUrl: 'app/admindashboard/admindashboard.template.html',
    controller: ['$scope', 'admindashboardService', '$state', '$timeout',
        function admindashboardController($scope, admindashboardService, $state, $timeout) {

            console.log("Inside adminDashboardController");

            

            $scope.notesLogs = [];
            $scope.notesWithOperations = [];
            $scope.createCount = [];
            $scope.deleteCount = [];
            $scope.dates = [];

            /* var getLog = admindashboardService.getAllNoteLogs();
            getLog.then(function(response) {
                console.log("Got the note logs");
                $scope.notesLogs = response.data;
            }, function(error) {
                console.log("Did not get log");
            }); */
            
            var getLog = admindashboardService.getNotesWithOperations();
            getLog.then(function(response) {                
                $scope.notesWithOperations = response.data;
                console.log($scope.notesWithOperations);

                for(var i= 0; i <$scope.notesWithOperations.length; i++) {

                    // push into appropiate counter
                    if($scope.notesWithOperations[i][1] == "CREATE") {
                        console.log("create note found");
                        $scope.createCount.push($scope.notesWithOperations[i][2]);
                    } else {
                        console.log("delete note found");
                        $scope.deleteCount.push($scope.notesWithOperations[i][2]);
                    }
                    
                    if(i % 2 == 0) {
                        $scope.dates.push($scope.notesWithOperations[i][0]);
                    }
                }
            }, function(error) {
                console.log("Did not get log");
            });
                        

            $scope.labels1 = [new Date(), "In-Store Sales", "Mail-Order Sales"];
            $scope.data1 = [300, 500, 100];

            $scope.labels2 = $scope.dates;
            $scope.series2 = ['Created', 'Deleted'];

            console.log($scope.createCount);
            console.log($scope.deleteCount);
        
            $scope.data2 = [
              $scope.createCount,
              $scope.deleteCount
            ];
        }
    ]
});