<form name="restoForm" data-nbspinner="" boolean="resto.$resolved">
	<div class="col-md-12 restos" data-ng-show="isVisible()">
		<div class="col-md-6">
			<h5 class="col-md-12"><?php echo i18("resto.menu.form.desc")?></h5>
			<multicms cms="resto.cms" label-name="<?php echo i18("resto.form.name")?>" label-desc="<?php echo i18("resto.form.desc")?>"
				label-keywords="<?php  echo i18("resto.form.keyword")?>" required-name="true" required-desc="true" show-name="true" show-desc="true"
				show-keywords="true"></multicms>
		</div>
		<div class="col-md-6 voffset4_5">
			<div class="panel panel-grey" nb-haserror>
				<div class="panel-heading">
					<label class="col-md-12 control-label"><?php echo i18("resto.localisation.title")?></label>
				</div>
				<small class="help-block panel-help" readmore="">
					<?php echo i18("resto.localisation.info")?><em class="more">...</em>
				</small>
				<div class="panel-body">
					<geoform address="resto.address" required="true"></geoform>
				</div>
			</div>
		</div>
		<div nb-haserror class="col-md-12">
			<h5 class="control-label"><?php  echo i18("form.label.images")?></h5>
			<multimg images="resto.images" required-img="true" mode="Multi" template="multimg_horizontal.html"></multimg>
		</div>
		<div class="clearfix col-md-12 voffset2">
			<div class="btn-group col-md-12 blank0" role="group">
				<button type="button" class="btn btn-danger col-md-4" data-ng-disabled="!resto.id" nbspinnerclick="remove()"><?php echo i18("delete")?></button>
				<button type="button" class="btn btn-default col-md-4" data-ng-click="cancel()"><?php echo i18("cancel")?></button>
				<button type="button" class="btn btn-success col-md-4" data-ng-disabled="restoForm.$invalid" nbspinnerclick="submit()"><?php echo i18("save")?></button>
			</div>
		</div>
	</div>
	<div class="disable_panel" data-ng-show="resto.configError" data-ng-click="cancelConfig()">
		<h5 class="text-center"><?php  echo i18("form.label.config.error")?></h5>
		<p data-ng-if="resto.cms.noDefaultLang" class="text-center"><?php  echo i18("form.label.config.defaut.error")?></p>
		<p data-ng-if="resto.cms.noSelectedLang" class="text-center"><?php  echo i18("form.label.config.selected.error")?></p>
	</div>
</form>