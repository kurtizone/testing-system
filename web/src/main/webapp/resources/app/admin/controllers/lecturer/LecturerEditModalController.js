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
        function ($rootScope, $scope, $translate, $modalInstance,
                  lecturersService) {

    
            $scope.defaultData = {};
            $scope.defaultData.academicStatus = {
                id: $rootScope.lecturer.academicStatus,
                label: null
            };
            $scope.defaultData.degree = {
                id: $rootScope.lecturer.degree,
                label: null
            };

            $scope.academicStatusData = [
                {
                    id: 'PROFESSOR',
                    label: null
                },
                {
                    id: 'DOCENT',
                    label: null
                },
                {
                    id: 'SENIOR_LECTURER',
                    label: null
                }
            ];

            $scope.degreeData = [
                {
                    id: 'CANDIDATE',
                    label: null
                },
                {
                    id: 'DOCTOR',
                    label: null
                },
                {
                    id: 'POSTGRADUATE',
                    label: null
                }
            ];
            console.log($scope.academicStatusData);
            console.log($scope.degreeData);
            /**
             * Localization of multiselect for type of organization
             */
            $scope.setTypeDataLanguage = function () {
                var lang = $translate.use();
                if (lang === 'ukr') {
                    $scope.academicStatusData[0].label = 'Професор';
                    $scope.academicStatusData[1].label = 'Доцент';
                    $scope.academicStatusData[2].label = 'Старший викладач';
                    $scope.degreeData[0].label = 'Кандидат наук';
                    $scope.degreeData[1].label = 'Доктор наук';
                    $scope.degreeData[2].label = 'Аспірант';
                } else if (lang === 'eng') {
                    $scope.academicStatusData[0].label = 'Professor';
                    $scope.academicStatusData[1].label = 'Docent';
                    $scope.academicStatusData[2].label = 'Senior Lecturer';
                    $scope.degreeData[0].label = 'PhD';
                    $scope.degreeData[1].label = 'Doctorate';
                    $scope.degreeData[2].label = 'Postgraduate';
                }
            };


            var setCurrentTypeDataLanguage = function () {
                var lang = $translate.use();
                if (lang === 'ukr') {
                    switch ($scope.defaultData.academicStatus.id) {
                        case "PROFESSOR":
                            console.log($scope.defaultData.academicStatus);
                            $scope.defaultData.academicStatus.label = 'Професор';
                            break;
                        case "DOCENT":
                            console.log($scope.defaultData.academicStatus);
                            $scope.defaultData.academicStatus.label = 'Доцент';
                            break;
                        case "SENIOR_LECTURER":
                            console.log($scope.defaultData.academicStatus);
                            $scope.defaultData.academicStatus.label = 'Старший викладач';
                            break;
                        default:
                            console.log($scope.defaultData.academicStatus.id + " not lecturer");
                    }
                    switch ($scope.defaultData.degree.id) {
                        case "CANDIDATE":
                            console.log($scope.defaultData.degree);
                            $scope.defaultData.degree.label = 'Кандидат наук';
                            break;
                        case "DOCTOR":
                            console.log($scope.defaultData.degree);
                            $scope.defaultData.degree.label = 'Доктор наук';
                            break;
                        case "POSTGRADUATE":
                            console.log($scope.defaultData.degree);
                            $scope.defaultData.degree.label = 'Аспірант';
                            break;
                        default:
                            console.log($scope.defaultData.degree.id + " not lecturer");
                    }

                } else if (lang === 'eng') {
                    switch ($scope.defaultData.academicStatus.id) {
                        case "PROFESSOR":
                            $scope.defaultData.academicStatus.label = 'Professor';
                            break;
                        case "THERMAL":
                            $scope.defaultData.academicStatus.label = 'Docent';
                            break;
                        case "SENIOR_LECTURER":
                            console.log($scope.defaultData.academicStatus);
                            $scope.defaultData.academicStatus.label = 'Senior Lecturer';
                            break;
                        default:
                            console.log($scope.defaultData.academicStatus.id + " not lecturer");
                    }
                    switch ($scope.defaultData.degree.id) {
                        case "CANDIDATE":
                            $scope.defaultData.degree.label = 'PhD';
                            break;
                        case "DOCTOR":
                            $scope.defaultData.degree.label = 'Doctorate';
                            break;
                        case "POSTGRADUATE":
                            console.log($scope.defaultData.degree);
                            $scope.defaultData.degree.label = 'Postgraduate';
                            break;
                        default:
                            console.log($scope.defaultData.degree.id + " not lecturer");
                    }
                }
            };

            $scope.setTypeDataLanguage();
            setTimeout(setCurrentTypeDataLanguage(), 2000);

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
            $rootScope.closeModal = function () {
                $modalInstance.close();
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
                            $scope.closeModal();
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