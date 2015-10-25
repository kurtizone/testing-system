/**
 * Created by Volodya NT on 03.10.2015.
 */
angular
    .module('employeeModule')

    .controller('CapacityEmployeeControllerVerificator', ['$scope', '$log', '$modalInstance', 'capacity',
        function ($scope, $log, $modalInstance, capacity) {

            $scope.verifications = capacity.data.content;

            $log.info($scope.verifications);

            $scope.close = function () {
                $modalInstance.close();
            };


        }]);




