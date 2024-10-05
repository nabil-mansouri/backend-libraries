(function() {
	Wick.DEPENDENCIES.push("order.table");
	var cashForm = angular.module("order.table", [ "client.service", 'restaurant.service', 'commons.all', "order.service" ]);
	cashForm.controller('OrderFilter', [ '$scope', '$rootScope', '$location', 'Order', 'Restaurant', function($scope, $rootScope, $location, Order, Restaurant) {
		$scope.restaurants = Restaurant.fetch()
		$scope.reset = function() {
			$scope.filter = {}
		}
		$scope.setClient = function(client) {
			$scope.filter.client = client.id;
		}
		$scope.setOrder = function(order) {
			$scope.filter.reference = order.uuid;
		}
		$scope.submit = function() {
			$rootScope.$broadcast("OnOrderFilter", $scope.filter)
		}
		$scope.clear = function() {
			$scope.reset()
			$rootScope.$broadcast("OnOrderFilter", $scope.filter)
		}
		$scope.reset()
	} ])
	cashForm.controller('OrderTable', [ '$scope', '$rootScope', '$location', 'Order', 'orderService', function($scope, $rootScope, $location, Order, orderService) {
		$scope.orderService = orderService;
		$scope.$on("OnOrderFilter", function(e, filter) {
			// COPY BECAUSE OF WATCH
			$scope.filter = angular.copy(filter);
		})
		$scope.params = {
			overrides : {
				doReloadModels : function(filter, loadCallBack) {
					// KEEP ORiGINAL
					var filter = angular.copy($scope.filter)
					if (filter.from) {
						filter.from = filter.from.getTime()
					}
					if (filter.to) {
						filter.to = filter.to.getTime()
					}
					return Order.fetch(filter, loadCallBack)
				},
				selectOrder : function(order) {
					$rootScope.$broadcast("OnOrderDetail", Order.get({
						id : order.idOrder
					}))
				}
			},
			watch : function() {
				return $scope.filter;
			}
		}
		$scope.reset = function() {
			$scope.filter = {}
		}
		$scope.clear = function() {
			$scope.reset()
		}
		$scope.reset()
	} ]);
})();
