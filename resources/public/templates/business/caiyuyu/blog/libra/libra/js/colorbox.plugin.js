
$(document).ready(function(){
    if(document.body.clientWidth>=900){
        $(".list_video").colorbox({iframe:true, innerWidth:600,innerHeight:500});
        $(".list_image").colorbox({rel:'.list_image',innerWidth:600, innerHeight:500})
    }else{
        var w = document.body.clientWidth;
        var h = w*9/16;
        $(".list_video").colorbox({iframe:true, innerWidth:w,innerHeight:h})
        $(".list_image").colorbox({rel:'.list_image',innerWidth:w, innerHeight:h})
    }	
})