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
		  items.push(' <img src="'+val['images']['1920x1280']['url']+'" alt="Second slide">          <div class="container">           <div class="carousel-caption">              <h1>'+val['title']+'</h1>              <p>'+val['topic_description']+ '</p>              <p><a class="btn btn-lg btn-primary" href="#" role="button">Learn more</a></p>            </div>          </div>        </div>');
		});

		$('div.carousel-inner').html(items.join(''));
		console.log(data);
		$('.carousel').carousel("pause").removeData();
		$('.carousel').carousel(0);	
	});
}