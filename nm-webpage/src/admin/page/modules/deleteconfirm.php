<script id="deleteconfirm.html" type="text/ng-template">
<tr class="bg-warning">
	<td class="center bg-orange text-warning nbrowtxt" colspan="{{colspan}}">
		<h6 class="col-md-4 col-md-offset-4">{{label}}</h6>
		<div class="btn-group col-md-4">
			<button type="button" class="btn btn-warning fa fa-arrow-left btn-sm" data-ng-click="cancel()">
				<?php echo i18("delete.cancel"); ?>
			</button>
			<button type="button" class="btn btn-danger fa fa-times btn-sm" data-ng-click="confirm()">
				<?php echo i18("delete.submit"); ?>
			</button>
		</div>
	</td>
</tr>
</script>
