	<?php 

$json = file_get_contents('http://www.panoramio.com/data/get_front_page_photos?topic=1&subtopic='.rand(1, 843).'&offset='.rand(0, 300).'&hl=en');
//$obj = json_decode($json);
echo $json;

?>