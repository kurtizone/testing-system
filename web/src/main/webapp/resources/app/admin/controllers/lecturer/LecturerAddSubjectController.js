angular
    .module('adminModule')
    .controller(
    'LecturerAddSubjectController',
    [
        '$rootScope',
        '$scope',
        '$translate',
        '$modalInstance',
        'LecturersService',
        '$filter',
        function ($rootScope, $scope, $translate, $modalInstance,
                  lecturersService) {

            console.log($rootScope.lecturerId);

            $scope.chooseData = {};
            $scope.getAllSubjects = function(lecturerId) {
                $scope.subjects = [];
                lecturersService.getAllAvaibleSubjects(lecturerId)
                    .then(function(subjects) {
                        $scope.subjects = subjects;
                        console.log($scope.subjects);
                    });
            };

            $scope.getAllSubjects($rootScope.lecturerId);


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
            $rootScope.closeModal = function (close) {
                $scope.resetAddSubjectForm();
                if(close === true) {
                    $modalInstance.close();
                }
                $modalInstance.dismiss();
            };

            /**
             * Validates organization form before saving
             */
            $scope.onAddSubjectFormSubmit = function () {
                $scope.$broadcast('show-errors-check-validity');
                if ($scope.addSubjectForm.$valid) {
                    $scope.addSubjectForm.subjectTitle = $scope.chooseData.subjectTitle.id;
                    var subjectForm = {
                        id: $scope.chooseData.subjectTitle.id
                    };
                    saveSubject(subjectForm);
                }
            };

            /**
             * Saves new organization from the form in database.
             * If everything is ok then resets the organization
             * form and updates table with organizations.
             */
            function saveSubject(subjectForm) {
                console.log(subjectForm);;
                console.log($rootScope.lecturer.id);
                lecturersService.addSubjectToLecturer(
                    subjectForm,
                    $rootScope.lecturer.id).then(
                    function (data) {
                        if (data == 200) {
                            $scope.closeModal(true);
                            $rootScope.onTableHandling();
                        }
                    });
            }


        }
    ]);