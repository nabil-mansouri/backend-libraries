<div class="panel panel-grey" data-ng-controller="IngredientsListController">
	<div class="panel-heading" nbspinner promise="refreshPromise" nb-haserror>
		<label class="checkbox col-md-12 control-label">
			<input type="hidden" data-ng-model="neverExists" data-ng-required="product.hasIngredients && !hasAtLeastOne()" />
			<input type="checkbox" data-ng-model="product.hasIngredients" /><?php  echo i18("form.ingredient.title")?>
		</label>
	</div>
	<small class="help-block panel-help" readmore><?php  echo i18("form.ingredient.info")?>
		<em class="more">...</em>
	</small>
	<div class="panel-body  blank0" data-ng-class="getCssClass()">
		<ul class="list-group col-md-5 blank0 brdr">
			<li class="list-group-item sm pointer" data-ng-click="create()">
				<small><?php  echo i18("form.ingredient.create")?></small>
			</li>
			<li class="list-group-item sm checkbox" data-ng-repeat="ing in ingredients">
				<input type="checkbox" data-ng-model="ing.selected" />
				{{ing.cms.name}}
				<i class="fa fa-edit pull-right" data-ng-click="edit(ing)" />
			</li>
		</ul>
		<ul class="list-group col-md-7 blank0">
			<li class="list-group-item sm"><?php echo i18("form.ingredient.recap")?></li>
			<li class="list-group-item sm checkbox" data-ng-repeat="ing in ingredients" data-ng-model="ing.selected" data-ng-if="ing.selected">
				{{ing.cms.name}}
				<button class="btn btn-default btn-xs pull-right" data-ng-if="ing.facultatif" data-ng-click="toggle(ing)"><?php  echo i18("form.ingredient.perso")?></button>
				<button class="btn btn-default btn-xs pull-right" data-ng-if="!ing.facultatif" data-ng-click="toggle(ing)"><?php  echo i18("form.ingredient.perso.not")?></button>
			</li>
		</ul>
	</div>
</div>
