var app = angular.module('commons.directives');
// Table
app.directive('nbtable', [ '$http', 'dispatcher', 'entityService', '$timeout', "anchorSmoothScroll", "draftService", 'feedback', function($http, dispatcher, entityService, $timeout, anchorSmoothScroll, draftService, feedback) {
	return {
		scope : {
			'service' : '=',
			'name' : '@',
			'params' : '='
		},
		transclude : true,
		templateUrl : "table.html",
		link : function($scope, elem, attrs, controller) {
			$scope.params = (typeof $scope.params == "object") ? $scope.params : {};
			$scope.params["idProp"] = ($scope.params["idProp"]) ? $scope.params["idProp"] : "id";
			$scope.params["uniqueSelect"] = ($scope.params["uniqueSelect"]) ? $scope.params["uniqueSelect"] : false;
			var name = ($scope.name) ? $scope.name : "Entity";
			var capitalize = name.capitalize(), tag = name;
			var evtSaved = sprintf("On%sSaved", capitalize), evtDelete = sprintf("On%sDelete", capitalize);
			var evtReload = sprintf("On%sReloadModel", capitalize)
			var evtForm = sprintf("On%sFormCreate", capitalize), evtRefresh = sprintf("On%sRefresh", capitalize);
			var tag = sprintf("[%sTable]", capitalize), evtCancel = sprintf("On%sCanceled", capitalize);
			var evtLoaded = sprintf("On%sLoaded", capitalize);
			$scope.checkedModel = {}
			//
			$scope.currentPage = 1; // current page
			$scope.maxSize = 5; // pagination max size
			$scope.entryLimit = 10; // max rows for data table
			$scope.models = [];
			$scope.selected = [];
			$scope["shows"] = {};
			$scope["shows"]["model"] = {}
			$scope["shows"]["model"]["delete"] = {}
			//
			if ($scope.params.scope) {
				for ( var prop in $scope.params.scope) {
					$scope[prop] = $scope.params.scope[prop]
				}
			}
			$scope.getSelected = function() {
				return $scope.selected;
			}
			//
			$scope.isEmpty = function() {
				return $scope.models.length == 0;
			};
			$scope["create"] = function() {
				var payload = ($scope.params["onBeforeCrate"]) ? $scope.params["onBeforeCrate"]() : {};
				console.debug(sprintf("%s Creating %s. Dispatching %s....", tag, name, evtForm), payload);
				dispatcher.broadcast(evtForm, payload);
			};
			$scope["select"] = function(model) {
				if ($scope.params["uniqueSelect"]) {
					$scope.getSelected().clear()
				}
				$scope.checkedModel[model.id] = true
				$scope.getSelected().push(model);
				$scope.params["onSelect"] && $scope.params["onSelect"]($scope.getSelected(), model, true)
				console.debug(sprintf("%s Selecting model %s....", tag, name));
			};
			$scope["unselect"] = function(model) {
				$scope.checkedModel[model.id] = false
				entityService.remove($scope.getSelected(), model)
				$scope.params["onSelect"] && $scope.params["onSelect"]($scope.getSelected(), model, false)
				console.debug(sprintf("%s UnSelecting toggle model %s....", tag, name));
			};
			$scope["selectToggle"] = function(model) {
				$scope.checkedModel[model.id] = !$scope.checkedModel[model.id]
				var adding = false;
				if (entityService.contains($scope.getSelected(), model)) {
					entityService.remove($scope.getSelected(), model)
				} else {
					if ($scope.params["uniqueSelect"]) {
						$scope.getSelected().clear()
					}
					$scope.getSelected().push(model);
					adding = true;
				}
				$scope.params["onSelect"] && $scope.params["onSelect"]($scope.getSelected(), model, adding)
				console.debug(sprintf("%s Selecting toggle model %s....", tag, name));
			};
			$scope["contains"] = function(model) {
				return entityService.contains($scope.getSelected(), model);
			};
			$scope["edit"] = function(object) {
				console.debug(sprintf("%s Editing %s. Dispatching %s", tag, name, evtForm));
				var data = $scope.service.edit(object[$scope.params["idProp"]], function() {
					console.debug(sprintf("%s Editing %s. Loaded edit %s", tag, name, name), data);
					dispatcher.broadcast(evtForm, {
						edit : data
					});
				});
			};
			$scope["askRemove"] = function(object) {
				var id = object[$scope.params["idProp"]];
				$scope["shows"]["model"]["delete"][id] = true;
			};
			$scope["cancelRemove"] = function(object) {
				var id = object[$scope.params["idProp"]];
				$scope["shows"]["model"]["delete"][id] = false;
			};
			$scope["remove"] = function(object, $index) {
				console.debug(sprintf("%s Removing %s...", tag, name), object);
				$scope.service["delete"](object, function() {
					entityService.remove($scope.models, object);
					//
					var id = object[$scope.params["idProp"]];
					$scope["shows"]["model"]["delete"][id] = false;
					//
					dispatcher.broadcast(evtDelete, object);
				});
			};
			var loadCallBack = function(data) {
				$timeout(function() {
					$scope.models = data;
					console.debug(sprintf("%s %s loaded", tag, capitalize), $scope.models);
					dispatcher.broadcast(evtLoaded, $scope.models);
				})
			};
			$scope.reloadModels = function(filter, force) {
				console.debug(sprintf("%s Reloading models %s ...", tag, capitalize));
				if (force) {
					$scope.service.clear && $scope.service.clear()
				}
				$scope.models = $scope.doReloadModels(filter, loadCallBack)
			}
			$scope.doReloadModels = function(filter, loadCallBack) {
				if (filter && !jQuery.isEmptyObject(filter)) {
					return $scope.service.filter(filter, loadCallBack);
				} else {
					return $scope.service.list(loadCallBack);
				}
			}
			$scope["refresh"] = function() {
				console.debug(sprintf("%s Refreshing %s", tag, capitalize));
				$scope.reloadModels($scope.params.filters, true);
			};

			$scope.$on(evtReload, function(event) {
				console.debug(sprintf("%s Reload model event  %s...", tag, evtReload));
				$scope.reloadModels($scope.params.filters, true);
			});
			$scope.$on(evtDelete, function(event, object) {
				console.debug(sprintf("%s Receiving event  %s...", tag, evtDelete), object);
				// FORCE REFRESH
				$timeout(function() {
					feedback.to(evtDelete).success = name + '.delete.success';
					entityService.remove($scope.models, object);
				}, 10);
			});
			$scope.$watch("params.filters", function(val) {
				$scope.reloadModels(val)
			});
			$scope.$on(evtSaved, function(event, object) {
				$scope.$apply(function() {
					console.debug(sprintf("%s %s %s...", tag, evtSaved, name), $scope.models, object);
					entityService.replace($scope.models, object);
					anchorSmoothScroll.scrollToElement(elem[0]);
					feedback.to(evtSaved).success = name + '.save.success';
				});

			});
			$scope.$on(evtRefresh, function(event, objects) {
				console.debug(sprintf("%sReceiving event event", tag, evtRefresh), objects);
				// FORCE REFRESH
				$timeout(function() {
					if (objects instanceof Array) {
						$scope.models = objects;
					} else {
						console.error(sprintf("%s Bad data refreshed from Event...", tag), objects);
						$scope.models = [];
					}
				}, 10);
			});
			$scope.$on(evtCancel, function(event) {
				console.debug(sprintf("%s Receiving event %s", tag, evtCancel));
				// FORCE REFRESH
				$timeout(function() {
					anchorSmoothScroll.scrollToElement(elem[0]);
				}, 10);
			});

			// FILTER
			$scope.filter = function() {
				$timeout(function() {
					// wait for 'filtered' to be changed change pagination
					// with $scope.filtered
					$scope.totalItems = $scope.filtered.length;
				}, 10);
			};
			// INIT
			$scope.updateTable = function() {
				$scope.totalItems = $scope.models.length;
				console.debug(sprintf("%s Update table totalItems", tag), $scope.totalItems);
			};
			$scope.$watch("models", $scope.updateTable);
			if ($scope.params.overrides) {
				for ( var prop in $scope.params.overrides) {
					$scope[prop] = $scope.params.overrides[prop]
				}
			}
			if ($scope.params.watch) {
				$scope.$watch($scope.params.watch, function() {
					$scope.reloadModels($scope.params.filters, true);
				},true)
			}
		}
	}
} ]);