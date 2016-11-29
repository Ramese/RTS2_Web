/*global angular*/

/*
README:

For dependencies declaration use file controllers.js, where is inicialized module name "controllers".

If you insert dependencies here, you override all modeles with name "controllers" (YOU DONT WANT!).
*/

(function () {
	'use strict';

	var app = angular.module('controllers');

	app.controller('NavCtrl', ['$scope', 'Auth', '$window', '$translate', function ($scope, Auth, $window, $translate) {

		$scope.user = Auth.user;
		$scope.userRoles = Auth.userRoles;
		$scope.accessLevels = Auth.accessLevels;

		$scope.langIcon = 'Content/img/czech_flag_icon.png';

		$scope.languages = {
			'en': 'Content/img/english_flag_icon.png',
			'de': 'Content/img/germany_flag_icon.png',
			'cs': 'Content/img/czech_flag_icon.png'
		};

		console.log("user", $scope.user);

		$scope.setLanguage = function (lang) {
			Auth.setLanguage(lang);
			$scope.setCurrentLang();
		};

		$scope.setCurrentLang = function () {
			if (Auth.getLanguage() === 'en' || Auth.getLanguage() === 'de' || Auth.getLanguage() === 'cs') {
				$translate.use(Auth.getLanguage());
				$scope.langIcon = $scope.languages[Auth.getLanguage()];
			} else {
				$translate.use('cs');
				$scope.setLanguage('cs');
				$scope.langIcon = $scope.languages['cs'];
			}
		};

		$scope.setCurrentLang();

		

		$scope.changeLanguage = function () {
			if ($translate.use() === 'cs') {
				$translate.use('en');
			}
			else if ($translate.use() === 'en') {
				$translate.use('cs');
			}

		};
	}]);
})();