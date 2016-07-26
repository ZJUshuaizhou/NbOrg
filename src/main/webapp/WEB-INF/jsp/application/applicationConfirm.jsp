<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript">
	function next() {
		document.getElementById("skip").value = "false";
		document.getElementById("updateForm").submit();
	}

	function skip() {
		document.getElementById("skip").value = "true";
		document.getElementById("updateForm").submit();
	}
</script>
<%@ include file="../head.jsp"%>


<div id="body" class="container">
	<div class="row">
		<%@ include file="../tree.jsp"%>
		<div class="col-md-8">
			<br>
			<form class="form-horizontal" method="post"
				action="application/update" id="updateForm">
				<h3>应用基本信息</h3>
				<input type="hidden" class="form-control" name="id"
					value="${app.id}" />
				<div class="row">
					<div class="col-md-12 form-group">
						<label class="col-md-3 control-label">应用名称:</label>
						<div class="col-md-9">
							<table style="width: 100%">
								<tr>
									<td width="75%"><input type="text" name="name"
										class="form-control" placeholder="请输入应用名称" value="${app.name}"></td>
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

				<div class="row">
					<div class="col-md-12 form-group">
						<label class="col-md-3 control-label">应用描述:</label>
						<div class="col-md-9">
							<div style="width: 75%">
								<textarea name="description" class="form-control" rows="3">${app.description}</textarea>
							</div>
						</div>
					</div>
				</div>
				<h3>应用接入配置</h3>
				<p>
					<i>请正确配置至少一种接入方式，否则用户将无法正常登陆该应用</i>
				</p>
				<h4>SAML配置</h4>
				<div class="row">
					<div class="col-md-12 form-group">
						<label class="col-md-3 control-label">发行者(Issuer):</label>
						<div class="col-md-9">
							<div style="width: 75%">
								<input type="text" class="form-control"
									placeholder="请输入发行者(Issuer)" name="saml.issuer"
									value="${app.saml.issuer }" disabled>
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-12 form-group">
						<label class="col-md-3 control-label">回调URL:</label>
						<div class="col-md-9">
							<div style="width: 75%">
								<input type="text" class="form-control" placeholder="请输入回调URL"
									name="saml.url" value="${app.saml.url}"
									onblur="validateurl(this,'surlmsg')">
							</div>
							<p id="surlmsg" style="display: none"></p>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-12 form-group">
						<label class="col-md-3 control-label">登出URL:</label>
						<div class="col-md-9">
							<div style="width: 75%">
								<input type="text" class="form-control" placeholder="请输入登出URL"
									name="saml.logoutUrl" value="${app.saml.logoutUrl }"
									onblur="validateurl(this,'slogmsg')">
							</div>
							<p id="slogmsg" style="display: none"></p>
						</div>
					</div>
				</div>

				<h4>OAuth配置</h4>
				<div class="row">
					<div class="col-md-12 form-group">
						<label class="col-md-3 control-label">回调URL:</label>
						<div class="col-md-9">
							<div style="width: 75%">
								<input type="text" class="form-control" placeholder="请输入回调URL"
									name="oauth.url" value="${app.oauth.url}"
									onblur="validateurl(this,'ourlmsg')">
							</div>
							<p id="ourlmsg" style="display: none"></p>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12 form-group">
						<label class="col-md-3 control-label">Key:</label>
						<div class="col-md-9">
							<table style="width: 100%">
								<tr>
									<td width="75%"><input type="text" class="form-control"
										name="oauth.oauthKey" id="key_text"
										value="${app.oauth.oauthKey}" disabled /></td>
									<td width="10%">
										<div id="key" style="margin-left: 10px" class='clip_button'>
											<img src="assets/img/copy.png" alt="复制" title="复制"><span style="font-size: 5px">复制</span>
										</div>
									</td>
									<td width="15%"></td>
								</tr>
							</table>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-12 form-group">
						<label class="col-md-3 control-label">Secret:</label>
						<div class="col-md-9">
							<table style="width: 100%">
								<tr>
									<td width="75%"><input type="text" class="form-control"
										name="oauth.oauthSecret" id="secret_text"
										value="${app.oauth.oauthSecret}" disabled /></td>
									<td width="10%">
										<div id="secret" style="margin-left: 10px" class='clip_button'>
											<img src="assets/img/copy.png" alt="复制" title="复制"><span style="font-size: 5px">复制</span>
										</div>
									</td>
									<td width="15%"></td>
								</tr>
							</table>
						</div>
					</div>
				</div>



				<h4>STS配置</h4>
				<div class="row">
					<div class="col-md-12 form-group">
						<label class="col-md-3 control-label">入口地址：</label>
						<div class="col-md-9">
							<div style="width: 75%">
								<input type="text" class="form-control" placeholder="请输入url"
									name="sts.endpoint" value="${app.sts.endpoint }" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12 form-group">
						<label class="col-md-3 control-label">证书别名：</label>
						<div class="col-md-9">
							<div style="width: 75%">
								<select id="id_select_alias" class="selectpicker bla bla bli"
									data-live-search="true" name="sts.certAlias"
									autofocus="${app.sts.certAlias}">
									<optgroup label="请选择证书" data-subtext="只能选一个"
										data-icon="icon-ok">
										<c:forEach items="${alias}" var="cert" varStatus="id">
											<option
												<c:if test="${cert eq app.sts.certAlias}">selected="selected"</c:if>>${cert}</option>
										</c:forEach>
									</optgroup>
								</select>
								<script type="text/javascript">
									$('.selectpicker').selectpicker();
								</script>
							</div>
						</div>
					</div>
				</div>
				<p id="tip"
					style="color: #0066cc; font-size: 14px; margin-top: 10px; margin-top: 10px; padding-left: 50px"></p>

				<div class="row">
					<div class="col-md-12 text-center">
						<input type="hidden" name="skip" id="skip" value="true" /> <a
							href="javascript:next()" type="button" class="btn btn-success"
							onmouseover="javascript:mouseOver()">下一步</a>
						&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:skip()" type="button"
							class="btn btn-success">跳过</a>
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
<script type="text/javascript" src="assets/js/jquery-confirm.js"></script>
<script type="text/javascript" src="assets/js/updateAppValidate.js"></script>
<script type="text/javascript"
	src="assets/js/ZeroClipboard/ZeroClipboard.js"></script>
<script type="text/javascript">
	window.onload = function() {
		var error = "${error}";
		var msg = "${msg}";
		if (error) {
			$.confirm({
				keyboardEnabled : true,
				title : '错误',
				content : error,
				confirmButtonClass : 'btn-info',
				autoClose : 'confirm|3000'
			});
		}
		if (msg) {
			$.confirm({
				keyboardEnabled : true,
				title : '提示',
				content : msg,
				confirmButtonClass : 'btn-info',
				autoClose : 'confirm|3000'
			});
		}
	}
	$("#li_application").addClass('active');

	var clip = null;
    var current_id = null;
    ZeroClipboard.setMoviePath("<%=basePath%>assets/js/ZeroClipboard/ZeroClipboard.swf");
	$(function() {
		clip = new ZeroClipboard.Client();
		clip.setHandCursor(true);
		clip.glue('key');
		clip.addEventListener('complete', function() {
		});
		$("div.clip_button").each(function() {
			$(this).bind('mouseover', function() {
				var button_id = $(this).attr('id');
				var text_id = button_id + '_text';
				move_swf(text_id, button_id);
			});
		});
	});
	function move_swf(text_id, button_id) {
		clip.reposition(button_id);
		clip.setText($('#' + text_id).val());
	}
</script>
</body>
</html>