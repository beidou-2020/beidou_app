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
          <input type="hidden" name="id" value="${readInfo.id }">	<!-- 主键ID -->
          <div class="layui-form-item">
              <label class="layui-form-label">
                  <span class="x-red">*</span>书名
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="bookName" name="bookName" 
                  class="layui-input" value="${readInfo.bookName }" readonly="readonly">
              </div>
              <div class="layui-form-mid layui-word-aux">
                  <span class="x-red">*</span>
              </div>
          </div>
          <input type="hidden" id="beginTimeValue" value="${readInfo.begintime }">
          <input type="hidden" id="endTimeValue" value="${readInfo.endtime }">
          <div class="layui-form-item">
              <label class="layui-form-label">
                  <span class="x-red"></span>开始时间
              </label>
              <div class="layui-input-inline">
                  <input class="layui-input" placeholder="阅读开始日期" name="begintime"
                  autocomplete="off" id="start" readonly="readonly">
              </div>
              <div class="layui-form-mid layui-word-aux">
                  <span class="x-red"></span>
              </div>
          </div>
          <div class="layui-form-item">
              <label class="layui-form-label">
                  <span class="x-red"></span>结束时间
              </label>
              <div class="layui-input-inline">
                  <input class="layui-input" placeholder="阅读结束日期" name="endtime"
                  autocomplete="off" id="end" readonly="readonly">
              </div>
              <div class="layui-form-mid layui-word-aux">
                  <span class="x-red"></span>
              </div>
          </div>
          <div class="layui-form-item">
              <label class="layui-form-label">
                  <span class="x-red"></span>阅读状态
              </label>
              <div class="layui-input-inline">
                  <select name="readFlag">
                    <option value="3" <c:if test="${readInfo.readFlag == 3}">selected</c:if> >待读</option>
                    <option value="1" <c:if test="${readInfo.readFlag == 1}">selected</c:if>>在读</option>
                    <option value="2" <c:if test="${readInfo.readFlag == 2}">selected</c:if>>读完</option>
                  </select>
              </div>
          </div>
          <div class="layui-form-item">
              <label class="layui-form-label">
                  <span class="x-red"></span>作者
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="author" name="author"
                  class="layui-input" value="${readInfo.author }" readonly="readonly">
              </div>
              <div class="layui-form-mid layui-word-aux">
                  <span class="x-red"></span>
              </div>
          </div>
          <!-- 截图预览区域 -->
          <div class="layui-form-item layui-form-text">
              <label class="layui-form-label">
                  <span class="x-red"></span>预览
              </label>
              <div class="layui-input-block">
              	<c:choose>
              		<c:when test="${readInfo.screenshotName != ''}">
              			<img class="layui-upload-img" width="160px" height="200px" src="${beidou }/${serverPath }/${readInfo.screenshotName} "/>
              		</c:when>
              		<c:otherwise>暂无截图信息</c:otherwise>
              	</c:choose>
              </div>
          </div>
          <div class="layui-form-item layui-form-text">
              <label class="layui-form-label">
                  <span class="x-red"></span>备注
              </label>
              <div class="layui-input-block">
                  <textarea placeholder="请输入内容" id="remark" name="remark" class="layui-textarea" readonly="readonly">${readInfo.remark }</textarea>
              </div>
          </div>
          <!-- <div class="layui-form-item">
              <label class="layui-form-label"><span class="x-red">*</span>角色</label>
              <div class="layui-input-block">
                <input type="checkbox" name="like1[write]" lay-skin="primary" title="超级管理员" checked="">
                <input type="checkbox" name="like1[read]" lay-skin="primary" title="编辑人员">
                <input type="checkbox" name="like1[write]" lay-skin="primary" title="宣传人员" checked="">
              </div>
          </div>
          <div class="layui-form-item">
              <label for="L_pass" class="layui-form-label">
                  <span class="x-red">*</span>密码
              </label>
              <div class="layui-input-inline">
                  <input type="password" id="L_pass" name="pass" required lay-verify="pass"
                  autocomplete="off" class="layui-input">
              </div>
              <div class="layui-form-mid layui-word-aux">
                  6到16个字符
              </div>
          </div>
          <div class="layui-form-item">
              <label for="L_repass" class="layui-form-label">
                  <span class="x-red">*</span>确认密码
              </label>
              <div class="layui-input-inline">
                  <input type="password" id="L_repass" name="repass" required lay-verify="repass"
                  autocomplete="off" class="layui-input">
              </div>
          </div> -->
      </form>
    </div>
<script src="../../js/jquery-3.4.1.min.js"></script>
<script src="../../layui/layui.js"></script>
    <script>
	    layui.use('laydate', function(){
	        var laydate = layui.laydate;
	        //执行一个laydate实例
	        var beginTime = $("#beginTimeValue").val();
	        laydate.render({
	          elem: '#start', //指定元素
	          value: new Date(beginTime)
	        });
	
	        var endTime = $("#endTimeValue").val();
	        //执行一个laydate实例
	        laydate.render({
	          elem: '#end', //指定元素
	          value: new Date(endTime)
	        });
	      });
    
        layui.use(['form','layer'], function(){
            $ = layui.jquery;
          var form = layui.form
          ,layer = layui.layer;
        
          //自定义验证规则
          /* form.verify({
            nikename: function(value){
              if(value.length < 5){
                return '昵称至少得5个字符啊';
              }
            }
            ,pass: [/(.+){6,12}$/, '密码必须6到12位']
            ,repass: function(value){
                if($('#L_pass').val()!=$('#L_repass').val()){
                    return '两次密码不一致';
                }
            }
          }); */

          
        });
    </script>
  </body>

</html>