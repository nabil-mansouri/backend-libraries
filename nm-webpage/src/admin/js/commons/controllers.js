(function() {

	var app = angular.module('commons.controllers', [])
	app.factory('controllerService', [ '$http', 'dispatcher', 'entityService', '$timeout', "anchorSmoothScroll", "draftService", 'feedback', function($http, dispatcher, entityService, $timeout, anchorSmoothScroll, draftService, feedback) {
		var service = {};
		return service;
	} ]);
}());