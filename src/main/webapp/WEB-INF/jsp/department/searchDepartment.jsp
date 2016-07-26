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
            <form class="form-inline" action="department/search">
                <input type="text" name="name" class="form-control"  placeholder="部门名称" >
                <input type="text" name="pdep" class="form-control"  placeholder="上级部门" >
                <button type="submit" class="btn btn-warning">搜索</button>
                  <c:if test="${adminFlag==1 }">
                <a href="department/add" type="button" class="btn btn-primary glyphicon-plus"> 添加部门</a>
                </c:if>
            </form>
            <br/><br/>
            <table class="table table-bordered table-hover text-center">
                <thead>
                <tr>
                    <th class="text-center">序号</th>
                    <th class="text-center">名称</th>
                    <th class="text-center">联系人</th>
                    <th class="text-center">联系方式</th>
                    <th class="text-center">上级部门</th>
                    <th class="text-center">操作</th>
                </tr>
                </thead>
                <tbody>
                  <c:forEach items="${requestScope.deplist}" var="dep" varStatus="id">
                <tr>
                    <td>${id.index+1 }</td>
                    <td>${dep.name }</td>
                    <td>${dep.contactPerson }</td>
                    <td>${dep.contactNumber }</td>
                    <td>${dep.parentdep }</td>
                    <td>
                        <div class="btn-group">
                            <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
                                    aria-haspopup="true" aria-expanded="false">
                                操作 <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" style="min-width:60px">
                                <li><a href="department/detail/${dep.id }">详情</a></li>
                                <c:if test="${adminFlag==1&&dep.isEdit==1}">
                                <li><a href="department/edit/${dep.id }">编辑</a></li>
                                <li><a onclick="del(${dep.id })">删除</a></li>
                                </c:if>
                            </ul>
                        </div>
                    </td>
                </tr>
                </c:forEach>
                </tbody>
            </table>
             <div>查询到${total_mine }条记录/共${pageTotal }页</div>
             <c:if test="${pageTotal>1}">
            <nav class="text-center">
                <ul class="pagination">
                <c:choose>
                    <c:when test="${pageIndex==1 }">
                     <li class="disabled"><a href="javascript:void(0);">首页</a></li>
                    <li class="disabled"><a href="javascript:void(0);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
                    </c:when>
                  <c:otherwise>
                   <li><a href="department/search?pageNo_mine=1&name=${name}&pdep=${pdep}">首页</a></li>
                  <li><a href="department/search?pageNo_mine=${pageIndex-1 }&name=${name}&pdep=${pdep}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
                  </c:otherwise>
                </c:choose>  
                <c:if test="${pageTotal<6 }">
                <%for(int i=1;i<=pageTotal;i++) {
                if(pageIndex==i) {%>
                <li class="active"><a href="department/search?pageNo_mine=<%=i%>&name=${name}&pdep=${pdep}"><%=i%><span class="sr-only">(current)</span></a></li>
                <%} else {%>
                    <li><a href="department/search?pageNo_mine=<%=i%>&name=${name}&pdep=${pdep}"><%=i%></a></li>
                   <% }%>
                    <% }%>
                      </c:if>
                    <c:if test="${(pageTotal>=6)&&(pageIndex>2)&&(pageIndex<pageTotal-2) }">
                <li><a href="department/search?pageNo_mine=${pageIndex-2 }&name=${name}&pdep=${pdep}">${pageIndex-2 }</a></li>
                <li><a href="department/search?pageNo_mine=${pageIndex-1 }&name=${name}&pdep=${pdep}">${pageIndex-1 }</a></li>
               <li class="active"><a href="department/search?pageNo_mine=${pageIndex }&name=${name}&pdep=${pdep}">${pageIndex }<span class="sr-only">(current)</span></a></li>
               <li><a href="department/search?pageNo_mine=${pageIndex+1 }&name=${name}&pdep=${pdep}">${pageIndex+1 }</a></li>
               <li><a href="department/search?pageNo_mine=${pageIndex+2 }&name=${name}&pdep=${pdep}">${pageIndex+2 }</a></li>
                </c:if>
                 <c:if test="${(pageTotal>=6)&&(pageIndex<=2) }">
              <%for(int i=1;i<=5;i++) {
                if(pageIndex==i) {%>
                <li class="active"><a href="department/search?pageNo_mine=<%=i%>&name=${name}&pdep=${pdep}"><%=i%><span class="sr-only">(current)</span></a></li>
                <%} else {%>
                    <li><a href="department/search?pageNo_mine=<%=i%>&name=${name}&pdep=${pdep}"><%=i%></a></li>
                   <% }%>
                    <% }%>
                </c:if>
                 <c:if test="${(pageTotal>=6)&&(pageIndex>=pageTotal-2) }">
              <%for(int i=pageTotal-4;i<=pageTotal;i++) {
                if(pageIndex==i) {%>
                <li class="active"><a href="department/search?pageNo_mine=<%=i%>&name=${name}&pdep=${pdep}"><%=i%><span class="sr-only">(current)</span></a></li>
                <%} else {%>
                    <li><a href="department/search?pageNo_mine=<%=i%>&name=${name}&pdep=${pdep}"><%=i%></a></li>
                   <% }%>
                    <% }%>
                </c:if>
                    <c:choose>
                    <c:when test="${pageIndex==pageTotal }"> 
                    <li class="disabled"><a href="javascript:void(0);" aria-label="Previous"><span aria-hidden="true">&raquo;</span></a></li>
                    <li class="disabled"><a href="javascript:void(0);">尾页</a></li>
                    </c:when>
                  <c:otherwise>
                  <li><a href="department/search?pageNo_mine=${pageIndex+1 }&name=${name}&pdep=${pdep}" aria-label="Previous"><span aria-hidden="true">&raquo;</span></a></li>
                  <li><a href="department/search?pageNo_mine=${pageTotal }&name=${name}&pdep=${pdep}">尾页</a></li>
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
   	    	window.location.href="person/del/"+depid;
   	    }
   	});
    }
	$("#li_department").addClass('active');
</script>
</body>
</html>