angular
    .module('welcomeModule')
    .controller('LoginController', ['$scope', '$http', '$modal', function ($scope, $http, $modal) {
        $scope.login = function () {
        	
        	$scope.incorrectStyle = false;
        	$scope.incorrectStylePlaceholder = false;
        	$scope.loginCorrect = false;
        	
            var loginData = 'username=' + $scope.loginForm.username
                + '&password=' + $scope.loginForm.password;

            var request = {
                method: 'POST',
                url: '/authenticate',
                data: loginData,
                headers: {
                    'Content-Type': "application/x-www-form-urlencoded",
                    'X-Login-Ajax-call': 'true'
                }
            };

            var response = $http(request);
            console.log(response);
            response.success(function (data) {
                var path = redirectByRole(data);
                $scope.loginForm.password = null;
            
                if (path)
                    window.location.replace(path);
                if (path == undefined){
                	$scope.loginCorrect = true;
                	$scope.incorrectStyle = true;
                	$scope.incorrectStylePlaceholder = true;
                }
            });
            response.error(function (data) {
                console.dir(data);
            });
            


                function redirectByRole(role) {
            	var path = undefined;
                if (role == "ADMIN")
                    path = '/admin';
                else if (role == 'STUDENT')
                    path = '/provider/admin/';
                else if (role == 'STUDENT')
                    path = '/verificator/admin/';
                  return path;
            }
       };

    }]);