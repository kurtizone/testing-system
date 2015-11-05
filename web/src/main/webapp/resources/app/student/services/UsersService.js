angular
    .module('studentModule')
    .factory('UsersService', function ($http) {
        return {
            getPage: function (currentPage, itemsPerPage, searchObj, sortCriteria, sortOrder) {
                return getData('student/users/' + currentPage + '/' + itemsPerPage + '/'
                   + sortCriteria +  '/' + sortOrder, searchObj);
            },
            getSysAdminsPage : function (currentPage, itemsPerPage, searchObj, sortCriteria, sortOrder) {

                return getData('student/sysstudents/' + currentPage + '/' + itemsPerPage + '/'
                    + sortCriteria +  '/' + sortOrder, searchObj);
            },
            getSysAdminByUsername: function (username) {
                var url = '/student/sysstudents/get_sys_student/' + username;
                return $http.get(url).then(function (result) {
                    return result;
                });
            },

            editSysAdmin: function (formData, username) {
                var url = '/student/sysstudents/edit/' + username;
                return $http.post(url, formData)
                    .then(function (result) {
                        return result.status;
                    });
            },

            deleteSysAdmin: function (username) {
                var url = '/student/sysstudents/delete/' + username;
                return $http.delete(url)
                    .then(function (result) {
                        return result.status;
                    });
            }
        }

        function getData(url, params) {
            return $http.get(url, {
                params: params
            }).success(function (data) {
                return data;
            }).error(function (err) {
                return err;
            });
        }

        function getDataWithParam(url, params) {
            return $http.get(url, {
                params : params
            }).success(function (data) {
                return data;
            }).error(function (err) {
                return err;
            });
        }


    });