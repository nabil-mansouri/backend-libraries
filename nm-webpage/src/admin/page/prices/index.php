<?php 
define("DIR_VIEW", dirname(__FILE__));
define("MAIN_VIEW", "main.php"); 
define("NAME", "tarifs"); 
define ( "PLUGINS", serialize ( array (
		'ckeditor',
		"tagsinput",
		"fileupload",
		"fullcalendar",
		"google-autocomplete",
		"mask",
		"strap",
		"datetime" 
) ) );
define ( "MODULES", serialize ( array (
		"products",
		"statistics" 
) ) );
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
