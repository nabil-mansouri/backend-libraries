(function() {
	Wick.DEPENDENCIES.push("cart.router");
	// console.debug("Application product form:
	// ",document.getElementById("appCategoryForm"));
	var cartForm = angular.module("cart.router", [ 'commons.all', 'ngRoute' ]);
	//
	cartForm.config([ '$routeProvider', function($routeProvider) {
		$routeProvider.when('/cart/draft', {
			templateUrl : 'cart_draft.html',
			controller : 'CartDraft'
		}).when('/cart/check', {
			templateUrl : 'cart_check.html',
			controller : 'CartCheck'
		}).when('/cart/client', {
			templateUrl : 'client_cart.html',
			controller : 'ClientCart'
		}).when('/cart/paiement', {
			templateUrl : 'cash_paiment.html',
			controller : 'CashPaiement'
		})
		.when('/cart/next', {
			templateUrl : 'empty.html',
			controller : 'CartNext'
		})
		//
		.when('/cart/success', {
			templateUrl : 'cart_submit.html',
			controller : 'CartSubmit'
		});
	} ]);
})();
