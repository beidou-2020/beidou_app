<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../../common/taglibs.jsp" %>
<html>
  <head>
    <meta charset="UTF-8">
    <title>北斗重生</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <!-- <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" /> -->
    <link rel="stylesheet" href="../../css/layui.css">
    <link rel="stylesheet" href="../../css/font.css">
  	<link rel="stylesheet" href="../../css/xadmin.css">
    
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
      <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
      <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <style type="text/css">
		.a-upload {
		    padding: 4px 10px;
		    height: 20px;
		    line-height: 20px;
		    position: relative;
		    cursor: pointer;
		    color: #888;
		    background: #fafafa;
		    border: 1px solid #ddd;
		    border-radius: 4px;
		    overflow: hidden;
		    display: inline-block;
		    *display: inline;
		    *zoom: 1
		}
		
		.a-upload  input {
		    position: absolute;
		    font-size: 100px;
		    right: 0;
		    top: 0;
		    opacity: 0;
		    filter: alpha(opacity=0);
		    cursor: pointer
		}
		
		.a-upload:hover {
		    color: #444;
		    background: #eee;
		    border-color: #ccc;
		    text-decoration: none
		}
    </style>
    
  </head>
  
  <body>
    <div class="x-body">
        <form class="layui-form" id="readFileFrom">
          <div class="layui-form-item">
              <label class="layui-form-label">
                  <span class="x-red">*</span>账号
              </label>
              <div class="layui-input-inline">
              <!-- required lay-verify="required"：前端校验非空项
              	   autocomplete="off"：是否开启自动提示功能，就是将提示历史输入过的记录值。
               -->
                  <input type="text" id="account" name="account" required lay-verify="required"
                  autocomplete="off" class="layui-input">
              </div>
              <div class="layui-form-mid layui-word-aux">
                  <span class="x-red">*</span>
              </div>
          </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                    <span class="x-red">*</span>密码
                </label>
                <div class="layui-input-inline">
                    <input type="password" id="password" name="password" required lay-verify="pass"
                           autocomplete="off" class="layui-input">
                </div>
                <div class="layui-form-mid layui-word-aux">
                    <span class="x-red">*</span>
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
                           autocomplete="off" class="layui-input">
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
                    <option value="0">女</option>
                    <option value="1">男</option>
                    <option value="2" selected>保密</option>
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
                  autocomplete="off" class="layui-input">
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
                        <option value="0" >管理员</option>
                        <option value="1" >普通</option>
                    </select>
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
           -->
          <div class="layui-form-item">
              <label class="layui-form-label">
              </label>
              <button class="layui-btn" lay-filter="add" lay-submit="">增加</button>
          </div>
      </form>
    </div>
<script src="../../layui/layui.js"></script>
<script type="text/javascript" src="../../js/xadmin.js"></script>

    <script>
        layui.use(['form','layer'], function(){
            $ = layui.jquery;
          var form = layui.form
          ,layer = layui.layer;
        
          //自定义验证规则
           form.verify({
               name: function(value){
              if(value.length < 5){
                return '昵称至少得5个字符啊';
              }
            }
            ,password: [/(.+){6,12}$/, '密码必须6到16位']
            ,repass: function(value){
                if($('#password').val()!=$('#L_repass').val()){
                    return '两次密码不一致';
                }
            }
          });

          //监听提交
          form.on('submit(add)', function(data){
            console.log(JSON.stringify(data.field));
            // 发异步提交数据
            $.ajax({
	  				type: "post",
	  				url: "${beidou}/user/add",
                    data: JSON.stringify(data.field),
                    contentType: "application/json;charset=utf-8",
	  				success: function(data) {
	  				    //alert(data.code);
                        if (data.code == 0) {
                            layer.alert("增加成功", {icon: 6},function () {
                                // 获得frame索引
                                var index = parent.layer.getFrameIndex(window.name);
                                //关闭当前frame
                                parent.layer.close(index);
                                window.parent.location.reload();//刷新父页面
                            });
                        }else {
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