(function () {
    'use strict';

    angular
        .module('hospitalManagementApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
