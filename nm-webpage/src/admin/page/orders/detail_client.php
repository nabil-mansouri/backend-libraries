<div ng-if="order.client.ignore">
	<h4>
	<?php echo i18("order.detail.account"); ?> 
	</h4>
	<div class="well">
		<address>
			<strong> <?php  echo i18("order.detail.unknown")?></strong>
		</address>
	</div>
</div>
<div ng-if="!order.client.ignore">
	<h4>
	<?php echo i18("order.detail.account"); ?> 
	</h4>
	<div class="well">
		<address>
			<strong>{{order.client.firstname}} {{order.client.name}}</strong>
			<!--  -->
			<br> <span ng-if="order.client.phone"> <abbr title="Phone">P:</abbr>{{order.client.phone}}
			</span>
			<!--  -->
			<br> <a href="javascript:" ng-if="order.client.email">{{order.client.email}} </a>
			<!--  -->
			<br> <strong><?php  echo i18("order.detail.client.id")?></strong><br>
			{{order.client.reference}}<br>
		</address>
	</div>
	<h4>
	<?php echo i18("order.detail.address"); ?> 
</h4>
	<div class="well" ng-if="order.client.address">
		<address>
			<strong>{{order.client.address.name}}</strong>
			<!--  -->
			<br>{{order.client.address.geocode}}
			<!--  -->
			<br>{{order.client.address.complement}}
		</address>
		<div ng-controller="AddressMapCtrl" ng-init="init(order.client.address)">
			<gm-map gm-map-id="'myMap'" gm-map-options="options.map" style="height:200px"> <gm-markers
				gm-objects="markers" gm-get-lat-lng="latlon"> </gm-markers> </gm-map>
		</div>

	</div>
</div>
