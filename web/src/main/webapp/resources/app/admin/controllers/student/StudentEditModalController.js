angular
    .module('adminModule')
    .controller(
    'StudentEditModalController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$modalInstance',
        'StudentsService',
        '$filter',
        function ($rootScope, $scope, $translate, $modalInstance,
                  studentsService, $filter) {

            $scope.defaultData = {};
            $scope.setTypeDataLanguage = function () {
            };
            $scope.setTypeDataLanguage();
            $scope.defaultData.enable = {
                id: $rootScope.student.enable,
                label: $filter('translate')($rootScope.student.enable)
            };

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
            $scope.getAllGroups = function () {
                $scope.groups = [];
                studentsService.getAllGroups()
                    .then(function (groups) {
                        $scope.groups = groups;
                        console.log($scope.groups);
                    });
                $scope.chooseData.group = {
                    id: $rootScope.student.groupId,
                    title: $rootScope.student.groupTitle
                };
            };

            $scope.getAllGroups();

            /**
             * Closes modal window on browser's back/forward button click.
             */
            $rootScope.$on('$locationChangeStart', function () {
                $modalInstance.close();
            });

            /**
             * Closes the modal window for adding new
             * student.
             */
            $rootScope.closeModal = function (close) {
                if (close === true) {
                    $modalInstance.close();
                }
                $modalInstance.dismiss();
            };

            $scope.changePassword = function () {
                $scope.password = 'generate';
                $scope.generationMessage = true;
            };


            /**
             * Validates student form before saving
             */
            $scope.onEditStudentFormSubmit = function () {
                $scope.$broadcast('show-errors-check-validity');
                if ($scope.editStudentForm.$valid) {
                    var studentForm = {
                        firstName: $rootScope.student.firstName,
                        lastName: $rootScope.student.lastName,
                        middleName: $rootScope.student.middleName,
                        numberGradebook: $rootScope.student.numberGradebook,
                        groupId: $scope.chooseData.group.id,
                        enable: $scope.defaultData.enable.id,
                        username: $rootScope.student.username,
                        email: $rootScope.student.email,
                        password:  $scope.password,
                        phone: $rootScope.student.phone
                    };
                    saveStudent(studentForm);
                }
            };

            /**
             * Saves new student from the form in database.
             * If everything is ok then resets the organization
             * form and updates table with organizations.
             */
            function saveStudent(studentForm) {
                console.log(studentForm);
                console.log($rootScope.student.id);
                studentsService.editStudent(
                    studentForm,
                    $rootScope.student.id).then(
                    function (data) {
                        if (data == 200) {
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