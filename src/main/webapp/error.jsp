<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
 <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

%>
<html lang="en-US"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>ERROR</title>
    <link rel="stylesheet" type="text/css" href="assets/files/cmstop-error.css" media="all">
</head>
<body class="body-bg">
<div class="main">
    <p class="title">非常抱歉，出现未知错误！3秒后请重新登陆！</p>
    <p class="title">3秒后跳转至登陆界面</p>
    <!-- <a href="/NbOrg/login.jsp" class="btn">返回网站首页</a> -->
</div>
<script>
window.onload = function(){
	setTimeout(function(){
		window.location.href = "/NbOrg/login.jsp";
	},3000);
}
</script>
</body></html>