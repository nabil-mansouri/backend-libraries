(function() {
	Wick.DEPENDENCIES.push("products.list");
	var module = angular.module("products.list", [ 'products.service', 'commons.all', 'ckedit', 'ui.bootstrap' ]);

	module.directive('subproducts', function($rootScope, $controller) {
		return {
			restrict : 'E',
			scope : {
				product : "=",
				parts : "="
			},
			templateUrl : 'subform_subproduct.html',
			link : function($scope, element, attrs, controller) {
				$controller("SubProductListController", {
					$scope : $scope
				})
			}
		};
	});
	module.controller('SubProductListController', [ '$scope', '$rootScope', 'Product', 'ScopeService',
			function($scope, $rootScope, Product, ScopeService) {
				$scope.subforms = {};
				$scope.pushForm = function(partForm, $index) {
					$scope.subforms[$index] = partForm;
				}
				$scope.add = function() {
					$scope.parts.push({})
				}
				$scope.remove = function(index) {
					$scope.parts.splice(index)
					$scope.select(0)
				}
				$scope.select = function(index) {
					$scope.parts.forEach(function(part) {
						part.selected = false;
					})
					$scope.parts[index].selected = true;
				}
				$scope.hasAtLeatOneProduct = function(part) {
					var ok = false;
					for ( var i in part.products) {
						ok = (ok || part.products[i]);
					}
					return ok;
				}
				$scope.edit = function(node, $event) {
					$event && $event.stopPropagation()
					$rootScope.$broadcast("AddSubview", ProductFormView({
						params : {
							edit : node.id
						}
					}))
				}
				$scope.getCssClass = function() {
					if (!$scope.$eval("product.hasProducts")) {
						return "nbdisbaled"
					}
				}
				$scope.getCssTab = function(part) {
					if (part && part.selected) {
						return "active"
					}
				}
				$scope.create = function() {
					$rootScope.$broadcast("AddSubview", ProductFormView())
				}
			} ]);

})();
