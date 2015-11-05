angular
    .module('studentModule')
    .controller(
    'SubjectEditModalController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$modalInstance',
        'SubjectsService',
        function ($rootScope, $scope, $translate, $modalInstance,
                  subjectsService) {

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

            /**
             * Validates organization form before saving
             */
            $scope.onEditSubjectFormSubmit = function () {
                $scope.$broadcast('show-errors-check-validity');
                console.log($scope.editSubjectForm);
                if ($scope.editSubjectForm.$valid) {
                    var subjectForm = {
                        title: $rootScope.subject.title,
                        multiplier: $rootScope.subject.multiplier,
                        hours: $rootScope.subject.hours,
                    };
                    console.log(subjectForm);
                    saveSubject(subjectForm);
                }
            };

            /**
             * Saves new organization from the form in database.
             * If everything is ok then resets the organization
             * form and updates table with organizations.
             */
            function saveSubject(subjectForm) {
                console.log(subjectForm);
                console.log($rootScope.subject.id);
                subjectsService.editSubject(
                    subjectForm,
                    $rootScope.subject.id).then(
                    function (data) {
                        if (data == 200) {
                            $scope.closeModal(true);
                            $rootScope.onTableHandling();
                        }
                    });
            }

        }
    ]);