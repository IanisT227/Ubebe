$("#smallImage").on('mouseover',function(){
    var smallImage = $(this);
    var largeReference = smallImage.attr('rel');
    $("#largeImage").attr('src',largeReference);
  });