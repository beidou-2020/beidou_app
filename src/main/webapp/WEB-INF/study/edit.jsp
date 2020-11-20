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
          <input type="hidden" name="id" value="${studyInfo.id }">	<!-- 主键ID -->
          <div class="layui-form-item">
              <label for="title" class="layui-form-label">
                  <span class="x-red">*</span>主题
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="title" name="title" required lay-verify="required"
                  autocomplete="off" class="layui-input" value="${studyInfo.title }">
              </div>
              <div class="layui-form-mid layui-word-aux">
                  <span class="x-red">*</span>
              </div>
          </div>
          <input type="hidden" id="beginTimeValue" value="${studyInfo.planBegintime}">
          <input type="hidden" id="endTimeValue" value="${studyInfo.planEndtime }">
          <div class="layui-form-item">
              <label class="layui-form-label">
                  <span class="x-red">*</span>开始时间
              </label>
              <div class="layui-input-inline">
                  <input class="layui-input" placeholder="计划开始日期" name="planBegintime" required lay-verify="required"
                  autocomplete="off" id="start" >
              </div>
              <div class="layui-form-mid layui-word-aux">
                  <span class="x-red">*</span>
              </div>
          </div>
          <div class="layui-form-item">
              <label class="layui-form-label">
                  <span class="x-red">*</span>截止时间
              </label>
              <div class="layui-input-inline">
                  <input class="layui-input" placeholder="计划截止日期" name="planEndtime" required lay-verify="required"
                  autocomplete="off" id="end">
              </div>
              <div class="layui-form-mid layui-word-aux">
                  <span class="x-red">*</span>
              </div>
          </div>
          <div class="layui-form-item layui-form-text">
              <label class="layui-form-label">
                  <span class="x-red"></span>学习项目
              </label>
              <div class="layui-input-block">
                  <textarea placeholder="请输入内容(建议每项之间用逗号分隔)" id="items" name="items" class="layui-textarea">${studyInfo.items }</textarea>
              </div>
          </div>
          <input type="hidden" id="items_2" value="${studyInfo.items }">
          <div class="layui-form-item layui-form-text">
              <label class="layui-form-label">
                  <span class="x-red"></span>备注
              </label>
              <div class="layui-input-block">
                  <textarea placeholder="请输入内容" id="remark" name="remark" class="layui-textarea">${studyInfo.remark }</textarea>
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

          //监听提交
          form.on('submit(add)', function(data){
            console.log(data);
            //发异步提交数据
            //alert(JSON.stringify(data.field));
            $.ajax({
  				type: "post",
  				url: "${beidou }/study/update",
  				data: JSON.stringify(data.field),
  				contentType: "application/json;charset=utf-8",
  				success: function(data) {
  					//alert(data);
  				}
  			});
            layer.alert("更新成功", {icon: 6},function () {
                // 获得frame索引
                var index = parent.layer.getFrameIndex(window.name);
                //关闭当前frame
                parent.layer.close(index);
                window.parent.location.reload();//刷新父页面
            });
            return false;
          });
          
          
        });
    </script>
  </body>

</html>