(function() {
	Wick.DEPENDENCIES.push("products.constants");
	var module = angular.module("products.constants", []);

	var Factory = function(params1) {
		var obj = jQuery.extend(params1, {});
		return function(params2) {
			var clone = angular.copy(obj);
			clone = jQuery.extend(clone, params2)
			return clone;
		}
	}
	module.value('ProductFormView', Factory({
		head : "product_form_title.html",
		body : "product_form.html"
	}));
	module.value('ProductDraftFormView', Factory({
		head : "product_draft_form_title.html",
		body : "product_draft_form.html"
	}));
	module.value('IngredientFormView', Factory({
		head : "ingredient_form_title.html",
		body : "ingredient_form.html",
	}));
	module.value('CategoryFormView', Factory({
		head : "category_form_title.html",
		body : "category_form.html",
	}));

})();
