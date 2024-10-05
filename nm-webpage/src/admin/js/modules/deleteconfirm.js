var app = angular.module('commons.directives');
// Table
app.directive('deleteConfirm', [ '$templateCache', '$compile', '$timeout', function($templateCache, $compile, $timeout) {
	return {
		restrict : "A",
		scope : {
			'deleteConfirm' : '&',
			'colspan' : '@',
			'label' : '@',
			'arguments' : "="
		},
		link : function($scope, elem, attrs, controller) {
			elem.on("click", function() {
				$scope.parentTr = elem.parents("tr:first");
				$scope.parentTr.hide();
				var template = $templateCache.get('deleteconfirm.html')
				$scope.elementSaved = angular.element(template);
				$scope.elementSaved.insertAfter($scope.parentTr)
				$compile($scope.elementSaved)($scope);
				$timeout(function() {
					console.debug("Refreshing...")
				})
			});
			$scope.cancel = function() {
				$scope.elementSaved.remove()
				$scope.parentTr.show()
			}
			$scope.confirm = function() {
				$scope.cancel()
				$scope.deleteConfirm()($scope.arguments)
			}
		}
	}
} ]);