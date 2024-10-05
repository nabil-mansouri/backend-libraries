(function() {
	'use strict';
	var app = angular.module('commons.services');
	// Currencies
	app.factory('Ordertype', [ '$resource', '$cacheFactory', function($resource, $cacheFactory) {
		var cache = $cacheFactory('Ordertype');
		var url = sprintf("%s/commons/ordertype/", Wick.BASE_URL);
		var resource = $resource(url, {}, {})
		var toServer = function(data, headers) {
			var obj = [];
			data.forEach(function(val) {
				if (val.selected) {
					obj = val;
				}
			})
			return angular.toJson(obj);
		};

		resource.clearCache = function() {
			cache.removeAll()
		}
		return resource;
	} ]);
	app.filter('ordertype', function() {
		return function(ordertype) {
			if (ordertype) {
				return ordertype.orderType;
			} else {
				return "";
			}
		}
	});
}());