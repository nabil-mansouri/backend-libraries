(function() {
	Wick.DEPENDENCIES.push("order.detail");
	var cashForm = angular.module("order.detail", [ "client.service", 'restaurant.service', 'commons.all', "order.service" ]);
	cashForm.controller('OrderDetail', [ '$scope', '$rootScope', '$location', 'Order', 'orderService', 'anchorSmoothScroll', '$element', function($scope, $rootScope, $location, Order, orderService, anchorSmoothScroll, $element) {
		$scope.states = [ 'Paid', 'Prepared', 'Served', 'Delivered', 'Refunded', 'Returned', 'Changed' ]
		$scope.reset = function() {
			$scope.visible = false;
		}
		$scope.reset()
		$scope.$on("OnOrderDetail", function(e, order) {
			$scope.reset()
			$scope.order = order
			$scope.visible = true;
			anchorSmoothScroll.scrollToElement($element[0]);
		})

	} ]).controller('AddressMapCtrl', function($scope) {
		$scope.init = function(address) {
			$scope.zoom = 15;
			var lng = address.details.geometry.location.D;
			var lat = address.details.geometry.location.k;
			$scope.center = new google.maps.LatLng(lat, lng);
			$scope.latlon = {
				lng : lng,
				lat : lat
			};
			$scope.markers = [ {
				name : address.name,
			} ];
			$scope.options = {
				map : {
					center : new google.maps.LatLng(lat, lng),
					zoom : $scope.zoom,
					mapTypeId : google.maps.MapTypeId.TERRAIN
				},
				volcanoes : {
					icon : 'https://maps.gstatic.com/mapfiles/ms2/micons/red-dot.png',
				}
			};
		}
		$scope.$watch('center', function(center) {
			if (center) {
				$scope.centerLat = center.lat();
				$scope.centerLng = center.lng();
			}
		});

		$scope.updateCenter = function(lat, lng) {
			$scope.center = new google.maps.LatLng(lat, lng);
		};
	}).directive('orderStateflow', [ 'Order', '$rootScope', 'orderService', function(Order, $rootScope, orderService) {
		return {
			restrict : 'E',
			scope : {
				message : "@",
				messageBegin : "@",
				stateBegin : "@",
				state : "@",
				order : "="
			},
			templateUrl : "order-stateflow.html",
			link : function($scope, element, attrs, controller) {
				$scope.isVisible = function() {
					return orderService.isInStateFlow($scope.order, $scope.state);
				}
				$scope.isBeginVisible = function() {
					return $scope.stateBegin && !orderService.isPastState($scope.order, $scope.stateBegin);
				}
				$scope.getClass = function() {
					if ($scope.isBeginVisible()) {
						if (orderService.isNextState($scope.order, $scope.stateBegin, $scope.state)) {
							return "btn-danger";
						} else {
							return "btn-default";
						}
					} else {
						if (orderService.isPastState($scope.order, $scope.state)) {
							return "btn-success"
						} else if (orderService.isNextState($scope.order, $scope.stateBegin, $scope.state)) {
							return "btn-warning";
						} else {
							return "btn-default";
						}
					}
				}
				$scope.isDisabled = function() {
					return !(orderService.isNextState($scope.order, $scope.stateBegin, $scope.state));
				}
				$scope.selectStateBegin = function() {
					Order.pushState({
						id : $scope.order.idOrder,
						state : $scope.stateBegin
					}).$promise.then(function(data) {
						$rootScope.$broadcast("OnOrderDetail", data)
					})
				}
				$scope.selectState = function() {
					Order.pushState({
						id : $scope.order.idOrder,
						state : $scope.state
					}).$promise.then(function(data) {
						$rootScope.$broadcast("OnOrderDetail", data)
					})
				}
			}
		};
	} ]);

})();
