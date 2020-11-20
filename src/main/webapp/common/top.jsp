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
	<!-- 左半部分 -->
    <ul class="layui-nav layui-layout-left">
      <li class="layui-nav-item"><a href="">系统概述</a></li>
      <li class="layui-nav-item">
		<a href="javascript:;">解决方案</a>
		<dl class="layui-nav-child"> <!-- 二级菜单 -->
		  <dd><a href="">系统问题</a></dd>
		  <dd><a href="">业务问题</a></dd>
		  <dd><a href="">个人建议</a></dd>
		</dl>
	  </li>
    </ul>
	<!-- 右半部分 -->
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item">
        <a href="javascript:;">
          <img src="../image/zxz.jpg" class="layui-nav-img">
          admin
        </a>
        <dl class="layui-nav-child">
          <dd><a href="">基本资料</a></dd>
          <dd><a href="">安全设置</a></dd>
        </dl>
      </li>
      <li class="layui-nav-item">
	  <a href="">注销</a>
	  </li>
    </ul>
</body>
<!-- //Body -->

</html>