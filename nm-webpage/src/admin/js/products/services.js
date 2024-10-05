(function() {
	// DAO

	var module = angular.module("products.service", [ "commons.all", "ngResource" ]);
	module.factory('Product', [ '$resource', '$cacheFactory', '$rootScope', function($resource, $cacheFactory, $rootScope) {
		var cache = $cacheFactory('Product');
		var url = sprintf("%s/products/%s/", Wick.BASE_URL, Wick.LANG);
		var resource = $resource(url + ":id", {}, {
			refreshCat : {
				url : url + "refresh/category",
				method : 'POST'
			},
			refreshIng : {
				url : url + "refresh/ingredient",
				method : 'POST'
			},
			refreshProd : {
				url : url + "refresh/product",
				method : 'POST'
			},
			create : {
				url : url + "create"
			},
			edit : {
				url : url + "edit/:id"
			},
			publish : {
				url : url + "publish/:id"
			},
			unpublish : {
				url : url + "unpublish/:id"
			}
		})
		resource.clearCache = function() {
			cache.removeAll()
		}
		return resource;
	} ]);
	module.factory('ProductDraft', [ '$resource', '$cacheFactory', function($resource, $cacheFactory) {
		var cache = $cacheFactory('ProductDraft');
		var url = sprintf("%s/products/drafts/%s/", Wick.BASE_URL, Wick.LANG);
		var resource = $resource(url + ":id", {}, {
			edit : {}
		})
		resource.clearCache = function() {
			cache.removeAll()
		}
		return resource;
	} ]); 
})();
