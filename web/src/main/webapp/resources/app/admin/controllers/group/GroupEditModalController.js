angular
    .module('adminModule')
    .controller(
    'GroupEditModalController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$modalInstance',
        'GroupsService',
        function ($rootScope, $scope, $translate, $modalInstance,
                  groupsService) {

    
            $scope.defaultData = {};
            $scope.defaultData.studyForm = {
                id: $rootScope.group.studyForm,
                label: null
            };
            $scope.defaultData.degree = {
                id: $rootScope.group.degree,
                label: null
            };

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

            console.log($scope.studyFormData);
            console.log($scope.degreeData);
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


            var setCurrentTypeDataLanguage = function () {
                var lang = $translate.use();
                if (lang === 'ukr') {
                    switch ($scope.defaultData.studyForm.id) {
                        case "EXTERNAL":
                            console.log($scope.defaultData.studyForm);
                            $scope.defaultData.studyForm.label = 'Екстернат';
                            break;
                        case "DAILY":
                            console.log($scope.defaultData.studyForm);
                            $scope.defaultData.studyForm.label = 'Денна';
                            break;
                        case "NONRESIDENCE":
                            console.log($scope.defaultData.studyForm);
                            $scope.defaultData.studyForm.label = 'Заочна викладач';
                            break;
                        default:
                            console.log($scope.defaultData.studyForm.id + " not group");
                    }
                    switch ($scope.defaultData.degree.id) {
                        case "MASTER":
                            console.log($scope.defaultData.degree);
                            $scope.defaultData.degree.label = 'Магістри';
                            break;
                        case "BACHELOR":
                            console.log($scope.defaultData.degree);
                            $scope.defaultData.degree.label = 'Бакалаври';
                            break;
                        default:
                            console.log($scope.defaultData.degree.id + " not group");
                    }

                } else if (lang === 'eng') {
                    switch ($scope.defaultData.studyForm.id) {
                        case "EXTERNAL":
                            $scope.defaultData.studyForm.label = 'External';
                            break;
                        case "DAILY":
                            $scope.defaultData.studyForm.label = 'Daily';
                            break;
                        case "NONRESIDENCE":
                            console.log($scope.defaultData.studyForm);
                            $scope.defaultData.studyForm.label = 'Non-residence';
                            break;
                        default:
                            console.log($scope.defaultData.studyForm.id + " not group");
                    }
                    switch ($scope.defaultData.degree.id) {
                        case "MASTER":
                            $scope.defaultData.degree.label = 'Master';
                            break;
                        case "BACHELOR":
                            $scope.defaultData.degree.label = 'Bachelor';
                            break;
                        default:
                            console.log($scope.defaultData.degree.id + " not group");
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
             * group.
             */
            $rootScope.closeModal = function () {
                $modalInstance.close();
            };

            /**
             * Validates group form before saving
             */
            $scope.onEditGroupFormSubmit = function () {
                console.log($scope.group.studyForm);
                console.log($scope.group.degree);
                console.log($scope.defaultData.studyForm.id);
                console.log($scope.defaultData.degree.id);
                $scope.$broadcast('show-errors-check-validity');
                if ($scope.editGroupForm.$valid) {
                    var groupForm = {
                        firstName: $rootScope.group.firstName,
                        lastName: $rootScope.group.lastName,
                        middleName: $rootScope.group.middleName,
                        studyForm: $scope.defaultData.studyForm.id,
                        degree: $scope.defaultData.degree.id
                    };
                    console.log(groupForm.studyForm);
                    console.log(groupForm.degree);
                    console.log(groupForm);
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