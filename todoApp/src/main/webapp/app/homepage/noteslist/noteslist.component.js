'use strict';

angular.
module('noteslist').
component('noteslist', {
    templateUrl: 'app/homepage/noteslist/noteslist.template.html',
    controller: ['$scope', 'homepageService', '$state', 'mdcDateTimeDialog', '$filter',
        function notesListController($scope, homepageService, $state, mdcDateTimeDialog, $filter) {

            console.log("Inside notesListController");

            var self = this;

            $scope.mouse = false;

            $scope.notes = [];

            $scope.fullNote = false;

            // dateTime picker
            $scope.displayDialog = function (note) {
                mdcDateTimeDialog.show({
                  minDate:  new Date(),
                  minuteSteps: 1 ,
                  shortTime : true                                                      ,
                  
                }).then(function (date) {
                //   var temporaryDate = $filter('date')(date,'yyyy MM dd HH mm a');
                //   note.reminder = temporaryDate.split(" ");
                note.reminder = date;

                  //note.reminder = [2017, 11, 28, 11, 12, 56];
                  console.log('New Date / Time selected:', note.reminder );
                  var remindMe = homepageService.updateNote(note);
                  remindMe.then(function (response) {
  
                          $state.reload();
  
                          console.log("Note updated successfully");
                      },
                      function (error) {
                          console.log("Could not update note");
                      });
                  

                }, function() {
                  console.log('Selection canceled');
                });
            }

            // color picker logic
            $scope.colors = ['transparent','#FF8A80', '#FFD180', '#FFFF8D', '#CFD8DC', '#80D8FF', '#A7FFEB', '#CCFF90',
                            '#fcff77', '#80ff80', '#99ffff', '#0099ff', '#1a53ff', '#9966ff', '#ff99cc', '#d9b38c', '#bfbfbf' ];
            $scope.color = '#FF8A80';
        
            $scope.colorChanged = function(newColor, oldColor,note) {
                console.log('from ', oldColor, ' to ', newColor);
                note.color = newColor;
                var colorChange = homepageService.updateNote(note);
                colorChange.then(function (response) {

                        $state.reload();

                        console.log("Note updated successfully");
                    },
                    function (error) {
                        console.log("Could not update note");
                    });
            }                        

            // call the necessary services and make appropiate initializations            

            // getAllNotes
            var getAllNotes = homepageService.getAllNotes();
            getAllNotes.then(function (response) {
                console.log('notes loaded');
                console.log(response.data);
                $scope.notes = (response.data);
            }, function (response) {
                console.log('error loading notes');
            });

            // pin note
            $scope.pin = function (note) {

                if (note.pinned == false) {
                    note.archive = false;
                    note.pinned = true;
                } else {
                    note.pinned = false;
                }

                // call upate service
                var archiveRequest = homepageService.updateNote(note);
                archiveRequest.then(function (response) {

                        $state.reload();

                        console.log("Note updated successfully " + $scope.notes);
                    },
                    function (error) {

                        console.log("Could not update note");
                    });

            }

            // archive note
            $scope.archive = function (note) {

                if (note.archive == false) {
                    note.archive = true;
                    note.pinned = false;
                } else {
                    note.archive = false;
                }


                // call upate service
                var archiveRequest = homepageService.updateNote(note);
                archiveRequest.then(function (response) {

                        $state.reload();

                        // add to the existing notes array later
                        console.log("Note updated successfully " + $scope.notes);
                    },
                    function (error) {

                        console.log("Could not update note");
                    });

            }

            // trash note
            $scope.trash = function (note) {

                // toggle the boolean variables

                note.archive = false;
                note.pinned = false;
                note.trash = true;

                // call upate service
                var trashRequest = homepageService.updateNote(note);
                trashRequest.then(function (response) {

                        $state.reload();

                        console.log("Note updated successfully");
                    },
                    function (error) {
                        console.log("Could not update note");
                    });

            }

        }
    ]
});