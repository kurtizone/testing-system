angular
    .module('adminModule')
    .controller(
    'GroupsPanelController',
    [
        '$rootScope',
        '$scope',
        '$modal',
        '$http',
        'GroupsService',
        'ngTableParams',
        '$translate',
        '$timeout',
        '$filter',
        'toaster',
        function ($rootScope, $scope, $modal, $http, groupsService, ngTableParams, $translate, $timeout, $filter, toaster) {
            $scope.totalItems = 0;
            $scope.currentPage = 1;
            $scope.itemsPerPage = 5;
            $scope.pageContent = [];

            //for measurement academicStatus
            $scope.selectedStudyForm = {
                name: null
            }

            //for measurement degree
            $scope.selectedDegree = {
                name: null
            }

            $scope.degreeData = [
                {
                    id: 'MASTER',
                    label: $filter('translate')('MASTER')
                },
                {
                    id: 'BACHELOR',
                    label: $filter('translate')('BACHELOR')
                }
            ];

            $scope.studyFormData = [
                {
                    id: 'EXTERNAL',
                    label: $filter('translate')('EXTERNAL')
                },
                {
                    id: 'DAILY',
                    label: $filter('translate')('DAILY')
                },
                {
                    id: 'NONRESIDENCE',
                    label: $filter('translate')('NONRESIDENCE')
                }
            ];

            /**
             * Localization of multiselect for type of organization
             */
            $scope.setTypeDataLanguage = function () {
            };
            $scope.setTypeDataLanguage();

            $scope.clearAll = function () {
                $scope.selectedStudyForm.name = null;
                $scope.selectedDegree.name = null;
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

                    if ($scope.selectedStudyForm.name != null) {
                        params.filter().studyForm = $scope.selectedStudyForm.name.id;
                    }
                    else {
                        params.filter().studyForm = null; //case when the filter is cleared with a button on the select
                    }

                    if ($scope.selectedDegree.name != null) {
                        params.filter().degree = $scope.selectedDegree.name.id;
                    }
                    else {
                        params.filter().degree = null; //case when the filter is cleared with a button on the select
                    }

                    groupsService.getPage(params.page(), params.count(), params.filter(), sortCriteria, sortOrder)
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
            $scope.openAddGroupModal = function () {
                var addGroup = $modal.open({
                    animation: true,
                    controller: 'GroupAddModalController',
                    templateUrl: '/resources/app/admin/views/modals/groups/group-add-modal.html',
                    size: 'md'
                });
                addGroup.result.then(function () {
                    toaster.pop('success', $filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_ADDED_GROUP'));
                });
            };

            /**
             * Opens modal window for editing category of counter.
             */
            $scope.openEditGroupModal = function (groupId) {
                $rootScope.groupId = groupId;
                groupsService.getGroupById(
                    $rootScope.groupId).then(
                    function (data) {
                        $rootScope.group = data;
                        console.log($rootScope.group);

                        var groupDTOModal = $modal
                            .open({
                                animation: true,
                                controller: 'GroupEditModalController',
                                templateUrl: '/resources/app/admin/views/modals/groups/group-edit-modal.html',
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
                $timeout(function () {
                    console.log('delete with timeout');
                    $rootScope.onTableHandling();
                }, 700);
            };

            /**
             * Opens modal window for editing category of counter.
             */
            $scope.openAddSubject = function(groupId) {
                $rootScope.groupId = groupId;
                var addGroup = $modal.open({
                    animation: true,
                    controller: 'GroupAddSubjectController',
                    templateUrl: '/resources/app/admin/views/modals/groups/group-add-subject.html',
                    size: 'md'
                });
                addGroup.result.then(function () {
                    toaster.pop('success', $filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_ADDED_GROUP'));
                });


            };

            /**
             * Opens modal window for editing category of counter.
             */
            $scope.openListOfSubjects = function (groupId) {
                groupsService.getSubjectsByGroupId(groupId).then(
                    function (data) {
                        $rootScope.groupId = groupId;
                        $rootScope.groupSubjects = data;
                        console.log($rootScope.groupSubjects);
                        var subjectsDTOModal = $modal
                            .open({
                                animation: true,
                                controller: 'GroupSubjectsController',
                                templateUrl: '/resources/app/admin/views/modals/groups/group-subjects.html',
                                size: 'lg'
                            });
                    });


            };

        }]);