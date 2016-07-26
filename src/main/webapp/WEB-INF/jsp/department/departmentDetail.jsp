<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../head.jsp"%>
<div id="body" class="container">
	<div class="row">
		<%@ include file="../tree.jsp"%>
		<%
    	int attention=(Integer)session.getAttribute("attention");
        %>
        <div class="col-md-8">
            <h1>部门详情</h1><br>

            <div class="row">
                <div class="col-md-6 form-group">
                    <label class="col-md-4 control-label text-right">名称:</label>
                    <label class="col-md-8 control-label">${dep.name }</label>
                </div>

                <div class="col-md-6 form-group">
                    <label class="col-md-4 control-label text-right">上级主管部门:</label>
                    <label class="col-md-8 control-label">${dep.parentdep }</label>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6 form-group">
                    <label class="col-md-4 control-label text-right">联系人:</label>
                    <label class="col-md-8 control-label">${dep.contactPerson }</label>
                </div>

                <div class="col-md-6 form-group">
                    <label class="col-md-4 control-label text-right">联系方式:</label>
                    <label class="col-md-8 control-label">${dep.contactNumber }</label>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12 form-group">
                    <label class="col-md-2 control-label text-right">地址:</label>
                    <label class="col-md-10 control-label">${dep.address }</label>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12 form-group">
                    <label class="col-md-2 control-label text-right">描述:</label>
                    <label class="col-md-10 control-label">${dep.description }
                    </label>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12 text-center">
                <c:if test="${adminFlag==1&&dep.isEdit==1}">
                 <a  href="department/edit/${dep.id }" type="button" class="btn btn-warning">编辑</a>
                  <a onclick="del(${dep.id })" type="button" class="btn btn-danger">删除</a>
                  </c:if>
                  <a href="department/list" type="button" class="btn btn-primary">返回</a>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="../footer.jsp" %>
</div>
<script src="assets/js/jquery.mockjax.min.js"></script>
<script src="assets/js/test.js"></script>
<script src="assets/js/vtree.js"></script>
<script src="assets/js/base.js"></script>
<script type="text/javascript" src="assets/js/jquery-confirm.js"></script>
<script type="text/javascript">
function del(depid){
   	$.confirm({
   		keyboardEnabled: true,
   	    title: '删除部门',
   	    content: '此操作会删除该部门下的所有人员以及子部门，而且操作不可撤销，确定删除该部门？',
   	    confirmButtonClass: 'btn-info',
   	    cancelButtonClass: 'btn-danger',
   	    confirm: function(){ 
   	    	window.location.href="department/del/"+depid;
   	    }
   	});
    }
	$("#li_department").addClass('active');
</script>
<%if(attention==2){ %>
<script type="text/javascript">
$.confirm({
		keyboardEnabled: true,
	    title: '添加成功',
	    content: '添加部门成功！',
	    confirmButtonClass: 'btn-info',
   	    cancelButtonClass: 'btn-danger',
	    autoClose: 'confirm|3000'
	});
</script>
<% session.setAttribute("attention", 0);} %>
<%if(attention==3){ %>
<script type="text/javascript">
$.confirm({
		keyboardEnabled: true,
	    title: '修改成功',
	    content: '更新部门成功！',
	    confirmButtonClass: 'btn-info',
   	    cancelButtonClass: 'btn-danger',
	    autoClose: 'confirm|3000'
	});
</script>
<% session.setAttribute("attention", 0);} %>
<%if(attention==-1){ %>
<script type="text/javascript">
$.confirm({
		keyboardEnabled: true,
	    title: '操作失败',
	    content: '操作失败，请重新尝试！',
	    confirmButtonClass: 'btn-info',
   	    cancelButtonClass: 'btn-danger',
	    autoClose: 'confirm|3000'
	});
</script>
<% session.setAttribute("attention", 0);} %>
</body>
</html>