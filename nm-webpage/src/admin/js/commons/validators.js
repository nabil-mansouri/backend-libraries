(function() {

	var commons = angular.module('commons.validators', [ 'commons.services' ]);
	commons.directive('nbMin', function() {
		return {
			restrict : 'A',
			require : 'ngModel',
			link : function(scope, elem, attr, ctrl) {
				function isEmpty(value) {
					return angular.isUndefined(value) || value === '' || value === null || value !== value;
				}
				scope.$watch(attr.nbMin, function() {
					ctrl.$setViewValue(ctrl.$viewValue);
				});
				var minValidator = function(value) {
					var min = scope.$eval(attr.nbMin) || 0;
					if (!isEmpty(value) && value < min) {
						ctrl.$setValidity('nbMin', false);
						return undefined;
					} else {
						ctrl.$setValidity('nbMin', true);
						return value;
					}
				};

				ctrl.$parsers.push(minValidator);
				ctrl.$formatters.push(minValidator);
			}
		};
	});

	commons.directive('nbMax', function() {
		return {
			restrict : 'A',
			require : 'ngModel',
			link : function(scope, elem, attr, ctrl) {
				function isEmpty(value) {
					return angular.isUndefined(value) || value === '' || value === null || value !== value;
				}
				scope.$watch(attr.nbMax, function() {
					ctrl.$setViewValue(ctrl.$viewValue);
				});
				var maxValidator = function(value) {
					var max = scope.$eval(attr.nbMax) || Infinity;
					if (!isEmpty(value) && value > max) {
						ctrl.$setValidity('nbMax', false);
						return undefined;
					} else {
						ctrl.$setValidity('nbMax', true);
						return value;
					}
				};

				ctrl.$parsers.push(maxValidator);
				ctrl.$formatters.push(maxValidator);
			}
		};
	});
	commons.directive('nbMaxFun', function() {
		return {
			restrict : 'A',
			require : 'ngModel',
			link : function(scope, elem, attr, ctrl) {
				function isEmpty(value) {
					return angular.isUndefined(value) || value === '' || value === null || value !== value;
				}
				scope.$watch(attr.nbMax, function() {
					ctrl.$setViewValue(ctrl.$viewValue);
				});
				var maxValidator = function(value) {
					var max = scope[attr.nbMaxFun]() || Infinity;
					if (!isEmpty(value) && value > max) {
						ctrl.$setValidity('nbMax', false);
						return undefined;
					} else {
						ctrl.$setValidity('nbMax', true);
						return value;
					}
				};

				ctrl.$parsers.push(maxValidator);
				ctrl.$formatters.push(maxValidator);
			}
		};
	});
	commons.directive('nbarray', function() {
		return {
			scope : {
				"nbarray" : "=",
				"nbcondition" : "="
			},
			require : [ "ngModel" ],
			link : function(scope, elm, attrs, ctl) {
				var model = ctl[0]
				var min = attrs["nbarraymin"];
				var max = attrs["nbarraymax"];
				var name = attrs["name"];
				var validate = function(newValue) {
					console.debug("[nbarray] dump model BEFORE", scope.nbcondition, name, model);
					if (scope.nbcondition) {
						if (newValue && newValue instanceof Array) {
							var minOk = !min || newValue.length >= min;
							var maxOk = !max || newValue.length <= max;
							model.$setValidity('nbarray', minOk && maxOk);
						} else {
							model.$setValidity('nbarray', true);
						}
					} else {
						model.$setValidity('nbarray', true);
					}
					console.debug("[nbarray] dump model AFTER", scope.nbcondition, name, model);
				};
				scope.$watchCollection("nbarray", function(newValue, oldValue) {
					console.debug('[nbarray] model array value changed...', newValue, oldValue);
					validate(newValue)
				});
				scope.$watch("nbcondition", function(newValue, oldValue) {
					console.debug('[nbarray] model nbcondition value changed...', newValue, oldValue);
					validate(newValue)
				});
			}
		};
	});

	commons.directive('nbdate', function() {
		return {
			require : 'ngModel',
			link : function(scope, elm, attrs, ctrl) {
				ctrl.$parsers.unshift(function(viewValue) {
					if (viewValue && viewValue instanceof Date) {
						// TODO recup le nom de l'erreur en valeur de l'attribut
						// "nbdate" (voir le dump de l objet form pour
						// comprendre)
						ctrl.$setValidity('nbdate', true);
						console.debug("[nbdate] dump model", ctrl);
						return viewValue;
					} else {
						ctrl.$setValidity('nbdate', false);
						console.debug("[nbdate] dump model", ctrl);
						return undefined;
					}
				});
			}
		};
	});

	commons.directive('nbValidDateRange', [ "$filter", function($filter) {
		var isValidDateRange = function(fromDate, toDate) {
			// Return true if one of the two dates are not setted (required has
			// to check it)
			if (!fromDate || !(fromDate instanceof Date))
				return true;
			if (!toDate || !(toDate instanceof Date))
				return true;
			var diff = toDate - fromDate;
			return diff >= 0;
		};
		return {
			require : 'ngModel',
			scope : {
				nbValidDateRange : '=',
				nbDateRangeBegin : '='
			},
			compile : function(element, attrs) {
				return {
					pre : function(scope, elm, attrs, ctrl) {
						var validateDateRange = function(dateEnd) {
							var isValid = isValidDateRange(scope.nbDateRangeBegin, scope.nbValidDateRange);
							ctrl.$setValidity('nbValidDateRange', isValid);
							console.debug("[nbValidDateRange] dump model", ctrl);
							return dateEnd;
						};
						scope.$watch('nbValidDateRange', function() {
							validateDateRange();
						});
						scope.$watch('nbDateRangeBegin', function() {
							validateDateRange();
						});
					}
				}
			}
		};
	} ]);

	commons.directive('nbFixdate', [ "$filter", function($filter) {
		return {
			require : 'ngModel',
			link : function(scope, elm, attrs, ctrl) {
				ctrl.$formatters.push(function(date) {
					console.debug("[nbFixdate]fixing date format...", date, arguments)
					if (typeof date == "number") {
						return new Date(date)
					}
					return date;
				});
				ctrl.$parsers.push(function(date) {
					console.debug("[nbFixdate]fixing date parse...", date, arguments)
					if (typeof date == "number") {
						return new Date(date)
					}
					return date;
				});
			}
		};
	} ]);

	commons.directive('nbDateMaxCurrent', [ "$filter", function($filter) {
		return {
			require : 'ngModel',
			link : function(scope, elm, attrs, ctrl) {
				elm.attr("max", moment().format("YYYY-MM-DD"))
			}
		};
	} ]);

})();
