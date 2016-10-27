$(document).ready(function() {
	$('#add-editForm').submit(function(event) {		
		var pattern = /^\d\d\d\d-\d\d-\d\d$/;
		var valid = true;
		$(this).find('.dateInput').each(function() {
			if ($(this).val().search(pattern) == -1) {
				valid = false;				
			}
		});
		if(!valid) {
			event.preventDefault();
			alert("Illegal date format!");
		}
	});
});