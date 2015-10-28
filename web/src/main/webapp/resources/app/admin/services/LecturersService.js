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
            deleteLecturer: function (id) {
                var url = '/admin/lecturers/delete/' + id;
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