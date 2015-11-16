angular
    .module('studentModule')
    .factory('TestsService', function ($http) {
        return {
            getPage: function (pageNumber, itemsPerPage, search, sortCriteria, sortOrder) {
                return getDataWithParams('/student/tests/' + pageNumber + '/' + itemsPerPage + '/' + sortCriteria + '/' + sortOrder, search);
            },
            getTestById: function (id) {
                var url = '/student/tests/get/' + id;
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            getTestIdWithQuestions: function (id) {
                var url = '/student/tests/get/questions/' + id;
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            editTest: function (formData, id) {
                var url = '/student/tests/edit/' + id;
                return $http.post(url, formData)
                    .then(function (result) {
                        return result.status;
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