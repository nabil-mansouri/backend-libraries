
<div class="col-sm-6">
	<form ng-controller="OrderStat" name="statForm">

		<section class="panel">
			<header class="panel-heading">
				<?php echo i18("statistics.title")?>
			</header>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-5 form-horizontal">
						<div class="form-group">
							<label class="control-label col-md-4"><?php echo i18("statistics.filter.measure")?></label>
							<div class="col-sm-8">
								<select class="form-control" ng-model="filter.measure" ng-required="true" multiple>
									<option value="TotalAmountOrder">
										<?php echo i18("statistics.measure.day.amount"); ?>
									</option>
									<option value="CountOrder">
										<?php echo i18("statistics.measure.day.count"); ?>
									</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-sm-5 form-horizontal">
						<div class="form-group">
							<label class="control-label col-md-6">
								<?php echo i18("statistics.filter.period")?>
							</label>
							<div class="col-sm-6">
								<select class="form-control" ng-model="filter.period"
									ng-required="true">
									<option value="Minute">
									<?php echo i18("statistics.filter.minute"); ?>
								</option>
									<option value="Hour">
									<?php echo i18("statistics.filter.hour"); ?>
								</option>
									<option value="Day">
									<?php echo i18("statistics.filter.day"); ?>
								</option>
									<option value="Week">
									<?php echo i18("statistics.filter.week"); ?>
								</option>
									<option value="Month">
									<?php echo i18("statistics.filter.month"); ?>
								</option>
									<option value="Year">
									<?php echo i18("statistics.filter.year"); ?>
								</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-sm-2 form-horizontal">
						<button class="btn btn-success" type="button" style="float: right; margin-left: 1em"
							ng-click="compute()" ng-disabled="statForm.$invalid">
						<?php echo i18("statistics.filter.submit")?>
					</button>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12" ng-if="result">
						<jqflot dataset="result" options="chartOpt" height="300px"></jqflot>
					</div>
				</div>
			</div>
		</section>
	</form>
</div>
<div class="col-sm-6">
	<form ng-controller="OrderStatClass" name="statForm">
		<section class="panel">
			<header class="panel-heading">
				<?php echo i18("statistics.class.title")?>
			</header>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6 form-horizontal">
						<div class="form-group">
							<label class="control-label col-md-5"><?php echo i18("statistics.filter.measure")?></label>
							<div class="col-sm-7">
								<select class="form-control" ng-model="filter.measure" ng-required="true">
									<option value="BestCount">
										<?php echo i18("statistics.measure.best.count"); ?>
									</option>
									<option value="BestSum">
										<?php echo i18("statistics.measure.best.amount"); ?>
									</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-sm-6 form-horizontal">
						<div class="form-group">
							<label class="control-label col-md-5">
							<?php echo i18("statistics.filter.limit")?>
						</label>
							<div class="col-md-7">
								<div class="iconic-input right">
									<input type="number" class="form-control" ng-model="filter.limit"
										ng-required="true">
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-6 form-horizontal">
						<div class="form-group">
							<label class="control-label col-md-5">
							<?php echo i18("statistics.filter.from")?>
						</label>
							<div class="col-md-7">
								<div class="iconic-input right">
									<input type="date" class="form-control" ng-model="filter.from" />
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-6 form-horizontal">
						<div class="form-group">
							<label class="control-label col-md-5">
							<?php echo i18("statistics.filter.to")?>
						</label>
							<div class="col-md-7">
								<div class="iconic-input right">
									<input type="date" class="form-control" ng-model="filter.to" />
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-6 form-horizontal">
						<div class="form-group">
							<label class="control-label col-md-5">
								<?php echo i18("statistics.filter.period")?>
							</label>
							<div class="col-sm-7">
								<select class="form-control" ng-model="filter.period"
									ng-required="true">
									<option value="Minute">
									<?php echo i18("statistics.filter.minute"); ?>
								</option>
									<option value="Hour">
									<?php echo i18("statistics.filter.hour"); ?>
								</option>
									<option value="Day">
									<?php echo i18("statistics.filter.day"); ?>
								</option>
									<option value="Week">
									<?php echo i18("statistics.filter.week"); ?>
								</option>
									<option value="Month">
									<?php echo i18("statistics.filter.month"); ?>
								</option>
									<option value="Year">
									<?php echo i18("statistics.filter.year"); ?>
								</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-sm-6 form-horizontal">
						<button class="btn btn-success" type="button" style="float: right; margin-left: 1em"
							ng-click="compute()" ng-disabled="statForm.$invalid">
						<?php echo i18("statistics.filter.submit")?>
					</button>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12" ng-if="result">
						<jqflot dataset="result" options="chartOpt" height="300px"></jqflot>
					</div>
				</div>
			</div>
		</section>
	</form>
</div>