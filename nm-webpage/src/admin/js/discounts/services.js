(function() {
	var discountService = angular.module("discount.service", [ "commons.all", "product.service", "price.service" ]);
	discountService.factory('Discount', [ '$resource', function($resource) {
		var url = sprintf("%s/ws/discounts/%s", Wick.BASE_URL, Wick.LANG);
		var Discount = $resource(sprintf("%s/:id", url), {
			id : '@id'
		}, {
			fetch : {
				cache : true,
				isArray : true
			},
			get : {
				method : 'GET',
				cache : true
			},
			edit : {
				method : 'GET',
				url : sprintf("%s/edit/:id", url)
			}
		});
		Discount.getBase = function() {
			return url;
		}
		return Discount;
	} ]);

	discountService.factory('discountService', [ '$http', '$q', 'crudService', 'priceService', 'configService', 'entityService', 'Discount', 'productService', 'Price', function($http, $q, crudService, priceService, configService, entityService, Discount, productService, Price) {
		var service = angular.copy(crudService);
		// Implemented
		service.getName = function() {
			return "DiscountService";
		}
		service.getBaseURL = function() {
			return Discount.getBase();
		}
		service.getResource = function() {
			return Discount;
		}
		//
		service.getTriggers = function() {
			return [ 'DateBegin', 'DateEnd', 'DateEq', 'PeriodLimit', 'NbTimes', 'NbUserAbs', 'Cumulable', 'Birthday', 'BirthdaySign', 'Period', 'OnSignin', 'OnLogin', 'Parrain', 'Filleul', 'Fidelity', 'UserGroupe', 'NbCommande', 'Depense', 'OnCommande', 'OnProduit', 'Restaurants', 'Geoloc' ]
		}
		service.addRule = function(discount) {
			discount.rules.push({})
		}
		service.create = function() {
			var discount = new Discount();
			discount.name = null;
			discount.type = null;
			// Decrease
			discount.prices = {};
			// Special
			discount.decreaserule = null;
			discount.rules = [];
			discount.product = null;
			discount.price = null;
			// Gift and free
			discount.gift = null;
			discount.free = null;
			//
			discount.trigger = {};
			discount.communication = {
				'Code' : {},
				'AutoComm' : {}
			}
			// Add langs
			var values = configService.getSync(AppConfig.Langs, [ Wick.LANG ]);
			for ( var i = 0; i < values.length; i++) {
				service.addLang(discount, values[i]);
			}
			// Add rules and price
			service.addRule(discount)
			discount.price = priceService.create()
			// Add triggers
			service.getTriggers().foreach(function(trigger, index) {
				discount.trigger[trigger] = {
					show : '',
					visible : (index == 0)
				}
			});
			return discount;
		};
		service.setSpecial = function(discount, product) {
			discount.price = priceService.create(product)
		}
		service.removeDecrease = function(discount, product) {
			delete discount.prices[product.id];
		};
		service.addDecrease = function(discount, product) {
			var price = Price.flexible({
				idProduct : product.id
			})
			price.$promise.then(function() {
				price.product.discountDetails.selected = true;
			})
			discount.prices[product.id] = price;
		};
		service.getCommunicationTypes = function() {
			return [ 'Email', 'Sms' ]
		}
		service.getTriggersTypes = function() {
			return [ 'DateEq', 'DateBegin', 'DateEnd', 'Period', 'PeriodLimit',//
			'NbTimes', 'NbUserAbs', 'Cumulable',//
			'Birthday', 'BirthdaySign', 'OnSignin', 'OnLogin', //
			'Parrain', 'Filleul', 'Fidelity',//
			'UserGroupe', 'NbCommande', 'Depense', 'OnCommande', 'OnProduit',//
			'Restaurants', 'Geoloc' ]
		}
		service.addLang = function(discount, lang, type) {
			var loadType = function(type) {
				if (!discount.communication[type]) {
					discount.communication[type] = {
						'contents' : []
					}
				}
				discount.communication[type]['contents'].push({
					lang : lang,
					description : "",
					descriptionText : ""
				})
			}
			if (type) {
				loadType(type);
			} else {
				service.getCommunicationTypes().forEach(function(type) {
					loadType(type);
				})
			}
			console.debug("[discountService]adding lang ...", discount, lang)
			return discount;
		};
		service.computeNewPrice = function(operation, old, value) {
			var res = 0;
			try {
				switch (operation) {
					case 'Manual':
						return value;
					case 'Free':
						return 0;
					case 'Percentage':
						res = old - (old * value / 100);
						break;
					case 'Fixe':
						res = old - value;
						break;
				}
			} catch (e) {
				console.error(e)
			}
			return (res > 0) ? res : 0;
		}
		return service;
	} ]);
	// Extended service
	discountService.factory('discountProductService', [ 'productService', function(productService) {
		var service = angular.copy(productService);
		service.getProductType = function() {
			return 'Promo';
		};
		service.getDao = function() {
			return ProductPromoDao;
		}
		service.getName = function() {
			return "promoProduct";
		}
		return service;
	} ]);
	discountService.factory('giftProductService', [ 'productService', function(productService) {
		var service = angular.copy(productService);
		service.getProductType = function() {
			return 'Gift';
		};
		service.getDao = function() {
			return ProductGiftDao;
		}
		service.getName = function() {
			return "gift";
		}
		return service;
	} ]);
})();
