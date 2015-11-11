angular
    .module('lecturerModule')
    .controller(
    'QuestionEditModalController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$modalInstance',
        'FillTestsService',
        '$filter',
        'toaster',
        function ($rootScope, $scope, $translate, $modalInstance,
                  fillTestsService, $filter, toaster) {

            $scope.defaultData = {};
            $scope.editQuestionFormData = {};
            $scope.editQuestionFormData.questionType = undefined;
            $scope.answerDTOList = [{}];

            $scope.questionTypeData = [
                {
                    id: 'ONE',
                    label: $filter('translate')('ONE')
                },
                {
                    id: 'MULTI',
                    label: $filter('translate')('MULTI')
                }
            ];
            $scope.choices = [{}];
            for (var i = 0; i < $rootScope.question.answerDTOList.length; i++) {
                correctness = false;
                if($rootScope.question.answerDTOList[i].grade > 0) {
                    correctness = true;
                }
                $scope.choices[i] = {
                    id: $rootScope.question.answerDTOList[i].id,
                    text: $rootScope.question.answerDTOList[i].text,
                    correct: correctness
                }
            }
            $scope.defaultData.questionType = {
                    id: $rootScope.question.questionType,
                    label: $filter('translate')($rootScope.question.questionType)
            }



            /**
             * Localization of multiselect for type of organization
             */
            $scope.setTypeDataLanguage = function () {
            };
            $scope.setTypeDataLanguage();

            /**
             * Closes modal window on browser's back/forward button click.
             */
            $rootScope.$on('$locationChangeStart', function () {
                $modalInstance.close();
            });

            /**
            /**
             * Closes the modal window for editing new
             * organization.
             */
            $rootScope.closeModal = function (close) {
                if(close === true) {
                    $modalInstance.close();
                }
                $modalInstance.dismiss();
            };

            $scope.setChoiceForQuestion = function (choice) {
                angular.forEach($scope.choices, function (choice) {
                    choice.correct = false;
                });

                choice.correct = true;
            };


            $scope.addNewChoice = function() {
                var newItemNo = $scope.choices.length+1;
                $scope.choices.push({'id': -1, text: '', correct: false});
            };

            $scope.removeChoice = function(id) {
                var lastItem = $scope.choices.length-1;
                $scope.choices.splice(lastItem);
                /*if(id !== null) {
                    fillTestsService.deleteAnswer(id).then(function () {
                        toaster.pop('error', $filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_DELETED_ANSWER'));
                    });
                }*/
            };

            /**
             * Validates organization form before saving
             */
            $scope.onEditQuestionFormSubmit = function () {
                $scope.$broadcast('show-errors-check-validity');
                if ($scope.editQuestionForm.$valid) {
                    console.log($scope.editQuestionForm);
                    choicesToAnswerDTOList();
                    var questionForm = {
                        text: $rootScope.question.text,
                        questionType: $scope.defaultData.questionType.id,
                        answerDTOList: $scope.answerDTOList,
                        testId: $rootScope.testId
                    };
                    saveQuestion(questionForm);
                }
            };

            function choicesToAnswerDTOList() {
                angular.forEach($scope.choices, function (choice) {
                    $scope.answerDTOList.push({id: choice.id,'text': choice.text, 'correct': choice.correct});
                });
                $scope.answerDTOList.shift();
            }

            /**
             * Saves new organization from the form in database.
             * If everything is ok then resets the organization
             * form and updates table with organizations.
             */
            function saveQuestion(questionForm) {
                console.log(questionForm);
                fillTestsService.editQuestion(
                    questionForm,
                    $rootScope.question.id
                    ).then(function (data) {
                        if (data == 200) {
                            $scope.closeModal(true);
                            $rootScope.reloadQuestions();
                        }
                    });
            }

            $scope.USERNAME_REGEX = /^[a-z0-9_-]{3,16}$/;
            $scope.PASSWORD_REGEX = /^(?=.{4,20}$).*/;
            $scope.PHONE_REGEX = /^[1-9]\d{8}$/;
            $scope.EMAIL_REGEX = /^[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i;
            $scope.FIRST_LAST_NAME_REGEX = /^([A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}\u002d{1}[A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}|[A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20})$/;
            $scope.FIRST_LAST_NAME_REGEX = /^([A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}\u002d{1}[A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}|[A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20})$/;
            $scope.MIDDLE_NAME_REGEX = /^[A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}$/;
        }
    ]);