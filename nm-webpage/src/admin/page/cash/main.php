<div class="row ">
	<div class="col-md-7">
		<div class="col-md-12" ng-controller="CashMenu">
			<?php require 'menu.php';?>
		</div>
		<!-- Creer commande -->
		<ng-view></ng-view>
		<!-- Commande en attente -->
		<div class="col-md-12" style="display: none">
			<div class="panel panel-tasks nb-product-draft">
				<div class="panel-heading bg-red">
					<?php echo i18("cash.draft.title"); ?>
				</div>
				<ul class="panel-body">
					<li ng-repeat="dr in drafts"><a href="javascript:;"> <span
							sprintf="<?php echo i18('cash.draft.name') ?>" args="{{$index}}"></span> <span>{{getDraftName($index)}}</span>
							<i class="fa fa-edit" ng-click="selectDraft($index)"></i> <i class="fa fa-times"
							ng-click="deleteDraft($index)"></i>
					</a></li>
					<li ng-show="!hasDraft()"><a href="javascript:;"> <?php echo i18("product.draft.empty"); ?>
				</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="col-md-5">
		<?php require '../cart/cart.php';?>
	</div>
</div>
<script type="text/ng-template" id="empty.html"> 
</script>
<script type="text/ng-template" id="filter.html"> 
		<?php require '../composer/filter.php'; ?>
</script>
<script type="text/ng-template" id="choices.html"> 
		<?php require '../composer/composer_choices.php'; ?>
</script>
<script type="text/ng-template" id="ingredients.html"> 
		<?php require '../composer/composer_ingredients.php'; ?>
</script>
<script type="text/ng-template" id="restaurants.html"> 
		<?php require '../composer/restaurants.php'; ?>
</script>
<script type="text/ng-template" id="type.html"> 
		<?php require '../composer/type.php'; ?>
</script>
<script type="text/ng-template" id="client_cart.html"> 
		<?php require '../client/client_cart.php';?>
</script>
<script type="text/ng-template" id="cart_check.html"> 
		<?php require '../cart/cart_check.php';?>
</script>
<script type="text/ng-template" id="cart_draft.html"> 
		<?php require '../cart/cart_draft.php';?>
</script>
<script type="text/ng-template" id="cash_paiment.html"> 
		<?php require '../cash/cash_paiment.php';?>
</script>
<script type="text/ng-template" id="cart_submit.html"> 
		<?php require '../cart/cart_submit.php';?>
</script>