var app = angular.module('commons.directives');
app.directive('nbprice', function(graphService,priceService) {
	return {
		restrict : 'E',
		scope : {
			price : "="
		},
		templateUrl : 'nbprice.html',
		link : function($scope, element, attrs, controller) {
			$scope.types = priceService.getTypeList()
			$scope.$watch("price.detail", function(val) {
				(!val) && ($scope.price.types = []);
			});
			$scope.$watch("price.product", function(val) {
				$scope.graphAsTable = []
				if (val) {
					graphService.deep($scope.price.product, function(node) {
						$scope.graphAsTable.push(node);
						return true;
					})
				} else {
					console.debug("[nbprice] no graph to design...", $scope.price.product);
				}
			}, true);
			$scope.isTableNodeVisible = function(node) {
				var visible = false;
				graphService.deep(node, function(node) {
					if (node.priceDetail && node.priceDetail.selected) {
						visible = true;
					}
					return true;
				});
				return visible;
			}
			$scope.isRowNodeVisible = function(node) {
				return node.priceDetail && node.priceDetail.selected;
			}
			$scope.onSelectSupplement = function(child) {
				console.debug("[nbprice] selected supplement", child);
				child.priceDetail.selected = true;
				$scope.showChoices = false;
			}
			$scope.deleteSupplement = function(node) {
				node.priceDetail.selected = false;
			}
			$scope.hasSupplements = function() {
				if ($scope.graphAsTable) {
					return $scope.graphAsTable.length > 0;
				} else {
					return false;
				}
			}
			$scope.addSupplement = function() {
				$scope.showChoices = true;
			}
			$scope.reset = function() {
				$scope.showChoices = false;
			};
			$scope.reset();
		}
	};
});