<form name="slotCloseForm" data-ng-controller="PlanningFormCloseController" class="row">
	<div class="form-group">
		<label class="control-label col-md-12   small"> <?php echo i18("planning.reccurent.close.info"); ?>
						</label>
		<div class="col-md-6 blank0 " nb-haserror>
			<p class="control-label col-md-12 small"> <?php echo i18("planning.reccurent.date.from"); ?>
						</p>
			<div class="col-md-12">
				<div class="input-group input-group-sm" nbdatetimeall data-min-date="$$NOW">
					<input type="text" class="form-control" ng-model="slot.dateBeginPlan" data-ng-required="true">
					<span class="input-group-btn">
						<button type="button" class="btn btn-primary">
							<i class="fa fa-calendar"></i>
						</button>
					</span>
				</div>
			</div>
		</div>
		<div class="col-md-6 blank0 " nb-haserror>
			<p class="control-label col-md-12 small"> <?php echo i18("planning.reccurent.date.to"); ?>
			</p>
			<div class="col-md-12">
				<div class="input-group input-group-sm nbdatepicker-right" nbdatetimeall data-min-date="slot.dateBeginPlan">
					<input type="text" class="form-control" ng-model="slot.dateEndPlan" data-ng-required="true">
					<span class="input-group-btn">
						<button type="button" class="btn btn-primary">
							<i class="fa fa-calendar"></i>
						</button>
					</span>
				</div>
			</div>
		</div>
		<p class="help-block col-md-12 small" nb-haserror>
			<input type="hidden" nb-valid-date-range="slot.dateEndPlan" nb-date-range-begin="slot.dateBeginPlan" data-ng-model="temp.neverexists" />
			<span class="control-label"><?php echo i18("planning.errors.date.range"); ?></span>
		</p>
	</div>
	<div class="form-group clearfix" nb-haserror>
		<label class="control-label col-md-12 small"> <?php echo i18("planning.reccurent.open.motif"); ?>
					</label>
		<div class="col-md-12 clearfix">
			<textarea class="form-control col-md-12 input-sm" ng-model="slot.cause" rows="3"></textarea>
		</div>
	</div>
	<button class="btn btn-success btn-sm col-md-12" type="button" nbspinnerclick="submit()" ng-disabled="slotCloseForm.$invalid">
		<i class="fa fa-add"></i>
		<?php echo i18("general.add"); ?>
	</button>
</form>