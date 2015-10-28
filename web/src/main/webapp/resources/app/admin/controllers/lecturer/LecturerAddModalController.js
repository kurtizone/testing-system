angular
    .module('adminModule')
    .controller(
    'LecturerAddModalController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$modalInstance',
        'SubjectsService',
        function ($rootScope, $scope, $translate, $modalInstance,
                  subjectsService) {

            $scope.addSubjectFormData = {};
            
            /**
             * Closes modal window on browser's back/forward button click.
             */
            $rootScope.$on('$locationChangeStart', function () {
                $modalInstance.close();
            });

            /**
             * Resets organization form
             */
            $scope.resetAddSubjectForm = function () {
                $scope.$broadcast('show-errors-reset');
                $scope.addSubjectForm.$setPristine();
                $scope.addSubjectForm.$setUntouched();
                $scope.addSubjectFormData = {};
            };

            /**
             * Closes the modal window for adding new
             * organization.
             */
            $rootScope.closeModal = function () {
                $scope.resetAddSubjectForm();
                $modalInstance.close();
            };

            /**
             * Validates organization form before saving
             */
            $scope.onAddSubjectFormSubmit = function () {
                $scope.$broadcast('show-errors-check-validity');
                if ($scope.addSubjectForm.$valid) {
                    console.log($scope.addSubjectForm);
                    saveSubject();
                }
            };

            /**
             * Saves new organization from the form in database.
             * If everything is ok then resets the organization
             * form and updates table with organizations.
             */
            function saveSubject() {
                console.log($scope.addSubjectFormData);
                subjectsService.saveSubject($scope.addSubjectFormData)
                    .then(function (data) {
                        if (data == 201) {
                            $scope.closeModal();
                            $scope.resetAddSubjectForm();
                            $rootScope.onTableHandling();
                        }
                    });
            }

        }
    ]);