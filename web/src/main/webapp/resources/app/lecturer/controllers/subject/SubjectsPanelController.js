angular
    .module('lecturerModule')
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
             * Opens modal window for editing category of counter.
             */
            $scope.openListOfGroups = function(
                subjectId) {
                subjectsService.getGroupsBySubjectId(
                    subjectId).then(
                    function(data) {
                        console.log(data);
                        $rootScope.groups = data;
                        console.log($rootScope.groups);
                        var subjectDTOModal = $modal
                            .open({
                                animation : true,
                                controller : 'SubjectEditModalController',
                                templateUrl : '/resources/app/lecturer/views/modals/subject/subject-edit-modal.html',
                                size: 'lg'
                            });
                        subjectDTOModal.result.then(function () {
                            toaster.pop('info', $filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_EDITED_SUBJECT'));
                        });
                    });

            };

            /**
             * Opens modal window for editing category of counter.
             */
            $scope.openListOfTests = function(
                subjectId) {
                console.log(subjectId);
                subjectsService.getTestsBySubjectId(
                    subjectId).then(
                    function(data) {
                        console.log(data);
                        $rootScope.tests = data;
                        var subjectDTOModal = $modal
                            .open({
                                animation : true,
                                controller : 'SubjectAddModalController',
                                templateUrl : '/resources/app/lecturer/views/modals/subject/subject-add-modal.html',
                                size: 'lg'
                            });
                        subjectDTOModal.result.then(function () {
                            toaster.pop('info', $filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_EDITED_SUBJECT'));
                        });
                    });

            };

        }]);