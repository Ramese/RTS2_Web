/*global angular, console */
/*global BootstrapDialog */

(function () {
	'use strict';

	var app = angular.module('app', [
        // ANGULARS MODULES:
        'ngRoute',
        'ngCookies',
        // 3TH PART MODULES:
        'ui.bootstrap',
        'mwl.calendar',
        'angularMoment',
        // OURS MODULES:
        'translateConfig',
        'services',
        'controllers'
	]);

	app.constant('CONSTANTS', (function () {
		// Define your variable
		var address = '127.0.0.1',
            port = 0,
            resource = 'http://' + address + ':' + port,
            webApiPrefix = '/rts2/API',
            webPrefix = '/rts2',
            webName = "rts2";

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
	}()));

	app.config(function ($routeProvider, $locationProvider, $httpProvider, CONSTANTS) {

		$httpProvider.interceptors.push(function ($q, $rootScope, $window, $translate, CONSTANTS) {

			var realEncodeURIComponent = window.encodeURIComponent;
			return {
				'request': function (config) {
					if ($rootScope.user !== undefined) {
						config.headers.Token = $rootScope.user.Token;
						config.headers.UserName = $rootScope.user.UserName;
					}
					return config;

				},
				'responseError': function (response) {
					var data = null;
                    //console.log("global catch", response.data);
					if (!(response === undefined || response === null)) {
						data = response.data;
					}

					if (!(data === null || data === undefined || data === "")) {
						BootstrapDialog.show({
							title: 'Error',
							closable: false,
							type: BootstrapDialog.TYPE_DANGER,
							message: data.isTranslatable ? $translate.instant('RESPONSE_' + data.message) : data.message,
							buttons: [{
								label: 'Close',
								cssClass: 'btn-danger',
								action: function (dialogRef) {
									if (response.status === 401) { // Unauthorized - User hasnt access
										$window.location.href = CONSTANTS.WEB_PREFIX + "/";
									} else if (response.status === 406) { // NotAcceptable - login expired or wrong token
										$window.location.href = CONSTANTS.WEB_PREFIX + '/logout';
									}
                                    //else if (response.status === 417) { // ExpectationFailed - ex
									//}
									dialogRef.close();
								}
							}]
						});

						return $q.reject(response);
					} else {
						return $q.reject(response);
					}
				}
			};
		});

		$routeProvider
			.when('/interface', {
				templateUrl: 'Views/interface.html',
				controller: 'InterfaceCtrl',
				caseInsenstivieMatch: true,
                freeAccess: true,
                title: "Interface | RTS2 Web"
			})
            .when('/', {
				templateUrl: 'Views/interface.html',
				controller: 'InterfaceCtrl',
				caseInsenstivieMatch: true,
                freeAccess: true,
                title: "Interface | RTS2 Web"
			})
            .when('/timetable', {
				templateUrl: 'Views/timeTable.html',
				controller: 'TimeTableCtrl',
				caseInsenstivieMatch: true,
                freeAccess: true,
                title: "TimeTable | RTS2 Web"
			})
            .when('/calendar', {
				templateUrl: 'Views/calendar.html',
				controller: 'CalendarCtrl',
				caseInsenstivieMatch: true,
                freeAccess: true,
                title: "Calendar | RTS2 Web"
			})
            .when('/registration', {
				templateUrl: 'Views/registration.html',
				controller: 'RegistrationCtrl',
				caseInsenstivieMatch: true,
                freeAccess: true,
                title: "Registration | RTS2 Web"
			})
            .when('/telescopes', {
				templateUrl: 'Views/telescopes.html',
				controller: 'TelescopesCtrl',
				caseInsenstivieMatch: true,
                freeAccess: false,
                title: "Telescopes list | RTS2 Web"
			})
			.when('/about', {
				templateUrl: 'Views/about.html',
				controller: 'AboutCtrl',
				caseInsensitiveMatch: true,
				freeAccess: true,
                title: "About | RTS2 Web"
			})
			.when('/login', {
				templateUrl: 'Views/login.html',
				controller: 'LoginCtrl',
				caseInsensitiveMatch: true,
				freeAccess: true,
				loginView: true,
                title: "Interface | RTS2 Web"
			})
			.when('/logout', {
				templateUrl: 'Views/logout.html',
				controller: 'LogoutCtrl',
				caseInsensitiveMatch: true,
                title: "Interface | RTS2 Web"
			})
			.otherwise({
				templateUrl:  'Views/404.html',
				controller: 'Error404Ctrl',
				freeAccess: true,
                title: "Interface | RTS2 Web"
			});

		$locationProvider.html5Mode(true);
	});

	app.run(function ($rootScope, $location, Auth) {
		$rootScope.$on("$routeChangeStart", function (event, next, current) {
			if (Auth.isLoggedIn()) {
				if (next.loginView) {
					$location.path("/");
				}
			} else {
				if (!next.freeAccess) {
					$location.path("/login");
				}
			}
			
		});
        $rootScope.$on("$routeChangeSuccess", function (event, currentRoute, previousRoute) {
            $rootScope.title = currentRoute.title;
        });
	});

}());