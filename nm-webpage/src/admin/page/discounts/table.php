<div id="appDiscountTable" ng-controller="DiscountTable" class="nb-discount-table">
	<div class="col-md-3"></div>
	<div class="col-md-9 nb-discount-table-panel">
		<div class="panel panel-white">
			<div class="panel-heading">
				<h4 class="panel-title">
					<?php echo i18("discounts.title.table"); ?>
				</h4>
			</div>
			<div class="panel-body" service="discountService" name="discount" params="params"
				nbtable>
				<div class="row">
					<div class="col-sm-12">
						<a class="btn btn-success" ng-click="create()"> <?php echo i18("general.create"); ?>&nbsp;<i
							class="fa fa-plus"></i>
						</a> <label class="pull-right"> <input type="text" class="form-control input-sm"
							placeholder="Search" ng-change="filter()" ng-model="searchDiscount">
						</label>
						<div class="btn-group">
							<a class="btn btn-primary " href="javascript:;" ng-click="refresh()"> <i
								class="fa fa-refresh"></i>
							</a>
						</div>
					</div>
				</div>
				<table class="table table-striped table-hover" style="margin-bottom: 10px">
					<thead>
						<tr>
							<th><?php echo i18("discounts.table.name"); ?></th>
							<th><?php echo i18("discounts.table.type"); ?></th>
							<th class="hidden-xs"><?php echo i18("discounts.table.created"); ?></th>
							<th class="hidden-xs"><?php echo i18("discounts.table.sendedto"); ?></th>
							<th><?php echo i18("discounts.table.actions"); ?></th>
						</tr>
					</thead>
					<tbody
						ng-repeat="discount in filtered = (models | filter:searchDiscount | orderBy:'name') | startFrom:(currentPage-1)*entryLimit | limitTo:entryLimit">
						<tr ng-show="shows.model.delete[discount.id]">
							<td class="center bg-orange text-white nbrowtxt" colspan="6"><span
								sprintf="<?php echo i18('product.delete.body') ?>" args="{{discount.name}}"></span>
								<div class="btn-group pull-right">
									<button type="button" class="btn btn-warn"
										ng-click="cancelRemove(discount,$index)">
										<?php echo i18("product.delete.cancel"); ?>
									</button>
									<button type="button" class="btn btn-danger" ng-click="remove(discount,$index)">
										<?php echo i18("product.delete.submit"); ?>
									</button>
								</div></td>
						</tr>
						<tr ng-show="!shows.model.delete[discount.id]">
							<td>{{ discount.name }}</td>
							<td>{{ discount.type }}</td>
							<td>{{ discount.createdAt | dateMs }}</td>
							<td></td>
							<td class="center">
								<div class="visible-md visible-lg hidden-sm hidden-xs">
									<a href="javascript:;" ng-click="edit(discount,$index)"
										class="btn btn-xs btn-blue tooltips" data-placement="top"
										data-original-title="Edit"><i class="fa fa-edit"></i></a> <a href="javascript:;"
										ng-click="askRemove(discount,$index)" class="btn btn-xs btn-red tooltips"
										data-placement="top" data-original-title="Remove"><i
										class="fa fa-times fa fa-white"></i></a>
								</div>
							</td>
						</tr>
						<tr ng-show="isEmpty()">
							<td colspan="6" class="center"><?php echo i18("discounts.table.empty"); ?></td>
						</tr>
					</tbody>
				</table>
				<pagination ng-model="currentPage" class="pagination-small pull-right nb-nomargin"></pagination>
			</div>
		</div>
	</div>
</div>