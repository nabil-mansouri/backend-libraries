<script id="multicms.html" type="text/ng-template">
<div class="col-md-12 box-grey box">
	<div class="col-md-3 blank0 hei100">
		<ul class="list-group col-md-12 blank0">
			<li class="list-group-item" data-ng-class="getCssSelected(content)" data-ng-repeat="content in cms.contents" data-ng-click="select(content)" data-nb-haserror>
				<span class="control-label">{{content.lang}}</span>
				<input type="hidden" data-ng-required="forms[$index].$invalid" data-ng-model="neverExists" />
			</li>
			<li class="list-group-item" data-ng-if="!cms.contents.length">Aucune langue configurée</li>
		</ul>
	</div>
	<div class="col-md-9 right" data-ng-repeat="content in cms.contents" data-ng-show="content.selected" data-ng-form="cmsForm">
		<div class="form-group voffset2" nb-haserror data-ng-init="pushForm(cmsForm,$index)">
			<label class="control-label">{{label.name}}</label>
			<input type="text" class="form-control" data-ng-required="required.name" data-ng-model="content.name" />
		</div>
		<div class="form-group" data-ng-if="show.desc" nb-haserror>
			<label class="control-label">{{label.desc}}</label>
			<textarea class="form-control" data-ng-required="required.desc" ckedit cktext="content.descriptionText" data-ng-model="content.description"></textarea>
		</div>
		<div class="form-group" data-ng-if="show.keywords" nb-haserror>
			<label class="control-label">{{label.keywords}}</label>
			<input type="hidden" data-ng-required="required.keywords && !content.keywords.length" data-ng-model="content.neverExists" />
			<tags-input data-ng-required="required.keywords" data-ng-model="content.keywords" placeholder="<? echo i18("add");?>"></tags-input>
		</div>
	</div>
</div>
</script>
