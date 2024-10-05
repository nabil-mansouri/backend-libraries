<section>
	<header class="panel-heading">
			<?php echo i18("cash.compose.panel"); ?> 
			<span class="tools pull-right"> <a href="javascript:;" class="fa fa-chevron-down"></a>
		</span>
	</header>
	<div class="row ng-ingredient panel-body" service="restoService" name="restaurant"
		params="paramsRestaurant" nbtable>
		<h3 class="col-sm-12">
			<?php echo i18("order.filter.product"); ?>
		</h3>
		<div class="col-sm-12">
			<label class="pull-right"> <input type="text" class="form-control input-sm"
				placeholder="Search" ng-change="filter()" ng-model="searchResto">
			</label>
			<div class="btn-group">
				<a class="btn btn-primary " href="javascript:;" ng-click="refresh()"> <i
					class="fa fa-refresh"></i>
				</a>
			</div>
		</div>
		<div class="col-md-12">
			<div class=" gallery-image-edit-env" style="height: 345px; overflow-y: scroll;">
				<div class="col-sm-2 col-xs-2 ng-scope" data-tag="3d"
					ng-repeat="resto in models | filter:searchResto">
					<article class="image-thumb nb-image-thumb" ng-click="select(resto)"
						ng-if="resto.state.canBuy" ng-class="{'active':contains(resto)}">
						<div class="image-options nb-image-options">
							<img ng-src="{{resto.img}}" /> <span>{{resto.name}}</span> <span>Ouvert</span>
						</div>
					</article>
					<article class="image-thumb nb-image-thumb" ng-if="!resto.state.canBuy"
						ng-class="{'active':contains(resto)}">
						<div class="image-options nb-image-options">
							<img ng-src="{{resto.img}}" /> <span>{{resto.name}}</span> <span>Fermé</span>
						</div>
					</article>
				</div>
			</div>
		</div>
	</div>
</section>