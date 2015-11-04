angular
    .module('adminModule')
    .controller(
    'GroupAddModalController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$modalInstance',
        'GroupsService',
        function ($rootScope, $scope, $translate, $modalInstance,
                  groupsService) {

            $scope.addGroupFormData = {};
            $scope.addGroupFormData.studyForm = undefined;
            $scope.addGroupFormData.degree = undefined;

            $scope.degreeData = [
                {
                    id: 'MASTER',
                    label: null
                },
                {
                    id: 'BACHELOR',
                    label: null
                }
            ];

            $scope.studyFormData = [
                {
                    id: 'EXTERNAL',
                    label: null
                },
                {
                    id: 'DAILY',
                    label: null
                },
                {
                    id: 'NONRESIDENCE',
                    label: null
                }
            ];


            /**
             * Localization of multiselect for type of organization
             */
            /**
             * Localization of multiselect for type of organization
             */
            $scope.setTypeDataLanguage = function () {
                var lang = $translate.use();
                if (lang === 'ukr') {
                    $scope.studyFormData[0].label = 'Екстернат';
                    $scope.studyFormData[1].label = 'Денна';
                    $scope.studyFormData[2].label = 'Заочна';
                    $scope.degreeData[0].label = 'Магістри';
                    $scope.degreeData[1].label = 'Бакалаври';
                } else if (lang === 'eng') {
                    $scope.studyFormData[0].label = 'External';
                    $scope.studyFormData[1].label = 'Daily';
                    $scope.studyFormData[2].label = 'Non-residence';
                    $scope.degreeData[0].label = 'Master';
                    $scope.degreeData[1].label = 'Bachelor';
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
            $scope.resetAddGroupForm = function () {
                $scope.$broadcast('show-errors-reset');
                $scope.addGroupForm.$setPristine();
                $scope.addGroupForm.$setUntouched();
                $scope.addGroupFormData = {};
            };

            /**
             * Closes the modal window for adding new
             * organization.
             */
            $rootScope.closeModal = function () {
                $scope.resetAddGroupForm();
                $modalInstance.close();
            };

            /**
             * Validates organization form before saving
             */
            $scope.onAddGroupFormSubmit = function () {
                $scope.$broadcast('show-errors-check-validity');
                if ($scope.addGroupForm.$valid) {
                    $scope.addGroupFormData.studyForm = $scope.addGroupFormData.studyForm.id;
                    $scope.addGroupFormData.degree = $scope.addGroupFormData.degree.id;
                    console.log($scope.addGroupForm);
                    saveGroup();
                }
            };

            /**
             * Saves new organization from the form in database.
             * If everything is ok then resets the organization
             * form and updates table with organizations.
             */
            function saveGroup() {
                console.log($scope.addGroupFormData);
                groupsService.saveGroup($scope.addGroupFormData)
                    .then(function (data) {
                        if (data == 201) {
                            $scope.closeModal();
                            $scope.resetAddGroupForm();
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