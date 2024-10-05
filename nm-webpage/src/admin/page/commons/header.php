<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title></title>
<!-- Fonts -->
<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700,800,400italic%7cMontserrat:400,700" rel="stylesheet" type="text/css">
<!-- Icons -->
<link href="<?php echo url('css/lib/saltkin/typicons.min.css')?>" rel="stylesheet">
<link href="<?php echo url('css/lib/saltkin/font-awesome.min.css')?>" rel="stylesheet">
<link href="<?php echo url('css/lib/saltkin/font-awesome-animated.min.css')?>" rel="stylesheet">
<!-- Bootstrap -->
<link href="<?php echo url('css/lib/bootstrap/css/bootstrap.min.css')?>" rel="stylesheet">
<!-- CUSTOM -->
<link href="<?php echo url('css/utils.css')?>" rel="stylesheet">
<link href="<?php echo url('css/overrides.css')?>" rel="stylesheet">
<link href="<?php echo url('css/custom_v2.css')?>" rel="stylesheet">
<script type="text/javascript" src="<?php echo url('js/lib/commons/strings.js')?>"></script>
<script type="text/javascript" src="<?php echo url('js/lib/commons/overrides.js')?>"></script>
<script type="text/javascript" src="<?php echo url('js/lib/jquery/jquery-latest.js')?>"></script>
<script type="text/javascript" src="<?php echo url('js/lib/angular/angular.js')?>"></script>
<script type="text/javascript" src="<?php echo url('js/lib/angular/angular-resource.js')?>"></script>
<script type="text/javascript" src="<?php echo url('js/lib/angular/angular-route.js')?>"></script>
<script type="text/javascript" src="<?php echo url('js/lib/others/underscore-min.js')?>"></script>
<script src="http://code.angularjs.org/1.0.8/i18n/angular-locale_fr-fr.js"></script>

<?php pluginHeaders()?>
<?php endHeaders()?>
<script type="text/javascript">
	var NbI18={
		get:function(key, defau){
			//console.log("[NbI18] Getting key:",key,defau);
				if(I18Manager[key]){
					return I18Manager[key];
				}else{
					return defau;
				}
			}
		};
	var Wick={
			LANG:"<?php echo $_SESSION['lang']?>",
			BASE_URL:"<?php echo prop('app.ws.url')?>",
			BASE_URL_PAGE:"<?php echo prop ( "app.url.base" )?>",
			CURRENT_LOCALE:{
				OBJECT:{
					langage:"<?php echo $_SESSION['lang']?>"
				}
			},
			IMG_DIR:"<?php echo imgDir(); ?>",
			URL_LOCALE: "/datas/locales.json",
			URL_COUNTRY: "/datas/countries/<?php echo $_SESSION['lang']?>/country.json",
			URL_CURRENCY: "/datas/currencies.json",
			DEPENDENCIES: []
	};
	</script>