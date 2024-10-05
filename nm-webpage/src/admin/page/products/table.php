<div class="panel-heading blank0">
	<h5 class="col-md-12" data-ng-if="isNotDraft()">
		<?php echo i18("products.title.table"); ?>
	</h5>
	<h5 class="col-md-12" data-ng-if="isDraft()">
		<?php echo i18("products.drafts.title.table"); ?>
	</h5>
</div>
<div class="panel-body blank0">
	<div class="col-md-12 ">
		<?php include 'table_menu.php';?>
	</div>
	<div class="col-md-12 blank0 ">
		<table class="table table-striped table-hover table-condensed voffset2">
			<thead>
				<tr>
					<th><?php echo i18("products.table.img"); ?></th>
					<th><?php echo i18("products.table.name"); ?></th>
					<th><?php echo i18("products.table.created"); ?></th>
					<th><?php echo i18("products.table.categories"); ?></th>
					<th><?php echo i18("products.table.actions"); ?></th>
				</tr>
			</thead>
			<tbody data-ng-repeat="product in products">
				<tr>
					<td class="center">
						<img data-ng-src="{{product.images.img}}" alt="{{ product.name }}" class="img-responsive" data-imgerror="<?php echo url('img/no-image.png')?>" />
					</td>
					<td>{{ product.cms.name }}</td>
					<td>
						<span data-ng-if="!product.createdAt">{{ product.createdAt }}</span>
					</td>
					<td>
						<span data-ng-repeat="category in product.categories" data-ng-if="!product.categories.length">{{ category.name }}</span>
					</td>
					<td class="center">
						<a href="javascript:;" data-ng-click="unpublish(product)" class="btn btn-xs btn-blue " data-ng-if="unpublish && product.state=='Publish'">
							<i class="fa fa-ban"></i>
						</a>
						<a href="javascript:;" data-ng-click="publish(product)" class="btn btn-xs btn-blue " data-ng-if="publish && product.state=='UnPublish'">
							<i class="fa fa-check"></i>
						</a>
						<a href="javascript:;" data-ng-click="stats(product)" class="btn btn-xs btn-blue " data-ng-if="stats">
							<i class="fa fa-line-chart"></i>
						</a>
						<a href="javascript:;" data-ng-click="price(product)" class="btn btn-xs btn-blue " data-ng-if="price">
							<i class="fa fa-euro"></i>
						</a>
						<a href="javascript:;" data-ng-click="edit(product)" class="btn btn-xs btn-blue ">
							<i class="fa fa-edit"></i>
						</a>
						<a href="javascript:;" delete-confirm="confirmDelete" colspan="6" label="<?php echo i18("products.delete")?>" arguments="product"
							class="btn btn-xs btn-red ">
							<i class="fa fa-times text-danger"></i>
						</a>
					</td>
				</tr>
			</tbody>
			<tbody>
				<tr ifempty="products">
					<td colspan="6" class="center"><?php echo i18("products.table.empty"); ?></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>