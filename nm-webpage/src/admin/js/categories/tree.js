(function() {
	//
	Wick.DEPENDENCIES.push("categories.tree");
	var module = angular.module("categories.tree", [ 'categories.service', 'commons.all', 'ui.bootstrap' ]);
	module.directive('categoryTree', function($rootScope, $controller) {
		return {
			restrict : 'E',
			scope : {
				categories : "="
			},
			templateUrl : 'category_tree.html',
			link : function($scope, element, attrs, controller) {
				$controller("CategoryListController", {
					$scope : $scope
				})
			}
		};
	});
	module.controller('CategoryListController', [ '$scope', '$rootScope', 'Category', 'ScopeService', 'CategoryFormView',
			function($scope, $rootScope, Category, ScopeService, CategoryFormView) {
				$scope.hasChildren = function(node) {
					return node && node.childrens.length > 0;
				}
				$scope.canExpand = function(node) {
					return $scope.hasChildren(node) && !node.expanded;
				}
				$scope.canUnExpand = function(node) {
					return $scope.hasChildren(node) && node.expanded;
				}
				$scope.toggle = function(node, $event) {
					$event && $event.stopPropagation()
					node.expanded = !node.expanded;
				}
				$scope.select = function(node) {
					node.selected = !node.selected;
				}
				$scope.edit = function(node, $event) {
					$event && $event.stopPropagation()
					$rootScope.$broadcast("AddSubview", CategoryFormView({
						params : {
							edit : node.id
						}
					}))
				}
				$scope.getCssClass = function(node) {
					if (node && node.selected) {
						return "bg-primary"
					}
				}
				$scope.isChildrenVisible = function(node) {
					return $scope.canUnExpand(node)
				}
				$scope.createRoot = function() {
					$rootScope.$broadcast("AddSubview", CategoryFormView())
				}
				$scope.createChild = function(parent) {
					$rootScope.$broadcast("AddSubview", CategoryFormView({
						params : {
							parent : parent.id
						}
					}))
				}
			} ]);
})();
