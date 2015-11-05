angular
    .module('adminModule')
    .controller(
    'SubjectPanelController',
    [
        '$rootScope',
        '$scope',
        '$modal',
        '$http',
        'SubjectsService',
        'ngTableParams',
        '$translate',
        '$timeout',
        'toaster',
        '$filter',
        function ($rootScope, $scope, $modal, $http, subjectsService, ngTableParams, $translate, $timeout, toaster, $filter) {
            $scope.totalItems = 0;
            $scope.currentPage = 1;
            $scope.itemsPerPage = 5;
            $scope.pageContent = [];

            $scope.clearAll = function () {
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

                    subjectsService.getPage(params.page(), params.count(), params.filter(), sortCriteria, sortOrder)
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
            $scope.openAddSubjectModal = function() {
                var addSubject = $modal.open({
                    animation : true,
                    controller : 'SubjectAddModalController',
                    templateUrl : '/resources/app/admin/views/modals/subject/subject-add-modal.html',
                    size: 'md'
                });
                addSubject.result.then(function () {
                    toaster.pop('success',$filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_ADDED_SUBJECT'));
                });
            };

            /**
             * Opens modal window for editing category of counter.
             */
            $scope.openEditSubjectModal = function(
                subjectId) {
                $rootScope.subjectId = subjectId;
                subjectsService.getSubjectById(
                    $rootScope.subjectId).then(
                    function(data) {
                        $rootScope.subject = data;
                        console.log($rootScope.subject);

                        var subjectDTOModal = $modal
                            .open({
                                animation : true,
                                controller : 'SubjectEditModalController',
                                templateUrl : '/resources/app/admin/views/modals/subject/subject-edit-modal.html',
                                size: 'md'
                            });
                        subjectDTOModal.result.then(function () {
                            toaster.pop('info', $filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_EDITED_SUBJECT'));
                        });
                    });

            };

            $scope.deleteSubject = function (id) {
                $rootScope.subjectId = id;
                console.log($rootScope.subjectId);
                subjectsService.deleteSubject(id).then(function () {
                    toaster.pop('error', $filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_DELETED_SUBJECT'));
                });
                $timeout(function() {
                    console.log('delete with timeout');
                    $rootScope.onTableHandling();
                }, 700);
            };


            $scope.openListOfGroups = function(
                subjectId) {
                $rootScope.subjectId = subjectId;
                subjectsService.getSubjectById(
                    $rootScope.subjectId).then(
                    function(data) {
                        $rootScope.subject = data;
                        console.log($rootScope.subject);

                        var subjectDTOModal = $modal
                            .open({
                                animation : true,
                                controller : 'SubjectEditModalController',
                                templateUrl : '/resources/app/admin/views/modals/subject/subject-edit-modal.html',
                                size: 'md'
                            });
                    });

            };

            $scope.openListOfLecturers = function(
                subjectId) {
                $rootScope.subjectId = subjectId;
                subjectsService.getSubjectById(
                    $rootScope.subjectId).then(
                    function(data) {
                        $rootScope.subject = data;
                        console.log($rootScope.subject);

                        var subjectDTOModal = $modal
                            .open({
                                animation : true,
                                controller : 'SubjectEditModalController',
                                templateUrl : '/resources/app/admin/views/modals/subject/subject-edit-modal.html',
                                size: 'md'
                            });
                    });

            };

        }]);