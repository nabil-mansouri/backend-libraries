<form data-ng-controller="CategoryFormController" name="categoryForm" data-ng-show="category.$resolved" data-nbspinner="" boolean="category.$resolved">
	<div class="col-md-12 products">
		<div class="col-md-6">
			<h5 class="col-md-12"><?php echo i18("category.menu.form.desc")?></h5>
			<multicms cms="category.cms" label-name="<?php echo i18("category.menu.form.name")?>" label-desc="<?php echo i18("category.menu.form.desc")?>"
				required-name="true" show-name="true" show-desc="true"></multicms>
		</div>
		<div class="col-md-6" nb-haserror>
			<h5 class="col-md-12 control-label"><?php  echo i18("form.label.images")?></h5>
			<multimg images="category.images" mode="Single"></multimg>
		</div>
		<div class="col-md-12 clearfix voffset2">
			<div class="alert alert-danger" data-ng-if="show.deleted.fail"><?php echo i18("form.category.delete.fail")?></div>
		</div>
		<div class="clearfix col-md-12 voffset2">
			<div class="btn-group col-md-12 blank0" role="group">
				<button type="button" class="btn btn-danger col-md-4" data-ng-disabled="!category.id" nbspinnerclick="remove()"><?php echo i18("delete")?></button>
				<button type="button" class="btn btn-default col-md-4" data-ng-click="cancel()"><?php echo i18("cancel")?></button>
				<button type="button" class="btn btn-success col-md-4" data-ng-disabled="categoryForm.$invalid" nbspinnerclick="submit()"><?php echo i18("save")?></button>
			</div>
		</div>
	</div>
</form>