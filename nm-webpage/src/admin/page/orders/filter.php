<div class="col-sm-12" ng-controller="OrderFilter">
	<section class="panel">
		<header class="panel-heading">
			<?php echo i18("order.filter.title")?>
			<span class="tools pull-right"> <a href="javascript:;" class="fa fa-chevron-down"></a>
				<a href="javascript:;" class="fa fa-times "></a>
			</span>
		</header>
		<div class="panel-body">
			<div class="col-sm-4 form-horizontal">
				<div class="form-group">
					<label class="control-label col-md-6"><?php echo i18("order.filter.resto")?></label>
					<div class="col-sm-6">
						<select class="form-control" multiple="" ng-model="filter.restaurants">
							<option ng-repeat="resto in restaurants" value="{{resto.id}}">{{resto.name}}</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">
						<?php echo i18("order.filter.from")?>
					</label>
					<div class="col-md-6">
						<div class="input-group date custom-form_datetime-component">
							<input type="date" class="form-control" ng-model="filter.from" />
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">
						<?php echo i18("order.filter.to")?>
					</label>
					<div class="col-md-6">
						<div class="input-group date custom-form_datetime-component">
							<input type="date" class="form-control" ng-model="filter.to" />
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-4 form-horizontal">
				<div class="form-group">
					<label class="control-label col-md-6">
						<?php echo i18("order.filter.ref")?>
					</label>
					<order-search ng-model="filter.reference" listener="setOrder"></order-search>
					<script type="text/ng-template" id="order_search.html"> 
					<div class="col-sm-6" >
						<input type="text" class="form-control" ng-model="model">
						<div class="col-md-12" ng-repeat="order in orders" ng-click="select(order)">{{order.uuid}}</div>
					</div>
				</script>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">
						<?php echo i18("order.filter.price.from")?>
					</label>
					<div class="col-md-6">
						<div class="iconic-input right">
							<i class="fa fa-euro"></i> <input type="text" class="form-control"
								ng-model="filter.priceFrom">
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">
						<?php echo i18("order.filter.price.to")?>
					</label>
					<div class="col-md-6">
						<div class="iconic-input right">
							<i class="fa fa-euro"></i> <input type="text" class="form-control"
								ng-model="filter.priceTo">
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-4 form-horizontal">
				<div class="form-group">
					<label class="control-label col-md-6">
						<?php echo i18("order.filter.client")?>
					</label>
					<client-search ng-model="filterClient" listener="setClient"></client-search>

				</div>
				<script type="text/ng-template" id="client_search.html"> 
					<div class="col-sm-6" >
						<input type="text" class="form-control" ng-model="model">
						<div class="col-md-12" ng-repeat="client in clients" ng-click="select(client)">{{client.name}}
							{{client.firstname}}</div>
					</div>
				</script>
				<div class="form-group">
					<label class="control-label col-md-6">
						<?php echo i18("order.filter.etat")?>
					</label>
					<div class="col-sm-6">
						<select class="form-control" multiple="" ng-model="filter.states">
							<option value="WaitingPayment"><?php echo i18("order.filter.notpaid")?></option>
							<option value="Paid"><?php echo i18("order.filter.paid")?></option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">
						<?php echo i18("order.filter.type")?>
					</label>
					<div class="col-sm-6">
						<select class="form-control" multiple="" ng-model="filter.types">
							<option value="OutPlace"><?php echo i18("order.filter.type.outplace")?></option>
							<option value="InPlace"><?php echo i18("order.filter.type.inplace")?></option>
							<option value="Delivered"><?php echo i18("order.filter.type.delivered")?></option>
						</select>
					</div>
				</div>
				<button class="btn btn-success" type="button" style="float: right; margin-left: 1em"
					ng-click="submit()">
					<?php echo i18("order.filter.filter")?>
				</button>
				<button class="btn btn-primary" type="button" style="float: right" ng-click="clear()">
					<?php echo i18("order.filter.clear")?>
				</button>
			</div>
		</div>
	</section>
</div>