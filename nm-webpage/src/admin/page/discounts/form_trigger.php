<div nbgroupnext>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error>
			<label class="control-label col-md-6 small"> 
			<?php echo i18("discount.trigger.dateeq")?>
			<input type="hidden" ng-model="get('DateEq')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="DateEq"></nbradiobi>
			</div>
			<div class="col-md-12" ng-if="isVisible('DateEq')" nb-has-error>
				<label class="control-label col-md-6 small"> 
				<?php echo i18("discount.trigger.date.fill")?>
			</label>
				<div class="col-md-6">
					<input type="text" ng-model="discount.trigger['DateEq']['date']" ng-required="true"
						mask-date name="DateEq" />
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error>
			<label class="control-label col-md-6 small"> 
			<?php echo i18("discount.trigger.datebegin")?>
			<input type="hidden" ng-model="get('DateBegin')['show']" ng-required="true" />
			</label>
			<div class="col-md-6 btn-group">
				<nbradiobi setter="setter" name="DateBegin"></nbradiobi>
			</div>
			<div class="col-md-12" ng-if="isVisible('DateBegin')" nb-has-error>
				<label class="control-label col-md-6 small"> 
				<?php echo i18("discount.trigger.date.fill")?>
			</label>
				<div class="col-md-6">
					<input type="text" ng-required="true" mask-date
						ng-model="discount.trigger['DateBegin']['date']" />
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error >
			<label class="control-label col-md-6 small"> 
			<?php echo i18("discount.trigger.dateend")?>
			<input type="hidden" ng-model="get('DateEnd')['show']" ng-required="true" />
			</label>
			<div class="col-md-6 btn-group">
				<nbradiobi setter="setter" name="DateEnd"></nbradiobi>
			</div>
			<div class="col-md-12" ng-if="isVisible('DateEnd')" nb-has-error>
				<label class="control-label col-md-6 small"> 
				<?php echo i18("discount.trigger.date.fill")?>
			</label>
				<div class="col-md-6">
					<input type="text" ng-model="discount.trigger['DateEnd']['date']" ng-required="true"
						mask-date />
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error>
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.trigger.period")?>
		<input type="hidden" ng-model="get('Period')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="Period"></nbradiobi>
			</div>
		</div>
		<div class="form-group col-md-12" ng-if="isVisible('Period')" nb-has-error>
			<label class="control-label col-md-6 small"> 
			<?php echo i18("discount.trigger.period.number")?>
		</label>
			<div class="col-md-3">
				<input type="number" min="1" ng-model="discount.trigger['Period']['number']"
					ng-required="true" />
			</div>
			<div class="col-md-3">
				<select type="text" ng-model="discount.trigger['Period']['period']" ng-required="true">
					<option value="Day" selected="selected"><?php echo i18("day")?></option>
					<option value="Month"><?php echo i18("month")?></option>
					<option value="Year"><?php echo i18("year")?></option>
				</select>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error >
			<label class="control-label col-md-6 small"> 
			<?php echo i18("discount.trigger.periodlimit")?>
			<input type="hidden" ng-model="get('PeriodLimit')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="PeriodLimit"></nbradiobi>
			</div>
			<div class="col-md-12" ng-if="isVisible('PeriodLimit')" nb-has-error>
				<label class="control-label col-md-6 small"> 
				<?php echo i18("discount.trigger.periodlimit.fill")?>
			</label>
				<div class="col-md-6">
					<input type="number" ng-model="discount.trigger['PeriodLimit']['number']"
						ng-required="true" />
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error >
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.trigger.usedlimit")?>
		<input type="hidden" ng-model="get('NbTimes')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="NbTimes"></nbradiobi>
			</div>
			<div class="col-md-12" ng-if="isVisible('NbTimes')" nb-has-error>
				<label class="control-label col-md-6 small"> 
				<?php echo i18("discount.trigger.nbtimes.fill")?>
			</label>
				<div class="col-md-6">
					<input type="number" ng-model="discount.trigger['NbTimes']['number']"
						ng-required="true" />
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error>
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.trigger.nbuserabs")?>
		<input type="hidden" ng-model="get('NbUserAbs')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="NbUserAbs"></nbradiobi>
			</div>
			<div class="col-md-12" ng-if="isVisible('NbUserAbs')" nb-has-error>
				<label class="control-label col-md-6 small"> 
				<?php echo i18("discount.trigger.nbuserabs.fill")?>
			</label>
				<div class="col-md-6">
					<input type="number" ng-model="discount.trigger['NbUserAbs']['number']"
						ng-required="true" />
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error >
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.trigger.cumulable")?>
		<input type="hidden" ng-model="get('Cumulable')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="Cumulable"></nbradiobi>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error >
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.trigger.birthday")?>
		<input type="hidden" ng-model="get('Birthday')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="Birthday"></nbradiobi>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error >
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.trigger.birthday.signin")?>
		<input type="hidden" ng-model="get('BirthdaySign')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="BirthdaySign"></nbradiobi>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error >
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.trigger.onsignin")?>
		<input type="hidden" ng-model="get('OnSignin')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="OnSignin"></nbradiobi>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error >
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.trigger.onlogin")?>
		<input type="hidden" ng-model="get('OnLogin')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="OnLogin"></nbradiobi>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error >
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.trigger.parrain")?>
		<input type="hidden" ng-model="get('Parrain')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="Parrain"></nbradiobi>
			</div>
		</div>
		<div class="form-group col-md-12" ng-if="isVisible('Parrain')" nb-has-error>
			<label class="control-label col-md-6 small"> 
			<?php echo i18("discount.trigger.parrain.number")?>
		</label>
			<div class="col-md-6">
				<input type="number" min="1" ng-model="discount.trigger['Parrain']['number']"
					ng-required="true" />
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error>
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.trigger.filleul")?>
		<input type="hidden" ng-model="get('Filleul')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="Filleul"></nbradiobi>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error>
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.trigger.smiles")?>
		<input type="hidden" ng-model="get('Fidelity')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="Fidelity"></nbradiobi>
			</div>
		</div>
		<div class="form-group col-md-12" ng-if="isVisible('Fidelity')" nb-has-error>
			<label class="control-label col-md-6 small"> 
			<?php echo i18("discount.trigger.smiles.number")?>
		</label>
			<div class="col-md-6">
				<input type="number" min="1" ng-model="discount.trigger['Fidelity']['number']"
					ng-required="true" />
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error>
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.trigger.usergroupe")?>
		<input type="hidden" ng-model="get('UserGroupe')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="UserGroupe"></nbradiobi>
			</div>
		</div>
		<div class="form-group col-md-12" ng-if="isVisible('UserGroupe')" nb-has-error>
			<label class="control-label col-md-6 small"> 
			<?php echo i18("discount.trigger.usergroupe.choice")?>
		</label>
			<div class="col-md-3">
				<select min="1" ng-model="discount.trigger['UserGroupe']['users']" ng-required="true"
					multiple>
				</select>
			</div>
			<div class="col-md-3">
				<select min="1" ng-model="discount.trigger['UserGroupe']['groups']" ng-required="true"
					multiple>
				</select> <input type="hidden" ng-model="neverExistsUserGroupe"
					ng-required="discount.trigger['UserGroupe']['groups'].length>0 || discount.trigger['UserGroupe']['users'].length>0" />
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error>
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.trigger.nbcommande")?>
		<input type="hidden" ng-model="get('NbCommande')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="NbCommande"></nbradiobi>
			</div>
		</div>
		<div class="form-group col-md-12" ng-if="isVisible('NbCommande')" nb-has-error>
			<label class="control-label col-md-6 small"> 
			<?php echo i18("discount.trigger.nbcommande.number")?>
		</label>
			<div class="col-md-6">
				<input type="number" min="1" ng-model="discount.trigger['NbCommande']['number']"
					ng-required="true" />
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error>
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.trigger.depense")?>
		<input type="hidden" ng-model="get('Depense')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="Depense"></nbradiobi>
			</div>
		</div>
		<div class="form-group col-md-12" ng-if="isVisible('Depense')">
			<div class="col-md-12" nb-has-error>
				<label class="control-label col-md-6 small"> 
				<?php echo i18("discount.trigger.depense.number")?>
			</label>
				<div class="col-md-6">
					<input type="number" min="1" ng-model="discount.trigger['Depense']['amount']"
						ng-required="true" />
				</div>
			</div>
			<div class="col-md-12" nb-has-error>
				<label class="col-md-6 control-label small">
				<?php echo i18("discount.trigger.depense.period")?>
			</label>
				<div class="col-md-3">
					<input type="number" min="1" ng-model="discount.trigger['Depense']['number']"
						ng-required="true" />
				</div>
				<div class="col-md-3">
					<select type="text" ng-model="discount.trigger['Depense']['period']"
						ng-required="true">
						<option value="Day"><?php echo i18("day")?></option>
						<option value="Month"><?php echo i18("month")?></option>
						<option value="Year"><?php echo i18("year")?></option>
					</select>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error >
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.trigger.oncommande")?>
		<input type="hidden" ng-model="get('OnCommande')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="OnCommande"></nbradiobi>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error >
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.trigger.onproduit")?>
		<input type="hidden" ng-model="get('OnProduit')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="OnProduit"></nbradiobi>
			</div>
		</div>
		<div class="form-group col-md-12" ng-if="isVisible('OnProduit')" nb-has-error>
			<label class="control-label col-md-6 small"> 
			<?php echo i18("discount.trigger.onproduit.choice")?>
		</label>
			<div class="col-md-3">
				<input type="number" min="1" ng-model="discount.trigger['OnProduit']['users']"
					ng-required="true" />
			</div>
			<div class="col-md-3">
				<select min="1" ng-model="discount.trigger['OnProduit']['groups']" ng-required="true"
					multiple>
				</select>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error >
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.trigger.restaurants")?>
		<input type="hidden" ng-model="get('Restaurants')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="Restaurants"></nbradiobi>
			</div>
		</div>
		<div class="form-group col-md-12" ng-if="isVisible('Restaurants')" nb-has-error>
			<label class="control-label col-md-6 small"> 
			<?php echo i18("discount.trigger.restaurants.choice")?>
		</label>
			<div class="col-md-4">
				<select min="1" ng-model="discount.trigger['Restaurants']['restaurants']"
					ng-required="true" multiple>
				</select>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="trigger">
		<div class="form-group col-md-12" nb-has-error >
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.trigger.geoloc")?>
		<input type="hidden" ng-model="get('Geoloc')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="Geoloc"></nbradiobi>
			</div>
		</div>
		<div class="form-group col-md-12" ng-if="isVisible('Geoloc')" nb-has-error>
			<label class="control-label col-md-6 small"> 
			<?php echo i18("discount.trigger.geoloc.choice")?>
		</label>
			<div class="col-md-4">
				<select nbarray nbarraymin="1" ng-model="discount.trigger['Geoloc']['groups']"
					ng-required="true" multiple>
				</select>
			</div>
		</div>
	</div>
</div>
