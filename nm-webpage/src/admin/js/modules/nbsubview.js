var app = angular.module('commons.directives');
// Table
// SUBVIEW
app.factory('nbSubviewService', [ '$timeout', '$compile', '$rootScope', function($timeout, $compile, $rootScope) {
	var service = {};
	service.all = [];
	service.base = jQuery(".subviews");
	service.setBase = function(base) {
		service.base = base;
	}
	service.closeCurrent = function() {
		var current = null;
		if (!service.isEmpty()) {
			current = service.all.pop();
			current.content.remove()
			current.onClose && current.onClose(current)
		}
		if (service.isEmpty()) {
			service.base.hide()
		}
		return current;
	}
	service.hideCurrent = function() {
		var current = null;
		if (!service.isEmpty()) {
			current = service.getCurrent();
			current.content.hide()
			current.onHide && current.onHide(current)
		}
		if (service.isEmpty()) {
			service.base.hide()
		}
		return current;
	}
	service.showCurrent = function() {
		if (service.isEmpty()) {
			service.base.hide()
		} else {
			service.base.show()
			var current = service.getCurrent()
			current.content.show();
			current.onShow && current.onShow(current);
			current.onInvest && current.onInvest(current);
			$rootScope.$broadcast("OnShowSubviewEnd", current)
		}
	}
	service.back = function() {
		var current = service.closeCurrent()
		service.showCurrent()
		return current;
	}
	service.isEmpty = function() {
		return service.all.length == 0;
	}
	service.hasMany = function() {
		return service.all.length > 1;
	}
	service.close = function() {
		while (service.all.length > 0) {
			service.closeCurrent()
		}
	}
	service.add = function(payload) {
		service.hideCurrent();
		//
		var clone = jQuery(payload.content).clone();
		payload.content = payload.onCompile(clone.html(), payload);
		// Compile before inserting
		service.base.find(".subviews-container").append(payload.content);
		service.all.push(payload)
		service.showCurrent();
	}
	service.getCurrent = function() {
		return service.all[service.all.length - 1]
	}
	return service;
} ]);
app.directive('nbSubviewcontent', [ '$rootScope', 'dispatcher', '$timeout', '$compile', 'nbSubviewService', 'anchorSmoothScroll', function($rootScope, dispatcher, $timeout, $compile, nbSubviewService, anchorSmoothScroll) {
	return {
		restrict : 'E',
		templateUrl : 'subview.html',
		replace : true,
		link : function($scope, element, attr) {
			element.hide();
			nbSubviewService.setBase(element)
			$scope.canBack = function() {
				return nbSubviewService.hasMany()
			}
			$scope.back = function() {
				console.debug("[nbSubviewmenu] backing subview...")
				nbSubviewService.back()
			}
			$scope.close = function() {
				console.debug("[nbSubviewmenu] closing subview...")
				nbSubviewService.close();
			}
			$scope.show = function(payload) {
				console.debug("[nbSubviewmenu] receiving on show ...")
				payload.onInvest = function(payload) {
					$timeout(function() {
						$scope.title = payload.title;
						$scope.subtitle = payload.subtitle;
						$timeout(function() {
							anchorSmoothScroll.scrollToElement(element[0]);
						}, 100);
					})
				}
				payload.onCompile = function(content, payload) {
					payload.scope = $rootScope.$new();
					payload.onOverrides && payload.onOverrides(payload.scope, payload)
					return $compile(content)(payload.scope);
				}
				nbSubviewService.add(payload);
			}
			var callBack = function(e, payload) {
				$scope.show(payload)
			};
			$scope.$on("OnSubViewShow", callBack);
			$scope.$on("OnSubViewHide", function() {
				$scope.close()
			});
		}
	};
} ])
app.directive('nbSubview', [ 'dispatcher', '$compile', '$rootScope', '$timeout', function(dispatcher, $compile, $rootScope, $timeout) {
	return {
		scope : {
			'close' : '&',
			'show' : '&',
			'hide' : '&',
			'overrides' : '&',
			'arguments' : '=',
			'title' : '@',
			'subtitle' : '@',
			'nbSubview' : '@',
		},
		link : function($scope, element, attr) {
			console.debug("[nbSubview] loading ...")
			$scope.subviewid = UUID.generate();
			var params = {};
			params.content = "#" + $scope.nbSubview;
			params.onClose = $scope.close();
			params.title = $scope.title;
			params.subtitle = $scope.subtitle;
			params.arguments = $scope.arguments;
			params.onShow = $scope.show();
			params.onHide = $scope.hide();
			params.onOverrides = $scope.overrides();
			jQuery(element).on("click", function() {
				$rootScope.$broadcast("OnSubViewShow", angular.copy(params));
			})
		}
	};
} ])