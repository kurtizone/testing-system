angular
    .module('lecturerModule')
    .factory('FillTestsService', function ($http) {
        return {
            getPage: function (pageNumber, itemsPerPage, search, sortCriteria, sortOrder) {
                return getDataWithParams('/lecturer/fill-tests/' + pageNumber + '/' + itemsPerPage + '/' + sortCriteria + '/' + sortOrder, search);
            },
            saveQuestion: function (formData) {
                return $http.post("/lecturer/fill-tests/add/question", formData)
                    .then(function (result) {
                        return result.status;

                    });
            },
            getTestById: function (id) {
                var url = '/lecturer/fill-tests/get/' + id;
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            getQuestionById: function (id) {
                var url = '/lecturer/fill-tests/get/question/' + id;
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            editQuestion: function (formData, id) {
                var url = '/lecturer/fill-tests/edit/question/' + id;
                return $http.post(url, formData)
                    .then(function (result) {
                        return result.status;
                    });
            },
            deleteQuestion: function (id) {
                var url = '/lecturer/fill-tests/delete/question/' + id;
                return $http.delete(url)
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