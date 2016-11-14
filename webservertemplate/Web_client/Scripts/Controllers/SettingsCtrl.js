/*global angular*/

/*
README:

For dependencies declaration use file controllers.js, where is inicialized module name "controllers".

If you insert dependencies here, you override all modeles with name "controllers" (YOU DONT WANT!).
*/

(function () {
	'use strict';

	var app = angular.module('controllers');

	app.controller('SettingsCtrl', ['$scope', '$window', '$translate', 'CONSTANTS', function ($scope, $window, $translate, CONSTANTS) {

		$scope.$root.title = $translate.instant('TITLE_SETTINGS') + " | " + CONSTANTS.WEB_NAME;

		
	}]);

})();