(function() {
	Wick.DEPENDENCIES.push("client.form");
	//
	var clientForm = angular.module("client.form", [ "cart.service", "client.service", 'commons.all' ]);
	clientForm.controller('ClientCart', [ '$scope', '$location', 'Client', 'cartService', 'clientService', function($scope, $location, Client, cartService, clientService) {
		$scope.cart = cartService.create();
		//
		if (!cartService.canValidate($scope.cart)) {
			console.debug("[ClientCart]cannot submit so redirecting to /cart/next...")
			$location.path("/cart/next")
		}
		$scope.cart.client = clientService.initSafe($scope.cart.client)
		if (!clientService.isNew($scope.cart.client)) {
			$scope.loading = true;
			$scope.cart.client = Client.edit({
				id : $scope.cart.client.id
			});
			$scope.cart.client.$promise.then(function() {
				$scope.loading = false;
			})
		}
		//
		$scope.filters = {
			search : ''
		}
		$scope.geoCodeOk = null;
		$scope.$watch("cart.client.address.details", function(val) {
			$scope.geoCodeOk = clientService.checkGeocode($scope.cart.client)
		})
		$scope.isGeocodeFalse = function() {
			return $scope.geoCodeOk === false;
		}
		$scope.canIgnore = function() {
			return !cartService.needAddress()
		}
		$scope.$watch("filters.search", function(val) {
			if (val && $scope.canAutocomplete()) {
				$scope.clients = Client.filter({
					filter : val,
					limit : 10
				})
			} else {
				$scope.clients = []
			}
		})
		$scope.selectClient = function(client) {
			$scope.cart.client = client;
			$scope.filters.search = "";
		}
		$scope.canAutocomplete = function() {
			return StringUtils.getLength($scope.filters.search) > 2;
		}
		$scope.ignore = function() {
			console.debug("[ClientCart]ignoring /cart/next...")
			$scope.cart.client.ignore = true;
			$location.path("/cart/next")
		}
		$scope.submit = function() {
			console.debug("[ClientCart]submitting /cart/next...")
			$scope.cart.client.ignore = false;
			$location.path("/cart/next")
		}
	} ]);

})();
