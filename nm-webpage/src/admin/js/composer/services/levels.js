(function() {
	// DAO

	var orderService = angular.module("composer.service");

	orderService.factory('levelService', [ 'historyService', 'graphService', 'productService', 'composerService', 'entityService', function(historyService, graphService, productService, composerService, entityService) {
		var service = {};
		service.buildLevels = function(composer) {
			var levels = {}
			// Continue only if part or product selected
			var explorer = function(current, children) {
				if (productService.isPartNode(current)) {
					if (historyService.hasBeenVisited(composer, current)) {
						var isCur = historyService.isCurrent(composer, current)
						var isParent = historyService.isParentOfCurrent(composer, current)
						var isChild = historyService.isChildOfCurrent(composer, current)
						return isCur || isParent || isChild;
					} else {
						return false;
					}

				} else if (productService.isProductNode(current) && current.getParent()) {
					var choice = current.getParent();
					return entityService.equals(choice.selected, current)
				} else {
					return false;
				}
			}
			var call = function(node) {
				if (productService.isPartNode(node) && node.children.length > 0) {
					if (!levels[node.level]) {
						levels[node.level] = []
					}
					levels[node.level].push(node);
				}
				return true;
			}
			//
			if (composer && composer.price) {
				graphService.width(composer.price.product, call, true, explorer);
			}
			return levels;
		}
		return service;
	} ]);

})();
