angular
    .module('lecturerModule')
    .controller(
    'TestAddModalController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$modalInstance',
        'TestsService',
        '$filter',
        'subjects',
        function ($rootScope, $scope, $translate, $modalInstance,
                  testsService, $filter, subjects) {

            $scope.addTestFormData = {};
            $scope.addTestFormData.type = undefined;
            $scope.addTestFormData.avaible = undefined;
            $scope.subjects = subjects.data;

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
            /**
             * Closes modal window on browser's back/forward button click.
             */
            $rootScope.$on('$locationChangeStart', function () {
                $modalInstance.close();
            });

            /**
             * Resets organization form
             */
            $scope.resetAddTestForm = function () {
                $scope.$broadcast('show-errors-reset');
                $scope.addTestForm.$setPristine();
                $scope.addTestForm.$setUntouched();
                $scope.addTestFormData = {};
            };

            /**
             * Closes the modal window for adding new
             * organization.
             */
            $rootScope.closeModal = function (close) {
                $scope.resetAddTestForm();
                if(close === true) {
                    $modalInstance.close();
                }
                $modalInstance.dismiss();
            };

            $scope.setSubject = function (selectedSubject) {
                console.log($scope.addTestForm.subject);
                console.log(selectedSubject.title);
                $scope.addTestForm.subject = selectedSubject.title;
                console.log($scope.addTestForm.subjectId);
                console.log(selectedSubject.id);
                $scope.addTestForm.subjectId = selectedSubject.id;
            };

            /**
             * Validates organization form before saving
             */
            $scope.onAddTestFormSubmit = function () {
                $scope.$broadcast('show-errors-check-validity');
                if ($scope.addTestForm.$valid) {
                    console.log($scope.addTestFormData);
                    console.log($scope.addTestForm);
                    $scope.addTestFormData.subject = $scope.addTestFormData.subjectTitle.title;
                    $scope.addTestFormData.subjectId = $scope.addTestFormData.subjectTitle.id;
                    $scope.addTestFormData.type = $scope.addTestFormData.type.id;
                    $scope.addTestFormData.avaible = $scope.addTestFormData.avaible.id;
                    saveTest();
                }
            };

            /**
             * Saves new organization from the form in database.
             * If everything is ok then resets the organization
             * form and updates table with organizations.
             */
            function saveTest() {
                console.log($scope.addTestFormData);
                testsService.saveTest($scope.addTestFormData)
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