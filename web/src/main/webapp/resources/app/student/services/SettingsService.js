angular
    .module('studentModule')
    .factory('SettingsService', function ($http) {
        return {
            changePassword: function (oldPass, newPass) {
                var url = '/student/settings/password';
                var passwords = {
                    oldPassword: oldPass,
                    newPassword: newPass
                };
                return $http.put(url, passwords)
                    .then(function (result) {
                        return result.status;
                    });
            }
        }
    });