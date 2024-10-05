(function() {
	var cartService = angular.module("cart.service", [ "commons.all", "composer.service" ,"price.service"]);
	// DAO
	cartService.factory('Cart', [ '$resource', function($resource) {
		var url = sprintf('%s/ws/carts/%s', Wick.BASE_URL, Wick.LANG);
		var Cart = $resource(sprintf("%s/", url), {}, {
			check : {
				method : 'POST',
				isArray : false,
				url : sprintf("%s/check", url)
			}
		});
		return Cart;
	} ]);
	cartService.factory('CartDraft', [ 'draftService', function(draftService) {
		var CartDraft = angular.copy(draftService);
		CartDraft.getType = function() {
			return 'Cart';
		}
		return CartDraft.getResource();
	} ]);
	cartService.factory('cartService', [ '$http', 'Cart', '$location', 'entityService', 'urlService', 'dispatcher', 'graphService', 'productService', 'composerService', 'Price', function($http, Cart, $location, entityService, urlService, dispatcher, graphService, productService, composerService, Price) {
		var service = {};
		service.clearComposer = function(cart) {
			if (!cart) {
				cart = service.create();
			}
			delete cart["composer"]
		}
		service.newComposer = function(cart, idProd) {
			if (!cart) {
				cart = service.create();
			}
			console.debug("[cartService] loading product...", idProd)
			var price = Price.compute({
				idProd : idProd,
				type : cart.type,
				resto : cart.restaurant.id
			});
			return price.$promise.then(function() {
				cart.composer = composerService.create(price);
			})
		}
		service.getChecked = function() {
			return AppLocalSession.currentCartCheck;
		}
		service.recompute = function(cart) {
			if (cart.details.length > 0) {
				cart.locked = true;
				AppLocalSession.currentCart = Cart.check(cart);
				AppLocalSession.currentCartCheck = null;
				AppLocalSession.currentCart.$promise.then(function() {
					AppLocalSession.currentCart.locked = false;
					if (AppLocalSession.currentCart.inError) {
						// CLEAN
						AppLocalSession.currentCartCheck = angular.copy(AppLocalSession.currentCart);
						AppLocalSession.currentCart.details = AppLocalSession.currentCart.details.filter(function(cur) {
							return !cur.context.inError;
						})
						urlService.redirectAndPush("/cart/check")
					}
				})
			}
		}
		service.hasRestaurant = function(cart) {
			if (!cart) {
				cart = service.create();
			}
			return (cart && cart.restaurant && cart.restaurant.id)
		}
		service.hasType = function(cart) {
			if (!cart) {
				cart = service.create();
			}
			return (cart && cart.type)
		}
		service.getLast = function() {
			return AppLocalSession.lastCart
		}
		service.reset = function() {
			console.debug("[cartService] resetting cart ...", AppLocalSession.currentCart)
			var cart = angular.copy(AppLocalSession.currentCart)
			AppLocalSession.currentCart = null;
			return cart;
		}
		service.create = function() {
			if (AppLocalSession.currentCart) {
				return AppLocalSession.currentCart;
			}
			var cart = {
				locked : false,
				details : [],
				type : null,
				payments : [],
				validations : {
					type : {
						state : false,
						ok : [],
						nok : []
					}
				}
			};
			AppLocalSession.currentCart = cart;
			console.debug("[cartService] creating order ...", cart)
			return cart;
		};
		service.getProductByGroup = function(cart) {
			if (!cart) {
				cart = service.create();
			}
			var list = {};
			cart.details.forEach(function(cur, ind) {
				if (!list[cur.product.id]) {
					list[cur.product.id] = [];
				}
				list[cur.product.id].push(cur);
			})
			return list;
		}
		service.hasProduct = function(choice) {
			return choice && choice.selected;
		}
		service.getProduct = function(choice) {
			return choice.selected;
		}
		service.isProductVisible = function(part, product) {
			return part && service.hasProduct(part) && entityService.equals(part.selected, product)
		}
		service.isIngredientVisible = function(part, product) {
			return part && part.excluded && entityService.contains(part.excluded, product)
		}
		service.hasIngredients = function(product) {
			var ing = ((product) ? product.excluded : []);
			return ing && ing.length > 0;
		}
		service.needAddress = function(cart) {
			if (!cart) {
				cart = service.create();
			}
			if (service.hasType(cart)) {
				return cart.type == "Delivered";
			}
			return false;
		}
		service.getIngredients = function(product) {
			var ing = (product) ? product.excluded : []
			return ing;
		}
		service.hasPrice = function(node) {
			return node.price && node.price > 0;
		}
		service.getQuantity = function(list, id) {
			return (list[id]) ? list[id].length : 0;
		}
		service.getTotalGroup = function(group) {
			var total = 0;
			group.forEach(function(prodPrice) {
				graphService.setParents(prodPrice.product)
				graphService.deep(prodPrice.product, function(node, isRoot) {
					if (productService.isProductNode(node) && service.hasPrice(node) && service.isProductVisible(node.getParent(), node)) {
						total += node.price;
					} else if (isRoot && productService.isProductNode(node) && service.hasPrice(node)) {
						total += node.price;
					}
					return true;
				});
			})
			return total;
		}
		service.getTotalFromGroups = function(groups) {
			var total = 0;
			for ( var id in groups) {
				total += service.getTotalGroup(groups[id]);
			}
			return total;
		}
		service.pushComposer = function(cart) {
			if (!cart) {
				cart = service.create();
			}
			if (cart.locked) {
				return false;
			}
			cart.details.push({
				product : cart.composer.price.product
			});
			service.clearComposer(cart)
			return true;
		}
		service.increment = function(group, groups, cart) {
			if (cart.locked) {
				return false;
			}
			if (group.length > 0) {
				var prodPrice = group[0];
				var clone = angular.copy(prodPrice)
				group.push(clone);
			}
			service.synchronize(groups, cart);
			return true;
		}
		service.priceHasChanged = function(cart, detail) {
			return detail.contextPrice && detail.contextPrice.changed.length > 0;
		}
		service.decrement = function(group, groups, cart) {
			if (cart.locked) {
				return false;
			}
			if (group.length > 0) {
				group.splice(0, 1)
			}
			service.cleanGroups(groups)
			service.synchronize(groups, cart)
			return true;
		}
		service.remove = function(group, ind, groups, cart) {
			if (cart.locked) {
				return false;
			}
			if (group.length > ind) {
				group.splice(ind, 1)
			}
			service.cleanGroups(groups)
			service.synchronize(groups, cart)
			return true;
		}
		service.synchronize = function(groups, cart) {
			cart.details = [];
			for ( var id in groups) {
				groups[id].forEach(function(cur) {
					cart.details.push(cur);
				})
			}
		}
		service.cleanGroups = function(groups) {
			for ( var id in groups) {
				if (groups[id].length == 0) {
					delete groups[id]
				}
			}
		}
		service.lock = function(cart) {
			if (!cart) {
				cart = service.create();
			}
			cart.locked = true;
		}
		service.unlock = function(cart) {
			if (!cart) {
				cart = service.create();
			}
			cart.locked = false;
		}
		service.hasType = function(cart) {
			return cart.type;
		}
		service.setType = function(cart, type) {
			if (!cart) {
				cart = service.create();
			}
			cart.type = type;
			service.recompute(cart)
		}
		service.getType = function(cart) {
			if (!cart) {
				cart = service.create();
			}
			return cart.type;
		}
		service.setRestaurant = function(cart, resto) {
			if (!cart) {
				cart = service.create();
			}
			cart.restaurant = resto;
			service.recompute(cart)
		}
		service.getRestaurant = function(cart) {
			if (!cart) {
				cart = service.create();
			}
			return cart.restaurant;
		}
		service.canValidate = function(cart) {
			return cart.type && cart.restaurant && cart.details.length > 0;
		}
		service.submit = function(cart) {
			if (!cart) {
				cart = service.create();
			}
			cart.locked = true;
			console.debug("[cartService]submitted so redirecting to /cart/next...")
			$location.path("/cart/next")
		}
		return service;
	} ]);
})();
