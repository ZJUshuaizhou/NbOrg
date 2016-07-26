<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../head.jsp"%>
<div id="body" class="container">
	<div class="row">
		<%@ include file="../tree.jsp"%>

        <div class="col-md-8">
            <h1>添加人员</h1><br>
            <form id="signupForm" class="form-horizontal" method="post" action="person/add">
                <div class="row">
                    <div class="col-md-6 form-group">
                        <label class="col-md-4 control-label">真实姓名:</label>
                        <div class="col-md-8">
                            <input id="name" type="text" class="form-control" name="name" placeholder="请输入真实姓名">
                        </div>
                    </div>
 					<div class="col-md-6 form-group">
                        <label class="col-md-4 control-label">用户名:</label>
                        <div class="col-md-8">
                            <input id="username" type="text" class="form-control" name="username" placeholder="请输入登录用户名">
                        </div>
                    </div>

                  
                </div>

                <div class="row">
                    <div class="col-md-6 form-group">
                        <label class="col-md-4 control-label">固定电话:</label>
                        <div class="col-md-8">
                            <input id="telephone" type="text" class="form-control" name="telephone" placeholder="请输入固定电话">
                        </div>
                    </div>

                    <div class="col-md-6 form-group">
                        <label class="col-md-4 control-label">手机:</label>
                        <div class="col-md-8">
                            <input id="mobilephone" type="text" class="form-control" name="mobilephone" placeholder="请输入手机">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 form-group">
                        <label class="col-md-4 control-label">邮箱:</label>
                        <div class="col-md-8">
                            <input id="email" type="text" class="form-control" name="email" placeholder="请输入邮箱">
                        </div>
                    </div>
                      <div class="col-md-6 form-group">
                        <label class="col-md-4 control-label">性别:</label>
                        <div class="col-md-8">
                            <label class="radio-inline" >
                                <input type="radio" name="gender" id="inlineRadio1" value="0" checked="checked"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="inlineRadio2" value="1"> 女
                            </label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 form-group">
                        <label class="col-md-4 control-label">所属部门:</label>
                        <div class="col-md-8">
                            <select id="id_select" class="selectpicker bla bla bli" multiple data-live-search="true" name=deplist>
		        <optgroup label="请选择所属部门" data-subtext="可选多个" data-icon="icon-ok" >
		             <c:forEach items="${requestScope.deplist}" var="dep" varStatus="id">
		             <option >${dep.fullname }</option>
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
                             <input id="pos" type="text" class="form-control" name="pos" placeholder="请输入人员职位">
                        </div>
                    </div>
                </div>
                   <div class="row">
                    <div class="col-md-6 form-group">
                        <label class="col-md-4 control-label">是否为管理员:</label>
                        <div class="col-md-8">
                            <input id="adminFlag" type="checkbox"  name="adminFlag" value="1">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <br>
                      <div class="col-md-12 text-center">
                        <input type="submit" class="btn btn-primary" value="确认" />
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="person/list" type="button" class="btn btn-danger">取消</a>
                    </div>
                </div>


            </form>
        </div>
    </div>
    <%@ include file="../footer.jsp" %>
</div>
<script src="assets/js/jquery.mockjax.min.js"></script>
<script src="assets/js/jquery.validate.js"></script>
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
		   nameCheck:true,
		   stringCheck:true
		   },
	  username: 
		   {required:true,
		   stringCheck:true,
		   byteRangeLength:[3,15],
		   remote: {
               url: "person/checkusername",
               type: "post",
               dataType: "json",
               data: {
                   username: function () {
                       return $("#username").val();
                   }
               },
               dataFilter: function (data) {
                   if (data == "true") {
                       return true;
                   }
                   else {
                       return false;
                   }
               }
           }
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
		   nameCheck: "姓名只能包括中文字、英文字母",
		   stringCheck:"姓名只能包括中文字、英文字母"
		   },
	    id_select:"所属部门不能为空",
	    username: 
		   {required:"<font color=red>请输入人员登录账号</fond>",
		   stringCheck:"用户名只能包括中文字、英文字母、数字和下划线", 
		   byteRangeLength: "用户名必须在3-15个字符之间(一个中文字算2个字符)",
		   remote:"改名称已被使用，请重新填写"
		   },
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