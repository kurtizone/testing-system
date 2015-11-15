angular
    .module('adminModule')
    .factory('StatisticService', function ($http) {
        var getData = function (type) {
            return $http.get('/admin/statistics/' + type)
                .then(function(result) {
                    return result.data;
                });
        };
        return {
            subjects: function() { return getData('subjects'); },
            lecturers: function() { return getData('lecturers'); },
            groups: function() { return getData('groups'); },
            students: function() {return getData('students'); },
            results: function() {return getData('results'); },
            employee: function() { return getData('employee'); }
        }
    });
