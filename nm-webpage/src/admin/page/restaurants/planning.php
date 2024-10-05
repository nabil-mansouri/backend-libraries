<div class="hei100 planning" data-ng-controller="PlanningController">
	<div class="col-md-9 blank0 hei100">
		<div style="display: none" ng-init="<?php echo i18('planning.calendar.month') ?>"></div>
		<div style="display: none" ng-init="<?php echo i18('planning.calendar.day') ?>"></div>
		<div style="display: none" ng-init="<?php echo i18('planning.calendar.week') ?>"></div>
		<div style="display: none" ng-init="<?php echo i18('planning.calendar.today') ?>"></div>
		<div style="display: none" ng-init="<?php echo i18('planning.calendar.today') ?>"></div>
		<div style="display: none" ng-init="<?php echo i18('planning.calendar.months') ?>"></div>
		<div style="display: none" ng-init="<?php echo i18('planning.calendar.days') ?>"></div>
		<div style="display: none" ng-init="<?php echo i18('planning.calendar.title.format.day') ?>"></div>
		<div style="display: none" ng-init="<?php echo i18('planning.calendar.title.format.week') ?>"></div>
		<div style="display: none" ng-init="<?php echo i18('planning.calendar.title.format.month') ?>"></div>
		<div class="btn-group btn-group-justified">
			<a class="btn btn-default" role="button" ng-click="changeMode('edit')" ng-class="getCssMode('edit')"> <?php echo i18("planning.mode.edit"); ?>
					</a>
			<a class="btn btn-default" role="button" ng-click="changeMode('view')" ng-class="getCssMode('view')"> <?php echo i18("planning.mode.view"); ?>
					</a>
		</div>
		<div data-ng-controller="PlanningEditController" data-ng-if="planning.$resolved" class="hei100" data-ng-show="isMode('edit')">
			<div class="hei100 delete" data-ng-if="show.delete.recurrent.end">
				<div>
					<p>{{'' | nbI18:'planning.delete.rec.body'| nbsprintf:toDeleteArgs}}</p>
					<div class="col-md12">
						<button class="btn text-warning btn-sm col-md-4" type="button" nbspinnerclick="cancel()">
							<i class="fa fa-undo"></i>
							<?php echo i18("general.cancel"); ?>
						</button>
						<button class="btn text-danger btn-sm col-md-4" type="button" nbspinnerclick="deleteAll()">
							<i class="fa fa-times"></i>
							<?php echo i18("planning.delete.submit.horaire"); ?>
						</button>
						<button class="btn text-danger btn-sm col-md-4" type="button" nbspinnerclick="deleteRange()">
							<i class="fa fa-times"></i>
							<?php echo i18("planning.delete.submit.creneau"); ?>
						</button>
					</div>
				</div>
			</div>
			<div class="hei100 delete" data-ng-if="show.delete.recurrent.noend">
				<div>
					<p>{{""| nbI18:'planning.delete.rec.noend.body'| nbsprintf:toDeleteArgs}}</p>
					<div class="col-md12">
						<button class="btn text-warning btn-sm col-md-4" type="button" nbspinnerclick="cancel()">
							<i class="fa fa-undo"></i>
							<?php echo i18("general.cancel"); ?>
						</button>
						<button class="btn text-danger btn-sm col-md-4" type="button" nbspinnerclick="deleteAll()">
							<i class="fa fa-times"></i>
							<?php echo i18("planning.delete.submit.horaire"); ?>
						</button>
						<button class="btn text-danger btn-sm col-md-4" type="button" nbspinnerclick="deleteRange()">
							<i class="fa fa-times"></i>
							<?php echo i18("planning.delete.submit.creneau"); ?>
						</button>
					</div>
				</div>
			</div>
			<div class="hei100 delete" data-ng-if="show.delete.exceptionnal">
				<div>
					<p>{{'' | nbI18:'planning.delete.exc.body'| nbsprintf:toDeleteArgs}}</p>
					<div class="col-md12">
						<button class="btn text-warning btn-sm col-md-4 col-md-offset-2" type="button" nbspinnerclick="cancel()">
							<i class="fa fa-undo"></i>
							<?php echo i18("general.cancel"); ?>
						</button>
						<button class="btn text-danger btn-sm col-md-4" type="button" nbspinnerclick="deleteAll()">
							<i class="fa fa-times"></i>
							<?php echo i18("planning.delete.submit.horaire"); ?>
						</button>
					</div>
				</div>
			</div>
			<div ui-calendar="uiConfig.calendar" class="calendar" ng-model="eventSources" calendar="calendarEdit"></div>
		</div>
		<div data-ng-controller="PlanningViewController" data-ng-if="planning.$resolved" class="hei100" data-ng-show="isMode('view')">
			<div ui-calendar="uiConfig.calendar" class="calendar" ng-model="eventSourcesView" calendar="calendarView"></div>
		</div>
	</div>
	<accordion close-others="oneAtATime" class="col-md-3 blank0 hei100">
		<?php include_once 'planning_form.php';?>
	</accordion>
</div>