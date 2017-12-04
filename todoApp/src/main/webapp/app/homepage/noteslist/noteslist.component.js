'use strict';

angular.
    module('noteslist').
    component('noteslist', {
        templateUrl: 'app/homepage/noteslist/noteslist.template.html',
        controller: ['$scope', 'homepageService', '$state', 'mdcDateTimeDialog', '$filter', 'toastr', '$interval', '$sanitize', '$mdDialog',
            function notesListController($scope, homepageService, $state, mdcDateTimeDialog, $filter, toastr, $interval, $sanitize, $mdDialog) {

                console.log("Inside notesListController");

                var self = this;

                $scope.mouse = false;

                $scope.notes = [];

                $scope.fullNote = false;

                // dateTime picker
                $scope.displayDialog = function (note) {
                    mdcDateTimeDialog.show({
                        minDate: new Date(),
                        minuteSteps: 1,
                        shortTime: true,

                    }).then(function (date) {
                        //   var temporaryDate = $filter('date')(date,'yyyy MM dd HH mm a');
                        //   note.reminder = temporaryDate.split(" ");
                        note.reminder = date;

                        //note.reminder = [2017, 11, 28, 11, 12, 56];
                        console.log('New Date / Time selected:', note.reminder);
                        var remindMe = homepageService.updateNote(note);
                        remindMe.then(function (response) {

                            $state.reload();

                            console.log("Note updated successfully");
                        },
                            function (error) {
                                console.log("Could not update note");
                            });


                    }, function () {
                        console.log('Selection canceled');
                    });
                }

                // color picker logic
                $scope.colors = ['transparent', '#FFD180', '#FFFF8D', '#CFD8DC', '#80D8FF', '#A7FFEB', '#CCFF90',
                    '#fcff77', '#80ff80', '#a7686a', '#0099ff', '#1a53ff', '#9966ff', '#ff99cc', '#d9b38c', '#bfbfbf'
                ];
                $scope.color = '#FF8A80';

                $scope.colorChanged = function (newColor, oldColor, note) {
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
                    $scope.notes = (response.data);

                    // toaster
                    $interval(function () {

                        for (var i = 0; i < response.data.length; i++) {
                            if (response.data[i].reminder) {
                                var date = new Date(response.data[i].reminder);
                                if ($filter('date', 'yyyy-MM-ddTHH:mm')(date) == $filter('date', 'yyyy-MM-ddTHH:mm')(new Date())) {
                                    console.log("Sanitize" + $sanitize('Hello, <b>World</b>!'));
                                    console.log("Sanitize" + $sanitize());
                                    console.log("Test--" + $sanitize(response.data[i].description) + " " + $sanitize(response.data[i].title))
                                    toastr.success($sanitize(response.data[i].description), $sanitize(response.data[i].title));
                                }
                            }
                        }
                    }, 60000);

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

                // SOCIAL SHARE
                $scope.socialShare = function (note) {
                    console.log(note.title);
                    console.log('inside fbAsyncInit');
                    FB.init({
                        appId: '876789189147877',
                        status: true,
                        cookie: true,
                        xfbml: true,
                        version: 'v2.4'
                    });

                    FB.ui({
                        method: 'share_open_graph',
                        action_type: 'og.likes',
                        action_properties: JSON.stringify({
                            object: {
                                'og:title': note.title,
                                'og:description': note.description
                            }
                        })
                    }, function (response) {
                        if (response && !response.error_message) {
                            toastr.success("Note shared successfully!");
                        } else {
                            toastr.success("Note could not be shared!");
                        }
                    });
                };

                //Image uploader
                $scope.openImageUploader = function (type) {
                    $scope.type = type;
                    $('#image').trigger('click');
                    return false;
                }


                $scope.stepsModel = [];
                $scope.imageUpload = function (element) {
                    var reader = new FileReader();
                    reader.onload = $scope.imageIsLoaded;
                    reader.readAsDataURL(element.files[0]);
                }

                $scope.imageIsLoaded = function (e) {
                    $scope.$apply(function () {
                        $scope.stepsModel.push(e.target.result);

                        var imageSrc = e.target.result;
                        $scope.type.image = imageSrc;



                        // call upate service
                        var archiveRequest = homepageService.updateNote($scope.type);
                        archiveRequest.then(function (response) {

                            $state.reload();

                        },
                            function (error) {

                                console.log("Could not update note");
                            });

                    });
                }

                // note edit dialog
                $scope.editDialog = function (event, note) {
                    $mdDialog.show({
                        controller: DialogController,
                        templateUrl: 'app/homepage/noteslist/tabDialog.tmpl.html',
                        parent: angular.element(document.body),
                        targetEvent: event,
                        clickOutsideToClose: true
                    })
                        .then(function (answer) {
                            $scope.status = 'You said the information was "' + answer + '".';
                        }, function () {
                            $scope.status = 'You cancelled the dialog.';
                        });
                }

                function DialogController($scope, $mdDialog) {
                    $scope.hide = function () {
                        $mdDialog.hide();
                    };

                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                }
            }
        ]
    });