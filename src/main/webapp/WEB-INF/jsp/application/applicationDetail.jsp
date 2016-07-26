<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../head.jsp"%>
<div id="body" class="container">
	<div class="row">
		<%@ include file="../tree.jsp"%>

		<div class="col-md-8">
			<br>
			<ul class="nav nav-tabs">
				<li role="presentation" class="active"><a href="#basicInfo"
					data-toggle="tab">应用基本信息</a></li>
				<li role="presentation"><a href="#roleAndUser"
					data-toggle="tab">角色和用户</a></li>
			</ul>
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade in active" id="basicInfo">
					<h3>&nbsp;&nbsp;&nbsp;&nbsp;应用基本信息</h3>
					<div class="row">
						<div class="col-md-12 form-group">
							<label class="col-md-3 control-label text-right">应用名称:</label> <label
								class="col-md-9 control-label">${app.name }</label>
						</div>
					</div>

					<div class="row">
						<div class="col-md-12 form-group">
							<label class="col-md-3 control-label text-right">应用描述:</label> <label
								class="col-md-9 control-label">${app.description}</label>
						</div>
					</div>

					<h3>&nbsp;&nbsp;&nbsp;&nbsp;SAML配置</h3>
					<div class="row">
						<div class="col-md-12 form-group">
							<label class="col-md-3 control-label text-right">发行者(Issuer):</label>
							<label class="col-md-9 control-label">${app.saml.issuer}</label>
						</div>
					</div>

					<div class="row">
						<div class="col-md-12 form-group">
							<label class="col-md-3 control-label text-right">回调URL:</label> <label
								class="col-md-9 control-label">${app.saml.url}</label>
						</div>
					</div>

					<div class="row">
						<div class="col-md-12 form-group">
							<label class="col-md-3 control-label text-right">登出URL:</label> <label
								class="col-md-9 control-label">${app.saml.logoutUrl }</label>
						</div>
					</div>

					<h3>&nbsp;&nbsp;&nbsp;&nbsp;OAuth配置</h3>
					<div class="row">
						<div class="col-md-12 form-group">
							<label class="col-md-3 control-label text-right">回调URL:</label> <label
								class="col-md-9 control-label">${app.oauth.url }</label>
						</div>
					</div>

					<div class="row">
						<div class="col-md-12 form-group">
							<label class="col-md-3 control-label text-right">Key:</label> <label
								class="col-md-9 control-label">${app.oauth.oauthKey}</label>
						</div>
					</div>

					<div class="row">
						<div class="col-md-12 form-group">
							<label class="col-md-3 control-label text-right">Secret:</label>
							<label class="col-md-9 control-label">${app.oauth.oauthSecret }</label>
						</div>
					</div>



					<h3>&nbsp;&nbsp;&nbsp;&nbsp;STS配置</h3>
					<div class="row">
						<div class="col-md-12 form-group">
							<label class="col-md-3 control-label text-right">入口地址:</label> <label
								class="col-md-9 control-label">${app.sts.endpoint}</label>
						</div>
					</div>



					<div class="row">
						<div class="col-md-12 form-group">
							<label class="col-md-3 control-label text-right">证书别名:</label> <label
								class="col-md-9 control-label">${app.sts.certAlias}</label>
						</div>
					</div>

				</div>
				<div class="tab-pane fade" id="roleAndUser">
					<c:forEach items="${roles}" var="role" varStatus="id">
						<table class="table table-bordered table-hover " 
							style="width:100%;table-layout: fixed; word-wrap: break-word; text-align: left; margin-top: 30px">

							<tr>
								<td ><b>${role.name}</b></td>
							</tr>
							<tr>
								<td><b>关联人员:</b>${role.personNames}</td>
							</tr>
							<tr>
								<td><b>关联部门:</b>${role.departmentNames}</td>
							</tr>
						</table>
					</c:forEach>
				</div>
			</div>

			<div class="row">
				<br>
				<div class="col-md-12 text-center">
					<a href="application/list" type="button" class="btn btn-danger">返回</a>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../footer.jsp"%>
</div>
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/jquery.mockjax.min.js"></script>
<script src="assets/js/test.js"></script>
<script src="assets/js/vtree.js"></script>
<script type="text/javascript">
	$("#li_application").addClass('active');
</script>
</body>
</html>