(function() {
    'use strict';

    angular
        .module('hospitalManagementApp')
        .controller('DoctorController', DoctorController);

    DoctorController.$inject = ['$scope', '$state', 'Doctor'];

    function DoctorController ($scope, $state, Doctor) {
        var vm = this;

        vm.doctors = [];

        loadAll();

        function loadAll() {
            Doctor.query(function(result) {
                vm.doctors = result;
            });
        }
    }
})();
