<form name="priceForm" data-nbspinner="" boolean="price.$resolved" data-ng-controller="PriceForm">
	<div class="col-md-12 prices">
		<div class="row">
			<div class="col-md-12 clearfix voffset2" data-ng-if="show.deleted.fail">
				<div class="alert alert-danger"><?php echo i18("form.price.delete.fail")?></div>
			</div>
			<div class="col-md-12 clearfix voffset2" data-ng-if="show.saved.fail">
				<div class="alert alert-danger"><?php echo i18("form.price.save.fail")?></div>
			</div>
			<div class="col-md-4">
				<?php  include 'form_filter.php';?>
			</div>
			<div class="col-md-8 price-product">
				<?php  include 'form_product.php';?>
			</div>
			<div class="clearfix col-md-12 voffset2">
				<div class="btn-group col-md-12 blank0" role="group">
					<button type="button" class="btn btn-danger col-md-4" data-ng-disabled="!price.id" nbspinnerclick="removePrice()"><?php echo i18("delete")?></button>
					<button type="button" class="btn btn-default col-md-4" nbspinnerclick="cancel()"><?php echo i18("cancel")?></button>
					<button type="button" class="btn btn-success col-md-4" data-ng-disabled="priceForm.$invalid" nbspinnerclick="submit()"><?php echo i18("save")?></button>
				</div>
			</div>
		</div>

		<div class="disable_panel" data-ng-show="price.configError">
			<h5 class="text-center"><?php  echo i18("form.label.config.error")?></h5>
			<p data-ng-if="price.noCurrency" class="text-center"><?php  echo i18("form.label.config.devise.error")?></p>
			<p data-ng-if="price.noOrderType" class="text-center"><?php  echo i18("form.label.config.ordertype.error")?></p>
			<p data-ng-if="price.noProducts" class="text-center"><?php  echo i18("form.label.config.products.error")?></p>

		</div>
	</div>
</form>