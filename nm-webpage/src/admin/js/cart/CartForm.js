(function() {
	Wick.DEPENDENCIES.push("cart.form");
	// console.debug("Application product form:
	// ",document.getElementById("appCategoryForm"));
	var cartForm = angular.module("cart.form", [ "order.service", "price.service", 'cart.service', 'product.service', 'commons.all' ]);
	// CartBlock
	cartForm.controller('CartRow', [ '$scope', 'cartService', 'productService', 'CartDraft', '$location', function($scope, cartService, productService, CartDraft, $location) {
		$scope.init = function(cart) {
			console.debug("[CartBlock] initing...", cart)
			$scope.cart = cart;
			$scope.groups = cartService.getProductByGroup($scope.cart);
			$scope.selected = [];
		}
		//
		$scope.$watch("cart", function() {
			console.debug("[CartBlock] cart watch has changed...", $scope.cart)
			if ($scope.cart) {
				$scope.init($scope.cart)
			}
		}, true)
		$scope.isVisible = function(parent, current) {
			if (productService.isProductNode(current)) {
				return cartService.isProductVisible(parent, current);
			} else if (productService.isPartNode(current)) {
				return cartService.hasProduct(current)
			}
		}
		$scope.recallDraft = function() {
			cartService.recompute($scope.cart)
		}
		$scope.isProduct = function(node) {
			return productService.isProductNode(node);
		}
		$scope.getName = function(node) {
			return node.name;
		}
		$scope.getGroupName = function(group) {
			return $scope.getName(group[0].product)
		}
		$scope.toggleDetail = function(id) {
			if ($scope.isDetailVisible(id)) {
				var index = $scope.selected.indexOf(id);
				$scope.selected.splice(index, 1);
			} else {
				$scope.selected.push(id);
			}
		}
		$scope.isDetailVisible = function(id) {
			var index = $scope.selected.indexOf(id);
			return (index > -1);
		}
		$scope.hasPrice = function(node) {
			return cartService.hasPrice(node);
		}
		$scope.getQuantity = function(id) {
			return cartService.getQuantity($scope.groups, id)
		}
		$scope.hasIngredient = function(choice, product) {
			return cartService.hasIngredients(choice)
		}
		$scope.getIngredients = function(choice, product) {
			var ings = cartService.getIngredients(choice);
			console.debug("[CartBlock] getting ingredients...", ings)
			return ings;
		}
		$scope.hasChild = function(child) {
			return child.children.length > 0;
		}
		$scope.isLock = function() {
			return $scope.cart.locked;
		}
		$scope.isUnLock = function() {
			return !$scope.isLock();
		}
		$scope.getPrice = function(node) {
			return node.price;
		}
		$scope.getGroupPrice = function(group) {
			return cartService.getTotalGroup(group)
		}
		$scope.getTotal = function() {
			return cartService.getTotalFromGroups($scope.groups)
		}
		$scope.increment = function(group, $event) {
			$event && $event.stopPropagation()
			cartService.increment(group, $scope.groups, $scope.cart)
		}
		$scope.decrement = function(group, $event) {
			$event && $event.stopPropagation()
			cartService.decrement(group, $scope.groups, $scope.cart)
		}
		$scope.remove = function(group, ind, $event) {
			$event && $event.stopPropagation()
			cartService.remove(group, ind, $scope.groups, $scope.cart)
		}
		$scope.submit = function() {
			console.debug("[CartRow] submiting ...", $scope.cart)
			cartService.submit($scope.cart)
		}
		$scope.cancel = function() {
			console.debug("[CartRow] canceling ...", $scope.cart)
			$scope.cart = cartService.reset($scope.cart)
		}
		$scope.draft = function() {
			CartDraft.save($scope.cart).$promise.then(function() {
				$scope.cancel()
				$location.path("/cart/draft")
			})
		}
	} ]);
	//
	cartForm.controller('CartForm', [ '$scope', '$controller', 'cartService', function($scope, $controller, cartService) {
		$controller('CartRow', {
			$scope : $scope
		});
		$scope.$watch(function() {
			return AppLocalSession.currentCart;
		}, function() {
			$scope.init(cartService.create())
		})
		$scope.init(cartService.create())
	} ]);
	cartForm.controller('CartSubmit', [ '$scope', '$rootScope', '$controller', 'cartService', '$location', 'Order', function($scope, $rootScope, $controller, cartService, $location, Order) {
		// RESET (SET LAST)
		$scope.cart = cartService.reset();

		//
		$controller('CartRow', {
			$scope : $scope
		});
		$scope.init($scope.cart);
	} ]);
	cartForm.controller('CartCheck', [ '$scope', '$rootScope', 'cartService', '$location', function($scope, $rootScope, cartService, $location) {
		$scope.checked = cartService.getChecked();
		if (!$scope.checked) {
			$location.path("/products")
		}
		$scope.hasPartAbsent = function(context) {
			return _.size(context.partsAbsent) > 0;
		}
		$scope.hasProductBad = function(context) {
			return _.size(context.productBadSelection) > 0;
		}
		$scope.hasPartMandatory = function(context) {
			return _.size(context.partsMandatory) > 0;
		}
		$scope.getPartAbsent = function(context) {
			return _.values(context.partsAbsent);
		}
		$scope.getProductBad = function(context) {
			return _.values(context.productBadSelection);
		}
		$scope.getPartMandatory = function(context) {
			return _.values(context.partsMandatory);
		}
	} ]);
	cartForm.controller('CartDraft', [ '$scope', '$rootScope', 'CartDraft', function($scope, $rootScope, CartDraft) {
		$scope.drafts = CartDraft.fetch()
	} ]);
	cartForm.controller('CartNext', [ '$scope', '$rootScope', 'cartService', '$location', '$routeParams', function($scope, $rootScope, cartService, $location, $routeParams) {
		var compute = function() {
			var cart = cartService.create();
			if (cartService.canValidate(cart)) {
				if (AppLocalSession.lastUrl) {
					var url = AppLocalSession.lastUrl;
					if (url == '/cart/client') {
						return '/cart/paiement'
					} else if (url == '/cart/paiement') {
						return '/cart/success'
					} else {
						return '/cart/client';
					}
				} else {
					return '/cart/client';
				}
			} else {
				return '/compose/next';
			}
		}
		var next = compute();
		console.debug("[CartNext] redirecting ...", next, $routeParams)
		$location.path(next)
	} ]);

})();
