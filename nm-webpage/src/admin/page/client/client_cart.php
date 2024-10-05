<div class="col-md-12">
	<section class="panel">
		<header class="panel-heading">
		<?php echo i18("cart.client.title"); ?>
		<span class="tools pull-right"> <a href="javascript:;" class="fa fa-chevron-down"></a>
			</span>
		</header>
		<div class="panel-body" ng-form="clientForm">
			<h3><?php echo i18("cart.type.subtitle"); ?></h3>
			<div class="row">
				<div class=" col-md-12 well">
					<div class="col-sm-3">
						<label><?php echo i18("cart.client.search"); ?></label>
					</div>
					<div class="col-sm-9">
						<input type="text" ng-model="filters.search" class="form-control" />
					</div>
				</div>
				<div class=" col-md-12 well" ng-if="clients.$resolved">
					<div class="col-md-12" ng-repeat="model in clients" ng-click="selectClient(model)">
						<div class="col-md-6">{{model.reference}}</div>
						<div class="col-md-2">{{model.name}}</div>
						<div class="col-md-2">{{model.firstname}}</div>
						<div class="col-md-2">{{model.birthDate | dateMs}}</div>
					</div>
				</div>
				<div class=" col-md-12">
					<div class=" col-md-6 well">
						<div class="form-group clearfix" ng-if="cart.client.reference" nb-has-error>
							<label class="col-sm-12"><?php echo i18("cart.client.ref"); ?></label>
							<div class="col-sm-12">{{cart.client.reference}}</div>
						</div>
						<div class="form-group clearfix" nb-has-error>
							<label class="col-sm-12"><?php echo i18("cart.client.name"); ?></label>
							<div class="col-sm-12">
								<input type="text" ng-model="cart.client.name" class="form-control"
									ng-required="true" />
							</div>
						</div>
						<div class="form-group clearfix" nb-has-error>
							<label class="col-sm-12"><?php echo i18("cart.client.firstname"); ?></label>
							<div class="col-sm-12">
								<input type="text" ng-model="cart.client.firstname" class="form-control"
									ng-required="true" />
							</div>
						</div>
						<div class="form-group clearfix" nb-has-error>
							<label class="col-sm-12"><?php echo i18("cart.client.birthdate"); ?></label>
							<div class="col-sm-12">
								<input type="date" nb-fixdate nb-date-max-current ng-model="cart.client.birthDate"
									class="form-control" ng-required="true" />
							</div>
						</div>
						<div class="form-group clearfix" nb-has-error>
							<label class="col-sm-12"><?php echo i18("cart.client.email"); ?></label>
							<div class="col-sm-12">
								<input type="email" ng-model="cart.client.email" class="form-control"
									ng-required="!cart.client.phone" />
							</div>
						</div>
						<div class="form-group clearfix" nb-has-error>
							<label class="col-sm-12"><?php echo i18("cart.client.phone"); ?></label>
							<div class="col-sm-12">
								<input type="text" ng-model="cart.client.phone" class="form-control"
									ng-required="!cart.client.email" />
							</div>
						</div>
					</div>
					<div class="col-md-6 well">
						<div class="form-group clearfix">
							<label class="col-sm-12"><?php echo i18("cart.client.address.geocode"); ?></label>
							<div class="col-sm-12">
								<textarea type="text" ng-model="cart.client.address.geocode" class="form-control"
									ng-required="canIgnore()" ng-google-autocomplete
									details="cart.client.address.details" rows="3">
									</textarea>
								<!-- HELP -->
								<div ng-if="isGeocodeFalse()" class="has-error">
									<span class="help-block"><?php echo i18("cart.client.address.geocode.ok"); ?></span>
								</div>
								<!-- REQUIRED -->
								<input type="hidden" ng-model="neverExists" ng-required="!geoCodeOk" /> <input
									type="hidden" ng-model="cart.client.address.details" ng-required="true" />
							</div>
						</div>
						<div class="form-group clearfix has-success">
							<!-- INFO -->
							<div ng-if="cart.client.address.details && !isGeocodeFalse()">
								<span class="help-block">{{cart.client.address.details.formatted_address}} </span>
							</div>
						</div>
						<div class="form-group clearfix">
							<label class="col-sm-12"><?php echo i18("cart.client.address.complement"); ?></label>
							<div class="col-sm-12">
								<textarea type="text" ng-model="cart.client.address.complement"
									class="form-control" rows="3">
							</textarea>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-12">
					<div class="btn-group btn-group-justified">
						<a href="javascript:;" class="btn btn-warning" ng-click="ignore()"
							ng-if="canIgnore()"> 
							<?php echo i18("cart.client.ignore"); ?>
						</a>
						<!--  -->
						<a href="javascript:;" class="btn btn-info" ng-click="submit()"
							ng-disabled="clientForm.$invalid"> 
							<?php echo i18("cart.client.next"); ?>
						</a>
					</div>
				</div>
			</div>

		</div>
	</section>
</div>
