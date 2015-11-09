angular
    .module('lecturerModule')
    .controller(
    'FillTestPanelController',
    [
        '$rootScope',
        '$scope',
        '$modal',
        '$http',
        'FillTestsService',
        'ngTableParams',
        '$translate',
        '$timeout',
        '$filter',
        'toaster',
        'TestsService',
        function ($rootScope, $scope, $modal, $http, fillTestsService, ngTableParams, $translate, $timeout, $filter, toaster, testsService) {

            $scope.chooseData = {};

            $scope.getAllSubjects = function() {
                $scope.subjects = [];
                testsService.findSubjects()
                    .then(function(subjects) {
                        $scope.subjects = subjects.data;
                        $scope.chooseData.test = undefined;
                        console.log($scope.subjects);
                });
            };

            $scope.getAllSubjects();





            /**
             * Localization of multiselect for type of organization



            $scope.isFilter = function () {
                var obj = $scope.tableParams.filter();
                for (var i in obj) {
                    if (obj.hasOwnProperty(i) && obj[i]) {
                        return true;
                    }
                }
                return false;
            };
             */

            $scope.receiveTests = function(subject) {
                $scope.tests = [];
                testsService.findTestBySubject(subject.id)
                    .then(function(tests) {
                        $scope.tests = tests.data;
                    });
            };

            $scope.getTestById = function(test) {
              $scope.testData = [];
                fillTestsService.getTestById(test.id)
                    .then(function(testData) {
                        $scope.testData = testData.data;
                        console.log(testData);
                        console.log($scope.testData);
                });
            };
                
            
            /**
             * Opens modal window for adding new category of counters.
             */
            $scope.openAddGroupModal = function() {
                var addGroup = $modal.open({
                    animation : true,
                    controller : 'GroupAddModalController',
                    templateUrl : '/resources/app/lecturer/views/modals/groups/group-add-modal.html',
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
                fillTestsService.getGroupById(
                    $rootScope.groupId).then(
                    function(data) {
                        $rootScope.group = data;
                        console.log($rootScope.group);

                        var groupDTOModal = $modal
                            .open({
                                animation : true,
                                controller : 'GroupEditModalController',
                                templateUrl : '/resources/app/lecturer/views/modals/groups/group-edit-modal.html',
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
                fillTestsService.deleteGroup(id).then(function () {
                    toaster.pop('error', $filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_DELETED_GROUP'));
                });
                $timeout(function() {
                    console.log('delete with timeout');
                    $rootScope.onTableHandling();
                }, 700);
            };

            $scope.$watch('chooseData.subjectTitle', function () {
                $scope.chooseData.test = undefined;
            });


        }]);