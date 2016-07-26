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
            <h1>人员详情</h1><br>
            <div class="row">
                <div class="col-md-6 form-group">
                    <label class="col-md-4 control-label text-right">姓名:</label>
                    <label class="col-md-8 control-label">${per.name }</label>
                </div>

                <div class="col-md-6 form-group">
                    <label class="col-md-4 control-label text-right">性别:</label>
                    <label class="col-md-8 control-label">${per.gender }</label>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6 form-group">
                    <label class="col-md-4 control-label text-right">固定电话:</label>
                    <label class="col-md-8 control-label">${per.telephone }</label>
                </div>

                <div class="col-md-6 form-group">
                    <label class="col-md-4 control-label text-right">手机:</label>
                    <label class="col-md-8 control-label">${per.mobilephone }</label>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6 form-group">
                    <label class="col-md-4 control-label text-right">邮箱:</label>
                    <label class="col-md-8 control-label">${per.email }</label>
                </div>
                
                 <div class="col-md-6 form-group">
                    <label class="col-md-4 control-label text-right">用户名:</label>
                    <label class="col-md-8 control-label">${per.username }</label>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6 form-group">
                    <label class="col-md-4 control-label text-right">所属部门:</label>
                    <label class="col-md-8 control-label">${per.dep }</label>
                </div>

                <div class="col-md-6 form-group">
                    <label class="col-md-4 control-label text-right">担任职务:</label>
                    <label class="col-md-8 control-label">${per.pos }</label>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12 text-center">
                 <c:if test="${adminFlag==1&&per.isEdit==1 }">
                 <a  href="person/edit/${per.id }?depsn=${per.depsn}" type="button" class="btn btn-warning">编辑</a>
                  <a onclick="del(${per.id })" type="button" class="btn btn-danger">删除</a>
                  </c:if>
                    <a href="person/list" type="button" class="btn btn-primary">返回</a>
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
function del(perid){
   	$.confirm({
   		keyboardEnabled: true,
   	    title: '删除人员',
   	    content: '此操作会删除该人员的所有信息，而且操作不可撤销，确定删除该人员？',
   	    confirmButtonClass: 'btn-info',
   	    cancelButtonClass: 'btn-danger',
   	    confirm: function(){ 
   	    	window.location.href="person/del/"+perid;
   	    	
   	    }
   	});
    }
    
	$("#li_person").addClass('active');
</script>
<%if(attention==2){ %>
<script type="text/javascript">
$.confirm({
		keyboardEnabled: true,
	    title: '添加成功',
	    content: '添加人员成功！',
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
	    content: '更新人员成功！',
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