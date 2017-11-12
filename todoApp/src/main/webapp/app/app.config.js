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
                });
            $urlRouterProvider.otherwise('/login');
        }]);