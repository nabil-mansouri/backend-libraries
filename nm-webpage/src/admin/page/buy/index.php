<?php
define ( "DIR_VIEW", dirname ( __FILE__ ) );
define ( "MAIN_VIEW", "main.php" );
define ( "NAME", "buy" );
define ( "PLUGINS", serialize ( array (
		'ckeditor',
		"tagsinput",
		"fileupload",
		"tree",
		"flot" 
) ) );
define ( "MODULES", serialize ( array () ) );
define ( "COMPONENTS", serialize ( array (
		"preferences",
		"multimg",
		"multicms",
		"multiview",
		"loader",
		"readmore",
		"deleteConfirm" 
) ) );
include_once '../index.php';
?>
