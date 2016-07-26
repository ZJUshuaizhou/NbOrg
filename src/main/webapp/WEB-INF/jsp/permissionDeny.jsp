<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ include file="head.jsp" %>
    <div id="body" class="container">
      <div class="row">
       <%@ include file="tree.jsp" %>
        <div id="main" class="col-md-8 col-sm-7">
          <div class="panel panel-default">
            <div class="panel-body">
              <div class="article-box">               
                <p>很抱歉，您没有权限进行该操作！</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <%@ include file="footer.jsp" %>
    </div>
    <script src="./assets/js/jquery.mockjax.min.js"></script>
    <!--  <script src="./assets/js/test.js"></script>-->
    <script src="./assets/js/vtree.js"></script>
    <script src="./assets/js/base.js"></script>
    <script type="text/javascript">
    	$("#li_index").addClass('active');
    </script>
  </body>
</html>