<div class="col-md-12">
	<section class="panel">
		<header class="panel-heading">
		<?php echo i18("cart.check.title"); ?>
		<span class="tools pull-right"> <a href="javascript:;" class="fa fa-chevron-down"></a>
			</span>
		</header>
		<div class="panel-body">
			<div class="row">
				<div class="col-md-12" ng-repeat="detail in checked.details">
					<h6 class="col-md-12">{{detail.product.name}}</h6>
					<div class="col-md-10 col-md-offset-2" ng-if="detail.context.unavailable">
					<?php echo i18("cart.check.unavailable"); ?>
				</div>
					<div class="col-md-12" ng-if="hasPartAbsent(detail.context)">
						<h6 class="col-md-12"><?php echo i18("cart.check.partabsent"); ?></h6>
						<ul class="col-md-12">
							<li ng-repeat="part in getPartAbsent(detail.context)">{{part.name}}</li>
						</ul>
					</div>
					<div class="col-md-12" ng-if="hasProductBad(detail.context)">
						<h6 class="col-md-12"><?php echo i18("cart.check.productbad"); ?></h6>
						<ul class="col-md-12">
							<li ng-repeat="bad in getProductBad(detail.context)">{{bad.part.name}} /
								{{bad.product.name}}</li>
						</ul>
					</div>
					<div class="col-md-12" ng-if="hasPartMandatory(detail.context)">
						<h6 class="col-md-12"><?php echo i18("cart.check.partmandatory"); ?></h6>
						<ul class="col-md-12">
							<li ng-repeat="part in getPartMandatory(detail.context)">{{part.name}}</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>