angular
    .module('studentModule')
    .controller('MainPanelController', ['$scope', 'StatisticService', '$timeout',
        function ($scope, statisticService, $timeout) {
            $scope.statistics = {
                subjects: 0,
                results: 0,
                groupResults: 0,
                tests: 0
            };
            statisticService.subjects().then(function (data) {
                $scope.statistics.subjects = data.count;
            });
            statisticService.results().then(function (data) {
                $scope.statistics.results = data.count;
            });
            statisticService.groupResults().then(function (data) {
                $scope.statistics.groupResults = data.count;
            });
            statisticService.tests().then(function (data) {
                $scope.statistics.tests = data.count;
            });

            $scope.countdownVal = 60;
            $scope.$on('timer-stopped', function (event, data){
                console.log('Timer Stopped - data = ', data);
            });
            $timeout(function() {
                $scope.startTimer();
                console.log('Timer Started');
            }, 5000);
            $scope.startTimer = function (){
                $scope.$broadcast('timer-start');
            };

        }]);
