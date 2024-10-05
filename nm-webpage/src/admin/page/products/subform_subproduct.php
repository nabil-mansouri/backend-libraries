<div class="panel panel-grey" data-ng-form="partWrapperForm">
	<div class="panel-heading" nbspinner promise="refreshPromise" nb-haserror>
		<label class="checkbox col-md-12 control-label">
			<input type="checkbox" data-ng-model="product.hasProducts" /><?php  echo i18("form.parts.title")?>
			<input type="hidden" data-ng-model="neverExists" data-ng-required="!parts.length" data-ng-if="product.hasProducts" />
		</label>
	</div>
	<small class="help-block panel-help" readmore><?php echo i18("form.parts.info")?>
		 <em class="more">...</em>
	</small>
	<div class="panel-body blank0 box-grey box" data-ng-class="getCssClass()">
		<div class="col-md-12 blank0">
			<div class="col-md-3 blank0 hei100">
				<ul class="list-group col-md-12 blank0">
					<li class="list-group-item " data-ng-repeat="part in parts" data-ng-class="getCssTab(part)" nb-haserror data-ng-click="select($index)">
						<span class="control-label" data-ng-if="!part.name"><?php  echo i18("form.parts.tab")?>{{$index + 1}}</span>
						<span class="control-label" data-ng-if="part.name">{{part.name}}</span>
						<input type="hidden" data-ng-model="neverExists" data-ng-required="subforms[$index].$invalid" data-ng-if="product.hasProducts" />
					</li>
					<li class="list-group-item" data-ng-click="add()"><?php  echo i18("add")?></li>
				</ul>
			</div>
			<div class="col-md-9 right blank0" data-ng-repeat="part in parts" data-ng-show="part.selected" data-ng-form="partForm">
				<div class="col-md-6" data-ng-init="pushForm(partForm,$index)">
					<div class="form-group voffset2" nb-haserror>
						<label class="control-label"><?php  echo i18("form.parts.name")?></label>
						<input type="text" class="form-control" data-ng-model="part.name" data-ng-required="true" data-ng-if="product.hasProducts" />
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group voffset2" nb-haserror>
						<label class="control-label"><?php  echo i18("form.parts.required")?></label>
						<div>
							<label class="radio-inline">
								<input type="radio" name="optradio{{$index}}" value="1" data-ng-model="part.facultatif" data-ng-required="true"
									data-ng-if="product.hasProducts">
								<?php  echo i18("yes")?>
							</label>
							<!--  -->
							<label class="radio-inline">
								<input type="radio" name="optradio{{$index}}" value="0" data-ng-model="part.facultatif" data-ng-required="true"
									data-ng-if="product.hasProducts">
								<?php  echo i18("no")?>
							</label>
						</div>
					</div>
				</div>
				<div class="clearfix col-md-12 brdt"></div>
				<div class="clearfix col-md-12 brdt text-danger text-center" data-ng-if="partForm.noOne.$error">
					<small><?php echo i18("form.parts.noone")?></small>
				</div>
				<input type="hidden" data-ng-model="neverExists" name="noOne" data-ng-required="!hasAtLeatOneProduct(part)" data-ng-if="product.hasProducts" />
				<ul class="list-group col-md-6 blank0 brdr">
					<li class="list-group-item sm pointer" data-ng-click="create()">
						<small><?php  echo i18("form.parts.create")?></small>
					</li>
					<li class="list-group-item sm checkbox" data-ng-repeat="prod in product.products">
						<input type="checkbox" data-ng-model="part.products[prod.id]" />
						{{prod.cms.name}}
						<i class="fa fa-edit pull-right" data-ng-click="edit(prod,$event)"></i>
					</li>
				</ul>
				<ul class="list-group col-md-6 blank0">
					<li class="list-group-item sm checkbox">
						<small><?php  echo i18("form.ingredient.recap")?></small>
					</li>
					<li class="list-group-item sm checkbox" data-ng-repeat="(id,bool) in part.products" data-ng-if="bool">{{product.products[id].cms.name}}</li>
				</ul>
				<div class="clearfix col-md-12 btn btn-danger bottom" data-ng-click="remove($index)" data-ng-if="!$first"><?php  echo i18("form.parts.delete")?></div>
			</div>
		</div>
	</div>
</div>