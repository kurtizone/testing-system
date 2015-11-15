angular
    .module('studentModule')
    .factory('StatisticService', function ($http) {
        var getData = function (type) {
            return $http.get('/student/statistics/' + type)
                .then(function(result) {
                    return result.data;
                });
        };
        return {
            subjects: function() { return getData('subjects'); },
            results: function() { return getData('results'); },
            groupResults: function() { return getData('group/results'); },
            tests: function() {return getData('tests'); },
            employee: function() { return getData('employee'); }
        }
    });
