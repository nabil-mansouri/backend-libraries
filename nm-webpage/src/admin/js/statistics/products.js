(function() {
	Wick.DEPENDENCIES.push("statistics.products");

	var statController = angular.module("statistics.products", [ 'statistics.service', 'commons.all' ]);

	statController.controller('ProductStat', [ '$scope', '$rootScope', 'ProductStat', 'ProductStatService', 'StatisticService', 'StatTransformer', function($scope, $rootScope, ProductStat, ProductStatService, StatisticService, StatTransformer) {
		$scope.getChartOptions = function() {
			return {

				xaxis : {
					tickLength : 0,
					tickDecimals : 0,
					mode : "categories",
					min : 0,
					font : {
						lineHeight : 18,
						style : "normal",
						variant : "small-caps",
						color : "#6F7B8A"
					}
				},
				yaxis : {
					min : 0,
					ticks : 5,
					tickDecimals : 0,
					tickColor : "#eee",
					font : {
						lineHeight : 14,
						style : "normal",
						variant : "small-caps",
						color : "#6F7B8A"
					},
					color : '#9ACAE6'
				},
				grid : {
					hoverable : true,
					clickable : true,
					tickColor : "#eee",
					borderColor : "#eee",
					borderWidth : 1
				}
			};
		}
		$scope.compute = function() {
			ProductStatService.compute($scope.filter).$promise.then(function(data) {
				var result = [];
				StatisticService.transform(data, StatTransformer).forEach(function(datas) {
					result.push({
						data : datas,
						points : {
							show : true,
							fill : true,
							radius : 4,
							fillColor : "rgba(154,202,230,0.25)",
							lineWidth : 2
						},
						lines : {
							show : true,
							fill : true,
							fillColor : "rgba(154,202,230,0.25)",
							lineWidth : 3
						},
						color : '#9ACAE6',
						shadowSize : 1
					});
				})
				$scope.result = result
				$scope.chartOpt = $scope.getChartOptions()
			})
		}
		// INIT
		$scope.reset = function() {
			$scope.result = null;
			$scope.filter = {
				measure : [ "CountProduct" ],
				period : "Day",
				products : []
			};
			$scope.compute()
		};
		$scope.$on("OnProductStat", function(e, product) {
			$scope.filter.products.length=0;
			$scope.filter.products.push(product.id)
			$scope.compute()
		})
		$scope.reset();
	} ]);

	statController.controller('ProductStatClass', [ '$scope', '$rootScope', 'ProductStat', 'ProductStatService', 'StatisticService', 'StatTransformer', function($scope, $rootScope, ProductStat, ProductStatService, StatisticService, StatTransformer) {
		$scope.getChartOptions = function() {
			return {
				series : {
					bars : {
						show : true,
						barWidth : 0.6,
						align : "center"
					}
				},
				xaxis : {
					tickLength : 0,
					mode : "categories"
				},
				yaxis : {
					min : 0,
					ticks : 5,
					tickDecimals : 0,
					tickColor : "#eee",
					font : {
						lineHeight : 14,
						style : "normal",
						variant : "small-caps",
						color : "#6F7B8A"
					}
				},
				grid : {
					hoverable : true,
					clickable : true,
					tickColor : "#eee",
					borderColor : "#eee",
					borderWidth : 1
				}
			};
		}
		$scope.compute = function() {
			ProductStatService.computeClass($scope.filter.measure, $scope.filter).$promise.then(function(data) {
				$scope.result = StatisticService.transform(data, StatTransformer)
				$scope.chartOpt = $scope.getChartOptions()
			})
		}
		// INIT
		$scope.reset = function() {
			$scope.result = null;
			$scope.filter = {
				measure : "BestCount",
				limit : 5,
				period : "Month",
			};
			$scope.compute()
		};
		$scope.reset();
	} ]);

})();
