<script id="geoform.html" type="text/ng-template">
<div>
	<input type="hidden" data-ng-model="neverExists" data-ng-required="address.failed && !address.ignored" />
	<div data-ng-form="formGeo">
		<div class="col-md-12">
			<div class="form-group" nb-haserror>
				<input type="text" class="form-control" data-ng-required="required.name" data-ng-model="address.geocode"
					placeholder="<?php  echo i18("geo.search")?>" ng-google-autocomplete details="address.details" data-ng-disabled="address.forceField" />
			</div>
		</div>
		<div class="col-md-12 checkbox">
			<label>
				<input type="checkbox" data-ng-model="address.forceField" />
			<?php  echo i18("geo.notfound")?>	
		</label>
		</div>
		<div class="col-md-12">
			<div class="form-group" nb-haserror>
				<input type="text" class="form-control" data-ng-disabled="!address.forceField" placeholder="<?php  echo i18("geo.address")?>"
					data-ng-model="address.components.street" data-ng-required="true" />
			</div>
		</div>
		<div class="col-md-12">
			<div class="form-group" nb-haserror>
				<input type="text" class="form-control" placeholder="<?php  echo i18("geo.comp")?>" data-ng-model="address.complement" />
			</div>
		</div>
		<div class="col-md-4">
			<div class="form-group" nb-haserror>
				<input type="text" class="form-control" data-ng-disabled="!address.forceField" placeholder="<?php  echo i18("geo.cp")?>"
					data-ng-model="address.components.postal" data-ng-required="true" />
			</div>
		</div>
		<div class="col-md-4">
			<div class="form-group" nb-haserror>
				<input type="text" class="form-control" data-ng-disabled="!address.forceField" placeholder="<?php  echo i18("geo.city")?>"
					data-ng-model="address.components.locality" data-ng-required="true" />
			</div>
		</div>
		<div class="col-md-4">
			<div class="form-group" nb-haserror>
				<input type="text" class="form-control" data-ng-disabled="!address.forceField" placeholder="<?php  echo i18("geo.country")?>"
					data-ng-model="address.components.country" data-ng-required="true" />
			</div>
		</div>
		<div data-ng-if="results.length>0" class="col-md-12">
			<h6 class="text-info"><?php echo i18("geo.search.title")?></h6>
			<ul class="list-group pointer">
				<li class="list-group-item" data-ng-repeat="result in results" data-ng-click="select(result)">{{result.formatted_address}}</li>
				<li class="list-group-item list-group-item-warning" data-ng-click="noone()"><?php echo i18("geo.none")?></li>
			</ul>
		</div>
		<div class="col-md-12 text-warning" data-ng-if="show.failed.search">
			<i class="fa fa-exclamation-triangle"></i>
			<span><?php echo i18("geo.fail.search")?></span>
		</div>
		<div class="col-md-12 text-success" data-ng-if="!address.failed">
			<i class="fa fa-check"></i>
			<span><?php  echo i18("geo.success")?></span>
		</div>
		<div class="col-md-12 text-warning" data-ng-if="canIgnoreAlert()">
			<i class="fa fa-exclamation-triangle"></i>
			<span><?php  echo i18("geo.fail")?></span>
		</div>
		<div class="col-md-12 text-info" data-ng-if="address.ignored">
			<i class="fa fa-info"></i>
			<span><?php  echo i18("geo.fail.confirmed")?></span>
		</div>
		<div class="clearfix col-md-12 voffset2">
			<div class="btn-group col-md-12 blank0" role="group">
				<button type="button" class="btn btn-warning col-md-6" data-ng-click="ignore()" data-ng-disabled="!canIgnore()"><?php echo i18("ignore")?></button>
				<button type="button" class="btn btn-default col-md-6" data-ng-click="search()" data-ng-disabled="!canSearch(formGeo)"><?php echo i18("geo.search.btn")?></button>
			</div>
		</div>
	</div>
</div>
</script>
