$(document).ready(function() {
	var selected;
	var selectedId;
	$( "#recordsTable tbody tr" ).on( "click", function( event ) {
 		if(selected != null) {
 			selected.removeClass("info");
 		}
 		selected = $(this);
 		selectedId = $(this).find("td").first().text();
 		selected.addClass("info");
    });
});