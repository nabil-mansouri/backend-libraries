<div class="col-md-12 " nb-haserror>
	<label class="control-label col-md-3 small"> <?php echo i18("released.form.product"); ?>
						</label>
	<div class="col-md-4">
		<div class="input-group input-group-sm" nbspinner promise="refreshPromise">
			<select class="form-control" data-ng-model="price.product.id" data-ng-required="true" data-ng-options="p.id as p.cms.name for p in price.products">
				<option value="">--Produits--</option>
			</select>
		</div>
	</div>
</div>
<div data-ng-if="price.product.id" class="col-md-12 voffset2">
	<div class="">
		<table class="table table-striped table-hover">
			<thead>
				<tr>
					<th><?php echo i18("tarif.table.ordertype"); ?></th>
					<th><?php echo i18("tarif.table.resto"); ?></th>
					<th><?php echo i18("tarif.table.price"); ?></th>
					<th></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="">
		<table class="table table-striped table-hover " data-ng-controller="PriceFormProductRow" data-ng-init="init(price.root)">
			<tbody>
				<tr>
					<th colspan="4">{{price.root.node.product | cmsname}}</th>
				</tr>
				<tr data-ng-repeat="v in price.root.values">
					<td nb-haserror class="col-md-4">
						<orderselect group="price.root" row="v" filter="price.filter"></orderselect>
					</td>
					<td nb-haserror class="col-md-4">
						<restoselect group="price.root" row="v" filter="price.filter"></restoselect>
					</td>
					<td nb-haserror class="col-md-4">
						<pricevalue group="price.root" row="v" currency="price.currency"></pricevalue>
					</td>
					<td>
						<i class="fa fa-times text-danger" data-ng-if="$index>0" data-ng-click="remove(price.root,$index)"></i>
					</td>
				</tr>
			</tbody>
			<tfoot data-ng-show="canAdd(price.root)" data-ng-click="add(price.root)" class="pointer">
				<tr>
					<th colspan="4" class="text-center"><?php  echo i18("add")?></th>
				</tr>
			</tfoot>
		</table>
	</div>
	<div class="" data-ng-repeat="n in price.nodes" data-ng-show="isProductPartOrPart(n)">
		<table class="table table-striped table-hover   " data-ng-controller="PriceFormProductRow" data-ng-init="init(n)">
			<tbody>
				<tr data-ng-if="isProductPart(n)">
					<th colspan="4">
						<div class="col-md-1">
							<input type="checkbox" data-ng-model="n.enable" />
						</div>
						<div class="col-md-11">{{n.node.product | cmsname}}</div>
					</th>
				</tr>
				<tr data-ng-if="isPart(n)">
					<th colspan="4">
						<div class="col-md-1">
							<input type="checkbox" data-ng-model="n.enable" />
						</div>
						<div class="col-md-11">{{n.node.part | partname}}</div>
					</th>
				</tr>
				<tr data-ng-repeat="v in n.values" data-ng-if="n.enable">
					<td nb-haserror class="col-md-4">
						<orderselect group="n" row="v" filter="price.filter"></orderselect>
					</td>
					<td nb-haserror class="col-md-4">
						<restoselect group="n" row="v" filter="price.filter"></restoselect>
					</td>
					<td nb-haserror class="col-md-4">
						<pricevalue group="n" row="v" currency="price.currency"></pricevalue>
					</td>
					<td>
						<i class="fa fa-times text-danger" data-ng-if="$index>0" data-ng-click="remove(n,$index)"></i>
					</td>
				</tr>
			</tbody>
			<tfoot data-ng-show="canAdd(n)" data-ng-click="add(n)" class="pointer">
				<tr>
					<th colspan="4" class="text-center"><?php  echo i18("add")?></th>
				</tr>
			</tfoot>
		</table>
	</div>
</div>
<script type="text/ng-template" id="pricevalue.html">
<div data-nb-haserror>
<div class="input-group" >
	<input type="number" class="form-control input-sm" data-ng-model="row.value" data-ng-disabled="!group.enable" data-ng-required="group.enable" />
	<div class="input-group-addon">{{currency.code}}</div>
</div></div>
</script>
<script type="text/ng-template" id="orderselect.html">
<div data-nb-haserror>
	<div class="input-group input-group-sm">
		<span class="input-group-addon">
			<input type="checkbox" aria-label="..." data-ng-model="row.allOrders">
		</span>
		<input class="form-control input-sm" data-ng-if="row.allOrders" readonly="readonly" value="<?php echo i18("all")?>" />
		<select class="form-control input-sm" data-ng-model="row.type" data-ng-disabled="!group.enable"
			data-ng-if="!row.allOrders" data-ng-required="true"
			data-ng-options="r.orderType as r|ordertype for r in filter.types | orderfilter:filter.allOrders"> 
		</select>
	</div>
</div>
</script>
<script type="text/ng-template" id="restoselect.html">
<div data-nb-haserror>
	<div class="input-group input-group-sm">
		<span class="input-group-addon">
			<input type="checkbox" aria-label="..." data-ng-model="row.allRestaurants">
		</span>
		<input class="form-control input-sm" data-ng-if="row.allRestaurants" readonly="readonly" value="<?php echo i18("all")?>" />
		<select class="form-control input-sm" data-ng-model="row.restaurant.id" data-ng-disabled="!group.enable"
			data-ng-if="!row.allRestaurants" data-ng-required="true"
			data-ng-options="r.id as r|cmsname for r in filter.restaurants | restofilter:filter.allRestaurants"> 
		</select>
	</div>
</div>
</script>