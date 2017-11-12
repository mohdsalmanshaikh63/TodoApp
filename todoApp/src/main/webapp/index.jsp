<!DOCTYPE html>
<html lang="en" ng-app="todoApp">

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>My AngularJS App</title>
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- Bootstrap dependencies -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/app/bower_components/bootstrap/dist/css/bootstrap.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/app/login/login.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/app/register/register.css">
  <script src="${pageContext.request.contextPath}/app/bower_components/jquery/dist/jquery.js"></script>
  <script src="${pageContext.request.contextPath}/app/bower_components/tether/dist/js/tether.js"></script>
  <script src="${pageContext.request.contextPath}/app/bower_components/bootstrap/dist/js/bootstrap.js"></script>

  <!-- angular dependencies -->
  <script src="${pageContext.request.contextPath}/app/bower_components/angular/angular.js"></script>
  <script src="${pageContext.request.contextPath}/app/bower_components/angular-animate/angular-animate.js"></script>
  <script src="${pageContext.request.contextPath}/app/bower_components/angular-resource/angular-resource.js"></script>
  <script src="${pageContext.request.contextPath}/app/bower_components/angular-ui-router/release/angular-ui-router.js"></script>

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

</head>

<body>

  <!-- router view -->
  <div class="view-container">
    <div ui-view class="view-frame"></div>
  </div>

</body>

</html>