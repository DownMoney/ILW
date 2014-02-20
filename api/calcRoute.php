<?php 
	$json = json_decode(file_get_contents('http://partners.api.skyscanner.net/apiservices/browseroutes/v1.0/GB/GBP/en-GB/LON/anywhere/anytime/anytime?apiKey=ilw03824094427015676662223000993'));
	
	$minPrice = 99999999;
	echo $json;
	$destID = 0;
	/*foreach ($json->Routes as $route){
		if ($minPrice >= $route->Price){
			$minPrice = $route->Price;
			echo "$route->Price\n";
			$destID = $route->DestinationId;
		}
		
	};
	echo "$minPrice";
	echo "$destID";*/
?>
