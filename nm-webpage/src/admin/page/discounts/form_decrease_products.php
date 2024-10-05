<h6 nb-has-error>
	<span class="control-label">
		<?php echo i18("discount.form.decrease.title"); ?>
		<input type="hidden" ng-model="neverExistsTitle"
		ng-required="discount.products.length==0" />
	</span>
</h6>
<div class="col-md-12">
	<div service="productService" name="product" params="paramsProductDecrease" nbtable>
		<div class="row">
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
		</div>
		<table class="table  table-hover">
			<thead>
				<tr>
					<th><?php echo i18("products.table.img"); ?></th>
					<th><?php echo i18("products.table.name"); ?></th>
					<th><?php echo i18("products.table.langs"); ?></th>
					<th><?php echo i18("products.table.actions"); ?></th>
				</tr>
			</thead>
			<tbody>
				<tr ng-class="{'bg-green':contains(product)}"
					ng-repeat="product in filtered = (models | filter:searchProduct | orderBy:'name') | startFrom:(currentPage-1)*entryLimit | limitTo:entryLimit">
					<td class="center"><img ng-src="{{ product.img }}" alt="{{ product.name }}"
						class="nb-apercu-img"></td>
					<td>{{ product.name }}</td>
					<td class="hidden-xs">{{ product.createdAt | dateMs }}</td>
					<td><span ng-repeat="lang in product.langs"> <img ng-src="{{getFlagURL(lang)}}"
							class="nb-flag-img" />
					</span></td>
					<td class="center">
						<div class="visible-md visible-lg hidden-sm hidden-xs">
							<a href="javascript:;" ng-click="selectToggle(product)"
								class="btn btn-xs btn-blue tooltips"><i class="fa fa-hand-o-up"></i></a>
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

