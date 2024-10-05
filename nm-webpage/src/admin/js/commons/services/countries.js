(function() {
	'use strict';
	var app = angular.module('commons.services');
	// Countries
	app.factory('Country', [ '$resource', '$q', function($resource, $q) {
		var url = sprintf("%s/addresses/countries", Wick.BASE_URL);
		var service = $resource(url, {}, {})
		return service;
	} ]);
	app.factory('CountryService', [ '$http', '$q', function($http, $q) {
		var service = {};
		return service;
	} ]);
}());