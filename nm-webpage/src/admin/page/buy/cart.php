<style>
#cart-vertical .list-group-item {
	font-size: 0.85em;
	padding: 5px 15px;
}

#cart-vertical .list-group-item ul {
	list-style: none;
}

#cart-vertical .list-group-item ul li {
	line-height: 8px;
}

#cart-vertical .list-group-item ul i {
	font-size: 8px;
}

#cart-vertical .list-group-item .level1 {
	padding-left: 25px;
}

#cart-vertical .list-group-item .level2 {
	padding-left: 35px;
}

#cart-vertical .list-group-item .level3 {
	padding-left: 45px;
}

#cart-vertical .list-group-item .level4 {
	padding-left: 55px;
}

#cart-vertical .list-group-item .level5 {
	padding-left: 65px;
}

#cart-vertical .list-group-item {
	position: relative;
}

#cart-vertical .list-group-item .bar {
	height: 35px;
	width: 69%;
	left: 14%;
	top: 0;
	position: absolute;
	border-right: 1px dashed grey;
	border-left: 1px dashed grey;
}

#cart-vertical .list-group-item button {
	border: 0;
	border-radius: 0;
}

#cart-vertical .list-group-item.btn-group {
	padding: 0;
}

#cart-vertical .panel-body {
	padding-bottom: 0;
	padding-top: 0
}
</style>
<section class="panel" id="cart-vertical">
	<header class="panel-heading">
		<label class="col-md-11"><?php echo i18("buy.cart.title"); ?></label>
	</header>
	<div class="panel-body">
		<form role="form" name="cartForm">
			<div class="row">
				<ul class="col-md-12 list-group blank0">
					<li class="list-group-item row">
						<label class="col-md-1 blankr0">
							<i class="fa fa-home"></i>
						</label>
						<label class=" col-md-11 blankr0">Restaurant du parc</label>
					</li>
					<li class="list-group-item row">
						<label class="col-md-1 blankr0">
							<i class="fa fa-home"></i>
						</label>
						<label class=" col-md-11 blankr0">A emporter</label>
					</li>
					<li class="list-group-item row">
						<div class="bar"></div>
						<label class="col-md-1">2X</label>
						<label class="col-md-8">Menu étudiants</label>
						<ul class="col-md-1 blank0">
							<li>
								<i class="fa fa-plus"></i>
							</li>
							<li>
								<i class="fa fa-minus"></i>
							</li>
						</ul>
						<label class=" col-md-1">14E</label>
					</li>
					<li class="list-group-item row">
						<div class="bar"></div>
						<label class="col-md-1"></label>
						<label class="col-md-8">Menu étudiants n°1</label>
						<ul class="col-md-1"></ul>
						<label class=" col-md-1">14E</label>
					</li>
					<li class="list-group-item row ">
						<div class="bar"></div>
						<label class="col-md-1"></label>
						<label class="col-md-8 level1">Tacos</label>
						<ul class="col-md-1"></ul>
						<label class="col-md-1"></label>
					</li>
					<li class="list-group-item row ">
						<div class="bar"></div>
						<label class="col-md-1"></label>
						<label class="col-md-8 level2">Sans oignon</label>
						<ul class="col-md-1"></ul>
						<label class="col-md-1"></label>
					</li>
					<li class="list-group-item row ">
						<div class="bar"></div>
						<label class="col-md-1"></label>
						<label class="col-md-8 level2">Sans tomate</label>
						<ul class="col-md-1"></ul>
						<label class="col-md-1"></label>
					</li>
					<li class="list-group-item row ">
						<div class="bar"></div>
						<label class="col-md-1"></label>
						<label class="col-md-8 level3">Sauce blanche</label>
						<ul class="col-md-1"></ul>
						<label class=" col-md-1">1E</label>
					</li>
					<li class="list-group-item row ">
						<div class="bar"></div>
						<label class="col-md-1"></label>
						<label class="col-md-8 level1">Frites</label>
						<ul class="col-md-1"></ul>
						<label class="col-md-1"></label>
					</li>
					<li class="list-group-item row ">
						<div class="bar"></div>
						<label class="col-md-1"></label>
						<label class="col-md-8 level2">Sauce blanche</label>
						<ul class="col-md-1"></ul>
						<label class="col-md-1"></label>
					</li>
					<li class="list-group-item row ">
						<div class="bar"></div>
						<label class="col-md-1"></label>
						<label class="col-md-8 level1">Chocolat fondat</label>
						<ul class="col-md-1"></ul>
						<label class=" col-md-1">1E</label>
					</li>
					<li class="list-group-item row ">
						<div class="bar"></div>
						<label class="col-md-1"></label>
						<label class="col-md-8 level2">Sans pépites</label>
						<ul class="col-md-1"></ul>
						<label class="col-md-1"></label>
					</li>
					<li class="list-group-item row bg-warning">
						<div class="bar"></div>
						<label class="col-md-1"></label>
						<label class="col-md-8">Remise -10%</label>
						<ul class="col-md-1"></ul>
						<label class=" col-md-1">-1E</label>
					</li>
					<li class="list-group-item row bg-warning">
						<div class="bar"></div>
						<label class="col-md-1"></label>
						<label class="col-md-8">TOTAL</label>
						<ul class="col-md-1"></ul>
						<label class=" col-md-1 text-bold">12E</label>
					</li>
					<li class="list-group-item row btn-group">
						<button type="button" class="btn btn-default col-md-12"> <?php echo i18("cash.form.draft"); ?></button>
					</li>
					<li class="list-group-item row btn-group">
						<button type="button" class="btn btn-default col-md-6"><?php echo i18("cash.form.cancel"); ?></button>
						<button type="button" class="btn btn-success col-md-6"><?php echo i18("cash.form.submit"); ?></button>
					</li>
				</ul>
			</div>
		</form>
	</div>
</section>