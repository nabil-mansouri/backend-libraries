<?php
define ( "DIR_VIEW", dirname ( __FILE__ ) );
define ( "MAIN_VIEW", "main.php" );
define ( "NAME", "discounts" );
define ( "PLUGINS", serialize ( array (
		'ckeditor',
		'icheck',
		"switch",
		"tagsinput",
		"fileupload",
		"datetime",
		"mask",
		"tree",
		"feedback"
) ) );
define ( "MODULES", serialize ( array (
		"products",
		"tarifs" 
) ) );
define ( "COMPONENTS", serialize ( array (
		"nbtable",
		"nbsubview",
		"nbradiobi",
		"nbi18tab" ,
		"nbtab"  ,
		"nbformpop"  ,
		"nbimgform"  ,
		"nbprice" 
) ) );
include_once '../index.php';
?>
