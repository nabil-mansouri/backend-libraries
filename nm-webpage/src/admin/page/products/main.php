<div class="row hei40 nbpanel-group-top">
	<div class="col-md-9 panel hei100 paddh0 panelwrapper" data-ng-controller="ProductMenuTable">
		<ng-view></ng-view>
	</div>
	<div class="col-md-3 panel right paddh0 hei100 panelwrapper">
		<preferences></preferences>
	</div>
</div>
<div class="row hei60" loader>
	<multiview></multiview>
</div>
<script id="product_table.html" type="text/ng-template">
	<?php include 'table.php';?>
</script>
<script id="product_form_title.html" type="text/ng-template">
	<?php echo i18("products.add")?>
</script>
<script id="product_form.html" type="text/ng-template">
	<div data-ng-controller="ProductFormController">
		<?php include 'form.php';?>
	</div>
</script>
<script id="product_draft_form_title.html" type="text/ng-template">
	<?php echo i18("products.add")?>
</script>
<script id="product_draft_form.html" type="text/ng-template">
	<div data-ng-controller="ProductDraftFormController">
		<?php include 'form.php';?>
	</div>
</script>
<script id="category_form_title.html" type="text/ng-template">
	<?php echo i18("category.add")?>
</script>
<script id="category_form.html" type="text/ng-template">
	<?php include 'form_category.php';?>
</script>
<script id="ingredient_form_title.html" type="text/ng-template">
	<?php echo i18("ingredient.add")?>
</script>
<script id="ingredient_form.html" type="text/ng-template">
	<?php include 'form_ingredient.php';?>
</script>
<script id="category_tree.html" type="text/ng-template">
	<?php include 'subform_category.php'?>
</script>
<script id="subform_ingredients.html" type="text/ng-template">
	<?php  include 'subform_ingredient.php'?>
</script>
<script id="subform_subproduct.html" type="text/ng-template">
	<?php  include 'subform_subproduct.php'?>
</script>
<script id="price_table.html" type="text/ng-template">
	<?php  include '../prices/table.php'?>
</script>
