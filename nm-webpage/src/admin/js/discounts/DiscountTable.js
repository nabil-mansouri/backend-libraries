(function(){
	//
	Wick.DEPENDENCIES.push("discount.table");
//	console.debug("Application discount table ",document.getElementById("appDiscountTable"));
	var discountTable = angular.module("discount.table", ['discount.service','commons.all','ui.bootstrap']);
	discountTable.controller('DiscountTable', [ '$scope',  'discountService','draftService',
			function($scope,discountService,draftService) {
				$scope.discountService=discountService;
				$scope.params={}
			} ]);	    
	
//	angular.bootstrap(document.getElementById("appDiscountTable"),["discount.table"]);
})();
