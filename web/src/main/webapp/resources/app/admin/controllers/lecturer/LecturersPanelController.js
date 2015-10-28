angular
    .module('adminModule')
    .controller(
    'LecturersPanelController',
    [
        '$rootScope',
        '$scope',
        '$modal',
        '$http',
        'LecturersService',
        'ngTableParams',
        '$translate',
        '$timeout',
        function ($rootScope, $scope, $modal, $http, lecturersService, ngTableParams, $translate, $timeout) {
            $scope.totalItems = 0;
            $scope.currentPage = 1;
            $scope.itemsPerPage = 5;
            $scope.pageContent = [];

            //for measurement academicStatus
            $scope.selectedAcademicStatus = {
                name: null
            }

            //for measurement degree
            $scope.selectedDegree = {
                name: null
            }

            $scope.academicStatusData = [
                {
                    id: 'PROFESSOR',
                    label: null
                },
                {
                    id: 'DOCENT',
                    label: null
                },
                {
                    id: 'SENIOR_LECTURER',
                    label: null
                }
            ];

            $scope.degreeData = [
                {
                    id: 'CANDIDATE',
                    label: null
                },
                {
                    id: 'DOCTOR',
                    label: null
                },
                {
                    id: 'POSTGRADUATE',
                    label: null
                }
            ];

            /**
             * Localization of multiselect for type of organization
             */
            $scope.setTypeDataLanguage = function () {
                var lang = $translate.use();
                if (lang === 'ukr') {
                    $scope.academicStatusData[0].label = 'Професор';
                    $scope.academicStatusData[1].label = 'Доцент';
                    $scope.academicStatusData[2].label = 'Старший викладач';
                    $scope.degreeData[0].label = 'Кандидат наук';
                    $scope.degreeData[1].label = 'Доктор наук';
                    $scope.degreeData[2].label = 'Аспірант';
                } else if (lang === 'eng') {
                    $scope.academicStatusData[0].label = 'Professor';
                    $scope.academicStatusData[1].label = 'Docent';
                    $scope.academicStatusData[2].label = 'Senior Lecturer';
                    $scope.degreeData[0].label = 'PhD';
                    $scope.degreeData[1].label = 'Doctorate';
                    $scope.degreeData[2].label = 'Postgraduate';
                }
            };

            $scope.setTypeDataLanguage();

            $scope.clearAll = function () {
                $scope.selectedAcademicStatus.name = null;
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

                    if ($scope.selectedAcademicStatus.name != null) {
                        params.filter().academicStatus = $scope.selectedAcademicStatus.name.id;
                    }
                    else {
                        params.filter().academicStatus = null; //case when the filter is cleared with a button on the select
                    }

                    if ($scope.selectedDegree.name != null) {
                        params.filter().degree = $scope.selectedDegree.name.id;
                    }
                    else {
                        params.filter().degree = null; //case when the filter is cleared with a button on the select
                    }

                    lecturersService.getPage(params.page(), params.count(), params.filter(), sortCriteria, sortOrder)
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
            $scope.openAddLecturerModal = function() {
                var addLecturer = $modal.open({
                    animation : true,
                    controller : 'LecturerAddModalController',
                    templateUrl : '/resources/app/admin/views/modals/lecturers/lecturer-add-modal.html',
                    size: 'md'
                });
            };

            /**
             * Opens modal window for editing category of counter.
             */
            $scope.openEditLecturerModal = function(
                lecturerId) {
                $rootScope.lecturerId = lecturerId;
                lecturersService.getLecturerById(
                    $rootScope.lecturerId).then(
                    function(data) {
                        $rootScope.lecturer = data;
                        console.log($rootScope.lecturer);

                        var lecturerDTOModal = $modal
                            .open({
                                animation : true,
                                controller : 'LecturerEditModalController',
                                templateUrl : '/resources/app/admin/views/modals/lecturers/lecturer-edit-modal.html',
                                size: 'md'
                            });
                    });

            };

            $scope.deleteLecturer = function (id) {
                $rootScope.deviceCategoryId = id;
                console.log($rootScope.deviceCategoryId);
                devicesService.deleteDeviceCategory(id);
                $timeout(function() {
                    console.log('delete with timeout');
                    $rootScope.onTableHandling();
                }, 700);
            };

            /**
             * Opens modal window for editing category of counter.
             */
            $scope.openAddSubject = function(
                lecturerId) {
                $rootScope.lecturerId = lecturerId;
                lecturersService.getLecturerById(
                    $rootScope.lecturerId).then(
                    function(data) {
                        $rootScope.lecturer = data;
                        console.log($rootScope.lecturer);

                        var lecturerDTOModal = $modal
                            .open({
                                animation : true,
                                controller : 'LecturerEditModalController',
                                templateUrl : '/resources/app/admin/views/modals/lecturers/lecturer-edit-modal.html',
                                size: 'md'
                            });
                    });

            };

            /**
             * Opens modal window for editing category of counter.
             */
            $scope.openListOfSubjects = function(
                lecturerId) {
                $rootScope.lecturerId = lecturerId;
                lecturersService.getLecturerById(
                    $rootScope.lecturerId).then(
                    function(data) {
                        $rootScope.lecturer = data;
                        console.log($rootScope.lecturer);

                        var lecturerDTOModal = $modal
                            .open({
                                animation : true,
                                controller : 'LecturerEditModalController',
                                templateUrl : '/resources/app/admin/views/modals/lecturers/lecturer-edit-modal.html',
                                size: 'md'
                            });
                    });

            };

        }]);