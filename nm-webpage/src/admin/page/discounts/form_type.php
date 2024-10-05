<div nb-has-error ng-controller="DiscountType" class="col-md-12">
	<input type="hidden" ng-model="discount.type" ng-required="true" />
	<div class="form-group col-sm-2 well" ng-class="{'bg-green': isActive('Product')}">
		<label class="col-sm-12"><?php echo i18("discount.form.product"); ?></label>
		<div class="col-sm-12">
			<button class="btn btn-yellow btn-sm" type="button" ng-click="set('Product')"><?php echo i18('discount.form.click'); ?></button>
		</div>
	</div>
	<div class="form-group col-sm-2 col-sm-offset-1 well"
		ng-class="{'bg-green': isActive('Decrease')}">
		<label class="col-sm-12"><?php echo i18("discount.form.decrease"); ?></label>
		<div class="col-sm-12">
			<button class="btn btn-yellow btn-sm" type="button" ng-click="set('Decrease')"><?php echo i18('discount.form.click'); ?></button>
		</div>
	</div>
	<div class="form-group col-sm-2 col-sm-offset-1 well"
		ng-class="{'bg-green': isActive('Gift')}">
		<label class="col-sm-12"><?php echo i18("discount.form.gift"); ?></label>
		<div class="col-sm-12">
			<button class="btn btn-yellow btn-sm" type="button" ng-click="set('Gift')"><?php echo i18('discount.form.click'); ?></button>
		</div>
	</div>
	<div class="form-group col-sm-2 col-sm-offset-1 well"
		ng-class="{'bg-green': isActive('Free')}">
		<label class="col-sm-12"><?php echo i18("discount.form.free"); ?></label>
		<div class="col-sm-12">
			<button class="btn btn-yellow btn-sm" type="button" ng-click="set('Free')"><?php echo i18('discount.form.click'); ?></button>
		</div>
	</div>
</div>
