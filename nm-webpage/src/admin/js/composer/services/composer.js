(function() {
	// DAO

	var orderService = angular.module("composer.service");

	orderService.factory('composerService', [ 'finderService', 'entityService', 'productService', 'historyService', function(finderService, entityService, productService, historyService) {
		var service = {};
		service.create = function(price) {
			if (price) {
				price = angular.copy(price)
			}
			var composer = {
				price : price,
				levels : {},
				currentNode : null,
				histories : [],
				parents : [],
				children : []
			};
			console.debug("[composerService] creating context ...", composer)
			//DO NOT BEGIN WAIT GoToNext
			return composer;
		};
		service.getHistory = function() {
			return historyService;
		}
		service.hasNext = function(composer) {
			return finderService.getNext(composer)
		}
		// Navigation
		service.goToNext = function(composer) {
			console.debug("[composerService]go to next ...")
			var next = finderService.getNext(composer)
			if (next) {
				historyService.begin(composer, next)
			}
			return next;
		}
		service.backTo = function(composer, node) {
			console.debug("[composerService] back to ...")
			historyService.back(composer, node)
			var next = finderService.getNext(composer)
			historyService.begin(composer, node)
		}
		service.visiteTo = function(composer, choice) {
			console.debug("[composerService] visit to ...")
			if (historyService.hasBeenVisited(composer, choice)) {
				historyService.begin(composer, choice)
				return true;
			}
			return false;
		}
		// Product
		service.setProductSelected = function(compose, product, autoback) {
			if (compose.currentNode && productService.isPartNode(compose.currentNode)) {
				if (autoback && historyService.isRevisited(compose)) {
					service.backTo(compose, compose.currentNode)
				}
				var previous = compose.currentNode.selected;
				var isSame = entityService.equals(previous, product)
				compose.currentNode.selected = product;
				if (!isSame) {
					service.setExcludeIngredients(compose.currentNode.selected, [])
				}
			}
			console.debug("[composerService] set product selected ...", compose, compose.currentNode)
		}
		service.isProductSelected = function(compose, prod) {
			return entityService.equals(compose.currentNode.selected, prod)
		}
		service.hasNoProductSelected = function(compose) {
			return compose.currentNode && !compose.currentNode.selected
		}
		// Ingredients
		service.getExcludeIngredients = function(compose) {
			if (compose.currentNode && productService.isProductNode(compose.currentNode)) {
				return compose.currentNode.excluded;
			}
			return [];
		}
		service.setExcludeIngredients = function(product, ing) {
			if (product) {
				product.excluded = ing;
			}
			return [];
		}
		service.hasExcludeIngredients = function(compose, ing) {
			var excl = service.getExcludeIngredients(compose);
			return entityService.contains(excl, ing);
		}
		service.toggleIngredient = function(compose, ingredient) {
			var ings = service.getExcludeIngredients(compose);
			if (entityService.contains(ings, ingredient)) {
				entityService.remove(ings, ingredient)
			} else {
				entityService.addUnique(ings, ingredient)
			}
			return true;
		}
		return service;
	} ]);

})();
