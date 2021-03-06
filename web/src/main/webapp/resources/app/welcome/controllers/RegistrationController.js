angular
    .module('welcomeModule')
    .controller('RegistrationController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$filter',
        'DataReceivingService',
        '$window',
        'toaster',
        '$timeout',
        function ($rootScope, $scope, $translate, $filter, dataReceivingService, $window, toaster, $timeout) {

            $scope.studentFormData = {};
            $scope.lecturerFormData = {};
            $scope.userRoleFormData = {};
            $scope.userRoleFormData.userRole = undefined;


            $scope.chooseData = {};
            $scope.getAllGroups = function () {
                $scope.groups = [];
                dataReceivingService.getAllGroups()
                    .then(function (groups) {
                        $scope.groups = groups.data;
                        console.log($scope.groups);
                    });
            };
            $scope.getAllGroups();

            $scope.userRolesData = [
                {
                    id: 'STUDENT',
                    label: $filter('translate')('STUDENT')
                },
                {
                    id: 'LECTURER',
                    label: $filter('translate')('LECTURER')
                }
            ];


            $scope.academicStatusData = [
                {
                    id: 'PROFESSOR',
                    label: $filter('translate')('PROFESSOR')
                },
                {
                    id: 'DOCENT',
                    label: $filter('translate')('DOCENT')
                },
                {
                    id: 'SENIOR_LECTURER',
                    label: $filter('translate')('SENIOR_LECTURER')
                }
            ];

            $scope.degreeData = [
                {
                    id: 'CANDIDATE',
                    label: $filter('translate')('CANDIDATE')
                },
                {
                    id: 'DOCTOR',
                    label: $filter('translate')('DOCTOR')
                },
                {
                    id: 'POSTGRADUATE',
                    label: $filter('translate')('POSTGRADUATE')
                }
            ];


            $scope.setTypeDataLanguage = function () {
            };
            $scope.setTypeDataLanguage();


            $scope.isUsernameAvailable = true;
            $scope.checkIfUsernameIsAvailable = function () {
                var username = undefined;
                if ($scope.userRoleFormData.userRole.id === 'STUDENT') {
                    username = $scope.studentFormData.username;
                    console.log($scope.studentFormData.username);
                } else {
                    username = $scope.lecturerFormData.username;
                }
                dataReceivingService.isUsernameAvailable(username).then(
                    function (data) {
                        $scope.isUsernameAvailable = data;
                        if ($scope.userRoleFormData.userRole.id === 'STUDENT') {
                            if ($scope.studentFormData.username === undefined) {
                                $scope.studentForm.studentUsername.$valid = !data;
                                $scope.studentForm.studentUsername.$invalid = data;
                            } else {
                                $scope.studentForm.studentUsername.$valid = data;
                                $scope.studentForm.studentUsername.$invalid = !data;
                            }
                        } else {
                            if ($scope.lecturerFormData.username === undefined) {
                                $scope.studentForm.studentUsername.$valid = !data;
                                $scope.studentForm.studentUsername.$invalid = data;
                            } else {
                                $scope.lecturerForm.username.$valid = data;
                                $scope.lecturerForm.username.$invalid = !data;
                            }
                        }

                    })
            }

            /**
             * Resets organization form
             */
            $scope.resetForms = function () {
                $scope.$broadcast('show-errors-reset');

                $scope.lecturerForm.$setPristine();
                $scope.lecturerForm.$setUntouched();
                $scope.studentForm.$setPristine();
                $scope.studentForm.$setUntouched();

                $scope.studentFormData = {};
                $scope.lecturerFormData = {};
                $scope.chooseData.group = undefined;
                $scope.chooseData.academicStatus = undefined;
                $scope.chooseData.degree = undefined;


            };


            /**
             * Validates organization form before saving
             */
            $scope.submitStudent = function () {
                $scope.$broadcast('show-errors-check-validity');
                if ($scope.studentForm.$valid) {
                    $scope.studentFormData.groupId = $scope.chooseData.group.id;
                    console.log($scope.studentFormData);
                    saveStudent();
                }
            };

            $scope.submitLecturer = function () {
                $scope.$broadcast('show-errors-check-validity');
                if ($scope.lecturerForm.$valid) {
                    $scope.lecturerFormData.academicStatus = $scope.chooseData.academicStatus.id;
                    $scope.lecturerFormData.degree = $scope.chooseData.degree.id;
                    console.log($scope.lecturerFormData);
                    saveLecturer();
                }
            };

            /**
             * Saves new organization from the form in database.
             * If everything is ok then resets the organization
             * form and updates table with organizations.
             */
            function saveStudent() {
                dataReceivingService.saveStudent($scope.studentFormData)
                    .then(function (data) {
                        if (data == 201) {
                            console.log("student");
                            $scope.resetForms();
                            $scope.userRoleFormData = {};
                            $scope.userRoleFormData.userRole = undefined;
                            toaster.pop('success', $filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_REGISTERED_STUDENT'));
                            $timeout(function() {
                                console.log('redirect with timeout');
                                $window.location = '/';
                            }, 3500);

                        }
                    });
            }

            function saveLecturer() {
                dataReceivingService.saveLecturer($scope.lecturerFormData)
                    .then(function (data) {
                        if (data == 201) {
                            console.log("lecturer");
                            $scope.resetForms();
                            $scope.userRoleFormData = {};
                            $scope.userRoleFormData.userRole = undefined;
                            toaster.pop('success', $filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_REGISTERED_LECTURER'));
                            $timeout(function() {
                                console.log('redirect with timeout');
                                $window.location = '/';
                            }, 3500);
                        }
                    });
            }

            $scope.$watch('userRoleFormData.userRole', function () {
                $scope.resetForms();
            });

            /**
             * Receives all regex for input fields
             *
             */
            $scope.STREET_REGEX = /^[a-z\u0430-\u044f\u0456\u0457]{1,20}\s([A-Z\u0410-\u042f\u0407\u0406][a-z\u0430-\u044f\u0456\u0457\u0454]{1,20}\u002d[A-Z\u0410-\u042f\u0407\u0406\u0454][a-z\u0430-\u044f\u0456\u0457\u0454]{1,20}|[A-Z\u0410-\u042f\u0407\u0406\u0454][a-z\u0430-\u044f\u0456\u0457\u0454]{1,20})$/;
            $scope.FIRST_LAST_NAME_REGEX = /^([A-Z\u0410-\u042f\u0407\u0406\u0404'][a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}\u002d[A-Z\u0410-\u042f\u0407\u0406\u0404'][a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}|[A-Z\u0410-\u042f\u0407\u0406\u0404'][a-z\u0430-\u044f\u0456\u0457\u0454']{1,20})$/;
            $scope.MIDDLE_NAME_REGEX = /^[A-Z\u0410-\u042f\u0407\u0406\u0404'][a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}$/;
            $scope.FLAT_REGEX = /^([1-9][0-9]{0,3}|0)$/;
            $scope.BUILDING_REGEX = /^[1-9][0-9]{0,3}([A-Za-z]|[\u0410-\u042f\u0407\u0406\u0430-\u044f\u0456\u0457])?$/;
            $scope.PHONE_REGEX = /^\d{10}$/;
            $scope.EMAIL_REGEX = /^[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i;


        }
    ]
);


