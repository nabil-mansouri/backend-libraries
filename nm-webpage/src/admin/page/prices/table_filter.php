<div data-ng-controller="PriceFilter" nbspinner boolean="filter.$resolved">
	<div class="col-md-2 col-md-offset-5 text-small pricefilter_action" data-ng-if="dohide">
		<h6 class="text-center" data-ng-click="show()"><?php echo i18("filter.prices")?>
			<i class="fa fa-angle-double-down pull-right" ></i>
		</h6>
	</div>
	<div class="col-md-12 text-small pricefilter" data-ng-if="!dohide">
		<div>
			<h5 class="text-center"><?php echo i18("filter.prices")?>
			<i class="fa fa-times pull-right text-danger" data-ng-click="hide()"></i>
			</h5>
			<div class="col-md-4 blank0">
				<div class="col-md-6 blankl0">
					<p class="control-label col-md-12 blank0 small "> <?php echo i18("search.from.label"); ?> 
					</p>
					<div class="input-group input-group-sm" nbdatetimeall data-min-date="temp.minto" data-ng-model="filter.from">
						<input type="text" class="form-control" data-ng-model="filter.from">
						<span class="input-group-btn">
							<button type="button" class="btn btn-primary">
								<i class="fa fa-calendar"></i>
							</button>
						</span>
					</div>
				</div>
				<div class="col-md-6 blankr0">
					<p class="control-label col-md-12 blank0 small "> <?php echo i18("search.to.label"); ?> 
					</p>
					<div class="input-group input-group-sm " nbdatetimeall data-min-date="filter.from" data-ng-model="filter.to">
						<input type="text" class="form-control" data-ng-model="filter.to">
						<span class="input-group-btn">
							<button type="button" class="btn btn-primary">
								<i class="fa fa-calendar"></i>
							</button>
						</span>
					</div>
				</div>
				<div class="col-md-12 checkbox">
					<p class="control-label col-md-12 small ">
						<input type="checkbox" data-ng-model="filter.onlyCurrent" />
					<?php echo i18("search.current.label"); ?> 
				</p>
				</div>
			</div>
			<div class="col-md-2 blank0">
				<div class="col-md-12 ">
					<p class="control-label col-md-12 blank0 small "> <?php echo i18("search.order.label"); ?> 
					</p>
					<div class="col-md-12 blank0 ">
						<div class="input-group input-group-sm">
							<span class="input-group-addon">
								<input type="checkbox" aria-label="..." data-ng-model="filter.anyOrder">
							</span>
							<div class="form-control rows_3" data-ng-disabled="filter.anyOrder">
								<div data-ng-if="filter.anyOrder"><?php echo i18("all")?></div>
								<div data-ng-if="!filter.anyOrder">
									<select class="form-control input-sm" data-ng-model="filter.orderTypes" data-ng-options="r as r|ordertype for r in filter.allTypes"
										multiple="multiple">
									</select>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-2 blank0">
				<div class="col-md-12 ">
					<p class="control-label col-md-12 blank0 small "> <?php echo i18("search.restos.label"); ?> 
					</p>
					<div class="col-md-12 blank0 ">
						<div class="input-group input-group-sm">
							<span class="input-group-addon">
								<input type="checkbox" aria-label="..." data-ng-model="filter.anyRestaurant">
							</span>
							<div class="form-control rows_3" data-ng-disabled="filter.anyRestaurant">
								<div data-ng-if="filter.anyRestaurant"><?php echo i18("all")?></div>
								<div data-ng-if="!filter.anyRestaurant">
									<select class="form-control input-sm" data-ng-model="filter.restaurants" data-ng-options="r.id as r|cmsname for r in filter.allRestaurants"
										multiple="multiple">
									</select>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-2 blank0">
				<div class="col-md-12 ">
					<p class="control-label col-md-12 blank0 small "> <?php echo i18("search.products.label"); ?> 
					</p>
					<div class="col-md-12 blank0 ">
						<div class="input-group input-group-sm">
							<span class="input-group-addon">
								<input type="checkbox" aria-label="..." data-ng-model="filter.anyProduct">
							</span>
							<div class="form-control rows_3" data-ng-disabled="filter.anyProduct">
								<div data-ng-if="filter.anyProduct"><?php echo i18("all")?></div>
								<div data-ng-if="!filter.anyProduct">
									<select class="form-control input-sm" data-ng-model="filter.products" data-ng-options="r.id as r|cmsname for r in filter.allProducts"
										multiple="multiple">
									</select>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-2 blank0">
				<button type="button" class="btn btn-primary col-md-12 voffset2" nbspinnerclick="submit()"><?php echo i18("filter")?></button>
				<button type="button" class="btn btn-info col-md-12 voffset2" nbspinnerclick="cancel()"><?php echo i18("clear")?></button>
			</div>
		</div>
	</div>
</div>
