angular.module('homepage').
factory('homepageService', ['localStorageService', '$http',
        function (localStorageService, $http) {

                console.log("Tokens are " + localStorageService.get('accessToken') + localStorageService.get('refreshToken'));

                var notes = {}

                function getAccessToken() {
                        return localStorageService.get('accessToken');
                }

                function getRefreshToken() {
                        return localStorageService.get('refreshToken');
                }

                notes.createNewNote = function (note) {
                        
                        console.log("Got the note as "+note);
                        return $http({
                                        method: 'PUT',
                                        url: 'notes/create',
                                        data : note,
                                        headers: {
                                                'Content-Type': 'application/json',
                                                'accessToken': getAccessToken,
                                                'refreshToken': getRefreshToken,
                                        }
                                });
                        }
                
                return notes;
        }
]);