angular.module('welcomeModule')
    .factory('DataReceivingService',
    [
        '$http',
        '$log',
        function ($http, $log) {

        return {
            isUsernameAvailable: function (username) {
                var url = '/application/users/available/username/' + username;
                return $http.get(url)
                    .then(function(result) {
                        console.log(result);
                        return result.data;
                    });
            },
            getAllGroups: function () {
                return getData('groups');
            },
            saveStudent: function (formData) {
                return $http.post("/application/users/student/add", formData)
                    .then(function (result) {
                        return result.status;

                    });
            },
            saveLecturer: function (formData) {
                return $http.post("/application/users/lecturer/add", formData)
                    .then(function (result) {
                        return result.status;
                    });
            }
        };

        function getData(url) {
            return $http.get('application/' + url).success(function (data) {
                return data;
            }).error(function (err) {
                return err;
            });
        }
    }]);
