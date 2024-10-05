(function() {
	Wick.DEPENDENCIES.push("restaurants.constants");
	var module = angular.module("restaurants.constants", []);

	var Factory = function(params1) {
		var obj = jQuery.extend(params1, {});
		return function(params2) {
			var clone = angular.copy(obj);
			clone = jQuery.extend(clone, params2)
			return clone;
		}
	}
	module.value('RestoFormView', Factory({
		head : "resto_form_title.html",
		body : "resto_form.html"
	}));
	module.value('RestoPlanningFormView', Factory({
		head : "planning_form_title.html",
		body : "planning_form.html",
		cssClass:"view_full"
	}));

})();
