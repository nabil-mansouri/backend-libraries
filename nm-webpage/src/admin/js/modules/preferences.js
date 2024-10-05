var app = angular.module('commons.directives');
// Table
app.directive('preferences', [ '$q', 'Locale', 'Currency', "CurrencyService", 'LocaleService', 'Ordertype',
		function($q, Locale, Currency, CurrencyService, LocaleService, Ordertype) {
			return {
				scope : {},
				templateUrl : "preferences.html",
				link : function($scope, elem, attrs, controller) {
					$scope.reload = function() {
						$scope.currencies = CurrencyService.getCurrencies()
						$scope.localeDefaults = LocaleService.getDefault()
						$scope.locales = LocaleService.getSelected();
						$scope.orders = Ordertype.query()
						// 
					}
					$scope.reload();
				}
			}
		} ]);

app.controller('AbstractPreference', [ '$scope', '$rootScope', function($scope, $rootScope) {
	$scope.init = function(value) {
		$scope.value;
		$scope.show = {}
	}
	$scope.edit = function() {
		$scope.$eval("show.edit=true")
	}
	$scope.cancel = function() {
		$scope.$eval("show.edit=false")
		$scope.reload();
	}
	$scope.canEdit = function() {
		return $scope.$eval("show.edit")
	}
	$scope.isEmpty = function(arr, prop) {
		var founded = false;
		if (!arr || !arr.forEach) {
			return !founded;
		}
		arr.forEach(function(val) {
			if (val[prop]) {
				founded = true;
				return false;
			}
		})
		return !founded;
	}
} ]);
app.controller('PreferenceLocales', [ '$scope', '$rootScope', '$controller', 'Locale', function($scope, $rootScope, $controller, Locale) {
	$controller('AbstractPreference', {
		$scope : $scope
	})
	$scope.submit = function() {
		Locale.saveSelect($scope.locales).$promise.then($scope.cancel, $scope.cancel)
	}
	$scope.canShowEmpty = function() {
		return $scope.isEmptyValue() && !$scope.canEdit()
	}
	$scope.isEmptyValue = function() {
		return $scope.isEmpty($scope.locales, 'selected')
	}
	$scope.isVisible = function(locale) {
		return $scope.canEdit() || locale.selected;
	}
} ]);
app.controller('PreferenceLocalesDefault', [ '$scope', '$rootScope', '$controller', 'Locale', function($scope, $rootScope, $controller, Locale) {
	$controller('AbstractPreference', {
		$scope : $scope
	})
	$scope.submit = function() {
		Locale.saveDefault($scope.localeDefaults).$promise.then($scope.cancel, $scope.cancel)
	}
	$scope.canShowEmpty = function() {
		return $scope.isEmptyValue() && !$scope.canEdit()
	}
	$scope.isEmptyValue = function() {
		return $scope.isEmpty($scope.localeDefaults, 'defaut')
	}
	$scope.isVisible = function(locale) {
		return $scope.canEdit() || locale.defaut;
	}
} ]);
app.controller('PreferenceCurrencies', [ '$scope', '$rootScope', '$controller', 'Currency', function($scope, $rootScope, $controller, Currency) {
	$controller('AbstractPreference', {
		$scope : $scope
	})
	$scope.submit = function() {
		Currency.saveSelect($scope.currencies).$promise.then($scope.cancel, $scope.cancel)
	}
	$scope.canShowEmpty = function() {
		return $scope.isEmptyValue() && !$scope.canEdit()
	}
	$scope.isEmptyValue = function() {
		return $scope.isEmpty($scope.currencies, 'defaut')
	}
	$scope.isVisible = function(cur) {
		return $scope.canEdit() || cur.defaut;
	}
} ]);
app.controller('PreferenceOrderType', [ '$scope', '$rootScope', '$controller', 'Ordertype', function($scope, $rootScope, $controller, Ordertype) {
	$controller('AbstractPreference', {
		$scope : $scope
	})
	$scope.submit = function() {
		Ordertype.save($scope.orders).$promise.then($scope.cancel, $scope.cancel)
	}
	$scope.canShowEmpty = function() {
		return $scope.isEmptyValue() && !$scope.canEdit()
	}
	$scope.isEmptyValue = function() {
		return $scope.isEmpty($scope.orders, 'selected')
	}
	$scope.isVisible = function(cur) {
		return $scope.canEdit() || cur.selected;
	}
} ]);