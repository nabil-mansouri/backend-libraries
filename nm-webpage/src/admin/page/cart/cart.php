<section class="panel" ng-controller="CartForm">
	<header class="panel-heading">
		<?php echo i18("cash.create"); ?>
		<span class="tools pull-right"> <a href="javascript:;" class="fa fa-chevron-down"></a>
		</span>
	</header>
	<div class="panel-body">
		<form role="form" name="cartForm">
			<div class="row">
				<ol class="dd-list col-md-12" ng-repeat="(id,group) in groups">
					<li class="dd-item">
						<div class="dd-handle" ng-click="toggleDetail(id)">
							<span class="pull-left text-success">{{getQuantity(id)}} X </span>
							{{getGroupName(group)}} <span class="text-danger pull-right">{{getGroupPrice(group)}}</span>
							<span class="text-danger pull-right" style="font-size: 1.4em;" ng-if="isUnLock()">
								<span ng-click="increment(group,$event)" style="padding: 0px 2px;">+</span><span
								ng-click="decrement(group,$event)" style="padding: 0px 2px;">-</span>
							</span>
						</div>
						<div ng-repeat="context in group" ng-if="isDetailVisible(id)">
							<ol class="dd-list">
								<li class="dd-item">
									<div class="dd-handle">
										{{getName(context.product)}} <span class="text-danger pull-right"
											ng-if="hasPrice(context.product)">{{getPrice(context.product)}}</span> <span
											ng-if="isUnLock()" class="text-danger pull-right"
											ng-click="remove(group,$index,$event)"
											style="font-size: 1.4em; padding: 0px 2px;">X</span>
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
					<li class="dd-item" ng-click="goTo('/compose/type/')">
						<div class="dd-handle text-center">{{cart.type}}</div>
					</li>
					<li class="dd-item" ng-click="goTo('/compose/restaurants/')">
						<div class="dd-handle text-center">{{cart.restaurant.name}}</div>
					</li>
					<li class="dd-item" ng-repeat="discount in cart.discounts">
						<div class="dd-handle">
							{{getNameDiscount(discount)}} <span class="text-success pull-right">{{getPriceDiscount(discount)}}</span>
						</div>
					</li>
					<li class="dd-item">
						<div class="dd-handle text-danger text-center">{{getTotal()}}</div>
					</li>
				</ol>
				<div class="col-sm-12" ng-if="isUnLock()">
					<a href="javascript:;" class="btn btn-default btn-info col-md-12" ng-click="draft()">
							 <?php echo i18("cash.form.draft"); ?>
						</a> <a href="javascript:;" class="btn btn-default btn-warning col-md-12"
						ng-click="cancel()"> 
							<?php echo i18("cash.form.cancel"); ?>
						</a>
					<!-- REQUIRED -->
					<input type="hidden" ng-model="cart.type" ng-required="true" />
					<input type="hidden" ng-model="cart.restaurant" ng-required="true" />
					<input type="hidden" ng-model="cartNeverExists" ng-required="cart.details.length ==0" />
					<!--  -->
					<a href="javascript:;" class="btn btn-default btn-success col-md-12"
						ng-click="submit()" ng-disabled="cartForm.$invalid"> <?php echo i18("cash.form.submit"); ?>
						</a>
				</div>
			</div>
		</form>
	</div>
</section>