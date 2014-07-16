$(document).read(function () {
	$('#delete').click(function () {
			$('#managefrom').attr("action", "/manage");
			$('#managefrom').submit();
		});
});

$(document).read(function () {
	$('#edit').click(function () {
			$('#managefrom').attr("action", "/");
			$('#managefrom').submit();
		});
});
