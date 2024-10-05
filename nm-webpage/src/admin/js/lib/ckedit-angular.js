(function() {
	var app = angular.module('ckedit', []);
	var $defer, loaded = false;

	app.run([ '$q', '$timeout', function($q, $timeout) {
		$defer = $q.defer();

		if (angular.isUndefined(CKEDITOR)) {
			throw new Error('CKEDITOR not found');
		}
		CKEDITOR.disableAutoInline = true;
		function checkLoaded() {
			if (CKEDITOR.status == 'loaded') {
				loaded = true;
				$defer.resolve();
			} else {
				checkLoaded();
			}
		}
		CKEDITOR.on('loaded', checkLoaded);
		$timeout(checkLoaded, 100);
	} ])

	app.directive('ckedit', [ '$timeout', '$q', '$parse', function($timeout, $q, $parse) {
		'use strict';
		var counter = 0, prefix = '__ckd_';
		return {
			restrict : 'AC',
			require : [ 'ngModel', '^?form' ],
			scope : false,
			link : function(scope, element, attrs, ctrls) {
				var getterText = $parse(attrs.cktext), setterText = getterText.assign;
				var ngModel = ctrls[0];
				var form = ctrls[1] || null;
				var EMPTY_HTML = '<p></p>', isTextarea = element[0].tagName.toLowerCase() == 'textarea', data = [], isReady = false;

				element.attr('contenteditable', true);
				if (!attrs.id) {
					attrs.$set('id', prefix + (++counter));
				}
				var onLoad = function() {
					var options = {
						toolbar : [ {
							name : 'basicstyles',
							items : [ 'Bold', 'Italic', 'Strike', 'Underline' ]
						}, {
							name : 'paragraph',
							items : [ 'BulletedList', 'NumberedList', 'Blockquote' ]
						}, {
							name : 'editing',
							items : [ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ]
						}, {
							name : 'links',
							items : [ 'Link', 'Unlink', 'Anchor' ]
						}, {
							name : 'tools',
							items : [ 'SpellChecker', 'Maximize' ]
						}, '/', {
							name : 'styles',
							items : [ 'Format', 'FontSize', 'TextColor', 'PasteText', 'PasteFromWord', 'RemoveFormat' ]
						}, {
							name : 'insert',
							items : [ 'Image','imgembed', 'Table', 'SpecialChar' ]
						}, {
							name : 'forms',
							items : [ 'Outdent', 'Indent' ]
						}, {
							name : 'clipboard',
							items : [ 'Undo', 'Redo' ]
						}, {
							name : 'document',
							items : [ 'PageBreak', 'Source' ]
						} ],
						colorButton_enableMore : true,
						extraPlugins : 'colorbutton,colordialog,image,justify,imgembed',
						disableNativeSpellChecker : false,
						uiColor : '#FAFAFA',
						height : '300px',
						width : '100%'
					};
					options = angular.extend(options, scope[attrs.ckeditor]);

					// you can use ckreadonly attribute to bind a variable
					// to set the editor readOnly status
					if (attrs.ckreadonly) {
						// if ckreadonly attribute is present,
						// set editor readOnly option
						var isReadOnly = scope.$eval(attrs.ckreadonly);
						options.readOnly = isReadOnly;

						// setup a watch on the attribute value
						// to update the editor readOnly mode
						// when value changes
						scope.$watch(attrs.ckreadonly, function(value) {
							// ignore callback if editable instance
							// is not ready yet
							if (instance && isReady) {
								instance.setReadOnly(value);
							}
						});
					}

					var instance = (isTextarea) ? CKEDITOR.replace(element[0], options) : CKEDITOR.inline(element[0], options), configLoaderDef = $q.defer();

					element.bind('$destroy', function() {
						instance.destroy(false // If the instance is replacing
												// a DOM element, this parameter
												// indicates whether or not to
												// update the element with the
												// instance contents.
						);
					});
					var lastData = null;
					var compare = function(str1, str2) {
						return (typeof str1 == "string") && (typeof str2 == "string") && str1 == str2;
					}
					var promise = null;
					var setModelData = function(setPristine) {
						var data = instance.getData();
						if (data == '') {
							data = null;
						}
						lastData = data;
						if (promise) {
							$timeout.cancel(promise)
						}
						promise = $timeout(function() { // for key up event
							if (setPristine !== true || data != ngModel.$viewValue) {
								ngModel.$setViewValue(data)
								setterText(scope, jQuery(data).text())
							}
							(setPristine === true && form) && form.$setPristine();
						}, 100);
					}, onUpdateModelData = function(setPristine) {
						if (!data.length) {
							return;
						}
						var item = data.pop() || EMPTY_HTML;
						isReady = false;
						instance.setData(item, function() {
							setModelData(setPristine);
							isReady = true;
						});
					}

					// instance.on('pasteState', setModelData);
					instance.on('change', setModelData);
					instance.on('blur', setModelData);
					// instance.on('key', setModelData); // for source view

					instance.on('instanceReady', function() {
						scope.$broadcast("ckeditor.ready");
						instance.setReadOnly(false);// MUST BE SETTED
						scope.$apply(function() {
							onUpdateModelData(true);
						});
						instance.document && instance.document.on("keyup", setModelData);
					});
					instance.on('customConfigLoaded', function() {
						configLoaderDef.resolve();
					});

					ngModel.$render = function() {
						if (ngModel.$viewValue) {
							data.push(ngModel.$viewValue);
						}
						if (isReady) {
							onUpdateModelData();
						}
					};
				};

				if (CKEDITOR.status == 'loaded') {
					loaded = true;
				}
				if (loaded) {
					onLoad();
				} else {
					$defer.promise.then(onLoad);
				}
			}
		};
	} ]);
})();
