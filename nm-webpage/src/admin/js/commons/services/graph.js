(function() {
	'use strict';
	var app = angular.module('commons.services');
	// GRAPH
	app.factory('graphService', [ '$q', '$http', 'entityService', function($q, $http, entityService) {
		var service = {};
		service.create = function(object, props, level) {
			!level && (level = 0);
			var copy = angular.copy(object);
			var node = {
				level : level,
				element : copy,
				children : [],
				type : props.main,
				context : {},
				id : copy.id
			}
			var recursion = function(node, level, props) {
				for ( var prop in props.others) {
					if (node.element[prop] && node.element[prop] instanceof Array) {
						node.element[prop].foreach(function(part) {
							var copy = angular.copy(part);
							var sub = {
								level : (level + 1),
								element : copy,
								parent : node,
								children : [],
								context : {},
								id : copy.id
							};
							sub.type = props.others[prop];
							node.children.push(sub);
							recursion(sub, sub.level, props)
						})
						node.element[prop] = [];
					}
				}
			};
			recursion(node, node.level, props)
			return node;
		};
		service.setParents = function(node) {
			var buildParent = function(cur, parent) {
				cur.getParent = function() {
					return parent;
				}
			}
			if (!node)
				return;
			buildParent(node)
			var stack = (node.children) ? ([].concat(node.children)) : [];
			// set parent
			stack.forEach(function(cur) {
				buildParent(cur, node)
			})
			//
			while (stack.length > 0) {
				var last = stack.shift();
				if (last.children) {
					// set parent
					last.children.forEach(function(cur) {
						buildParent(cur, last)
					})
					stack = last.children.concat(stack)
				}
			}
		}
		service.parents = function(node, func, exclude) {
			if (!node)
				return;
			//
			exclude && (node = node.getParent());
			var isOn = true;
			while (node && isOn) {
				isOn = func(node);
				node = node.getParent();
			}
		}
		service.deep = function(node, func, exclude, explorer) {
			if (!node)
				return;
			!exclude && (func(node, true));
			var stack = (node.children) ? ([].concat(node.children)) : [];
			//
			var isOn = true;
			while (stack.length > 0 && isOn) {
				var last = stack.shift();
				isOn = func(last, false);
				if (last.children) {
					//
					if (explorer) {
						if (explorer(last, last.children)) {
							stack = last.children.concat(stack)
						}
					} else {
						stack = last.children.concat(stack)
					}
				}
			}
		}
		service.width = function(node, func, exclude, explorer) {
			if (!node)
				return;
			!exclude && (func(node));
			var stack = (node.children) ? ([].concat(node.children)) : [];
			var isOn = true;
			while (stack.length > 0 && isOn) {
				var last = stack.shift();
				isOn = func(last);
				if (last.children) {
					if (explorer) {
						if (explorer(last, last.children)) {
							stack = stack.concat(last.children)
						}
					} else {
						stack = stack.concat(last.children)
					}
				}
			}
		}
		service.level = function(node, level, func) {
			if (!node)
				return;
			var stack = [ node ];
			var isOn = true;
			while (stack.length > 0 && isOn) {
				var last = stack.pop();
				(last.level == level) && (isOn = func(last));
				if ((last.children) && (last.level < level)) {
					stack = last.children.concat(stack)
				}
			}
		}
		return service;
	} ]);
}());