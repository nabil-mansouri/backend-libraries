(function() {
	// DAO

	var clientService = angular.module("client.service", [ "commons.all" ]);
	clientService.factory('Client', [ '$resource', '$q', 'crudService', 'entityService', 'configService', 'taxServices', 'productService', 'graphService', function($resource, $q, crudService, entityService, configService, taxServices, productService, graphService) {
		var url = sprintf('%s/ws/client', Wick.BASE_URL, Wick.LANG);
		var Client = $resource(sprintf("%s/:id", url), {
			id : '@id'
		}, {
			fetch : {
				method : 'GET',
				cache : true,
				url : url,
				isArray : true
			},
			filter : {
				method : 'GET',
				cache : true,
				url : sprintf("%s/filter/:filter/:limit", url),
				isArray : true
			},
			edit : {
				method : 'GET',
				url : sprintf("%s/edit/:id", url)
			},
			save : {
				method : 'POST',
				params : {
					id : '@id'
				}
			}
		});
		Client.getBase = function() {
			return url;
		}
		return Client;
	} ]);
	clientService.factory('clientService', [ '$http', '$q', 'LocaleService', 'entityService', 'configService', 'crudService', 'graphService', 'Client', function($http, $q, LocaleService, entityService, configService, crudService, graphService, Client) {
		var service = angular.copy(crudService);
		// Implemented
		service.getName = function() {
			return "ClientService";
		}
		service.getBaseURL = function() {
			return Client.getBase();
		}
		service.getResource = function() {
			return Client;
		}
		service.getName = function() {
			return "client";
		}
		service.create = function() {
			var client = service.newResource()
			client.name = null;
			client.firstname = null;
			client.email = null;
			console.debug("[clientService] creating product ...", client)
			return client;
		};
		service.isNew = function(client) {
			service.initSafe(client)
			return !(client.id)
		}
		service.initSafe = function(client) {
			if (!client) {
				client = {}
			}
			if (!client.address) {
				client.address = {
					geocode : "",
					details : {}
				}
			}
			return client;
		}
		service.checkGeocode = function(client) {
			service.initSafe(client)
			service.components(client);
			var details = client.address.details;
			if (!details || !details.types || details.types.length == 0) {
				return false;
			}
			if (details.types.indexOf("street_address") == -1) {
				return false;
			}
			return true;
		}
		service.components = function(client) {
			service.initSafe(client)
			var details = client.address.details;
			client.address.components = {
				street : "",
				locality : "",
				postal : "",
				country : ""
			};
			if (!details || !details.types || details.types.length == 0) {
				return false;
			}
			if (details.geometry && details.geometry.location) {
				client.address.components.latitude = details.geometry.location.k;
				client.address.components.longitude = details.geometry.location.D;
			}
			details.address_components.forEach(function(cur) {
				if (cur.types.indexOf("street_number") > -1) {
					client.address.components.street += " " + cur.long_name;
				} else if (cur.types.indexOf("route") > -1) {
					client.address.components.street += " " + cur.long_name;
				} else if (cur.types.indexOf("locality") > -1) {
					client.address.components.locality += cur.long_name;
				} else if (cur.types.indexOf("country") > -1) {
					client.address.components.country += cur.long_name;
				} else if (cur.types.indexOf("postal_code") > -1) {
					client.address.components.postal += cur.long_name;
				}
			})
		}
		return service;
	} ]);
	clientService.directive('clientSearch', function(Client) {
		return {
			restrict : 'E',
			require : 'ngModel',
			templateUrl : "client_search.html",
			scope : {
				model : "=ngModel",
				listenerList : "&",
				listener : "&",
				limit : "@",
				minchar : "@"
			},
			link : function($scope, element, attrs, ctrl) {
				$scope.clients = []
				$scope.limit = ($scope.limit) ? $scope.limit : 10;
				$scope.minchar = ($scope.minchar) ? $scope.minchar : 2;
				ctrl.$formatters.push(function(val) {
					if ($scope.lock) {
						$scope.lock = false;
						return val;
					}
					if (val && $scope.canAutocomplete(val)) {
						$scope.clients = Client.filter({
							filter : val,
							limit : $scope.limit
						})
					} else {
						$scope.clients = []
					}
					$scope.listenerList() && $scope.listenerList()($scope.clients)
					return val;
				})
				$scope.select = function(client) {
					$scope.listener()(client);
					$scope.model = $scope.getFullName(client);
					$scope.clients = [];
					$scope.lock = true;
				}
				$scope.getFullName = function(client) {
					return client.name + " " + client.firstname;
				}
				$scope.canAutocomplete = function(val) {
					return StringUtils.getLength(val) > $scope.minchar;
				}
			}
		};
	});
})();
