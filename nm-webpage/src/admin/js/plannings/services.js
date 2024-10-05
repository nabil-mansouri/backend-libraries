(function() {
	// DAO
	var module = angular.module("plannings.services", [ "commons.all" ]);
	module.factory('PlanningRestaurant', [ '$resource', function($resource) {
		var url = sprintf("%s/planning/restaurant", Wick.BASE_URL);
		var transformResponse = function(data) {
			data = JSON.parse(data);
			data.forEach(function(event) {
				event.start = new Date(event.start);
				event.end = new Date(event.end);
			})
			console.debug("[PlanningService] datas ...", data)
			return data;
		}
		var resource = $resource(sprintf("%s/:id", url), {}, {
			close : {
				url : sprintf("%s/edit/close/:id/:start/:end", url),
				isArray : true,
				transformResponse : transformResponse
			},
			open : {
				url : sprintf("%s/edit/open/:id/:start/:end", url),
				isArray : true,
				transformResponse : transformResponse
			},
			viewOpen : {
				isArray : true,
				url : sprintf("%s/open/:id/:start/:end", url),
				transformResponse : transformResponse,
			},
			viewClose : {
				isArray : true,
				url : sprintf("%s/close/:id/:start/:end", url),
				transformResponse : transformResponse,
			}
		});
		resource.getBase = function() {
			return url;
		}
		return resource;
	} ]);
	module.factory('Slot', [ '$resource', function($resource) {
		var url = sprintf("%s/plannings/slots/", Wick.BASE_URL);
		var resource = $resource(sprintf("%s/:id", url), {}, {
			save : {
				method : 'POST',
				transformRequest : function(data, headers) {
					// To this year
					if (data.dateBeginHoraire) {
						data.dateBeginHoraire = moment(data.dateBeginHoraire).year(moment().year()).toDate()
					}
					if (data.dateEndHoraire) {
						data.dateEndHoraire = moment(data.dateEndHoraire).year(moment().year()).toDate()
					}
					return angular.toJson(data);
				},
				url : sprintf("%s/:idPlanning", url)
			},
			deleteRange : {
				method : 'DELETE',
				url : sprintf("%s/:id/:start/:end", url)
			}
		});
		resource.getBase = function() {
			return url;
		}
		return resource;
	} ]);

})();
