/*global angular, console*/

/*
README:

For dependencies declaration use file controllers.js, where is inicialized module name "controllers".

If you insert dependencies here, you override all modeles with name "controllers" (YOU DONT WANT!).
*/

(function () {
	'use strict';

	var app = angular.module('controllers');

	app.controller('TelescopesCtrl', function ($scope, TelescopeService) {
        
        function LoadData() {
            TelescopeService.getTelescopes()
                .success(function (data) {
                    console.log("tels ctrl: ", data);
                    $scope.telescopes = data;
                })
                .error(function (data) {
                
                });
        }
        
        LoadData();
        
	});

}());