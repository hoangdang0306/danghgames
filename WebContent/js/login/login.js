$j("#login-button").click(function(event){
//	event.preventDefault();
//	$('form').fadeOut(500);
//	$('.wrapper').addClass('form-success');
	
	var username = document.getElementById("name");
	var password = document.getElementById("pass");
	if (!isValidData(username.value) || username.value.length == 0 || !isValidData(password.value) || password.value.length == 0) {
		$j('div#login-message').html("Please input the valid data!");
		return;
	}
	
	$.ajax({
		type:"POST",
		url:CONTEXT + "/json/login_json.json",
		dataType:"json",
		data:"username=" + encodeURIComponent(username.value) + "&password=" + encodeURIComponent(password.value),
		success:function(result) {
			if (result) {
				console.log("success");
			} else {
				console.log("fail");
//				window.location.reload();
			}
		},
		error:function() {
			console.log("error");
//			window.location.reload();
		}
	});
});

function isValidData(data) {
	if (data == undefined || data === null) {
		return false;
	}
	return true;
}