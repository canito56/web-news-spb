function removeNews(id) {
	swal({
	  title: "Are you sure?",
	  text: "Once deleted, you will not be able to recover this News!",
	  icon: "warning",
	  buttons: true,
	  dangerMode: true,
	})
.	then((OK) => {
	  if (OK) {
		$.ajax({url:"/delete/"+id, success: function(res) {
				console.log(res);
			}
		});
		location.href="/list";
	  }
	});
}