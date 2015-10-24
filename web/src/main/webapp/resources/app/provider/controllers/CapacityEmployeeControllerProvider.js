/**
 * Created by MAX on 12.07.2015.
 */
angular
    .module('employeeModule')

    .controller('CapacityEmployeeControllerProvider', ['$scope', '$log', '$modalInstance', 'capacity',
        function ($scope, $log, $modalInstance, capacity) {

            $scope.verifications = capacity.data.content;
            $log.info($scope.verifications);

            $scope.close = function () {
                $modalInstance.close(); 
            };


        }]);



