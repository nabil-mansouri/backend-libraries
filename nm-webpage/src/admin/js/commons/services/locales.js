(function() {
	'use strict';
	var app = angular.module('commons.services');
	// Currencies
	app.factory('Locale', [ '$resource', '$cacheFactory', function($resource, $cacheFactory) {
		var cache = $cacheFactory('Locale');
		var url = sprintf("%s/commons/locales/", Wick.BASE_URL);
		var toServer = function(data, headers) {
			var obj = {};
			data.forEach(function(val) {
				if (val.defaut==val.code) {
					obj = val;
				}
			})
			return angular.toJson(obj);
		};
		var toServerArray = function(data, headers) {
			var obj = [];
			data.forEach(function(val) {
				if (val.selected) {
					obj.push(val)
				}
			})
			return angular.toJson(obj);
		};
		var resource = $resource(url, {}, {
			fetch : {
				url : Wick.URL_LOCALE,
				cache : cache ,
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
				url : url + "selected",
				isArray:true
			},
			saveSelect : {
				url : url + "selected",
				method : 'POST',
				isArray : true,
				transformRequest : toServerArray
			},
			"default" : {
				url : url + "default"
			},
			saveDefault : {
				url : url + "default",
				method : 'POST',
				transformRequest : toServer
			}
		})
		resource.clearCache = function() {
			cache.removeAll()
		}
		return resource;
	} ]);
	app.factory('LocaleService', [ 'Locale', '$q', function(Locale, $q) {
		var service = {};
		service.getSelected = function() {
			var r = Locale.fetch();
			r.$promise.then(function() {
				Locale.select().$promise.then(function(selected) {
					r.forEach(function(val) {
						selected.forEach(function(val2){
							// MUST use IF because has many
							if((val.code == val2.code)){
								val.selected = true;
							}
						})
					})
				})
			})
			return r;
		}
		service.getDefault = function() {
			var r = Locale.select();
			r.$promise.then(function() {
				Locale.default().$promise.then(function(selected) {
					r.forEach(function(val) {
						if(val.code == selected.code){
							val.defaut = selected.code;
						}
					})
				})
			})
			return r;
		}
		return service;
	} ]);
	app.filter('locale', function() {
        return function(locale) {
                if (locale) {
                        return locale.name;
                }  else {
                        return "";
                }
        }}) ;
}());