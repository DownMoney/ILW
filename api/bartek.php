<?php
	$fromcity = $_GET['from'];
	if($_GET['to'] != ""){
		$tocity = $_GET['to'];
	} else {
		$tocity = "NULL";
	}
	$startdate = date('Y-m-d', strtotime($_GET['ddate']) . "00:00:00";
	$enddate = date('Y-m-d', strtotime($_GET['adate']) . "23:59:59";

		$viacities = '';
		$i=0;
		while (isset($_GET['via'.$i])) {
			$viacities = $viacities . $_GET['via'.$i] . ',';
		}
		$viacities = rtrim($viacities,',');
		$category = $_GET['eventType'];
		$keywords = $_GET['keywords'];
		$maxmoney = $_GET['maxamount'];

	$command = $fromcity . ";" . $tocity . ";" . $startdate . ";" . $enddate . ";" . $viacities . ";" . $category . ";" . $keywords . ";" . $maxmoney . ";";
	$var = shell_exec('java main ' . $command);
	echo $var;
?>