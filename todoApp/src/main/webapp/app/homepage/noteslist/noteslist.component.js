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

                // archive note
                $scope.archive = function (note) {

                    note.archive = true;
                    note.pinned = false;


                    // call upate service
                    var archiveRequest = homepageService.updateNote(note);
                    archiveRequest.then(function (response) {

                        for (var i = 0; i < $scope.notes.length; i++)
                            if ($scope.notes[i].noteId == note.noteId) {
                                $scope.notes[i] = note;
                            }

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
                        //$scope.notes.push(note);

                        // add to the existing notes array later
                        console.log("Note updated successfully");
                    },
                        function (error) {
                            console.log("Could not update note");
                        });

                }

            }]
    });