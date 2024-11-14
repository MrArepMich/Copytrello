angular.module('copytrello', ['ngStorage'])
    .controller('registerController', function ($scope, $http, $localStorage) {

        $scope.user = {
            email: '',
            password: '',
            confirmPassword: '',
        };

        $scope.registerMe = function () {
            if ($scope.user.password !== $scope.user.confirmPassword) {
                alert("Passwords do not match!");
                return;
            }

            if ($scope.user.email && $scope.user.password && $scope.user.confirmPassword) {
                $http.post('http://localhost:8300/copytrello/api/v1/auth/register', $scope.user)
                    .then(function (response) {
                        $('#login-tab').tab('show');
                        alert("Registration successful! Please log in.");
                    }, function errorCallback(response) {
                        alert(response.data.value);
                    });
            }
        }

        $scope.loginMe = function () {
            $http.post('http://localhost:8300/copytrello/api/v1/auth/authenticate', $scope.user)
                .then(function successCallback(response) {
                    if (response.data.token) {
                        $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                        $localStorage.copytrelloUser = {username: $scope.user.email, token: response.data.token};
                        $scope.userRoles = response.data.roles;
                        alert($scope.user.email + ' is authorized');
                        $scope.user.email = null;
                        $scope.user.password = null;
                        window.location.href = 'index.html';
                    }
                }, function errorCallback(error) {
                    if (error.data && error.data.value) {
                        alert(error.data.value);
                    } else {
                        alert("Unexpected error occurred");
                    }
                });
        };
    })
