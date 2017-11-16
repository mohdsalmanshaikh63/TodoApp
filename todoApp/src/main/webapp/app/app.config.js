'use strict';

angular.
    module('todoApp').
    config(['$stateProvider', '$urlRouterProvider', 'localStorageServiceProvider',
        function config($stateProvider, $urlRouterProvider, localStorageServiceProvider) {

            localStorageServiceProvider.setPrefix('todoApp')
            .setStorageType('localStorage');

            $stateProvider.
                state('login', {
                    url: '/login',
                    template: '<login></login>'
                }).
                state('register', {
                    url:'/register',
                    template:'<register></register>'
                }).
                state('forgotpassword', {
                    url: '/forgotpassword',
                    template: '<forgotpassword></forgotpassword>'
                })
                .state('resetpassword', {
                    url:'/resetpassword/:token',
                    template:'<resetpassword></resetpassword>'});;
            $urlRouterProvider.otherwise('/login');
        }]);