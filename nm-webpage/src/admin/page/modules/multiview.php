<script id="multiview.html" type="text/ng-template">
<div class="col-md-12 panel hei100 paddh0 panelwrapper">
	<div class="panel-heading blank0" data-ng-repeat="view in views track by $index" data-ng-show="isSelected($index)" data-ng-class="getCssClass(view)">
		<h5 class="col-md-12">
			<div class="col-md-9">
				<ng-include src="view.head"></ng-include>
			</div>
			<div class="col-md-1">
				<i class="fa fa-chevron-left" data-ng-show="canPrevious()" data-ng-click="previous()"></i>
			</div>
			<div class="col-md-1">
				<i class="fa fa-chevron-right" data-ng-show="canNext()" data-ng-click="next()"></i>
			</div>
			<div class="col-md-1">
				<i class="fa fa-times" data-ng-show="canCancel()" data-ng-click="cancel()"></i>
			</div>
		</h5>
	</div>
	<div class="panel-body blank0" data-ng-repeat="view in views track by $index" data-ng-show="isSelected($index)" data-ng-class="getCssClass(view)">
		<ng-include src="view.body"></ng-include>
	</div>
</div>
</script>
