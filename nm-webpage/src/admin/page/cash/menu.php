<section class="panel">
	<header class="panel-heading">
		<?php echo i18("cash.menu.title"); ?> <span class="tools pull-right"> <a
			href="javascript:;" class="fa fa-chevron-down"></a>
		</span>
	</header>
	<div class="panel-body">
		<div class="media-gal isotope small-grallery btn-group btn-group-justified">
			<div class="images audio item  isotope-item btn" ng-click="goTo('/products')"
				ng-class="getCss('/products')">
				<a href="javascript:;" data-toggle="modal">
					<div>
						<img
							src="https://cdn1.iconfinder.com/data/icons/adiante-apps-app-templates-incos-in-grey/128/app_type_shop_512px_GREY.png"
							alt="">
					</div>
				</a>
				<p><?php echo i18("cash.menu.compose"); ?></p>
			</div>
			<div class="images audio item  isotope-item btn" ng-click="goTo('/cart/draft')"
				ng-class="getCss('/cart/draft')">
				<a href="javascript:;" data-toggle="modal">
					<div>
						<img
							src="https://cdn1.iconfinder.com/data/icons/Primo_Icons/PNG/128x128/button_grey_pause.png"
							alt="">
					</div>
				</a>
				<p><?php echo i18("cash.menu.draft"); ?>
				</p>
			</div>
			<div class="images audio item  isotope-item btn" ng-click="goTo('/cash/history')"
				ng-class="getCss('/cash/history')">
				<a href="javascript:;" data-toggle="modal">
					<div>
						<img
							src="https://cdn1.iconfinder.com/data/icons/devine-icons-part-2/128/Currency-_Dollar.png"
							alt="">
					</div>
				</a>
				<p><?php echo i18("cash.menu.history"); ?>
				</p>
			</div>
			<div class="images audio item  isotope-item btn" ng-click="goTo('/cash/stats')"
				ng-class="getCss('/cash/stats')">
				<a href="javascript:;" data-toggle="modal">
					<div>
						<img src="https://cdn1.iconfinder.com/data/icons/TWG_Retina_Icons/64/chart_pie.png"
							alt="">
					</div>
				</a>
				<p><?php echo i18("cash.menu.stats"); ?></p>
			</div>
		</div>
	</div>
</section>