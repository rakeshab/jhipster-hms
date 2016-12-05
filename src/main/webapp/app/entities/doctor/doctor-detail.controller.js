(function() {
    'use strict';

    angular
        .module('hospitalManagementApp')
        .controller('DoctorDetailController', DoctorDetailController);

    DoctorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Doctor', 'Organization'];

    function DoctorDetailController($scope, $rootScope, $stateParams, previousState, entity, Doctor, Organization) {
        var vm = this;

        vm.doctor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hospitalManagementApp:doctorUpdate', function(event, result) {
            vm.doctor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
