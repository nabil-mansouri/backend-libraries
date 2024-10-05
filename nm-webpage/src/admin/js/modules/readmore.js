var app = angular.module('commons.directives');
// Table
app.directive('readmore', [ '$templateCache', function($templateCache) {
	return {
		restrict : "A",
		compile : function(element, attrs) {
			return {
				post : function(scope, element) {
					element.on("click", function() {
						element.toggleClass("active")
					})
				}
			}
		},
		controller : function($scope) {

		}
	}
} ]);