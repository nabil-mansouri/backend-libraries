(function() {
	// DAO
	var statService = angular.module("statistics.service", [ "commons.all" ]);
	statService.factory('ProductStat', [ '$resource', function($resource) {
		var url = sprintf('%s/ws/statistics/product', Wick.BASE_URL);
		var ProductStat = $resource(sprintf("%s", url), {}, {
			measure : {
				method : 'GET',
				cache : true,
				isArray : true,
				url : sprintf("%s", url)
			},
			count : {
				method : 'GET',
				cache : true,
				isArray : true,
				url : sprintf("%s/count", url)
			},
			sum : {
				method : 'GET',
				cache : true,
				isArray : true,
				url : sprintf("%s/sum", url)
			},
			bestCount : {
				method : 'GET',
				cache : true,
				isArray : true,
				url : sprintf("%s/best/count", url)
			},
			bestSum : {
				method : 'GET',
				cache : true,
				isArray : true,
				url : sprintf("%s/best/sum", url)
			}
		});
		return ProductStat;
	} ]);
	statService.factory('OrderStat', [ '$resource', function($resource) {
		var url = sprintf('%s/ws/statistics/order', Wick.BASE_URL);
		var OrderStat = $resource(sprintf("%s", url), {}, {
			measure : {
				method : 'GET',
				cache : true,
				isArray : true,
				url : sprintf("%s", url)
			},
			count : {
				method : 'GET',
				cache : true,
				isArray : true,
				url : sprintf("%s/count", url)
			},
			sum : {
				method : 'GET',
				cache : true,
				isArray : true,
				url : sprintf("%s/sum", url)
			},
			bestCount : {
				method : 'GET',
				cache : true,
				isArray : true,
				url : sprintf("%s/best/count", url)
			},
			bestSum : {
				method : 'GET',
				cache : true,
				isArray : true,
				url : sprintf("%s/best/sum", url)
			}
		});
		return OrderStat;
	} ]);
	statService.factory('StatisticService', [ function() {
		var StatisticService = {};
		StatisticService.transform = function(result, transformer) {
			var array = [];
			result.forEach(function(cur) {
				var dims = [];
				cur.dimensions.forEach(function(dim) {
					dims.push(transformer.transform(dim.dimension, dim.value))
				});
				cur.measures.forEach(function(mea, index) {
					if (!array[index]) {
						array[index] = []
					}
					var clone = dims.clone();
					clone.push((mea.value) ? mea.value : 0)
					array[index].push(clone);
				});
			})
			return array;
		}
		StatisticService.transformInverse = function(result, transformer) {
			var array = [];
			result.forEach(function(cur) {
				var dims = [];
				cur.measures.forEach(function(mea) {
					dims.push((mea.value) ? mea.value : 0)
				});
				cur.dimensions.forEach(function(dim, index) {
					if (!array[index]) {
						array[index] = []
					}
					var clone = dims.clone();
					clone.push(transformer.transform(dim.dimension, dim.value))
					array[index].push(clone);
				});
			})
			return array;
		}
		return StatisticService;
	} ]);
	statService.factory('PeriodService', [ 'ProductStat', function(ProductStat) {
		var PeriodService = {};
		PeriodService.getFrom = function(from, period) {
			if (from) {
				return from.getTime();
			} else {
				var mom = moment(from);
				switch (period) {
					case "Year":
					case "Month":
						mom.months(0);
					case "Week":
						mom.days(0);
					case "Day":
						mom.weekday(0);
					case "Hour":
						mom.hours(0);
					case "Minute":
						mom.minutes(0);
						mom.milliseconds(0);
						mom.seconds(0);
				}
				return mom.toDate().getTime()
			}
		}
		PeriodService.getTo = function(to, period, from) {
			if (to) {
				return to.getTime();
			} else {
				var mom = moment(PeriodService.getFrom(from, period));
				switch (period) {
					case "Year":
						mom.add(10, 'year');
					case "Month":
						mom.add(1, 'year');
					case "Week":
						mom.add(1, 'month')
					case "Day":
						mom.add(1, 'week');
					case "Hour":
						mom.add(1, 'day');
					case "Minute":
						mom.add(30, 'minute');
				}
				return mom.toDate().getTime()
			}
		}
		return PeriodService;
	} ]);
	statService.factory('StatTransformer', [ function() {
		var service = {};
		service.transform = function(type, value) {
			switch (type) {
				case "Minute":
					return moment(value).format("HH:mm");
				case "Hour":
					return moment(value).format("HH:mm");
				case "Day":
					return moment(value).format("DD-MM-YY");
				case "Week":
					return moment(value).format("DD-MM-YY");
				case "Month":
					return moment(value).format("MM-YY");
				case "Year":
					return moment(value).format("yyyy");
			}
			return value;
		}
		return service;
	} ]);
	statService.factory('ProductStatService', [ 'ProductStat', 'PeriodService', function(ProductStat, PeriodService) {
		var ProductStatService = {};
		ProductStatService.prepareFilter = function(filter) {
			return filter = {
				from : PeriodService.getFrom(filter.from, filter.period),
				to : PeriodService.getTo(filter.to, filter.period, filter.from),
				period : filter.period,
				measures : filter.measure,
				products : filter.products
			}
		}
		ProductStatService.compute = function(filter) {
			return ProductStat.measure(ProductStatService.prepareFilter(filter))
		}
		ProductStatService.prepareFilterClass = function(measure, filter) {
			return filter = {
				limit : filter.limit,
				from : PeriodService.getFrom(filter.from, filter.period),
				to : PeriodService.getTo(filter.to, filter.period, filter.from),
				period : filter.period
			}
		}
		ProductStatService.computeClass = function(measure, filter) {
			switch (measure) {
				case "BestCount": {
					return ProductStat.bestCount(ProductStatService.prepareFilterClass(measure, filter))
				}
				case "BestSum": {
					return ProductStat.bestSum(ProductStatService.prepareFilterClass(measure, filter))
				}
			}
		}
		return ProductStatService;
	} ]);
	statService.factory('OrderStatService', [ 'OrderStat', 'PeriodService', function(OrderStat, PeriodService) {
		var OrderStatService = {};
		OrderStatService.prepareFilter = function(filter) {
			return filter = {
				from : PeriodService.getFrom(filter.from, filter.period),
				to : PeriodService.getTo(filter.to, filter.period, filter.from),
				period : filter.period,
				measures : filter.measure
			}
		}
		OrderStatService.compute = function(filter) {
			return OrderStat.measure(OrderStatService.prepareFilter(filter))
		}
		OrderStatService.prepareFilterClass = function(measure, filter) {
			return filter = {
				limit : filter.limit,
				from : PeriodService.getFrom(filter.from, filter.period),
				to : PeriodService.getTo(filter.to, filter.period, filter.from),
				period : filter.period
			}
		}
		OrderStatService.computeClass = function(measure, filter) {
			switch (measure) {
				case "BestCount": {
					return OrderStat.bestCount(OrderStatService.prepareFilterClass(measure, filter))
				}
				case "BestSum": {
					return OrderStat.bestSum(OrderStatService.prepareFilterClass(measure, filter))
				}
			}
		}
		return OrderStatService;
	} ]);
})();
