angular
    .module('studentModule')
    .factory('GroupsService', function ($http) {
        return {
            getPage: function (pageNumber, itemsPerPage, search, sortCriteria, sortOrder) {
                return getDataWithParams('/student/groups/' + pageNumber + '/' + itemsPerPage + '/' + sortCriteria + '/' + sortOrder, search);
            },
            saveGroup: function (formData) {
                return $http.post("/student/groups/add", formData)
                    .then(function (result) {
                        return result.status;

                    });
            },
            getGroupById: function (id) {
                var url = '/student/groups/get/' + id;
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            editGroup: function (formData, id) {
                var url = '/student/groups/edit/' + id;
                return $http.post(url, formData)
                    .then(function (result) {
                        return result.status;
                    });
            },
            deleteGroup: function (id) {
                var url = '/student/groups/delete/' + id;
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