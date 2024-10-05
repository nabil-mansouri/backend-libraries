<form data-ng-controller="PlanningFormRecurrentController" name="slotForm" class="row">
	<div class="form-group " nb-haserror>
		<label class="control-label col-md-12 small"><?php echo i18("planning.reccurent.add.days"); ?>
		</label>
		<div class="col-md-12">
			<select class="form-control  input-sm" multiple="multiple" data-ng-model="slot.planningDays" data-ng-required="true">
				<option value="AllDays">
					<?php echo i18("days.all"); ?>
				</option>
				<option value="Monday">
					<?php echo i18("days.monday"); ?>					
				</option>
				<option value="Tuesday">
					<?php echo i18("days.tuesday"); ?>
				</option>
				<option value="Wednesday">
					<?php echo i18("days.wednesday"); ?>
				</option>
				<option value="Thirsday">
					<?php echo i18("days.thirsday"); ?>
				</option>
				<option value="Friday">
					<?php echo i18("days.friday"); ?>
				</option>
				<option value="Saturday">
					<?php echo i18("days.saturday"); ?>
				</option>
				<option value="Monday">
					<?php echo i18("days.sunday"); ?>
				</option>
			</select>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-12   small"> <?php echo i18("planning.reccurent.hour.title"); ?>
						</label>
		<div class="col-md-6 blank0" nb-haserror>
			<span class="control-label col-md-12 small"> <?php echo i18("planning.reccurent.hour.from"); ?>
						</span>
			<div class="col-md-12">
				<div class="input-group input-group-sm" nbtimeall>
					<input type="text" class="form-control  input-sm" data-ng-required="true" ng-model="slot.dateBeginHoraire" data-placement="left">
					<span class="input-group-btn ">
						<button class="btn btn-default" type="button">
							<i class="fa fa-clock-o"></i>
						</button>
					</span>
				</div>
			</div>
		</div>
		<div class="col-md-6 blank0" nb-haserror>
			<span class="control-label col-md-12  small"> <?php echo i18("planning.reccurent.hour.to"); ?>
						</span>
			<div class="col-md-12">
				<div class="input-group input-group-sm " nbtimeall>
					<input type="text" class="form-control input-sm" ng-model="slot.dateEndHoraire" data-ng-required="true" data-placement="left">
					<span class="input-group-btn">
						<button class="btn btn-default" type="button">
							<i class="fa fa-clock-o"></i>
						</button>
					</span>
				</div>
			</div>
		</div>
		<span class="help-block col-md-12 small" nb-haserror>
			<input type="hidden" nb-valid-date-range="slot.dateEndHoraire" nb-date-range-begin="slot.dateBeginHoraire" data-ng-model="temp.neverexists" />
			<span class="control-label"><?php echo i18("planning.errors.hours.range"); ?></span>
		</span>
		<div class="checkbox col-md-12">
			<label class="col-md-12 control-label small ">
				<input type="checkbox" data-ng-model="slot.hasNoLimit">
				 <?php echo i18("planning.reccurent.days.limit"); ?>
			</label>
		</div>
	</div>
	<div class="form-group" data-ng-if="!slot.hasNoLimit">
		<div class="col-md-12 blank0">
			<label class="control-label col-md-12   small"> <?php echo i18("planning.reccurent.preiod.title"); ?>
						</label>
		</div>
		<div class="col-md-6 blank0" nb-haserror>
			<span class="control-label col-md-12 small"> <?php echo i18("planning.reccurent.hour.from"); ?>
						</span>
			<div class="col-md-12 blankr0">
				<div class="input-group input-group-sm" nbdatetimeall data-min-date="$$NOW">
					<input type="text" class="form-control input-sm" data-ng-required="true" data-ng-model="slot.dateBeginPlan">
					<span class="input-group-btn">
						<button type="button" class="btn btn-primary">
							<i class="fa fa-calendar"></i>
						</button>
					</span>
				</div>
			</div>
		</div>
		<div class="col-md-6 blank0" nb-haserror>
			<span class="control-label col-md-12 small"> <?php echo i18("planning.reccurent.hour.to"); ?>
						</span>
			<div class="col-md-12 blankl0">
				<div class="input-group input-group-sm nbdatepicker-right" nbdatetimeall data-min-date="slot.dateBeginPlan">
					<input type="text" class="form-control input-sm" data-ng-required="true" data-ng-model="slot.dateEndPlan">
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
	<button class="btn btn-success btn-sm col-md-12" type="button" nbspinnerclick="submit()" ng-disabled="slotForm.$invalid">
		<i class="fa fa-add"></i>
		<?php echo i18("general.add"); ?>
	</button>
</form>
