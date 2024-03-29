<?php 

function getDirect($startCode, $endCode, $startDate, $endDate){
		$data = json_decode(file_get_contents('http://partners.api.skyscanner.net/apiservices/browsequotes/v1.0/GB/GBP/en-GB/'.$startCode.'/'.$endCode.'/'.$startDate.'/'.$endDate.'?apiKey=ilw03824094427015676662223000993'), true);
		//echo var_dump($data);

		$minPrice = 99999999;
	//echo json_encode($data->Routes);
	$destID = 0;
	$routes = $data['Quotes'];
	$iata = '';
	
	$airport = '';

	foreach ($routes as $route){
		//echo var_dump($route);
		//echo json_encode($route->Price);
		if (array_key_exists('MinPrice', $route) && $minPrice > ($route['MinPrice']) ){
			
				$minPrice = ($route['MinPrice']);
				//echo "$route->Price\n";
				$destID = ($route['OutboundLeg']['DestinationId']);
				
			
		}
		
	};

	foreach ($data['Places'] as $dest) {		
		if($dest['PlaceId'] == $destID)
		{
			$airport = strtoupper($dest['Name']);
			$iata = strtoupper($dest['IataCode']);
		}
	}

	//echo $iata;

	$returnJson = '{"code":"'.$iata.'", "airport":"'.$airport.'", "price":'.$minPrice.', "destID":'.$destID.' }';
	return $returnJson;
}

	function getCheapest($startCode, $startDate, $endDate, $visitedA)
	{
	$data = json_decode(file_get_contents('http://partners.api.skyscanner.net/apiservices/browseroutes/v1.0/GB/GBP/en-GB/'.$startCode.'/anywhere/'.$startDate.'/'.$endDate.'?apiKey=ilw03824094427015676662223000993'), true);
	
	$minPrice = 99999999;
	//echo json_encode($data->Routes);
	$destID = 0;
	$routes = $data['Routes'];

	foreach ($routes as $route){
		//echo var_dump($route);
		//echo json_encode($route->Price);
		if (!in_array($route['DestinationId'], $visitedA) && array_key_exists('Price', $route) ){
			if($minPrice > ($route['Price']))
			{
				$minPrice = ($route['Price']);
				//echo "$route->Price\n";
				$destID = ($route['DestinationId']);
			}
			
		}
		
	};

	$oldDestID = $destID;
	
	$country = "";
	foreach ($data['Places'] as $dest) {		
		if($dest['PlaceId'] == $destID)
			$country = strtoupper($dest['Name']);
	}

	$countryCodes = json_decode('{"ÅLAND ISLANDS":"AX","AFGHANISTAN":"AF","ALBANIA":"AL","ALGERIA":"DZ","AMERICAN SAMOA":"AS","ANDORRA":"AD","ANGOLA":"AO","ANGUILLA":"AI","ANTARCTICA":"AQ","ANTIGUA AND BARBUDA":"AG","ARGENTINA":"AR","ARMENIA":"AM","ARUBA":"AW","AUSTRALIA":"AU","AUSTRIA":"AT","AZERBAIJAN":"AZ","BAHAMAS":"BS","BAHRAIN":"BH","BANGLADESH":"BD","BARBADOS":"BB","BELARUS":"BY","BELGIUM":"BE","BELIZE":"BZ","BENIN":"BJ","BERMUDA":"BM","BHUTAN":"BT","BOLIVIA PLURINATIONAL STATE OF":"BO","BONAIRE SINT EUSTATIUS AND SABA":"BQ","BOSNIA AND HERZEGOWINA":"BA","BOTSWANA":"BW","BOUVET ISLAND":"BV","BRAZIL":"BR","BRITISH INDIAN OCEAN TERRITORY":"IO","BRUNEI DARUSSALAM":"BN","BULGARIA":"BG","BURKINA FASO":"BF","BURUNDI":"BI","CAMBODIA":"KH","CAMEROON":"CM","CANADA":"CA","CAPE VERDE":"CV","CAYMAN ISLANDS":"KY","CENTRAL AFRICAN REPUBLIC":"CF","CHAD":"TD","CHILE":"CL","CHINA":"CN","CHRISTMAS ISLAND":"CX","COCOS ISLANDS":"CC","COLOMBIA":"CO","COMOROS":"KM","CONGO":"CG","CONGO THE DEMOCRATIC REPUBLIC OF THE":"CD","COOK ISLANDS":"CK","COSTA RICA":"CR","COTE DIVOIRE":"CI","CROATIA":"HR","CUBA":"CU","CURAÇAO":"CW","CYPRUS":"CY","CZECH REPUBLIC":"CZ","DENMARK":"DK","DJIBOUTI":"DJ","DOMINICA":"DM","DOMINICAN REPUBLIC":"DO","ECUADOR":"EC","EGYPT":"EG","EL SALVADOR":"SV","EQUATORIAL GUINEA":"GQ","ERITREA":"ER","ESTONIA":"EE","ETHIOPIA":"ET","FALKLAND ISLANDS":"FK","FAROE ISLANDS":"FO","FIJI":"FJ","FINLAND":"FI","FRANCE":"FR","FRENCH GUIANA":"GF","FRENCH POLYNESIA":"PF","FRENCH SOUTHERN TERRITORIES":"TF","GABON":"GA","GAMBIA":"GM","GEORGIA":"GE","GERMANY":"DE","GHANA":"GH","GIBRALTAR":"GI","GREECE":"GR","GREENLAND":"GL","GRENADA":"GD","GUADELOUPE":"GP","GUAM":"GU","GUATEMALA":"GT","GUERNSEY":"GG","GUINEA":"GN","GUINEA-BISSAU":"GW","GUYANA":"GY","HAITI":"HT","HEARD AND MC DONALD ISLANDS":"HM","HONDURAS":"HN","HONG KONG":"HK","HUNGARY":"HU","ICELAND":"IS","INDIA":"IN","INDONESIA":"ID","IRAN":"IR","IRAQ":"IQ","IRELAND":"IE","ISLE OF MAN":"IM","ISRAEL":"IL","ITALY":"IT","JAMAICA":"JM","JAPAN":"JP","JERSEY":"JE","JORDAN":"JO","KAZAKHSTAN":"KZ","KENYA":"KE","KIRIBATI":"KI","KOREA DEMOCRATIC PEOPLES REPUBLIC OF":"KP","KOREA REPUBLIC OF":"KR","KUWAIT":"KW","KYRGYZSTAN":"KG","LAO PEOPLES DEMOCRATIC REPUBLIC":"LA","LATVIA":"LV","LEBANON":"LB","LESOTHO":"LS","LIBERIA":"LR","LIBYA":"LY","LIECHTENSTEIN":"LI","LITHUANIA":"LT","LUXEMBOURG":"LU","MACAO":"MO","MACEDONIA THE FORMER YUGOSLAV REPUBLIC OF":"MK","MADAGASCAR":"MG","MALAWI":"MW","MALAYSIA":"MY","MALDIVES":"MV","MALI":"ML","MALTA":"MT","MARSHALL ISLANDS":"MH","MARTINIQUE":"MQ","MAURITANIA":"MR","MAURITIUS":"MU","MAYOTTE":"YT","MEXICO":"MX","MICRONESIA FEDERATED STATES OF":"FM","MOLDOVA REPUBLIC OF":"MD","MONACO":"MC","MONGOLIA":"MN","MONTENEGRO":"ME","MONTSERRAT":"MS","MOROCCO":"MA","MOZAMBIQUE":"MZ","MYANMAR":"MM","NAMIBIA":"NA","NAURU":"NR","NEPAL":"NP","NETHERLANDS":"NL","NEW CALEDONIA":"NC","NEW ZEALAND":"NZ","NICARAGUA":"NI","NIGER":"NE","NIGERIA":"NG","NIUE":"NU","NORFOLK ISLAND":"NF","NORTHERN MARIANA ISLANDS":"MP","NORWAY":"NO","OMAN":"OM","PAKISTAN":"PK","PALAU":"PW","PALESTINE STATE OF":"PS","PANAMA":"PA","PAPUA NEW GUINEA":"PG","PARAGUAY":"PY","PERU":"PE","PHILIPPINES":"PH","PITCAIRN":"PN","POLAND":"PL","PORTUGAL":"PT","PUERTO RICO":"PR","QATAR":"QA","REUNION":"RE","ROMANIA":"RO","RUSSIAN FEDERATION":"RU","RWANDA":"RW","SAINT HELENA ASCENSION AND TRISTAN DA CUNHA":"SH","SAINT BARTHÉLEMY":"BL","SAINT KITTS AND NEVIS":"KN","SAINT LUCIA":"LC","SAINT PIERRE AND MIQUELON":"PM","SAINT VINCENT AND THE GRENADINES":"VC","SAMOA":"WS","SAN MARINO":"SM","SAO TOME AND PRINCIPE":"ST","SAUDI ARABIA":"SA","SENEGAL":"SN","SERBIA":"RS","SEYCHELLES":"SC","SIERRA LEONE":"SL","SINGAPORE":"SG","SINT MAARTEN":"SX","SLOVAKIA":"SK","SLOVENIA":"SI","SOLOMON ISLANDS":"SB","SOMALIA":"SO","SOUTH AFRICA":"ZA","SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS":"GS","SOUTH SUDAN":"SS","SPAIN":"ES","SRI LANKA":"LK","SUDAN":"SD","SURINAME":"SR","SVALBARD AND JAN MAYEN ISLANDS":"SJ","SWAZILAND":"SZ","SWEDEN":"SE","SWITZERLAND":"CH","SYRIAN ARAB REPUBLIC":"SY","TAIWAN PROVINCE OF CHINA":"TW","TAJIKISTAN":"TJ","TANZANIA UNITED REPUBLIC OF":"TZ","THAILAND":"TH","TIMOR-LESTE":"TL","TOGO":"TG","TOKELAU":"TK","TONGA":"TO","TRINIDAD AND TOBAGO":"TT","TUNISIA":"TN","TURKEY":"TR","TURKMENISTAN":"TM","TURKS AND CAICOS ISLANDS":"TC","TUVALU":"TV","UGANDA":"UG","UKRAINE":"UA","UNITED ARAB EMIRATES":"AE","UNITED KINGDOM":"GB","UNITED STATES":"US","UNITED STATES MINOR OUTLYING ISLANDS":"UM","URUGUAY":"UY","UZBEKISTAN":"UZ","VANUATU":"VU","VENEZUELA BOLIVARIAN REPUBLIC OF":"VE","VIET NAM":"VN","VIRGIN ISLANDS":"VG","VIRGIN ISLANDS":"VI","WALLIS AND FUTUNA ISLANDS":"WF","WESTERN SAHARA":"EH","YEMEN":"YE","ZAMBIA":"ZM","ZIMBABWE":"ZW"}', true);
    
    $countryCode = $countryCodes[$country];
    
    $json = json_decode(file_get_contents('http://partners.api.skyscanner.net/apiservices/browseroutes/v1.0/GB/GBP/en-GB/'.$startCode.'/'.$countryCode.'/'.$startDate.'/'.$endDate.'?apiKey=ilw03824094427015676662223000993'), true);

   // echo var_dump($json);
    $iata = "";
    $airport = "";
    foreach ($json['Routes'] as $route) {
    	if (array_key_exists('Price', $route) && $minPrice >= ($route['Price'])){
    		$destID = ($route['DestinationId']);
    		$minPrice = $route['Price'];
    	}
    }

    foreach ($json['Places'] as $dest) {		
		if($dest['PlaceId'] == $destID){

			$airport = strtoupper($dest['Name']);
			$iata = strtoupper($dest['IataCode']);
			break;
		}
	}

	$returnJson = '{"code":"'.$iata.'", "airport":"'.$airport.'", "countryCode":"'.$countryCode.'", "country":"'.$country.'", "price":'.$minPrice.', "destID":'.$oldDestID.' }';
	//echo $returnJson;
	return $returnJson;
}

$startCode = 'LHR';
$code = $startCode;
$endCode = '';
$city = 'London';
$cost = 0;
$sDate = ('2014-03-03');
$eDate = ('2014-03-05');
$routes = '{"Routes":[';
$visited = array();
$stops = 5;


for ($i=0; $i < $stops; $i++) { 
	$res = getCheapest($code, $sDate, $eDate, $visited);
	$json = json_decode($res, true);
	if($json['price']< 99999999)
	{

	$routes= $routes.'{"From":"'.$code.'", "To": "'.$json['code'].'", "Price":'.$json['price'].', "FromCity":"'.$city.'", "ToCity":"'.$json['airport'].'", "FromDate":"'.$sDate.'", "ToDate":"'.$eDate.'"},';
	
	$cost += $json['price'];
	$city = $json['airport'];
	$code = $json['code'];
	//$i--;
	$sDate = date('Y-m-d', strtotime($sDate. ' + 2 days'));
	$eDate = date('Y-m-d', strtotime($eDate. ' + 2 days'));
	}
	array_push($visited, $json['destID']);

	if($code == $endCode)
		break;

	if ($i == ($stops-1))
	{
		$res = getDirect($code, $startCode, $sDate, $eDate);
		$json = json_decode($res, true);
		$routes= $routes.'{"From":"'.$code.'", "To": "'.$json['code'].'", "Price":'.$json['price'].', "FromCity":"'.$city.'", "ToCity":"'.$json['airport'].'"},';
		$cost += $json['price'];
	}
}
$routes = rtrim($routes, ",");
$routes = $routes.'], "Price":'.$cost.'}';


echo $routes;
?>
