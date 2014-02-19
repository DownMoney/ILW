/*
 * <plugin name>
 * version 1
 * author: ChilliPear
 * http://chillipear.com
 */

(function($){

function initialize($obj, posX, posY, option3){

        //Gets the first li element and clones it
        var objLi = $obj.find('li');
        var objSpan = $obj.find('span');
        var append = objLi.first().clone();
        //hides all li except the first  
        objLi.first().siblings().hide();
        
        //adds the cloned first element at the top
        $obj.prepend(append);
        
        $('.chilli-select li:first-child h6').remove();
        
        //hides the first Li
        objLi.first().hide();
        
        
        $('body').on('click','.chilli-select li', function(){
            //console.log($(this).data('value'));
            $obj.find('li').first().remove();
            $(this).clone().prependTo($obj);
            $('.chilli-select li:first-child img').remove();
            $('.chilli-select li').removeClass('selected');
            $(this).addClass('selected');
            $('.chilli-select li:first-child h6').remove();
            $('.chilli-select li:first-child').append('<img style="display:block !important;width:32px;height:27px;position:absolute;top:3px;right:-3px;" src="img/arrow_drop.png"/>');
            if($obj.find('li').is(":hidden")){
                $obj.find('li').slideDown('100');
                $obj.find('span').slideDown('100');
            }else {
                $obj.find('li').first().siblings().hide();
                $obj.find('span').hide('100');
            }
        });


        
        
        //show image on hover
/*        $('body').delegate('.chilli-select li img','hover', function( event ) {
            if( event.type === 'mouseenter' )  {
            var pos = $(this).parent().position();
            
             $obj.append('<div class="chilli-sel-img"><img src="" /></div>');
           
            $('.chilli-sel-img').css({'left':(pos.left+posX),'top':(pos.top+posY)});
            
            var src = $(this).attr('src');
            
            //$('.img').show();
            $('.chilli-sel-img img').attr('src',src);
             }
            else {
                $('.chilli-sel-img').empty().remove();
            }
        });*/
        
        
        //show tool tip on hover
        $('body').delegate('.chilli-select li p','hover', function( event ) {
            if( event.type === 'mouseenter' )  {
            var pos = $(this).parent().position();
            
            $obj.append('<div class="chilli-sel-tooltip"></div>');
           
            $('.chilli-sel-tooltip').css({'left':(pos.left+posX),'top':(pos.top+posY)});
            
            var span = $(this).clone();
            
            $('.chilli-sel-tooltip').append(span);
             }
            else {
                $('.chilli-sel-tooltip').empty().remove();
            }
        });


        //show tool tip on hover
        $('body').delegate('.chilli-select li','hover', function( event ) {
         
         
         if($(this).find('h6').length > 0){

            if( event.type === 'mouseenter' )  {
               var pos = $(this).position();
               //alert('is');
               $obj.append('<div class="chilli-sel-img"><img src="" /></div>');
              
               $('.chilli-sel-img').css({'left':(pos.left+posX),'top':(pos.top+posY)});
               
               var src = $(this).find('h6').data('img');
               
               $('.chilli-sel-img img').attr('src',src);
                }
               else {
                  $('.chilli-sel-img').empty().remove();
               }
         }
        });

};	
 
// plugin name                
$.fn.select = function(options){


        //set defaults
	var defaults = { 
		posX: 230,
		posY: 10,
		option3: "value"
        };


	var option = $.extend(defaults, options);

	return this.each(function(){
	  var $this = $(this);  
	  initialize($this, option.posX, option.posY, option.option3);		
	});	

};
})(jQuery);