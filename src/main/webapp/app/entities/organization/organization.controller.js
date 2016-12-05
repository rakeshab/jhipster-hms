(function() {
    'use strict';

    angular
        .module('hospitalManagementApp')
        .controller('OrganizationController', OrganizationController);

    OrganizationController.$inject = ['$scope', '$state', 'Organization'];

    function OrganizationController ($scope, $state, Organization) {
        var vm = this;

        vm.organizations = [];

        loadAll();

        function loadAll() {
            Organization.query(function(result) {
                vm.organizations = result;
            });
        }
    }
})();
