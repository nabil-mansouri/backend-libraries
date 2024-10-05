<script type="text/ng-template" id="subview.html"> 
<div class="subviews">	
	<div class="container-fluid">
		<div class="toolbar row">
			<div class="col-sm-6 hidden-xs">
				<div class="page-header">
					<h4>
						{{title}}<small class="bis">{{subtitle}}</small>
					</h4>
				</div>
			</div>
			<div class="col-sm-6 col-xs-12">
				<a href="javascript:;" class="back-subviews" ng-click="back()" ng-show="canBack()" style="opacity: 1 !important;left: 0px;"> <i
					class="fa fa-chevron-left" ></i> BACK
				</a> 
				<a href="javascript:;" class="close-subviews" ng-click="close()"
						style="display: block !important; opacity: 1 !important; left: 0px;"> <i
						class="fa fa-times"></i> CLOSE
				</a>
			</div>
		</div>
	</div>
	<div class="subviews-container"></div>
</div>
</script>