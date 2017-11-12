angular.module('login').
        factory('loginService', ['$resource',
                function ($resource) {
                        return $resource('user/login', {}, {
                                query: {
                                        method: 'POST',
                                        headers: {
                                                'Content-Type': 'application/json',
                                                'uId':7
                                        }
                                }
                        });
                }
        ]);