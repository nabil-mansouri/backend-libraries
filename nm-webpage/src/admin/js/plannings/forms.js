(function() {
	//
	Wick.DEPENDENCIES.push("plannings.forms");
	var planning = angular.module("plannings.forms", [ 'plannings.services', 'commons.all', 'ui.calendar', 'ui.bootstrap', 'mgcrea.ngStrap' ]);
	planning.controller('PlanningFormController', [ '$scope', 'Slot', function($scope, Slot) {
		$scope.reset = function() {
			$scope.slot = new Slot();
			$scope.slot.hasNoLimit = true;
		}
		$scope.$watch("slot.dateBeginHoraire", function() {
			$scope.slot.dateEndHoraire = null;
		})
		$scope.$watch("slot.dateBeginPlan", function() {
			$scope.slot.dateEndPlan = null;
		})
		$scope.reset();
	} ]);
	planning.controller('PlanningFormRecurrentController', [ '$scope', '$controller', 'Slot', '$rootScope',
			function($scope, $controller, Slot, $rootScope) {
				$controller("PlanningFormController", {
					$scope : $scope
				})
				$scope.submit = function() {
					$scope.slot.type = 'Recurrent';
					$scope.slot.typeOfSlot = 'RestaurantOpen';
					var promise = Slot.save({
						idPlanning : $scope.getPlanningId()
					}, $scope.slot).$promise;
					promise.then(function(data) {
						$scope.reset();
						$rootScope.$broadcast("ReloadEvents")
					}, function() {
						$rootScope.$broadcast("ReloadEvents")
					});
					return promise;
				};
			} ]);
	planning.controller('PlanningFormCloseController', [ '$scope', '$controller', 'Slot', '$rootScope',
			function($scope, $controller, Slot, $rootScope) {
				$controller("PlanningFormController", {
					$scope : $scope
				})
				$scope.submit = function() {
					$scope.slot.type = 'Exceptionnal';
					$scope.slot.typeOfSlot = 'RestaurantClose';
					var promise = Slot.save({
						idPlanning : $scope.getPlanningId()
					}, $scope.slot).$promise;
					promise.then(function(data) {
						$scope.reset();
						$rootScope.$broadcast("ReloadEvents")
					}, function() {
						$rootScope.$broadcast("ReloadEvents")
					});
					return promise;
				};
			} ]);
	planning.controller('PlanningFormOpenController', [ '$scope', '$controller', 'Slot', '$rootScope',
			function($scope, $controller, Slot, $rootScope) {
				$controller("PlanningFormController", {
					$scope : $scope
				})
				$scope.submit = function() {
					$scope.slot.type = 'Exceptionnal';
					$scope.slot.typeOfSlot = 'RestaurantOpen';
					var promise = Slot.save({
						idPlanning : $scope.getPlanningId()
					}, $scope.slot).$promise;
					promise.then(function(data) {
						$scope.reset();
						$rootScope.$broadcast("ReloadEvents")
					}, function() {
						$rootScope.$broadcast("ReloadEvents")
					});
					return promise;
				};
			} ]);
})();
