$(document).ready(function() {
	$('#editBtn').on('click', function(e) {
		//alert("going edit");
		console.log("going to edit");
		console.log(document.selectedTitle);
		window.location.href += "/edit?title="+encodeURIComponent(document.selectedTitle);
	})
	$('#hiddenDeleteForm').submit(function(event) {
		$(this).find("input").val(document.selectedTitle);
	});
});