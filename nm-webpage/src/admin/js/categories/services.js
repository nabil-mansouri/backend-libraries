(function() {
	// DAO

	var module = angular.module("categories.service", [ "commons.all", "ngResource" ]);
	module.factory('Category', [ '$resource', '$cacheFactory', function($resource, $cacheFactory) {
		var cache = $cacheFactory('Category');
		var url = sprintf("%s/categories/%s/", Wick.BASE_URL, Wick.LANG);
		var resource = $resource(url + ":id", {}, {
			create : {
				url : url + "create/:id"
			},
			edit : {
				url : url + "edit/:id"
			}
		})
		resource.clearCache = function() {
			cache.removeAll()
		}
		return resource;
	} ]);

})();
