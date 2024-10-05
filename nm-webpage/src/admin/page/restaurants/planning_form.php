<accordion-group is-open="status.open[0]" data-ng-init="status.open[0]=true"> 
	<?php ?>
	<accordion-heading> <span class="small"><?php echo i18("planning.reccurent.add.title"); ?></span> <i class="pull-right glyphicon"
	ng-class="{'glyphicon-chevron-down': status.open[0], 'glyphicon-chevron-right': !status.open[0]}"></i> 
	<?php ?>	
	</accordion-heading>
	<?php include 'planning_form_recurrent.php'?>
</accordion-group>
<!--  -->
<accordion-group is-open="status.open[1]">
	<?php ?>
	<accordion-heading> <span class="small"><?php echo i18("planning.reccurent.close.title"); ?></span> <i class="pull-right glyphicon"
	ng-class="{'glyphicon-chevron-down': status.open[1], 'glyphicon-chevron-right': !status.open[1]}"></i>
        <?php ?>
    </accordion-heading> 
	<?php include 'planning_form_close.php'?>
</accordion-group>
<?php ?>
<accordion-group is-open="status.open[2]"> 
	<?php ?>
	<accordion-heading> <span class="small"><?php echo i18("planning.reccurent.open.title"); ?></span> <i class="pull-right glyphicon"
	ng-class="{'glyphicon-chevron-down': status.open[2], 'glyphicon-chevron-right': !status.open[2]}"></i> 
    <?php ?>
    </accordion-heading>
	<?php include 'planning_form_open.php'?>
</accordion-group>