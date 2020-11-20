<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>北斗重生</title>
  <link rel="stylesheet" href="../css/xadmin.css">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo">北斗重生</div>
    <!-- 头部区域（顶层水平导航栏） -->
	<%@ include file="../common/top.jsp" %>
  </div>
  
  <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（垂直导航栏） -->
      <%@ include file="../common/left.jsp" %>
    </div>
  </div>
  
  <div class="layui-body x-body">
    <!-- 内容主体区域 -->
    <fieldset class="layui-elem-field">
        <legend>数据统计</legend>
        <div class="layui-field-box">
            <div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-card-body">
                        <div class="layui-carousel x-admin-carousel x-admin-backlog" lay-anim="" lay-indicator="inside" lay-arrow="none" style="width: 100%; height: 90px;">
                            <div carousel-item="">
                                <ul class="layui-row layui-col-space10 layui-this">
                                    <li class="layui-col-xs2">
                                        <a href="${biedou }/read/list" class="x-admin-backlog-body">
                                            <h3>累计阅读量</h3>
                                            <p>
                                                <cite>${readNum }</cite>
                                            </p>
                                        </a>
                                    </li>
                                    <li class="layui-col-xs2">
                                        <a href="${biedou }/study/list" class="x-admin-backlog-body">
                                            <h3>累计计划数</h3>
                                            <p>
                                                <cite>${studyNum }</cite>
                                            </p>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </fieldset>
  
    <div style="padding: 15px;" class="layui-bg-gray">
    	<blockquote class="layui-elem-quote">本月计划结束的学习模块列表如下：</blockquote>
		<table class="layui-table" lay-even lay-skin="line" lay-size="lg">
		  <colgroup>
			<col width="150">
			<col width="200">
			<col>
		  </colgroup>
		  <thead>
			<tr>
			  <th>标题</th>
			  <th>开始时间</th>
			  <th>模块项</th>
			</tr> 
		  </thead>
		  <tbody>
		  <c:forEach var="item" items="${endStudyList}">
		  	<tr>
			  <td>${item.title }</td>
			  <td>${item.planBegintime }</td>
			  <td>${item.items }</td>
			</tr>
		  </c:forEach>
		  </tbody>
		</table>
		
		<br/>
		<blockquote class="layui-elem-quote">本年度阅读数据简报如下：</blockquote>
		<ul class="layui-timeline">
		<c:forEach var="item" items="${todayYearReadList }">
		  <li class="layui-timeline-item">
			<i class="layui-icon layui-timeline-axis">&#xe63f;</i>
			<div class="layui-timeline-content layui-text">
			  <h3 class="layui-timeline-title">${item.begintime } ~ ${item.endtime }</h3>
			  <p>
				书名：&laquo;${item.bookName }&raquo;
				<br>作者：${item.author }
				<br>备注：${item.remark }
			  </p>
			</div>
		  </li>
		</c:forEach>
		</ul>
	</div>
  </div>

    <!-- 页脚 -->
    <%@ include file="../common/foot.jsp" %>
</div>
<script src="../js/layui.js"></script>
<script>
//JavaScript代码区域
layui.use('element', function(){
  var element = layui.element;
  
});
</script>
</body>
</html>