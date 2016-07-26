 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

%>
<!DOCTYPE html>
<html >
 <head>
  	<base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="./assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="./assets/css/bootstrap-select.css" rel="stylesheet">
    <!--<link href="./assets/css/bootstrap-gtreetable.min.css" rel="stylesheet">-->
    <link href="./assets/css/vtree.css" rel="stylesheet">
    <link href="./assets/css/base.css" rel="stylesheet">
    <link href="./assets/css/jquery-confirm.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries--><!--[if lt IE 9]>
    <script src="./assets/js/html5shiv.min.js"></script><![endif]-->
    <script src="assets/js/jquery.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/bootstrap-select.js"></script>
  </head>
  <body>
    <div id="header">
      <nav class="navbar navbar-default">
        <div class="container">
          <!-- Brand and toggle get grouped for better mobile display-->
          <div class="navbar-header">
            <button type="button" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false" class="navbar-toggle collapsed"><span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button>
           <a href="index" class="navbar-brand">统一身份与应用管理系统</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling-->
            <div id="bs-example-navbar-collapse-1" class="collapse navbar-collapse">
                <ul id="index_nav" class="nav navbar-nav">
                    <li id="li_index"><a href="index">首页 </a></li>
                    <c:if test="${adminFlag==1 }">
                    <li id="li_department"><a href="department/list">部门管理</a></li>
                    </c:if>
                     <c:if test="${adminFlag==0 }">
                    <li id="li_department" ><a href="department/list">部门查看</a></li>
                    </c:if>
                    <c:if test="${adminFlag==1 }">
                    <li id="li_person"><a href="person/list">人员管理</a></li>
                    </c:if>
                     <c:if test="${adminFlag==0 }">
                    <li id="li_person"><a href="person/list">人员查看</a></li>
                    </c:if>
                    <li id="li_application"><a href="application/list">应用管理</a></li>
                </ul>
                 <c:if test="${user.deps.size()>1 }">
                 <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown"><a href="#" data-toggle="dropdown" role="button" aria-haspopup="true"
                                            aria-expanded="false" class="dropdown-toggle">切换管理部门</a>
                        <ul class="dropdown-menu">
                        <c:forEach items="${user.deps}" var="d" varStatus="id">
                            <li <c:if test="${d.name==dep.name }">class="active"</c:if>><a href="department/changedep/${d.id }">${d.name }</a></li>
                         </c:forEach>
                        </ul>
                    </li>
                </ul>
                </c:if>
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown"><a href="#" data-toggle="dropdown" role="button" aria-haspopup="true"
                                            aria-expanded="false" class="dropdown-toggle">欢迎您，${user.name }<span
                            class="glyphicon glyphicon-user left15 right15"></span><span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="userinfo">个人信息</a></li>
                            <li><a href="usersetting">设置</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="logout">注销</a></li>
                </ul>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    </div>