(function() {
	'use strict';
	window.AppLocalSession = {
		currentCart : null
	};
	// Angularjs
	var plugins = angular.module('commons.plugins', [])
	var app = angular.module('commons.all', [ "commons.directives", "commons.filters", "commons.services", "commons.ui", "commons.validators",
			"commons.controllers", "commons.plugins", "ngResource", "ngRoute" ]);
	Wick.DEPENDENCIES.push("commons.all")
	app.controller('AdminController', [ '$scope', '$location', '$route', function($scope, $location, $route) {
		$scope.goTo = function(path) {
			$location.path(path)
		}
		$scope.goToRefresh = function(path) {
			$location.path(path)
			$route.reload();
		}
	} ]);
	app.run(function($rootScope, $route) {
		AppLocalSession.urlStack = [];
		$rootScope.$on('$routeChangeSuccess', function(event, current) {
			console.log("[RouteChangeSuccess]current and previous....", $route, current)
			current.$$route && AppLocalSession.urlStack.push(current.$$route.originalPath);
			if (AppLocalSession.urlStack.length > 1) {
				AppLocalSession.lastUrl = AppLocalSession.urlStack[AppLocalSession.urlStack.length - 2]
			} else {
				AppLocalSession.lastUrl = null;
			}
		})
	});

	// DATE MS
	var regexIso8601 = /^(\d{4}|\+\d{6})(?:-(\d{2})(?:-(\d{2})(?:T(\d{2}):(\d{2}):(\d{2})\.(\d{1,})(Z|([\-+])(\d{2}):(\d{2}))?)?)?)?$/;
	function convertDateStringsToDates(input) {
		// Ignore things that aren't objects.
		if (typeof input !== "object")
			return input;

		for ( var key in input) {
			if (!input.hasOwnProperty(key))
				continue;

			var value = input[key];
			var match;
			// Check for string properties which look like dates.
			if (typeof value === "string" && (match = value.match(regexIso8601))) {
				var milliseconds = Date.parse(match[0])
				if (!isNaN(milliseconds)) {
					input[key] = new Date(milliseconds);
				}
			} else if (typeof value === "object") {
				// Recurse into object
				convertDateStringsToDates(value);
			}
		}
	}
	app.config([ "$httpProvider", function($httpProvider) {
		$httpProvider.defaults.transformResponse.push(function(responseData) {
			convertDateStringsToDates(responseData);
			return responseData;
		});
	} ]);
}());