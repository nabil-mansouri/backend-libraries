(function() {
	//
	Wick.DEPENDENCIES.push("categories.form");
	var module = angular.module("categories.form", [ 'categories.service', 'commons.all', 'ui.bootstrap' ]);
	module.controller('CategoryFormController', [ '$scope', '$rootScope', 'Category', 'ScopeService', 'MultiviewService',
			function($scope, $rootScope, Category, ScopeService, MultiviewService) {
				if (MultiviewService.hasParam("parent")) {
					$scope.category = Category.create({
						id : MultiviewService.getParam("parent")
					})
				} else if (MultiviewService.hasParam("edit")) {
					$scope.category = Category.edit({
						id : MultiviewService.getParam("edit")
					})
				} else {
					$scope.category = Category.create()
				}
				$scope.submit = function() {
					ScopeService.hideAll($scope)
					return $scope.category.$save().then(function() {
						$scope.cancel();
						$rootScope.$broadcast("CategorySaved", $scope.category)
					}, function() {
						ScopeService.show($scope, "saved.fail")
					})
				}
				$scope.cancel = function() {
					$rootScope.$broadcast("RemoveSubview")
				}
				$scope.remove = function() {
					return $scope.category.$remove({
						id : $scope.category.id
					}).then(function() {
						$rootScope.$broadcast("RemoveSubview")
						$rootScope.$broadcast("CategoryDeleted", $scope.category)
					}, function() {
						ScopeService.show($scope, "deleted.fail")
					})
				}
			} ]);
})();
