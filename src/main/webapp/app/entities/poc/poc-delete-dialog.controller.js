(function() {
    'use strict';

    angular
        .module('hospitalManagementApp')
        .controller('PocDeleteController',PocDeleteController);

    PocDeleteController.$inject = ['$uibModalInstance', 'entity', 'Poc'];

    function PocDeleteController($uibModalInstance, entity, Poc) {
        var vm = this;

        vm.poc = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Poc.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
