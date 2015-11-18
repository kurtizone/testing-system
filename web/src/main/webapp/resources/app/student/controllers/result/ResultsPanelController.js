angular
    .module('studentModule')
    .controller(
    'ResultsPanelController',
    [
        '$rootScope',
        '$scope',
        '$modal',
        '$http',
        'ResultsService',
        'ngTableParams',
        '$translate',
        '$timeout',
        '$filter',
        'toaster',
        function ($rootScope, $scope, $modal, $http, resultsService, ngTableParams, $translate, $timeout, $filter, toaster) {
            $scope.totalItems = 0;
            $scope.currentPage = 1;
            $scope.itemsPerPage = 5;
            $scope.pageContent = [];
            $scope.$broadcast('timer-stop');
            //for measurement academicStatus
            $scope.selectedTestType= {
                name: null
            }

            $scope.testTypeData = [
                {
                    id: 'MODUL',
                    label: $filter('translate')('MODUL')
                },
                {
                    id: 'LABARATORY',
                    label: $filter('translate')('LABARATORY')
                },
                {
                    id: 'FINAL',
                    label: $filter('translate')('FINAL')
                }
            ];


            /**
             * Localization of multiselect for type of organization
             */
            $scope.setTypeDataLanguage = function () {
            };
            $scope.setTypeDataLanguage();

            $scope.clearAll = function () {
                $scope.selectedTestType.name = null;
                $scope.tableParams.filter({});
            };

            $scope.doSearch = function () {
                $scope.tableParams.reload();
            };

            $scope.tableParams = new ngTableParams({
                page: 1,
                count: 10,
                sorting: {
                    id: 'desc'
                }
            }, {
                total: 0,
                filterDelay: 10000,
                getData: function ($defer, params) {

                    var sortCriteria = Object.keys(params.sorting())[0];
                    var sortOrder = params.sorting()[sortCriteria];

                    if ($scope.selectedTestType.name != null) {
                        params.filter().testType = $scope.selectedTestType.name.id;
                    }
                    else {
                        params.filter().testType = null; //case when the filter is cleared with a button on the select
                    }

                    resultsService.getPage(params.page(), params.count(), params.filter(), sortCriteria, sortOrder)
                        .success(function (result) {
                            $scope.resultsCount = result.totalItems;
                            $defer.resolve(result.content);
                            params.total(result.totalItems);
                        }, function (result) {
                            $log.debug('error fetching data:', result);
                        });
                }
            });
            /**
             * Updates the table with device.
             */
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
            /**
             * Opens modal window for adding new category of counters.
             */
            $scope.openAddGroupModal = function() {
                var addGroup = $modal.open({
                    animation : true,
                    controller : 'GroupAddModalController',
                    templateUrl : '/resources/app/student/views/modals/groups/group-add-modal.html',
                    size: 'md'
                });
                addGroup.result.then(function () {
                    toaster.pop('success',$filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_ADDED_GROUP'));
                });
            };

            /**
             * Opens modal window for editing category of counter.
             */
            $scope.openEditGroupModal = function(
                groupId) {
                $rootScope.groupId = groupId;
                groupsService.getGroupById(
                    $rootScope.groupId).then(
                    function(data) {
                        $rootScope.group = data;
                        console.log($rootScope.group);

                        var groupDTOModal = $modal
                            .open({
                                animation : true,
                                controller : 'GroupEditModalController',
                                templateUrl : '/resources/app/student/views/modals/groups/group-edit-modal.html',
                                size: 'md'
                            });
                        groupDTOModal.result.then(function () {
                            toaster.pop('info', $filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_EDITED_GROUP'));
                        });
                    });

            };

            $scope.deleteGroup = function (id) {
                $rootScope.id = id;
                console.log($rootScope.id);
                groupsService.deleteGroup(id).then(function () {
                    toaster.pop('error', $filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_DELETED_GROUP'));
                });
                $timeout(function() {
                    console.log('delete with timeout');
                    $rootScope.onTableHandling();
                }, 700);
            };

            /**
             * Opens modal window for editing category of counter.
             */
            $scope.openAddSubject = function(
                groupId) {
                $rootScope.groupId = groupId;
                groupsService.getGroupById(
                    $rootScope.groupId).then(
                    function(data) {
                        $rootScope.group = data;
                        console.log($rootScope.group);

                        var groupDTOModal = $modal
                            .open({
                                animation : true,
                                controller : 'GroupEditModalController',
                                templateUrl : '/resources/app/student/views/modals/groups/group-edit-modal.html',
                                size: 'md'
                            });
                    });

            };

            /**
             * Opens modal window for editing category of counter.
             */
            $scope.openListOfSubjects = function(
                groupId) {
                $rootScope.groupId = groupId;
                groupsService.getGroupById(
                    $rootScope.groupId).then(
                    function(data) {
                        $rootScope.group = data;
                        console.log($rootScope.group);

                        var groupDTOModal = $modal
                            .open({
                                animation : true,
                                controller : 'GroupEditModalController',
                                templateUrl : '/resources/app/student/views/modals/groups/group-edit-modal.html',
                                size: 'md'
                            });
                    });

            };

        }]);