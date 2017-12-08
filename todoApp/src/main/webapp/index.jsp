<!DOCTYPE html>
<html lang="en" ng-app="todoApp">

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>My AngularJS App</title>
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- Google Roboto font -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,400italic">
  <!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"> -->

  <!-- Font awesome -->
  <link rel="stylesheet" href="app/bower_components/font-awesome/css/font-awesome.min.css">

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

  <!-- Css dependencies -->
  <%-- <link rel="stylesheet" href="app/bower_components/angular-material/angular-material.css" > --%>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.0/angular-material.min.css">
    <link rel="stylesheet" href="app/app.css">
    <link rel="stylesheet" href="app/login/login.css">
    <link rel="stylesheet" href="app/register/register.css">
    <link rel="stylesheet" href="app/homepage/homepage.css">
    <link rel="stylesheet" href="app/homepage/navbar/navbar.css">
    <link rel="stylesheet" href="app/homepage/sidenav/sidenav.css">
    <link rel="stylesheet" href="app/homepage/maincard/maincard.css">
    <link rel="stylesheet" href="app/homepage/noteslist/noteslist.css">
    <link rel="stylesheet" href="app/admindashboard/admindashboard.css">

    <!-- Google material icons font's -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <!-- angular dependencies -->
    <!-- Angular Material requires Angular.js Libraries -->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-animate.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-aria.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-messages.min.js"></script>

    <!-- Color picker -->
    <script src="app/bower_components/colorpicker/dist/colorPicker.js"></script>
    <link rel="stylesheet" href="app/bower_components/colorpicker/dist/colorPickerStyle.css">

    <!-- dateTime Picker -->
    <script src="app/bower_components/moment/moment.js"></script>
    <script src="app/bower_components/ng-material-datetimepicker/dist/angular-material-datetimepicker.min.js"></script>
    <script src="app/bower_components/ng-material-datetimepicker/dist/angular-material-datetimepicker.min.js.map"></script>
    <link rel="stylesheet" href="app/bower_components/ng-material-datetimepicker/dist/material-datetimepicker.min.css">

      <!-- Angular Material Library -->
      <script src="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.0/angular-material.min.js"></script>      

        <script src="app/bower_components/angular-resource/angular-resource.js"></script>
        <script src="app/bower_components/angular-ui-router/release/angular-ui-router.js"></script>
       
          <!-- confirm password library -->
          <script src="app/bower_components/angular-password/angular-password.js"></script>

          <!-- local storage library -->
          <script src="app/bower_components/angular-local-storage/dist/angular-local-storage.js"></script>

          <!-- Ng sanitizer for cleaning html -->
          <script src="app/bower_components/angular-sanitize/angular-sanitize.js"></script>

          <!-- Toaster dependencies -->
          <link rel="stylesheet" href="app/bower_components/angular-toastr/dist/angular-toastr.css" />
          <script src="app/bower_components/angular-toastr/dist/angular-toastr.tpls.js"></script>

          <!-- Chart dependencies -->
          <script src="app/bower_components/chart.js/dist/Chart.min.js"></script>
          <script src="app/bower_components/angular-chart.js/dist/angular-chart.min.js"></script>


          <!-- core dependencies -->
          <script src="app/app.module.js"></script>
          <script src="app/app.config.js"></script>

          <!-- login module and it's dependencies -->
          <script src="app/login/login.module.js"></script>
          <script src="app/login/login.component.js"></script>
          <script src="app/login/login.service.js"></script>

          <!-- register module and it's dependencies -->
          <script src="app/register/register.module.js"></script>
          <script src="app/register/register.component.js"></script>
          <script src="app/register/register.service.js"></script>

          <!-- forgotpassword module and it's dependencies -->
          <script src="app/forgotpassword/forgotpassword.module.js"></script>
          <script src="app//forgotpassword/forgotpassword.component.js"></script>
          <script src="app/forgotpassword/forgotpassword.service.js"></script>

          <!-- resetpassword module and it's dependencies -->
          <script src="app/resetpassword/resetpassword.module.js"></script>
          <script src="app/resetpassword/resetpassword.component.js"></script>
          <script src="app/resetpassword/resetpassword.service.js"></script>

          <!-- dummypage module and it's dependencies -->
          <script src="app/dummypage/dummypage.module.js"></script>
          <script src="app/dummypage/dummypage.component.js"></script>
          <script src="app/dummypage/dummypage.service.js"></script>

          <!-- homepage module and it's dependencies -->
          <script src="app/homepage/homepage.module.js"></script>
          <script src="app/homepage/homepage.component.js"></script>
          <!-- <script src="${pageContext.request.contextPath}/app/homepage/homepage.controller.js"></script> -->
          <script src="app/homepage/homepage.service.js"></script>

          <!-- Load navbar and it's dependencies -->
          <script src="app/homepage/navbar/navbar.module.js"></script>
          <script src="app/homepage/navbar/navbar.component.js"></script>

          <!-- Load navbar and it's dependencies -->
          <script src="app/homepage/sidenav/sidenav.module.js"></script>
          <script src="app/homepage/sidenav/sidenav.component.js"></script>

          <!-- Load maincard and it's dependencies -->
          <script src="app/homepage/maincard/maincard.module.js"></script>
          <script src="app/homepage/maincard/maincard.component.js"></script>

          <!-- Load notesList and it's dependencies -->
          <script src="app/homepage/noteslist/noteslist.module.js"></script>
          <script src="app/homepage/noteslist/noteslist.component.js"></script>

          <!-- admindashboard module and it's dependencies -->
          <script src="app/admindashboard/admindashboard.module.js"></script>
          <script src="app/admindashboard/admindashboard.component.js"></script>
          <script src="app/admindashboard/admindashboard.service.js"></script>

          <!-- Facebook login -->
          <script src="https://connect.facebook.net/en_US/sdk.js"></script>

          <!-- Load image upload and it's dependencies -->
          <script src="app/bower_components/ng-file-upload/ng-file-upload-shim.min.js"></script>
          <script src="app/bower_components/ng-file-upload/ng-file-upload.min.js"></script>


</head>

<body>

  <!-- router view -->
  <div class="view-container" ng-cloak>
    <div ui-view class="view-frame"></div>
  </div>

</body>

</html>