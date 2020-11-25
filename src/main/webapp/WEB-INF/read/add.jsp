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
                  <span class="x-red">*</span>书名
              </label>
              <div class="layui-input-inline">
              <!-- required lay-verify="required"：前端校验非空项
              	   autocomplete="off"：是否开启自动提示功能，就是将提示历史输入过的记录值。
               -->
                  <input type="text" id="bookName" name="bookName" required lay-verify="required"
                  autocomplete="off" class="layui-input">
              </div>
              <div class="layui-form-mid layui-word-aux">
                  <span class="x-red">*</span>
              </div>
          </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                    <span class="x-red">*</span>类别
                </label>
                <div class="layui-input-inline">
                    <select name="category">
                        <option value="1">养性</option>
                        <option value="2">专业</option>
                    </select>
                </div>
                <div class="layui-form-mid layui-word-aux">
                    <span class="x-red">*</span>
                </div>
            </div>
          <div class="layui-form-item">
              <label class="layui-form-label">
                  <span class="x-red"></span>开始时间
              </label>
              <div class="layui-input-inline">
                  <input class="layui-input" placeholder="阅读开始日期" name="begintime" 
                  autocomplete="off" id="start">
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
                   autocomplete="off" id="end">
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
                    <option value="3">待读</option>
                    <option value="1">在读</option>
                    <option value="2">读完</option>
                  </select>
              </div>
          </div>
          <div class="layui-form-item">
              <label class="layui-form-label">
                  <span class="x-red">*</span>作者
              </label>
              <div class="layui-input-inline">
              <!-- required lay-verify="required"：前端校验非空项
              	   autocomplete="off"：是否开启自动提示功能，就是将提示历史输入过的记录值。
               -->
                  <input type="text" id="author" name="author"
                  autocomplete="off" class="layui-input">
              </div>
              <div class="layui-form-mid layui-word-aux">
                  <span class="x-red">*</span>
              </div>
          </div>
          <div class="layui-form-item layui-form-text">
              <label class="layui-form-label">
                  <span class="x-red"></span>备注
              </label>
              <div class="layui-input-block">
                  <textarea placeholder="请输入内容" id="remark" name="remark" class="layui-textarea"></textarea>
              </div>
          </div>
          <div class="layui-form-item layui-form-text">
              <label class="layui-form-label">
                  <span class="x-red"></span>附件
              </label>
              <div class="layui-input-block">
                  <!-- <input type="file" name="readPic"/> --><!-- 单文件上传 -->
                  <a href="javascript:;" class="a-upload">
                  	<input type="file" name="readPicFiles" multiple="multiple"/>选择阅读截图<!-- 多文件上传 -->
                  </a>
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
              <button class="layui-btn" lay-filter="add" lay-submit="">增加</button>
          </div>
      </form>
    </div>
<script src="../../layui/layui.js"></script>
<script type="text/javascript" src="../../js/xadmin.js"></script>

    <script>
	    layui.use('laydate', function(){
	        var laydate = layui.laydate;
	        //执行一个laydate实例
	        laydate.render({
	          elem: '#start' //指定元素
	        });
	
	        //执行一个laydate实例
	        laydate.render({
	          elem: '#end' //指定元素
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
            console.log(JSON.stringify(data.field));
            // 发异步提交数据
            // 因为表单数据中包含file类型，所以需将整个from先进行序列化，还要设置相应的属性
            var formData = new FormData(document.getElementById("readFileFrom"));
            $.ajax({
	  				type: "post",
	  				url: "${beidou}/read/add",
	  				data: formData,
	  				async: false,
	  				//contentType: "application/json;charset=utf-8",
	  				processData: false, //告诉jQuery不要去处理发送的数据
	  			    contentType: false, //告诉jQuery不要去设置Content-Type请求头
	  				success: function(data) {
	  				}
	  			});
            layer.alert("增加成功", {icon: 6},function () {
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