(function() {
	// DAO

	var module = angular.module("products.filters", [ "commons.all" ]);

	module.filter("partname", function() {
		return function(input) {
			if (input) {
				return input.name;
			} else {
				return "N/A"
			}
		}
	});
	module.filter("cmsname", function() {
		return function(input) {
			if (input && input.cms) {
				return input.cms.name;
			} else {
				return "N/A"
			}
		}
	});
})();
