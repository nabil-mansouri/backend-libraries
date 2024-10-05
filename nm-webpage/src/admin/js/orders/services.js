(function() {
	// DAO
	var orderService = angular.module("order.service", [ "commons.all" ,"cart.service"]);
	orderService.factory('Order', [ '$resource', function($resource) {
		var url = sprintf('%s/ws/orders/%s', Wick.BASE_URL, Wick.LANG);
		var Order = $resource(sprintf("%s/:id", url), {
			id : '@id'
		}, {
			fetch : {
				method : 'GET',
				cache : true,
				url : url,
				isArray : true
			},
			get : {
				method : 'GET',
				cache : true,
				isArray : false
			},
			filterUUID : {
				method : 'GET',
				cache : true,
				url : sprintf("%s/filter/:filter/:limit",url),
				isArray : true
			},
			pushState : {
				method : 'GET',
				cache : false,
				url : sprintf("%s/push/:id/:state",url)
			}
		});
		return Order;
	} ]);
	orderService.factory('Transaction', [ '$resource', function($resource) {
		var url = sprintf('%s/ws/orders/transaction/:idOrder/%s', Wick.BASE_URL,Wick.LANG);
		var Order = $resource(url, {
			id : '@id'
		}, {
			pushCash : {
				method : 'POST',
				url : sprintf("%s/push/cash", url)
			},
			returnCash : {
				method : 'POST',
				url : sprintf("%s/return/cash", url)
			},
			pushCheck : {
				method : 'POST',
				url : sprintf("%s/push/check", url)
			},
			pushElectronics : {
				method : 'POST',
				url : sprintf("%s/push/electronics", url)
			},
			commit : {
				method : 'GET',
				url : sprintf("%s/commit", url)
			},
			refund : {
				method : 'GET',
				url : sprintf("%s/refund", url)
			},
			rollback : {
				method : 'GET',
				url : sprintf("%s/rollback/all", url)
			},
			rollbackTransaction : {
				method : 'POST',
				url : sprintf("%s/rollback", url)
			}
		});
		return Order;
	} ]);
	//
	orderService.factory('orderService', [ '$resource', 'Order', 'cartService', 'Transaction', 'dispatcher', '$location', function($resource, Order, cartService, Transaction, dispatcher, $location) {
		var service = {};
		service.save = function() {
			var cart = cartService.create();
			var order = Order.save(cart);
			order.$promise.then(function(data) {
				service.setCurrent(order.cart)
			}).catch(function(response) {
			    if(response.status == 400){
			    	service.setCurrent(response.data)
			    }
			    $location.path("/cart/check")
			})
			return order;
		}
		service.setCurrent = function(cart) {
			AppLocalSession.currentCart = cart;
		}
		service.setCurrentOrder = function(cart) {
			AppLocalSession.currentOrder = cart;
		}
		service.getCurrentOrder = function() {
			return AppLocalSession.currentOrder;
		}
		service.isCommit = function(order) {
			return order && order.transaction && order.transaction.lastState.state == "Commit"
		}
		service.getCurrent = function() {
			return AppLocalSession.currentCart;
		}
		service.returnCash = function(transaction, orderParam) {
			var order = transaction.$returnCash({
				idOrder : orderParam.idOrder
			});
			return order;
		}
		service.rollback = function(transaction, orderParam) {
			var tr = new Transaction()
			tr.transactionId = transaction.transactionId;
			return tr.$rollbackTransaction({
				idOrder : orderParam.idOrder
			});
		}
		service.forceCommit = function(orderParam) {
			var order = Transaction.commit({
				idOrder : orderParam.idOrder
			});
			order.$promise.then(function(order) {
				service.setCurrentOrder(order)
				$location.path("/cart/next")
			})
			return order;
		}
		service.saveTransaction = function(transaction, order) {
			switch (transaction.paymentType) {
				case "Cash": {
					return transaction.$pushCash({
						idOrder : order.idOrder
					})
				}
				case "Check":
					transaction.quantity=1;
					return transaction.$pushCheck({
						idOrder : order.idOrder
					})
				case "RestaurantTicket": {
					return transaction.$pushCheck({
						idOrder : order.idOrder
					})
				}
				case "Cb": {
					return transaction.$pushElectronics({
						idOrder : order.idOrder
					})
				}
			}
			return order;
		}
		service.isInStateFlow=function(order,state){
			return order.statesFlow[state];
		}
		service.isPastState=function(order,state){
			var e = order.statesFlow[state];
			return StringUtils.equalsIgnoreCase(e, "Occured");
		}
		service.isNextState=function(order,stateBegin,state){
			var e = order.statesFlow[state];
			var e2 = stateBegin && order.statesFlow[stateBegin];
			return StringUtils.inIgnoreCase("CanOccure", [ e, e2 ]);
		}
		return service;
	} ]);
	orderService.directive('orderSearch', function(Order) {
		return {
			restrict : 'E',
			require:'ngModel',
			templateUrl : "order_search.html",
			scope : {
				model : "=ngModel",
				listenerList : "&",
				listener : "&",
				limit : "@",
				minchar : "@"
			},
			link : function($scope, element, attrs, ctrl) {
				$scope.orders=[]
				$scope.limit = ($scope.limit) ? $scope.limit : 10;
				$scope.minchar = ($scope.minchar) ? $scope.minchar : 2;
				ctrl.$formatters.push(function(val){
					if($scope.lock){
						$scope.lock=false;
						return val;
					}
					if (val && $scope.canAutocomplete(val)) {
						$scope.orders = Order.filterUUID({
							filter : val,
							limit : $scope.limit
						})
					} else {
						$scope.orders = []
					}
					$scope.listenerList() && $scope.listenerList()($scope.orders)
					return val;
				})
				$scope.select = function(order) {
					$scope.listener()(order);
					$scope.model = order.uuid;
					$scope.orders = [];
					$scope.lock=true;
				}
				
				$scope.canAutocomplete = function(val) {
					return StringUtils.getLength(val) > $scope.minchar;
				}
			}
		};
	});
})();
