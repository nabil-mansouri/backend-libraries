(function() {
	//
	Wick.DEPENDENCIES.push("prices.table");
	var app = angular.module("prices.table", [ 'prices.service', 'products.filters', 'commons.all', 'ui.bootstrap' ]);
	app.controller('PriceTable', [ '$scope', '$rootScope', 'Price', '$timeout', function($scope, $rootScope, Price, $timeout) {
		$scope.prices = Price.query();

		$scope.edit = function(price) {
			var entity = Price.edit({
				id : price.id
			})
			$rootScope.$broadcast("PriceEdit", entity)
			return entity.$promise;
		}
		$scope.$on("PriceFiltered", function(e, prices) {
			$scope.prices = prices;
		})
		$scope.$on("PriceSaved", function() {
			$scope.prices = Price.query();
		})
		$scope.$on("PriceDeleted", function() {
			$scope.prices = Price.query();
		})
	} ]);
	app.controller('PriceFilter', [ '$scope', '$rootScope', 'Price', '$timeout', function($scope, $rootScope, Price, $timeout) {
		$scope.reset = function() {
			$scope.dohide = true;
			$scope.filter = Price.createFilter();
			return $scope.filter.$promise;
		}
		$scope.reset()
		$scope.hide = function() {
			$scope.dohide = true;
		}
		$scope.show = function() {
			$scope.dohide = false;
		}
		$scope.cancel = function() {
			$scope.reset()
			var prices = Price.query()
			$rootScope.$broadcast("PriceFiltered", prices)
			return prices.$promise;
		}
		$scope.submit = function() {
			var prices = Price.filter($scope.filter)
			$rootScope.$broadcast("PriceFiltered", prices)
			return prices.$promise;
		}
	} ]);

})();
