function initialize() {

	var mapOptions = {
		zoom: 5,
	    center: new google.maps.LatLng(50,20),	
	    mapTypeId: google.maps.MapTypeId.DRIVING
	};

  	var map = new google.maps.Map(document.getElementById('googlemapcanvas'), mapOptions);


    if(navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var pos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude); 
            alert("Latitude: " + position.coords.latitude +
  "<br>Longitude: " + position.coords.longitude);
            map.setCenter(pos);
        }, function() {
            alert("WRONG");
            //handleNoGeolocation(true);
        });
    } else {
    // Browser doesn't support Geolocation
        //handleNoGeolocation(false);
        alert("WRONG");
    }


function handleNoGeolocation(errorFlag) {
  if (errorFlag) {
    var content = 'Error: The Geolocation service failed.';
  } else {
    var content = 'Error: Your browser doesn\'t support geolocation.';
  }
}


  	var flightPlanCoordinates = [];

    for (var r = 0; r < 10; r++) {
        flightPlanCoordinates[r] = new google.maps.LatLng((Math.random() * 180) - 90,(Math.random() * 360) - 180);
        console.log(flightPlanCoordinates);  
    }

  	var i = 1;
  	var j = [];
  	var temp;
  	$.each(flightPlanCoordinates, function(index,point) {
  		temp = new google.maps.Marker({
  			position : point,
  			map : map,
  			title : "Point " + i
  		})
  		i++;
  	});

  	$.each(flightPlanCoordinates, function(index,point) {
  		j = [];
  		if(index < flightPlanCoordinates.length - 1) {
  			j = [point, flightPlanCoordinates[index+1]];
  			console.log(j);
  			var temp = new google.maps.Polyline({
  				path : j,
  				map: map,
  				geodesic: true,
  				strokeColor: getRandomColor(),
  				strokeOpacity: 1.0,
  				strokeWeight: 2
  			});
  		}
  	})
}
google.maps.event.addDomListener(window, 'load', initialize);

function getRandomColor() {
    var letters = '0123456789ABCDEF'.split('');
    var color = '#';
    for (var i = 0; i < 6; i++ ) {
        color += letters[Math.round(Math.random() * 15)];
    }
    return color;
}