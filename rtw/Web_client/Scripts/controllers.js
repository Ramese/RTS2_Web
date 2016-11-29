/*global angular*/

/*
README:

Here is inicialized module "controllers", you can specify dependecies.

*/

(function () {
	'use strict';

	angular.module('controllers', [])

		// Path: /about
		.controller('AboutCtrl', ['$scope', '$location', '$window', '$translate', 'AboutService', 'CONSTANTS', function ($scope, $location, $window, $translate, AboutService, CONSTANTS) {

			$scope.$root.title = $translate.instant('TITLE_ABOUT') + " | " + CONSTANTS.WEB_NAME;

			AboutService.getVersion().success(function (data) {
				$scope.version = data.data;
			}).error(function () {
				$scope.version = "-1";
			});

			console.log('About user: ', $scope.user);
		}])

		// Path: /error/404

		.controller('Error404Ctrl', ['$scope', '$location', '$window', '$translate', 'CONSTANTS', function ($scope, $location, $window, $translate, CONSTANTS) {

			$scope.$root.title = $translate.instant('TITLE_NOT_FOUND') + " | " + CONSTANTS.WEB_NAME;
		}]);
})();