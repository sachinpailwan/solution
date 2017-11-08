$(document).ready(
		function() {

			var table = $("tableId");
			$("button").click(
					function() {
						$.ajax({
							url : "/item",
							success : function(data) {

								$('#table >tbody').empty();
								for ( var i in data) {

									var id = data[i].id;
									var name = data[i].name;
									var price = data[i].price;

									$("#table > tbody").append(
											"<tr><td>" + id + "</td><td>"
													+ name + "</td><td>"
													+ price + "</td></tr>");
								}
							}
						});
					});

			/*$("#submit").click(function(){
			    alert("submit is called");
				$("#form").submit(function(e){

			        $.ajax({

			            url: "/item",
			            type: "POST",
			            data: $(this).serialize(),

			            success: function(data){
			                alert(data);
			            
			            }


			        });

			    });*/

			$('#submitModel')
					.click(
							function(e) {
								e.preventDefault();
								//alert(JSON.stringify($('#myForm').serializeArray()));
								var str = $("#myForm").serialize() + "&id="
										+ Math.floor(Math.random() * 1E16);
								alert(str);
								var data1 = str.split("&");
								console.log(data1);
								var obj = {};
								for ( var key in data1) {
									console.log(data1[key]);
									var value = data1[key].split("=")[1];
									if($.isNumeric(value)){
										value =parseInt(value);
									}
									obj[data1[key].split("=")[0]] = value;
								}

								console.log(obj);

								
								$.ajax({

									url : "/item",
									type : "POST",
									contentType: 'application/json',
									dataType:'json',
									data : obj,

									success : function(data) {
										alert(data);

									},
									error: function(e) {
									    console.log(e);
									  }

								});
							});
		});
