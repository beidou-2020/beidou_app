<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="taglibs.jsp" %>
<html>
<!-- Head -->
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>顶部</title>
  <link rel="stylesheet" href="../css/layui.css">
</head>

<!-- Body -->
<body>
	<ul class="layui-nav layui-nav-tree"  lay-filter="test">
		<%--<li class="layui-nav-item layui-nav-itemed"><a href="${beidou }/index">个人控制台</a></li>--%>
	   <li class="layui-nav-item layui-nav-itemed"><a href="${beidou }/indexByConcurrent">个人控制台</a></li>	<!-- 默认打开当前模块 -->
	   <li class="layui-nav-item">
		 <a class="#" href="javascript:;">阅读管理</a>
		 <dl class="layui-nav-child">
		   <dd><a href="${beidou}/read/list">阅读列表</a></dd>
		 </dl>
	   </li>
	   <li class="layui-nav-item">
		 <a class="#" href="javascript:;">技能管理</a>
		 <dl class="layui-nav-child">
		   <dd><a href="${beidou}/study/list">技能列表</a></dd>
		 </dl>
	   </li>
	   <li class="layui-nav-item">
		 <a class="#" href="javascript:;">用户管理</a>
		 <dl class="layui-nav-child">
		   <dd><a href="${beidou}/user/list">用户列表</a></dd>
		   <%--<dd><a href="javascript:;">僵尸列表</a></dd>--%>
		 </dl>
	   </li>
		<li class="layui-nav-item">
			<a class="#" href="javascript:;">系统管理</a>
			<dl class="layui-nav-child">
				<dd><a href="${beidou}/recycle/dataRecycle">回收站</a></dd>
				<%--<dd><a href="javascript:;">僵尸列表</a></dd>--%>
			</dl>
		</li>
     </ul>
</body>
<!-- //Body -->

</html>