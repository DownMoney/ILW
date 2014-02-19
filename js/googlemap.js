function initialize() {

	var mapOptions = {
		zoom: 1,
	    center: new google.maps.LatLng(0, -180),	
	    mapTypeId: google.maps.MapTypeId.DRIVING
	};

  	var map = new google.maps.Map(document.getElementById('googlemapcanvas'), mapOptions);


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