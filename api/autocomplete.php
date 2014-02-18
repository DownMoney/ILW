<?php 
	$json = file_get_contents('http://www.skyscanner.net/dataservices/geo/v1.0/autosuggest/uk/EN/'.$_GET['city'].'?isDestination=false&ccy=GBP');
	echo $json;
?>