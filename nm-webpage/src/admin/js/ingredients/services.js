(function() {
	// DAO

	var module = angular.module("ingredients.service", [ "commons.all", "ngResource" ]);
	module.factory('Ingredient', [ '$resource', '$cacheFactory', function($resource, $cacheFactory) {
		var cache = $cacheFactory('Ingredient');
		var url = sprintf("%s/ingredients/%s/", Wick.BASE_URL, Wick.LANG);
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
