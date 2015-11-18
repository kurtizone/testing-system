angular
    .module('lecturerModule')
    .controller(
    'TestEditModalController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$modalInstance',
        'TestsService',
        '$filter',
        'subjects',
        function ($rootScope, $scope, $translate, $modalInstance,
                  testsService, $filter, subjects) {

            $scope.subjects = subjects.data;
            $scope.defaultData = {};
            $scope.defaultData.type = {
                id: $rootScope.test.type,
                label: $filter('translate')($rootScope.test.type)
            };
            $scope.defaultData.avaible = {
                id: $rootScope.test.avaible,
                label: $filter('translate')($rootScope.test.avaible)
            };
            $scope.defaultData.subjectTitle = {
                id: $rootScope.test.subjectId,
                title: $rootScope.test.subject
            };

            $scope.typeData = [
                {
                    id: 'MODUL',
                    label: $filter('translate')('MODUL')
                },
                {
                    id: 'LABARATORY',
                    label: $filter('translate')('LABARATORY')
                },
                {
                    id: 'FINAL',
                    label: $filter('translate')('FINAL')
                }
            ];

            console.log($scope.typeData);

            $scope.avaibleData = [
                {
                    id: true,
                    label: $filter('translate')('TRUE_AVAIBLE')
                },
                {
                    id: false,
                    label: $filter('translate')('FALSE_AVAIBLE')
                },

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
             * test.
             */
            $rootScope.closeModal = function (close) {
                if(close === true) {
                    $modalInstance.close();
                }
                $modalInstance.dismiss();
            };

            /**
             * Validates test form before saving
             */
            $scope.onEditTestFormSubmit = function () {
                $scope.$broadcast('show-errors-check-validity');
                if ($scope.editTestForm.$valid) {
                    var testForm = {
                        title: $rootScope.test.title,
                        subjectId: $scope.defaultData.subjectTitle.id,
                        subject: $scope.defaultData.subjectTitle.title,
                        type: $scope.defaultData.type.id,
                        maxGrade: $rootScope.test.maxGrade,
                        time: $rootScope.test.time,
                        avaible: $scope.defaultData.avaible.id
                    };
                    saveTest(testForm);
                }
            };



            /**
             * Saves new test from the form in database.
             * If everything is ok then resets the organization
             * form and updates table with organizations.
             */
            function saveTest(testForm) {
                console.log(testForm);
                console.log($rootScope.test.id);
                testsService.editTest(
                    testForm,
                    $rootScope.test.id).then(
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