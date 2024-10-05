<form role="form" name="productForm" data-file-upload="options">
	<div class="row">
		<h3 class="col-sm-12">
					<?php echo i18("gift.create"); ?>
				</h3>
		<div class="col-sm-7">

			<nbtabi18 list="product.langs" factory="onAddLang">
			<div class="form-group" nbvalidclass="name">
				<label class="control-label"><?php echo i18("product.menu.form.name"); ?></label>
				<div>
					<input class="form-control" type="text" ng-model="current.name" name="name"
						ng-required="true" ng-minlength="5" />
				</div>
				<nbformpopover field="name" i18prefix="product.form.name"></nbformpopover>
			</div>
			<div class="form-group" nbvalidclass="descT">
				<label class="control-label"><?php echo i18("product.menu.form.desc"); ?></label> <span
					class="help-block"><?php echo i18("product.i18n.description.default"); ?></span>

				<textarea ckedit ng-model="current.description" cktext="current.descriptionText"
					class=""></textarea>
				<input type="hidden" ng-model="current.descriptionText" name="descT"
					ng-required="true" />
			</div>
			<div class="form-group">
				<label class="control-label"><?php echo i18("product.i18n.keyword.label"); ?></label>
				<tags-input ng-model="current.keywords" enable-editing-last-tag="true"
					placeholder="<?php echo i18('product.form.keyword.placeholder') ?>"> </tags-input>
			</div>
			</nbtabi18>
		</div>
		<div class="col-sm-5 nb-product">
			<nbimgform main-img="product.img" imgs="product.imgs" options="options"></nbimgform>
		</div>

	</div>
	<div class="row">
		<div class="col-sm-12">
			<div class="btn-group btn-group-justified">
				<a href="javascript:;" class="btn btn-default btn-warning " ng-click="cancelProduct()"> 
							<?php echo i18("product.form.cancel"); ?>
						</a> <a href="javascript:;" class="btn btn-default btn-success"
					ng-click="submitProduct()" ng-disabled="productForm.$invalid"> <?php echo i18("product.form.submit"); ?>
						</a>
			</div>
		</div>
	</div>
</form>