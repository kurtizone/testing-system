angular
    .module('adminModule')
    .controller(
    'LecturerSubjectsController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$modalInstance',
        'LecturersService',
        'toaster',
        '$filter',
        '$timeout',
        function ($rootScope, $scope, $translate, $modalInstance, lecturersService, toaster, $filter, $timeout) {

            $scope.deleteSubjectOfLecturer = function (lecturerId, subjectId) {
                console.log(lecturerId);
                console.log(subjectId);
                lecturersService.deleteSubjectOfLecturer(lecturerId, subjectId).then(function () {
                    toaster.pop('error', $filter('translate')('INFORMATION'), $filter('translate')('SUCCESSFUL_DELETED_LECTURER'));
                });
                $timeout(function() {
                    console.log('delete with timeout');
                    lecturersService.getListOfSubjects(lecturerId).then(
                        function(data) {
                            $rootScope.lecturerSubjects = data;
                            console.log($rootScope.lecturerSubjects);
                        });
                }, 700);
            };


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