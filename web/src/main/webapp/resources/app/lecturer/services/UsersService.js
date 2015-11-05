angular
    .module('lecturerModule')
    .factory('UsersService', function ($http) {
        return {
            getPage: function (currentPage, itemsPerPage, searchObj, sortCriteria, sortOrder) {
                return getData('lecturer/users/' + currentPage + '/' + itemsPerPage + '/'
                   + sortCriteria +  '/' + sortOrder, searchObj);
            },
            getSysAdminsPage : function (currentPage, itemsPerPage, searchObj, sortCriteria, sortOrder) {

                return getData('lecturer/syslecturers/' + currentPage + '/' + itemsPerPage + '/'
                    + sortCriteria +  '/' + sortOrder, searchObj);
            },
            getSysAdminByUsername: function (username) {
                var url = '/lecturer/syslecturers/get_sys_lecturer/' + username;
                return $http.get(url).then(function (result) {
                    return result;
                });
            },

            editSysAdmin: function (formData, username) {
                var url = '/lecturer/syslecturers/edit/' + username;
                return $http.post(url, formData)
                    .then(function (result) {
                        return result.status;
                    });
            },

            deleteSysAdmin: function (username) {
                var url = '/lecturer/syslecturers/delete/' + username;
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