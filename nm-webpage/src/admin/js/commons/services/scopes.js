(function() {
	'use strict';
	var app = angular.module('commons.services');
	app.factory('ScopeService', [ '$location', function($location) {
		var service = {};
		service.hideAll = function($scope) {
			$scope.$eval("show={}")
		}
		service.show = function($scope, key) {
			$scope.$eval(sprintf("show.%s=true", key))
		}
		service.hide = function($scope, key) {
			$scope.$eval(sprintf("show.%s=false", key))
		}
		return service;
	} ]);

}());