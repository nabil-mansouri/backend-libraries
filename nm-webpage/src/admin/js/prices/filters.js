(function() {
	// DAO

	var module = angular.module("prices.filters", [ "commons.all" ]);

	module.filter("ordertype", function() {
		return function(input) {
			if (input) {
				if (I18Manager[input]) {
					return I18Manager[input];
				} else if (input.orderType && I18Manager[input.orderType]) {
					return I18Manager[input.orderType];
				}
			}
			return "N/A"
		}
	});
})();
