<!DOCTYPE html>
<html lang="en">
  <head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="shortcut icon" href="/ico/favicon.ico">

	<title>SKYPLANNER</title>

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
  <div id="progress">&nbsp;</div>
	<div class="navbar-wrapper">
	  <div class="container">
		<div class="navbar nav navbar-inverse navbar-fixed-top" role="navigation">
		  <div class="container">
			<div class="navbar-header">
			  <a class="navbar-brand" href="/">SKY<b>PLANNER</b></a>
			</div>
			 <div id="" style="width:100%; text-align:center;margin-left: -150px; margin-top:15px;">
				<h2 id="price"></h2>
			</div>
		  </div>
		</div>
	  </div>
	 
	</div>

   
	 <div id="googlemapcanvas"></div>

	 

	<div onclick="$(window).scrollTop($('#googlemapcanvas').height()-50);" style="padding-top: 50px;">
	<div class="timeline-container"  style="width:900;">
		<div class="time-line-time">
			<div id="timeline-line" style="width:900;"></div>
			<span id="legend">
				
			</span>


			<div id="bubbles" style="width:900; height:400;margin-top:50px;">
			</div>
			 


		</div>
		</div>
		</div>
		<div id="panelDir"></div>
			<div id="panel"></div>
	<div id="timeline-outputtext" style="top: 120px; left: 0px; display:none" ></div>

	
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
    <script type="text/javascript" src= "./js/googlemap.js"></script>

    <script>
     $("#progress").animate({width:"83%"},5000);
    var flightPlanCoordinates2 = [];
    var map2;
    function loadPoints(map){
    	map2 = map;
    p = /([a-z]+|[a-z]\s[a-z])+\s?\(([a-z]+)\)/ig;
	cityName = '<?php echo $_GET["from"] ?>';
	m = p.exec(cityName);
	code= m[2];
	city = m[1];
		console.log('/api/calcRoute.php?startCode='+code+'&startDate='+encodeURIComponent('<?php echo $_GET["ddate"]?>')+'&endDate='+encodeURIComponent('<?php echo $_GET["adate"]?>')+'&startCity='+city);
    	$.getJSON('/api/calcRoute.php?startCode='+code+'&startDate='+encodeURIComponent('<?php echo $_GET["ddate"]?>')+'&endDate='+encodeURIComponent('<?php echo $_GET["adate"]?>')+'&startCity='+city+'&maxamount=<?php echo $_GET["maxamount"] ?>', function(data){
    		$("#progress").animate({width:"100%"},1000, function(){
    			$("#progress").css('width', '0%');
    		});
    		console.log(data);
    		$('#price').html('£'+data['Price']);
    		toTimeline(data['Routes']);
    		$.each(data['Routes'], function(i){
    			p = /([a-z]+)/ig;
    			console.log(i);
				name = data['Routes'][i]['FromCity'];
			    name = p.exec(name)[1];
			    console.log(name);
    			getCityLocation(name, function(coord, city){
    				console.log(coord);
    				point = new google.maps.LatLng(coord['lat'], coord['lng']);
    				flightPlanCoordinates2.push(point);
    				temp = new google.maps.Marker({
			  			position : point,
			  			map : map,
			  			title : "Point " + i
			  		});
    			});
    		});

    		setTimeout(draw, 500);
    		console.log(flightPlanCoordinates)
    		

    	});
    }

    function draw(){
    	//point = new google.maps.LatLng(coord['lat'], coord['lng']);
    				flightPlanCoordinates2.push(flightPlanCoordinates2[0]);
    	connectPoints(flightPlanCoordinates2, map2);
    }
    </script>
  </body>
</html>
