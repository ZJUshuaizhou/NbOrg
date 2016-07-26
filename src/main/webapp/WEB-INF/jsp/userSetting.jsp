<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="head.jsp"%>
<%
    	int attention=(Integer)session.getAttribute("attention");
        %>
<div id="body" class="container">
	<div class="row">
		<%@ include file="tree.jsp"%>
		<div class="col-md-8">
			<ul class="nav nav-tabs">
				<li id="li_userinfo" role="presentation"><a href="#userinfo"
					data-toggle="tab" class="active">编辑基本信息</a></li>
				<li id="li_changepsw" role="presentation"><a
					href="#changepsw" data-toggle="tab">修改密码</a></li>
			</ul>
			<div id="myTabContent" class="tab-content">
			<div id="userinfo" 
			<c:choose>
						<c:when test="${usersetting eq true}">
       						class="tab-pane fade in active" 
    					</c:when>
						<c:when test="${usersetting eq false}">
       			 			class="tab-pane fade"
    					</c:when>
					</c:choose>>
			<br/><br/>
			 <form id="signupForm" class="form-horizontal" method="post" action="updateuserinfo">
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
                            <input id="email" type="email" name="email" class="form-control" value="${per.email }">
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
                        <a href="index" type="button" class="btn btn-danger">返回</a>
                    </div>
                </div>
                </div>
            </form>
			
			</div>
			<div id="changepsw"
			<c:choose>
						<c:when test="${usersetting eq true}">
       						class="tab-pane fade " 
    					</c:when>
						<c:when test="${usersetting eq false}">
       			 			class="tab-pane fade in active"
    					</c:when>
					</c:choose>>
						<br/><br/>
				 <form id="changepswForm" class="form-horizontal" method="post" action="changepsw">
                <div class="row">
                    <div class="col-md-8 form-group">
                        <label class="col-md-4 control-label">旧的登录密码:</label>
                        <div class="col-md-6">
                            <input id="oldpsw" type="password" name="oldpsw" class="form-control" placeholder="请输入旧的登录密码">
                        </div>
                    </div>	
                    </div>
                     <div class="row">
                     <div class="col-md-8 form-group">
                        <label class="col-md-4 control-label">新密码:</label>
                        <div class="col-md-6">
                            <input id="newpsw" type="password" name="newpsw" class="form-control" placeholder="请输入新密码">
                        </div>
                    </div>	
                    </div>
                     <div class="row">
                     <div class="col-md-8 form-group">
                        <label class="col-md-4 control-label">再次输入新密码:</label>
                        <div class="col-md-6">
                            <input id="confirm_newpsw" type="password" name="confirm_newpsw" class="form-control" placeholder="请再次输入新密码">
                        </div>
                    </div>	
                    </div>
                    <br/><br/>
					  <div class="row">
                    <div class="col-md-8 text-center">
                        <input type="submit" class="btn btn-primary" value="确认"/>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="index" type="button" class="btn btn-danger">返回</a>
                    </div>
                </div>
            </form>
			</div>
			</div>
		</div>
	</div>
	<%@ include file="footer.jsp"%>
</div>
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/jquery.mockjax.min.js"></script>
<script src="assets/js/test.js"></script>
<script src="assets/js/vtree.js"></script>
<script src="assets/js/base.js"></script>
<script type="text/javascript" src="assets/js/jquery-confirm.js"></script>
<script src="assets/js/jquery.validate.js"></script>
<script src="assets/js/message_zh.js"></script>
<script src="assets/js/validate_expand.js"></script>
<script type="text/javascript">
window.onload = function() {
	var usersetting = "${usersetting}";
	if (usersetting == "true") {
		$("#li_userinfo").addClass('active');
		$("#li_changepsw").removeClass('active');
	} else {
		$("#li_changepsw").addClass('active');
		$("#li_userinfo").removeClass('active');
	}
	 $("#signupForm").validate({
	        rules: {
	   name: 
		   {required:true,
		   nameCheck:true
		   },
	   telephone:
		   {
		   isTel:true
	   },
	   mobilephone:
	   {
		   isPhone:true
	   },
	   email:{ 
		   email:true 
		   }, 
		   pos:{
			   stringCheck:true, 
			   byteRangeLength:[2,15] 
			   }
			   
	  },
	        messages: {
	   name: 
		   {
		   required: "<font color=red>请输入人员姓名</fond>",
		   nameCheck: "姓名只能包括中文字、英文字母"
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
		   email: "请输入一个有效的Email地址" 
		   }, 
		   pos:{
			   stringCheck:"职位只能包括中文字、英文字母、数字和下划线", 
			   byteRangeLength:"职位必须在2-15个字符之间(一个中文字算2个字符)"
			   }
			   
	  }
	    });
	 $("#changepswForm").validate({
	        rules: {
	   oldpsw: 
		   {required:true,
		   minlength: 5
		   },
	   newpsw:
	   {required:true,
		   minlength: 5
		   },
	   confirm_newpsw:
		   {required:true,
			   minlength: 5,
			   equalTo: "#newpsw" 
			   }
			   
	  },
	        messages: {
	        	 oldpsw: 
	  		   {required:"请输入原来的登录密码", 
	  		   minlength: "确认旧密码不能小于5个字符"
	  		   },
	  	   newpsw:
	  	   {required:"请输入新的登录密码",
	  		   minlength: "确认旧密码不能小于5个字符"
	  		   },
	  	   confirm_newpsw:
	  		   {required:"请输入原来的登录密码",
	  			   minlength: "确认旧密码不能小于5个字符",
	  			   equalTo: "两次输入密码不一致不一致" 
	  			   }
			   
	  }
	    });
}
</script>
<%if(attention==3){ %>
<script type="text/javascript">
$.confirm({
		keyboardEnabled: true,
	    title: '更新成功',
	    content: '更新基本信息成功！',
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
<%if(attention==5){ %>
<script type="text/javascript">
$.confirm({
		keyboardEnabled: true,
	    title: '修改成功',
	    content: '修改密码成功！',
	    confirmButtonClass: 'btn-info',
   	    cancelButtonClass: 'btn-danger',
	    autoClose: 'confirm|3000'
	});
</script>
<% session.setAttribute("attention", 0);} %>
</body>
</html>