(function() {
	Wick.DEPENDENCIES.push("composer.router");
	// console.debug("Application product form:
	// ",document.getElementById("appCategoryForm"));
	var composerRouter = angular.module("composer.router", [ 'commons.all', 'ngRoute' ]);
	//
	composerRouter.config([ '$routeProvider', function($routeProvider) {
		$routeProvider.when('/products', {
			templateUrl : 'filter.html',
			controller : 'ComposeProducts'
		}).when('/products/notfound/:errorCode', {
			templateUrl : 'filter.html',
			controller : 'ComposeProducts'
		}).when('/products/:idCat', {
			templateUrl : 'filter.html',
			controller : 'OrderFilter'
		}).when('/compose/restaurants/', {
			templateUrl : 'restaurants.html',
			controller : 'ComposerRestaurant'
		}).when('/compose/restaurants/:idProd', {
			templateUrl : 'restaurants.html',
			controller : 'ComposerRestaurant'
		}).when('/compose/type/', {
			templateUrl : 'type.html',
			controller : 'ComposerType'
		}).when('/compose/type/:idProd', {
			templateUrl : 'type.html',
			controller : 'ComposerType'
		}).when('/compose/new/:idProd', {
			controller : 'ComposerNew',
			templateUrl : 'empty.html'
		}).when('/compose/choices/', {
			templateUrl : 'choices.html',
			controller : 'ComposerChoice'
		}).when('/compose/ingredients', {
			templateUrl : 'ingredients.html',
			controller : 'ComposerIngredient'
		}).when('/compose/finish/', {
			controller : 'ComposerFinish',
			templateUrl : 'empty.html',
		}).when('/compose/next', {
			templateUrl : 'empty.html',
			controller : 'ComposerNext'
		}).when('/compose/next/:idProd', {
			templateUrl : 'empty.html',
			controller : 'ComposerNext'
		}).when('/compose/reset', {
			redirectTo : "/products"
		}).otherwise({
			redirectTo : "/products"
		});
	} ]);
})();
