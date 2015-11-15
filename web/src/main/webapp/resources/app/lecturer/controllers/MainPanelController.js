angular
    .module('lecturerModule')
    .controller('MainPanelController', ['$scope', 'StatisticService',
        function ($scope, statisticService) {
            $scope.statistics = {
                subjects: 0,
                tests: 0,
                results: 0
            };
            statisticService.subjects().then(function (data) {
                $scope.statistics.subjects = data.count;
            });
            statisticService.tests().then(function (data) {
                $scope.statistics.tests = data.count;
            });
            statisticService.results().then(function (data) {
                $scope.statistics.results = data.count;
            });
    }]);
