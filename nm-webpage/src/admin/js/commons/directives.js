(function() {

	var app = angular.module('commons.directives', [ 'commons.services' ]);
	// Sprintf
	app.directive('sprintf', function() {
		return {
			restrict : 'A',
			scope : {
				arrayArgs : "="
			},
			link : function(scope, element, attrs, controller) {
				var args = attrs.args;
				var string = attrs.sprintf;
				console.debug("[sprintf] format:", format, " string/ args : ", string, attrs);
				if (typeof args == "string") {
					var format = sprintf(string, args);
					console.debug("[sprintf] format:", format, " string and args : ", string, args);
					element.text(format);
				}
				if (typeof scope.arrayArgs == "object") {
					var argument = scope.arrayArgs;
					argument.unshift(string);
					var format = sprintf.apply(this || window, argument);
					console.debug("[sprintf] format:", format, " string and args : ", string, scope.arrayArgs);
					element.text(format);
				}
			}
		};
	});
	app.directive('nbselected', function() {
		return {
			restrict : 'A',
			scope : {
				nbselected : "="
			},
			link : function(scope, element, attrs, controller) {
				scope.$watch("nbselected", function(val) {
					if (val) {
						element.addClass("nbselected")
						element.attr("selected", "selected")
					} else {
						element.removeClass("nbselected")
						element.removeAttr("selected")
					}
				})
				element.on("click", function(e) {
					scope.nbselected = !scope.nbselected;
				})
			}
		};
	});
	app.directive('htmlprintf', function() {
		return {
			restrict : 'A',
			scope : {
				arrayArgs : "="
			},
			link : function(scope, element, attrs, controller) {
				var args = attrs.args;
				var string = attrs.htmlprintf;
				if (typeof args == "string") {
					var format = sprintf(string, args);
					console.debug("[htmlprintf] format:", format, " string and args : ", string, args);
					element.append(format);
				}
				if (typeof scope.arrayArgs == "object") {
					var argument = scope.arrayArgs;
					argument.unshift(string);
					var format = sprintf.apply(this || window, argument);
					console.debug("[htmlprintf] format:", format, " string and args : ", string, scope.arrayArgs);
					element.append(format);
				}
			}
		};
	});
	// 
	app.directive('nbvalidshow', [ function() {
		return {
			require : '^form',
			restrict : 'A',
			scope : true, // MUST HAVE SCOPE
			link : function($scope, element, attrs, form) {
				var load = function(validity) {
					element.removeClass("nb-force-hide")
					if (validity) {
						element.addClass("nb-force-hide")
					} else {
					}
				}
				$scope.nbvalidshow = form[attrs.nbvalidshow];
				if (typeof attrs.nbvalidshowerr == "string") {
					$scope.nbvalidshow = form[attrs.nbvalidshow];
					$scope.$watch('nbvalidshow.$error.' + attrs.nbvalidshowerr, function(validity) {
						var validity = !validity
						console.debug("[ValidShow] changed...", validity, attrs.nbvalidshow, form)
						load(validity)
					});
				} else {
					$scope.$watch('nbvalidshow.$valid', function(validity) {
						console.debug("[ValidShow] changed...", validity, attrs.nbvalidshow, form)
						load(validity)
					});
					load($scope.nbvalidshow.$valid)
				}
				console.debug("[ValidShow] loading...", attrs.nbvalidshow, $scope.nbvalidshow, form)
			}
		}
	} ]);

	// INJECT
	app.directive('inject', function() {
		return {
			restrict : 'EA',
			link : function($scope, $element, $attrs, controller, $transclude) {
				if (!$transclude) {
					throw 'Illegal use of ngTransclude directive in the template! ' + 'No parent directive that requires a transclusion found. '
							+ 'Element: ' + ($element);
				}
				var innerScope = $scope.$new();
				$transclude(innerScope, function(clone) {
					$element.empty();
					$element.append(clone);
					$element.on('$destroy', function() {
						innerScope.$destroy();
					});
				});
			}
		};
	});

	app.directive('bsHasError', [ function() {
		return {
			restrict : "A",
			link : function(scope, element, attrs, ctrl) {
				var input = element.find('[ng-model]');
				if (input) {
					scope.$watch(function() {
						return input.hasClass('ng-invalid');
					}, function(isInvalid) {
						element.toggleClass('has-error', isInvalid);
					});
				}
			}
		};
	} ]);
	app.directive('bsHasTitle', [ function() {
		return {
			restrict : "A",
			link : function(scope, element, attrs, ctrl) {
				var input = element.find('[ng-model]');
				if (input) {
					scope.$watch(function() {
						return input.hasClass('ng-invalid');
					}, function(isInvalid) {
						element.toggleClass('text-danger', isInvalid);
					});
				}
			}
		};
	} ]);
	app.directive('nbHaserror', [ function() {
		return {
			restrict : "A",
			compile : function(element, attrs) {
				var input = element.find('[ng-model],[data-ng-model]');
				input.attr("data-nb-haserror-watcher", "");
				return {
					post : function($scope, $element, attrs, ctrls) {
						$scope.$watch(function() {
							var invalid = false;
							ctrls.models.forEach(function(model) {
								var inv = model.model.$invalid;
								var exists = $element.find(model.element).length > 0;
								invalid = invalid || (inv && exists);
							})
							return invalid;
						}, function(isInvalid) {
							if (isInvalid) {
								$element.addClass("has-error")
							} else {
								$element.removeClass("has-error")
							}
						})
					}
				}
			},
			controller : function($scope, $element) {
				this.models = []
				this.addModel = function(model, element) {
					this.models.push({
						model : model,
						element : element
					})
				}
			},
		};
	} ]);

	app.directive('nbHaserrorWatcher', [ function() {
		return {
			restrict : "A",
			require : [ 'ngModel', '^nbHaserror' ],
			link : function(scope, element, attrs, ctrls) {
				ctrls[1].addModel(ctrls[0], element)
			}
		};
	} ]);
	app.directive('imgerror', function() {
		return {
			link : function(scope, element, attrs) {
				element.bind('error', function() {
					if (attrs.src != attrs.imgerror) {
						attrs.$set('src', attrs.imgerror);
					}
				});
			}
		}
	});
	app.directive('ifempty', function() {
		return {
			scope : {
				ifempty : "="
			},
			link : function(scope, element, attrs) {
				var onChange = function(val) {
					if (scope.$eval("ifempty.length>0")) {
						element.hide()
					} else {
						element.show()
					}
				}
				scope.$watch("ifempty", onChange)
				scope.$watch("ifempty.$resolved", onChange)
			}
		}
	});
	app.directive('nbdatetimeall', [ '$timeout', 'uiDatetimePickerConfig', function($timeout, uiDatetimePickerConfig) {
		return {
			compile : function(element, attrs) {
				var uid = "$$" + guid().replaceAll("-", "");
				var uid2 = "$$" + guid().replaceAll("-", "");
				var input = element.find("input[type=text]");
				input.attr("data-nbmask", "99/99/9999 99:99")
				input.attr("data-datetime-picker", "dd/MM/yyyy HH:mm")
				input.attr("data-is-open", uid)
				input.attr("data-datepicker-options", uid2)
				input.attr("data-timepicker-options", uid2)
				input.attr("data-min-date", attrs.minDate)
				//
				uiDatetimePickerConfig.appendToBody = false;
				uiDatetimePickerConfig.showButtonBar = false;
				return {
					pre : function(scope, element, attrs) {
						scope.$$NOW = new Date();
						scope[uid2] = {
							use24hours : true,
							showMeridian : false
						}
						var input = element.find("input");
						var button = element.find("button");
						var callback = function(e) {
							var isInBtn = jQuery.contains(button.get(0), e.target);
							var isBtn = e.target == button.get(0);
							if (isInBtn || isBtn) {
								e.stopPropagation()
								$timeout(function() {
									scope[uid] = !scope[uid];
								})
							} else {
								$timeout(function() {
									scope[uid] = false;
								})
							}
						}
						jQuery(document).click(callback)
					}
				}
			}
		}
	} ]);
	app.directive('nbdateall', [ '$timeout', function($timeout) {
		return {
			compile : function(element, attrs) {
				var uid = "$$" + guid().replaceAll("-", "");
				var uid2 = "$$" + guid().replaceAll("-", "");
				var input = element.find("input");
				input.attr("data-nbmask", "99/99/9999")
				input.attr("data-datetime-picker", "dd/MM/yyyy")
				input.attr("data-is-open", uid)
				input.attr("data-enable-time", "false")
				input.attr("data-datepicker-options", uid2)
				input.attr("data-min-date", attrs.minDate)
				//
				uiDatetimePickerConfig.appendToBody = false;
				uiDatetimePickerConfig.showButtonBar = false;
				return {
					pre : function(scope, element, attrs) {
						scope.$$NOW = new Date();
						scope[uid2] = {}
						var input = element.find("input");
						var button = element.find("button");
						var callback = function(e) {
							var isInBtn = jQuery.contains(button.get(0), e.target);
							var isBtn = e.target == button.get(0);
							if (isInBtn || isBtn) {
								e.stopPropagation()
								$timeout(function() {
									scope[uid] = !scope[uid];
								})
							} else {
								$timeout(function() {
									scope[uid] = false;
								})
							}
						}
						jQuery(document).click(callback)
					}
				}
			}
		}
	} ]);
	app.directive('nbtimeall', [ '$timeout', 'uiDatetimePickerConfig', function($timeout, uiDatetimePickerConfig) {
		return {
			compile : function(element, attrs) {
				var uid = "$$" + guid().replaceAll("-", "");
				var uid2 = "$$" + guid().replaceAll("-", "");
				var input = element.find("input");
				input.attr("data-nbmask", "99:99")
				input.attr("data-datetime-picker", "HH:mm")
				input.attr("data-is-open", uid)
				input.attr("data-enable-date", "false")
				input.attr("data-timepicker-options", uid2)
				input.attr("data-min-date", attrs.minDate)
				//
				uiDatetimePickerConfig.appendToBody = false;
				uiDatetimePickerConfig.showButtonBar = false;
				return {
					pre : function(scope, element, attrs) {
						scope.$$NOW = new Date();
						scope[uid2] = {
							showMeridian : false
						}
						var input = element.find("input");
						var button = element.find("button");
						var callback = function(e) {
							var isInBtn = jQuery.contains(button.get(0), e.target);
							var isBtn = e.target == button.get(0);
							if (isInBtn || isBtn) {
								e.stopPropagation()
								$timeout(function() {
									scope[uid] = !scope[uid];
								})
							} else {
								$timeout(function() {
									scope[uid] = false;
								})
							}
						}
						jQuery(document).click(callback)
					}
				}
			}
		}
	} ]);

})();
