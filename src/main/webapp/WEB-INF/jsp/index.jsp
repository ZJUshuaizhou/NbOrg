<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ include file="head.jsp" %>
    <div id="body" class="container">
      <div class="row">
       <%@ include file="tree.jsp" %>
        <div id="main" class="col-md-8 col-sm-7">
          <div class="panel panel-default">
            <div class="panel-body">
              <div class="article-box">
                <p>宁波，简称甬，浙江省第二大城市，副省级城市，计划单列市，有制订地方性法规权限的较大的市。是中华人民共和国文化部批准的全国历史文化名城，长三角五大区域中心之一，长三角南翼经济中心，浙江省经济中心，现代化国际港口城市，连续三次蝉联中国文明城市。</p>
                <p>宁波地处东南沿海，位于中国大陆海岸线中段，长江三角洲南翼，东有舟山群岛为天然屏障，北濒杭州湾，西接绍兴市的嵊州、新昌、上虞，南临三门湾，并与台州的三门、天台相连。</p>
              </div>
              <div class="intro-img"><img src="./assets/img/u141.png" alt="宁波市人民政府机构设置" class="img-responsive"></div>
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