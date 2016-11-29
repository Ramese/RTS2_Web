/*global angular*/

/*
README:

For dependencies declaration use file controllers.js, where is inicialized module name "controllers".

If you insert dependencies here, you override all modeles with name "controllers" (YOU DONT WANT!).
*/

(function () {
	'use strict';

	var app = angular.module('controllers');

	app.controller('LoginCtrl', ['$scope', '$window', 'Auth', '$translate', 'CONSTANTS', function ($scope, $window, Auth, $translate, CONSTANTS) {

		$scope.$root.title = $translate.instant('TITLE_LOGIN') + " | " + CONSTANTS.WEB_NAME;

		if ($scope.user.username !== "") {
			$window.location.href = 'about';
		}

		$scope.rememberme = true;

		$scope.login = function () {
			Auth.login({
				username: $scope.username,
				password: $scope.password,
				rememberme: $scope.rememberme
			},
				function (res) {
					$window.location.href = 'about';

					$scope.loginError = undefined;
				},
				function (err) {
					$scope.loginError = err.Message;
					//$scope.username = "";
					$scope.password = "";
				});
		};
	}]);

})();