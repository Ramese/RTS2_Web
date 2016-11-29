/*global angular,routingConfig */

/*
README:

For dependencies declaration use file services.js, where is inicialized module name "services".

If you insert dependencies here, you override all modeles with name "services".
*/

(function () {
	'use strict';

	var app = angular.module('services');

	app.factory('Auth', function ($http, $cookies, $rootScope, CONSTANTS) {

		var cookiesName = CONSTANTS.COOKIES_NAME;

		var accessLevels = routingConfig.accessLevels
			, userRoles = routingConfig.userRoles
			, currentUser = $cookies.getObject(cookiesName) || { username: '', role: userRoles.public };

		$rootScope.user = currentUser;

		function changeUser(user) {
			$cookies.putObject(cookiesName, user);
			currentUser = $cookies.getObject(cookiesName);
			$rootScope.user = user;
		}

		return {
			authorize: function (accessLevel, role) {
				if (role === undefined) {
					role = currentUser.role;
				}

				if (accessLevel === undefined) {
					accessLevel = accessLevels.admin;
				}

				/*jslint bitwise: true*/
				var autorized = accessLevel.bitMask & role.bitMask;
				/*jslint bitwise: false*/
				return autorized;
			},
			isLoggedIn: function (user) {
				if (user === undefined) {
					user = currentUser;
				}

				return user.role.bitMask > 1;
			},
			login: function (user, success, error) {
				$http.post(CONSTANTS.API_PREFIX + '/login', user).success(function (user) {
					user.lang = currentUser.lang;
					changeUser(user);
					success(user);
				}).error(error);
			},
			logout: function (success, error) {
				$http.post(CONSTANTS.API_PREFIX + '/logout').success(function () {
					changeUser({
						username: '',
						role: userRoles.public
					});

					$cookies.remove(cookiesName);
					success();
				}).error(error);
			},
			getLanguage: function () {
				return currentUser.lang;
			},
			setLanguage: function (lang) {
				currentUser.lang = lang;
				changeUser(currentUser);
			},
			accessLevels: accessLevels,
			userRoles: userRoles,
			user: currentUser
		};
	});

})();