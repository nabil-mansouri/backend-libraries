<div ng-controller="OrderDetail" class="nb-product-table">
	<div class="col-md-12 nb-product-table-panel" ng-if="visible && order.$resolved">
		<div class="panel panel-white">
			<div class="panel-heading">
				<h4 class="panel-title">
					<?php echo i18("order.detail.title"); ?> 
					<span class="label label-info">{{order.uuid}}</span>
				</h4>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-4">
						<?php require 'detail_cart.php'?>
					</div>
					<div class="col-xs-4">
						<?php require 'detail_states.php'?>
					</div>
					<div class="col-xs-4">
						<?php require 'detail_client.php'?>
					</div>
					<div class="col-xs-6">
						<?php require 'detail_transaction.php'?>
					</div>
					<div class="col-md-6 panel-chat">
						<?php require 'detail_chat.php'?>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>