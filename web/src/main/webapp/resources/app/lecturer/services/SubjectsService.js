angular
    .module('lecturerModule')
    .factory('SubjectsService', function ($http) {
        return {
            getPage: function (pageNumber, itemsPerPage, search, sortCriteria, sortOrder) {
                return getDataWithParams('/lecturer/subjects/' + pageNumber + '/' + itemsPerPage + '/' + sortCriteria + '/' + sortOrder, search);
            },
            saveSubject: function (formData) {
                return $http.post("/lecturer/subjects/add", formData)
                    .then(function (result) {
                        return result.status;

                    });
            },
            getSubjectById: function (id) {
                var url = '/lecturer/subjects/get/' + id;
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            editSubject: function (formData, id) {
                var url = '/lecturer/subjects/edit/' + id;
                return $http.post(url, formData)
                    .then(function (result) {
                        return result.status;
                    });
            },
            deleteSubject: function (id) {
                var url = '/lecturer/subjects/delete/' + id;
                return $http.delete(url)
                    .then(function (result) {
                        return result.status;
                    });
            },
            getGroupsBySubjectId: function (id) {
                var url = '/lecturer/subjects/get-list-groups/' + id;
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            getTestsBySubjectId: function (id) {
                var url = '/lecturer/subjects/get-list-tests/' + id;
                return $http.get(url).then(function (result) {
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