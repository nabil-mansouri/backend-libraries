var app = angular.module('commons.directives');
app.directive('nbradiobi', function() {
	return {
		restrict : 'E',
		scope : {
			setter : '&',
			name : '@'
		},
		controller : function($scope) {
			$scope.set = function(val) {
				$scope.setter()($scope.name, val);
			};
			$scope.isActive = function(val) {
				var temp = $scope.setter()($scope.name);
				console.debug("[nbradiobi] is active?", temp, val)
				return (typeof temp != "undefined") && temp === val;
			};
		},
		templateUrl : "nbradiobi.html"
	};
});