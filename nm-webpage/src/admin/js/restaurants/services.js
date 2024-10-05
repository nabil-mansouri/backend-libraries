(function() {
	// DAO
	var module = angular.module("restaurants.services", [ "commons.all" ]);
	module.factory('Restaurant', [ '$resource', function($resource) {
		var url = sprintf('%s/restaurants/%s', Wick.BASE_URL, Wick.LANG);
		var resource = $resource(sprintf("%s/:id", url), {
			id : '@id'
		}, {
			create : {
				method : 'GET',
				url : sprintf("%s/create", url)
			},
			fetch : {
				method : 'GET',
				cache : true,
				isArray : true
			},
			edit : {
				method : 'GET',
				url : sprintf("%s/edit/:id", url)
			},
			planning : {
				method : 'GET',
				url : sprintf("%s/planning/:id", url)
			},
			currents : {
				method : 'GET',
				url : sprintf("%s/currents/", url),
				isArray : true
			}
		});
		resource.getBase = function() {
			return url;
		}
		return resource;
	} ]);

})();
