<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../head.jsp"%>
<div id="body" class="container">
	<div class="row">
		<%@ include file="../tree.jsp"%>

		<div class="col-md-8">
			<br>
			<form class="form-horizontal" method="post" id="addAppForm"
				action="application/finishAdd">
				<h3>应用基本信息</h3>
				<div class="row-app">
					<div class="col-md-12 form-group">
						<label class="col-md-3 control-label">应用名称:</label>
						<div class="col-md-9">
							<table style="width: 100%">
								<tr>
									<td width="75%"><input type="text" name="name"
										class="form-control" id="AppName"
										onblur="validateExist(this,'appNameMsg')"
										onfocus="myfocus(this)" placeholder="请输入应用名称"></td>
									<td width="5%"><span
										style="margin-left: 5px; font-size: 18px; color: red;"><b>*</b></span>
									</td>
									<td width="20%"><div id="appNameMsg"
											style="margin-left: 10px; display: none; font-size: 10px; color: red;">
											<b>此名称已被占用</b>
										</div></td>
								</tr>
							</table>
						</div>

					</div>
				</div>


				<div class="row-app">
					<div class="col-md-12 form-group">
						<label class="col-md-3 control-label">应用描述:</label>
						<div class="col-md-9">
							<table style="width: 75%">
								<tr>
									<td><textarea name="description" class="form-control"
											rows="3"></textarea></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<div class="row-app">
					<div class="col-md-12 text-center">
						<input type="button" class="btn btn-primary" value="下一步"
							onclick="javascript:validate()" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<%@ include file="../footer.jsp"%>
</div>
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/jquery.mockjax.min.js"></script>
<script src="assets/js/test.js"></script>
<script src="assets/js/vtree.js"></script>
<script src="assets/js/base.js"></script>
<script type="text/javascript" src="assets/js/jquery-confirm.js"></script>
<script type="text/javascript">
	function validateExist(element, msgId) {
		//alert(element.value+" "+element.name +" "+element.id);
		var msg = document.getElementById(msgId);

		var postUrl = "application/validate" + element.id;
		var postData = "{\"" + element.name + "\":\"" + element.value + "\"}";
		var postJson = JSON.parse(postData);
		//	var msgId = "\""+element.id+"Msg\"";
		if (element.value) {
			$.ajax({
				url : postUrl,
				dataType : "json",
				type : "POST",
				data : postJson,
				success : function(data) {
					msg.style.display = "none";

				},
				error : function() {
					msg.style.display = "";
					element.style.backgroundColor = "#ffeeee";
				}
			});
		}
	}
	function validate() {
		if (!document.getElementsByName("name")[0].value) {
			tip = "应用名称不能为空";
			$.confirm({
				keyboardEnabled : true,
				title : '提示',
				content : tip,
				confirmButtonClass : 'btn-info',
				autoClose : 'confirm|3000'
			});
		} else {
			$.ajax({
				url : "application/validateAppName",
				dataType : "json",
				type : "POST",
				data : {
					"name" : $("#AppName").val()
				},
				success : function(data) {
					document.getElementById("addAppForm").submit();
				},
				error : function() {
					$.confirm({
						keyboardEnabled : true,
						title : '提示',
						content : '应用名称已被占用，请重新输入',
						confirmButtonClass : 'btn-info',
						autoClose : 'confirm|3000'
					});
				}
			});

		}
	}

	function myfocus(element) {
		element.style.backgroundColor = "#fff";
	}

	$("#li_application").addClass('active');
</script>
</body>
</html>