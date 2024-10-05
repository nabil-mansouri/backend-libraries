<div class="panel panel-grey">
	<div class="panel-heading" nbspinner promise="refreshPromise">
		<label class="col-md-12"><?php echo i18("form.category.title")?></label>
	</div>
	<small class="help-block panel-help" readmore><?php echo i18("form.category.info")?><em class="more">...</em>
	</small>
	<div class="panel-body ">
		<div class="tree">
			<ul>
				<li>
					<span>
						<i class="icon-folder-open"></i><?php echo i18("all_e")?>
						</span>
					<ul>
						<li data-ng-repeat="rootNode in categories" dx-start-with="rootNode">
							<span data-ng-click="select($dxPrior)" data-ng-class="getCssClass($dxPrior)">
								<i class="fa fa-plus" data-ng-if="canExpand($dxPrior)" data-ng-click="toggle($dxPrior,$event)"></i>
								<i class="fa fa-minus" data-ng-if="canUnExpand($dxPrior)" data-ng-click="toggle($dxPrior,$event)"></i>
								{{$dxPrior.cms.name}}
								<i class="fa fa-edit pull-right" data-ng-click="edit($dxPrior,$event)"></i>
							</span>
							<ul>
								<li ng-repeat="node in $dxPrior.childrens | orderBy:'name'" class="pointer" data-ng-if="isChildrenVisible($dxPrior)">
									<div dx-connect="node"></div>
								</li>
								<li data-ng-click="createChild($dxPrior)" class="pointer">
									<small>
										<em><?php echo i18("form.category.sub.create")?></em>
									</small>
								</li>
							</ul>
						</li>
						<li data-ng-click="createRoot()" class="pointer">
							<small>
								<em><?php echo i18("form.category.create")?></em>
							</small>
						</li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</div>
</script>
</div>