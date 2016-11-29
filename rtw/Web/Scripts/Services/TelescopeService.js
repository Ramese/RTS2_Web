/*global angular, console*/
(function () {
	'use strict';

	var app = angular.module('services');

	app.service('TelescopeService', function ($http, CONSTANTS) {

		this.getTelescopes = function (pagination) {
			return $http({
				method: "POST",
				url: CONSTANTS.API_PREFIX + "/telescopes",
				headers: { 'Content-Type': 'application/json' },
                data: pagination
			}).error(function (data) {
				console.log(data);
				console.log("TelescopeService error");
			});
		};
        
	});

}());