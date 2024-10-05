<?php
define ( "DIR_VIEW", dirname ( __FILE__ ) );
define ( "MAIN_VIEW", "main.php" );
define ( "NAME", "restaurants" );
define ( "PLUGINS", serialize ( [ 
		'ckeditor',
		"tagsinput",
		"fileupload",
		"fullcalendar",
		"google-autocomplete",
		"mask",
		"strap",
		"datetime" 
] ) );
define ( "COMPONENTS", serialize ( array (
		"preferences",
		"multimg",
		"multicms",
		"multiview",
		"loader",
		"readmore",
		"deleteConfirm",
		"geoform" 
) ) );
include_once '../index.php';
?>
