angular.module('admindashboard').
factory('admindashboardService', ['localStorageService', '$http',
        function (localStorageService, $http) {

                console.log("Tokens are " + localStorageService.get('accessToken') + localStorageService.get('refreshToken'));

                var adminpageRequests = {}

                
                adminpageRequests.getAllNoteLogs = function () {
                        return $http({
                                method: 'GET',
                                url: 'admin/getAllNoteLogs'
                                
                        });
                }

                adminpageRequests.getNotesWithOperations = function () {
                        return $http({
                                method: 'GET',
                                url: 'admin/getNotesWithOperations'
                                
                        });
                }

                return adminpageRequests;
        }
]);