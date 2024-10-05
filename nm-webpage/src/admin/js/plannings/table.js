(function() {
	//
	Wick.DEPENDENCIES.push("plannings.table");
	var module = angular.module("plannings.table", [ 'plannings.services', 'commons.all', 'ui.calendar', 'ui.bootstrap' ]);
	module.controller('PlanningController', [ '$scope', 'uiCalendarConfig', "$routeParams", 'PlanningRestaurant', 'MultiviewService', '$rootScope',
			function($scope, uiCalendarConfig, $routeParams, PlanningRestaurant, MultiviewService, $rootScope) {
				$scope.reload = function(id) {
					$scope.planning = PlanningRestaurant.get({
						id : id
					});
				}
				if (MultiviewService.hasParam("planning")) {
					$scope.reload(MultiviewService.getParam("planning"))
				}
				$scope.view = {
					mode : 'edit'
				};
				$scope.changeMode = function(mode) {
					$scope.view.mode = mode;
					$rootScope.$broadcast("ReloadEvents")
				};
				$scope.isMode = function(mode) {
					return $scope.view.mode == mode;
				}
				$scope.getCssMode = function(mode) {
					if ($scope.view.mode == mode) {
						return 'active'
					}
				}
				$scope.getPlanningId = function() {
					return $scope.planning.id;
				}
				$scope.setShow = function(prefix, value) {
					$scope.$eval(sprintf("show.%s=%s", prefix, value))
				}
				$scope.resetShow = function() {
					$scope.$eval("show={}")
				}
				//
				$scope.initMonths = function(short, long) {
					console.log("[Planning] Setting months", short, long);
					$scope.monthNames = long;
					$scope.monthNamesShort = short;
				};
				$scope.initDays = function(short, long) {
					console.log("[Planning] Setting text", short, long);
					$scope.dayNames = long;
					$scope.dayNamesShort = short;
				};
				$scope.titleFormat = {}
				$scope.initFormat = function(key, value) {
					console.log("[Planning] Setting format title", key, value);
					$scope.titleFormat[key] = value;
				};
				$scope.reloadEvents = function() {
					for ( var i in uiCalendarConfig.calendars) {
						uiCalendarConfig.calendars[i].fullCalendar('refetchEvents');
					}
				};
				$scope.$on("ReloadEvents", $scope.reloadEvents)
			} ]);
	module.controller('PlanningViewController', [ '$scope', 'uiCalendarConfig', '$element', "Slot", 'PlanningRestaurant',
			function($scope, uiCalendarConfig, $element, Slot, PlanningRestaurant) {
				$scope.openViewEvents = {
					events : function(start, end, timezone, callback) {
						PlanningRestaurant.viewOpen({
							id : $scope.getPlanningId(),
							start : start.toDate().getTime(),
							end : end.toDate().getTime()
						}).$promise.then(function(data) {
							callback(data)
						});
					},
					className : 'label-success' // an option!
				};
				$scope.closeViewEvents = {
					events : function(start, end, timezone, callback) {
						PlanningRestaurant.viewClose({
							id : $scope.getPlanningId(),
							start : start.toDate().getTime(),
							end : end.toDate().getTime()
						}).$promise.then(function(data) {
							callback(data)
						});
					},
					className : 'label-danger' // an option!
				};
				/* config object */
				$scope.uiConfig = {
					calendar : {
						monthNames : $scope.monthNames,
						monthNamesShort : $scope.monthNamesShort,
						dayNames : $scope.dayNames,
						dayNamesShort : $scope.dayNamesShort,
						titleFormat : $scope.titleFormat,
						header : {
							left : 'prev,next today',
							center : 'title',
							right : 'month,agendaWeek,agendaDay'
						},
						firstDay : 1,
						defaultView : 'agendaWeek',
						editable : true,
						droppable : false,
						handleWindowResize : true,
						columnFormat : {
							month : 'dddd',
							week : 'dddd DD/MM',
							day : 'dddd DD/MM'
						},
						timeFormat : {
							agenda : 'HH:mm',
							month : 'HH:mm'
						},
						allDaySlot : false,
						axisFormat : 'HH:mm',
						firstHour : 7,
						eventRender : function(event, element) {
							element.attr("title", event.title);
						},
						buttonText : {
							today : "Ajourd'hui",
							month : "Mois",
							week : "Semaine",
							day : "Jour"
						}
					}
				};
				$scope.eventSourcesView = [ $scope.closeViewEvents, $scope.openViewEvents ];
				// 
			} ]);
	module.controller('PlanningEditController', [
			'$scope',
			'uiCalendarConfig',
			'$element',
			"$routeParams",
			'PlanningRestaurant',
			'Slot',
			function($scope, uiCalendarConfig, $element, $routeParams, PlanningRestaurant, Slot) {
				/* SOURCE FROM WS */
				$scope.closeEvents = {
					events : function(start, end, timezone, callback) {
						PlanningRestaurant.close({
							id : $scope.getPlanningId(),
							start : start.toDate().getTime(),
							end : end.toDate().getTime()
						}).$promise.then(function(data) {
							callback(data)
						});
					},
					className : 'label-danger' // an option!
				};
				$scope.openEvents = {
					events : function(start, end, timezone, callback) {
						PlanningRestaurant.open({
							id : $scope.getPlanningId(),
							start : start.toDate().getTime(),
							end : end.toDate().getTime()
						}).$promise.then(function(data) {
							callback(data)
						});
					},
					className : 'label-success' // an option!
				};
				$scope.deleteOnlick = function(event, allDay, jsEvent, view) {
					var formatDate = "DD/MM/YYYY HH:mm";
					$scope.toDelete = event;
					var templateUrl = null;
					if (event.eventType == "Recurrent") {
						if (event.noEndPlan) {
							$scope.toDeleteArgs = [ event.originalStartHoraire, event.originalEndHoraire, moment(event.start).format(formatDate),
									moment(event.end).format(formatDate) ];
							$scope.setShow("delete.recurrent.noend", true)
						} else {
							$scope.toDeleteArgs = [ event.originalStartHoraire, event.originalEndHoraire,
									moment(event.originalEndPlan).format(formatDate), moment(event.start).format(formatDate),
									moment(event.end).format(formatDate) ];
							$scope.setShow("delete.recurrent.end", true)
						}
					} else {
						$scope.toDeleteArgs = [ moment(event.originalStartPlan).format(formatDate), moment(event.originalEndPlan).format(formatDate),
								moment(event.start).format(formatDate), moment(event.end).format(formatDate) ];
						$scope.setShow("delete.exceptionnal", true)
					}
				};
				$scope.cancel = function() {
					$scope.resetShow()
				}
				$scope.deleteAll = function() {
					var promise = Slot["delete"]({
						id : $scope.toDelete.id
					}).$promise;
					promise.then(function() {
						$scope.cancel()
						$scope.reloadEvents();
					});
					return promise;
				}
				$scope.deleteRange = function() {
					var promise = Slot["deleteRange"]({
						start : $scope.toDelete.start.toDate().getTime(),
						end : $scope.toDelete.end.toDate().getTime(),
						id : $scope.toDelete.id
					}).$promise;
					promise.then(function() {
						$scope.cancel();
						$scope.reloadEvents();
					});
					return promise;
				}
				/* config object */
				$scope.uiConfig = {
					calendar : {
						monthNames : $scope.monthNames,
						monthNamesShort : $scope.monthNamesShort,
						dayNames : $scope.dayNames,
						dayNamesShort : $scope.dayNamesShort,
						titleFormat : $scope.titleFormat,
						header : {
							left : 'prev,next today',
							center : 'title',
							right : 'month,agendaWeek,agendaDay'
						},
						firstDay : 1,
						defaultView : 'agendaWeek',
						editable : true,
						droppable : false,
						handleWindowResize : true,
						columnFormat : {
							month : 'dddd',
							week : 'dddd DD/MM',
							day : 'dddd DD/MM'
						},
						timeFormat : {
							agenda : 'HH:mm',
							month : 'HH:mm'
						},
						allDaySlot : false,
						axisFormat : 'HH:mm',
						firstHour : 7,
						eventClick : $scope.deleteOnlick,
						eventRender : function(event, element) {
							element.append('<a class="nb-event-close"><i class="fa fa-times fa-white"></i></a>');
							element.attr("title", event.title);
						},
						buttonText : {
							today : "Ajourd'hui",
							month : "Mois",
							week : "Semaine",
							day : "Jour"
						}
					}
				};
				/* event sources array */
				$scope.eventSources = [ $scope.closeEvents, $scope.openEvents ];

			} ]);
})();
