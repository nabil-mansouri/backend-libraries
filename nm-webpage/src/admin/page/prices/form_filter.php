
<div class="col-md-6 blank0 " nb-haserror>
	<label class="control-label col-md-12 small blankl0"> <?php echo i18("tarif.table.plan.begin"); ?>
						</label>
	<div class="col-md-12 blankl0">
		<div class="input-group input-group-sm" nbdatetimeall data-min-date="$$NOW" data-ng-model="price.filter.from">
			<span class="input-group-addon">
				<input type="checkbox" aria-label="..." data-ng-model="price.filter.hasFrom">
			</span>
			<input type="text" class="form-control" ng-model="price.filter.from" data-ng-required="price.filter.hasFrom"
				data-ng-disabled="!price.filter.hasFrom">
			<span class="input-group-btn">
				<button type="button" class="btn btn-primary" data-ng-disabled="!price.filter.hasFrom">
					<i class="fa fa-calendar"></i>
				</button>
			</span>
		</div>
	</div>
</div>
<div class="col-md-6 blank0 " nb-haserror>
	<label class="control-label col-md-12 small blankr0"> <?php echo i18("tarif.table.plan.end"); ?>
					</label>
	<div class="col-md-12 blankr0">
		<div class="input-group input-group-sm nbdatepicker-right" nbdatetimeall data-min-date="temp.minto" data-ng-model="price.filter.to">
			<span class="input-group-addon">
				<input type="checkbox" aria-label="..." data-ng-model="price.filter.hasTo">
			</span>
			<input type="text" class="form-control" data-ng-model="price.filter.to" data-ng-required="price.filter.hasTo"
				data-ng-disabled="!price.filter.hasTo">
			<span class="input-group-btn">
				<button type="button" class="btn btn-primary" data-ng-disabled="!price.filter.hasTo">
					<i class="fa fa-calendar"></i>
				</button>
			</span>
		</div>
	</div>
</div>
<div class="col-md-6 blank0 " nb-haserror>
	<label class="control-label col-md-12 small blankl0"> <?php echo i18("tarif.table.restaurants"); ?>
					</label>
	<div class="col-md-12 blankl0">
		<div class="input-group input-group-sm nbdatepicker-right">
			<span class="input-group-addon">
				<input type="checkbox" aria-label="..." data-ng-model="price.filter.allRestaurants">
			</span>
			<div multiple="multiple" class="form-control rows_3" data-ng-disabled="price.filter.allRestaurants">
				<div data-ng-if="price.filter.allRestaurants"><?php echo i18("all")?></div>
				<div data-ng-if="!price.filter.allRestaurants" data-ng-repeat="r in price.filter.restaurants" nbselected="r.selected" class="pointer">{{r.cms.name}}</div>
			</div>
		</div>
	</div>
</div>
<div class="col-md-6 blank0 " nb-haserror>
	<label class="control-label col-md-12 small blankr0"> <?php echo i18("tarif.table.ordertype"); ?> 
					</label>
	<div class="col-md-12 blankr0">
		<div class="input-group input-group-sm">
			<span class="input-group-addon">
				<input type="checkbox" aria-label="..." data-ng-model="price.filter.allOrders">
			</span>
			<div multiple="multiple" class="form-control rows_3" data-ng-disabled="price.filter.allOrders">
				<div data-ng-if="price.filter.allOrders"><?php echo i18("all")?></div>
				<div data-ng-if="!price.filter.allOrders" data-ng-repeat="r in price.filter.types" nbselected="r.selected" class="pointer">{{r.orderType |
					ordertype}}</div>
			</div>
		</div>
	</div>
</div>