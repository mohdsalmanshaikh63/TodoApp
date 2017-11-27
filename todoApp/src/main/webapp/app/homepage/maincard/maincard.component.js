'use strict';

angular.
    module('maincard').
    component('maincard', {
        templateUrl: 'app/homepage/maincard/maincard.template.html',
        controller: ['$scope', 'homepageService', '$state',
         function mainCardController($scope, homepageService, $state) {

            console.log("Inside maincardController");

            var self = this;

            // toggler for main card
            $scope.showFullNote = function () {

                $scope.fullNote = true;
            }

            // create new note
            $scope.createNote = function () {

                // get the note data from form
                self.newNote = {};
                self.newNote.title = document.getElementById("mainNoteTitle").innerHTML;
                self.newNote.description = document.getElementById("mainNoteDescription").innerHTML;

                // call the service
                var createNoteRequest = homepageService.createNewNote(self.newNote);
                createNoteRequest.then(
                    function (response) {
                        console.log("Got the response data as " + JSON.stringify(response));
                        document.getElementById("mainNoteTitle").innerHTML = "";
                        document.getElementById("mainNoteDescription").innerHTML = "";
                        self.newNote.noteId = response.data.noteId;
                        var note = angular.copy(self.newNote);
                        $state.reload();                        
                    },
                    function (error) {
                        console.log("Got the response data as " + error);
                    });

                console.log("Got the note as " + JSON.stringify(self.newNote));

            }

        }]
    });