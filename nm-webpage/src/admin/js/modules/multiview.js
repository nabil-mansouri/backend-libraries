(function() {
	var module = angular.module('commons.directives');
	// Table
	module.factory('MultiviewService', [ '$resource', function($resource) {
		var service = {
			setParams : function(params) {
				this.params = params;
			},
			getParam : function(name) {
				var param = this.params[name];
				delete this.params[name];
				return param;
			},
			hasParam : function(name) {
				return this.params && this.params[name];
			}
		}
		return service
	} ]);
	module.directive('multiview', [ '$timeout', 'MultiviewService', function($timeout, MultiviewService) {
		return {
			restrict : "E",
			replace : true,
			scope : {

			},
			templateUrl : function(element, attr) {
				return attr.template ? attr.template : "multiview.html";
			},
			link : function($scope, elem, attrs, controller) {
				$scope.views = [];
				$scope.selected = 0;
				//
				$scope.$on("AddSubview", function(e, view) {
					$scope.add(view)
				})
				$scope.$on("RemoveSubview", function(e) {
					$scope.cancel()
				})
				$scope.add = function(view) {
					$scope.views.push(view)
					$scope.selected = $scope.views.length - 1;
					if (view.params) {
						MultiviewService.setParams(view.params)
					}
				}
				$scope.getCssClass = function(view) {
					return view.cssClass;
				}
				$scope.isSelected = function(index) {
					return $scope.selected == index;
				}
				$scope.canPrevious = function() {
					return 0 < $scope.selected && 1 < $scope.views.length;
				}
				$scope.canNext = function() {
					return $scope.selected < ($scope.views.length - 1) && 1 < $scope.views.length;
				}
				$scope.canCancel = function() {
					return 0 < $scope.views.length;
				}
				$scope.next = function() {
					$scope.selected++;
					$scope.selected = Math.min(($scope.views.length - 1), $scope.selected)
				}
				$scope.previous = function() {
					$scope.selected--;
					$scope.selected = Math.max(0, $scope.selected)
				}
				$scope.cancel = function() {
					$scope.views.splice($scope.selected, 1)
					$scope.previous()
				}
			}
		}
	} ]);
})();