angular
    .module('adminModule')
    .controller(
    'GroupStudentsController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$modalInstance',
        function ($rootScope, $scope, $translate, $modalInstance) {

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
             * organization.
             */
            $rootScope.closeModal = function (close) {
                if(close === true) {
                    $modalInstance.close();
                }
                $modalInstance.dismiss();
            };


        }
    ]);