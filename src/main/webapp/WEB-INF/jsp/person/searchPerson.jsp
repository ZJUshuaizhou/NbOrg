<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../head.jsp"%>
<div id="body" class="container">
	<div class="row">
		<%@ include file="../tree.jsp"%>
        <%
        int pageTotal=(Integer)request.getAttribute("pageTotal");
    	int pageIndex=(Integer)request.getAttribute("pageIndex");
        %>
        <div class="col-md-8">
            <form class="form-inline" action="person/search">
                <input type="text" name="name" class="form-control"  placeholder="人员名称" >
                <input type="text" name="depname" class="form-control"  placeholder="所在部门" >
                <button type="submit" class="btn btn-warning">搜索</button>
                <a href="person/add" type="button" class="btn btn-primary glyphicon-plus"> 添加人员</a>
            </form>
            <br/><br/>
            <table class="table table-bordered table-hover text-center">
                <thead>
                <tr>
                    <th class="text-center">序号</th>
                    <th class="text-center">姓名</th>
                    <th class="text-center">性别</th>
                    <th class="text-center">固定电话</th>
                    <th class="text-center">手机</th>
                    <th class="text-center">邮箱</th>
                    <th class="text-center">所在部门</th>
                    <th class="text-center">职务</th>
                    <th class="text-center">操作</th>
                </tr>
                </thead>
                <tbody>
                 <c:forEach items="${requestScope.perlist}"  var="per"  varStatus="id">
                <tr>
                 <td>${per.samePersonId}</td>
                    <td>${per.name }</td>
                    <td>${per.gender }</td>
                    <td>${per.telephone }</td>
                    <td>${per.mobilephone }</td>
                    <td>${per.email }</td>
                    <td>${per.dep}</td>
                    <td>${per.pos}</td>
                    <td>
                        <div class="btn-group">
                            <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
                                    aria-haspopup="true" aria-expanded="false">
                                操作 <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" style="min-width:60px">
                                <li><a href="person/detail/${per.id }?depsn=${per.depsn}">详情</a></li>
                                 <c:if test="${adminFlag==1&&per.isEdit==1 }">
                                <li><a href="person/edit/${per.id }?depsn=${per.depsn}">编辑</a></li>
                                  <li><a onclick="del(${per.id })">删除</a></li>
                                  <li><a href="person/restpassword/${per.id }">重置密码</a></li>
                               </c:if>
                            </ul>
                        </div>
                    </td>
                </tr>
                </c:forEach>
                </tbody>
            </table>
           <div>查询到${total_mine }位符合条件的人员/共${pageTotal }页</div>
             <c:if test="${pageTotal>1}">
            <nav class="text-center">
                <ul class="pagination">
                <c:choose>
                    <c:when test="${pageIndex==1}">
                     <li class="disabled"><a href="javascript:void(0);">首页</a></li>
                    <li class="disabled"><a href="javascript:void(0);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
                    </c:when>
                  <c:otherwise>
                   <li><a href="person/search?pageNo_mine=1&name=${name}&depname=${depname}">首页</a></li>
                  <li><a href="person/search?pageNo_mine=${pageIndex-1 }&name=${name}&depname=${depname}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
                  </c:otherwise>
                </c:choose>  
                <c:if test="${pageTotal<6 }">
                <%for(int i=1;i<=pageTotal;i++) {
                if(pageIndex==i) {%>
                <li class="active"><a href="person/search?pageNo_mine=<%=i%>&name=${name}&depname=${depname}"><%=i%><span class="sr-only">(current)</span></a></li>
                <%} else {%>
                    <li><a href="person/search?pageNo_mine=<%=i%>&name=${name}&depname=${depname}"><%=i%></a></li>
                   <% }%>
                    <% }%>
                      </c:if>
                    <c:if test="${(pageTotal>=6)&&(pageIndex>2)&&(pageIndex<pageTotal-2) }">
                <li><a href="person/search?pageNo_mine=${pageIndex-2 }&name=${name}&depname=${depname}">${pageIndex-2 }</a></li>
                <li><a href="person/search?pageNo_mine=${pageIndex-1 }&name=${name}&depname=${depname}">${pageIndex-1 }</a></li>
               <li class="active"><a href="person/search?pageNo_mine=${pageIndex }&name=${name}&depname=${depname}">${pageIndex }<span class="sr-only">(current)</span></a></li>
               <li><a href="person/search?pageNo_mine=${pageIndex+1 }&name=${name}&depname=${depname}">${pageIndex+1 }</a></li>
               <li><a href="person/search?pageNo_mine=${pageIndex+2 }&name=${name}&depname=${depname}">${pageIndex+2 }</a></li>
                </c:if>
                 <c:if test="${(pageTotal>=6)&&(pageIndex<=2) }">
              <%for(int i=1;i<=5;i++) {
                if(pageIndex==i) {%>
                <li class="active"><a href="person/search?pageNo_mine=<%=i%>&name=${name}&depname=${depname}"><%=i%><span class="sr-only">(current)</span></a></li>
                <%} else {%>
                    <li><a href="person/search?pageNo_mine=<%=i%>&name=${name}&depname=${depname}"><%=i%></a></li>
                   <% }%>
                    <% }%>
                </c:if>
                 <c:if test="${(pageTotal>=6)&&(pageIndex>=pageTotal-2) }">
              <%for(int i=pageTotal-4;i<=pageTotal;i++) {
                if(pageIndex==i) {%>
                <li class="active"><a href="person/search?pageNo_mine=<%=i%>&name=${name}&depname=${depname}"><%=i%><span class="sr-only">(current)</span></a></li>
                <%} else {%>
                    <li><a href="person/search?pageNo_mine=<%=i%>&name=${name}&depname=${depname}"><%=i%></a></li>
                   <% }%>
                    <% }%>
                </c:if>
                    <c:choose>
                    <c:when test="${pageIndex==pageTotal }"> 
                    <li class="disabled"><a href="javascript:void(0);" aria-label="Previous"><span aria-hidden="true">&raquo;</span></a></li>
                    <li class="disabled"><a href="javascript:void(0);">尾页</a></li>
                    </c:when>
                  <c:otherwise>
                  <li><a href="person/search?pageNo_mine=${pageIndex+1 }&name=${name}&depname=${depname}" aria-label="Previous"><span aria-hidden="true">&raquo;</span></a></li>
                  <li><a href="person/search?pageNo_mine=${pageTotal }&name=${name}&depname=${depname}">尾页</a></li>
                  </c:otherwise>
                </c:choose>  
                </ul>
            </nav>
            </c:if>
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
</body>
</html>