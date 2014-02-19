 function updateHeight(){
        $('div.carousel, div.carousel-inner, div.item, img').css('height', $(window).height()-50);
        $('div.searchbar').css('height', $(window).height()-243);
      }


function getRandomPic(){
	$.getJSON( '/api/randomphoto.php', function(data) {
		b = true;
  		var items = [];
		$.each(data['photos'], function( key, val ) {
			if (b)
			{
				b = false;
				items.push ('<div class="item active"> ');
			}
			else
				items.push ('<div class="item"> ');
		  items.push(' <img src="'+val['images']['1920x1280']['url']+'" alt="Second slide">          <div class="container">           <div class="carousel-caption">              <h1>'+val['title']+'</h1>              <p>'+val['topic_description']+ '</p>              <p><a class="btn btn-lg btn-primary" href="#" role="button">Fly here</a></p>            </div>          </div>        </div>');
		});

		$('div.carousel-inner').html(items.join(''));
		console.log(data);
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
		
		items = [];
		$.each(events, function(val){
			items.push('<div class="row"><div class="col-md-3"><img src="'+events[val]['image']['medium']['url']+'"/></div><div class="col-md-7"><h4><a href="'+events[val]['url']+'" target ="_blank">'+events[val]['title']+'</a></h4><p>'+events[val]['start_time']+'</p><p>'+events[val]['venue_name']+'</p><p>'+events[val]['venue_address']+'</p></div></div>');
		});

		$('div.eventsList').html(items.join(''));
		//$('#events').modal('toggle');
	});
}
function getRoute(depart, arrive, other) {
	 
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
	for (var i = 0; i < cities.length; i++) {
		$('#legend').append('<div class="time-line-circle" style="width:25%">					<a class="time-line-href href1" href="#" onclick="change1()">					<div class="circleLine" style="opacity: 0;"></div>					<span>'+cities[i]['city']+'</span></a>				</div>');
		for (var j = 0; j < cities[i]['events'].length; j++)
		{
			$('#bubbles').append('<div class="timeline-circles timeline-circle2change2" id="timeline-circid1"><span id="textcircid1" onclick="ptext(\''+cities[i]['events'][j]['name']+'\');" class="timeline-textformat textincircle2and1">'+cities[i]['events'][j]['name']+'</span></div>');
		}
	};

	bubbles();
}

function getRoute(start, end, travelMode, fn){
	$.getJSON('/api/route.php?start='+start+'&end='+end+'mode='+travelMode, function(data){
		fn(data['routes']);
	});
}