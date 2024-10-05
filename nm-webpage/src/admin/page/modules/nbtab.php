<script type="text/ng-template" id="tab.html"> 
<div class="tabbable nb-tabs-left">
			<ul id="myTab3" class="nav nav-tabs">
				<li 
					ng-repeat="model in list" ng-click="select($index)"
					ng-class="{true: 'active', false: ''}[isVisible($index)]">
					<a href="javascript:;" ng-bind="getName($index)"></a></li>
				<li ng-click="add()"><a class="nb-actions"> + </a></li>
			</ul>
			<div class="nb-tab-content">
				<div class="tab-pane fade in  active" ng-repeat="current in  list"
					ng-form="subForm"
					ng-class="{true:'active', false:'nb-force-hide'}[isVisible($index)]">
					<a class="btn btn-danger nb-close-tab" ng-show="list.length > 1"
						href="javascript:;" ng-click="delete($index)"> <i
						class="fa fa-times fa fa-white"></i>
					</a>
					<div inject></div>
				</div>
				<div style="clear: both"></div>
			</div>
</div>
</script>