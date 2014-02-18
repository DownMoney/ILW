<?php 
	$json = file_get_contents("http://www.panoramio.com/map/get_panoramas.php?set=public&from=0&to=20&minx=".$_GET['minx']."&miny=".$_GET['miny']."&maxx=".$_GET['maxx']."&maxy=".$_GET['maxy']."&size=original&mapfilter=true");
	echo $json;
?>