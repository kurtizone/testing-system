angular
    .module('adminModule')
    .factory('GroupsService', function ($http) {
        return {
            getPage: function (pageNumber, itemsPerPage, search, sortCriteria, sortOrder) {
                return getDataWithParams('/admin/groups/' + pageNumber + '/' + itemsPerPage + '/' + sortCriteria + '/' + sortOrder, search);
            },
            saveGroup: function (formData) {
                return $http.post("/admin/groups/add", formData)
                    .then(function (result) {
                        return result.status;

                    });
            },
            getGroupById: function (id) {
                var url = '/admin/groups/get/' + id;
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            editGroup: function (formData, id) {
                var url = '/admin/groups/edit/' + id;
                return $http.post(url, formData)
                    .then(function (result) {
                        return result.status;
                    });
            },
            getSubjectsByGroupId: function (id) {
                var url = '/admin/groups/get/' + id + '/subjects';
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            getStudentsByGroupId: function (id) {
                var url = '/admin/groups/get/' + id + '/students';
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            getAllAvaibleSubjects: function (id) {
                var url = '/admin/groups/get/subjects/' + id;
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            addSubjectToGroup: function (formData, id) {
                var url = '/admin/groups/add/subject/' + id;
                return $http.post(url, formData)
                    .then(function (result) {
                        return result.status;
                    });
            },
            deleteGroup: function (id) {
                var url = '/admin/groups/delete/' + id;
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