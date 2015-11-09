angular
    .module('lecturerModule')
    .controller(
    'TestsPanelController',
    [
        '$rootScope',
        '$scope',
        '$modal',
        '$http',
        'TestsService',
        'ngTableParams',
        '$translate',
        '$timeout',
        'toaster',
        '$filter',
        function ($rootScope, $scope, $modal, $http, testsService, ngTableParams, $translate, $timeout, toaster, $filter) {
            $scope.totalItems = 0;
            $scope.currentPage = 1;
            $scope.itemsPerPage = 5;
            $scope.pageContent = [];

            //for measurement testType
            $scope.selectedType = {
                name: null
            }
            $scope.selectedAvaible = {
                name: null
            }

            $scope.typeData = [
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

            console.log($scope.typeData);

            $scope.avaibleData = [
                {
                    id: 'true',
                    label: $filter('translate')('TRUE_AVAIBLE')
                },
                {
                    id: 'false',
                    label: $filter('translate')('FALSE_AVAIBLE')
                },

            ];




            $scope.setTypeDataLanguage = function () {

            };
            $scope.setTypeDataLanguage();

            $scope.clearAll = function () {
                $scope.selectedType.name = null;
                $scope.selectedAvaible.name = null;
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

                    if ($scope.selectedType.name != null) {
                        console.log($scope.selectedType.name);
                        params.filter().type = $scope.selectedType.name.id;
                    }
                    else {
                        params.filter().type = null; //case when the filter is cleared with a button on the select
                    }

                    if ($scope.selectedAvaible.name != null) {
                        params.filter().avaible = $scope.selectedAvaible.name.id;
                    }
                    else {
                        params.filter().avaible = null; //case when the filter is cleared with a button on the select
                    }


                    testsService.getPage(params.page(), params.count(), params.filter(), sortCriteria, sortOrder)
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
            $scope.openAddTestModal = function() {
                var addTest = $modal.open({
                    animation : true,
                    controller : 'TestAddModalController',
                    templateUrl : '/resources/app/lecturer/views/modals/tests/test-add-modal.html',
                    size: 'md',
                    resolve: {
                        subjects: function () {
                            console.log(testsService.findSubjects());
                            return testsService.findSubjects();
                        }
                    }
                });
                addTest.result.then(function () {
                    toaster.pop('success',$filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_ADDED_LECTURER'));
                });
            };

            /**
             * Opens modal window for editing category of counter.
             */
            $scope.openEditTestModal = function(
                testId) {
                $rootScope.testId = testId;
                testsService.getTestById(
                    $rootScope.testId).then(
                    function(data) {
                        $rootScope.test = data;
                        console.log($rootScope.test);

                        var testDTOModal = $modal
                            .open({
                                animation : true,
                                controller : 'TestEditModalController',
                                templateUrl : '/resources/app/lecturer/views/modals/tests/test-edit-modal.html',
                                size: 'md',
                                resolve: {
                                    subjects: function () {
                                        console.log(testsService.findSubjects());
                                        return testsService.findSubjects();
                                    }
                                }
                            });
                        testDTOModal.result.then(function () {
                            toaster.pop('info', $filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_EDITED_LECTURER'));
                        });
                    });

            };

            $scope.deleteTest = function (id) {
                $rootScope.id = id;
                console.log($rootScope.id);
                testsService.deleteTest(id).then(function () {
                    toaster.pop('error', $filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_DELETED_LECTURER'));
                });
                $timeout(function() {
                    console.log('delete with timeout');
                    $rootScope.onTableHandling();
                }, 700);
            };



        }]);