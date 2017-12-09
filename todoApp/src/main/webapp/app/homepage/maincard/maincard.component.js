'use strict';

angular.
module('maincard').
component('maincard', {
    templateUrl: 'app/homepage/maincard/maincard.template.html',
    controller: ['$scope', 'homepageService', '$state',
        function mainCardController($scope, homepageService, $state) {

            console.log("Inside maincardController");

            

            // toggler for main card
            $scope.showFullNote = function () {

                $scope.fullNote = true;
            }

            // create new note
            $scope.createNote = function () {

                // get the note data from form
                $scope.newNote = {};
                $scope.newNote.title = document.getElementById("mainNoteTitle").innerHTML;
                $scope.newNote.description = document.getElementById("mainNoteDescription").innerHTML;
                $scope.newNote.image=$scope.addimage;
                $scope.createImageSrc="";
                $scope.addimage="";
                // call the service
                var createNoteRequest = homepageService.createNewNote($scope.newNote);
                createNoteRequest.then(
                    function (response) {                        
                        document.getElementById("mainNoteTitle").innerHTML = "";
                        document.getElementById("mainNoteDescription").innerHTML = "";
                        $scope.newNote.noteId = response.data.noteId;
                        var note = angular.copy($scope.newNote);
                        $state.reload();
                    },
                    function (error) {
                        console.log("Got the response data as " + error);
                    });

                

            }

            //Image uploader
            $scope.openImageUploader = function (type) {
                
                $("#newimage").trigger("click");
                return false;
            };

            $scope.stepsModel = [];
            $scope.imageUpload = function (element) {
                var reader = new FileReader();
                reader.onload = $scope.imageIsLoaded;
                reader.readAsDataURL(element.files[0]);
            };

            $scope.imageIsLoaded = function (e) {
                $scope.$apply(function () {
                    $scope.stepsModel.push(e.target.result);

                    $scope.createImageSrc = e.target.result;
                    console.log($scope.createImageSrc);
                    $scope.addimage = $scope.createImageSrc;

                    
                    
                });
            };

        }
    ]
});