$(document).ready(function(){
	//wrapper necessary?
});

$(window).scroll(function()
		{
		    if($(window).scrollTop() == $(document).height() - $(window).height())
		    {
		        $('div#loadmoreajaxloader').show();
		        $.ajax({
		        url: "loadmore.php",
		        success: function(html)
		        {
		            if(html)
		            {
		                $("#postswrapper").append(html);
		                $('div#loadmoreajaxloader').hide();
		            }else
		            {
		                $('div#loadmoreajaxloader').html('<center>No more posts to show.</center>');
		            }
		        }
		        });
		    }
		});

/*
 * 
 * <div id="postswrapper">
   <div class="item">content</div>
   ...
   <div id="loadmoreajaxloader" style="display:none;"><center><img src="ajax-loader.gif" /></center></div>
</div>

 */
*/