(function() {
	'use strict';
	var app = angular.module('commons.services');
	app.factory('urlService', [ '$location', function($location) {
		var service = {};
		service.pushPath = function(path) {
			if (!AppLocalSession.urlStack) {
				AppLocalSession.urlStack = []
			}
			AppLocalSession.urlStack.push(path)
		}
		service.redirectAndPush = function(url) {
			if (!AppLocalSession.urlStack) {
				AppLocalSession.urlStack = []
			}
			service.pushPath($location.path());
			$location.path(url);
		}
		service.redirectToLast = function() {
			if (!AppLocalSession.urlStack) {
				AppLocalSession.urlStack = []
			}
			if (AppLocalSession.urlStack.length > 0) {
				$location.path(AppLocalSession.urlStack.pop())
			}
		}
		return service;
	} ]);

}());