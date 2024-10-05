<div class="col-md-12">
	<section class="panel">
		<header class="panel-heading">
			<?php echo i18("cart.paiment.title"); ?>
			<span class="tools pull-right"> <a href="javascript:;" class="fa fa-chevron-down"></a>
			</span>
		</header>
		<div class="panel-body" ng-if="!loading">
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6 col-sm-offset-3">
						<table class="table table-striped" style="font-weight: bolder">
							<tbody>
								<tr>
									<td><?php echo i18("cart.paiment.total"); ?></td>
									<td></td>
									<td>{{order.total}} &euro;</td>
									<td></td>
								</tr>
							</tbody>
							<tbody ng-repeat="transaction in order.transaction.transactions">
								<tr ng-if="isType(transaction, 'Check')" ng-class="getRowClass(transaction)">
									<td><?php echo i18("cart.paiment.check"); ?></td>
									<td></td>
									<td>{{transaction.amount}} &euro;</td>
									<td><a href="javascript:;" ng-click="rollback(transaction)"
										class="btn btn-xs btn-red tooltips" data-placement="top"
										data-original-title="Remove"><i class="fa fa-times fa fa-white"></i></a></td>
								</tr>

								<tr ng-if="isType(transaction, 'Cash')" ng-class="getRowClass(transaction)">
									<td ng-if="isAction(transaction,'Credit')"><?php echo i18("cart.paiment.cash.push"); ?></td>
									<td ng-if="isAction(transaction,'Debit')"><?php echo i18("cart.paiment.cash.return"); ?></td>
									<td></td>
									<td>{{transaction.amount}} &euro;</td>
									<td><a href="javascript:;" ng-click="rollback(transaction)"
										class="btn btn-xs btn-red tooltips" data-placement="top"
										data-original-title="Remove"><i class="fa fa-times fa fa-white"></i></a></td>
								</tr>
								<tr ng-if="isType(transaction, 'RestaurantTicket')"
									ng-class="getRowClass(transaction)">
									<td><?php echo i18("cart.paiment.ticket"); ?></td>
									<td>{{transaction.quantity}} X</td>
									<td>{{transaction.amount}} &euro;</td>
									<td><a href="javascript:;" ng-click="rollback(transaction)"
										class="btn btn-xs btn-red tooltips" data-placement="top"
										data-original-title="Remove"><i class="fa fa-times fa fa-white"></i></a></td>
								</tr>
								<tr ng-if="isType(transaction, 'Paypal')" ng-class="getRowClass(transaction)">
									<td><?php echo i18("cart.paiment.paypal"); ?></td>
									<td></td>
									<td>{{transaction.amount}} &euro;</td>
									<td><a href="javascript:;" ng-click="rollback(transaction)"
										class="btn btn-xs btn-red tooltips" data-placement="top"
										data-original-title="Remove"><i class="fa fa-times fa fa-white"></i></a></td>
								</tr>
								<tr ng-if="isType(transaction, 'Cb')" ng-class="getRowClass(transaction)">
									<td><?php echo i18("cart.paiment.cb"); ?></td>
									<td></td>
									<td>{{transaction.amount}} &euro;</td>
									<td><a href="javascript:;" ng-click="rollback(transaction)"
										class="btn btn-xs btn-red tooltips" data-placement="top"
										data-original-title="Remove"><i class="fa fa-times fa fa-white"></i></a></td>
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
						<div class="row" ng-if="isOk()">
							<div class="btn-group btn-group-justified">
								<a href="javascript:;" class="btn btn-default btn-info" ng-click="forceCommit()">
								 		<?php echo i18("cart.paiment.due.ok"); ?>
									</a>
							</div>
						</div>
						<div class="row" ng-if="!isDue()">
							<div class="col-md-12">
								<div class="btn-group btn-group-justified">
									<a href="javascript:;" class="btn btn-default btn-warning "
										ng-click="showReturn=!showReturn"> 
										<?php echo i18("cart.paiment.due.return"); ?>
									</a>
									<!--  -->
									<a href="javascript:;" class="btn btn-default btn-info" ng-click="forceCommit()">
								 		<?php echo i18("cart.paiment.due.ignore"); ?>
									</a>
								</div>
							</div>
							<div class="col-md-12" ng-if="showReturn" ng-form="returnForm"
								ng-controller="CashPaiementReturn">
								<div class="col-md-6">
									<div class="form-group clearfix" nb-has-error>
										<label class="col-sm-12 control-label"><?php echo i18("cart.paiment.cash.push.action"); ?></label>
										<div class="col-sm-12">
											<input type="number" step="any" ng-model="form.amount" ng-required="true"
												nb-min="0" nb-max="getInverseDue()" class="form-control" />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<a href="javascript:;" class="btn btn-info" ng-click="submit(form)"
										ng-disabled="returnForm.$invalid"> 
										<?php echo i18("cart.paiment.due.return"); ?>
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
				<h3><?php echo i18("cart.paiment.mode"); ?></h3>
				<div class="row">
					<div class="col-xs-3 col-md-3" ng-click="push('Cash')">
						<a href="javascript:;" class="thumbnail"> <img style="height: 100px"
							src="http://www.fatines.fr/wp-content/uploads/2009/11/monnaie.png">
						</a>
					</div>
					<div class="col-xs-3 col-md-3" ng-click="push('RestaurantTicket')">
						<a href="javascript:;" class="thumbnail"> <img style="height: 100px"
							src="http://blog.a500m.com/wp-content/uploads/2014/01/ticket_restaurant.gif">
						</a>
					</div>
					<div class="col-xs-3 col-md-3" ng-click="push('Check')">
						<a href="javascript:;" class="thumbnail"> <img style="height: 100px"
							src="http://la-poelee-fidesienne.fr/images/moyenne-photo/logo_paiement_cheque%20copie.jpg">
						</a>
					</div>
					<div class="col-xs-3 col-md-3" ng-click="push('Cb')">
						<a href="javascript:;" class="thumbnail"> <img style="height: 100px"
							src="http://www.homemaison.com/media/logo_carte_bancaire.gif">
						</a>
					</div>
				</div>
				<div class="row" ng-controller="CashPaiementDetail">
					<div class="col-md-12" ng-if="is('Cash')" ng-form="cashForm">
						<div class="col-md-6">
							<div class="form-group clearfix" nb-has-error>
								<label class="col-sm-12 control-label"><?php echo i18("cart.paiment.cash.push.action"); ?></label>
								<div class="col-sm-12">
									<input type="number" step="any" ng-model="transaction.amount" ng-required="true"
										class="form-control" />
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<a href="javascript:;" class="btn btn-info" ng-click="submit()"
								ng-disabled="cashForm.$invalid"> 
								<?php echo i18("cart.paiment.cash.push.submit"); ?>
							</a>
						</div>
					</div>
					<div class="col-md-12" ng-if="is('RestaurantTicket')" ng-form="ticketForm">
						<div class="col-md-4">
							<div class="form-group clearfix" nb-has-error>
								<label class="col-sm-12 control-label"><?php echo i18("cart.paiment.ticket.push.amount"); ?></label>
								<div class="col-sm-12">
									<input type="number" step="any" ng-model="transaction.amount" ng-required="true"
										class="form-control" />
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group clearfix" nb-has-error>
								<label class="col-sm-12 control-label"><?php echo i18("cart.paiment.ticket.push.qty"); ?></label>
								<div class="col-sm-12">
									<input type="number" step="any" ng-model="transaction.quantity"
										ng-required="true" class="form-control" />
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<a href="javascript:;" class="btn btn-info" ng-click="submit()"
								ng-disabled="ticketForm.$invalid"> 
								<?php echo i18("cart.paiment.cash.push.submit"); ?>
							</a>
						</div>
					</div>
					<div class="col-md-12" ng-if="is('Check')" ng-form="checkForm">
						<div class="col-md-6">
							<div class="form-group clearfix" nb-has-error>
								<label class="col-sm-12 control-label"><?php echo i18("cart.paiment.ticket.push.amount"); ?></label>
								<div class="col-sm-12">
									<input type="number" step="any" ng-model="transaction.amount" ng-required="true"
										class="form-control" />
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<a href="javascript:;" class="btn btn-info" ng-click="submit()"
								ng-disabled="checkForm.$invalid"> 
								<?php echo i18("cart.paiment.cash.push.submit"); ?>
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>

</div>
</section>
</div>