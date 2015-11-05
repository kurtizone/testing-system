angular
    .module('studentModule')
    .factory('SubjectsService', function ($http) {
        return {
            getPage: function (pageNumber, itemsPerPage, search, sortCriteria, sortOrder) {
                return getDataWithParams('/student/subjects/' + pageNumber + '/' + itemsPerPage + '/' + sortCriteria + '/' + sortOrder, search);
            },
            saveSubject: function (formData) {
                return $http.post("/student/subjects/add", formData)
                    .then(function (result) {
                        return result.status;

                    });
            },
            getSubjectById: function (id) {
                var url = '/student/subjects/get/' + id;
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            editSubject: function (formData, id) {
                var url = '/student/subjects/edit/' + id;
                return $http.post(url, formData)
                    .then(function (result) {
                        return result.status;
                    });
            },
            deleteSubject: function (id) {
                var url = '/student/subjects/delete/' + id;
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