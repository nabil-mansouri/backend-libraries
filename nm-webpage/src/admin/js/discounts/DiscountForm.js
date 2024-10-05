(function() {
	Wick.DEPENDENCIES.push("discount.form");
	// console.debug("Application release form:
	// ",document.getElementById("appDiscountForm"));
	var releaseForm = angular.module("discount.form", [ 'discount.service', 'commons.all', 'ui.bootstrap' ]);

	releaseForm.controller('DiscountForm', [ '$scope', '$rootScope', '$http', 'LocaleService', 'anchorSmoothScroll', '$element', '$modal', 'dispatcher', 'discountService', '$timeout', 'entityService', 'feedback', 'productService', 'discountProductService', 'draftService', function($scope, $rootScope, $http, LocaleService, anchorSmoothScroll, $element, $modal, dispatcher, discountService, $timeout, entityService, feedback, productService, discountProductService, draftService) {
		$scope.events = {};
		$scope.discountProductService = discountProductService;
		$scope.productService = productService;
		$scope.paramsProduct = {
			uniqueSelect : true,
			onSelect : function(selected) {
				$scope.discount.product = null;
				if (selected && selected.length > 0) {
					var product = selected[selected.length - 1];
					discountService.setSpecial($scope.discount, product)
				}
			},
			overrides : {
				contains : function(model) {
					return $scope.discount.product && entityService.contains([ $scope.discount.product ], model);
				}
			}
		};
		$scope.hasSpecial = function() {
			return $scope.discount.price && $scope.discount.price.product && $scope.discount.price.product.id;
		}
		$scope.cancelDiscount = function() {
			console.debug("[DiscountForm] cancel release.");
			$scope.reset();
			$scope.panelVisible = false;
			dispatcher.emit("OnDiscountCanceled");
		};

		$scope.submitDiscount = function() {
			discountService.save($scope.discount, function(data) {
				$scope.reset();
				console.debug("[DiscountForm] DiscountForm saved success: ", data);
				dispatcher.broadcast("OnDiscountSaved", data);
			}, function() {
				console.debug("[DiscountForm] DiscountForm saved failed: ", arguments);
			});
		};
		$scope.draftDiscount = function() {
			console.debug("[DiscountForm] draftMainProduct :", $scope.product);
			draftService["save"]($scope.discount, "Discount").then(function(data) {
				$scope.reset();
				anchorSmoothScroll.scrollToElement($element[0]);
				dispatcher.broadcast("OnDiscountDraftAdd", data);
			});
		};
		$scope.$on("OnDiscountDelete", function(event, discount) {
			console.debug("[DiscountForm]Receiving event OnDiscountDelete", discount);
			// FORCE REFRESH
			$timeout(function() {
				if (entityService.equals($scope.discount, discount)) {
					console.warn("[DiscountForm]deleting the current edit discount", discount);
					$scope.reset();
				} else {
					console.debug("[DiscountForm] deleted and actual are not same");
				}
			}, 10);
		});
		$scope.$on("OnDiscountFormCreate", function(event, payload) {
			event && event.stopPropagation && event.stopPropagation();
			console.debug("[DiscountForm] OnDiscountFormCreate. Displaying modal ...", payload);
			var callBack = function() {
				$scope.reset();
				// MUST BE AFTER RESET
				$scope.panelVisible = true;
				if (payload.edit) {
					console.debug("[DiscountForm] OnDiscountFormCreate... Loading edit ", payload.edit);
					discountService.edit(payload.edit.id, function(data) {
						console.debug("[DiscountForm] OnDiscountFormCreate... Edit loaded ! ", data);
						$scope.discount = data;
					});
				}
				$timeout(function() {
					anchorSmoothScroll.scrollToElement($element[0]);
				}, 100);
			};
			//
			console.debug("[DiscountForm] open panel");
			callBack();
		});
		// INIT
		$scope.reset = function() {
			$scope.panelVisible = false;
			$scope.discount = discountService.create();
		};
		$scope.reset();
		//

	} ]);

	releaseForm.controller('DiscountType', [ '$scope', '$rootScope', '$http', function($scope, $rootScope, $http) {
		$scope.set = function(type) {
			$scope.discount.type = type;
		}
		$scope.isActive = function(type) {
			return $scope.discount.type == type;
		}
	} ]);

	releaseForm.controller('DiscountTrigger', [ '$scope', '$rootScope', '$http', 'discountService', function($scope, $rootScope, $http, discountService) {
		$scope.setter = function(type, value) {
			console.debug("[DiscountTrigger] setter", type, value)
			if (typeof value != "undefined") {
				$scope.discount.trigger[type]['show'] = value;
			}
			return $scope.discount.trigger[type]['show'];
		}
		$scope.isVisible = function(type) {
			return $scope.discount.trigger[type]['show'] == true;
		}
		$scope.get = function(type) {
			return $scope.discount.trigger[type];
		}
	} ]);
	releaseForm.controller('DiscountCommunication', [ '$scope', '$rootScope', '$http', 'discountService', function($scope, $rootScope, $http, discountService) {
		$scope.setter = function(type, value) {
			console.debug("[DiscountCommunication] setter", type, value)
			if (typeof value != "undefined") {
				$scope.discount.communication[type]['show'] = value;
			}
			return $scope.discount.communication[type]['show'];
		}
		$scope.isVisible = function(type) {
			return $scope.discount.communication[type]['show'] == true;
		}
		$scope.get = function(type) {
			return $scope.discount.communication[type];
		}
		$scope.onAddLangEmail = function(lang) {
			discountService.addLang($scope.discount, lang, 'Email')
		}
		$scope.onAddLangSms = function(lang) {
			discountService.addLang($scope.discount, lang, 'Sms')
		}
	} ]);

	releaseForm.controller('DiscountFormDecrease', [ '$scope', '$rootScope', '$http', 'graphService', 'priceService', 'entityService', 'discountService', function($scope, $rootScope, $http, graphService, priceService, entityService, discountService) {

		$scope.set = function(type) {
			$scope.decreasetype = type;
		}
		$scope.isActive = function(type) {
			return $scope.decreasetype == type;
		}
		$scope.types = priceService.getTypeList()
		$scope.paramsProductDecrease = {
			onSelect : function(selected, model, isAdd) {
				if (selected) {
					var isNew = false;
					if (isAdd) {
						discountService.addDecrease($scope.discount, model)
					} else {
						discountService.removeDecrease($scope.discount, model)
					}
				} else {
					$scope.discount.prices = {}
				}
			},
			overrides : {
				contains : function(model) {
					return $scope.discount.prices[model.id]
				}
			}
		};
		$scope.getCssClass = function(model) {
			return (model.id) ? "info" : "warning"
		}

		$scope.getResult = function(model, type) {
			return discountService.computeNewPrice(model.product.discountDetails.operations[type], model.product.priceDetail.prices[type], model.product.discountDetails.overrides[type])
		}
		$scope.set = function(type) {
			$scope.discount.decreaserule = type;
		}
		$scope.isActive = function(type) {
			return $scope.discount.decreaserule == type;
		}
		$scope.addRule = function() {
			discountService.addRule($scope.discount)
		}
		$scope.deleteRule = function(index) {
			$scope.discount.rules.splice(index, 1)
		}
	} ]);
	releaseForm.controller('DiscountGift', [ '$scope', '$rootScope', '$http', 'LocaleService', 'anchorSmoothScroll', '$element', '$modal', 'dispatcher', 'discountService', '$timeout', 'entityService', 'feedback', 'discountProductService', 'giftProductService', 'nbSubviewService', function($scope, $rootScope, $http, LocaleService, anchorSmoothScroll, $element, $modal, dispatcher, discountService, $timeout, entityService, feedback, discountProductService, giftProductService, nbSubviewService) {
		$scope.giftProductService = giftProductService;		
		$scope.paramsProduct = {
			uniqueSelect : true,
			onSelect : function(selected, model) {
				$scope.discount.gift = null;
				if (model) {
					$scope.discount.gift = model;
				}
			}
		};

	} ]);
	releaseForm.controller('DiscountFree', [ '$scope', '$rootScope', '$http', 'LocaleService', 'anchorSmoothScroll', '$element', '$modal', 'dispatcher', 'discountService', '$timeout', 'entityService', 'feedback', 'productService', 'giftProductService', 'nbSubviewService', function($scope, $rootScope, $http, LocaleService, anchorSmoothScroll, $element, $modal, dispatcher, discountService, $timeout, entityService, feedback, productService, giftProductService, nbSubviewService) {
		$scope.productService = productService;
		$scope.paramsProduct = {
			uniqueSelect : true,
			onSelect : function(selected, model) {
				$scope.discount.free = null;
				if (model) {
					$scope.discount.free = model;
				}
			}
		};

	} ]);
	// angular.bootstrap(document.getElementById("appDiscountForm"),["discount.form"]);
})();
