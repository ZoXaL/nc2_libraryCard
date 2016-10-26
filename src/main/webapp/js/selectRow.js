$(document).ready(function() {
	var selected;
	$( "#recordsTable tbody tr" ).on( "click", function( event ) {
 		if(selected != null) {
 			selected.removeClass("info");
 		}
 		selected = $(this);
 		document.selectedTitle = $(this).find("td:eq(1)").text();
 		selected.addClass("info");
    });
});