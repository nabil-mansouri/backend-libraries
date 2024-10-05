(function() {
	'use strict';
	var app = angular.module('commons.services');
	// Countries
	app.factory('AddressService', [ '$q', function($q) {
		var service = {};
		service.geocoder = new google.maps.Geocoder();
		service.search = function(address) {
			return $q(function(resolve, reject) {
				address.ignored = false;
				var addr = "";
				addr += address.components.street + " ";
				addr += address.components.postal + " ";
				addr += address.components.locality + " ";
				addr += address.components.country + " ";
				service.geocoder.geocode({
					'address' : addr
				}, function(results, status) {
					if (status === google.maps.GeocoderStatus.OK) {
						resolve(results);
					} else {
						reject(status);
					}
				});
			})
		}
		service.simulate = function(address) {
			if (!address) {
				return;
			}
			if (address.geocode) {
				address.geocode = address.geocode.trim() + " "
			} else {
				address.geocode = " "
			}
		}
		service.force = function(address) {
			address.components = {
				street : "",
				locality : "",
				postal : "",
				country : ""
			};
			address.details = {};
			address.failed = true;
		}
		service.checkGeocode = function(address) {
			address.ignored = false;
			service.components(address);
			var details = address.details;
			if (!details || !details.types || details.types.length == 0) {
				address.failed = true;
			} else {
				address.failed = false;
			}
			return !address.failed;
		}
		service.components = function(address) {
			var details = address.details;
			address.components = {
				street : "",
				locality : "",
				postal : "",
				country : ""
			};
			if (!details || !details.types || details.types.length == 0) {
				return false;
			}
			if (details.geometry && details.geometry.location) {
				if (details.geometry.location.k) {
					address.components.latitude = details.geometry.location.k;
				} else if (details.geometry.location.K) {
					address.components.latitude = details.geometry.location.K;
				} else if (details.geometry.location.lat) {
					address.components.latitude = details.geometry.location.lat;
				}
				//
				if (details.geometry.location.D) {
					address.components.longitude = details.geometry.location.D;
				} else if (details.geometry.location.G) {
					address.components.longitude = details.geometry.location.G;
				} else if (details.geometry.location.lng) {
					address.components.longitude = details.geometry.location.lng;
				}
			}
			details.address_components.forEach(function(cur) {
				if (cur.types.indexOf("street_number") > -1) {
					address.components.street += " " + cur.long_name;
				} else if (cur.types.indexOf("route") > -1) {
					address.components.street += " " + cur.long_name;
				} else if (cur.types.indexOf("locality") > -1) {
					address.components.locality += cur.long_name;
				} else if (cur.types.indexOf("country") > -1) {
					address.components.country += cur.long_name;
				} else if (cur.types.indexOf("postal_code") > -1) {
					address.components.postal += cur.long_name;
				}
			})
		}
		return service;
	} ]);
}());