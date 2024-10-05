(function() {
	'use strict';

	var app = angular.module('commons.filters', []);

	app.filter("nbI18", function(nbsprintfFilter) {
		return function(input, prefix, defau, args) {
			input = input || '';
			defau = defau || input;
			var temp = "";
			if (input.length > 0) {
				var key = (prefix) ? prefix + "." + input : input;
				temp = NbI18.get(key, defau);
			} else {
				temp = NbI18.get(prefix, defau);
			}
			if (args) {
				return nbsprintfFilter(temp, args)
			} else {
				return temp;
			}
		}
	});
	app.filter("nbDevise", function() {
		return function(input) {
			input = input || '0';
			// TODO get from config
			return input + " E";
		}
	});
	app.filter("nbFlag", [ "LocaleService", function(LocaleService) {
		return function(input) {
			return LocaleService.getFlagURL(input)
		}
	} ]);
	app.filter("sprintf", [ function() {
		return function(input) {
			return sprintf(input, arguments.splice(0, 1))
		}
	} ]);
	app.filter("nbsprintf", [ function() {
		return function(input, array) {
			if (array instanceof Array) {
				var arr = new Array()
				arr.push(input)
				for (var i = 0; i < array.length; i++) {
					arr.push(array[i])
				}
				var temp = sprintf.apply(null, arr);
				return temp;
			}
		}
	} ]);
	app.filter('nbNotIn', function($filter, entityService) {
		return function(haystack, selected) {
			if (selected && selected instanceof Array && selected.length > 0) {
				var result = _.filter(haystack, function(item) {
					var res = entityService.contains(selected, item)
					// console.debug("[NotInt] is in array?",res,item)
					return !res;
				});
				return result;
			} else {
				return haystack;
			}
		};
	});
	app.filter("nbLocale", [ "LocaleService", function(LocaleService) {
		return function(input, all) {
			// console.log("[nbLocaleFilter] filtering locale ",input)
			if (typeof input == "string") {
				input = LocaleService.getByCode(input)
			}
			if (input && input.name && input.nativeName) {
				input = (all) ? (input.name + " / " + input.nativeName) : input.nativeName;
			} else if (input && input.displayName) {
				input = input.displayName;
			}
			return input;
		}
	} ]);
	app.filter('nbfullsearch', function($filter) {
		return function(items, text) {
			if (!text || text.length === 0)
				return items;
			// split search text on space
			var searchTerms = text.split(' ');
			// search for single terms.
			// this reduces the item list step by step
			searchTerms.forEach(function(term) {
				if (term && term.length)
					items = $filter('filter')(items, term);
			});
			return items;
		};
	});
	app.filter('startFrom', function() {
		return function(input, start) {
			if (input && input instanceof Array) {
				start = +start; // parse to int
				return input.slice(start);
			}
			return [];
		};
	});
	app.filter('dateMs', function() {
		return function(input) {
			if (input) {
				return moment(input).format("DD/MM/YYYY HH:mm")
			}
			return [];
		};
	});
}());