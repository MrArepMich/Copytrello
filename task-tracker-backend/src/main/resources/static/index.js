angular.module('copytrello', ['ngStorage'])
    .controller('taskController', function ($scope, $http, $localStorage) {

        $scope.tryToLogout = function () {
            $scope.clearUser();
            window.location.href = 'login.html';
        };

        $scope.clearUser = function () {
            delete $localStorage.copytrelloUser;
            $http.defaults.headers.common.Authorization = '';
        };

        $scope.isUserLoggedIn = function () {
            return !!$localStorage.copytrelloUser;
        };

        if ($localStorage.copytrelloUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.copytrelloUser.token;
        }

        $scope.tasks = [];
        $scope.searchResults = [];
        $scope.filterStatus = '';

        $scope.createTask = function() {
            $http.post('http://localhost:8300/copytrello/api/v1/tasks', $scope.newTask).then(function(response) {
                $scope.newTask = {};
                $scope.getAllTasks();
            }).catch(function(error) {
                alert(error.data.value);
            });
        };

        $scope.getAllTasks = function() {
            $http.get('http://localhost:8300/copytrello/api/v1/tasks').then(function(response) {
                $scope.tasks = response.data;
            }).catch(function(error) {
                alert(error.data.value);
            });
        };

        $scope.deleteAllTasks = function() {
            $http.delete('http://localhost:8300/copytrello/api/v1/tasks').then(function(response) {
                $scope.tasks = [];
            }).catch(function(error) {
                alert(error.data.value);
            });
        };

        $scope.editTask = function(task) {
            $scope.currentEditTask = angular.copy(task);
            $scope.oldTask = angular.copy(task)
            console.log('Editing task:', $scope.currentEditTask);
            $('#editTaskModal').modal('show');
        };

        $scope.updateTask = function() {
            $http.put('http://localhost:8300/copytrello/api/v1/tasks/title/' + $scope.oldTask.title, $scope.currentEditTask)
                .then(function(response) {
                    $scope.getAllTasks();
                    $('#editTaskModal').modal('hide');
                    $scope.currentEditTask = {};
                })
                .catch(function(error) {
                    alert(error.data.value);
                });
        };

        $scope.deleteTask = function(id) {
            $http.delete('http://localhost:8300/copytrello/api/v1/tasks/' + id).then(function(response) {
                $scope.tasks = $scope.tasks.filter(task => task.id !== id);
            }).catch(function(error) {
                alert(error.data.value);
            });
        };

        $scope.searchTask = function() {
            if ($scope.searchType === 'title') {
                $scope.getTaskByTitle($scope.searchQuery);
            } else if ($scope.searchType === 'id') {
                $scope.getTaskById($scope.searchQuery);
            }
        };

        $scope.getTaskByTitle = function(title) {
            console.log('Searching task by title:', title);
            $http.get('http://localhost:8300/copytrello/api/v1/tasks/title/' + title).then(function(response) {
                console.log('Response data:', response.data);
                $scope.tasks = [response.data];
            }).catch(function(error) {
                alert(error.data.value);
            });
        };

        $scope.getTaskById = function(id) {
            $http.get('http://localhost:8300/copytrello/api/v1/tasks/' + id).then(function(response) {
                $scope.tasks = [response.data];
            }).catch(function(error) {
                alert(error.data.value);
            });
        };

        $scope.deleteTaskByTitle = function(title) {
            $http.delete('http://localhost:8300/copytrello/api/v1/tasks/title/' + title).then(function(response) {
                $scope.tasks = $scope.tasks.filter(task => task.title !== title);
            }).catch(function(error) {
                alert(error.data.value);
            });
        };

        $scope.patchTask = function(taskId, updates) {
            $http.patch('http://localhost:8300/copytrello/api/v1/tasks/' + taskId, updates)
                .then(function(response) {
                    $scope.getAllTasks();
                })
                .catch(function(error) {
                    alert(error.data.value);
                });
        };

        $scope.getCompletedTasks = function() {
            $http.get('http://localhost:8300/copytrello/api/v1/tasks/completed').then(function(response) {
                $scope.tasks = response.data;
            }).catch(function(error) {
                alert(error.data.value);
            });
        };

        $scope.getInProgressTasks = function() {
            $http.get('http://localhost:8300/copytrello/api/v1/tasks/in-progress').then(function(response) {
                $scope.tasks = response.data;
            }).catch(function(error) {
                alert(error.data.value);
            });
        };

        $scope.filterTasks = function() {
            if ($scope.filterStatus === 'In_Progress') {
                $scope.getInProgressTasks();
            } else if ($scope.filterStatus === 'Completed') {
                $scope.getCompletedTasks();
            } else {
                $scope.getAllTasks();
            }
        };

        $('#editTaskModal').on('hidden.bs.modal', function () {
            $scope.currentEditTask = {};
            $scope.$apply();
        });
    });
