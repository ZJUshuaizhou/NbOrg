<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../head.jsp"%>
<div id="body" class="container">
	<div class="row">
		<%@ include file="../tree.jsp"%>
		<div class="col-md-8">
			<form id="updateRole" class="form-inline"
				action="application/updateRole" method="POST">
				<input type="hidden" class="form-control" name="appId" id="appId"
					value="${appId }" /> <span style="font-size: 24px"><b>配置角色</b></span>
				<a href="application/addRole?appId=${appId}" id="addRoleBtn"
					type="button" class="btn btn-primary" style="margin-left: 560px;">添加角色</a>
				<br> <br>

				<c:if test="${roles != null && !empty roles}">
					<table id="rolesInfo"
						class="table table-bordered table-hover text-center"
						style="table-layout: fixed; word-wrap: break-word;">
						<thead>
							<tr>
								<th class="text-center" width=80px>角色</th>
								<th class="text-center" width=290px>已选部门</th>
								<th class="text-center" width=290px>已选人员</th>
								<th class="text-center" width=100px>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${roles}" var="role" varStatus="id">
								<tr>
									<td>${role.name }</td>
									<td style="text-align: left">${role.departmentNames }</td>
									<td style="text-align: left">${role.personNames }</td>
									<td>
										<div class="btn-group">
											<button type="button"
												class="btn btn-primary btn-xs dropdown-toggle"
												data-toggle="dropdown" aria-haspopup="true"
												aria-expanded="false">
												操作 <span class="caret"></span>
											</button>
											<ul class="dropdown-menu" style="min-width: 60px">
												<li><a
													href="application/editRole?roleId=${role.id}&appId=${appId}">编辑</a></li>
												<li><a href="javascript:del(${role.id},${appId})">删除</a></li>
											</ul>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>

				<div id="editRole"
					style="width: 800px; height: 300px; margin-bottom: 5px">

					<input type="hidden" class="form-control" name="roleId" id="roleId"
						value="${roleId }" />
					<table id="roleInfo"
						style="width: 95%; table-layout: fixed; border-collapse: separate; border-spacing: 5px 20px;">
						<tr>
							<td>
								<div class="col-md-8">
									<label class="col-md-4 control-label form-control" style="margin-left:15px">角色名称:</label> <input
										id="roleName" type="text" class="form-control" style="margin-left:10px" name="roleName"
										placeholder="请输入角色名称" value="${roleName }">
								</div>
							</td>
						</tr>

						<tr>
							<td><input id="personName" type="text"
								style="margin-left: 30px; margin-right: 20px"
								class="form-control" placeholder="搜索人员"> <a
								href="javascript:searchPerson()" type="button"
								class="btn btn-success">搜索</a></td>
						</tr>


						<tr>
							<td>

								<div style="margin-left: 30px; margin-top: -20px">

									<div style="height: 140px;" class="select_side">
										<p>
											<b>给人员赋予该角色(<i>可选多个人员</i>)
											</b>
										</p>
										<select style="height: 140px; width: 290px" id="selectL"
											name="selectL" multiple="multiple">
											<c:forEach items="${persons}" var="person" varStatus="id">
												<option id="${person.id }" title="${person.dep}">${person.name }</option>
											</c:forEach>
										</select>
									</div>

									<div class="select_opt">
										<p id="toright" class="select_toright" title="添加">
											<img src="assets/img/toLeft.jpg" />
										</p>
										<p id="toleft" class="select_toleft" title="移除">
											<img src="assets/img/toRight.jpg" />
										</p>
									</div>
									<div class="select_side">
										<p>已选人员</p>
										<select style="height: 140px; width: 290px" id="selectR"
											name="selectR" multiple="multiple">
											<c:forEach items="${originPer}" var="per" varStatus="id">
												<option id="${per.id }" title="${per.dep}">${per.name }
													| ${per.dep}</option>
											</c:forEach>
										</select>
									</div>
									<input id="pidList" name="pidList" type="hidden" value="" />
								</div>
							</td>
						</tr>

						<tr>
							<td><input id="depName" type="text"
								style="margin-left: 30px; margin-right: 20px"
								class="form-control" name="depName" placeholder="搜索部门">
								<a href="javascript:searchDepartment()" type="button"
								class="btn btn-success">搜索</a></td>
						</tr>
						<tr>
							<td>
								<div style="margin-left: 30px;; margin-top: -20px">
									<div style="height: 140px" class="select_side">
										<p>
											<b>给部门里所有人员赋予该角色(<i>可选多个部门</i>)
											</b>
										</p>
										<select style="height: 140px; width: 290px" id="selectDepL"
											name="selectDepL" multiple="multiple">
											<c:forEach items="${departments}" var="department"
												varStatus="id">
												<option id="${department.id }">${department.fullname }</option>
											</c:forEach>
										</select>
									</div>

									<div class="select_opt">
										<p id="toDepRight" class="select_toright" title="添加">
											<img src="assets/img/toLeft.jpg" />
										</p>
										<p id="toDepLeft" class="select_toleft" title="移除">
											<img src="assets/img/toRight.jpg" />
										</p>
									</div>
									<div class="select_side">
										<p>已选部门</p>
										<select style="height: 140px; width: 290px" id="selectDepR"
											name="selectDepR" multiple="multiple">
											<c:forEach items="${originDep}" var="dep" varStatus="id">
												<option id="${dep.id }">${dep.fullname }</option>
											</c:forEach>
										</select>
									</div>
									<input id="didList" name="didList" type="hidden" value="" />
								</div>
							</td>
						</tr>
					</table>
					<div class="row">
						<div class="col-md-12" style="padding-top: 5px">
							<a href="javascript:updateSubmit()" type="button"
								style="margin-right: 50px; margin-left: 230px"
								class="btn btn-success">&nbsp;&nbsp;保&nbsp;&nbsp;&nbsp;&nbsp;存&nbsp;&nbsp;</a>
							<a type="button" id="cancel" class="btn btn-danger">&nbsp;&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;&nbsp;</a>
						</div>
					</div>
				</div>


				<div class="col-md-12" style="margin-top: 5px" id="complete">
					<a href="application/list" type="button" style="margin-left: 300px"
						class="btn btn-success">&nbsp;&nbsp;&nbsp;&nbsp;完&nbsp;&nbsp;&nbsp;&nbsp;成&nbsp;&nbsp;&nbsp;&nbsp;</a>
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
<script src="assets/js/select2select.js"></script>
<script src="assets/js/select2select-dep.js"></script>

<script type="text/javascript">
	window.onload = function() {
		var toggle = "${toggle}";

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

		if (toggle == "true") {
			$('#editRole').slideToggle();
		} else {
			window.location.hash = "#editRole";
			document.getElementById("complete").style.display = "none";
		}

	}
	$("#li_application").addClass('active');
	function del(roleId, appId) {
		$.confirm({
			keyboardEnabled : true,
			title : '删除角色',
			content : '此操作会删除该角色的所有信息，而且操作不可撤销，确定删除该角色吗？',
			confirmButtonClass : 'btn-info',
			cancelButtonClass : 'btn-danger',
			confirm : function() {
				$.ajax({
					url : "application/deleteRole",
					dataType : "json",
					type : "POST",
					data : {
						"appId" : appId,
						"roleId" : roleId
					},
					success : function(data) {
						window.location.href = "application/listRoles?appId="
								+ data.appId;
					}
				});
			}
		});
	}

	function updateSubmit() {
		var selectsR = [];
		var rightSel = $("#selectDepR");
		rightSel.find("option").each(function() {
			selectsR.push(this.id);
		});
		selectsR = selectsR.join(",");
		$("#didList").val(selectsR);

		var selectsR = [];
		var rightSel = $("#selectR");
		rightSel.find("option").each(function() {
			selectsR.push(this.id);
		});
		selectsR = selectsR.join(",");
		$("#pidList").val(selectsR);

		if (!$("#roleName").val()) {
			$.confirm({
				keyboardEnabled : true,
				title : '提示',
				content : '角色名称不能为空',
				confirmButtonClass : 'btn-info',
				autoClose : 'confirm|3000'
			});
		} else {

			$.ajax({
				url : "application/validateAppRole",
				dataType : "json",
				type : "post",
				data : {
					"roleName" : $("#roleName").val(),
					"appId" : $("#appId").val(),
					"roleId" : $("#roleId").val()
				},
				success : function(data) {
					$("#updateRole").submit();
				},
				error : function() {
					$.confirm({
						keyboardEnabled : true,
						title : '提示',
						content : '您已经有此角色，请重新输入角色名称',
						confirmButtonClass : 'btn-info',
						autoClose : 'confirm|3000'
					});
				}
			})
		}
	}
	function searchPerson() {
		var selectsR = [];
		var rightSel = $("#selectR");
		rightSel.find("option").each(function() {
			selectsR.push(this.id);
		});
		selectsR = selectsR.join(",");
		$("#pidList").val(selectsR);
		$.ajax({
			url : "application/searchPerson",
			dataType : "json",
			type : "POST",
			data : {
				"name" : $("#personName").val(),
				"pids" : $("#pidList").val()
			},
			success : function(data) {
				var leftSel = $("#selectL");
				leftSel.empty();
				$.each(data.persons, function(i, item) {
					addPersonOption(leftSel, item.id, item.name, item.dep);
				})
			}
		});
	}

	function searchDepartment() {
		var selectsR = [];
		var rightSel = $("#selectDepR");
		rightSel.find("option").each(function() {
			selectsR.push(this.id);
		});
		selectsR = selectsR.join(",");
		$("#didList").val(selectsR);
		$.ajax({
			url : "application/searchDepartment",
			dataType : "json",
			type : "POST",
			data : {
				"name" : $("#depName").val(),
				"dids" : $("#didList").val()
			},
			success : function(data) {
				var leftSel = $("#selectDepL");
				leftSel.empty();
				$.each(data.departments, function(i, item) {
					addDepartmentOption(leftSel, item.id, item.fullname);
				})
			}
		});
	}

	$(function() {
		$('#cancel').click(function() {
			$('#editRole').slideToggle();
			document.getElementById("complete").style.display = "";
		})
	})
	function addPersonOption(leftSel, id, value, dep) {

		var option = "<option id='";
		option = option+id+"' value='";
		option = option+value+"' >";
		option = option + value + "\t|\t" + dep + "</option>";
		leftSel.append(option);
	}

	function addDepartmentOption(leftSel, id, fullname) {
		var option = "<option id='";
		option = option+id+"' value='";
		option = option+fullname+"' >";
		option = option + fullname + "</option>";
		leftSel.append(option);
	}
</script>
<style type="text/css">
.select_side {
	float: left;
	width: 290px
}

select {
	width: 180px;
	height: 120px
}

.select_opt {
	float: left;
	width: 40px;
	height: 100%;
	margin-left: 20px;
	margin-right: 20px;
	margin-top: 80px;
	margin-bottom: 20px;
}

.select_toright {
	width: 40px;
	height: 40px;
	margin-left: 10px;
	margin-top: -20px;
	cursor: pointer;
}

.select_toleft {
	width: 40px;
	height: 40px;
	margin-left: 10px;
	margin-top: 10px;
	cursor: pointer;
}
</style>
</body>
</html>