var app = angular.module('commons.directives');
// Table
app.directive('geoform', [ 'AddressService', function(AddressService) {
	return {
		restrict : "E",
		scope : {
			address : "=",
			required : "@"
		},
		replace : true,
		templateUrl : function(element, attr) {
			return attr.template ? attr.template : "geoform.html";
		},
		compile : function(element, attrs) {
			return {
				post : function(scope, element) {
					scope.$watch("address.details", function(n, o) {
						if (n !== o) {
							AddressService.checkGeocode(scope.address)
						}
					})
					scope.$watch("address.forceField", function(n) {
						if (n) {
							AddressService.force(scope.address)
						}
					})
					scope.canIgnore = function() {
						return scope.$eval("address.forceField")
					}
					scope.canIgnoreAlert = function() {
						return scope.canIgnore() && scope.$eval("!address.ignored && address.failed")
					}
					scope.canSearch = function(form) {
						return scope.$eval("address.forceField") && form.$valid
					}
					scope.ignore = function() {
						scope.$eval("address.ignored=true")
					}
					scope.select = function(result) {
						scope.results = [];
						scope.address.details = result;
						AddressService.checkGeocode(scope.address)
					}
					scope.noone = function() {
						scope.$eval("address.failed=true")
						scope.results = [];
					}
					scope.search = function() {
						scope.$eval("show={}")
						AddressService.search(scope.address).then(function(results) {
							scope.results = results;
						}, function(status) {
							scope.$eval("show.failed.search=true")
						})
					}
				}
			}
		},
		controller : function($scope) {

		}
	}
} ]);