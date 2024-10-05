(function() {
	var orderService = angular.module("composer.service");

	orderService.factory('finderService', [ 'graphService', 'productService', 'historyService', 'entityService', function(graphService, productService, historyService, entityService) {
		var service = {};
		service.getExplorer = function(current, children) {
			console.debug("[finderService] current node is a part or a product selected or a root (current/parent)? ...")
			if (productService.isPartNode(current)) {
				return true;
			} else if (productService.isProductNode(current) && current.getParent()) {
				var choice = current.getParent();
				return service.isProductSelected(choice, current)
			} else {
				return false;
			}
		};
		service.getNextIngredient = function(composer) {
			var nextNode = false;
			// Continue only if part or product selected
			var call = function(node) {
				// Stop on first product with parent not visited
				console.debug("[finderService] this node is product or ingredient? ...", node)
				// && node.facultatif
				if (productService.isIngredientNode(node)) {
					console.debug("[finderService] this node has been visited? ...")
					if (historyService.hasBeenVisited(composer, node.getParent())) {
						return true; // Continue
					} else {
						console.debug("[finderService] founded next node (parent/child) ...")
						nextNode = node.getParent();
						return false;
					}
				}
				return true;
			}
			// Exclude root
			// Fix : refresh getParent reference...
			graphService.setParents(composer.price.product)
			graphService.deep(composer.price.product, call, true, service.getExplorer);
			return nextNode;
		}
		service.getNextPart = function(composer) {
			var nextNode = false;
			var call = function(node) {
				// Stop on first product with parent not visited
				console.debug("[finderService] this node is product or ingredient? ...", node)
				if (productService.isProductNode(node)) {
					console.debug("[finderService] this node is a root? ...")
					console.debug("[finderService] this node has been visited? ...")
					// Other case
					if (historyService.hasBeenVisited(composer, node.getParent())) {
						return true; // Continue
					} else {
						console.debug("[finderService] founded next node (parent/child) ...")
						nextNode = node.getParent();
						return false;
					}
				}
				return true;
			}
			// Exclude root
			// Fix : refresh getParent reference...
			graphService.setParents(composer.price.product)
			graphService.deep(composer.price.product, call, true, service.getExplorer);
			return nextNode;
		}
		service.getNext = function(composer) {
			// Find ingredient
			var founded = service.getNextIngredient(composer)
			if (founded) {
				return founded;
			}
			// If no ingredient find part
			return service.getNextPart(composer);
		}
		service.isProductSelected = function(choice, product1) {
			if (choice) {
				var product2 = choice.selected;
				return entityService.equalsOrNull(product1, product2);
			} else {
				return false;
			}
		}
		return service;
	} ]);

})();
