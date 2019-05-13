$(document).ready(function() {
  $("footer").append("<div id='snabbkontakt'><h3>Contact us</h3></div>");

  $("#snabbkontakt").click(function() {
    $('html, body').animate({
      scrollTop: $("footer").offset().top + 1000
    }, 1000);
  });

});