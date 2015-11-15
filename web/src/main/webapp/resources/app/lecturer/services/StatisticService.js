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
            tests: function() { return getData('tests'); },
            results: function() { return getData('results'); },
            employee: function() { return getData('employee'); }
        }
    });
