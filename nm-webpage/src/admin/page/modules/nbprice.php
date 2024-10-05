<script type="text/ng-template" id="nbprice.html"> 
<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-5 small"> 
											<?php echo i18("released.price.change"); ?>
									</label>
						<div class="">
							<input type="checkbox" bs-switch="" ng-model="price.detail"
								switch-on-text="<?php echo i18('general.on'); ?>"
								switch-off-text="<?php echo i18('general.off'); ?>" data-size="small">
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group" ng-show="price.detail" nbvalidclass="release_types">
						<label class="control-label col-md-5 small"> <?php echo i18("released.amount.outplace"); ?>
									</label>
						<div class="">
							<select multiple ng-multiple="true" ng-model="price.types"
								ng-required="price.detail" name="release_types" nbarray
								nbcondition="price.detail" nbarraymin="1">
								<option ng-repeat="type in types" value="{{type}}">{{type | nbI18:'release.type'}}
								</option>
							</select>
						</div>
					</div>
				</div>
			</div>
<div class="row">
				<table class="table table-striped table-hover" style="margin-bottom: 10px">
					<thead>
						<tr>
							<th><?php echo i18("tarif.table.produit"); ?></th>
							<th ng-if="!price.detail"><?php echo i18("tarif.table.price")?></th>
							<th ng-if="price.detail" ng-repeat="type in price.types track by $index">{{type |
								nbI18:'release.type'}}</th>
						</tr>
					</thead>
					<tbody ng-repeat="node in graphAsTable" ng-show="isTableNodeVisible(node)">
						<tr ng-if="isRowNodeVisible(node)">
							<td class="center">{{node.name}}</td>
							<td ng-if="!price.detail" bs-has-error><input type="number"
								ng-model="node.priceDetail.prices['Default']" ng-required="true"
								class="form-control" /></td>
							<td ng-if="price.detail" ng-repeat="type in price.types track by $index" bs-has-error><input
								type="number" ng-model="node.priceDetail.prices[type]" ng-required="true"
								class="form-control" /></td>
							<td><a href="javascript:;" class="text-danger fa fa-times"
								ng-click="deleteSupplement(node)" ng-if="$index>0"></a></td>
						</tr>
						<tr ng-if="!isRowNodeVisible(node)">
							<td colspan="{{price.types.length+3}}">{{node.name}}</td>
						</tr>
					</tbody>
				</table>
				<div>
					<a href="javascript:;" class="btn btn-success col-md-12" ng-click="addSupplement()"
						ng-show="!showChoices">
									<?php echo i18("tarif.add.supplement"); ?>
								</a>
				</div>
				<div class="col-md-12" ng-show="showChoices">
					<div class="dd">
						<ol class="dd-list" dx-start-with="price.product">
							<li class="dd-item" ng-repeat="child in $dxPrior.children">
								<div class="dd-handle" ng-click="onSelectSupplement(child)">{{child.name}}</div>
								<ol class="dd-list" ng-if="child.children && child.children.length >0">
									<li dx-connect="child" class="dd-item"></li>
								</ol>
							</li>
						</ol>
					</div>
				</div>
			</div>
</script>