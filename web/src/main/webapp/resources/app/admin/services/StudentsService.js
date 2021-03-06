angular
    .module('adminModule')
    .factory('StudentsService', function ($http) {
        return {
            getPage: function (pageNumber, itemsPerPage, search, sortCriteria, sortOrder) {
                return getDataWithParams('/admin/students/' + pageNumber + '/' + itemsPerPage + '/' + sortCriteria + '/' + sortOrder, search);
            },
            saveStudent: function (formData) {
                return $http.post("/admin/students/add", formData)
                    .then(function (result) {
                        return result.status;

                    });
            },
            getStudentById: function (id) {
                var url = '/admin/students/get/' + id;
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            getAllGroups: function () {
                var url = '/admin/students/get/groups';
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            getSubjectsByStudentId: function (id) {
                var url = '/admin/students/get/' + id + '/subjects';
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            editStudent: function (formData, id) {
                var url = '/admin/students/edit/' + id;
                return $http.post(url, formData)
                    .then(function (result) {
                        return result.status;
                    });
            },
            deleteStudent: function (id) {
                var url = '/admin/students/delete/' + id;
                return $http.delete(url)
                    .then(function (result) {
                        return result.status;
                    });
            },
            isUsernameAvailable: function (username) {
                var url = '/application/users/available/username/' + username;
                return $http.get(url)
                    .then(function(result) {
                        console.log(result);
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