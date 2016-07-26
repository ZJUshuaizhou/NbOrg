<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../head.jsp"%>
<div id="body" class="container">
	<div class="row">
		<%@ include file="../tree.jsp"%>
		<%
			int pageTotal = (Integer) request.getAttribute("totalPage_mine");
			int pageIndex = (Integer) request.getAttribute("pageNo_mine");

			int pageTotalOther = (Integer) request.getAttribute("totalPage_dep");
			int pageIndexOther = (Integer) request.getAttribute("pageNo_dep");
			String myAppUrl = "application/list?myapp=";
			String appname = (String)request.getSession().getAttribute("searchAppByName");
			String otherappname = (String)request.getSession().getAttribute("searchOtherAppByName");
			String otherappcreator = (String)request.getSession().getAttribute("searchOtherAppByCreator");
			if(appname!=null){
				myAppUrl = "application/search?myapp=";
			}
		%>
		<div class="col-md-8">
			<ul class="nav nav-tabs">
				<li id="li_mine" role="presentation"><a href="#myApplication"
					data-toggle="tab" class="active">我的应用</a></li>
				<li id="li_other" role="presentation"><a
					href="#otherApplication" data-toggle="tab">其它应用</a></li>
			</ul>
			<div id="myTabContent" class="tab-content">
				<div
					<c:choose>
						<c:when test="${myapp eq true}">
       						class="tab-pane fade in active" 
    					</c:when>
						<c:when test="${myapp eq false}">
       			 			class="tab-pane fade"
    					</c:when>
					</c:choose>
					id="myApplication">
					<br>
					<form class="form-inline" action="application/search" method="POST">
					<input type="hidden" name="myapp" value="true"/>
					<input type="hidden" name="searchOtherAppByName" value="${searchOtherAppByName }"/>
					<input type="hidden" name="searchOtherAppByCreator" value="${searchOtherAppByCreator }"/>
						<input type="text" name="searchAppByName" class="form-control"
							placeholder="应用名称" value="${searchAppByName }"> 
						<button type="submit" class="btn btn-warning">搜索</button>
						<a href="application/add" type="button"
							class="btn btn-primary glyphicon-plus"> 添加应用</a>
					</form>
					<br /> <br />
					<table class="table table-bordered table-hover text-center">
						<thead>
							<tr>
								<th class="text-center">序号</th>
								<th class="text-center">应用名称</th>
								<th class="text-center">应用描述</th>
								<th class="text-center">创建者</th>
								<th class="text-center">管理部门</th>
								<th class="text-center">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${raws_mine}" var="app" varStatus="id">
								<tr>
									<td>${id.index+1 }</td>
									<td>${app.name }</td>
									<td>${app.description }</td>
									<td>${app.creator }</td>
									<td>${app.manageDep }</td>
									<td>
										<div class="btn-group">
											<button type="button" class="btn btn-primary btn-xs dropdown-toggle"
												data-toggle="dropdown" aria-haspopup="true"
												aria-expanded="false">
												操作 <span class="caret"></span>
											</button>
											<ul class="dropdown-menu" style="min-width: 60px">
												<li><a href="application/detail?app=${app.id}">应用详情</a></li>
												<li><a href="application/edit?app=${app.id}">应用配置</a></li>
												<li><a href="application/listRoles?appId=${app.id}">角色配置</a></li>
												<li><a href="javascript:del(${app.id})">删除应用</a></li>
											</ul>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div>查询到${total_mine }条记录/共${totalPage_mine }页</div>
					<c:if test="${totalPage_mine > 1 }">
						<nav class="text-center">
							<ul class="pagination">
								<c:choose>
									<c:when test="${pageNo_mine==1 }">
										<li class="disabled"><a href="javascript:void(0);">首页</a></li>
										<li class="disabled"><a href="javascript:void(0);"
											aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
									</c:when>
									<c:otherwise>
										<li><a
											href="<%=myAppUrl %>true&pageNo_mine=1&pageNo_dep=${pageNo_dep}">首页</a></li>
										<li><a
											href="<%=myAppUrl %>true&pageNo_mine=${pageNo_mine-1}&pageNo_dep=${pageNo_dep}"
											aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
									</c:otherwise>
								</c:choose>
								<c:if test="${totalPage_mine<6 }">
									<%
										for (int i = 1; i <= pageTotal; i++) {
													if (pageIndex == i) {
									%>
									<li class="active"><a
										href="<%=myAppUrl %>true&pageNo_mine=<%=i%>&pageNo_dep=${pageNo_dep}"><%=i%><span
											class="sr-only">(current)</span></a></li>
									<%
										} else {
									%>
									<li><a
										href="<%=myAppUrl %>true&pageNo_mine=<%=i%>&pageNo_dep=${pageNo_dep}"><%=i%></a></li>
									<%
										}
									%>
									<%
										}
									%>
								</c:if>
								<c:if
									test="${(totalPage_mine>=6)&&(pageNo_mine>2)&&(pageNo_mine<totalPage_mine-2) }">
									<li><a
										href="<%=myAppUrl %>true&pageNo_mine=${pageNo_mine-2}&pageNo_dep=${pageNo_dep}">${pageNo_mine-2 }</a></li>
									<li><a
										href="<%=myAppUrl %>true&pageNo_mine=${pageNo_mine-1}&pageNo_dep=${pageNo_dep}">${pageNo_mine-2 }</a></li>
									<li class="active"><a
										href="<%=myAppUrl %>true&pageNo_mine=${pageNo_mine}&pageNo_dep=${pageNo_dep}">${pageNo_mine }<span
											class="sr-only">(current)</span></a></li>
									<li><a
										href="<%=myAppUrl %>true&pageNo_mine=${pageNo_mine+1 }&pageNo_dep=${pageNo_dep}">${pageNo_mine+1 }</a></li>
									<li><a
										href="<%=myAppUrl %>true&pageNo_mine=${pageNo_mine+2 }&pageNo_dep=${pageNo_dep}">${pageNo_mine+2 }</a></li>
								</c:if>
								<c:if test="${(totalPage_mine>=6)&&(pageNo_mine<=2) }">
									<%
										for (int i = 1; i <= 5; i++) {
													if (pageIndex == i) {
									%>
									<li class="active"><a
										href="<%=myAppUrl %>true&pageNo_mine=<%=i%>&pageNo_dep=${pageNo_dep}"><%=i%><span
											class="sr-only">(current)</span></a></li>
									<%
										} else {
									%>
									<li><a
										href="<%=myAppUrl %>true&pageNo_mine=<%=i%>&pageNo_dep=${pageNo_dep}"><%=i%></a></li>
									<%
										}
									%>
									<%
										}
									%>
								</c:if>
								<c:if
									test="${(totalPage_mine>=6)&&(pageNo_mine>=totalPage_mine-2) }">
									<%
										for (int i = pageTotal - 4; i <= pageTotal; i++) {
													if (pageIndex == i) {
									%>
									<li class="active"><a
										href="<%=myAppUrl %>true&pageNo_mine=<%=i%>&pageNo_dep=${pageNo_dep}"><%=i%><span
											class="sr-only">(current)</span></a></li>
									<%
										} else {
									%>
									<li><a
										href="<%=myAppUrl %>true&pageNo_mine=<%=i%>&pageNo_dep=${pageNo_dep}"><%=i%></a></li>
									<%
										}
									%>
									<%
										}
									%>
								</c:if>
								<c:choose>
									<c:when test="${pageNo_mine==totalPage_mine }">
										<li class="disabled"><a href="javascript:void(0);"
											aria-label="Previous"><span aria-hidden="true">&raquo;</span></a></li>
										<li class="disabled"><a href="javascript:void(0);">尾页</a></li>
									</c:when>
									<c:otherwise>
										<li><a
											href="<%=myAppUrl %>true&pageNo_mine=${pageNo_mine+1}&pageNo_dep=${pageNo_dep}"
											aria-label="Previous"><span aria-hidden="true">&raquo;</span></a></li>
										<li><a
											href="<%=myAppUrl %>true&pageNo_mine=${totalPage_mine}&pageNo_dep=${pageNo_dep}">尾页</a></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</nav>
					</c:if>
				</div>
				<div
					<c:choose>
						<c:when test="${myapp eq false}">
       						class="tab-pane fade in active" 
    					</c:when>
						<c:when test="${myapp eq true}">
       			 			class="tab-pane fade"
    					</c:when>
					</c:choose>
					id="otherApplication">
					<br>
					<form class="form-inline" action="application/search"  method="POST">
					<input type="hidden" name="myapp" value="false"/>
						<input type="hidden" name="searchAppByName" value="${searchAppByName }"/>
						<input name="searchOtherAppByName" type="text" class="form-control" placeholder="应用名称" value="${searchOtherAppByName }">
						<input name="searchOtherAppByCreator" type="text" class="form-control" placeholder="创建者" value="${searchOtherAppByCreator }">
						<button type="submit" class="btn btn-warning">搜索</button>
					</form>
					<br /> <br />
					<table class="table table-bordered table-hover text-center">
						<thead>
							<tr>
								<th class="text-center">序号</th>
								<th class="text-center">应用名称</th>
								<th class="text-center">应用描述</th>
								<th class="text-center">创建者</th>
								<th class="text-center">管理部门</th>
								<th class="text-center">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${raws_dep}" var="app" varStatus="id">
								<tr>
									<td>${id.index+1 }</td>
									<td>${app.name }</td>
									<td>${app.description }</td>
									<td>${app.creator }</td>
									<td>${app.manageDep }</td>
									<td>
										<div class="btn-group">
											<button type="button" class="btn btn-primary dropdown-toggle"
												data-toggle="dropdown" aria-haspopup="true"
												aria-expanded="false">
												操作 <span class="caret"></span>
											</button>
											<ul class="dropdown-menu" style="min-width: 60px">
												<li><a href="application/detail?app=${app.id}">应用详情</a></li>
												<c:if test="${adminFlag eq 1}">
													<li><a href="application/edit?app=${app.id}">应用配置</a></li>
													<li><a href="application/listRoles?appId=${app.id}">角色配置</a></li>
													<li><a href="javascript:del(${app.id})" href="#">删除应用</a></li>
												</c:if>
											</ul>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div>查询到${total_dep }条记录/共${totalPage_dep }页</div>
					<c:if test="${totalPage_dep > 1}">
						<nav class="text-center">
							<ul class="pagination">
								<c:choose>
									<c:when test="${pageNo_dep==1 }">
										<li class="disabled"><a href="javascript:void(0);">首页</a></li>
										<li class="disabled"><a href="javascript:void(0);"
											aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
									</c:when>
									<c:otherwise>
										<li><a
											href="<%=myAppUrl %>false&pageNo_dep=1&pageNo_mine=${pageNo_mine}">首页</a></li>
										<li><a
											href="<%=myAppUrl %>false&pageNo_dep=${pageNo_dep-1}&pageNo_mine=${pageNo_mine}"
											aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
									</c:otherwise>
								</c:choose>
								<c:if test="${totalPage_dep<6 }">
									<%
										for (int i = 1; i <= pageTotalOther; i++) {
													if (pageIndexOther == i) {
									%>
									<li class="active"><a
										href="<%=myAppUrl %>false&pageNo_dep=<%=i%>&pageNo_mine=${pageNo_mine}"><%=i%><span
											class="sr-only">(current)</span></a></li>
									<%
										} else {
									%>
									<li><a
										href="<%=myAppUrl %>false&pageNo_dep=<%=i%>&pageNo_mine=${pageNo_mine}"><%=i%></a></li>
									<%
										}
									%>
									<%
										}
									%>
								</c:if>
								<c:if
									test="${(totalPage_dep>=6)&&(pageNo_dep>2)&&(pageNo_dep<totalPage_dep-2) }">
									<li><a
										href="<%=myAppUrl %>false&pageNo_dep=${pageNo_dep-2}&pageNo_mine=${pageNo_mine}">${pageNo_dep-2 }</a></li>
									<li><a
										href="<%=myAppUrl %>false&pageNo_dep=${pageNo_dep-1}&pageNo_mine=${pageNo_mine}">${pageNo_dep-2 }</a></li>
									<li class="active"><a
										href="<%=myAppUrl %>false&pageNo_dep=${pageNo_dep}&pageNo_mine=${pageNo_mine}">${pageNo_dep }<span
											class="sr-only">(current)</span></a></li>
									<li><a
										href="<%=myAppUrl %>false&pageNo_dep=${pageNo_dep+1 }&pageNo_mine=${pageNo_mine}">${pageNo_dep+1 }</a></li>
									<li><a
										href="<%=myAppUrl %>false&pageNo_dep=${pageNo_dep+2 }&pageNo_mine=${pageNo_mine}">${pageNo_dep+2 }</a></li>
								</c:if>
								<c:if test="${(totalPage_dep>=6)&&(pageNo_dep<=2) }">
									<%
										for (int i = 1; i <= 5; i++) {
													if (pageIndex == i) {
									%>
									<li class="active"><a
										href="<%=myAppUrl %>false&pageNo_dep=<%=i%>&pageNo_mine=${pageNo_mine}"><%=i%><span
											class="sr-only">(current)</span></a></li>
									<%
										} else {
									%>
									<li><a
										href="<%=myAppUrl %>false&pageNo_dep=<%=i%>&pageNo_mine=${pageNo_mine}"><%=i%></a></li>
									<%
										}
									%>
									<%
										}
									%>
								</c:if>
								<c:if
									test="${(totalPage_dep>=6)&&(pageNo_dep>=totalPage_dep-2) }">
									<%
										for (int i = pageTotalOther - 4; i <= pageTotalOther; i++) {
													if (pageIndexOther == i) {
									%>
									<li class="active"><a
										href="<%=myAppUrl %>false&pageNo_dep=<%=i%>&pageNo_mine=${pageNo_mine}"><%=i%><span
											class="sr-only">(current)</span></a></li>
									<%
										} else {
									%>
									<li><a
										href="<%=myAppUrl %>false&pageNo_=<%=i%>&pageNo_dep=${pageNo_dep}"><%=i%></a></li>
									<%
										}
									%>
									<%
										}
									%>
								</c:if>
								<c:choose>
									<c:when test="${pageNo_dep==totalPage_dep }">
										<li class="disabled"><a href="javascript:void(0);"
											aria-label="Previous"><span aria-hidden="true">&raquo;</span></a></li>
										<li class="disabled"><a href="javascript:void(0);">尾页</a></li>
									</c:when>
									<c:otherwise>
										<li><a
											href="<%=myAppUrl %>false&pageNo_dep=${pageNo_dep+1}&pageNo_mine=${pageNo_mine}"
											aria-label="Previous"><span aria-hidden="true">&raquo;</span></a></li>
										<li><a
											href="<%=myAppUrl %>false&pageNo_mine=${pageNo_mine}&pageNo_dep=${totalPage_dep}">尾页</a></li>
									</c:otherwise>
								</c:choose>
							</ul>

						</nav>
					</c:if>
				</div>
			</div>
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
	function del(app) {
		$.confirm({
			keyboardEnabled : true,
			title : '删除应用',
			content : '此操作会删除该应用的所有信息，而且操作不可撤销，确定删除该应用吗？',
			confirmButtonClass : 'btn-info',
			cancelButtonClass : 'btn-danger',
			confirm : function() {
				$.ajax({
					url : "application/delete",
					dataType : "json",
					type : "POST",
					data : {
						"app" : app
					},
					success : function(data) {
						window.location = "application/list"
					}
				});
			}
		});
	}

	window.onload = function() {
		var myapp = "${myapp}";
		if (myapp == "true") {
			$("#li_mine").addClass('active');
			$("#li_other").removeClass('active');
		} else {
			$("#li_other").addClass('active');
			$("#li_mine").removeClass('active');
		}
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
</script>
</body>
</html>