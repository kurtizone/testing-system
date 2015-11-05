angular
    .module('adminModule')
    .controller(
    'LecturerEditModalController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$modalInstance',
        'LecturersService',
        '$filter',
        function ($rootScope, $scope, $translate, $modalInstance,
                  lecturersService, $filter) {

    
            $scope.defaultData = {};
            $scope.defaultData.academicStatus = {
                id: $rootScope.lecturer.academicStatus,
                label: $filter('translate')($rootScope.lecturer.academicStatus)
            };
            $scope.defaultData.degree = {
                id: $rootScope.lecturer.degree,
                label: $filter('translate')($rootScope.lecturer.degree)
            };

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

            /**
             * Closes modal window on browser's back/forward button click.
             */
            $rootScope.$on('$locationChangeStart', function () {
                $modalInstance.close();
            });

            /**
             * Closes the modal window for adding new
             * lecturer.
             */
            $rootScope.closeModal = function (close) {
                if(close === true) {
                    $modalInstance.close();
                }
                $modalInstance.dismiss();
            };

            /**
             * Validates lecturer form before saving
             */
            $scope.onEditLecturerFormSubmit = function () {
                console.log($scope.lecturer.academicStatus);
                console.log($scope.lecturer.degree);
                console.log($scope.defaultData.academicStatus.id);
                console.log($scope.defaultData.degree.id);
                $scope.$broadcast('show-errors-check-validity');
                if ($scope.editLecturerForm.$valid) {
                    var lecturerForm = {
                        firstName: $rootScope.lecturer.firstName,
                        lastName: $rootScope.lecturer.lastName,
                        middleName: $rootScope.lecturer.middleName,
                        academicStatus: $scope.defaultData.academicStatus.id,
                        degree: $scope.defaultData.degree.id
                    };
                    console.log(lecturerForm.academicStatus);
                    console.log(lecturerForm.degree);
                    console.log(lecturerForm);
                    saveLecturer(lecturerForm);
                }
            };

            $scope.OnSelectDegree = function () {
                console.log($scope.defaultData.academicStatus);
                console.log($scope.defaultData.degree);
            };

            /**
             * Saves new lecturer from the form in database.
             * If everything is ok then resets the organization
             * form and updates table with organizations.
             */
            function saveLecturer(lecturerForm) {
                console.log(lecturerForm);
                console.log($rootScope.lecturer.id);
                lecturersService.editLecturer(
                    lecturerForm,
                    $rootScope.lecturer.id).then(
                    function (data) {
                        if (data == 200) {
                            $scope.closeModal(true);
                            console.log(data);
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