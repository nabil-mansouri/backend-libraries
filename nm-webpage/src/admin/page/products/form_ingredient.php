<form data-ng-controller="IngredientFormController" name="ingredientForm" data-ng-show="ingredient.$resolved" data-nbspinner=""
	boolean="ingredient.$resolved">
	<div class="col-md-12 products">
		<div class="col-md-6">
			<h5 class="col-md-12"><?php echo i18("ingredient.menu.form.desc")?></h5>
			<multicms cms="ingredient.cms" label-name="<?php echo i18("ingredient.menu.form.name")?>"
				label-desc="<?php echo i18("ingredient.menu.form.desc")?>" required-name="true" show-name="true" show-desc="true"></multicms>
		</div>
		<div class="col-md-6" nb-haserror>
			<h5 class="col-md-12 control-label"><?php  echo i18("form.label.images")?></h5>
			<multimg images="ingredient.images" mode="Single"></multimg>
		</div>
		<div class="clearfix col-md-12 voffset2">
			<div class="btn-group col-md-12 blank0" role="group">
				<button type="button" class="btn btn-danger col-md-4" data-ng-disabled="!ingredient.id" nbspinnerclick="remove()"><?php echo i18("delete")?></button>
				<button type="button" class="btn btn-default col-md-4" data-ng-click="cancel()"><?php echo i18("cancel")?></button>
				<button type="button" class="btn btn-success col-md-4" data-ng-disabled="ingredientForm.$invalid" nbspinnerclick="submit()"><?php echo i18("save")?></button>
			</div>
		</div>
	</div>
</form>