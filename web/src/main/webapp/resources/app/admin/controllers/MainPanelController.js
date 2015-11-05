angular
    .module('adminModule')
    .controller('MainPanelController', ['$scope', 'StatisticService',
        function ($scope, statisticService) {
            $scope.statistics = {
                subjects: 0,
                lecturers: 0,
                groups: 0,
                students: 0
            };
            statisticService.subjects().then(function (data) {
                $scope.statistics.subjects = data.count;
            });
            statisticService.lecturers().then(function (data) {
                $scope.statistics.lecturers = data.count;
            });
            statisticService.groups().then(function (data) {
                $scope.statistics.groups = data.count;
            });
            statisticService.students().then(function (data) {
                $scope.statistics.students = data.count;
            });
    }]);
