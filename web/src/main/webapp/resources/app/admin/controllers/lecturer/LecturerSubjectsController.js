angular
    .module('adminModule')
    .controller(
    'LecturerSubjectsController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$modalInstance',
        function ($rootScope, $scope, $translate, $modalInstance) {

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
             * organization.
             */
            $rootScope.closeModal = function (close) {
                $scope.resetAddLecturerForm();
                if(close === true) {
                    $modalInstance.close();
                }
                $modalInstance.dismiss();
            };

        }
    ]);