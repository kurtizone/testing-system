angular
    .module('studentModule')
    .factory('SubjectsService', function ($http) {
        return {
            getPage: function (pageNumber, itemsPerPage, search, sortCriteria, sortOrder) {
                return getDataWithParams('/student/subjects/' + pageNumber + '/' + itemsPerPage + '/' + sortCriteria + '/' + sortOrder, search);
            },
            getLecturersBySubject: function (id) {
                var url = '/student/subjects/get/lecturers/' + id;
                return $http.get(url).then(function (result) {
                    return result.data;
                });
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