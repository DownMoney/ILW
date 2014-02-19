<?php 
	$json = file_get_contents('https://maps.googleapis.com/maps/api/directions/json?origin='.$_GET['start'].'&destination='.$_GET['end'].'&sensor=false&mode='.$_GET['mode'].'&key=AIzaSyCsHxnjIW3dFbEh7eJQrLF--Qv1mqy4n58');
	echo $json;
?>