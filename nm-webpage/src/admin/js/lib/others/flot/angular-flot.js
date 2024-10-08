(function() {

	var app = angular.module('commons.services');
	//
	app.directive('jqflot', function() {
		return {
			restrict : 'EA',
			template : '<div></div>',
			scope : {
				dataset : '=',
				options : '=',
				callback : '='
			},
			link : function(scope, element, attributes) {
				var height, init, onDatasetChanged, onOptionsChanged, plot, plotArea, width, _ref, _ref1;
				var plot = null;
				width = attributes.width || '100%';
				height = attributes.height || '100%';
				if (((_ref = scope.options) != null ? (_ref1 = _ref.legend) != null ? _ref1.container : void 0 : void 0) instanceof jQuery) {
					throw 'Please use a jQuery expression string with the "legend.container" option.';
				}
				if (!scope.dataset) {
					scope.dataset = [];
				}
				if (!scope.options) {
					scope.options = {
						legend : {
							show : false
						}
					};
				}
				plotArea = $(element.children()[0]);
				plotArea.css({
					width : width,
					height : height
				});
				init = function() {
					var plotObj;
					plotObj = $.plot(plotArea, scope.dataset, scope.options);
					if (scope.callback) {
						scope.callback(plotObj);
					}
					return plotObj;
				};
				onDatasetChanged = function(dataset) {
					if (plot) {
						plot.destroy()
					}
					return plot = init();

				};
				scope.$watch('dataset', onDatasetChanged, true);
				onOptionsChanged = function() {
					return plot = init();
				};
				return scope.$watch('options', onOptionsChanged, true);
			}
		};
	});
})()