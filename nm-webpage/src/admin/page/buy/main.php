<div class="row hei40 nbpanel-group-top">
	<div class="col-md-9 panel hei100 paddh0 panelwrapper">
		<ng-view></ng-view>
	</div>
	<div class="col-md-3 panel right paddh0 hei100 panelwrapper">
		<preferences></preferences>
	</div>
</div>
<div class="row hei60" loader>
	<div class="col-md-9 panel hei100 paddh0 panelwrapper">
		<multiview></multiview>
	</div>
	<div class="col-md-3 panel right paddh0 hei100 panelwrapper">
		<?php include 'cart.php'?>
	</div>
</div>
