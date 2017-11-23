'use strict';

// Register 'login module along with controller and template
angular.
module('homepage').
component('homepage', {
    templateUrl: 'app/homepage/homepage.template.html',
    controller: ['$scope', 'homepageService',
        function HomepageController($scope, homepageService) {

            var self = this;

            console.log("HomepageController called");

            $scope.fullNote = false;

            // call the necessary services and make appropiate initializations
            $scope.showFullNote = function () {



                $scope.fullNote = true;
            }

            $scope.createNote = function () {

                // get the note data from form
                self.newNote = {};
                self.newNote.title = document.getElementById("mainNoteTitle").innerHTML;
                self.newNote.description = document.getElementById("mainNoteDescription").innerHTML;

                // call the service
                homepageService.createNewNote(self.newNote,
                    function (response) {
                        console.log("Got the response data as " + JSON.stringify(response.data));
                        document.getElementById("mainNoteTitle").innerHTML = "";
                        document.getElementById("mainNoteDescription").innerHTML = "";
                    },
                function(error) {
                    console.log("Got the response data as " + JSON.stringify(error));
                });

                

                console.log("Got the note as " + JSON.stringify(self.newNote));

            }
        }
    ]
});