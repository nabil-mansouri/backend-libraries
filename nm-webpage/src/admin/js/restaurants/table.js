(function() {
	//
	Wick.DEPENDENCIES.push("restaurants.table");
	var module = angular.module("restaurants.table", [ 'restaurants.services', 'commons.all', 'ui.bootstrap' ]);
	module.config([ '$routeProvider', function($routeProvider) {
		$routeProvider.when('/', {
			templateUrl : 'resto_table.html',
			controller : 'RestaurantTable'
		}).when('/planning/:id', {
			templateUrl : 'resto_table.html',
			controller : 'RestaurantPlanningTable'
		}).otherwise({
			redirectTo : '/'
		});
	} ]);
	module.controller('RestaurantMenuTable', [ '$scope', '$rootScope','RestoFormView', function($scope, $rootScope,RestoFormView) {
		$scope.create = function() {
			$rootScope.$broadcast("AddSubview", RestoFormView())
		}
		$scope.$on("RestaurantDeleted",function(){
			$scope.goToRefresh("/")
		})
		$scope.$on("RestaurantSaved",function(e,saved){
			if(saved.isnew){
				$scope.goToRefresh("/planning/"+saved.id)
			}else{
				$scope.goToRefresh("/")
			}
		}) 
	} ]);
	module.controller('RestaurantTable', [ '$scope', '$rootScope', 'Restaurant','RestoFormView', function($scope, $rootScope, Restaurant,RestoFormView) {
		$scope.reload = function() {
			$scope.restos = Restaurant.query()
		}
		$scope.reload() 
		$scope.planning=function(resto){
			$scope.goTo("/planning/"+resto.id)
		}
		$scope.edit = function(resto) {
			$rootScope.$broadcast("AddSubview", RestoFormView({
				params : {
					edit : resto.id
				}
			}))
		}
		$scope.confirmDelete = function(resto) {
			return Restaurant.delete({
				id:resto.id
			}).$promise.then(function(){
				$scope.goToRefresh("/")
			})
		}
		$scope.isEmpty = function() { 
			return $scope.restos.length == 0;
		}
	} ]);
	module.controller('RestaurantPlanningTable', [ '$scope', '$rootScope', '$controller','RestoPlanningFormView','$routeParams', function($scope, $rootScope, $controller,RestoPlanningFormView,$routeParams) {
		$controller('RestaurantTable',{
			$scope:$scope
		})
		$rootScope.$broadcast("AddSubview", RestoPlanningFormView({
				params : {
					planning:$routeParams.id 
				}
			}))
	} ]);
})();
