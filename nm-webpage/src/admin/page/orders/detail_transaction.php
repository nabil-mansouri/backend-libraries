<h4>
	<?php echo i18("order.detail.transactions"); ?> 
</h4>
<div class="row" ng-controller="ShowCashPaiement">
	<div class="col-md-12 well">
		<table class="table table-striped" style="font-weight: bolder">
			<tbody ng-repeat="transaction in order.transaction.transactions">
				<tr ng-if="isType(transaction, 'Check')" ng-class="getRowClass(transaction)">
					<td><?php echo i18("cart.paiment.check"); ?></td>
					<td>{{transaction.transactionId}}</td>
					<td></td>
					<td>{{transaction.amount}} &euro;</td>
					<td></td>
				</tr>
				<tr ng-if="isType(transaction, 'Cash')" ng-class="getRowClass(transaction)">
					<td ng-if="isAction(transaction,'Credit')"><?php echo i18("cart.paiment.cash.push"); ?></td>
					<td ng-if="isAction(transaction,'Debit')"><?php echo i18("cart.paiment.cash.return"); ?></td>
					<td>{{transaction.transactionId}}</td>
					<td></td>
					<td>{{transaction.amount}} &euro;</td>
					<td></td>
				</tr>
				<tr ng-if="isType(transaction, 'RestaurantTicket')"
					ng-class="getRowClass(transaction)">
					<td><?php echo i18("cart.paiment.ticket"); ?></td>
					<td>{{transaction.transactionId}}</td>
					<td>{{transaction.quantity}} X</td>
					<td>{{transaction.amount}} &euro;</td>
					<td></td>
				</tr>
				<tr ng-if="isType(transaction, 'Paypal')" ng-class="getRowClass(transaction)">
					<td><?php echo i18("cart.paiment.paypal"); ?></td>
					<td>{{transaction.transactionId}}</td>
					<td></td>
					<td>{{transaction.amount}} &euro;</td>
				</tr>
				<tr ng-if="isType(transaction, 'Cb')" ng-class="getRowClass(transaction)">
					<td><?php echo i18("cart.paiment.cb"); ?></td>
					<td>{{transaction.transactionId}}</td>
					<td></td>
					<td>{{transaction.amount}} &euro;</td>
				</tr>
			</tbody>
			<tbody>
				<tr ng-if="isDue()">
					<td><?php echo i18("cart.paiment.due"); ?></td>
					<td></td>
					<td>{{order.due}} &euro;</td>
					<td></td>
				</tr>
				<tr ng-if="!isDue()">
					<td><?php echo i18("cart.paiment.due.title"); ?></td>
					<td></td>
					<td>{{order.due}} &euro;</td>
					<td></td>
				</tr>
			</tbody>
		</table>
		<div class="row" ng-if="isUnlock()">
			<div class="btn-group btn-group-justified">
				<a href="javascript:;" class="btn btn-default btn-info" ng-click="refund()">
		 			<?php echo i18("order.paiment.refund"); ?>
				</a>
			</div>
		</div>
		<div class="row" ng-if="isUnlock()">
			<div class="btn-group btn-group-justified" ng-click="toggleLock()">
				<?php echo i18("order.paiment.lock"); ?>
			</div>
		</div>
		<div class="row" ng-if="!isUnlock()">
			<div class="btn-group btn-group-justified" ng-click="toggleLock()">
				<?php echo i18("order.paiment.unlock"); ?>
			</div>
		</div>
	</div>
</div>