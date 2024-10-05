(function() {
	Wick.DEPENDENCIES.push("composer.form");
	// console.debug("Application product form:
	// ",document.getElementById("appCategoryForm"));
	var cashForm = angular.module("composer.form", [ "restaurant.service", "price.service", 'composer.service', 'cart.service', 'product.service', 'commons.all' ]);

	cashForm.controller('ComposeProducts', [ '$scope', '$rootScope', '$location', 'categoryService', 'productService', 'dispatcher', 'anchorSmoothScroll', 'cartService', 'entityService', 'priceService', 'Price', 'restoService', 'Restaurant', '$routeParams', 'Category', 'feedback', function($scope, $rootScope, $location, categoryService, productService, dispatcher, anchorSmoothScroll, cartService, entityService, priceService, Price, restoService, Restaurant, $routeParams, Category, feedback) {
		$scope.categoryService = categoryService;
		$scope.productService = productService;
		$scope.priceService = priceService;
		$scope.filter = {
			category : null,
			type : null,
			restaurant : null
		}
		$scope.$watch("filter",function(){
			$rootScope.$broadcast("OnProductReloadModel")
		},true)
		$scope.paramsProduct = {
			uniqueSelect : true,
			onSelect : function(selected, model, isAdd) {
				cartService.clearComposer()
				$location.path("/compose/next/" + model.product.id)
			},
			overrides : {
				doReloadModels : function(filter, loadCallBack) {
					var cart = cartService.create()
					var filter = angular.copy($scope.filter);
					if (cart.type) {
						filter.type = cart.type;
					}
					if (cart.restaurant) {
						filter.restaurant = cart.restaurant.id;
					}
					return Price.currents(filter, loadCallBack)
				}
			}
		}
		$scope.paramsCategory = {
			uniqueSelect : true,
			onSelect : function(selected, model, isAdd) {
				if (isAdd) {
					$scope.filter.category = model.id;
				} else {
					delete $scope.filter.category
				}
				console.debug("[OrderFilter] on selected category...", $scope.filter.category)
				$rootScope.$broadcast('OnProductReloadModel');
			},
			overrides : {
				resetCategory : function() {
					delete $scope.filter.category
					$rootScope.$broadcast('OnProductReloadModel');
				},
				getCssCategory : function(cat1) {
					var cat2Id = $scope.filter.category;
					if (cat1) {
						console.debug("[OrderFilter] get css category...", cat1, cat2Id)
						if (cat2Id && cat1.id == cat2Id) {
							return "active";
						} else {
							return "";
						}
					} else {
						return (!cat1 && !cat2Id) ? "active" : "";
					}
				}
			}
		}
		$scope.reset = function() {
			ObjectUtils.clear($scope.paramsProduct.filters)
			$rootScope.$broadcast('OnProductReloadModel');
			cartService.unlock()
		}
		if ($routeParams.idCat) {
			$scope.filter.category = Category.get({
				id : $routeParams.idCat
			})
			$scope.filter.category.$promise.then(function() {
				$rootScope.$broadcast('OnProductReloadModel');
			})
		} else if ($routeParams.errorCode) {
			if (parseInt($routeParams.errorCode) == 404) {
				feedback.to("FilterComposer").warn = 'product.notfound';
			}
		}
		$scope.reset();
	} ]);

	//
	cashForm.controller('ComposerLevel', [ '$scope', 'composerService', 'historyService', 'cartService', '$location', 'levelService', function($scope, composerService, historyService, cartService, $location, levelService) {
		console.debug("[ComposerLevel] initing ...")
		$scope.composer = cartService.create().composer;
		$scope.levels = levelService.buildLevels($scope.composer)
		$scope.$watch("composer", function() {
			$scope.levels = levelService.buildLevels($scope.composer)
		}, true)
		$scope.getCssChoice = function(nodeChoice) {
			if (historyService.isCurrent($scope.composer, nodeChoice)) {
				return "btn-info";
			} else if (historyService.isParentOfCurrent($scope.composer, nodeChoice)) {
				return "btn-primary";
			} else if (historyService.isPrevious($scope.composer, nodeChoice)) {
				return "btn-success";
			} else {
				return "btn-warning";
			}
		}
		$scope.isEnable = function(nodeChoice) {
			return historyService.hasBeenVisited($scope.composer, nodeChoice)
		}
		$scope.isDisable = function(nodeChoice) {
			return !historyService.hasBeenVisited($scope.composer, nodeChoice)
		}
		$scope.goTo = function(nodeChoice) {
			return composerService.visiteTo($scope.composer, nodeChoice)
		}
	} ]);
	cashForm.controller('ComposerChoice', [ '$scope', 'composerService', 'cartService', '$location', function($scope, composerService, cartService, $location) {
		console.debug("[ComposerChoice] initing ...")
		$scope.composer = cartService.create().composer;
		if (!$scope.composer) {
			$location.path('/compose/next')
		}
		$scope.getCssProduct = function(prod1) {
			if ($scope.composer) {
				if (composerService.isProductSelected($scope.composer, prod1)) {
					return "active";
				} else {
					return "";
				}
			}
		}
		$scope.getCssNoProduct = function() {
			if ($scope.composer) {
				if (composerService.hasNoProductSelected($scope.composer)) {
					return "active";
				} else {
					return "";
				}
			}
		}
		$scope.noselect = function() {
			composerService.setProductSelected($scope.composer, null, true);
			$location.path('/compose/next')
		}
		$scope.select = function(nodeProduct) {
			composerService.setProductSelected($scope.composer, nodeProduct, true);
			$location.path('/compose/next')
		}
	} ]);
	cashForm.controller('ComposerIngredient', [ '$scope', '$location', 'cartService', 'composerService', function($scope, $location, cartService, composerService) {
		$scope.composer = cartService.create().composer;
		if (!$scope.composer) {
			$location.path('/compose/next')
		}
		$scope.getCssIngredient = function(ing) {
			console.debug("[ComposerIngredient] get css ingredient...", ing, $scope)
			if (composerService.hasExcludeIngredients($scope.composer, ing)) {
				return "active";
			} else {
				return "";
			}
		}
		$scope.hasCssIngredient = function(ing) {
			console.debug("[ComposerIngredient] has css ingredient...", ing, $scope)
			return composerService.hasExcludeIngredients($scope.composer, ing);
		}
		$scope.toggle = function(ing) {
			composerService.toggleIngredient($scope.composer, ing)
		}
		$scope.submit = function() {
			$location.path('/compose/next')
		}
	} ]);
	cashForm.controller('ComposerRestaurant', [ '$scope', '$rootScope', 'restoService', 'Restaurant', 'cartService', '$location', '$routeParams', function($scope, $rootScope, restoService, Restaurant, cartService, $location, $routeParams) {
		$scope.restoService = restoService;
		$scope.paramsRestaurant = {
			uniqueSelect : true,
			onSelect : function(selected, model, isAdd) {
				cartService.setRestaurant(null, model);
				if ($routeParams.idProd) {
					$location.path("/compose/next/" + $routeParams.idProd)
				} else {
					$location.path("/products")
				}
			},
			overrides : {
				doReloadModels : function(filter, loadCallBack) {
					return Restaurant.currents(loadCallBack)
				}
			}
		}
	} ]);
	cashForm.controller('ComposerType', [ '$scope', '$rootScope', '$location', '$routeParams', 'dispatcher', 'anchorSmoothScroll', 'cartService', 'productService', function($scope, $rootScope, $location, $routeParams, dispatcher, anchorSmoothScroll, cartService, productService) {
		$scope.isSelected = function(type) {
			return cartService.getType(null) == type;
		}
		$scope.setType = function(type) {
			cartService.setType(null, type)
			if ($routeParams.idProd) {
				$location.path("/compose/next/" + $routeParams.idProd)
			} else {
				$location.path("/products")
			}
		}
	} ]);
	cashForm.controller('ComposerNew', [ '$scope', '$rootScope', 'cartService', '$location', '$routeParams', function($scope, $rootScope, cartService, $location, $routeParams) {
		cartService.newComposer(null, $routeParams.idProd).then(function() {
			$location.path("/compose/next/" + $routeParams.idProd)
		}, function() {
			$location.path("/products/notfound/404")
		})
	} ]);
	cashForm.controller('ComposerFinish', [ '$scope', '$rootScope', 'cartService', '$location', function($scope, $rootScope, cartService, $location) {
		cartService.pushComposer()
		$location.path('/compose/reset');
	} ]);
	cashForm.controller('ComposerNext', [ '$scope', '$rootScope', 'cartService', '$location', '$routeParams', 'productService', 'composerService', function($scope, $rootScope, cartService, $location, $routeParams, productService, composerService) {
		var compute = function() {
			var cart = cartService.create();
			if (cart.restaurant) {
				if (cart.type) {
					if (cart.composer) {
						var next = composerService.goToNext(cart.composer);
						if (next && productService.isPartNode(next)) {
							return '/compose/choices/';
						} else if (next && productService.isProductNode(next)) {
							return '/compose/ingredients/';
						} else {
							return '/compose/finish/';
						}
					} else {
						return '/compose/new/' + $routeParams.idProd;
					}
				} else {
					return '/compose/type/' + $routeParams.idProd;
				}
			} else {
				return '/compose/restaurants/' + $routeParams.idProd;
			}
		}
		var next = compute();
		console.debug("[ComposerNext] redirecting ...", next, $routeParams)
		$location.path(next)
	} ]);

})();
