angular.module('homepage').
factory('homepageService', ['localStorageService', '$http',
        function (localStorageService, $http) {

                console.log("Tokens are " + localStorageService.get('accessToken') + localStorageService.get('refreshToken'));

                var homepageRequests = {}

                function getAccessToken() {
                        return localStorageService.get('accessToken');
                }

                function getRefreshToken() {
                        return localStorageService.get('refreshToken');
                }

                homepageRequests.createNewNote = function (note) {

                        console.log("Got the note as " + note);
                        return $http({
                                method: 'PUT',
                                url: 'notes/create',
                                data: note,
                                headers: {
                                        'Content-Type': 'application/json',
                                        'accessToken': getAccessToken,
                                        'refreshToken': getRefreshToken,
                                }
                        });
                }

                homepageRequests.getAllNotes= function () {
                        return $http({
                                method: 'GET',
                                url: 'notes/getAllNotes',
                                headers: {
                                        'Content-Type': 'application/json',
                                        'accessToken': getAccessToken,
                                        'refreshToken': getRefreshToken,
                                }
                        });
                }

                homepageRequests.updateNote = function (note) {
                        console.log("Got the note as " + JSON.stringify(note));
                        return $http({
                                method: 'POST',
                                url: 'notes/update',
                                data: note,
                                headers: {
                                        'Content-Type': 'application/json',
                                        'accessToken': getAccessToken,
                                        'refreshToken': getRefreshToken
                                }
                        });
                }

                homepageRequests.xyz = function () {

                        console.log("Getting user for navbar");

                        return $http({
                                method: 'GET',
                                url: 'user/getUserById',
                                headers: {
                                        'Content-Type': 'application/json',
                                        'accessToken': getAccessToken,
                                        'refreshToken': getRefreshToken
                                }
                        });
                }

                homepageRequests.logoutUser = function () {                        

                        return $http({
                                method: 'POST',
                                url: 'user/logout',
                                headers: {
                                        'Content-Type': 'application/json',
                                        'accessToken': getAccessToken,
                                        'refreshToken': getRefreshToken
                                }
                        });

                }


                return homepageRequests;
        }
]);