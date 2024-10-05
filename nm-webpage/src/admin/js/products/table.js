(function() {
	//
	Wick.DEPENDENCIES.push("products.table");
	var module = angular.module("products.table", [ 'products.service', 'commons.all', 'ui.bootstrap' ]);
	module.config([ '$routeProvider', function($routeProvider) {
		$routeProvider.when('/products', {
			templateUrl : 'product_table.html',
			controller : 'ProductTable'
		}).when('/drafts', {
			templateUrl : 'product_table.html',
			controller : 'ProductDraft'
		}) .otherwise({
			redirectTo : '/products'
		});
	} ]);
	module.controller('ProductMenuTable', [ '$scope', '$rootScope','ProductFormView', function($scope, $rootScope,ProductFormView) {
		$scope.create = function() {
			$rootScope.$broadcast("AddSubview", ProductFormView())
		}
		$scope.table = function() {
			$scope.$eval("show=false")
			$scope.$eval("show.table=true")
		}
		$scope.drafts = function() {
			$scope.$eval("show=false")
			$scope.$eval("show.drafts=true")
		}
		$scope.$on("ProductDeleted",function(){
			$scope.goToRefresh("/products")
		})
		$scope.$on("ProductSaved",function(){
			$scope.goToRefresh("/products")
		}) 
		$scope.$on("ProductDraftSaved",function(){
			$scope.goToRefresh("/drafts")
		})
		$scope.$on("ProductDraftDeleted",function(){
			$scope.goToRefresh("/drafts")
		})
	} ]);
	module.controller('AbstractProductTable', [ '$scope', '$rootScope', 'Entity', function($scope, $rootScope, Entity) {
		$scope.reload = function() {
			$scope.products = Entity.query()
		}
		$scope.reload() 
	} ]);
	module.controller('ProductTable', [ '$scope', '$rootScope', '$controller', 'Product','ProductFormView', function($scope, $rootScope, $controller, Product,ProductFormView) {
		$controller("AbstractProductTable", {
			$scope : $scope,
			Entity : Product
		}) 
		$scope.publish=function(product){
			Product.publish({
				id:product.id
			}).$promise.then(function(data){
				product.state=data.state;
				product.dateState=data.dateState;
			})
		}
		$scope.unpublish=function(product){
			Product.unpublish({
				id:product.id
			}).$promise.then(function(data){
				product.state=data.state;
				product.dateState=data.dateState;
			})
		}
		$scope.stats = function(product) {

		}
		$scope.price = function(product) {

		}
		$scope.edit = function(product) {
			$rootScope.$broadcast("AddSubview", ProductFormView({
				params : {
					edit : product.id
				}
			}))
		}
		$scope.confirmDelete = function(product) {
			return Product.delete({
				id:product.id
			}).$promise.then(function(){
				$scope.goToRefresh("/products")
			})
		}
		$scope.isNotDraft=function(){
			return true;
		}
		$scope.getCssProduct=function(){
			return "active"
		}
	} ]);
	module.controller('ProductDraft', [ '$scope', '$rootScope', '$controller', 'ProductDraft','ProductDraftFormView',
			function($scope, $rootScope, $controller, ProductDraft,ProductDraftFormView) {
				$controller("AbstractProductTable", {
					$scope : $scope,
					Entity : ProductDraft
				})
				$scope.edit = function(product) {
					$rootScope.$broadcast("AddSubview", ProductDraftFormView({
						params : {
							draft : product.idDraft,
							draftObject : product
						}
					}))
				}
				$scope.confirmDelete = function(product) {
					return ProductDraft.delete({
						id:product.idDraft
					}).$promise.then(function(){
						$scope.goToRefresh("/drafts")
					})
				}
				$scope.isDraft=function(){
					return true;
				}
				$scope.getCssDraft=function(){
					return "active"
				}
			} ]);
})();
