(function() {
	'use strict';
	var app = angular.module('commons.services');
	//
	app.factory('entityService', [ function() {
		var service = {};
		service.getSafeIdProp = function(idProp) {
			return (typeof idProp == "undefined") ? "id" : idProp;
		};
		service.addOrRemove = function(list, entity, add, idProp) {
			if (add) {
				list.push(entity)
			} else {
				service.remove(list, entity, idProp)
			}
		}
		service.addUnique = function(list, entity, idProp) {
			if (service.contains(list, entity, idProp)) {
				return false;
			} else {
				list.push(entity)
				return true;
			}
		}
		service.remove = function(list, entity, idProp) {
			if (!list || !(list instanceof Array)) {
				return;
			}
			idProp = service.getSafeIdProp(idProp);
			var removed = false;
			for ( var i = 0; i < list.length; i++) {
				if (this.equals(list[i], entity, idProp)) {
					// console.debug("[entityService] removing entity",list,
					// entity, i);
					list.splice(i, 1);
					removed = true;
				}
			}
			return removed;
		};
		service.removeById = function(list, id, idProp) {
			if (!list || !(list instanceof Array)) {
				return;
			}
			idProp = service.getSafeIdProp(idProp);
			for ( var i = 0; i < list.length; i++) {
				if (list[i][idProp] == id) {
					// console.debug("[entityService] removing entity by
					// id",list, id, i);
					list.splice(i, 1);
				}
			}
		};
		service.getById = function(list, id, idProp) {
			if (!list || !(list instanceof Array)) {
				return;
			}
			idProp = service.getSafeIdProp(idProp);
			for ( var i = 0; i < list.length; i++) {
				if (list[i][idProp] == id) {
					// console.debug("[entityService] getting entity by
					// id",list, id, i);
					return list[i];
				}
			}
			return false;
		};
		service.toIds = function(list, idProp) {
			if (!list || !(list instanceof Array)) {
				return;
			}
			idProp = service.getSafeIdProp(idProp);
			var res = []
			list.forEach(function(current) {
				res.push(current[idProp]);
			})
			return res;
		};
		service.replace = function(list, entity, idProp) {
			if (!list || !(list instanceof Array)) {
				return;
			}
			service.remove(list, entity, idProp);
			list.push(entity);
		};
		service.equals = function(entity1, entity2, idProp) {
			idProp = service.getSafeIdProp(idProp);
			return entity1 && entity2 && entity1[idProp] == entity2[idProp];
		};
		service.equalsOrNull = function(entity1, entity2, idProp) {
			if (!entity1 && !entity2) {
				return true;
			} else {
				return service.equals(entity1, entity2, idProp);
			}
		};
		service.contains = function(list, entity, idProp) {
			if (!list || !(list instanceof Array)) {
				console.error("[entityService]entity contains list n'est pas un tableau", list, entity);
				return false;
			}
			for ( var i = 0; i < list.length; i++) {
				if (this.equals(list[i], entity, idProp)) {
					// console.debug("[entityService]entity contains",list,
					// entity);
					return true;
				}
			}
			// console.debug("[entityService]entity does NOT contains",list,
			// entity);
			return false;
		};
		service.intersection = function(arr1, arr2, idProp) {
			var inter = [];
			for ( var i = 0; i < arr1.length; i++) {
				if (service.contains(arr2, arr1[i], idProp)) {
					inter.push(arr1[i]);
				}
			}
			return inter;
		};
		service.difference = function(arr1, arr2, idProp) {
			var diff = [];
			for ( var i = 0; i < arr1.length; i++) {
				if (!service.contains(arr2, arr1[i], idProp)) {
					diff.push(arr1[i]);
				}
			}
			return diff;
		};
		service.interAndDiff = function(arr1, arr2, idProp) {
			var inter = [];
			var diff = [];
			for ( var i = 0; i < arr1.length; i++) {
				if (service.contains(arr2, arr1[i], idProp)) {
					inter.push(arr1[i]);
				} else {
					diff.push(arr1[i]);
				}
			}
			return {
				inter : inter,
				diff : diff
			};
		};
		service.filter = function(arr1, filter) {
			var inter = [];
			var diff = [];
			for ( var i = 0; i < arr1.length; i++) {
				if (filter(arr1[i])) {
					inter.push(arr1[i]);
				} else {
					diff.push(arr1[i]);
				}
			}
			return {
				inter : inter,
				diff : diff
			};
		};
		return service;
	} ]);

}());