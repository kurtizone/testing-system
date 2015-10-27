angular
    .module('adminModule')
    .factory('SubjectsService', function ($http) {
        return {
            getPage: function (pageNumber, itemsPerPage, search, sortCriteria, sortOrder) {
                return getDataWithParams('/admin/subjects/' + pageNumber + '/' + itemsPerPage + '/' + sortCriteria + '/' + sortOrder, search);
            },
            saveSubject: function (formData) {
                return $http.post("/admin/subjects/add", formData)
                    .then(function (result) {
                        return result.status;

                    });
            },
            getSubjectById: function (id) {
                var url = '/admin/subjects/get/' + id;
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            editSubject: function (formData, id) {
                var url = '/admin/subjects/edit/' + id;
                return $http.post(url, formData)
                    .then(function (result) {
                        return result.status;
                    });
            },
            deleteSubject: function (id) {
                var url = '/admin/subjects/delete/' + id;
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