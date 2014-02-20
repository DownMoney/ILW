<?php 
	$json = file_get_contents('https://maps.googleapis.com/maps/api/place/nearbysearch/json?location='.$_GET['location'].'&radius=45000&types=airport&sensor=false&key=AIzaSyCsHxnjIW3dFbEh7eJQrLF--Qv1mqy4n58');
	echo $json;
?>
