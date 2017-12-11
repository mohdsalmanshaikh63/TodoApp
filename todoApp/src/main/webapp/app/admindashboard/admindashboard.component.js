"use strict";

angular.module("admindashboard").component("admindashboard", {
  templateUrl: "app/admindashboard/admindashboard.template.html",
  controller: [
    "$scope",
    "admindashboardService",
    "$state",
    "$timeout",
    function admindashboardController(
      $scope,
      admindashboardService,
      $state,
      $timeout
    ) {
      console.log("Inside adminDashboardController");

      // utility function called on page load as well useful to call for refreshing all charts
      $scope.pageload = function () {
        reloadBarVariable();
        $scope.loadBarChart();
        $scope.loadPieChart();
        $scope.loadLineChart();
      }

      // utility functions for refreshing individual data
      $scope.refreshBar = function() {
        reloadBarVariable();
        $scope.loadBarChart();
      }
      function reloadBarVariable(){
      // arrays for bar chart
        $scope.notesWithOperations = [];
        $scope.createCount = [];
        $scope.deleteCount = [];
        $scope.dates = [];
      }
      // arrays for pie chart
      $scope.noteDetailsCount = [];
      $scope.noteType = [];
      $scope.typeCount = [];

      // arrays for line chart
      $scope.actionCountList = [];
      $scope.lineSeries = [];
      $scope.lineLabels = [];
      $scope.archiveCount = [];
      $scope.pinnedCount = [];
      $scope.trashCount = [];

      $scope.isBarLoading = true;

      // bar chart for showing daily created and deleted notes according to date
      $scope.loadBarChart = function () {
        var getLog = admindashboardService.getNotesWithOperations();
        getLog.then(
          function (response) {
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

            $scope.data2 = [$scope.createCount, $scope.deleteCount];    
            $scope.labels2 = $scope.dates;
          },
          function (error) {
            console.log("Did not get log");
          }
        );
      }

      $scope.labels2 = $scope.dates || [];
      $scope.series2 = ["Created", "Deleted"];

      $scope.data2 = [$scope.createCount, $scope.deleteCount];      
      // Pie chart for showing notes containing different types of elements
      $scope.loadPieChart = function () {
        var noteDetailsCount = admindashboardService.getNoteDetailsCount();
        setTimeout(function () {
          noteDetailsCount.then(
            function (response) {

              $scope.isBarLoading = false;
              $scope.noteDetailsCount = response.data;
              //console.log($scope.noteDetailsCount);
              for (var i = 0; i < $scope.noteDetailsCount.length; i++) {
                $scope.noteType.push($scope.noteDetailsCount[i].noteType);
                $scope.typeCount.push($scope.noteDetailsCount[i].count);
              }
            },
            function (error) {
              console.log("Error while getting note details count");
            }
          );
        }, 2000);
      }
      /* console.log($scope.noteType);
         console.log($scope.typeCount);             */

      $scope.label = $scope.noteType;
      $scope.data = $scope.typeCount;

      // line chart displaying count of different actions performed daily
      $scope.loadLineChart = function () {
        var getActionCount = admindashboardService.getNotesCountByActionAndDate();
        getActionCount.then(
          function (response) {
            $scope.actionCountList = response.data;
            console.log($scope.actionCountList);

            for (var i = 0; i < $scope.actionCountList.length; i++) {
              // add action categories to labels
              $scope.lineSeries.push($scope.actionCountList[i].category);

              for (var j = 0; j < $scope.actionCountList[i].noteDateCount.length; j++) {
                // push Date into LineLabels
                if (i == 0) {
                  $scope.lineLabels.push(
                    $scope.actionCountList[i].noteDateCount[j].date
                  );
                }

                // add the count of actions for that category
                if ($scope.actionCountList[i].category === "archive") {
                  console.log("Got an Archive");
                  $scope.archiveCount.push(
                    $scope.actionCountList[i].noteDateCount[j].count
                  );
                }
                if ($scope.actionCountList[i].category === "pinned") {
                  console.log("Got a pin");
                  $scope.pinnedCount.push(
                    $scope.actionCountList[i].noteDateCount[j].count
                  );
                }
                if ($scope.actionCountList[i].category === "trash") {
                  console.log("Got a trash");
                  $scope.trashCount.push(
                    $scope.actionCountList[i].noteDateCount[j].count
                  );
                }
              }
            }
            console.log($scope.trashCount);
          },
          function (error) {
            console.log("Error while getting action count");
          }
        );
      }

      /* $scope.lineLabels = [
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July"
      ];
      $scope.lineSeries = ["Archive", "Pinned", "Trash"]; */
      $scope.lineData = [
        $scope.archiveCount,
        $scope.pinnedCount,
        $scope.trashCount
      ];

      $scope.onClick = function (points, evt) {
        console.log(points, evt);
      };

      $scope.datasetOverride = [{
        yAxisID: "y-axis-"
      }];
      $scope.lineOptions = {
        scales: {
          yAxes: [{
            id: "y-axis-",
            type: "linear",
            display: true,
            position: "left",
            ticks: {
              beginAtZero: true
            }
          }]
        }
      };
    }
  ]
});