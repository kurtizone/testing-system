angular
    .module('lecturerModule')
    .factory('StudentsService', function ($http) {
        return {
            getPage: function (pageNumber, itemsPerPage, search, sortCriteria, sortOrder) {
                return getDataWithParams('/lecturer/students/' + pageNumber + '/' + itemsPerPage + '/' + sortCriteria + '/' + sortOrder, search);
            },
            saveStudent: function (formData) {
                return $http.post("/lecturer/students/add", formData)
                    .then(function (result) {
                        return result.status;

                    });
            },
            getStudentById: function (id) {
                var url = '/lecturer/students/get/' + id;
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            editStudent: function (formData, id) {
                var url = '/lecturer/students/edit/' + id;
                return $http.post(url, formData)
                    .then(function (result) {
                        return result.status;
                    });
            },
            deleteStudent: function (id) {
                var url = '/lecturer/students/delete/' + id;
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