var app = angular.module('commons.directives');
// Table
app.directive('loader', [ '$templateCache', function($templateCache) {
	return {
		restrict : "A",
		link : function($scope, element, attrs, controller) {
			var template = $templateCache.get("loader.html")
			$scope.show = function() {
				if (!$scope.element) {
					$scope.element = jQuery(template).clone();
					element.append($scope.element)
				}
			}
			$scope.hide = function() {
				if ($scope.element) {
					$scope.element.remove();
					$scope.element = null;
				}
			}
			$scope.$watch("visible", function(visible) {
				if (visible) {
					$scope.show()
				} else {
					$scope.hide()
				}
			})
		},
		controller : function($scope) {
			this.promise = function(promise) {
				if (typeof promise == "object") {
					$scope.visible = true;
					promise.then(function() {
						$scope.visible = false;
					}, function() {
						$scope.visible = false;
					})
				}
			}
			this.boolean = function(boolean) {
				if (typeof boolean == "boolean") {
					$scope.visible = true;
					if (boolean) {
						$scope.visible = false;
					}
				}
			}
		}
	}
} ]);
app.directive('nbspinner', function($interval) {
	return {
		restrict : "A",
		require : "^loader",
		link : function(scope, element, attrs, ctrl) {
			if (attrs.promise) {
				scope.$watch(function() {
					var func = scope.$eval(attrs.promise);
					if (typeof func == "object") {
						return func;
					}
				}, function(val) {
					ctrl.promise(val)
				})
			}
			if (attrs.boolean) {
				scope.$watch(function() {
					return scope.$eval(attrs.boolean)
				}, function(val) {
					ctrl.boolean(val)
				})
			}
		}
	};
});

app.directive('nbspinnerclick', function() {
	return {
		restrict : "A",
		require : "^loader",
		link : function(scope, element, attrs, ctrl) {
			var click = function(e) {
				var result = scope.$eval(attrs.nbspinnerclick)
				ctrl.promise(result)
			}
			element.on('click', click);
			scope.$on('$destroy', function() {
				element.off('click', click);
			});
		}
	};
});
