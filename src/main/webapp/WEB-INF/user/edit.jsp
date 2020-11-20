<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../../common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
  
  <head>
    <meta charset="UTF-8">
    <title>北斗重生</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <link rel="stylesheet" href="../../css/layui.css">
	<link rel="stylesheet" href="../../css/font.css">
	<link rel="stylesheet" href="../../css/xadmin.css">
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
      <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
      <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  
  <body>
    <div class="x-body">
        <form class="layui-form">
          <input type="hidden" name="id" value="${userInfo.id }">	<!-- 主键ID -->
            <div class="layui-form-item">
                <label class="layui-form-label">
                    <span class="x-red">*</span>账号
                </label>
                <div class="layui-input-inline">
                    <!-- required lay-verify="required"：前端校验非空项
                           autocomplete="off"：是否开启自动提示功能，就是将提示历史输入过的记录值。
                     -->
                    <input type="text" id="account" name="account" required lay-verify="required"
                           autocomplete="off" class="layui-input" value="${userInfo.account}">
                </div>
                <div class="layui-form-mid layui-word-aux">
                    <span class="x-red">*</span>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                    <span class="x-red"></span>昵称
                </label>
                <div class="layui-input-inline">
                    <!-- required lay-verify="required"：前端校验非空项
                           autocomplete="off"：是否开启自动提示功能，就是将提示历史输入过的记录值。
                     -->
                    <input type="text" id="name" name="name" required lay-verify="required"
                           autocomplete="off" class="layui-input" value="${userInfo.name}">
                </div>
                <div class="layui-form-mid layui-word-aux">
                    <span class="x-red"></span>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                    <span class="x-red"></span>性别
                </label>
                <div class="layui-input-inline">
                    <select name="gender">
                        <option value="0" <c:if test="${userInfo.gender == 0}">selected</c:if>>女</option>
                        <option value="1" <c:if test="${userInfo.gender == 1}">selected</c:if>>男</option>
                        <option value="2" <c:if test="${userInfo.gender == 2}">selected</c:if>>保密</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                    <span class="x-red"></span>联系方式
                </label>
                <div class="layui-input-inline">
                    <!-- required lay-verify="required"：前端校验非空项
                           autocomplete="off"：是否开启自动提示功能，就是将提示历史输入过的记录值。
                     -->
                    <input type="text" id="telephone" name="telephone"
                           autocomplete="off" class="layui-input" value="${userInfo.telephone}">
                </div>
                <div class="layui-form-mid layui-word-aux">
                    <span class="x-red"></span>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                    <span class="x-red"></span>用户类型
                </label>
                <div class="layui-input-inline">
                    <select name="userType">
                        <option value="0" <c:if test="${userInfo.userType == 0}">selected</c:if>>管理员</option>
                        <option value="1" <c:if test="${userInfo.userType == 1}">selected</c:if>>普通</option>
                    </select>
                </div>
            </div>
          <div class="layui-form-item">
              <label class="layui-form-label">
              </label>
              <button  class="layui-btn" lay-filter="add" lay-submit="">
                 编辑
              </button>
          </div>
      </form>
    </div>
<script src="../../js/jquery-3.4.1.min.js"></script>
<script src="../../layui/layui.js"></script>
    <script>
        layui.use(['form','layer'], function(){
            $ = layui.jquery;
          var form = layui.form
          ,layer = layui.layer;

          //监听提交
          form.on('submit(add)', function(data){
            console.log(data);
            //发异步提交数据
            //alert(JSON.stringify(data.field));
            $.ajax({
  				type: "post",
  				url: "${beidou }/user/update",
  				data: JSON.stringify(data.field),
  				contentType: "application/json;charset=utf-8",
  				success: function(data) {
  					if (data.code == 0){
                        layer.alert("更新成功", {icon: 6},function () {
                            // 获得frame索引
                            var index = parent.layer.getFrameIndex(window.name);
                            //关闭当前frame
                            parent.layer.close(index);
                            window.parent.location.reload();//刷新父页面
                        });
                    }else{
                        layer.alert("服务器异常", {icon: 2},function () {
                            // 获得frame索引
                            var index = parent.layer.getFrameIndex(window.name);
                            //关闭当前frame
                            parent.layer.close(index);
                            window.parent.location.reload();//刷新父页面
                        });
                    }
  				}
  			});
            return false;
          });
        });
    </script>
  </body>

</html>