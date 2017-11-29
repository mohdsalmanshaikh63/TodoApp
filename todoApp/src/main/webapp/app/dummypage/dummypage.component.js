'use strict';

// Register 'login module along with controller and template
angular.
module('dummypage').
component('dummypage', {
    templateUrl: 'app/dummypage/dummypage.template.html',
    controller: ['$scope', 'dummypageService', 'localStorageService',
        '$state', '$stateParams',
        function DummyController($scope, dummypageService, localStorageService, $state, $stateParams) {

            console.log("DummyController called");

            var socialToken = $stateParams.token;
            
            // call dummy service to get access & refresh tokens            
            var getTokensRequest = dummypageService.getTokens(socialToken);
            getTokensRequest.then(function (response) {
                
                // save the tokens in the local storage
                var accessToken = response.data.accessToken.value;
                var refreshToken = response.data.refreshToken.value;

                setAccessToken(accessToken);
                setRefreshToken(refreshToken);

                $state.go('homepage');
                
                console.log("tokens saved");
                
            }, function (response) {
                console.log('error loading notes');
                $state.go('login');
            });

            // functions to save access and refresh Tokens
            function setAccessToken(accessToken) {
                return localStorageService.set('accessToken', accessToken);
            }

            function setRefreshToken(refreshToken) {
                return localStorageService.set('refreshToken', refreshToken);
            }

        }
    ]
});