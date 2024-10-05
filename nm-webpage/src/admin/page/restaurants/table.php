<div class="panel-heading blank0">
	<h5 class="col-md-12">
		<?php echo i18("resto.title.table"); ?>
	</h5>
</div>
<div class="panel-body blank0">
	<div class="col-md-12 ">
		<div class="btn-group col-md-6 blank0">
			<div class="btn btn-default col-md-4" data-ng-click="create()">
				<i class="fa fa-plus"></i>
				<h6><?php echo i18("add")?></h6>
			</div>
		</div>
		<div class="col-md-3 pull-right">
			<input type="text" placeholder="Rechercher..." class="form-control" />
		</div>
	</div>
	<div class="col-md-12">
		<table class="table table-striped table-hover table-condensed">
			<thead>
				<tr>
					<th><?php echo i18("resto.table.img"); ?></th>
					<th><?php echo i18("resto.table.name"); ?></th>
					<th><?php echo i18("resto.table.created"); ?></th>
					<th><?php echo i18("resto.table.categories"); ?></th>
					<th><?php echo i18("resto.table.actions"); ?></th>
				</tr>
			</thead>
			<tbody data-ng-repeat="resto in restos">
				<tr>
					<td class="center">
						<img data-ng-src="{{resto.images.img}}" alt="{{ resto.name }}" class="img-responsive" data-imgerror="<?php echo url('img/no-image.png')?>" />
					</td>
					<td>{{ resto.cms.name }}</td>
					<td>
						<span data-ng-if="!resto.createdAt">{{ resto.createdAt }}</span>
					</td>
					<td>
						<span data-ng-repeat="category in resto.categories" data-ng-if="!resto.categories.length">{{ category.name }}</span>
					</td>
					<td class="center">
						<a href="javascript:;" data-ng-click="planning(resto)" class="btn btn-xs btn-blue ">
							<i class="fa fa-calendar"></i>
						</a>
						<a href="javascript:;" data-ng-click="edit(resto)" class="btn btn-xs btn-blue ">
							<i class="fa fa-edit"></i>
						</a>
						<a href="javascript:;" delete-confirm="confirmDelete" colspan="6" label="<?php echo i18("restos.delete")?>" arguments="resto"
							class="btn btn-xs btn-red ">
							<i class="fa fa-times text-danger"></i>
						</a>
					</td>
				</tr>
			</tbody>
			<tbody>
				<tr ifempty="restos">
					<td colspan="6" class="center"><?php echo i18("resto.table.empty"); ?></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>