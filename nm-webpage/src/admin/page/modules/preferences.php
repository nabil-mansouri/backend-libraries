<script id="preferences.html" type="text/ng-template">
<div class="panel-heading blank0">
	<h5 class="col-md-12"><?php echo i18("pref.title"); ?></h5>
</div>
<div class="panel-body blank0">
	<div class="col-md-12 ">
		<form name="localesForm" data-ng-controller="PreferenceLocales">
			<div class="form-group" >
				<label class="col-md-10 blank0"><?php echo i18("pref.lang.selected");?></label>
				<label class="col-md-2 text-right">
					<i class="fa fa-edit" data-ng-click="edit()" data-ng-if="!canEdit()"></i>
					<i class="fa fa-check" data-ng-click="submit()" data-ng-if="canEdit() && !localesForm.$invalid"></i>
				</label>
				<input type="hidden" data-ng-model="neverExists" data-ng-required="isEmptyValue()"/>
				<div class="clearfix"></div>
				<ul class="list-group col-md-12">
					<li class="list-group-item checkbox" data-ng-repeat="locale in locales | orderBy:['name']" data-ng-if="isVisible(locale)"><input type="checkbox"
							data-ng-model="locale.selected" data-ng-if="canEdit()" />{{locale | locale}}</li>
					<li class="list-group-item checkbox" data-ng-if="canShowEmpty()"><? echo i18("pref.lang.empty");?></li>

				</ul>
			</div>
		</form>
		<form name="localeForm" data-ng-controller="PreferenceLocalesDefault">
			<div class="form-group" >
				<label class="col-md-10 blank0"><?php echo i18("pref.lang.default");?></label>
				<label class="col-md-2 text-right">
					<i class="fa fa-edit" data-ng-click="edit()" data-ng-if="!canEdit()"></i>
					<i class="fa fa-check" data-ng-click="submit()" data-ng-if="canEdit() && !localeForm.$invalid"></i>
				</label>
				<input type="hidden" data-ng-model="neverExists" data-ng-required="isEmptyValue()"/>
				<div class="clearfix"></div>
				<ul class="list-group col-md-12"> 
					<li class="list-group-item radio" data-ng-repeat="locale in localeDefaults | orderBy:['name']" data-ng-if="isVisible(locale)"><input
							type="radio" value="{{locale.code}}" data-ng-model="locale.defaut" name="prefLocale" data-ng-if="canEdit()" />{{locale | locale}}</li>
					<li class="list-group-item checkbox" data-ng-if="canShowEmpty()"><? echo i18("pref.lang.empty");?></li>
				</ul>
			</div>
		</form>
		<form name="currencyForm" data-ng-controller="PreferenceCurrencies">
			<div class="form-group" >
				<label class="col-md-10 blank0"><?php echo i18("pref.cur.default");?></label>
				<label class="col-md-2 text-right">
					<i class="fa fa-edit" data-ng-click="edit()" data-ng-if="!canEdit()"></i>
					<i class="fa fa-check" data-ng-click="submit()" data-ng-if="canEdit() && !currencyForm.$invalid"></i>
				</label>
				<input type="hidden" data-ng-model="neverExists" data-ng-required="isEmptyValue()"/>
				<div class="clearfix"></div>
				<ul class="list-group col-md-12">
					<li class="list-group-item radio" data-ng-repeat="cur in currencies | orderBy:['name']" data-ng-if="isVisible(cur)"><input type="radio"
							name="curPref" value="{{cur.code}}" data-ng-model="cur.defaut" data-ng-if="canEdit()" />{{cur| currency}}</li>
					<li class="list-group-item checkbox" data-ng-if="canShowEmpty()"><? echo i18("pref.cur.empty");?></li>
				</ul>
			</div>
		</form>
		<form name="orderTypeForm" data-ng-controller="PreferenceOrderType">
			<div class="form-group" >
				<label class="col-md-10 blank0"><?php echo i18("pref.ordertype.selected");?></label>
				<label class="col-md-2 text-right">
					<i class="fa fa-edit" data-ng-click="edit()" data-ng-if="!canEdit()"></i>
					<i class="fa fa-check" data-ng-click="submit()" data-ng-if="canEdit() && !orderTypeForm.$invalid"></i>
				</label>
				<input type="hidden" data-ng-model="neverExists" data-ng-required="isEmptyValue()"/>
				<div class="clearfix"></div>
				<ul class="list-group col-md-12">
					<li class="list-group-item radio" data-ng-repeat="cur in orders" data-ng-if="isVisible(cur)"><input type="checkbox"
							  data-ng-model="cur.selected" data-ng-if="canEdit()" />{{cur| ordertype}}</li>
					<li class="list-group-item checkbox" data-ng-if="canShowEmpty()"><? echo i18("pref.cur.empty");?></li>
				</ul>
			</div>
		</form>
	</div>
</div>
</script>
