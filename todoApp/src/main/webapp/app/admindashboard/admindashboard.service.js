angular.module('admindashboard').
factory('admindashboardService', ['localStorageService', '$http',
        function (localStorageService, $http) {

                console.log("Tokens are " + localStorageService.get('accessToken') + localStorageService.get('refreshToken'));

                var adminpageRequests = {}

                function getAccessToken() {
                        return localStorageService.get('accessToken');
                }

                function getRefreshToken() {
                        return localStorageService.get('refreshToken');
                }

                /* adminpageRequests.createNewNote = function (note) {
                        
                        return $http({
                                method: 'POST',
                                url: 'notes/create',
                                data: note,
                                headers: {
                                        'Content-Type': 'application/json',
                                        'accessToken': getAccessToken,
                                        'refreshToken': getRefreshToken,
                                }
                        });
                } */

                adminpageRequests.getAllNoteLogs = function () {
                        return $http({
                                method: 'GET',
                                url: 'admin/getAllNoteLogs'
                                
                        });
                }

                /* adminpageRequests.updateNote = function (note) {                        
                        return $http({
                                method: 'PUT',
                                url: 'notes/update',
                                data: note,
                                headers: {
                                        'Content-Type': 'application/json',
                                        'accessToken': getAccessToken,
                                        'refreshToken': getRefreshToken
                                }
                        });
                }

                adminpageRequests.deleteNote = function (noteId) {                        
                        return $http({
                                method: 'DELETE',
                                url: 'notes/delete/'+noteId,                                
                                headers: {
                                        'Content-Type': 'application/json',
                                        'accessToken': getAccessToken,
                                        'refreshToken': getRefreshToken
                                }
                        });
                }

                adminpageRequests.xyz = function () {

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

                adminpageRequests.logoutUser = function () {                        

                        return $http({
                                method: 'POST',
                                url: 'user/logout',
                                headers: {
                                        'Content-Type': 'application/json',
                                        'accessToken': getAccessToken,
                                        'refreshToken': getRefreshToken
                                }
                        });

                } */ 


                return adminpageRequests;
        }
]);