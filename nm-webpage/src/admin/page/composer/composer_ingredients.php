<div class="col-md-12">
	<section class="panel">
		<header class="panel-heading">
		 <?php echo i18("cash.product.detail.title"); ?><span class="text-info">
				{{composer.price.product.name}}</span> <span class="tools pull-right"> <a
				href="javascript:;" class="fa fa-chevron-down"></a>
			</span>
		</header>
		<div class="panel-body">
			<?php require 'composer_levels.php';?>
			<div class="col-md-12">
				<div class="col-md-12 text-center clearfix">
					<h6>
						 <?php echo i18("cash.ingredient.title"); ?> 
						 <span class="text_info">
						 	{{composer.currentNode.name}}
						</span>
					</h6>
				</div>
				<div id="gallery" class="media-gal isotope small-grallery">
					<div class="col-sm-2 col-xs-2 ng-scope"
						ng-repeat="ing in composer.currentNode.ingredients">
						<article class="image-thumb nb-image-thumb btn"
							ng-class="getCssIngredient(ing)" ng-click="toggle(ing)">
							<div class="image-options nb-image-options">
								<span>{{ing.name}}</span>
							</div>
							<a href="javascript:;" class="image"> <img ng-src="{{ing.img}}"
								style="height: 75px"></a>
						</article>
					</div>
				</div>
				<div class="btn-group btn-group-justified col-md-12">
					<a href="javascript:;" class="btn btn-success" ng-click="submit()">
						 <?php echo i18("cash.ingredient.submit"); ?> </a>
				</div>
			</div>
		</div>
	</section>
</div>