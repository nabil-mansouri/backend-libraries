<script type="text/ng-template" id="formstate.html"> 
<div style="position: relative;" ng-show="visible">
	<div class="popover fade bottom in" role="tooltip"
		style="top: 0px; left: 84px; display: block;"
		ng-show="visible">
		<div class="arrow"></div>
		<div class="popover-content">
			<div ng-repeat="row in rows"
				ng-class="{true:'has-error', false:'has-success'}[row.error]">
				<div class="checkbox-inline">
					<div class="icheckbox_flat-green col-sm-2"
						ng-class="{true:'', false:'checked'}[row.error]">
						<input type="checkbox" class="flat-green" disabled="disabled"
							style="display: none" />
					</div>
					<div class="col-sm-10 control-label">
						{{row.txt | nbI18}}
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</script>