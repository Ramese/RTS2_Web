/*global angular, console*/

/*
README:

For dependencies declaration use file controllers.js, where is inicialized module name "controllers".

If you insert dependencies here, you override all modeles with name "controllers" (YOU DONT WANT!).
*/

(function () {
	'use strict';

	var app = angular.module('controllers');

	app.controller('TelescopeCtrl', function ($scope, TelescopeService, $routeParams) {
        
        $scope.save = function () {
            TelescopeService.saveTelescope($scope.telescope)
                .success(function (data) {
                    $scope.telescope = data;
                })
                .error(function (data) {
                    console.log("telescope save error", data);
                });
        };
        
        function loadData() {
            TelescopeService.getTelescope($routeParams.id)
                .success(function (data) {
                    $scope.telescope = data;
                })
                .error(function (data) {
                
                });
        }
        
        function init() {
            $scope.telescope = {};
            
            if ($routeParams.id === "new") {
                console.log("new");
            } else {
                loadData();
            }
        }
        
        init();
        
	});

}());