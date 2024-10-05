(function() {
	// DAO
	Array.prototype.frequencies = function() {
		var l = this.length, result = {
			all : []
		};
		while (l--) {
			result[this[l]] = result[this[l]] ? ++result[this[l]] : 1;
		}
		// all pairs (label, frequencies) to an array of arrays(2)
		for ( var l in result) {
			if (result.hasOwnProperty(l) && l !== 'all') {
				result.all.push([ l, result[l] ]);
			}
		}
		return result;
	};
	var orderService = angular.module("composer.service");

	orderService.factory('historyService', [ 'graphService', 'productService', 'entityService', function(graphService, productService, entityService) {
		var service = {};
		// Return true if already visited
		service.hasBeenVisited = function(composer, choice) {
			return composer.histories.indexOf(productService.getKey(choice)) > -1;
		}
		service.isCurrent = function(composer, node) {
			return entityService.equals(composer.currentNode, node);
		}
		service.isRevisited = function(composer) {
			var key = productService.getKey(composer.currentNode);
			var freq = composer.histories.frequencies()
			return freq[key] > 1;
		}
		service.isParentOfCurrent = function(composer, node) {
			var isParent = composer.parents.indexOf(productService.getKey(node)) > -1;
			var isCurrent = service.isCurrent(composer, node);
			return isParent && !isCurrent;
		}
		service.isChildOfCurrent = function(composer, node) {
			var isChild = composer.children.indexOf(productService.getKey(node)) > -1;
			var isCurrent = service.isCurrent(composer, node);
			return isChild && !isCurrent;
		}
		service.isPrevious = function(composer, node) {
			var previous = composer.histories.indexOf(productService.getKey(node)) > -1;
			var isCurrent = service.isCurrent(composer, node);
			var isParent = service.isParentOfCurrent(composer, node);
			return previous && !isCurrent && !isParent;
		}
		service.back = function(composer, node) {
			var index = composer.histories.indexOf(productService.getKey(node));
			var removed = composer.histories.splice(index);
			return removed;
		}
		service.begin = function(composer, current) {
			composer.currentNode = current;
			composer.parents = []
			composer.children = []
			composer.histories.push(productService.getKey(current))
			// Child
			graphService.deep(current, function(node) {
				if (productService.isPartNode(node)) {
					composer.children.push(productService.getKey(node))
				}
				return true;
			}, true);
			// Parents
			graphService.parents(current, function(node) {
				if (productService.isPartNode(node)) {
					composer.parents.push(productService.getKey(node))
				}
				return true;
			})
		}
		return service;
	} ]);

})();
