<!DOCTYPE html>
<html lang="en" ng-app="todoApp">

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>My AngularJS App</title>
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,400italic">

  <!-- Css dependencies -->
  <%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/app/bower_components/angular-material/angular-material.css" > --%>
  <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.0/angular-material.min.css">  
  <link rel="stylesheet" href="${pageContext.request.contextPath}/app/app.css">  
  <link rel="stylesheet" href="${pageContext.request.contextPath}/app/login/login.css">  
  <link rel="stylesheet" href="${pageContext.request.contextPath}/app/register/register.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/app/homepage/homepage.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/app/homepage/navbar/navbar.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/app/homepage/sidenav/sidenav.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/app/homepage/maincard/maincard.css">
  
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  
  
  <!-- angular dependencies -->
  <!-- Angular Material requires Angular.js Libraries -->
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-animate.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-aria.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-messages.min.js"></script>

  <!-- Angular Material Library -->
  <script src="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.0/angular-material.min.js"></script>
  <%-- <script src="${pageContext.request.contextPath}/app/bower_components/angular/angular.js"></script>
  <script src="${pageContext.request.contextPath}/app/bower_components/angular-animate/angular-animate.js"></script>
  <script src="${pageContext.request.contextPath}/app/bower_components/angular-aria/angular-aria.js"></script>
  <script src="${pageContext.request.contextPath}/app/bower_components/angular-messages/angular-messages.js"></script> --%>
  
  <script src="${pageContext.request.contextPath}/app/bower_components/angular-resource/angular-resource.js"></script>
  <script src="${pageContext.request.contextPath}/app/bower_components/angular-ui-router/release/angular-ui-router.js"></script>
  
  <!-- angular material dependcies -->  
  <%-- <script src="${pageContext.request.contextPath}/app/bower_components/angular-material/angular-material.js"></script> --%>

  <!-- confirm password library -->
  <script src="${pageContext.request.contextPath}/app/bower_components/angular-password/angular-password.js"></script>

  <!-- local storage library -->
  <script src="${pageContext.request.contextPath}/app/bower_components/angular-local-storage/dist/angular-local-storage.js"></script>

  <!-- core dependencies -->
  <script src="${pageContext.request.contextPath}/app/app.module.js"></script>
  <script src="${pageContext.request.contextPath}/app/app.config.js"></script>

  <!-- login module and it's dependencies -->
  <script src="${pageContext.request.contextPath}/app/login/login.module.js"></script>
  <script src="${pageContext.request.contextPath}/app/login/login.component.js"></script>
  <script src="${pageContext.request.contextPath}/app/login/login.service.js"></script>

  <!-- register module and it's dependencies -->
  <script src="${pageContext.request.contextPath}/app/register/register.module.js"></script>
  <script src="${pageContext.request.contextPath}/app/register/register.component.js"></script>
  <script src="${pageContext.request.contextPath}/app/register/register.service.js"></script>
  
  <!-- forgotpassword module and it's dependencies -->
  <script src="${pageContext.request.contextPath}/app/forgotpassword/forgotpassword.module.js"></script>
  <script src="${pageContext.request.contextPath}/app//forgotpassword/forgotpassword.component.js"></script>
  <script src="${pageContext.request.contextPath}/app/forgotpassword/forgotpassword.service.js"></script>

  <!-- forgotpassword module and it's dependencies -->
  <script src="${pageContext.request.contextPath}/app/resetpassword/resetpassword.module.js"></script>
  <script src="${pageContext.request.contextPath}/app/resetpassword/resetpassword.component.js"></script>
  <script src="${pageContext.request.contextPath}/app/resetpassword/resetpassword.service.js"></script>
  
  <!-- homepage module and it's dependencies -->
  <script src="${pageContext.request.contextPath}/app/homepage/homepage.module.js"></script>
  <script src="${pageContext.request.contextPath}/app/homepage/homepage.component.js"></script>
  <!-- <script src="${pageContext.request.contextPath}/app/homepage/homepage.controller.js"></script> -->
  <script src="${pageContext.request.contextPath}/app/homepage/homepage.service.js"></script>
  
  <!-- Load navbar and it's dependencies -->
  <script src="${pageContext.request.contextPath}/app/homepage/navbar/navbar.module.js"></script>
  <script src="${pageContext.request.contextPath}/app/homepage/navbar/navbar.component.js"></script>

  <!-- Load navbar and it's dependencies -->
  <script src="${pageContext.request.contextPath}/app/homepage/sidenav/sidenav.module.js"></script>
  <script src="${pageContext.request.contextPath}/app/homepage/sidenav/sidenav.component.js"></script>

  <!-- Load maincard and it's dependencies -->
  <script src="${pageContext.request.contextPath}/app/homepage/maincard/maincard.module.js"></script>
  <script src="${pageContext.request.contextPath}/app/homepage/maincard/maincard.component.js"></script>
  
</head>

<body>

  <!-- router view -->
  <div class="view-container">
    <div ui-view class="view-frame"></div>
  </div>

</body>

</html>