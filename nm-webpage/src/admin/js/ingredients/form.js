(function() {
	//
	Wick.DEPENDENCIES.push("ingredients.form");
	var module = angular.module("ingredients.form", [ 'ingredients.service', 'commons.all', 'ui.bootstrap' ]);
	module.controller('IngredientFormController', [ '$scope', '$rootScope', 'Ingredient', 'ScopeService', 'MultiviewService',
			function($scope, $rootScope, Ingredient, ScopeService, MultiviewService) {
				if (MultiviewService.hasParam("edit")) {
					$scope.ingredient = Ingredient.edit({
						id : MultiviewService.getParam("edit")
					})
				} else {
					$scope.ingredient = Ingredient.create()
				}
				$scope.submit = function() {
					ScopeService.hideAll($scope)
					return $scope.ingredient.$save().then(function() {
						$scope.cancel();
						$rootScope.$broadcast("IngredientSaved", $scope.ingredient)
					}, function() {
						ScopeService.show($scope, "saved.fail")
					})
				}
				$scope.cancel = function() {
					$rootScope.$broadcast("RemoveSubview")
				}
				$scope.remove = function() {
					return $scope.ingredient.$remove({
						id : $scope.ingredient.id
					}).then(function() {
						$rootScope.$broadcast("RemoveSubview")
						$rootScope.$broadcast("IngredientDeleted", $scope.ingredient)
					}, function() {
						ScopeService.show($scope, "deleted.fail")
					})
				}
			} ]);
})();
