<?php
define ( "DIR_VIEW", dirname ( __FILE__ ) );
define ( "MAIN_VIEW", "main.php" );
define ( "NAME", "orders" );
define ( "PLUGINS", serialize ( array (
		'icheck',
		"switch",
		"datetime",
		"mask",
		"tree",
		"feedback",
		"google-map",
		"flot" 
) ) );
define ( "MODULES", serialize ( array (
		"orders" ,
		"statistics"
) ) );
define ( "COMPONENTS", serialize ( array (
		"nbtable" 
) ) );
include_once '../index.php';
?>
