(function() {
	'use strict';
	var app = angular.module('commons.services', []);
	// Dispatcher
	app.factory('dispatcher', [ '$rootScope', '$timeout', function($rootScope, $timeout) {
		var service = {};
		service.emit = function(event, args) {
			$rootScope.$emit(event, args);
			// console.debug("[productDispatcher] dispatch emit",arguments);
			// var apps = jQuery('.ng-scope:not(.ng-scope .ng-scope)');
			// for(var i = 0 ; i < apps.length; i++){
			// angular.element(apps[i]).scope().$emit(event,args);
			// }
		};
		service.broadcast = function(event, args) {
			// console.debug("[productDispatcher] dispatch
			// broadcast",arguments);
			$timeout(function() {
				$rootScope.$broadcast(event, args);
			})
			// var apps = jQuery('.ng-scope:not(.ng-scope .ng-scope)');
			// for(var i = 0 ; i < apps.length; i++){
			// angular.element(apps[i]).scope().$broadcast(event,args);
			// }
		};
		return service;
	} ]);

}());