<section class="panel" id="appDiscountForm" ng-controller="DiscountForm"
	ng-show="panelVisible">
	<header class="panel-heading">
		<?php echo i18("discount.create"); ?>
		<span class="tools pull-right"> <a href="javascript:;" class="fa fa-chevron-down"></a>
		</span>
	</header>
	<div class="panel-body">
		<form role="form" name="discountForm" data-file-upload="options">
			<h3 class="col-sm-12">
				<?php echo i18("discount.form.desc"); ?>
			</h3>
			<div class="col-md-12">
				<div class="form-group" nb-has-error>
					<label class="control-label"><?php echo i18("product.menu.form.desc.name"); ?></label>
					<div>
						<input class="form-control" type="text" ng-model="discount.name" name="name"
							ng-required="true" />
					</div>
				</div>
			</div>
			<h3 class="col-sm-12">
				<?php echo i18("discount.form.type"); ?>
			</h3>
			<h5 class="col-sm-12"><?php echo i18("discount.form.part1")?></h5>
			<!-- FIRST CHOICE -->
			<?php require_once 'form_type.php';?>	
			<!-- /FIRST CHOICE -->
			<!-- PRODUCT part -->
			<div class="form-group col-sm-12" ng-if="discount.type=='Product'">
				<div class="col-md-6">
					<?php require_once 'form_special.php';?>				
				</div>
				<div class="col-md-6" ng-if="hasSpecial()">
					<?php require_once 'form_special_price.php';?>				
				</div>
			</div>
			<!-- DECREASE part -->
			<div class="form-group col-sm-12" ng-if="discount.type=='Decrease'"
				ng-controller="DiscountFormDecrease">
				<?php require_once 'form_decrease.php';?>
			</div>
			<!-- GIFT part -->
			<div class="form-group col-sm-12" ng-if="discount.type=='Gift'"
				ng-controller="DiscountGift">
				<?php require_once 'form_gift.php';?>
			</div>
			<!-- FREE part -->
			<div class="form-group col-sm-12" ng-if="discount.type=='Free'"
				ng-controller="DiscountFree">
				<?php require_once 'form_free.php';?>
			</div>
			<!-- TRIGGER -->
			<h1><?php echo i18("discount.form.part2")?></h1>
			<div class="form-group col-sm-12" ng-controller="DiscountTrigger">
				<?php require_once 'form_trigger.php';?>
			</div>
			<!-- /TRIGGER -->
			<!-- COMMUNCIATION -->
			<h1><?php echo i18("discount.form.part4")?></h1>
			<div class="form-group col-sm-12" ng-controller="DiscountCommunication">
				<?php require_once 'form_communication.php';?>
			</div>
			<!-- /COMMUNCIATION -->
			<!-- ACTION PART -->
			<div class="row">
				<div class="col-sm-12">
					<div class="btn-group btn-group-justified">
						<a class="btn btn-default btn-warning " href="javascript:;"
							ng-click="cancelDiscount()"> 
							<?php echo i18("discount.form.cancel"); ?>
						</a><a class="btn btn-default btn-info" href="javascript:;"
							ng-click="draftDiscount()">
							 <?php echo i18("discount.form.draft"); ?>
						</a> <a class="btn btn-default btn-success" href="javascript:;"
							ng-click="submitDiscount()" ng-disabled="discountForm.$invalid"> <?php echo i18("discount.form.submit"); ?>
						</a>
					</div>
				</div>
			</div>
		</form>
		<div class="no-display" id="subview-form-discount" ng-non-bindable>
			<div class="col-md-12" ng-controller="ProductFormDiscount">
				<?php include '../products/form.php';?>
			</div>
		</div>
		<div class="no-display" id="subview-form-gift" ng-non-bindable>
			<div class="col-md-12" ng-controller="ProductFormGift">
				<?php include 'subview_gift.php';?>
			</div>
		</div>
		<div class="no-display" id="subview-form-product" ng-non-bindable>
			<div class="col-md-12" ng-controller="ProductFormSubview">
					<?php include '../products/form.php';?>
				</div>
		</div>
	</div>
</section>