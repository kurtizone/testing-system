angular
    .module('adminModule')
    .controller('SysAdminsController', ['$scope', 'UsersService', 'AddressService', '$modal', '$log', 'ngTableParams', '$timeout', '$filter','$rootScope',
        'toaster',
        function ($scope, userService, addressService, $modal, $log, ngTableParams, $timeout, $filter, $rootScope, toaster) {


            $scope.cantAddEmployee;

            $scope.clearAll = function () {
                $scope.tableParams.filter({});
            };


            $scope.tableParams = new ngTableParams({
                page: 1,
                count: 5,
                sorting: {
                    lastName: 'asc'     // initial sorting
                }
            }, {
                total: 0,
                getData: function ($defer, params) {

                    var sortCriteria = Object.keys(params.sorting())[0];
                    var sortOrder = params.sorting()[sortCriteria];

                    userService.getSysAdminsPage(params.page(), params.count(), params.filter(), sortCriteria, sortOrder)
                        .success(function (result) {
                            console.log(result);
                            $scope.totalEmployee=result.totalItems;
                            $defer.resolve(result.content);
                            params.total(result.totalItems);
                        }, function (result) {
                            $log.debug('error fetching data:', result);
                        });
                }
            });

            $rootScope.onTableHandling = function () {
                $scope.tableParams.reload();
            };

            $rootScope.onTableHandling();


            $scope.isFilter = function () {
                var obj = $scope.tableParams.filter();
                for (var i in obj) {
                    if (obj.hasOwnProperty(i) && obj[i]) {
                        return true;
                    }
                }
                return false;
            };

            $scope.openAddSysAdminModal = function() {
                var addEmployeeModal = $modal
                    .open({
                        animation : true,
                        controller : 'SysAdminAddModalController',
                        templateUrl : '/resources/app/admin/views/modals/sys-admin-add-modal.html',
                    });

                /**
                 * executes when modal closing
                 */
                addEmployeeModal.result.then(function () {
                    $scope.popNotification($filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_CREATED_ADMIN'));
                });
            };

            $scope.openEditSysAdminModal = function (username) {
                $rootScope.username = username;
                userService.getSysAdminByUsername(
                    $rootScope.username).then(
                    function (result) {$rootScope.sysAdmin = result.data;
                        console.log($rootScope.sysAdmin);

                        var sysAdminDTOModal = $modal
                            .open({
                                animation: true,
                                controller: 'SysAdminEditModalController',
                                templateUrl: '/resources/app/admin/views/modals/sys-admin-edit-modal.html',
                                size: 'lg',
                                resolve: {
                                    regions: function () {
                                        return addressService.findAllRegions();
                                    }
                                }
                            });
                    });
            };

            $scope.deleteSysAdmin = function (username){
                userService.deleteSysAdmin(username);
                $timeout(function() {
                    console.log('delete with timeout');
                    $rootScope.onTableHandling();
                }, 700);
            }



            $scope.cantAddNewEmployee = function() {
                userService.getOrganizationEmployeeCapacity().success(
                    function(data) {
                        $scope.organizationEmployeesCapacity = data;
                        if ($scope.totalEmployee < $scope.organizationEmployeesCapacity) {
                            $scope.cantAddEmployee = false;
                        } else {
                            $scope.cantAddEmployee = true;
                        }
                    });
            };

            $scope.popNotification = function (title, text) {
                toaster.pop('success', title, text);
            };

        }]);
