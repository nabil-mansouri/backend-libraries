<div class="col-md-12">
	<section class="panel">
		<header class="panel-heading">
		<?php echo i18("cash.create"); ?>
		<span class="tools pull-right"> <a href="javascript:;" class="fa fa-chevron-down"></a>
			</span>
		</header>
		<div class="panel-body">
			<h4 class="text-success"><?php echo i18("cart.save.success"); ?></h4>
			<div class="row">
				<ol class="dd-list col-md-12" ng-repeat="(id,group) in groups">
					<li class="dd-item">
						<div class="dd-handle" ng-click="toggleDetail(id)">
							<span class="pull-left text-success">{{getQuantity(id)}} X </span>
							{{getGroupName(group)}} <span class="text-danger pull-right">{{getGroupPrice(group)}}</span>
						</div>
						<div ng-repeat="context in group" ng-if="isDetailVisible(id)">
							<ol class="dd-list">
								<li class="dd-item">
									<div class="dd-handle">
										{{getName(context.product)}} <span class="text-danger pull-right"
											ng-if="hasPrice(context.product)">{{getPrice(context.product)}}</span>
									</div>
									<ol class="dd-list" dx-start-with="context.product">
										<li class="dd-item" ng-repeat="child in $dxPrior.children"
											ng-if="isVisible($dxPrior,child)">
											<div class="dd-handle" ng-if="isProduct(child)">
												{{getName(child)}} <span class="text-danger pull-right"
													ng-if="hasPrice(child)">{{getPrice(child)}}</span>
											</div>
											<ol class="dd-list" ng-if="hasIngredient($dxPrior,child)">
												<li ng-repeat="ing in getIngredients($dxPrior,child)">
													<div class="dd-handle bg-red text-white">{{ing.name}}</div>
												</li>
											</ol>
											<ol class="dd-list" ng-if="hasChild(child)">
												<li dx-connect="child" class="dd-item"></li>
											</ol>
										</li>
									</ol>
								</li>
							</ol>
						</div>
					</li>
				</ol>
				<ol class="dd-list col-md-12">
					<li class="dd-item">
						<div class="dd-handle text-center">{{cart.type}}</div>
					</li>
					<li class="dd-item">
						<div class="dd-handle text-center">{{cart.restaurant.name}}</div>
					</li>
					<li class="dd-item" ng-repeat="discount in cart.discounts">
						<div class="dd-handle">
							{{getNameDiscount(discount)}} <span class="text-success pull-right">{{getPriceDiscount(discount)}}</span>
						</div>
					</li>
				</ol>
			</div>
			<div class="row" ng-controller="SubmitCashPaiement">
				<div class="col-md-12">
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
								<td></td>
							</tr>

							<tr ng-if="isType(transaction, 'Cash')" ng-class="getRowClass(transaction)">
								<td ng-if="isAction(transaction,'Credit')"><?php echo i18("cart.paiment.cash.push"); ?></td>
								<td ng-if="isAction(transaction,'Debit')"><?php echo i18("cart.paiment.cash.return"); ?></td>
								<td></td>
								<td>{{transaction.amount}} &euro;</td>
								<td></td>
							</tr>
							<tr ng-if="isType(transaction, 'RestaurantTicket')"
								ng-class="getRowClass(transaction)">
								<td><?php echo i18("cart.paiment.ticket"); ?></td>
								<td>{{transaction.quantity}} X</td>
								<td>{{transaction.amount}} &euro;</td>
								<td></td>
							</tr>
							<tr ng-if="isType(transaction, 'Paypal')" ng-class="getRowClass(transaction)">
								<td><?php echo i18("cart.paiment.paypal"); ?></td>
								<td></td>
								<td>{{transaction.amount}} &euro;</td>
								<td></td>
							</tr>
							<tr ng-if="isType(transaction, 'Cb')" ng-class="getRowClass(transaction)">
								<td><?php echo i18("cart.paiment.cb"); ?></td>
								<td></td>
								<td>{{transaction.amount}} &euro;</td>
								<td></td>
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
				</div>
			</div>
		</div>
	</section>
</div>