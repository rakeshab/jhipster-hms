(function() {
    'use strict';

    angular
        .module('hospitalManagementApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('doctor', {
            parent: 'entity',
            url: '/doctor',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'hospitalManagementApp.doctor.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/doctor/doctors.html',
                    controller: 'DoctorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('doctor');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('doctor-detail', {
            parent: 'entity',
            url: '/doctor/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'hospitalManagementApp.doctor.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/doctor/doctor-detail.html',
                    controller: 'DoctorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('doctor');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Doctor', function($stateParams, Doctor) {
                    return Doctor.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'doctor',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('doctor-detail.edit', {
            parent: 'doctor-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/doctor/doctor-dialog.html',
                    controller: 'DoctorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Doctor', function(Doctor) {
                            return Doctor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('doctor.new', {
            parent: 'doctor',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/doctor/doctor-dialog.html',
                    controller: 'DoctorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                age: null,
                                qualification: null,
                                specialization: null,
                                phonenumber: null,
                                email: null,
                                gender: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('doctor', null, { reload: 'doctor' });
                }, function() {
                    $state.go('doctor');
                });
            }]
        })
        .state('doctor.edit', {
            parent: 'doctor',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/doctor/doctor-dialog.html',
                    controller: 'DoctorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Doctor', function(Doctor) {
                            return Doctor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('doctor', null, { reload: 'doctor' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('doctor.delete', {
            parent: 'doctor',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/doctor/doctor-delete-dialog.html',
                    controller: 'DoctorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Doctor', function(Doctor) {
                            return Doctor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('doctor', null, { reload: 'doctor' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
