angular
    .module('lecturerModule')
    .factory('StatisticService', function ($http) {
        var getData = function (type) {
            return $http.get('/lecturer/statistics/' + type)
                .then(function(result) {
                    return result.data;
                });
        };
        return {
            subjects: function() { return getData('subjects'); },
            lecturers: function() { return getData('lecturers'); },
            groups: function() { return getData('groups'); },
            students: function() {return getData('students'); },
            employee: function() { return getData('employee'); }
        }
    });
