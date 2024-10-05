<?php
define ( "DIR_VIEW", dirname ( __FILE__ ) );
define ( "MAIN_VIEW", "main.php" );
define ( "NAME", "cash" );
define ( "PLUGINS", serialize ( array (
		'icheck',
		"switch",
		"datetime",
		"mask",
		"tree",
		"subview",
		"feedback" ,
		"google-autocomplete" 
) ) );
define ( "MODULES", serialize ( array (
		"cash" 
) ) );
define ( "COMPONENTS", serialize ( array (
		"nbtable" 
) ) );
include_once '../index.php';
?>
