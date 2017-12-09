"use strict";

angular.module("noteslist").component("noteslist", {
  templateUrl: "app/homepage/noteslist/noteslist.template.html",
  controller: [
    "$scope",
    "homepageService",
    "$state",
    "mdcDateTimeDialog",
    "$filter",
    "toastr",
    "$interval",
    "$mdDialog",
    function notesListController(
      $scope,
      homepageService,
      $state,
      mdcDateTimeDialog,
      $filter,
      toastr,
      $interval,
      $mdDialog
    ) {
      console.log("Inside notesListController");

      var self = this;

      $scope.mouse = false;

      $scope.notes = [];

      $scope.fullNote = false;

      // dateTime picker
      $scope.displayDialog = function (note) {
        mdcDateTimeDialog
          .show({
            minDate: new Date(),
            minuteSteps: 1,
            shortTime: true
          })
          .then(
            function (date) {
              //   var temporaryDate = $filter('date')(date,'yyyy MM dd HH mm a');
              //   note.reminder = temporaryDate.split(" ");
              note.reminder = date;

              //note.reminder = [2017, 11, 28, 11, 12, 56];
              console.log("New Date / Time selected:", note.reminder);
              $scope.updateNote(note);
            },
            function () {
              console.log("Selection canceled");
            }
          );
      };

      // color picker logic
      $scope.colors = [
        "transparent",
        "#FFD180",
        "#FFFF8D",
        "#CFD8DC",
        "#80D8FF",
        "#A7FFEB",
        "#CCFF90",
        "#fcff77",
        "#80ff80",
        "#a7686a",
        "#0099ff",
        "#1a53ff",
        "#9966ff",
        "#ff99cc",
        "#d9b38c",
        "#bfbfbf"
      ];
      $scope.color = "#FF8A80";

      $scope.colorChanged = function (newColor, oldColor, note) {
        console.log("from ", oldColor, " to ", newColor);
        note.color = newColor;
        $scope.updateNote(note);
      };

      // call the necessary services and make appropiate initializations

      // getAllNotes
      var getAllNotes = homepageService.getAllNotes();
      getAllNotes.then(
        function (response) {
          console.log("notes loaded");
          $scope.notes = response.data;
          console.log($scope.notes);

          // toaster
          $interval(function () {
            for (var i = 0; i < response.data.length; i++) {
              if (response.data[i].reminder) {
                var date = new Date(response.data[i].reminder);
                if (
                  $filter("date", "yyyy-MM-ddTHH:mm")(date) ==
                  $filter("date", "yyyy-MM-ddTHH:mm")(new Date())
                ) {
                  console.log("Sanitize" + "Hello, <b>World</b>!");
                  console.log(
                    "Test--" +
                    response.data[i].description +
                    " " +
                    response.data[i].title
                  );
                  toastr.success(
                    DOMPurify.sanitize(response.data[i].description),
                    DOMPurify.sanitize(response.data[i].title)
                  );
                }
              }
            }
          }, 60000);
        },
        function (response) {
          console.log("error loading notes");
        }
      );

      // pin note
      $scope.pin = function (note) {
        if (note.pinned == false) {
          note.archive = false;
          note.pinned = true;
        } else {
          note.pinned = false;
        }
        $scope.updateNote(note);
      };

      // archive note
      $scope.archive = function (note) {
        if (note.archive == false) {
          note.archive = true;
          note.pinned = false;
        } else {
          note.archive = false;
        }

        $scope.updateNote(note);
      };

      // trash note
      $scope.trash = function (note) {
        // toggle the boolean variables

        note.archive = false;
        note.pinned = false;
        note.trash = true;

        // call upate service
        $scope.updateNote(note);
      };

      // delete note
      $scope.delete = function (noteId) {
        homepageService.deleteNote(noteId)
          .then(
            function (response) {
              $state.reload();

              console.log("Note deleted successfully");
            },
            function (error) {
              console.log("Could not update note");
            }
          );
      }

      // SOCIAL SHARE
      $scope.socialShare = function (note) {
        console.log(note.title);
        console.log("inside fbAsyncInit");
        FB.init({
          appId: "876789189147877",
          status: true,
          cookie: true,
          xfbml: true,
          version: "v2.4"
        });

        FB.ui({
            method: "share_open_graph",
            action_type: "og.likes",
            action_properties: JSON.stringify({
              object: {
                "og:title": note.title,
                "og:description": note.description
              }
            })
          },
          function (response) {
            if (response && !response.error_message) {
              toastr.success("Note shared successfully!");
            } else {
              toastr.success("Note could not be shared!");
            }
          }
        );
      };

      //Image uploader
      $scope.openImageUploader = function (type) {
        $scope.type = type;
        $("#image").trigger("click");
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

          var imageSrc = e.target.result;
          $scope.type.image = imageSrc;

          // call upate service
          $scope.updateNote($scope.type);
        });
      };

      // note edit dialog
      $scope.editDialog = function (event, note) {
        $mdDialog
          .show({
            locals: {
              dataToPass: note // Pass the note data into dialog box
            },
            controllerAs: 'controller',
            controller: DialogController,
            templateUrl: "app/homepage/noteslist/tabDialog.tmpl.html",
            parent: angular.element(document.body),
            targetEvent: event,
            clickOutsideToClose: false
          });
      };

      function DialogController($scope, $mdDialog, dataToPass) {
        console.log("note is" + dataToPass);
        $scope.hide = function () {
          $mdDialog.hide();
        };

        $scope.cancel = function () {
          $mdDialog.cancel();
        };

        $scope.mdDialogData = dataToPass;

        // remove image from the note
        $scope.removeImage = function (mdDialogData) {
          mdDialogData.image = null;
          $scope.updateNote(mdDialogData);
        };

        $scope.saveUpdatedNote = function () {
          console.log("Inside edit update");
          dataToPass.title = document.getElementById('editNoteTitle').innerHTML;
          dataToPass.description = document.getElementById('editNoteDescription').innerHTML;
          $scope.updateNote(dataToPass);
          $scope.hide();
        }        

        /* dataToPass.title = document.getElementById(
          "updatedNoteTitle"
        ).innerHTML;

        dataToPass.body = document.getElementById("updatedNoteBody").innerHTML; */

        //updateNote(dataToPass);
        //$scope.hide();
      }

      // generic update function
      $scope.updateNote = function(note) {
        var update = homepageService.updateNote(note);
        update.then(
          function (response) {
            $state.reload();

            console.log("Note updated successfully");
          },
          function (error) {
            console.log("Could not update note");
          }
        );
      }

    }
  ]
});