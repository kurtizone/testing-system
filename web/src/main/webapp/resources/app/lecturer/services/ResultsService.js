angular
    .module('lecturerModule')
    .factory('ResultsService', function ($http) {
        return {
            getPage: function (pageNumber, itemsPerPage, search, sortCriteria, sortOrder) {
                return getDataWithParams('/lecturer/results/' + pageNumber + '/' + itemsPerPage + '/' + sortCriteria + '/' + sortOrder, search);
            }
        };

        function getDataWithParams(url, params) {
            return $http.get(url, {
                params: params
            }).success(function (data) {
                return data;
            }).error(function (err) {
                return err;
            });
        }
    });