angular
    .module('adminModule')
    .controller(
    'GroupSubjectsController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$modalInstance',
        'GroupsService',
        'toaster',
        '$filter',
        '$timeout',
        function ($rootScope, $scope, $translate, $modalInstance, groupsService, toaster, $filter, $timeout) {


            $scope.deleteSubjectOfLecturer = function (groupId, subjectId) {
                console.log(groupId);
                console.log(subjectId);
                groupsService.deleteSubjectOfGroup(groupId, subjectId).then(function () {
                    toaster.pop('error', $filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_DELETED_LECTURER'));
                });
                $timeout(function() {
                    console.log('delete with timeout');
                    groupsService.getSubjectsByGroupId(groupId).then(
                        function(data) {
                            $rootScope.groupSubjects = data;
                            console.log($rootScope.groupSubjects);
                        });
                }, 700);
            };

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