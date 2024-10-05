(function() {
	'use strict';
	var app = angular.module('commons.services');
	// Currencies
	app.factory('Currency', [ '$resource', '$cacheFactory', function($resource, $cacheFactory) {
		var cache = $cacheFactory('Currency');
		var url = sprintf("%s/commons/currencies/", Wick.BASE_URL);
		var toServer = function(data, headers) {
			var obj = {};
			data.forEach(function(val) {
				if (val.defaut == val.code) {
					obj = val;
				}
			})
			return angular.toJson(obj);
		};
		var resource = $resource(url, {}, {
			fetch : {
				url : Wick.URL_CURRENCY,
				cache : cache,
				isArray : true,
				transformResponse : function(data, headers) {
					var obj = angular.fromJson(data);
					var arr = [];
					for ( var i in obj) {
						arr.push(obj[i])
					}
					return arr;
				}
			},
			select : {
				url : url + "selected"
			},
			saveSelect : {
				url : url + "selected",
				method : 'POST',
				transformRequest : toServer
			}
		})
		resource.clearCache = function() {
			cache.removeAll()
		}
		return resource;
	} ]);
	app.factory('CurrencyService', [ 'Currency', '$q', function(Currency, $q) {
		var service = {};
		service.getCurrencies = function() {
			var r = Currency.fetch();
			r.$promise.then(function() {
				Currency.select().$promise.then(function(selected) {
					r.forEach(function(val) {
						if ((val.code == selected.code)) {
							val.defaut = selected.code;
						}
					})
				})
			})
			return r;
		}
		return service;
	} ]);

	app.filter('currency', function() {
		return function(currency) {
			if (currency) {
				return currency.name;
			} else {
				return "";
			}
		}
	});
}());