(function() {
	Wick.DEPENDENCIES.push("cash.paiment");
	// console.debug("Application product form:
	// ",document.getElementById("appCategoryForm"));
	var cashForm = angular.module("cash.paiment", [ 'commons.all', "order.service", "cart.service" ]);
	cashForm.controller('AbstractCashPaiement', [ '$scope', '$rootScope', '$location', 'Order', 'cartService', 'orderService', 'Transaction', function($scope, $rootScope, $location, Order, cartService, orderService, Transaction) {
		$scope.setOrder = function(order) {
			$scope.order = order;
		}

		$scope.$on("OnOrderChanged", function(e, order) {
			console.debug("[AbstractCashPaiement] receiving OnTransactioChange")
			$scope.setOrder(order);
		})
		$scope.rollback = function(transaction) {
			orderService.rollback(transaction, $scope.order).then(function(order) {
				$scope.setOrder(order);
			})
		}
		$scope.forceCommit = function() {
			orderService.forceCommit($scope.order);
		}
		$scope.push = function(type) {
			console.debug("[AbstractCashPaiement] emitting OnPushTransaction")
			$scope.$broadcast("OnTransactionBegin", {
				type : type,
				order : $scope.order
			})
		}
		$scope.isType = function(transaction, type) {
			return transaction.paymentType == type;
		}
		$scope.isAction = function(transaction, type) {
			return transaction.actionType == type;
		}
		$scope.isDue = function() {
			return $scope.order && $scope.order.due >= 0;
		}
		$scope.isOk = function() {
			return $scope.order && $scope.order.due == 0;
		}
		$scope.getInverseDue = function() {
			return !$scope.order.due;
		}
		$scope.getRowClass = function(transaction) {
			if (transaction.lastState.state == "Rollback") {
				return "nb-linethrough";
			} else {
				return "";
			}
		}
	} ]);
	cashForm.controller('CashPaiement', [ '$scope', '$controller', '$location', 'Order', 'cartService', 'orderService', function($scope, $controller, $location, Order, cartService, orderService) {
		//
		$controller('AbstractCashPaiement', {
			$scope : $scope
		});
		//
		$scope.loading = true;
		$scope.setOrder(orderService.save());
		$scope.cart = cartService.create();
		//
		$scope.order.$promise.then(function(data) {
			$scope.loading = false;
		})
	} ]);
	cashForm.controller('ShowCashPaiement', [ '$scope', '$controller', '$location', 'Order', 'cartService', 'orderService', function($scope, $controller, $location, Order, cartService, orderService) {
		//
		$controller('AbstractCashPaiement', {
			$scope : $scope
		});
		//
		$scope.lockTransaction = true;
		$scope.isUnlock = function() {
			return !$scope.lockTransaction;
		}
		$scope.isUnlock = function() {
			return !$scope.lockTransaction;
		}
		$scope.toggleLock = function() {
			$scope.lockTransaction = !$scope.lockTransaction;
		}
		$scope.$on("OnOrderDetail", function(e, order) {
			// COPY BECAUSE OF WATCH
			$scope.setOrder(order);
		})
	} ]);
	cashForm.controller('SubmitCashPaiement', [ '$scope', '$controller', '$location', 'Order', 'cartService', 'orderService', function($scope, $controller, $location, Order, cartService, orderService) {
		//
		$controller('AbstractCashPaiement', {
			$scope : $scope
		});
		//
		$scope.setOrder(orderService.getCurrentOrder());
		if (!orderService.isCommit($scope.order)) {
			console.debug("[SubmitCashPaiement] last cart was not committed so redirecting to /cart/next...", $scope.cart)
			$location.path("/cart/next")
		}
	} ]);

	cashForm.controller('CashPaiementDetail', [ '$scope', '$location', 'Transaction', 'orderService', '$rootScope', function($scope, $location, Transaction, orderService, $rootScope) {
		$scope.reset = function() {
			$scope.transaction = new Transaction
		}
		$scope.reset();
		//
		$scope.$on("OnTransactionBegin", function(e, params) {
			console.debug("[CashPaiementDetail] receiving OnPushTransaction")
			$scope.reset()
			$scope.transaction.paymentType = params.type;
			$scope.order = params.order;
		})
		$scope.submit = function() {
			$scope.order = orderService.saveTransaction($scope.transaction, $scope.order)
			$scope.order.then(function(order) {
				$scope.reset()
				$rootScope.$broadcast("OnOrderChanged", order)
			})
		}
		$scope.is = function(type) {
			return $scope.transaction.paymentType && $scope.transaction.paymentType == type;
		}
	} ]);

	cashForm.controller('CashPaiementReturn', [ '$scope', '$location', 'Transaction', 'orderService', '$rootScope', function($scope, $location, Transaction, orderService, $rootScope) {
		$scope.reset = function() {
			$scope.form = new Transaction
		}
		$scope.getInverseDue = function() {
			return -$scope.order.due;
		}
		$scope.reset();
		//
		$scope.submit = function() {
			orderService.returnCash($scope.form, $scope.order).then(function(order) {
				$scope.reset()
				$rootScope.$broadcast("OnOrderChanged", order)
			})
		}
	} ]);

})();
