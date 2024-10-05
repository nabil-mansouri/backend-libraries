(function() {
	Wick.DEPENDENCIES.push("cash.menu");
	// console.debug("Application product form:
	// ",document.getElementById("appCategoryForm"));
	var cashForm = angular.module("cash.menu", [ 'commons.all' ]);
	cashForm.controller('CashMenu', [ '$scope', '$location', function($scope, $location) {

		$scope.getCss = function(name) {
			var path = $location.path();
			return (path == name) ? "active" : "";
		}
	} ]);

})();
