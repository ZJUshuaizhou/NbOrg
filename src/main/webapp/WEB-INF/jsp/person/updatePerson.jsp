<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../head.jsp"%>
<div id="body" class="container">
	<div class="row">
		<%@ include file="../tree.jsp"%>

        <div class="col-md-8">
            <h1>编辑人员</h1><br>
           <form id="signupForm" class="form-horizontal" method="post" action="person/update">
                <div class="row">
                    <div class="col-md-6 form-group">
                        <label class="col-md-4 control-label">姓名:</label>
                        <div class="col-md-8">
                            <input id="name" type="text" name="name" class="form-control" value="${per.name }">
                        </div>
                    </div>

                    <div class="col-md-6 form-group">
                        <label class="col-md-4 control-label">性别:</label>
                        <div class="col-md-8">
                            <label class="radio-inline">
                                <input type="radio" name="gender"  id="inlineRadio1" value="0" <c:if test="${'男' eq per.gender}">checked="checked"</c:if>> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="inlineRadio2" value="1" <c:if test="${'女' eq per.gender}">checked="checked"</c:if>> 女
                            </label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 form-group">
                        <label class="col-md-4 control-label">固定电话:</label>
                        <div class="col-md-8">
                            <input id="telephone" type="text" name="telephone" class="form-control" value="${per.telephone }">
                        </div>
                    </div>

                    <div class="col-md-6 form-group">
                        <label class="col-md-4 control-label">手机:</label>
                        <div class="col-md-8">
                            <input id="mobilephone" type="text" name="mobilephone" class="form-control" value="${per.mobilephone}">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 form-group">
                        <label class="col-md-4 control-label">邮箱:</label>
                        <div class="col-md-8">
                            <input id="email" type="text" name="email" class="form-control" value="${per.email }">
                        </div>
                    </div>
                </div>

                 <div class="row">
                    <div class="col-md-6 form-group">
                        <label class="col-md-4 control-label">所属部门:</label>
                        <div class="col-md-8">
                            <select id="id_select" class="selectpicker bla bla bli" data-live-search="true" name="deplist" ><!-- disabled="disabled" -->
		        <optgroup label="请选择所属部门" data-subtext="只能选一个部门更新" data-icon="icon-ok" >
		             <c:forEach items="${editdeplist}" var="dep" varStatus="id">
		             <option <c:if test="${dep.fullname eq per.dep}"> selected="selected"</c:if>>${dep.fullname }</option>
		             </c:forEach>
		        </optgroup>
		    </select>
		    <!-- <input id="deplist" type="hidden" name="deps" /> -->
			<script type="text/javascript">
			$('.selectpicker').selectpicker();  
			/* function displayVals() {   
			        var multipleValues = $("#id_select").val() || [];   
			        $('#deplist').val(multipleValues.toString());
			    }    
			$("select").change(displayVals);    */
			</script>  
                        </div>
                    </div>

                    <div class="col-md-6 form-group">
                        <label class="col-md-4 control-label">担任职务:</label>
                        <div class="col-md-8">
                             <input id="pos" type="text" class="form-control" name="pos" value="${per.pos}">
                        </div>
                    </div>
                </div>
                   <div class="row">
                    <div class="col-md-6 form-group">
                        <label class="col-md-4 control-label">是否为管理员:</label>
                        <div class="col-md-8">
                            <input type="checkbox"  name="adminFlag" value="1" <c:if test="${1 eq adminFlag}">checked="checked"</c:if>>
                        </div>
                    </div>
                </div>
                <input type="hidden"  name="id" value="${per.id }"/>
                 <input type="hidden"  name="predep" value="${per.dep }"/>
                <div class="row">
                    <br>
                   <div class="row">
                    <div class="col-md-12 text-center">
                        <input type="submit" class="btn btn-primary" value="确认"/>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="person/list" type="button" class="btn btn-danger">取消</a>
                    </div>
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
		   nameCheck:true
		   },
	   id_select:{required:true},
	   telephone:
		   {
		   isTel:true
	   },
	   mobilephone:
	   {
		   isPhone:true
	   },
	   email:{ 
		   isEmail:true 
		   }, 
		   pos:{
			   nameCheck:true, 
			   byteRangeLength:[2,15] 
			   }
			   
	  },
	        messages: {
	   name: 
		   {
		   required: "<font color=red>请输入人员姓名</fond>",
		   nameCheck: "姓名只能包括中文字、英文字母"
		   },
	    id_select:"所属部门不能为空",
	    telephone:
		   {
		   isTel:"请输入一个有效的联系电话"
	   },
	   mobilephone:
	   {
		   isPhone:"请输入一个有效的手机号码"
	   },
	   email:{ 
		   isEmail: "请输入一个有效的Email地址" 
		   }, 
		   pos:{
			   nameCheck:"职位只能包括中文字、英文字母", 
			   byteRangeLength:"职位必须在2-15个字符之间(一个中文字算2个字符)"
			   }
			   
	  }
	    });
	});
  $("#li_person").addClass('active');
</script>
</body>
</html>