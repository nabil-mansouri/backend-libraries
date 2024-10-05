<div ng-controller="OrderTable" class="nb-product-table">
	<div class="col-md-12 nb-product-table-panel">
		<div class="panel panel-white">
			<div class="panel-heading">
				<h4 class="panel-title">
					<?php echo i18("order.title.table"); ?>
				</h4>
			</div>
			<div class="panel-body" service="orderService" name="order" params="params" nbtable>
				<div class="row">
					<div class="col-sm-12">
						<a class="btn btn-success" ng-click="create()"> <?php echo i18("general.create"); ?>&nbsp;<i
							class="fa fa-plus"></i>
						</a> <label class="pull-right"> <input type="text" class="form-control input-sm"
							placeholder="Search" ng-change="filter()" ng-model="searchOrder">
						</label>
						<div class="btn-group">
							<a class="btn btn-primary " href="javascript:;" ng-click="refresh()"> <i
								class="fa fa-refresh"></i>
							</a>
						</div>
					</div>
					<div class="col-md-12">
						<div nb-feedback="" active-class="in alert" class="fade" role="alert"
							feedbackid="OnOrderSaved;OnOrderDelete">{{flash.message}}</div>
					</div>
				</div>
				<table class="table table-striped table-hover" style="margin-bottom: 10px">
					<thead>
						<tr>
							<th><?php echo i18("orders.table.num"); ?></th>
							<th><?php echo i18("orders.table.date"); ?></th>
							<th><?php echo i18("orders.table.client"); ?></th>
							<th><?php echo i18("orders.table.total"); ?></th>
							<th><?php echo i18("orders.table.seller"); ?></th>
							<th><?php echo i18("orders.table.status"); ?></th>
							<th><?php echo i18("orders.table.type"); ?></th>
							<th><?php echo i18("orders.table.resto"); ?></th>
							<th><?php echo i18("orders.table.actions"); ?></th>
						</tr>
					</thead>
					<tbody
						ng-repeat="order in filtered = (models | filter:searchOrder | orderBy:'date') | startFrom:(currentPage-1)*entryLimit | limitTo:entryLimit">
						<tr ng-show="shows.model.delete[order.id]">
							<td class="center bg-orange text-white nbrowtxt" colspan="6"><span
								sprintf="<?php echo i18('order.delete.body') ?>" args="{{order.num}}"></span>
								<div class="btn-group pull-right">
									<button type="button" class="btn btn-warn" ng-click="cancelRemove(order,$index)">
										<?php echo i18("order.delete.cancel"); ?>
									</button>
									<button type="button" class="btn btn-danger" ng-click="remove(order,$index)">
										<?php echo i18("order.delete.submit"); ?>
									</button>
								</div></td>
						</tr>
						<tr ng-show="!shows.model.delete[order.id]">
							<td>{{order.uuid}}</td>
							<td>{{order.created | dateMs}}</td>
							<td>{{order.client.name}} {{order.client.firstname}}</td>
							<td>{{order.total}}</td>
							<td>{{order.seller.name}}</td>
							<td>{{order.lastState.state}}</td>
							<td>{{order.type}}</td>
							<td>{{order.restaurant.name}}</td>
							<td class="center">
								<div class="visible-md visible-lg hidden-sm hidden-xs">
									<a href="javascript:;" ng-click="selectOrder(order,$index)"
										class="btn btn-xs btn-blue tooltips"><i class=" glyphicon glyphicon-hand-up"></i></a>
									<!--  -->
									<!--  -->
									<a href="javascript:;" ng-click="askRemove(order,$index)"
										class="btn btn-xs btn-red tooltips"><i class="fa fa-times fa fa-white"></i></a>
								</div>
							</td>
						</tr>
						<tr ng-show="isEmpty()">
							<td colspan="6" class="center"><?php echo i18("orders.table.empty"); ?></td>
						</tr>
					</tbody>
				</table>
				<pagination ng-model="currentPage" class="pagination-small pull-right nb-nomargin"></pagination>
			</div>
		</div>
	</div>
</div>