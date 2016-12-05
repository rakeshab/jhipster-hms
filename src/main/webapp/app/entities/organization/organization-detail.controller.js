(function() {
    'use strict';

    angular
        .module('hospitalManagementApp')
        .controller('OrganizationDetailController', OrganizationDetailController);

    OrganizationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Organization'];

    function OrganizationDetailController($scope, $rootScope, $stateParams, previousState, entity, Organization) {
        var vm = this;

        vm.organization = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hospitalManagementApp:organizationUpdate', function(event, result) {
            vm.organization = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
