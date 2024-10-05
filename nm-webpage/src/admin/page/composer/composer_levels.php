<div ng-controller="ComposerLevel">
	<div class="btn-group btn-group-justified col-md-12" ng-repeat="(level,nodes) in levels">
		<a href="javascript:;" class="btn {{getCssChoice(choice)}}"
			ng-disabled="isDisable(choice)" ng-click="goTo(choice)" ng-repeat="choice in nodes">
			{{choice.name}}</a>
	</div>
</div>
