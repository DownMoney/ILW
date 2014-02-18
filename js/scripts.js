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
	getCoord($(self).val(), function(coord, city){
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
		  items.push(' <img src="'+val['photo_file_url']+'" alt="Second slide">          <div class="container">           <div class="carousel-caption">              <h1>'+val['photo_title']+'</h1>              <p>'+ '</p>              <p><a class="btn btn-lg btn-primary" href="#" role="button">Fly here</a></p>            </div>          </div>        </div>');
		});

		$('div.carousel-inner').html(items.join(''));
		$('.carousel').carousel("pause").removeData();
				
		$('.carousel').carousel(0);	
		});
	});
}