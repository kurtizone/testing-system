angular
    .module('studentModule')
    .controller('CommonController', ['$rootScope', '$scope', 'RoleService',
        function ($rootScope, $scope, roleService) {
            /*    roleService.isSuperAdmin().then(function (result) {
                    $rootScope.isSuperAdmin = result.data;
                    console.log($rootScope.isSuperAdmin);
                });*/
    }]);
