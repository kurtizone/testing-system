angular
    .module('lecturerModule')
    .controller(
    'QuestionAddModalController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$modalInstance',
        'FillTestsService',
        '$filter',
        function ($rootScope, $scope, $translate, $modalInstance,
                  fillTestsService, $filter) {

            $scope.addQuestionFormData = {};
            $scope.addQuestionFormData.questionType = undefined;

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

            $scope.choices = [{id: 'choice1'}];
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
             * Resets organization form
             */
            $scope.resetAddQuestionForm = function () {
                $scope.$broadcast('show-errors-reset');
                $scope.addQuestionForm.$setPristine();
                $scope.addQuestionForm.$setUntouched();
                $scope.addQuestionFormData = {};
            };

            /**
             * Closes the modal window for adding new
             * organization.
             */
            $rootScope.closeModal = function (close) {
                $scope.resetAddQuestionForm();
                if(close === true) {
                    $modalInstance.close();
                }
                $modalInstance.dismiss();
            };

            $scope.addNewChoice = function() {
                var newItemNo = $scope.choices.length+1;
                $scope.choices.push({'id':'choice'+newItemNo});
            };

            $scope.removeChoice = function() {
                var lastItem = $scope.choices.length-1;
                $scope.choices.splice(lastItem);
            };

            /**
             * Validates organization form before saving
             */
            $scope.onAddQuestionFormSubmit = function () {
                $scope.$broadcast('show-errors-check-validity');
                if ($scope.addQuestionForm.$valid) {
                    $scope.addQuestionFormData.studyForm = $scope.addQuestionFormData.studyForm.id;
                    $scope.addQuestionFormData.degree = $scope.addQuestionFormData.degree.id;
                    console.log($scope.addQuestionForm);
                    saveQuestion();
                }
            };

            /**
             * Saves new organization from the form in database.
             * If everything is ok then resets the organization
             * form and updates table with organizations.
             */
            function saveQuestion() {
                console.log($scope.addQuestionFormData);
                fillTestsService.saveQuestion($scope.addQuestionFormData)
                    .then(function (data) {
                        if (data == 201) {
                            $scope.closeModal(true);
                            $rootScope.onTableHandling();
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