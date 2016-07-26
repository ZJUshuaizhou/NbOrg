<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
    <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

%>
<html lang="en-US"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>404ERROR</title>
    <link rel="stylesheet" type="text/css" href="./assets/files/cmstop-error.css" media="all">
</head>
<body class="body-bg">
<div class="main">
    <p class="title">非常抱歉，您要查看的页面没有办法找到</p>
    <a href="/NbOrg/index" class="btn">返回网站首页</a>
</div>
</body></html>