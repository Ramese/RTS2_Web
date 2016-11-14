/*global angular,routingConfig */

/*
README:

Here is inicialized module "services", you can specify dependecies.

*/

(function () {
	'use strict';

	var app = angular.module('services', []);

	app.service('ExampleService', ['$http', 'CONSTANTS', function ($http, CONSTANTS) {

		this.getExample = function (pagingInfo) {

			return $http({
				method: "POST",
				url: CONSTANTS.API_PREFIX + "/Example",
				headers: { 'Content-Type': 'application/json' },
				data: pagingInfo
			}).success(function (data) {
				// dont use this success
			}).error(function (data) {
				console.log("ExampleService.getExample() error");
			});
		};
	}]);

	app.service('AboutService', ['$http', 'CONSTANTS', function ($http, CONSTANTS) {

		this.getVersion = function () {
			return $http({
				method: "GET",
				url: CONSTANTS.API_PREFIX + "/GetVersion",
				headers: { 'Content-Type': 'application/json' }
			}).success(function (data) {
				// dont use this success
			}).error(function (data) {
				console.log("AboutService.getVersion() error");
			});
		};
	}]);

})();