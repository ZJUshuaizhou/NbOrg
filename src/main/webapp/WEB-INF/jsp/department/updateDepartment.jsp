<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../head.jsp"%>
<div id="body" class="container">
	<div class="row">
		<%@ include file="../tree.jsp"%>

        <div class="col-md-8">
            <h1>编辑部门</h1><br>
            <form id="signupForm" class="form-horizontal" method="post" action="department/update">
                <div class="row">
                    <div class="col-md-6 form-group">
                        <label class="col-md-4 control-label">名称:</label>
                        <div class="col-md-8">
                            <input id="name" type="text" class="form-control" name="name" value="${dep.name }">
                        </div>
                    </div>
                    
                     <div class="col-md-6 form-group">
                        <label class="col-md-4 control-label">上级主管部门:</label>
                        <div class="col-md-8">
                             <select id="id_select" class="selectpicker bla bla bli" data-live-search="true"  name="pdep">
		        <optgroup label="请选择所属部门" data-subtext="只能选一个" data-icon="icon-ok" >
		             <c:forEach items="${requestScope.deplist}" var="d" varStatus="id">
		             <option <c:if test="${d.name eq dep.parentdep}">selected="selected"</c:if>>${d.fullname }</option>
		             </c:forEach>
		        </optgroup>
		    </select>
			<script type="text/javascript">
			$('.selectpicker').selectpicker();
			</script>
                        </div>
                    </div> 
                </div>

                <div class="row">
                    <div class="col-md-6 form-group">
                        <label class="col-md-4 control-label">联系人:</label>
                        <div class="col-md-8">
                            <input id="contactPerson" type="text" class="form-control" name="contactPerson" value="${dep.contactPerson }">
                        </div>
                    </div>

                    <div class="col-md-6 form-group">
                        <label class="col-md-4 control-label">联系方式:</label>
                        <div class="col-md-8">
                            <input id="contactNumber" type="text" class="form-control" name="contactNumber"  value="${dep.contactNumber }">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 form-group">
                        <label class="col-md-2 control-label">地址:</label>
                        <div class="col-md-10">
                            <input id="address" type="text" class="form-control" name="address"  value="${dep.address }">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 form-group">
                        <label class="col-md-2 control-label">描述:</label>
                        <div class="col-md-10">
                            <textarea id="description" class="form-control" name="description" rows="3">${dep.description }</textarea>
                        </div>
                    </div>
                </div>
 <input type="hidden"  name="id" value="${dep.id }"/>
                <div class="row">
                    <div class="col-md-12 text-center">
                        <input type="submit" class="btn btn-primary" value="确认"/>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="department/list" type="button" class="btn btn-danger">取消</a>
                    </div>
                </div>


            </form>
        </div>
    </div>
    <%@ include file="../footer.jsp" %>
</div>
<script src="assets/js/jquery.mockjax.min.js"></script>
<script src="assets/js/test.js"></script>
<script src="assets/js/vtree.js"></script>
<script src="assets/js/base.js"></script>
<script src="assets/js/jquery.validate.js"></script>
<script src="assets/js/message_zh.js"></script>
<script src="assets/js/validate_expand.js"></script>
<script type="text/javascript">
$().ready(function() {
	 $("#signupForm").validate({
	        rules: {
	   name: 
		   {required:true,
		   stringCheck:true
		   },
	   id_select:"required",
	   contactPerson:
		   {
		   required:true,
		   nameCheck:true
	   },
	   contactNumber:
	   {
		   required:true,
		   isPhone:true
	   },
	   address:{ 
		   stringCheck:true, 
		   byteRangeLength:[3,100] 
		   },
		   description:{
			   stringCheck:true, 
			   byteRangeLength:[3,100] 
			   }
			   
	  },
	        messages: {
	   name: 
		   {
		   required: "请输入部门名称",
		   stringCheck: "用户名只能包括中文字、英文字母、数字和下划线"
		   },
	    id_select:"主管部门不能为空",
		  contactPerson:
		  {
			   required: "请填写部门联系人",
			   nameCheck: "用户名只能包括中文字、英文字母"
			   },
		   contactNumber:
		   {
			   required: "请填写部门联系方式",
			   isPhone: "请输入一个有效的联系电话" 
			   },
			 address:{ 
				   stringCheck: "请正确输入您的联系地址", 
				   byteRangeLength: "请详实您的联系地址以便于我们联系您" 
				   },
				   description:{ 
					   stringCheck: "请正确输入部门描述", 
					   byteRangeLength: "请详实相关的部门描述" 
					   }
	  }
	    });
	});
    $("#li_department").addClass('active');
</script>
</body>
</html>