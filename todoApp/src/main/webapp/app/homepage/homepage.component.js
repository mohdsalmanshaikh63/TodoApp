'use strict';

// Register 'gomepage module along with controller and template
angular.
module('homepage').
component('homepage', {
    templateUrl: 'app/homepage/homepage.template.html',
    controller: ['$scope', 'homepageService',
        function HomepageController($scope, homepageService) {

            var self = this;

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
                var httpRequest = homepageService.createNewNote(self.newNote);
                httpRequest.then(
                    function (response) {
                        console.log("Got the response data as " + JSON.stringify(response));
                        document.getElementById("mainNoteTitle").innerHTML = "";
                        document.getElementById("mainNoteDescription").innerHTML = "";
                    },
                    function (error) {
                        console.log("Got the response data as " + error);
                    });

                console.log("Got the note as " + JSON.stringify(self.newNote));

            }
        }
    ]
});