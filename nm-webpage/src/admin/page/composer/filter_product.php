<div class="row ng-ingredient" service="priceService" name="product"
	params="paramsProduct" nbtable>
	<h3 class="col-sm-12">
		<?php echo i18("order.filter.product"); ?>
	</h3>
	<div class="col-sm-12">
		<label class="pull-right"> <input type="text" class="form-control input-sm"
			placeholder="Search" ng-change="filter()" ng-model="searchProduct">
		</label>
		<div class="btn-group">
			<a class="btn btn-primary " href="javascript:;" ng-click="refresh()"> <i
				class="fa fa-refresh"></i>
			</a>
		</div>
	</div>
	<div class="col-md-12">
		<div nb-feedback="" active-class="in alert" class="fade" role="alert"
			feedbackid="FilterComposer">{{flash.message}}</div>
	</div>
	<div class="col-md-12">
		<div class="panel-body gallery-image-edit-env"
			style="height: 345px; overflow-y: scroll;">
			<div class="col-sm-2 col-xs-2 ng-scope" data-tag="3d"
				ng-repeat="price in models | filter:searchProduct">
				<article class="image-thumb nb-image-thumb" ng-click="select(price)">
					<div class="image-options nb-image-options">
						<span>{{price.product.name}}</span>
					</div>
					<a href="javascript:;" class="image"> <img ng-src="{{price.product.img}}"
						style="height: 75px"></a> <span class="text-danger">{{price.compute.value}} &euro;</span>
				</article>
			</div>
		</div>
	</div>
</div>