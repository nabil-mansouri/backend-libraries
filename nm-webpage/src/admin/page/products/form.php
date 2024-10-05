<form  name="productForm" data-nbspinner="" boolean="product.$resolved">
	<div class="col-md-12 products" data-ng-show="isVisible()">
		<div class="col-md-6">
			<h5 class="col-md-12"><?php echo i18("product.menu.form.desc")?></h5>
			<multicms cms="product.cms" label-name="<?php echo i18("product.menu.form.name")?>" label-desc="<?php echo i18("product.menu.form.desc")?>"
				label-keywords="<?php  echo i18("product.menu.form.keywords")?>" required-name="true" show-name="true" show-desc="true" show-keywords="true"></multicms>
		</div>
		<div class="col-md-6" nb-haserror>
			<h5 class="col-md-12 control-label"><?php  echo i18("form.label.images")?></h5>
			<multimg images="product.images" required-img="true" mode="Multi"></multimg>
		</div>
		<div class="clearfix col-md-12  blank0">
			<div class="col-md-3 voffset2">
				<div data-ng-if="product.$resolved">
					<category-tree categories="product.categories"></category-tree>
				</div>
			</div>
			<div class="col-md-4 voffset2">
				<div data-ng-if="product.$resolved">
					<subingredients product="product" ingredients="product.ingredients"></subingredients>
				</div>
			</div>
			<div class="col-md-5 voffset2">
				<subproducts product="product" parts="product.parts"></subproducts>
			</div>
		</div>
		<div class="clearfix col-md-12 voffset2">
			<div class="btn-group col-md-12 blank0" role="group">
				<button type="button" class="btn btn-danger col-md-3" data-ng-disabled="!product.id" nbspinnerclick="remove()"><?php echo i18("delete")?></button>
				<button type="button" class="btn btn-default col-md-3" data-ng-click="cancel()"><?php echo i18("cancel")?></button>
				<button type="button" class="btn btn-default col-md-3" nbspinnerclick="draft()"><?php echo i18("draft")?></button>
				<button type="button" class="btn btn-success col-md-3" data-ng-disabled="productForm.$invalid" nbspinnerclick="submit()"><?php echo i18("save")?></button>
			</div>
		</div>
	</div>
	<div class="disable_panel" data-ng-show="product.configError" data-ng-click="cancelConfig()">
		<h5 class="text-center"><?php  echo i18("form.label.config.error")?></h5>
		<p data-ng-if="product.cms.noDefaultLang" class="text-center"><?php  echo i18("form.label.config.defaut.error")?></p>
		<p data-ng-if="product.cms.noSelectedLang" class="text-center"><?php  echo i18("form.label.config.selected.error")?></p>
	</div>
</form>