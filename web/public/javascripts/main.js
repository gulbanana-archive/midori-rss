$(document).ready(function(){
	//wrapper necessary?
});

var skip = 15
$(window).scroll(function()
{
    if($(window).scrollTop() == $(document).height() - $(window).height())
    {
        $('div#more').show();
        $.ajax("/more", {type:"POST", data: JSON.stringify(skip), contentType: "application/json", success: function(html)
        {
            if(html)
            {
            	skip += 15
                $("#items").append(html);
                $('div#more').hide();
            }
            else
            {
                $('div#more').html('<center>No more items to show.</center>');
            }
        }, error: function()
        {
        	$('div#more').hide();
        }
        });
    }
});