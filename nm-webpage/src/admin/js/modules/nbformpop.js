var app = angular.module('commons.directives');
// 18 Config
app.directive('nbformpopover', [ '$timeout', function($timeout) {
	return {
		restrict : 'E',
		require : '^form',
		replace : true,
		scope : {
			'field' : '@',
			'i18prefix' : '@',
		},
		templateUrl : "formstate.html",
		link : function($scope, element, attrs, form) {
			// Init
			console.debug("[FormPopover] form ...", form, form[$scope.field], $scope.field, attrs)
			var loadRules = function() {
				$scope.rows = []
				for ( var prop in form[$scope.field].$error) {
					var rule = form[$scope.field].$error[prop];
					var row = {
						txt : $scope.i18prefix + "." + prop,
						error : rule
					}
					$scope.rows.push(row)
				}
				console.debug("[FormPopover] loaded rules ...", form, form[$scope.field], $scope.rows)
			}
			// visible
			var promise = null;
			$scope.visible = false;
			var setVisible = function() {
				$scope.visible = true;
				loadRules()
				console.debug('[FormPopover] model has changed ... ', $scope.field, $scope.rows);
				if (promise != null) {
					// Stop the pending timeout
					$timeout.cancel(promise);
				}
				promise = $timeout(function() {
					$scope.visible = false;
				}, 1000);
			}
			// Parser
			form[$scope.field].$parsers.push(function(val) {
				console.log("[FormPopover]Changed push ", arguments)
				setVisible()
				return val;
			});
			form[$scope.field].$formatters.push(function(val) {
				console.log("[FormPopover]Changed format ", arguments)
				setVisible()
				return val;
			});

		}
	};
} ])