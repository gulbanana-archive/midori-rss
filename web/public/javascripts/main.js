$(document).ready(function(){
	//wrapper necessary?
});

var skip = 0;
$(window).scroll(function()
{
    if($(window).scrollTop() == $(document).height() - $(window).height())
    {
        $('div#more').show();
        skip += 15;
        $.get("/more/"+skip, function(html)
        {
            if(html)
            {
                $("#items").append(html);
                $('div#more').hide();
            }
            else
            {
                $('div#more').html('<center>No more items to show.</center>');
            }
        });
    }
});