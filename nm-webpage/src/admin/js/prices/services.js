(function() {
	// DAO
	var module = angular.module("prices.service", [ "commons.all", "ngResource" ]);
	module.factory('Price', [ '$resource', '$cacheFactory', '$rootScope', function($resource, $cacheFactory, $rootScope) {
		var cache = $cacheFactory('Price');
		var url = sprintf("%s/prices/%s/", Wick.BASE_URL, Wick.LANG);
		// transformRequest pour eviter les null dans les combo
		var iterator = function(price, it) {
			if (price) {
				if (price.root && price.root.values) {
					price.root.values.forEach(it)
				}
				if (price.nodes) {
					price.nodes.forEach(function(n) {
						n.values && n.values.forEach(it)
					})
				}
			}
		}
		var transformResponse = function(price, headers) {
			price = angular.fromJson(price);
			var reset = function(val) {
				if (val.type == null) {
					val.type = "";
				}
			}
			iterator(price, reset)
			return price;
		};
		var transformRequest = function(price, headers) {
			var reset = function(val) {
				if (val.type == "") {
					val.type = null;
				}
			}
			iterator(price, reset)
			return angular.toJson(price);
		};
		var resource = $resource(url + ":id", {}, {
			refresh : {
				url : url + "refresh",
				method : 'POST',
				transformRequest : transformRequest,
				transformResponse : transformResponse
			},
			create : {
				url : url + "create",
				transformResponse : transformResponse
			},
			edit : {
				url : url + "edit/:id",
				transformResponse : transformResponse
			},
			createFilter : {
				url : url + "filter"
			},
			filter : {
				url : url + "filter",
				method : 'POST',
				isArray : true
			},
			save : {
				url : url + ":id",
				method : 'POST',
				transformRequest : transformRequest,
				transformResponse : transformResponse
			}
		})
		resource.clearCache = function() {
			cache.removeAll()
		}
		return resource;
	} ]);

})();
