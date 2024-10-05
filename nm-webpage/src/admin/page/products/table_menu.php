<div class="btn-group col-md-6 blank0">
	<div class="btn btn-default col-md-3" data-ng-click="create()">
		<i class="fa fa-plus"></i>
		<h6><?php echo i18("add")?></h6>
	</div>
	<div class="btn btn-default col-md-3" data-ng-click="goTo('/products')" data-ng-class="getCssProduct()">
		<i class="fa fa-th-list"></i>
		<h6><?php echo i18("product.menu.products")?></h6>
	</div>
	<div class="btn btn-default col-md-3" data-ng-click="goTo('drafts')" data-ng-class="getCssDraft()">
		<i class="fa fa-pause"></i>
		<h6><?php echo i18("product.menu.drafts")?></h6>
	</div>
	<a class="btn btn-default col-md-3" href="<?php echo url('page/prices/') ?>"  > 
		<i class="fa fa-euro"></i>
		<h6><?php echo i18("product.menu.prices")?></h6>
	</a>
</div>
<div class="col-md-3 pull-right">
	<input type="text" placeholder="<?php echo i18("product.menu.search")?>" class="form-control" />
</div>