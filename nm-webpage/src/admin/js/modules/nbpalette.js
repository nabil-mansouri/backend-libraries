var app = angular.module('commons.directives');
// Palette
app.directive('nbpalette', [ '$filter', function($filter) {
	return {
		restrict : 'A',
		scope : {
			'list' : '=',
			'selected' : '='
		},
		controller : function($scope) {
			if (!$scope.list) {
				$scope.list = []
			}
			if (!$scope.selected) {
				$scope.selected = []
			}
			$scope.unselected = []
			this.unselect = function(index) {
				$scope.selected.splice(index, 1);
				// console.debug("[Palette] Unselecting object :
				// ",index,$scope.selected);
			};
			this.select = function(object) {
				$scope.selected.push(object);
				// console.debug("[Palette] selecting object :
				// ",object,$scope.selected);
			};
			//
			// console.debug("[Palette]Init with current and list
			// ...",$scope.list,$scope.selected);
			//
		}
	};
} ]);
app.directive('nbpalselect', function($timeout) {
	return {
		scope : {
			'nbpalselect' : '=',
		},
		require : '^nbpalette',
		link : function(scope, elem, attrs, controller) {
			elem.on('click', function() {
				$timeout(function() {
					controller.select(scope.nbpalselect)
				}, 0);
			});
		}
	}
});
app.directive('nbpalunselect', function($timeout) {
	return {
		scope : {
			'nbpalunselect' : '=',
		},
		require : '^nbpalette',
		link : function(scope, elem, attrs, controller) {
			elem.on('click', function() {
				$timeout(function() {
					controller.unselect(scope.nbpalunselect)
				}, 0);
			});
		}
	}
});