var app = angular.module('commons.directives');
app.directive('nbtab', [ 'LocaleService', 'configService', function(LocaleService, configService) {
	return {
		restrict : 'E',
		transclude : true,
		scope : {
			'factory' : '&',
			'list' : '=',
			'parent' : '=',
			'current' : '=',
			'factoryname' : '&'
		},
		templateUrl : "tab.html",
		link : function($scope, element, attrs) {
			var autoSelect = function() {
				if (!$scope.list || $scope.list.length == 0) {
					$scope.list = []
					$scope.add();
				}
				if (!$scope.currentIndex) {
					$scope.select(0)
				}
			}
			$scope.$watch("list", function(val) {
				autoSelect()
			})
			$scope["delete"] = function(index) {
				$scope.list.splice(index, 1);
				$scope.select(0)
			};
			//
			$scope.isVisible = function(index) {
				if ($scope.currentIndex != null) {
					return $scope.currentIndex == index;
				}
				return false;
			}
			$scope.select = function(index) {
				$scope.currentIndex = index;
				$scope.current = $scope.list[index];
			};
			$scope.getName = function(index) {
				return $scope.factoryname()(index + 1);
			};
			$scope.add = function() {
				// console.debug("[Tab] Adding tab...");
				var temp = $scope.factory()()
				$scope.list.push(temp)
				var index = $scope.list.length - 1;
				$scope.select(index);
			};
			//
			// console.debug("[Tab]Init with current and list
			// ...",$scope.list,$scope.current);
			//
		}
	};
} ])