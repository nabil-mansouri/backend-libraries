<div class="col-md-12">
	<section class="panel">
		<header class="panel-heading">
		 <?php echo i18("cash.product.detail.title"); ?><span class="text-info">
				{{composer.price.product.name}}</span> <span class="tools pull-right"> <a href="javascript:;"
				class="fa fa-chevron-down"></a>
			</span>
		</header>
		<div class="panel-body">
			<?php require 'composer_levels.php';?>
			<div>
				<div class="col-md-12">
					<div class="col-md-12">
						<div class="pull-right" style="margin-top: 1em">
							<input type="text" class="form-control" name="keyword" placeholder="Search here..."
								ng-model="filterProduct">
						</div>
					</div>

					<div id="gallery" class="media-gal isotope small-grallery col-md-12">
						<div class="panel-body gallery-image-edit-env"
							style="height: 345px; overflow-y: scroll;">
							<div class="col-sm-2 col-xs-2 ng-scope" ng-if="!composer.currentNode.mandatory">
								<article class="image-thumb nb-image-thumb btn {{getCssProduct()}} "
									ng-click="noselect()">
									<div class="image-options nb-image-options">
										<span><?php echo i18("cash.compose.optionnal"); ?></span>
									</div>
									<a href="javascript:;" class="image"> <img ng-src="" style="height: 75px"></a>
								</article>
							</div>
							<div class="col-sm-2 col-xs-2 ng-scope"
								ng-repeat="product in filtered=(composer.currentNode.children | filter:filterProduct)">
								<article class="image-thumb nb-image-thumb btn {{getCssProduct(product)}}"
									ng-click="select(product)">
									<div class="image-options nb-image-options">
										<span>{{product.name}}</span>
										<p ng-if="product.price">{{product.price}}</p>
									</div>
									<a href="javascript:;" class="image"> <img ng-src="{{product.img}}"
										style="height: 75px"></a>
								</article>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</section>
</div>