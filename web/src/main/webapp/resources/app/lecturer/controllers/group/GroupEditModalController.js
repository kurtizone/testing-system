angular
    .module('lecturerModule')
    .controller(
    'GroupEditModalController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$modalInstance',
        'GroupsService',
        '$filter',
        function ($rootScope, $scope, $translate, $modalInstance,
                  groupsService, $filter) {

    
            $scope.defaultData = {};
            $scope.defaultData.studyForm = {
                id: $rootScope.group.studyForm,
                label: $filter('translate')($rootScope.group.studyForm)
            };
            $scope.defaultData.degree = {
                id: $rootScope.group.degree,
                label: $filter('translate')($rootScope.group.degree)
            };

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
             * Closes the modal window for adding new
             * group.
             */
            $rootScope.closeModal = function (close) {
                if(close === true) {
                    $modalInstance.close();
                }
                $modalInstance.dismiss();
            };

            /**
             * Validates group form before saving
             */
            $scope.onEditGroupFormSubmit = function () {
                $scope.$broadcast('show-errors-check-validity');
                if ($scope.editGroupForm.$valid) {
                    var groupForm = {
                        title: $rootScope.group.title,
                        grade: $rootScope.group.grade,
                        degree: $scope.defaultData.degree.id,
                        studyForm: $scope.defaultData.studyForm.id,
                    };
                    saveGroup(groupForm);
                }
            };

            $scope.OnSelectDegree = function () {
                console.log($scope.defaultData.studyForm);
                console.log($scope.defaultData.degree);
            };

            /**
             * Saves new group from the form in database.
             * If everything is ok then resets the organization
             * form and updates table with organizations.
             */
            function saveGroup(groupForm) {
                console.log(groupForm);
                console.log($rootScope.group.id);
                groupsService.editGroup(
                    groupForm,
                    $rootScope.group.id).then(
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