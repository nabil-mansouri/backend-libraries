<div>
	<h3><?php echo i18("cart.type.subtitle"); ?></h3>
	<div class="row">
		<div class=" col-md-4" ng-class="{'active':isSelected('InPlace')}"
			ng-click="setType('InPlace')">
			<a href="javascript:;" class="thumbnail"> <img style="height: 100px"
				src="https://cdn2.iconfinder.com/data/icons/windows-8-metro-style/128/table.png">
			</a>
		</div>
		<div class=" col-md-4" ng-class="{'active':isSelected('OutPlace')}"
			ng-click="setType('OutPlace')">
			<a href="javascript:;" class="thumbnail"> <img style="height: 100px"
				src="https://cdn0.iconfinder.com/data/icons/small-n-flat/24/678112-bag-128.png">
			</a>
		</div>
		<div class=" col-md-4" ng-class="{'active':isSelected('Delivered')}"
			ng-click="setType('Delivered')">
			<a href="javascript:;" class="thumbnail"> <img style="height: 100px"
				src="https://cdn2.iconfinder.com/data/icons/windows-8-metro-style/128/scooter.png">
			</a>
		</div>
	</div>
</div>