angular
    .module('studentModule')
    .controller(
    'SubjectLecturersController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$modalInstance',
        function ($rootScope, $scope, $translate, $modalInstance) {

            
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