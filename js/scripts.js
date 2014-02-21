 function updateHeight(){
        $('div.carousel, div.carousel-inner, div.item, img').css('height', $(window).height()-50);
        $('div.searchbar').css('height', $(window).height()-243);
      }


function getRandomPic(){
	$.getJSON( '/api/randomphoto.php', function(data) {
		b = true;
  		var items = [];

  		//
		$.each(data['photos'], function( key, val ) {
			if (b)
			{
				b = false;
				showEvents(val['position'][0].toString()+','+val['position'][1].toString());
				items.push ('<div class="item active"> ');
			}
			else
				items.push ('<div class="item"> ');
		  items.push(' <img src="'+val['images']['1920x1280']['url']+'" alt="Second slide">          <div class="container">           <div class="carousel-caption">              <h1>'+val['topic_description']+'</h1>              <p>'+val['title']+ '</p>              <p><a class="btn btn-lg btn-primary" href="#" role="button" onclick="betterSearchBar();getAirport(\''+val['position'][1]+'\',\''+val['position'][0]+'\');">Fly here</a><a class="btn btn-lg btn-primary" href="#"  data-toggle="modal" data-target="#events" role="button">Check events</a></p>            </div>          </div>        </div>');
		});

		$('div.carousel-inner').html(items.join(''));
	//	console.log(data.photos);
	//console.log(data.photos[0]);
		$('.carousel').carousel("pause").removeData();
		$('.carousel').carousel(0);	

		updateHeight();
	});
}

function autocompleteCity(text){
	
	$.getJSON('/api/autocomplete.php?city='+$(text).val(), function(data){
		items = [];
		
		$.each(data, function(key, val){
			items.push(val['PlaceName']+' ('+val['PlaceId']+')');
		});

		$(text).autocomplete({source: items});
	});
}

function getCityLocation(city, fn){
	$.getJSON( "https://maps.googleapis.com/maps/api/geocode/json?address="+city+"&sensor=false&key=AIzaSyCsHxnjIW3dFbEh7eJQrLF--Qv1mqy4n58", function( data ) {
	  coord = data['results'][0]['geometry']['location'];
	  

	  fn(coord, city);
	});
}

function getCoord(city, fn){
	$.getJSON( "https://maps.googleapis.com/maps/api/geocode/json?address="+city+"&sensor=false&key=AIzaSyCsHxnjIW3dFbEh7eJQrLF--Qv1mqy4n58", function( data ) {
	  coord = data['results'][0]['geometry']['bounds'];
	  box= {};
	  box['minX'] = coord['southwest']['lng'];
	  box['minY'] = coord['southwest']['lat'];

	  box['maxX'] = coord['northeast']['lng'];
	  box['maxY'] = coord['northeast']['lat'];

	  fn(box, city);
	});
}

function showPictures(self){
	p = /([a-z]+)/ig;
	cityName = $(self).val();
	cityName = p.exec(cityName)[1];
	showEvents(cityName);
	getCoord(cityName, function(coord, city){

		$.getJSON("/api/getpics.php?minx="+coord['minX']+"&miny="+coord['minY']+"&maxx="+coord['maxX']+"&maxy="+coord['maxY'], function( data ) {
			var items = [];
			b = true;
			console.log(data);
			$.each(data['photos'], function( key, val ) {
			if (b)
			{
				b = false;
				items.push ('<div class="item active"> ');
			}
			else
				items.push ('<div class="item"> ');

		  items.push(' <img src="'+val['photo_file_url']+'" alt="Second slide">          <div class="container">           <div class="carousel-caption">              <h1>'+city+'</h1>              <p>'+ '</p>              <p><a class="btn btn-lg btn-primary" href="#"  data-toggle="modal" data-target="#events" role="button">Check events</a></p>            </div>          </div>        </div>');

		});

		$('div.carousel-inner').html(items.join(''));
		$('.carousel').carousel("pause").removeData();
				
		$('.carousel').carousel(0);	
		updateHeight();
		});
	});
}


function getEvents(city, fn){
	$.getJSON('/api/events.php?city='+city, function(data){
		fn(data['events']['event'], city);
	});
}

function showEvents(coord){

	getEvents(coord, function(events, city){
		console.log(events);
		var items = [];
		$.each(events, function(val){
			console.log(val);
			try{
			items.push('<div class="row"><div class="col-md-3"><img src="'+events[val]['image']['medium']['url']+'"/></div><div class="col-md-7"><h4><a href="'+events[val]['url']+'" target ="_blank">'+events[val]['title']+'</a></h4><p>'+events[val]['start_time']+'</p><p>'+events[val]['venue_name']+'</p><p>'+events[val]['venue_address']+'</p></div></div>');
			}
			catch(err){
				;
			}
		});
		console.log(items);
		$('div.eventsList').html(items.join(''));
		//$('#events').modal('toggle');
	});
}

function bubbles(){
	$('body').css('overflow-x','hidden');
	var con = $('.timeline-container');
	var position = con.position();
	$('#timeline-outputtext').css('top',(position.top));
	
	$('.time-line-href').on('click', function(e){
		e.preventDefault();
		$('.circleLine').css('opacity','0')
		$(this).find('div').css('opacity','1')
		$('#timeline-outputtext').animate({
		    'left':-275
		});
	});
	$('.timeline-circles').on('click',function(){
		//$('#timeline-outputtext').css('top', '0px');
		var outputR = $('#timeline-outputtext').css('<?php echo direction;?>');
		if(outputR == '-275px'){
			$('#timeline-outputtext').animate({
				'left':'0'
			});
		}else {
			$('#timeline-outputtext').animate({
				'left':'-275px'
			}).animate({
				'left':'0'
			});
		}	
	});
}

function ptext(test){
	$('#timeline-outputtext').html('text');
}

function toTimeline(cities){
	$('#legend').html('');
	$('#bubbles').html('');
	div = 100/cities.length;
	p  = /[a-z]+/ig;
    			
			   
	for (var i = 0; i < cities.length; i++) {
		name = cities[i]['FromCity'].trim();
		m = p.exec(name);
		if (m!=null)
			name = m[0];
		console.log(m);
		$('#legend').append('<div class="time-line-circle" style="width:'+div+'%">					<a class="time-line-href href1" href="#" onclick="change1()">					<div class="circleLine" style="opacity: 0;"></div>					<span>'+name+'</span></a>				</div>');
		
		getEvents(name, function(data){
			console.log(data);
			for (var j = 0; j < data.length; j++)
			{
				try{
				$('#bubbles').append('<div class="timeline-circles timeline-circle2change2" id="timeline-circid1"><span id="textcircid1" onclick="ptext(\''+data[j]['title']+'\');" class="timeline-textformat textincircle2and1">'+data[j]['title']+'</span></div>');
				}
				catch(errr){}
			}
		});
		/**/
	};

	bubbles();
}

function getRoute(start, end, travelMode, fn){
	$.getJSON('/api/route.php?start='+start+'&end='+end+'mode='+travelMode, function(data){
		fn(data['routes']);
	});
}

function getAirport(lng, lat){
	showEvents(lat.toString()+','+lng.toString());
	$.getJSON('/api/airport.php?location='+lat.toString()+','+lng.toString(), function(data){


		p = /([a-z]+)/ig;
			cityName = data['results'][0]['name'];
			cityName = p.exec(cityName);
			$.getJSON('/api/autocomplete.php?city='+cityName, function(data2){
		
		
		
		i =($('#via').children().length);
		addField();
		$('#via'+i.toString()).val(data2[0]['PlaceName']+' ('+data2[0]['PlaceId']+')');
		getRandomPic();
		
	});

		
	});
}

function addField(){
	i =($('#via').children().length);
	$('#via').append(' <div class="viaFields ui-widget">   <label for="from">FLY VIA: </label>  <input id="via'+i+'" name="via'+i+'" class="form-control" onkeyup="autocompleteCity(this);"></div>')
}

function getEventTypes(){
	$.getJSON('/api/eventsCategories.php', function(data){
		$.each(data['category'], function(i){
			$('#eventType').append('<option value="'+data['category'][i]['id']+'">'+data['category'][i]['name']+'</option>')
		});
	});
}

function getCurrentAirport(){
if(navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
          // var pos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude); 
		$.getJSON('/api/airport.php?location='+position.coords.latitude.toString()+','+position.coords.longitude.toString(), function(data){
		
			p = /([a-z]+)/ig;
			cityName = data['results'][0]['name'];
			cityName = p.exec(cityName);
			$.getJSON('/api/autocomplete.php?city='+cityName, function(data2){
		
		
	
		$('#from').val(data2[0]['PlaceName']+' ('+data2[0]['PlaceId']+')');
		//getRandomPic();
		});

			//$('#from').val(data['results'][0]['name']);
		
	});
	});
    }
}

function betterSearchBar() {
	if($('#searchForm').height() > 530) {
		$('#VIA').css('display','inline-block');
		$('.viaFields').css({'visibility':'hidden','z-score': '90000'});
		$('#via').animate({
			height: "0px"
		})

		setTimeout(function(){
			var w = $('.searchbar').width() + 10;
			$('#buttonvia').animate({marginLeft:"0px"});

			$('#via').css({
				"position" : "absolute",
				"visibility" : "hidden",
				"margin-left" : w + "px",
				"margin-top" : "50px",
				"background" : "rgba(0,0,0,0.7)",
				"width" : "230px",
				"padding" : "10px"
			})
		},500);
	}
}

var clicked = false;
function getVia() {

	if(!clicked){
		$('.viaFields').css('visibility','visible');
		setTimeout($('#via').animate({
			display : 'block',
			visibility : "visible",
			height : "200px",
			padding : "10px"
		}),500);
		clicked = true;
	} else {
		$('.viaFields').css('visibility','hidden');
		$('#via').animate({
			display : 'none',
			visibility : 'hidden',
			height: "0px",
			padding : "0px"
		});
		clicked = false;
	}
}