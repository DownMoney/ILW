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
		  items.push(' <img src="'+val['images']['1920x1280']['url']+'" alt="Second slide">          <div class="container">           <div class="carousel-caption">              <h1>'+val['topic_description']+'</h1>              <p>'+(val['title'])+ '</p>              <p><a class="btn btn-lg btn-primary" href="#" role="button" >Fly here</a></p>            </div>          </div>        </div>');
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