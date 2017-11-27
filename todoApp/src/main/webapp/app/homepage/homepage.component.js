'use strict';

// Register 'gomepage module along with controller and template
angular.
module('homepage').
component('homepage', {
    templateUrl: 'app/homepage/homepage.template.html',
    controller: ['$scope', 'homepageService', '$state',
        function HomepageController($scope, homepageService, $state) {                                        

        }
    ]
});