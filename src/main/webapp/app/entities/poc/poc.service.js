(function() {
    'use strict';
    angular
        .module('hospitalManagementApp')
        .factory('Poc', Poc);

    Poc.$inject = ['$resource'];

    function Poc ($resource) {
        var resourceUrl =  'api/pocs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
