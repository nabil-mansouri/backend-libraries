<script id="multimg.html" type="text/ng-template">
<div class="col-md-12">
	<div class="row">
		<div class="col-md-3" data-ng-controller="MultiImgSingleController" data-ng-init="init(images.img)">
			<div class="thumbnail" data-ng-if="images.img.relativeURL" data-ng-controller="FileUploadController">
				<label>
					<input type="file" name="file" class="nbhidden_file" />
					<img data-ng-src="{{images.img.absoluteURL}}" class="img-responsive">
				</label>
				<div class="caption paddv0">
					<i class="fa fa-times text-danger pull-right" data-ng-click="removeMain()"></i>
				</div>
			</div>
			<div class="thumbnail thumbnail_i" data-ng-if="!images.img.relativeURL" data-ng-controller="FileUploadController">
				<label>
					<input type="file" name="file" class="nbhidden_file" />
					<i class="fa fa-download"></i>
				</label>
			</div>
		</div>
		<input type="hidden" data-ng-model="images.img.relativeURL" data-ng-required="required.img" nb-haserror-watcher/>
	</div>
	<div class="row" data-ng-if="show.detail">
		<div class="col-md-2" data-ng-repeat="img in images.imgs" data-ng-controller="MultiImgSingleController" data-ng-init="init(img)">
			<div class="thumbnail" data-ng-if="img.relativeURL" data-ng-controller="FileUploadController">
				<label>
					<input type="file" name="file" class="nbhidden_file" />
					<img data-ng-src="{{img.absoluteURL}}" class="img-responsive">
				</label>
				<div class="caption paddv0">
					<i class="fa fa-times text-danger pull-right" data-ng-click="remove(img,$index)"></i>
					<i class="fa fa-thumbs-up pull-right" data-ng-click="select(img)"></i>
				</div>
			</div>
			<div class="thumbnail thumbnail_i" data-ng-if="!img.relativeURL" data-ng-controller="FileUploadController">
				<label>
					<input type="file" name="file" class="nbhidden_file" />
					<i class="fa fa-download"></i>
				</label>
				<div class="caption paddv0">
					<i class="fa fa-times text-danger pull-right" data-ng-click="remove(img,$event)"></i>
				</div>
			</div>
		</div>
		<div class="col-md-2" data-ng-controller="MultiImgSingleController" >
			<div class="thumbnail thumbnail_i" data-ng-controller="FileUploadController">
				<label>
					<input type="file" name="file" class="nbhidden_file" />
					<i class="fa fa-plus"></i></label>
			</div>
		</div>
	</div>
</div>
</script>
<script id="multimg_horizontal.html" type="text/ng-template">
<div class="row">
	<div class="col-md-2" data-ng-controller="MultiImgSingleController" data-ng-init="init(images.img)">
		<div class="thumbnail" data-ng-if="images.img.relativeURL" data-ng-controller="FileUploadController">
			<label>
				<input type="file" name="file" class="nbhidden_file" />
				<img data-ng-src="{{images.img.absoluteURL}}" class="img-responsive">
			</label>
			<div class="caption paddv0">
				<i class="fa fa-times text-danger pull-right" data-ng-click="removeMain()"></i>
			</div>
		</div>
		<div class="thumbnail thumbnail_i" data-ng-if="!images.img.relativeURL" data-ng-controller="FileUploadController">
			<label>
				<input type="file" name="file" class="nbhidden_file" />
				<i class="fa fa-download"></i>
			</label>
		</div>
	</div>
	<input type="hidden" data-ng-model="images.img.relativeURL" data-ng-required="required.img" nb-haserror-watcher />
	<div class="col-md-9" data-ng-if="show.detail">
		<div class="col-md-2" data-ng-repeat="img in images.imgs" data-ng-controller="MultiImgSingleController" data-ng-init="init(img)">
			<div class="thumbnail" data-ng-if="img.relativeURL" data-ng-controller="FileUploadController">
				<label>
					<input type="file" name="file" class="nbhidden_file" />
					<img data-ng-src="{{img.absoluteURL}}" class="img-responsive">
				</label>
				<div class="caption paddv0">
					<i class="fa fa-times text-danger pull-right" data-ng-click="remove(img,$index)"></i>
					<i class="fa fa-thumbs-up pull-right" data-ng-click="select(img)"></i>
				</div>
			</div>
			<div class="thumbnail thumbnail_i" data-ng-if="!img.relativeURL" data-ng-controller="FileUploadController">
				<label>
					<input type="file" name="file" class="nbhidden_file" />
					<i class="fa fa-download"></i>
				</label>
				<div class="caption paddv0">
					<i class="fa fa-times text-danger pull-right" data-ng-click="remove(img,$event)"></i>
				</div>
			</div>
		</div>
		<div class="col-md-2" data-ng-controller="MultiImgSingleController">
			<div class="thumbnail thumbnail_i" data-ng-controller="FileUploadController">
				<label>
					<input type="file" name="file" class="nbhidden_file" />
					<i class="fa fa-plus"></i>
				</label>
			</div>
		</div>
	</div>
</div>
</script>

