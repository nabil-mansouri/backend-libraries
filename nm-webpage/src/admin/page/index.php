<?php
include_once 'functions.php';
if (defined ( "PLUGINS" )) {
	plugin ( unserialize ( PLUGINS ) );
}
if (defined ( "MODULES" )) {
	loadLangModules ( unserialize ( MODULES ) );
}
?>
<!DOCTYPE html>
<html style="height: 100%">
<head>
	<?php include_once 'commons/header.php';?>
	<?php if(file_exists(DIR_VIEW.'/header.php')): ?>
		<?php include_once DIR_VIEW.'/header.php'?>
	<?php endif; ?>
</head>
<body ng-app="mainApp" style="height: 100%" ng-controller="AdminController" data-ng-cloak="">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12 nbheader">
				<div class="col-md-12">
					<ul class="nav navbar-nav navbar-right">
						<li><a href="javascript:;">
								<i class="fa fa-globe"></i>
								<span class="badge-alert"></span>
							</a></li>
						<li><a href="javascript:;">
								<i class="fa fa-comments-o"></i>
								<span class="badge-alert"></span>
							</a></li>
					</ul>
				</div>
			</div>
			<div class="col-md-2 nbmenu padd0">
				<?php include_once 'commons/menu.php';?>
			</div>
			<div class="col-md-10 hei100">
				<?php if(file_exists(DIR_VIEW.'/'.MAIN_VIEW)): ?>
					<?php include_once DIR_VIEW.'/'.MAIN_VIEW;?>
				<?php endif; ?>
			</div>
		</div>
	</div>
	<?php include_once 'commons/footer.php';?>
	<?php if(file_exists(DIR_VIEW.'/footer.php')): ?>
		<?php include_once DIR_VIEW.'/footer.php'?>
	<?php endif; ?>
	<?php loadCommons();?>
	<?php loadComponents()?>
	<script type="text/javascript">
		var mainApp = angular.module("mainApp",Wick.DEPENDENCIES);
	</script>
</body>
</html>

