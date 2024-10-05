<script type="text/ng-template" id="nbradiobi.html"> 
<button class="btn btn-yellow btn-sm" type="button" ng-click="set(false)" ng-class='{active: isActive(false)}'><?php echo i18('off'); ?></button>
<button class="btn btn-success btn-sm" type="button" ng-click="set(true)" ng-class='{active: isActive(true)}'><?php echo i18('on'); ?></button>
</script>