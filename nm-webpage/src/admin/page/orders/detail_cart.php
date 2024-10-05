<h4>
	<?php echo i18("order.detail.content"); ?> 
</h4>
<div ng-controller="CartRow" ng-init="init(order.cart)">
	<div>
		<ol class="dd-list col-md-12" ng-repeat="(id,group) in groups">
			<li class="dd-item">
				<div class="dd-handle" ng-click="toggleDetail(id)">
					<span class="pull-left text-success">{{getQuantity(id)}} X </span>
					{{getGroupName(group)}} <span class="text-danger pull-right">{{getGroupPrice(group)}}</span>
				</div>
				<div ng-repeat="row in group" ng-if="isDetailVisible(id)">
					<ol class="dd-list">
						<li class="dd-item">
							<div class="dd-handle">
								{{getName(row.product)}} <span class="text-danger pull-right"
									ng-if="hasPrice(row.product)">{{getPrice(row.product)}}</span>
							</div>
							<ol class="dd-list" dx-start-with="row.product">
								<li class="dd-item" ng-repeat="child in $dxPrior.children"
									ng-if="isVisible($dxPrior,child)">
									<div class="dd-handle" ng-if="isProduct(child)">
										{{getName(child)}} <span class="text-danger pull-right" ng-if="hasPrice(child)">{{getPrice(child)}}</span>
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
			<li class="dd-item">
				<div class="dd-handle text-danger text-center">{{getTotal()}}</div>
			</li>
		</ol>
	</div>
</div>