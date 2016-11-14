/*global angular, routingConfig*/

(function () {
	'use strict';

	var app = angular.module('app', [	// ANGULARS MODULES:
										'ngRoute',
										'ngCookies',
										// 3TH PART MODULES:
										//ui-boostrap etc.
										// OURS MODULES:
										'translateConfig',
										'services',
										'controllers'
	]);

	app.constant('CONSTANTS', (function () {
		// Define your variable
		var address = '127.0.0.1';
		var port = 50238;
		var resource = 'http://' + address + ':' + port + '';
		var webApiPrefix = '/WebApi';
		var webPrefix = '/web';
		var webName = "TempWeb";

		// Use the variable in your constants
		return {
			USERS_DOMAIN: resource,
			API_ADDRESS: resource + webApiPrefix,
			API_PREFIX: webApiPrefix,
			WEB_ADDRESS: resource + webPrefix,
			WEB_PREFIX: webPrefix,
			WEB_NAME: webName,
			COOKIES_NAME: webName + "UserInfo"
		};
	})());

	app.config(['$routeProvider', '$locationProvider', '$httpProvider', 'CONSTANTS', function ($routeProvider, $locationProvider, $httpProvider, CONSTANTS) {

		$httpProvider.interceptors.push(['$q', '$log', '$rootScope', '$window', '$translate', function ($q, $log, $rootScope, $window, $translate) {

			var realEncodeURIComponent = window.encodeURIComponent;
			return {
				'request': function (config) {
					config.headers.token = $rootScope.user.token;
					//config.headers["tokenDate"] = $rootScope.user.tokenDate;

					return config;

				},
				'responseError': function (response) {
					if (!(response === undefined || response === null)) {
						var data = response.data;
					}

					if (!(data === null || data === undefined || data === "")) {
						BootstrapDialog.show({
							title: 'Chyba',
							closable: false,
							type: BootstrapDialog.TYPE_DANGER,
							message: $translate.instant('RESPONSE_' + data),
							buttons: [{
								label: 'Zavřít',
								cssClass: 'btn-danger',
								action: function (dialogRef) {
									if (response.status === 401) { // Unauthorized - User hasnt access
										$window.location.href = CONSTANTS.WEB_PREFIX + '/';
										dialogRef.close();
										return $q.reject(response);
									} else if (response.status === 406) { // NotAcceptable - login expired or wrong token
										$window.location.href = CONSTANTS.WEB_PREFIX + '/logout';
										dialogRef.close();
										return $q.reject(response);
									} else if (response.status === 417) { // ExpectationFailed - ex
										dialogRef.close();
										return $q.reject(response);
									}

									return $q.reject(response);
								}
							}]
						});
					} else {
						return $q.reject(response);
					}
				}
			};
		}]);

		$routeProvider
			.when('/example', {
				templateUrl: 'Views/exampleform.html',
				controller: 'ExampleCtrl',
				caseInsensitiveMatch: true
			})
			.when('/about', {
				templateUrl: 'Views/about.html',
				controller: 'AboutCtrl',
				caseInsensitiveMatch: true
			})
			.when('/login', {
				templateUrl: 'Views/login.html',
				controller: 'LoginCtrl',
				caseInsensitiveMatch: true
			})
			.when('/logout', {
				templateUrl: 'Views/logout.html',
				controller: 'LogoutCtrl',
				caseInsensitiveMatch: true
			})
			.otherwise({
				templateUrl: 'Views/404.html',
				controller: 'Error404Ctrl'
			});

		$locationProvider.html5Mode(true);
	}]);

		
})();