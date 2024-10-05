<script type="text/ng-template" id="imgform.html"> 
<div>
					<label class="col-sm-12 nb-image-add" ng-file-upload="options"> 
						<input type="file"
						name="file" multiple="" data-file-upload="options">
						<span class="btn btn-info btn-block"><i class="fa fa-plus"></i>&nbsp;
							<?php echo i18("product.form.image.add"); ?> </span>
					</label>
					<div class="col-sm-12"
						ng-class="{true:'has-error ng-show', false:'ng-hide'}[form.imgURL.$error.required]">
						<span class="control-label"><?php echo i18("product.form.image.required"); ?></span>
					</div>
					<div class="col-sm-12 gallery-env">
						<div class="col-sm-4 col-xs-4 nb-main-image" data-tag="1d"
							ng-show="mainImg != null">
							<article class="image-thumb nb-image-thumb">
								<a href="javascript:;" class="image"> <img ng-src="{{mainImg.absoluteURL}}"></a>
								<div class="image-options nb-image-options">
									<a href="javascript:;" class="delete"
										ng-click="removeMainImage(); $event.preventDefault();"> <i class="fa fa-trash-o"></i></a>
								</div>
								<input name="imgURL" type="hidden" ng-model="mainImg.absoluteURL"
									ng-required="true" />
							</article>
						</div>
						<div class="col-sm-4 col-xs-4 nb-main-image " data-tag="1d"
							ng-show="mainImg == null">
							<article class="image-thumb nb-image-thumb nb-noimage">
								<a href="javascript:;" class="image"> <img src="<?php echo url('img/no-image.png')?>"
									style="max-height: 300px" /></a>
							</article>
						</div>
						<div class="col-sm-2 col-xs-2" data-tag="3d" ng-repeat="img in imgs">
							<article class="image-thumb nb-image-thumb">
								<a href="javascript:;" class="image"> <img ng-src="{{img.absoluteURL}}"
									class="nb-small"></a>
								<div class="image-options nb-image-options">
									<a href="javascript:;" class="edit"	ng-click="setMainImage($index)">
										<i class="fa fa-thumbs-o-up"></i>
									</a> 
									<a href="javascript:;" class="delete" ng-click="removeImage($index)">
										<i class="fa fa-trash-o"></i></a>
								</div>
							</article>
						</div>
					</div>
</div>
</script>