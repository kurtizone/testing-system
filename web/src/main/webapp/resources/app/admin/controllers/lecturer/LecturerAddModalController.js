angular
    .module('adminModule')
    .controller(
    'LecturerAddModalController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$modalInstance',
        'LecturersService',
        function ($rootScope, $scope, $translate, $modalInstance,
                  lecturersService) {

            $scope.addLecturerFormData = {};
            $scope.addLecturerFormData.academicStatus = undefined;
            $scope.addLecturerFormData.degree = undefined;

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
            $scope.resetAddLecturerForm = function () {
                $scope.$broadcast('show-errors-reset');
                $scope.addLecturerForm.$setPristine();
                $scope.addLecturerForm.$setUntouched();
                $scope.addLecturerFormData = {};
            };

            /**
             * Closes the modal window for adding new
             * organization.
             */
            $rootScope.closeModal = function () {
                $scope.resetAddLecturerForm();
                $modalInstance.close();
            };

            /**
             * Validates organization form before saving
             */
            $scope.onAddLecturerFormSubmit = function () {
                $scope.$broadcast('show-errors-check-validity');
                if ($scope.addLecturerForm.$valid) {
                    $scope.addLecturerFormData.academicStatus = $scope.addLecturerFormData.academicStatus.id;
                    $scope.addLecturerFormData.degree = $scope.addLecturerFormData.degree.id;
                    console.log($scope.addLecturerForm);
                    saveLecturer();
                }
            };

            /**
             * Saves new organization from the form in database.
             * If everything is ok then resets the organization
             * form and updates table with organizations.
             */
            function saveLecturer() {
                console.log($scope.addLecturerFormData);
                lecturersService.saveLecturer($scope.addLecturerFormData)
                    .then(function (data) {
                        if (data == 201) {
                            $scope.closeModal();
                            $scope.resetAddLecturerForm();
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