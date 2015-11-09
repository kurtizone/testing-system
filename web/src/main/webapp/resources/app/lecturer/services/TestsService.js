angular
    .module('lecturerModule')
    .factory('TestsService', function ($http) {
        return {
            getPage: function (pageNumber, itemsPerPage, search, sortCriteria, sortOrder) {
                return getDataWithParams('/lecturer/tests/' + pageNumber + '/' + itemsPerPage + '/' + sortCriteria + '/' + sortOrder, search);
            },
            saveTest: function (formData) {
                return $http.post("/lecturer/tests/add", formData)
                    .then(function (result) {
                        return result.status;

                    });
            },
            getTestById: function (id) {
                var url = '/lecturer/tests/get/' + id;
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            editTest: function (formData, id) {
                var url = '/lecturer/tests/edit/' + id;
                return $http.post(url, formData)
                    .then(function (result) {
                        return result.status;
                    });
            },
            deleteTest: function (id) {
                var url = '/lecturer/tests/delete/' + id;
                return $http.delete(url)
                    .then(function (result) {
                        return result.status;
                    });
            },
            findSubjects: function (id) {
                return $http.get('/lecturer/tests/get/subjects')
                    .success(function (data) {
                        return data;
                    }).error(function(err) {
                        return err;
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