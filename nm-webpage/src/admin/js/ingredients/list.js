(function() {
	//
	Wick.DEPENDENCIES.push("ingredients.list");
	var module = angular.module("ingredients.list", [ 'ingredients.service', 'products.constants', 'commons.all', 'ui.bootstrap' ]);
	module.directive('subingredients', function($rootScope, $controller) {
		return {
			restrict : 'E',
			scope : {
				product : "=",
				ingredients : "="
			},
			templateUrl : 'subform_ingredients.html',
			link : function($scope, element, attrs, controller) {
				$controller("IngredientsListController", {
					$scope : $scope
				})
			}
		};
	});
	module.controller('IngredientsListController', [ '$scope', '$rootScope', 'IngredientFormView', function($scope, $rootScope, IngredientFormView) {
		$scope.create = function() {
			$rootScope.$broadcast("AddSubview", IngredientFormView())
		}
		$scope.edit = function(node, $event) {
			$event && $event.stopPropagation()
			$rootScope.$broadcast("AddSubview", IngredientFormView({
				params : {
					edit : node.id
				}
			}))
		}
		$scope.getCssClass = function() {
			if (!$scope.$eval("product.hasIngredients")) {
				return "nbdisbaled"
			}
		}
		$scope.hasAtLeastOne = function() {
			if ($scope.ingredients) {
				var ok = false;
				$scope.ingredients.forEach(function(val) {
					ok = val.selected || ok;
				})
				return ok;
			}
		}
		$scope.toggle = function(ing) {
			ing.facultatif = !ing.facultatif;
		}
	} ]);

})();
