angular
    .module('adminModule')
    .factory('LecturersService', function ($http) {
        return {
            getPage: function (pageNumber, itemsPerPage, search, sortCriteria, sortOrder) {
                return getDataWithParams('/admin/lecturers/' + pageNumber + '/' + itemsPerPage + '/' + sortCriteria + '/' + sortOrder, search);
            },
            saveLecturer: function (formData) {
                return $http.post("/admin/lecturers/add", formData)
                    .then(function (result) {
                        return result.status;

                    });
            },
            getLecturerById: function (id) {
                var url = '/admin/lecturers/get/' + id;
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            editLecturer: function (formData, id) {
                var url = '/admin/lecturers/edit/' + id;
                return $http.post(url, formData)
                    .then(function (result) {
                        return result.status;
                    });
            },
            getListOfSubjects: function (id) {
                var url = '/admin/lecturers/get/' + id + '/subjects';
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            getAllAvaibleSubjects: function (id) {
                var url = '/admin/lecturers/get/subjects/' + id;
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            addSubjectToLecturer: function (formData, id) {
                var url = '/admin/lecturers/add/subject/' + id;
                return $http.post(url, formData)
                    .then(function (result) {
                        return result.status;
                    });
            },
            deleteLecturer: function (id) {
                var url = '/admin/lecturers/delete/' + id;
                return $http.delete(url)
                    .then(function (result) {
                        return result.status;
                    });
            },
            deleteSubjectOfLecturer: function (lecturerId, subjectId) {
                var url = '/admin/lecturers/delete/subject/' + lecturerId + '/' + subjectId;
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