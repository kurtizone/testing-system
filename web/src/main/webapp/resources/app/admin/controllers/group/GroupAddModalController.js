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
        '$filter',
        function ($rootScope, $scope, $translate, $modalInstance,
                  groupsService, $filter) {

            $scope.addGroupFormData = {};
            $scope.addGroupFormData.studyForm = undefined;
            $scope.addGroupFormData.degree = undefined;

            $scope.degreeData = [
                {
                    id: 'MASTER',
                    label: $filter('translate')('MASTER')
                },
                {
                    id: 'BACHELOR',
                    label: $filter('translate')('BACHELOR')
                }
            ];

            $scope.studyFormData = [
                {
                    id: 'EXTERNAL',
                    label: $filter('translate')('EXTERNAL')
                },
                {
                    id: 'DAILY',
                    label: $filter('translate')('DAILY')
                },
                {
                    id: 'NONRESIDENCE',
                    label: $filter('translate')('NONRESIDENCE')
                }
            ];


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
            $rootScope.closeModal = function (close) {
                $scope.resetAddGroupForm();
                if(close === true) {
                    $modalInstance.close();
                }
                $modalInstance.dismiss();
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
                            $scope.closeModal(true);
                            $rootScope.onTableHandling();
                        }
                    });
            }

            $scope.GROUP_REGEX = /^[a-z0-9_-]{3,16}$/;
            $scope.PASSWORD_REGEX = /^(?=.{4,20}$).*/;
            $scope.PHONE_REGEX = /^[1-9]\d{8}$/;
            $scope.EMAIL_REGEX = /^[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i;
            $scope.FIRST_LAST_NAME_REGEX = /^([A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}\u002d{1}[A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}|[A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20})$/;
            $scope.FIRST_LAST_NAME_REGEX = /^([A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}\u002d{1}[A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}|[A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20})$/;
            $scope.MIDDLE_NAME_REGEX = /^[A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}$/;
        }
    ]);