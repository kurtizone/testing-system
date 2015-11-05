angular
    .module('lecturerModule')
    .factory('RoleService', function ($http) {
        return {
            isSuperAdmin: function () {
                var url = '/lecturer/is_super_student/';
                return $http.get(url)
                    .then(function(result) {
                        return result;
                    });
            }
        };
    });

