'use strict';

angular.
module('noteslist').
component('noteslist', {
    templateUrl: 'app/homepage/noteslist/noteslist.template.html',
    controller: ['$scope', 'homepageService', '$state',
        function notesListController($scope, homepageService, $state) {

            console.log("Inside notesListController");

            var self = this;

            $scope.mouse = false;

            $scope.notes = [];

            // color picker logic
            $scope.colors = ['transparent','#FF8A80', '#FFD180', '#FFFF8D', '#CFD8DC', '#80D8FF', '#A7FFEB', '#CCFF90'];
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

                // {
                //     "color":                    
                // }, {
                //     "color": 
                    
                // }, {
                //     "color": 
                // }, {
                //     "color": '#fcff77'
                // }, {
                //     "color": '#80ff80'
                // }, {
                //     "color": '#99ffff'
                // }, {
                //     "color": '#0099ff'
                // }, {
                //     "color": '#1a53ff'
                // }, {
                //     "color": '#9966ff'
                // }, {
                //     "color": '#ff99cc'
                // }, {
                //     "color": '#d9b38c'
                // }, {
                //     "color": '#bfbfbf'
                // }



            console.log("HomepageController called");

            $scope.fullNote = false;

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