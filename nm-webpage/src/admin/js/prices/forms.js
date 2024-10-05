(function() {
	//
	Wick.DEPENDENCIES.push("prices.forms");
	var app = angular.module("prices.forms", [ 'prices.service', 'prices.filters', 'products.filters', 'commons.all', 'ui.calendar', 'ui.bootstrap',
			'mgcrea.ngStrap' ]);
	app.controller('PriceForm', [ '$rootScope', '$scope', 'Price', 'ScopeService', function($rootScope, $scope, Price, ScopeService) {
		$scope.reset = function() {
			$scope.temp = {};
			$scope.price = Price.create();
			return $scope.price.$promise;
		}
		$scope.reset();
		//

		$scope.$on("PriceEdit", function(e, price) {
			$scope.temp = {};
			$scope.price = price;
		})
		$scope.$watch("price.filter.from", function(val,oldValue) {
			if (val !== oldValue) {
				if (val) {
					$scope.temp.minto = val;
				} else {
					$scope.temp.minto = new Date();
				}
			  }
			
		})
		$scope.$watch("price.product.id", function(val) {
			if (val) {
				$scope.refreshPromise = Price.refresh($scope.price).$promise;
				$scope.refreshPromise.then(function(data) {
					$scope.price = data;
				})
			}
		})
		$scope.removePrice = function() {
			return $scope.price.$remove({
				id : $scope.price.id
			}).then(function() {
				$scope.cancel();
				$rootScope.$broadcast("PriceDeleted", $scope.price)
			}, function() {
				ScopeService.show($scope, "deleted.fail")
			})
		}
		$scope.cancel = function() {
			return $scope.reset()
		}
		$scope.submit = function() {
			ScopeService.hideAll($scope)
			// Must do like that because of draft.save
			return $scope.price.$save().then(function() {
				$scope.cancel();
				$rootScope.$broadcast("PriceSaved", $scope.price)
			}, function() {
				ScopeService.show($scope, "saved.fail")
			})
		}
		$scope.remove = function(node, index) {
			node.values.splice(index, 1)
		}
		$scope.add = function(node) {
			node.values.push({})
		}
		$scope.canAdd = function(n) {
			return n.enable;
		}
		$scope.isProductPartOrPart = function(n) {
			return $scope.isPart(n) || $scope.isProductPart(n);
		}
		$scope.isProductPart = function(n) {
			return n.node.type == 'PRODUCT_PART';
		}
		$scope.isPart = function(n) {
			return n.node.type == 'PART';
		}
	} ]);
	app.controller('PriceFormProductRow', [ '$scope', function($scope) {
		$scope.init = function(node) {
			$scope.node = node;
		}
	} ]);

	app.filter('orderfilter', [ function($filter) {
		return function(inputArray, allOrders) {
			var data = [];
			angular.forEach(inputArray, function(item) {
				if (item.selected || allOrders) {
					data.push(item);
				}
			});
			return data;
		};
	} ]);
	app.directive('orderselect', function() {
		return {
			restrict : 'E',
			scope : {
				group : "=",
				row : "=",
				filter : "=",
			},
			templateUrl : function(element, attr) {
				return "orderselect.html";
			},
			link : function($scope, $element, $attrs, ctls) {
				$scope.$watch("row.allOrders", function(v) {
					if (typeof v == "undefined") {
						$scope.row.allOrders = true;
					}
				})
				$scope.canShow = function(r) {
					return r.selected || $scope.filter.allOrders;
				}
			}
		};
	});

	app.filter('restofilter', [ function($filter) {
		return function(inputArray, allRestaurants) {
			var data = [];
			angular.forEach(inputArray, function(item) {
				if (item.selected || allRestaurants) {
					data.push(item);
				}
			});
			return data;
		};
	} ]);
	app.directive('restoselect', function() {
		return {
			restrict : 'E',
			scope : {
				group : "=",
				row : "=",
				filter : "=",
			},
			templateUrl : function(element, attr) {
				return "restoselect.html";
			},
			link : function($scope, $element, $attrs, ctls) {
				$scope.$watch("row.allRestaurants", function(v) {
					if (typeof v == "undefined") {
						$scope.row.allRestaurants = true;
					}
				})
				$scope.canShow = function(r) {
					return r.selected || $scope.filter.allRestaurants;
				}
			}
		};
	});
	app.directive('pricevalue', function() {
		return {
			restrict : 'E',
			scope : {
				group : "=",
				row : "=",
				currency : "=",
			},
			templateUrl : function(element, attr) {
				return "pricevalue.html";
			},
			link : function($scope, $element, $attrs, ctls) {
			}
		};
	});
})();
