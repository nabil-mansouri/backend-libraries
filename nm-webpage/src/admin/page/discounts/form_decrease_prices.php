<div class="btn-group col-md-12">
	<table class="table  table-hover small" style="margin-bottom: 10px">
		<tbody ng-repeat="(key,m) in discount.prices">
			<tr class="{{getCssClass(m)}}">
				<th>{{m.product.name}}</th>
				<td nb-has-error colspan="4">
					<table>
						<thead>
							<tr>
								<th><?php i18e("discount.decrease.pricetype")?></th>
								<th><?php i18e("discount.decrease.oldprice")?></th>
								<th><?php i18e("discount.decrease.operation")?></th>
								<th><?php i18e("discount.decrease.value")?></th>
								<th><?php i18e("discount.decrease.newprice")?></th>
							</tr>
						</thead>
						<tbody>
							<tr ng-repeat="type in m.types" class="center">
								<td>{{type}}</td>
								<td class="text-danger"><span ng-if="m.product.priceDetail.prices[type]">{{m.product.priceDetail.prices[type]|nbDevise}}</span>
								</td>
								<td nb-has-error><select ng-model="m.product.discountDetails.operations[type]" ng-required="true"
									class="form-control" style="padding: 0; height: auto">
										<option value="Manual"><?php  echo i18("discount.decrease.operation.manual")?></option>
										<option value="Free"><?php  echo i18("discount.decrease.operation.free")?></option>
										<option value="Fixe"><?php  echo i18("discount.decrease.operation.fixe")?></option>
										<option value="Percentage"><?php  echo i18("discount.decrease.operation.percent")?></option>
								</select></td>
								<td nb-has-error>
									<div class="input-group input-group-sm col-md-8 col-md-offset-2"
										ng-if="m.product.discountDetails.operations[type]=='Fixe' || m.product.discountDetails.operations[type]=='Manual'">
										<input type="number" class="form-control"
											ng-model="m.product.discountDetails.overrides[type]" ng-required="true"
											style="padding: 0" /> <span class="input-group-addon">&euro;</span>
									</div>
									<div class="input-group input-group-sm col-md-8 col-md-offset-2"
										ng-if="m.product.discountDetails.operations[type]=='Percentage'">
										<input type="number" class="form-control"
											ng-model="m.product.discountDetails.overrides[type]" ng-required="true"
											style="padding: 0" /> <span class="input-group-addon">%</span>
									</div>
								</td>
								<td class="text-primary">{{getResult(m,type)}}</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
		<tbody>
			<tr ng-show="isEmpty()">
				<td colspan="7" class="center"><?php echo i18("tarif.table.empty"); ?></td>
			</tr>
		</tbody>
	</table>
</div>