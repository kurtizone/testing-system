angular
    .module('adminModule')
    .controller(
    'SubjectLecturerController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$modalInstance',
        'SubjectsService',
        function ($rootScope, $scope, $translate, $modalInstance,
                  subjectsService) {

            console.log($rootScope.lecturers);
            /**
             * Closes modal window on browser's back/forward button click.
             */
            $rootScope.$on('$locationChangeStart', function () {
                $modalInstance.close();
            });

            /**
             * Closes the modal window for adding new
             * organization.
             */
            $rootScope.closeModal = function (close) {
                $scope.resetAddSubjectForm();
                if(close === true) {
                    $modalInstance.close();
                }
                $modalInstance.dismiss();
            };



        }
    ]);