<h6 nb-has-error>
	<span class="control-label"><?php echo i18("discount.form.gift.main.title"); ?></span> <input
		type="hidden" ng-model="neverExistsGift" ng-required="!discount.gift" />
</h6>
<div class="col-md-6">
	<div class="row">
		<div class="col-sm-12">
			<a class="btn btn-info" nb-subview="subview-form-gift" 
				title="<?php echo i18("gift.create.title")?>"
				subtitle="<?php echo i18("gift.create.subtitle")?>">
			<?php echo i18("gift.create.new"); ?>&nbsp;
			<i class="fa fa-plus"></i>
			</a> <label class="pull-right"> <input type="text" class="form-control input-sm"
				placeholder="Search" ng-change="filter()" ng-model="searchProduct">
			</label>
			<div class="btn-group">
				<a class="btn btn-primary " href="javascript:;" ng-click="refresh()"> <i
					class="fa fa-refresh"></i>
				</a>
			</div>
		</div>
	</div>
	<div service="giftProductService" name="product" params="paramsProduct" nbtable>
		<table class="table table-striped table-hover">
			<thead>
				<tr>
					<th><?php echo i18("products.table.img"); ?></th>
					<th><?php echo i18("products.table.name"); ?></th>
					<th><?php echo i18("products.table.langs"); ?></th>
					<th><?php echo i18("products.table.actions"); ?></th>
				</tr>
			</thead>
			<tbody
				ng-repeat="product in filtered = (models | filter:searchProduct | orderBy:'name') | startFrom:(currentPage-1)*entryLimit | limitTo:entryLimit">
				<tr ng-show="shows.model.delete[product.id]">
					<td class="center bg-orange text-white nbrowtxt" colspan="5"><span
						sprintf="<?php echo i18('product.delete.body') ?>" args="{{product.name}}"></span>
						<div class="btn-group pull-right">
							<button type="button" class="btn btn-warn" ng-click="cancelRemove(product,$index)">
						<?php echo i18("product.delete.cancel"); ?>
					</button>
							<button type="button" class="btn btn-danger" ng-click="remove(product,$index)">
						<?php echo i18("product.delete.submit"); ?>
					</button>
						</div></td>
				</tr>
				<tr ng-show="!shows.model.delete[product.id]"
					ng-class="{'bg-green':contains(product)}">
					<td class="center"><img ng-src="{{ product.img }}" alt="{{ product.name }}"
						class="nb-apercu-img"></td>
					<td>{{ product.name }}</td>
					<td class="hidden-xs">{{ product.createdAt | dateMs }}</td>
					<td><span ng-repeat="lang in product.langs"> <img ng-src="{{getFlagURL(lang)}}"
							class="nb-flag-img" />
					</span></td>
					<td class="center">
						<div class="visible-md visible-lg hidden-sm hidden-xs">
							<a href="javascript:;" ng-click="select(product)"
								class="btn btn-xs btn-blue tooltips"><i class="fa fa-hand-o-up"></i></a>
							<!-- EDIT -->
							<a href="javascript:;" class="nb-select" nb-subview="subview-form-gift"
								arguments="product.id" title="<?php echo i18("form.subproduct.title")?>"
								subtitle="<?php echo i18("form.subproduct.subtitle")?>"> <i class="fa fa-pencil"></i>
							</a>
							<!-- REMOVE -->
							<a href="javascript:;" ng-click="askRemove(product,$index)"
								class="btn btn-xs btn-red tooltips"><i class="fa fa-times fa fa-white"></i></a>
						</div>
					</td>
				</tr>
				<tr ng-show="isEmpty()">
					<td colspan="6" class="center"><?php echo i18("discount.form.product.empty"); ?></td>
				</tr>
			</tbody>
		</table>
		<pagination ng-model="currentPage" class="pagination-small pull-right nb-nomargin"></pagination>
	</div>
</div>
<div class="col-md-6">
	<div class="well col-md-12" ng-if="discount.gift">
		<p><?php i18e("gift.selected")?></p>
		<p>{{discount.gift.name}}</p>
		<div class="col-md-12">
			<img ng-src="{{discount.gift.img}}" />
		</div>
	</div>
</div>