angular.module('admindashboard').
factory('admindashboardService', ['localStorageService', '$http',
        function (localStorageService, $http) {

                console.log("Tokens are " + localStorageService.get('accessToken') + localStorageService.get('refreshToken'));

                var adminpageRequests = {}
                                
                adminpageRequests.getNotesWithOperations = function () {
                        
                        return $http({
                                method: 'GET',
                                url: 'admin/getNotesWithOperations'
                                
                        });
                }

                adminpageRequests.getNoteDetailsCount = function () {
                        return $http({
                                method: 'GET',
                                url: 'admin/getNoteDetailsCount'
                                
                        });
                }

                adminpageRequests.getNotesCountByActionAndDate = function () {
                        return $http({
                                method: 'GET',
                                url: 'admin/getNotesCountByActionAndDate'
                                
                        });
                }

                return adminpageRequests;
        }
]);