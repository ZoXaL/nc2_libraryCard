$(document).ready(function() {
	//Editing
	$('#editBtn').on('click', function(e) {
		if (document.selectedTitle=="none") {
			return;
		}
		console.log(document.selectedTitle);
		window.location.href += "/edit?title="+encodeURIComponent(document.selectedTitle);
	})
	//Deleting
	$('#hiddenDeleteForm').submit(function(event) {
		$(this).find("input").val(document.selectedTitle);
	});
});