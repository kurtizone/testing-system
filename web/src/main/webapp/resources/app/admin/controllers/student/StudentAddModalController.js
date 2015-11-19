angular
    .module('adminModule')
    .controller(
    'StudentAddModalController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$modalInstance',
        'StudentsService',
        '$filter',
        function ($rootScope, $scope, $translate, $modalInstance,
                  studentsService, $filter) {

            $scope.addStudentFormData = {};

            $scope.addStudentFormData.enable = undefined;

            $scope.enableData = [
                {
                    id: 'true',
                    label: $filter('translate')('true')
                },
                {
                    id: 'false',
                    label: $filter('translate')('false')
                }

            ];

            $scope.chooseData = {};
            $scope.getAllGroups = function() {
                $scope.groups = [];
                studentsService.getAllGroups()
                    .then(function(groups) {
                        $scope.groups = groups;
                        console.log($scope.groups);
                    });
            };

            $scope.getAllGroups();

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
            $scope.resetAddStudentForm = function () {
                $scope.$broadcast('show-errors-reset');
                $scope.addStudentForm.$setPristine();
                $scope.addStudentForm.$setUntouched();
                $scope.addStudentFormData = {};
            };

            /**
             * Closes the modal window for adding new
             * organization.
             */
            $rootScope.closeModal = function (close) {
                $scope.resetAddStudentForm();
                if(close === true) {
                    $modalInstance.close();
                }
                $modalInstance.dismiss();
            };

            $scope.isUsernameAvailable = true;
            $scope.checkIfUsernameIsAvailable = function () {
                var username = $scope.addStudentFormData.username;
                console.log($scope.addStudentFormData.username);
                studentsService.isUsernameAvailable(username).then(
                    function (data) {
                        $scope.isUsernameAvailable = data;
                        if ($scope.addStudentFormData.username === undefined) {
                            $scope.addStudentForm.username.$valid = !data;
                            $scope.addStudentForm.username.$invalid = data;
                        } else {
                            $scope.addStudentForm.username.$valid = data;
                            $scope.addStudentForm.username.$invalid = !data;
                        }

                    });
            }

            /**
             * Validates organization form before saving
             */
            $scope.onAddStudentFormSubmit = function () {
                $scope.$broadcast('show-errors-check-validity');
                if ($scope.addStudentForm.$valid) {
                    $scope.addStudentFormData.groupId = $scope.chooseData.group.id;
                    $scope.addStudentFormData.enable = $scope.addStudentFormData.enable.id;
                    saveStudent();
                }
            };

            /**
             * Saves new organization from the form in database.
             * If everything is ok then resets the organization
             * form and updates table with organizations.
             */
            function saveStudent() {
                console.log($scope.addStudentFormData);
                studentsService.saveStudent($scope.addStudentFormData)
                    .then(function (data) {
                        if (data == 201) {
                            $scope.closeModal(true);
                            $rootScope.onTableHandling();
                        }
                    });
            }

            $scope.USERNAME_REGEX = /^[a-z0-9_-]{3,16}$/;
            $scope.PHONE_REGEX = /^\d{10}$/;
            $scope.EMAIL_REGEX = /^[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i;
            $scope.FIRST_LAST_NAME_REGEX = /^([A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}\u002d{1}[A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}|[A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20})$/;
            $scope.MIDDLE_NAME_REGEX = /^[A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}$/;
        }
    ]);