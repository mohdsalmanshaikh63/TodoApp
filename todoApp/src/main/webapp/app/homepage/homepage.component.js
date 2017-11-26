'use strict';

// Register 'gomepage module along with controller and template
angular.
module('homepage').
component('homepage', {
    templateUrl: 'app/homepage/homepage.template.html',
    controller: ['$scope', 'homepageService', '$state',
        function HomepageController($scope, homepageService, $state) {

            var self = this;

            $scope.mouse =  false;

            $scope.notes = [];


            console.log("HomepageController called");

            $scope.fullNote = false;

            // call the necessary services and make appropiate initializations

            // toggler for main card
            $scope.showFullNote = function () {

                $scope.fullNote = true;
            }

            // getAllNotes
            var getAllNotes = homepageService.getAllNotes();
            getAllNotes.then(function (response) {
                console.log('notes loaded');
                console.log(response.data);
                $scope.notes = (response.data);
            }, function (response) {
                console.log('error loading notes');
            });

            // create new note
            $scope.createNote = function () {

                // get the note data from form
                self.newNote = {};
                self.newNote.title = document.getElementById("mainNoteTitle").innerHTML;
                self.newNote.description = document.getElementById("mainNoteDescription").innerHTML;

                // call the service
                var getAllNotesRequest = homepageService.createNewNote(self.newNote);
                getAllNotesRequest.then(
                    function (response) {
                        console.log("Got the response data as " + JSON.stringify(response));
                        document.getElementById("mainNoteTitle").innerHTML = "";
                        document.getElementById("mainNoteDescription").innerHTML = "";
                        self.newNote.noteId = response.data.noteId;
                        var note = angular.copy(self.newNote);
                        $state.reload();
                        $scope.notes.push(note);
                    },
                    function (error) {
                        console.log("Got the response data as " + error);
                    });

                console.log("Got the note as " + JSON.stringify(self.newNote));

            }

            // archive note
            $scope.archive = function (note) {
               
                    note.archive = true;
                    note.pinned = false;
                

                // call upate service
                var archiveRequest = homepageService.updateNote(note);
                archiveRequest.then(function(response) {
                    
                    for (var i = 0; i < $scope.notes.length; i++)
                    if ($scope.notes[i].noteId == note.noteId) {
                    $scope.notes[i] = note;
                    }
                                       
                    // add to the existing notes array later
                    console.log("Note updated successfully "+$scope.notes);
                },
                function(error) {

                    console.log("Could not update note");
                });

            }

            // trash note
            $scope.trash = function (note) {
                
                                // toggle the boolean variables
                                
                                    note.archive = false;
                                    note.pinned = false;
                                    note.trash =true;                                
                
                                // call upate service
                                var trashRequest = homepageService.updateNote(note);
                                trashRequest.then(function(response) {
                                    //$scope.notes.push(note);

                                    // add to the existing notes array later
                                    console.log("Note updated successfully");
                                },
                                function(error) {                
                                    console.log("Could not update note");
                                });
                
                            }
                            

        }
    ]
});