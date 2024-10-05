<?php
define ( "FUNC_DIR", dirname ( __FILE__ ) . DIRECTORY_SEPARATOR );
session_start ();
//
function startsWith($haystack, $needle) {
	return $needle === "" || strpos ( $haystack, $needle ) === 0;
}
function endsWith($haystack, $needle) {
	return $needle === "" || substr ( $haystack, - strlen ( $needle ) ) === $needle;
}
//
function parse_propertiesf($filename) {
	if (file_exists ( FUNC_DIR . $filename ))
		return parse_properties ( file_get_contents ( FUNC_DIR . $filename ) );
	else
		return array ();
}
function parse_properties($txtProperties) {
	$result = array ();
	$lines = explode ( "\n", $txtProperties );
	$key = "";
	$isWaitingOtherLine = false;
	foreach ( $lines as $i => $line ) {
		if (empty ( $line ) || (! $isWaitingOtherLine && strpos ( $line, "#" ) === 0))
			continue;
		
		if (! $isWaitingOtherLine) {
			$key = substr ( $line, 0, strpos ( $line, '=' ) );
			$value = substr ( $line, strpos ( $line, '=' ) + 1, strlen ( $line ) );
		} else {
			$value .= $line;
		}
		/* Check if ends with single '\' */
		$value = trim ( $value );
		if (endsWith ( $value, "\\" )) {
			$value = substr ( $value, 0, strlen ( $value ) - 1 ) . "\n";
			$isWaitingOtherLine = true;
		} else {
			$isWaitingOtherLine = false;
		}
		
		$result [$key] = $value;
		unset ( $lines [$i] );
	}
	return $result;
}

$app_conf = parse_propertiesf ( ".." . DIRECTORY_SEPARATOR . "conf" . DIRECTORY_SEPARATOR . "app.properties" );
function prop($key) {
	global $app_conf;
	if (isset ( $app_conf [$key] ))
		return $app_conf [$key];
	else
		return "";
}
function url($path) {
	return prop ( "app.url.base" ) . trim ( $path );
}
function imgDir() {
	return prop ( "app.url.base" ) . "img/";
}

$plugins = [ 
		"header" => [ 
				"css" => [ ],
				"js" => [ ] 
		],
		"footer" => [ 
				"js" => [ ] 
		] 
];
$plugins_prop = parse_propertiesf ( ".." . DIRECTORY_SEPARATOR . "conf" . DIRECTORY_SEPARATOR . "plugins.properties" );
function plugin($keys) {
	global $plugins_prop;
	global $plugins;
	foreach ( $keys as $key ) {
		$keyCss = $key . ".css";
		if (isset ( $plugins_prop [$keyCss] )) {
			$plugins ["header"] ["css"] = array_merge ( $plugins ["header"] ["css"], explode ( ";", $plugins_prop [$keyCss] ) );
		}
		$keyJsh = $key . ".jsh";
		if (isset ( $plugins_prop [$keyJsh] )) {
			$plugins ["header"] ["js"] = array_merge ( $plugins ["header"] ["js"], explode ( ";", $plugins_prop [$keyJsh] ) );
		}
		$keyJs = $key . ".js";
		if (isset ( $plugins_prop [$keyJs] )) {
			$plugins ["footer"] ["js"] = array_merge ( $plugins ["footer"] ["js"], explode ( ";", $plugins_prop [$keyJs] ) );
		}
	}
}
function endHeaders() {
	global $langsJS;
	$js = json_encode ( $langsJS );
	echo "<script type='text/javascript'>var I18Manager=$js</script>";
}
function pluginHeaders() {
	global $plugins;
	foreach ( $plugins ["header"] ["css"] as $ind => $css ) {
		if (! startsWith ( $css, "http:" )) {
			$css = url ( $css );
		}
		echo "<link href='$css' rel='stylesheet'/>";
	}
	foreach ( $plugins ["header"] ["js"] as $ind => $js ) {
		if (! startsWith ( $js, "http:" )) {
			$js = url ( $js );
		}
		echo "<script type='text/javascript' src='$js'></script>";
	}
}
function pluginFooters() {
	global $plugins;
	foreach ( $plugins ["footer"] ["js"] as $ind => $js ) {
		if (! startsWith ( $js, "http:" )) {
			$js = url ( $js );
		}
		echo "<script type='text/javascript' src='$js'></script>";
	}
}

// LANGS
if (isset ( $_GET ["lang"] )) {
	$lang = strtolower ( $_GET ["lang"] );
	if (isset ( $files [$lang] )) {
		$_SESSION ["lang"] = $lang;
	}
}
if (! isset ( $_SESSION ["lang"] )) {
	$_SESSION ["lang"] = "fr";
}
function loadLang($locale, $name) {
	$path = ".." . DIRECTORY_SEPARATOR . "lang" . DIRECTORY_SEPARATOR . $locale . DIRECTORY_SEPARATOR . $name . ".properties";
	return parse_propertiesf ( $path );
}
function loadLangJS($locale, $name) {
	$path = ".." . DIRECTORY_SEPARATOR . "lang" . DIRECTORY_SEPARATOR . $locale . DIRECTORY_SEPARATOR . $name . ".js.properties";
	return parse_propertiesf ( $path );
}
function loadLangModules($names) {
	global $langs, $langsJS;
	foreach ( $names as $name ) {
		$langs = array_merge ( $langs, loadLang ( prop ( "app.lang.default" ), $name ) );
		$langs = array_merge ( $langs, loadLang ( $_SESSION ["lang"], $name ) );
		//
		$langsJS = array_merge ( $langsJS, loadLangJS ( prop ( "app.lang.default" ), $name ) );
		$langsJS = array_merge ( $langsJS, loadLangJS ( $_SESSION ["lang"], $name ) );
	}
}

// LOAD ALL
$langs = loadLang ( prop ( "app.lang.default" ), "commons" );
$langs = array_merge ( $langs, loadLang ( prop ( "app.lang.default" ), NAME ) );
$langs = array_merge ( $langs, loadLang ( $_SESSION ["lang"], "commons" ) );
$langs = array_merge ( $langs, loadLang ( $_SESSION ["lang"], NAME ) );
//
$langsJS = loadLangJS ( prop ( "app.lang.default" ), "commons" );
$langsJS = array_merge ( $langsJS, loadLangJS ( prop ( "app.lang.default" ), NAME ) );
$langsJS = array_merge ( $langsJS, loadLangJS ( $_SESSION ["lang"], "commons" ) );
$langsJS = array_merge ( $langsJS, loadLangJS ( $_SESSION ["lang"], NAME ) );
//
function i18e($key, $slash = false) {
	echo i18 ( $key, $slash );
}
function i18($key, $slash = false) {
	global $langs;
	$i18 = "";
	if (isset ( $langs [$key] )) {
		$i18 = ($langs [$key]);
	}
	if ($slash == true) {
		$i18 = addslashes ( $i18 );
	}
	return $i18;
}
// Model
$model = array ();
function model($key, $value = null) {
	global $model;
	if ($value != null) {
		$model [$key] = $value;
	}
	return $model [$key];
}
function clearModel() {
	global $model;
	$model = array ();
}
// Modules
function loadComponents() {
	if (defined ( "COMPONENTS" )) {
		$components = unserialize ( COMPONENTS );
		foreach ( $components as $prop => $value ) {
			if (@include (FUNC_DIR . "modules" . DIRECTORY_SEPARATOR . $value . ".php")) {
				include (FUNC_DIR . "modules" . DIRECTORY_SEPARATOR . $value . ".php");
			}
			echo '<script type="text/javascript">';
			include (FUNC_DIR . ".." . DIRECTORY_SEPARATOR . "js" . DIRECTORY_SEPARATOR . "modules" . DIRECTORY_SEPARATOR . $value . ".js");
			echo '</script>';
		}
	}
}
function loadCommons() {
	$directory = FUNC_DIR . ".." . DIRECTORY_SEPARATOR . "js" . DIRECTORY_SEPARATOR . "commons" . DIRECTORY_SEPARATOR . "services" . DIRECTORY_SEPARATOR;
	foreach ( scandir ( $directory ) as $file ) {
		if ('.' === $file)
			continue;
		if ('..' === $file)
			continue;
		echo '<script type="text/javascript" src="/admin/js/commons/services/' . $file . '">';
		// include ($directory . $file);
		echo '</script>';
	}
}
function menuActive($name) {
	if ($name == NAME) {
		echo "active";
	}
}
?>