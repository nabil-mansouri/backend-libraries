<div class="panel-heading blank0">
	<h5 class="col-md-12">
		<?php echo i18("tarif.title.table"); ?>
	</h5>
</div>
<div class="panel-body blank0">
	<div class="col-md-12 ">
		<?php include 'table_menu.php';?>
	</div>
	<div class="col-md-12 blank0 " data-nbspinner="" boolean="prices.$resolved">
		<table class="table table-striped table-hover small" style="margin-bottom: 10px">
			<thead>
				<tr>
					<th><?php echo i18("tarif.table.produit"); ?></th>
					<th><?php echo i18("tarif.table.plan.begin"); ?></th>
					<th><?php echo i18("tarif.table.plan.end"); ?></th>
					<th><?php echo i18("tarif.table.resto"); ?></th>
					<th><?php echo i18("tarif.table.ordertype"); ?></th>
					<th><?php echo i18("tarif.table.price"); ?></th>
					<th><?php echo i18("tarif.table.nb.supp"); ?></th>
					<th><?php echo i18("tarif.table.actions"); ?></th>
				</tr>
			</thead>
			<tbody ng-repeat="model in prices">
				<tr>
					<td>{{model.product |cmsname}}</td>
					<td data-ng-if="model.filter.hasFrom">{{model.filter.from| dateMs}}</td>
					<td data-ng-if="!model.filter.hasFrom"><?php echo i18("na")?></td>
					<td data-ng-if="model.filter.hasTo">{{model.filter.to| dateMs}}</td>
					<td data-ng-if="!model.filter.hasTo"><?php echo i18("na")?></td>
					<td data-ng-if="!model.filter.allRestaurants">{{model.filter.restaurant | cmsname}}</td>
					<td data-ng-if="model.filter.allRestaurants"><?php echo i18("all")?></td>
					<td data-ng-if="!model.filter.allOrders">{{model.filter.type | ordertype}}</td>
					<td data-ng-if="model.filter.allOrders"><?php echo i18("all")?></td>
					<td class="text-right">{{model.value}} {{model.currency.symbol}}</td>
					<td class="text-right">{{model.countSupplements}}</td>
					<td class="center">
						<a href="javascript:;" nbspinnerclick="edit(model)" class="btn btn-xs btn-blue ">
							<i class="fa fa-edit"></i>
						</a>
					</td>
				</tr>
			</tbody>
			<tbody>
				<tr ifempty="prices">
					<td colspan="8" class="center"><?php echo i18("tarif.table.empty"); ?></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
