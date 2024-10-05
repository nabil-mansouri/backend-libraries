(function() {
	Wick.DEPENDENCIES.push("restaurants.form");
	var module = angular.module("restaurants.form", [ 'restaurants.services', 'commons.all', 'ckedit', 'ui.bootstrap' ]);

	module.controller('RestaurantFormController', [ '$scope', '$rootScope', 'ScopeService', 'Restaurant', 'MultiviewService',
			function($scope, $rootScope, ScopeService, Restaurant, MultiviewService) {
				if (MultiviewService.hasParam("edit")) {
					$scope.resto = Restaurant.edit({
						id : MultiviewService.getParam("edit")
					})
				} else {
					$scope.resto = Restaurant.create()
				}
				$scope.isVisible = function() {
					return $scope.$eval("resto.$resolved && !resto.configError");
				}
				$scope.cancelConfig = function() {
					$rootScope.$broadcast("RemoveSubview")
				}
				$scope.remove = function() {
					return $scope.resto.$remove({
						id : $scope.resto.id
					}).then(function() {
						$rootScope.$broadcast("RemoveSubview")
						$rootScope.$broadcast("RestaurantDeleted", $scope.resto)
					}, function() {
						ScopeService.show($scope, "deleted.fail")
					})
				}
				$scope.cancel = function() {
					$rootScope.$broadcast("RemoveSubview")
				}
				$scope.submit = function() {
					ScopeService.hideAll($scope)
					return $scope.resto.$save().then(function(data) {
						$scope.cancel();
						$rootScope.$broadcast("RestaurantSaved", data)
					}, function() {
						ScopeService.show($scope, "saved.fail")
					})
				}
			} ]);
})();
