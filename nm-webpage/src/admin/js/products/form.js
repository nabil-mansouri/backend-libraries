(function() {
	Wick.DEPENDENCIES.push("products.form");
	var module = angular.module("products.form", [ 'products.service', 'commons.all', 'ckedit', 'ui.bootstrap' ]);

	module.controller('AbstractProductFormController', [ '$scope', '$rootScope', 'ScopeService', 'Product', 'ProductDraft', 'MultiviewService',
			function($scope, $rootScope, ScopeService, Product, ProductDraft, MultiviewService) {
				if (MultiviewService.hasParam("edit")) {
					$scope.product = Product.edit({
						id : MultiviewService.getParam("edit")
					})
				} else if (MultiviewService.hasParam("draft")) {
					$scope.product = ProductDraft.edit({
						id : MultiviewService.getParam("draft")
					})
				} else {
					$scope.product = Product.create()
				}
				$scope.isVisible = function() {
					return $scope.$eval("product.$resolved && !product.configError");
				}
				$scope.cancelConfig = function() {
					$rootScope.$broadcast("RemoveSubview")
				}
				$scope.remove = function() {
					return $scope.product.$remove({
						id : $scope.product.id
					}).then(function() {
						$rootScope.$broadcast("RemoveSubview")
						$rootScope.$broadcast($scope.getDeleteEvent(), $scope.product)
					}, function() {
						ScopeService.show($scope, "deleted.fail")
					})
				}
				$scope.cancel = function() {
					$rootScope.$broadcast("RemoveSubview")
				}
				$scope.draft = function() {
					return ProductDraft.save($scope.product).$promise.then(function(data) {
						$scope.cancel();
						$rootScope.$broadcast("ProductDraftSaved", data)
					}, function() {
						ScopeService.show($scope, "saved.fail")
					})
				}
				$scope.submit = function() {
					ScopeService.hideAll($scope)
					// Must do like that because of draft.save
					return Product.save($scope.product).$promise.then(function() {
						$scope.cancel();
						$rootScope.$broadcast("ProductSaved", $scope.product)
					}, function() {
						ScopeService.show($scope, "saved.fail")
					})
				}
				{
					var catChanged = function() {
						$scope.refreshPromise = $scope.product.$refreshCat();
						$scope.refreshPromise.then(function(data) {
							$scope.product = data;
						})
					}
					$scope.$on("CategorySaved", catChanged)
					$scope.$on("CategoryDeleted", catChanged)
				}
				{
					var prodChanged = function() {
						$scope.refreshPromise = $scope.product.$refreshProd();
						$scope.refreshPromise.then(function(data) {
							$scope.product = data;
						})
					}
					$scope.$on("ProductSaved", prodChanged)
					$scope.$on("ProductDeleted", prodChanged)
				}
				{
					var ingChanged = function() {
						$scope.refreshPromise = $scope.product.$refreshIng();
						$scope.refreshPromise.then(function(data) {
							$scope.product = data;
						})
					}
					$scope.$on("IngredientSaved", ingChanged)
					$scope.$on("IngredientDeleted", ingChanged)
				}
			} ]);
	module.controller('ProductFormController', [ '$scope', '$controller', function($scope, $controller) {
		$controller("AbstractProductFormController", {
			$scope : $scope
		})
		$scope.getDeleteEvent = function() {
			return "ProductDeleted";
		}
	} ]);
	module.controller('ProductDraftFormController', [ '$scope', '$controller', function($scope, $controller) {
		$controller("AbstractProductFormController", {
			$scope : $scope
		})
		$scope.getDeleteEvent = function() {
			return "ProductDraftDeleted";
		}
	} ]);
})();
