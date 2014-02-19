<!DOCTYPE html>
<html lang="en">
  <head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="shortcut icon" href="/ico/favicon.ico">

	<title>Red Cookie District</title>

	<!-- Bootstrap core CSS -->
	<link href="/css/bootstrap.min.css" rel="stylesheet">

	<!-- Just for debugging purposes. Don't actually copy this line! -->
	<!--[if lt IE 9]><script src="/js/ie8-responsive-file-warning.js"></script><![endif]-->

	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	  <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->

	<!-- Custom styles for this template -->
	<link href="carousel.css" rel="stylesheet">
	<link href="/css/custom.css" rel="stylesheet">
	<link href="/css/select.css" rel="stylesheet">
	<link href="/css/main.css" rel="stylesheet">
  </head>
<!-- NAVBAR
================================================== -->
  <body>
	<div class="navbar-wrapper">
	  <div class="container">
		<div class="navbar nav navbar-inverse navbar-fixed-top" role="navigation">
		  <div class="container">
			<div class="navbar-header">
			  <a class="navbar-brand" href="/">Cookie Light District</a>
			</div>
		  </div>
		</div>
	  </div>
	</div>

   
	 <div id="googlemapcanvas"></div>
	<div id="panel" style="width: 300px; float: right;"></div> 
	<div id="schematictravel"></div> 


	<div class="timeline-container" style="width:800;margin-top:50px;">
		<div class="time-line-time">
			<div id="timeline-line" style="width:800;"></div>
			<span id="legend">
				<div class="time-line-circle" style="width:25%">
					<a class="time-line-href href1" href="#" onclick="change1()">
					<div class="circleLine" style="opacity: 0;"></div>
					<span>2001</span></a>
				</div>
			</span>
			<div id="bubbles" style="width:800; height:400;margin-top:50px;">
				<div class="timeline-circles timeline-circle1change2" id="timeline-circid1">
					<span id="textcircid1" onclick="ptext();" class="timeline-textformat textincircle1and2">University</span></div>
				<div class="timeline-circles timeline-circle2change2" id="timeline-circid2">
					<span id="textcircid2" onclick="ptext();" class="timeline-textformat textincircle2and2">Boston</span></div>
				<div class="timeline-circles timeline-circle3change2" id="timeline-circid3">
					<span id="textcircid3" onclick="ptext();" class="timeline-textformat textincircle3and2"><img src="key.png"></span></div>
				<div class="timeline-circles timeline-circle4change2" id="timeline-circid4">
					<span id="textcircid4" onclick="ptext();" class="timeline-textformat textincircle4and2">Flash</span></div>
				<div class="timeline-circles timeline-circle5change2" id="timeline-circid5">
					<span id="textcircid5" onclick="ptext();" class="timeline-textformat textincircle5and2">CSS</span></div>
				<div class="timeline-circles timeline-circle6change2" id="timeline-circid6">
					<span id="textcircid6" onclick="ptext();" class="timeline-textformat textincircle6and2">Jazz</span></div>
			</div>



		</div>
	<div id="timeline-outputtext" style=" top: 150px; left: -275px;"></div>
	<!-- Bootstrap core JavaScript
	================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script src="/js/scripts.js"></script>
	<script src="/js/select.js"></script>
	<script type="text/javascript"
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCsHxnjIW3dFbEh7eJQrLF--Qv1mqy4n58&sensor=true">
    </script>
    <script type="text/javascript" src="./js/scripts.js"></script>
        <script>

		/*var map;
		getCityLocation('edinburgh', function(coord, city){
			var mapOptions = {
    			zoom: 8,
    			center: new google.maps.LatLng(coord['lat'], coord['lng'])
  			};
  			map = new google.maps.Map(document.getElementById('googlemapcanvas'), mapOptions);
			});*/
			var directionsService = new google.maps.DirectionsService();
     var directionsDisplay = new google.maps.DirectionsRenderer();

     var map = new google.maps.Map(document.getElementById('googlemapcanvas'), {
       zoom:7,
       mapTypeId: google.maps.MapTypeId.ROADMAP
     });

     directionsDisplay.setMap(map);
     //directionsDisplay.setPanel(document.getElementById('panel'));

     var request = {
       origin: 'Chicago', 
       destination: 'New York',
       travelMode: google.maps.DirectionsTravelMode.DRIVING
     };

     directionsService.route(request, function(response, status) {
       if (status == google.maps.DirectionsStatus.OK) {
         directionsDisplay.setDirections(response);
       	}
     	});


    	</script>
  </body>
</html>
