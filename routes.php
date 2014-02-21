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
	<div id="panel"></div>
	<div id="panelDir"></div> 

	<div onclick="$(window).scrollTop($('#googlemapcanvas').height()-50);" style="padding-top: 50px;">
	<div class="timeline-container"  style="width:900;">
		<div class="time-line-time">
			<div id="timeline-line" style="width:900;"></div>
			<span id="legend">
				<div class="time-line-circle" style="width:25%">
					<a class="time-line-href href1" href="#" onclick="change1()">
					<div class="circleLine" style="opacity: 0;"></div>
					<span>2001</span></a>
				</div>
			</span>
			<div id="bubbles" style="width:900; height:400;margin-top:50px;">
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
		</div>
		</div>
	<div id="timeline-outputtext" style=" top: 150px; left: -275px;" ></div>

	
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
	

    <script type="text/javascript" src="/js/scripts.js"></script>
    <script type="text/javascript" src= "/js/googlemap.js"></script>

    <script>

    var flightPlanCoordinates2 = [];
    var map2;

  
	
	
 var p = /([a-z]+|[a-z]\s[a-z])+\s?\(([a-z]+)\)/ig;
    var cities = [];
    function loadPoints(map){
    	map2 = map;
    
		var cityName = '<?php echo $_GET["from"] ?>';
		m = p.exec(cityName);
	//code= m[2];
	if (m != null && m.length>0)
		cityName = m[1];

	cities.push({"FromCity": cityName});
	
	console.log(cityName);
		q = ('/Backend/bartek.php?from='+cityName+'&ddate='+encodeURIComponent('<?php echo $_GET["ddate"]?>')+'&adate='+encodeURIComponent('<?php echo $_GET["adate"]?>'));
		
		q+="<?php 
		$viacities = '';
		$i = 0;
		while (isset($_GET['via'.$i])) {
			$viacities = $viacities . 'via'.$i.'='. $_GET['via'.$i] . '&';
		}
		$viacities = rtrim($viacities, '&');
		echo $viacities;
		?>";

		q+="&maxamount=<?php echo $_GET['maxamount']?>";
		console.log(q);

		getCityLocation(cityName, function(coord, city){
			console.log(city);
		    				console.log(coord);
		    				cities.push({'FromCity': city});
		    				point = new google.maps.LatLng(coord['lat'], coord['lng']);
		    				flightPlanCoordinates2.push(point);
		    				temp = new google.maps.Marker({
					  			position : point,
					  			map : map,
					  			title : city.toString()
					  			});
	    				});


    	$.get(q, function(data){
    		console.log('done!');
    		json = JSON.parse(data);
    		console.log(json);
    		
    		//$.each(json['Routes'], function(i){
    			i=0;
    			things = json['Routes'][i][i];
    			$('#routes').append('<li>Route '+i.toString()+'</li>');
    			$('#price').html('£'+parseFloat(json['Routes'][i]['cost']).toString());
    			$.each(things, function(j){
    				console.log(things[j]);


    				if(things[j]['type']=='transport'){
	    				getCityLocation(things[j]['to_city'], function(coord, city){
		    				console.log(coord);
		    				cities.push({'FromCity': city});
		    				point = new google.maps.LatLng(coord['lat'], coord['lng']);
		    				flightPlanCoordinates2.push(point);
		    				temp = new google.maps.Marker({
					  			position : point,
					  			map : map,
					  			title : city
					  			});
	    				});
	    			}


    			});
    		//});
    		
    		setTimeout(draw, 1000);

    		/*toTimeline(data['Routes']);
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

    		setTimeout(draw, 100);
    		console.log(flightPlanCoordinates)
    		
	*/
    	});
    }

    function draw(){
    	console.log({'a':cities});
    
    		getCityLocation("<?php echo $_GET['to'] ?>", function(coord, city){
		    				console.log(coord);
		    				cities.push({'FromCity': city});
		    				point = new google.maps.LatLng(coord['lat'], coord['lng']);
		    				flightPlanCoordinates2.push(point);
		    				temp = new google.maps.Marker({
					  			position : point,
					  			map : map2,
					  			title : city
					  			});

		    				toTimeline(cities);
    	
	    				});

    	connectPoints(flightPlanCoordinates2, map2);
    }
    </script>
  </body>
</html>
