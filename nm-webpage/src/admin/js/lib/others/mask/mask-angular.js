(function() {
	angular.module('commons.plugins').directive('nbmask', function() {
		return {
			restrict : 'A',
			link : function(scope, elem, attr, ctrl) {

				if (attr.nbmask)
					elem.mask(attr.nbmask, {
						placeholder : attr.maskPlaceholder
					});
			}
		};
	});
})()