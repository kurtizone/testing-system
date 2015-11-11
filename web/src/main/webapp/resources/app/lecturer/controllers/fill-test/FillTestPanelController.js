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

            console.log($scope.testData);

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
                        $scope.testData = testData;
                        console.log(testData);
                        console.log($scope.testData);
                });
            };

            $rootScope.reloadQuestions = function() {
                $timeout(function() {
                    fillTestsService.getTestById($scope.testData.id)
                        .then(function(testData) {
                            $scope.testData = testData;
                        });
                }, 700);
            }
                
            
            /**
             * Opens modal window for adding new category of counters.
             */
            $scope.openAddQuestionModal = function() {
                $rootScope.testId = $scope.testData.id;
                var addQuestion = $modal.open({
                    animation : true,
                    controller : 'QuestionAddModalController',
                    templateUrl : '/resources/app/lecturer/views/modals/fill-test/question-add-modal.html',
                    size: 'lg'
                });
                addQuestion.result.then(function () {
                    toaster.pop('success',$filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_ADDED_GROUP'));
                });
            };

            /**
             * Opens modal window for editing category of counter.
             */
            $scope.openEditQuestionModal = function(
                questionId) {
                $rootScope.questionId = questionId;
                fillTestsService.getQuestionById(
                    $rootScope.questionId).then(
                    function(data) {
                        $rootScope.question = data;
                        console.log($rootScope.question);
                        var groupDTOModal = $modal
                            .open({
                                animation : true,
                                controller : 'QuestionEditModalController',
                                templateUrl : '/resources/app/lecturer/views/modals/fill-test/question-edit-modal.html',
                                size: 'lg'
                            });
                        groupDTOModal.result.then(function () {
                            toaster.pop('info', $filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_EDITED_GROUP'));
                        });
                    });

            };

            $scope.deleteQuestion = function (id) {
                $rootScope.id = id;
                console.log($rootScope.id);
                fillTestsService.deleteQuestion(id).then(function () {
                    toaster.pop('error', $filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_DELETED_GROUP'));
                });
                $timeout(function() {
                    console.log('delete with timeout');
                    $rootScope.reloadQuestions();
                }, 700);
            };

            $scope.$watch('chooseData.subjectTitle', function () {
                $scope.chooseData.test = undefined;
                $scope.testData = undefined;
            });


        }]);