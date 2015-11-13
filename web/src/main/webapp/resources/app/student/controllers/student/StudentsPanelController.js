angular
    .module('studentModule')
    .controller(
    'StudentsPanelController',
    [
        '$rootScope',
        '$scope',
        '$modal',
        '$http',
        'StudentsService',
        'ngTableParams',
        '$translate',
        '$timeout',
        'toaster',
        '$filter',
        function ($rootScope, $scope, $modal, $http, studentsService, ngTableParams, $translate, $timeout, toaster, $filter) {
            $scope.totalItems = 0;
            $scope.currentPage = 1;
            $scope.itemsPerPage = 5;
            $scope.pageContent = [];

            $scope.setTypeDataLanguage = function () {
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

                    studentsService.getPage(params.page(), params.count(), params.filter(), sortCriteria, sortOrder)
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
            $scope.openAddStudentModal = function() {
                var addStudent = $modal.open({
                    animation : true,
                    controller : 'StudentAddModalController',
                    templateUrl : '/resources/app/student/views/modals/students/student-add-modal.html',
                    size: 'md'
                });
                addStudent.result.then(function () {
                    toaster.pop('success',$filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_ADDED_STUDENT'));
                });
            };

            /**
             * Opens modal window for editing category of counter.
             */
            $scope.openEditStudentModal = function(
                studentId) {
                $rootScope.studentId = studentId;
                studentsService.getStudentById(
                    $rootScope.studentId).then(
                    function(data) {
                        $rootScope.student = data;
                        console.log($rootScope.student);

                        var studentDTOModal = $modal
                            .open({
                                animation : true,
                                controller : 'StudentEditModalController',
                                templateUrl : '/resources/app/student/views/modals/subject/student-subject-lecturers.html',
                                size: 'md'
                            });
                        studentDTOModal.result.then(function () {
                            toaster.pop('info', $filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_EDITED_STUDENT'));
                        });
                    });

            };

            $scope.deleteStudent = function (id) {
                $rootScope.id = id;
                console.log($rootScope.id);
                studentsService.deleteStudent(id).then(function () {
                    toaster.pop('error', $filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_DELETED_STUDENT'));
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
                studentId) {
                $rootScope.studentId = studentId;
                studentsService.getStudentById(
                    $rootScope.studentId).then(
                    function(data) {
                        $rootScope.student = data;
                        console.log($rootScope.student);

                        var studentDTOModal = $modal
                            .open({
                                animation : true,
                                controller : 'StudentEditModalController',
                                templateUrl : '/resources/app/student/views/modals/subject/student-subject-lecturers.html',
                                size: 'md'
                            });
                    });

            };

            /**
             * Opens modal window for editing category of counter.
             */
            $scope.openListOfSubjects = function(
                studentId) {
                $rootScope.studentId = studentId;
                studentsService.getStudentById(
                    $rootScope.studentId).then(
                    function(data) {
                        $rootScope.student = data;
                        console.log($rootScope.student);

                        var studentDTOModal = $modal
                            .open({
                                animation : true,
                                controller : 'StudentEditModalController',
                                templateUrl : '/resources/app/student/views/modals/subject/student-subject-lecturers.html',
                                size: 'md'
                            });
                    });

            };

        }]);