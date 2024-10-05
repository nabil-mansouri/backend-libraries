<div nb-has-error class="col-md-12">
	<input type="hidden" ng-model="discount.decreaserule" ng-required="true" />
	<div class="form-group col-sm-3 well" ng-class="{'bg-green': isActive('Global')}">
		<label class="col-sm-12"><?php echo i18("discount.decreaserule.global"); ?></label>
		<div class="col-sm-12">
			<button class="btn btn-yellow btn-sm" type="button" ng-click="set('Global')"><?php echo i18('discount.form.click'); ?></button>
		</div>
	</div>
	<div class="form-group col-sm-3 col-sm-offset-1 well"
		ng-class="{'bg-green': isActive('Detail')}">
		<label class="col-sm-12"><?php echo i18("discount.decreaserule.detail"); ?></label>
		<div class="col-sm-12">
			<button class="btn btn-yellow btn-sm" type="button" ng-click="set('Detail')"><?php echo i18('discount.form.click'); ?></button>
		</div>
	</div>
</div>
<div class="col-md-12" ng-if="isActive('Global')">
	<div class="form-group col-md-12" ng-repeat="rule in discount.rules">
		<p><?php i18e("discount.decreaserule.title")?> {{$index+1}}</p>
		<label class="control-label col-md-4">
			<?php i18e("discount.decreaserule.select")?>			
		</label>
		<div class="col-md-4" nb-has-error>
			<select ng-model="rule.type" class="form-control" ng-required="true">
				<option value="TaxFree"><?php i18e("discount.decrease.operation.taxfree")?></option>
				<option value="SuppFree"><?php i18e("discount.decrease.operation.suppfree")?></option>
				<option value="Free"><?php i18e("discount.decrease.operation.free")?></option>
				<option value="Percentage"><?php i18e("discount.decrease.operation.percent")?></option>
				<option value="Fixe"><?php i18e("discount.decrease.operation.fixe")?></option>
				<option value="Manual"><?php i18e("discount.decrease.operation.manual")?></option>
			</select>
		</div>
		<div class="col-md-3" nb-has-error>
			<div class="input-group input-group-sm"
				ng-if="rule.type=='Fixe' || rule.type=='Manual'">
				<input type="number" class="form-control" ng-model="rule.value" ng-required="true"
					style="padding: 0" /> <span class="input-group-addon">&euro;</span>
			</div>
			<div class="input-group input-group-sm" ng-if="rule.type=='Percentage'">
				<input type="number" class="form-control" ng-model="rule.value" ng-required="true"
					style="padding: 0" /> <span class="input-group-addon">%</span>
			</div>
		</div>
		<div class="col-md-1">
			<a href="javascript:;" class="text-danger fa fa-times"
				ng-click="deleteRule($index)" ng-if="$index>0"></a>
		</div>
	</div>
	<div class="form-group col-md-12">
		<button ng-click="addRule()" class="btn btn-primary"><?php i18e("discount.decreaserule.add")?></button>
	</div>
</div>
<div class="col-md-6" ng-if="isActive('Detail')">
	<?php require_once 'form_decrease_products.php';?>				
</div>
<div class="col-md-6" ng-if="isActive('Detail')">
	<?php require_once 'form_decrease_prices.php';?>			
</div>