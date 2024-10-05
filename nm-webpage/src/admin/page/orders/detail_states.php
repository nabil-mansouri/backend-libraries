<h4>
	<?php echo i18("order.detail.states"); ?> 
</h4>
<order-stateflow message-begin="<?php echo i18("order.states.WaitingPayment"); ?>"
	message="<?php echo i18("order.states.Paid"); ?>" state-begin="WaitingPayment"
	state="Paid" order="order"> </order-stateflow>
<order-stateflow message-begin="<?php echo i18("order.states.Preparing"); ?>"
	message="<?php echo i18("order.states.Prepared"); ?>" state-begin="Preparing"
	state="Prepared" order="order"> </order-stateflow>
<order-stateflow message="<?php echo i18("order.states.Served"); ?>" state="Served"
	order="order"> </order-stateflow>
<order-stateflow message-begin="<?php echo i18("order.states.Delivering"); ?>"
	message="<?php echo i18("order.states.Delivered"); ?>" state-begin="Delivering"
	state="Delivered" order="order"> </order-stateflow>
<order-stateflow message="<?php echo i18("order.states.Refunded"); ?>" state="Refunded"
	order="order"> </order-stateflow>
<order-stateflow message="<?php echo i18("order.states.Returned"); ?>" state="Returned"
	order="order"> </order-stateflow>
<order-stateflow message="<?php echo i18("order.states.Changed"); ?>"
	message-begin="<?php echo i18("order.states.Changing"); ?>" state-begin="Changing"
	state="Changed" order="order"> </order-stateflow>
<script type="text/ng-template" id="order-stateflow.html"> 
	<div ng-if="isVisible()">
		<div class="col-md-12 btn" ng-class="getClass()" 
			ng-if="isBeginVisible()"
			ng-click="selectStateBegin()" ng-disabled="isDisabled()">
			<!--  -->
			{{messageBegin}}
			<!--  -->
		</div>
		<div class="col-md-12 btn" ng-class="getClass()" 
			ng-if="!isBeginVisible()"
			ng-click="selectState()" ng-disabled="isDisabled()">
			<!--  -->
			{{message}}
			<!--  -->
		</div>
	</div>
	
</script>
