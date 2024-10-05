<div>
	<div class="col-md-10 col-md-offset-1" nbnext="comm">
		<div class="form-group col-md-12" nb-has-error>
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.communication.auto")?>
			<input type="hidden" ng-model="get('AutoComm')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="AutoComm"></nbradiobi>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="comm">
		<div class="form-group col-md-12" nb-has-error>
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.communication.email")?>
			<input type="hidden" ng-model="get('Email')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="Email"></nbradiobi>
			</div>
			<div class="col-md-12" ng-if="isVisible('Email')" nb-has-error>
				<nbtabi18 list="discount.communication['Email']['contents']" factory="onAddLangEmail">
				<div class="form-group compose-mail" nb-has-error>
					<textarea ckedit ng-model="current.content" cktext="current.contentText" class=""></textarea>
					<input type="hidden" ng-model="current.contentText" name="descT" ng-required="true">
					</textarea>
				</div>
				</nbtabi18>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="comm">
		<div class="form-group col-md-12" nb-has-error>
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.communication.sms")?>
			<input type="hidden" ng-model="get('Sms')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="Sms"></nbradiobi>
			</div>
			<div class="col-md-12" ng-if="isVisible('Sms')" nb-has-error>
				<nbtabi18 list="discount.communication['Sms']['contents']" factory="onAddLangSms">
				<div class="form-group compose-mail" nb-has-error>
					<textarea ckedit ng-model="current.content" cktext="current.contentText" class=""></textarea>
					<input type="hidden" ng-model="current.contentText" name="descT" ng-required="true">
					</textarea>
				</div>
				</nbtabi18>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-md-offset-1" nbnext="comm">
		<div class="form-group col-md-12" nb-has-error>
			<label class="control-label col-md-6 small"> 
		<?php echo i18("discount.communication.code")?>
			<input type="hidden" ng-model="get('Code')['show']" ng-required="true" />
			</label>
			<div class="col-md-6">
				<nbradiobi setter="setter" name="Code"></nbradiobi>
			</div>
			<div class="col-md-12" ng-if="isVisible('Code')" nb-has-error>
				<label class="control-label col-md-6 small"> 
				<?php echo i18("discount.communication.code.content")?>
			</label>
				<div class="col-md-6">
					<input type="text" ng-model="discount.communication['Code']['content']"
						ng-required="true" />
				</div>
			</div>
		</div>
	</div>
</div>