angular
    .module('studentModule')
    .factory('RoleService', function ($http) {
        return {
            isSuperAdmin: function () {
                var url = '/student/is_super_student/';
                return $http.get(url)
                    .then(function(result) {
                        return result;
                    });
            }
        };
    });

