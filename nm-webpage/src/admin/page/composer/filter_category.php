<div service="categoryService" name="category" params="paramsCategory" nbtable>
	<div class="well well-sm" ng-if="models.length>0">
		<div class="row ng-ingredient">
			<h3 class="col-sm-12">
			<?php echo i18("order.filter.category"); ?>
		</h3>
			<div class="col-md-12">
				<div class="panel-body gallery-image-edit-env" style="overflow-y: scroll;">
					<div class="col-sm-2 col-xs-2 ng-scope " data-tag="3d">
						<article class="image-thumb nb-image-thumb {{getCssCategory()}} btn" ng-click="resetCategory()">
							<div class="image-options nb-image-options">
								<span><?php echo i18("cash.category.all"); ?></span>
							</div>
							<a href="javascript:;" class="image"> <img ng-src="" style="height:75px"/></a>
						</article>
					</div>
					<div class="col-sm-2 col-xs-2 ng-scope " data-tag="3d" ng-repeat="cat in models">
						<article class="image-thumb nb-image-thumb {{getCssCategory(cat)}} btn" ng-click="select(cat)">
							<div class="image-options nb-image-options">
								<span>{{cat.name}}</span>
							</div>
							<a href="javascript:;" class="image"> <img ng-src="{{cat.img}}" style="height:75px"/></a>
						</article>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
