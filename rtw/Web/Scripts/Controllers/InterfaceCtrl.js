/*global angular,console*/

/*
README:

For dependencies declaration use file controllers.js, where is inicialized module name "controllers".

If you insert dependencies here, you override all modeles with name "controllers" (YOU DONT WANT!).
*/

(function () {
	'use strict';

	var app = angular.module('controllers');

	app.controller('InterfaceCtrl', function ($scope, $routeParams) {
        console.log("id", $routeParams.id);
        
        
		
	});

}());