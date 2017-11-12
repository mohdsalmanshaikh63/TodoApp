angular.module('register').
    factory('registerService', ['$resource',
        function ($resource) {
            return $resource('user/create', {}, {
                register: {
                    method: 'POST'
                }
            })
        }
    ])