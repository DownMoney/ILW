<?php 
	$fileContents = file_get_contents('http://api.evdb.com/rest/categories/list?app_key=vd93CbRJDN8TgsPG');
	$fileContents = str_replace(array("\n", "\r", "\t"), '', $fileContents);
$fileContents = trim(str_replace('"', "'", $fileContents));
$xml = simplexml_load_string($fileContents);
$json = json_encode($xml);

echo $json;
?>