<div class="row hei40 nbpanel-group-top">
	<div class="col-md-9 panel hei100 paddh0 panelwrapper" data-ng-controller="RestaurantMenuTable">
		<ng-view></ng-view>
	</div>
	<div class="col-md-3 panel right paddh0 hei100 panelwrapper">
		<preferences></preferences>
	</div>
</div>
<div class="row hei60" loader>
	<multiview></multiview>
</div>
<script id="resto_table.html" type="text/ng-template">
		<?php include 'table.php';?>
</script>
<script id="resto_form_title.html" type="text/ng-template">
	<?php echo i18("resto.create")?>
</script>
<script id="resto_form.html" type="text/ng-template"> 
	<div data-ng-controller="RestaurantFormController">
		<?php include 'form.php';?> 
	</div>
</script>
<script id="planning_form_title.html" type="text/ng-template">
	<?php echo i18("planning.title"); ?>
</script>
<script id="planning_form.html" type="text/ng-template"> 
	<?php include 'planning.php';?> 
</script>
