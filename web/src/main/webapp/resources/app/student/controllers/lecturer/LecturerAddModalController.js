angular
    .module('studentModule')
    .controller(
    'LecturerAddModalController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$modalInstance',
        'LecturersService',
        '$filter',
        function ($rootScope, $scope, $translate, $modalInstance,
                  lecturersService, $filter) {

            $scope.addLecturerFormData = {};
            $scope.addLecturerFormData.academicStatus = undefined;
            $scope.addLecturerFormData.degree = undefined;

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
            $rootScope.closeModal = function (close) {
                $scope.resetAddLecturerForm();
                if(close === true) {
                    $modalInstance.close();
                }
                $modalInstance.dismiss();
            };

            /**
             * Validates organization form before saving
             */
            $scope.onAddLecturerFormSubmit = function () {
                $scope.$broadcast('show-errors-check-validity');
                if ($scope.addLecturerForm.$valid) {
                    $scope.addLecturerFormData.academicStatus = $scope.addLecturerFormData.academicStatus.id;
                    $scope.addLecturerFormData.degree = $scope.addLecturerFormData.degree.id;
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