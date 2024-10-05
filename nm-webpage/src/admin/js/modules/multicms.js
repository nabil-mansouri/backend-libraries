var app = angular.module('commons.directives');
// Table
app.directive('multicms', [ '$q', function($q) {
	return {
		restrict : "E",
		scope : {
			cms : "="
		},
		replace : true,
		templateUrl : function(element, attr) {
			return attr.template ? attr.template : "multicms.html";
		},
		link : function($scope, elem, attrs, controller) {
			$scope.forms = {}
			$scope.label = {
				name : attrs["labelName"],
				desc : attrs["labelDesc"],
				keywords : attrs["labelKeywords"]
			}
			$scope.required = {
				name : $scope.$eval(attrs["requiredName"]),
				desc : $scope.$eval(attrs["requiredDesc"]),
				keywords : $scope.$eval(attrs["requiredKeywords"])
			}
			$scope.show = {
				name : $scope.$eval(attrs["showName"]),
				desc : $scope.$eval(attrs["showDesc"]),
				keywords : $scope.$eval(attrs["showKeywords"])
			}
			$scope.getCssSelected = function(content) {
				if (content.selected) {
					return "active"
				} else {
					return ""
				}
			}
			$scope.select = function(content) {
				$scope.cms.contents.forEach(function(c) {
					c.selected = false;
				})
				content.selected = true;
			}

			$scope.pushForm = function(form, index) {
				$scope.forms[index] = form;
			}
		}
	}
} ]);
