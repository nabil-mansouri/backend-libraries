<div class="row hei60 nbpanel-group-top" >
	<div class="col-md-9 panel hei100 paddh0 panelwrapper" loader>
		<div data-ng-controller="PriceTable">
			<?php  include 'table.php'?>
			<?php  include 'table_filter.php'?>
		</div>
	</div>
	<div class="col-md-3 panel right paddh0 hei100 panelwrapper" loader>
		<preferences></preferences>
	</div>
</div>
<div class="row hei40" loader>
	<div class="col-md-12 panel hei100 paddh0 panelwrapper">
		<div class="panel-heading blank0">
			<h5 class="col-md-12">
				<?php echo i18("tarif.title.form"); ?>
			</h5>
		</div>
		<div class="panel-body blank0">
			<?php  include 'form.php'?>
		</div>
	</div>
</div>

