'use strict';

angular.
module('admindashboard').
component('admindashboard', {
    templateUrl: 'app/admindashboard/admindashboard.template.html',
    controller: ['$scope', 'admindashboardService', '$state', '$timeout',
        function admindashboardController($scope, admindashboardService, $state, $timeout) {

            console.log("Inside adminDashboardController");
            
            // arrays for bar chart
            $scope.notesWithOperations = [];
            $scope.createCount = [];
            $scope.deleteCount = [];

            // arrays for pie chart
            $scope.dates = [];
            $scope.noteDetailsCount = [];
            $scope.noteType = [];
            $scope.typeCount = []

            // bar chart for showing daily created and deleted notes according to date
            var getLog = admindashboardService.getNotesWithOperations();
            getLog.then(function (response) {
                $scope.notesWithOperations = response.data;
                //console.log($scope.notesWithOperations);

                for (var i = 0; i < $scope.notesWithOperations.length; i++) {

                    // push into appropiate counter
                    if ($scope.notesWithOperations[i][1] == "CREATE") {
                        //console.log("create note found");
                        $scope.createCount.push($scope.notesWithOperations[i][2]);
                    } else {
                        //console.log("delete note found");
                        $scope.deleteCount.push($scope.notesWithOperations[i][2]);
                    }

                    if (i % 2 == 0) {
                        $scope.dates.push($scope.notesWithOperations[i][0]);
                    }
                }
            }, function (error) {
                console.log("Did not get log");
            });

            $scope.labels2 = $scope.dates;
            $scope.series2 = ['Created', 'Deleted'];

            $scope.data2 = [
                $scope.createCount,
                $scope.deleteCount
            ];

            // Pie chart for showing notes containing different types of elements
            var noteDetailsCount = admindashboardService.getNoteDetailsCount();
            noteDetailsCount.then(function (response) {
                $scope.noteDetailsCount = response.data;
                //console.log($scope.noteDetailsCount);
                for(var i = 0; i < $scope.noteDetailsCount.length; i++) {                    
                    $scope.noteType.push($scope.noteDetailsCount[i].noteType);
                    $scope.typeCount.push($scope.noteDetailsCount[i].count);
                }                
            }, function (error) {
                console.log("Error while getting note details count");
            });

            console.log($scope.noteType);
            console.log($scope.typeCount);

            /* $scope.label = $scope.noteType;
            $scope.data = $scope.noteDetailsCount; */

            $scope.label = $scope.noteType;
            $scope.data = $scope.typeCount;

        }
    ]
});