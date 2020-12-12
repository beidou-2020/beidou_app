<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>北斗重生</title>
  <link rel="stylesheet" href="../../css/font.css">
  <link rel="stylesheet" href="../../css/xadmin.css">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo">北斗重生</div>
    <!-- 头部区域（顶层水平导航栏） -->
	<%@ include file="../../common/top.jsp" %>
  </div>
  
  <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（垂直导航栏） -->
      <%@ include file="../../common/left.jsp" %>
    </div>
  </div>
  
  <div class="layui-body">
    <!-- 内容主体区域 -->
    <div class="x-body">
      <div class="layui-row">
        <form class="layui-form layui-col-md12 x-so" action="${beidou }/study/list" method="get">
          <input class="layui-input" placeholder="计划开始日期" name="planBegintime" id="start" autocomplete="off" 
          value="${query.planBegintime }">
          <input class="layui-input" placeholder="计划截止日期" name="planEndtime" id="end" autocomplete="off"
          value="${query.planEndtime }">
          <input type="text" name="title"  placeholder="主题" class="layui-input" value="${query.title }">
          <input type="text" name="items"  placeholder="学习项目" class="layui-input" value="${query.items }">
          <button class="layui-btn"  lay-submit="" lay-filter="sreach"><i class="layui-icon">&#xe615;</i></button>
        </form>
      </div>
      <xblock>
        <button class="layui-btn layui-btn-danger" onclick="delAll()"><i class="layui-icon"> </i>批量删除</button>
        <button class="layui-btn" onclick="x_admin_show('添加','${zxz}/study/toAddView')"><i class="layui-icon"> </i>添加</button>
        <button type="button" class="layui-btn" id="importStudyPlan"><i class="layui-icon">&#xe62f;</i>导入</button>
          <button type="button" class="layui-btn" id="syncTaskStatus"><i class="layui-icon">&#xe669;</i>同步</button>
        <span class="x-right" style="line-height:40px">共有数据：${total } 条</span>
      </xblock>
      <table id="studyList" class="layui-table">
        <thead>
          <tr>
            <th>
              <div class="layui-unselect header layui-form-checkbox" lay-skin="primary"><i class="layui-icon">&#xe605;</i></div>
            </th>
            <th>序号</th>
            <th>主题</th>
            <!-- <th>学习项目</th> -->
            <th>计划开始时间</th>
            <th>计划截止时间</th>
            <th>任务状态</th>
            <th>操作</th>
        </thead>
        <tbody>
        <c:forEach var="item" items="${list }" varStatus="num">
        	<tr>
	            <td>
	              <div class="layui-unselect layui-form-checkbox" lay-skin="primary" data-id='${item.id}'><i class="layui-icon">&#xe605;</i></div>
	            </td>
	            <td>${num.count }</td>
	            <td>${item.title }</td>
	            <%-- <td>${item.items }</td> --%>
	            <td>${item.planBegintime } </td>
	            <td>${item.planEndtime } </td>
	            <td class="td-status">
	            	<c:if test="${item.planStatus == 0}">
		            	<span class="layui-btn layui-btn-radius layui-btn-xs layui-btn-disabled">待执行</span>
		            </c:if>
		            <c:if test="${item.planStatus == 1}">
		            	<span class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">执行中</span>
		            </c:if>
		            <c:if test="${item.planStatus == 2}">
		            	<span class="layui-btn layui-btn-radius layui-btn-xs layui-btn-primary">已结束</span>
		            </c:if>
                    <c:if test="${item.planStatus == 3}">
                        <span class="layui-btn layui-btn-radius layui-btn-xs layui-btn-warm">已挂起</span>
                    </c:if>
	            </td>
	            <td class="td-manage">
	              <a title="详情"  onclick="x_admin_show('详情','${beidou}/study/toDetailsView/${item.id}')" href="javascript:;">
	              	<i class="layui-icon">&#xe705;</i>
	              </a>
	              <a title="编辑"  onclick="x_admin_show('编辑','${beidou}/study/toEditView/${item.id }')" href="javascript:;">
	                <i class="layui-icon">&#xe642;</i>
	              </a>
	              <a title="删除" onclick="member_del(this,'${item.id}')" href="javascript:;">
	                <i class="layui-icon">&#xe640;</i>
	              </a>
                    <%--只能挂起执行中或待执行的--%>
                    <c:if test="${item.planStatus == 1 || item.planStatus == 0}">
                        <a onclick="member_stop(this,'${item.id}')" href="javascript:;"  title="挂起">
                            <i class="layui-icon">&#xe616;</i>
                        </a>
                    </c:if>
                    <%--只能开启已被挂起的计划--%>
                    <c:if test="${item.planStatus == 3}">
                        <a onclick="member_turn_on(this,'${item.id}')" href="javascript:;"  title="开启">
                            <i class="layui-icon">&#xe61f;</i>
                        </a>
                    </c:if>
	            </td>
	        </tr>
        </c:forEach>
        </tbody>
      </table>
        <%--分页容器--%>
        <%--<div id="page" class="page"></div>--%>
        <div class="page">
            <div>
                <c:choose>
                    <c:when test="${totalPage == 1}">
                        <span class="current">${totalPage}</span>
                    </c:when>
                    <c:when test="${totalPage == currPageNumber}">
                        <a class="prev" href="${beidou }/study/list?currentPageNumber=${currPageNumber-1}">上一页</a>
                    </c:when>
                    <c:when test="${currPageNumber<totalPage && currPageNumber>1}">
                        <a class="prev" href="${beidou }/study/list?currentPageNumber=1">首页</a>
                        <a class="prev" href="${beidou }/study/list?currentPageNumber=${currPageNumber-1}">上一页</a>
                        <a class="prev" href="${beidou }/study/list?currentPageNumber=${currPageNumber+1}">下一页</a>
                        <a class="prev" href="${beidou }/study/list?currentPageNumber=${totalPage}">尾页</a>
                    </c:when>
                    <c:otherwise>
                        <a class="prev" href="${beidou }/study/list?currentPageNumber=${currPageNumber+1}">下一页</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
  </div>

    <!-- 页脚 -->
    <%@ include file="../../common/foot.jsp" %>
</div>
<script src="../../layui/layui.js"></script>
<script type="text/javascript" src="../../js/xadmin.js"></script>
<%--<script>
    /*自动集成layui的分页组件*/
    layui.use('laypage', function(){
        var laypage = layui.laypage;
        //执行一个laypage实例
        laypage.render({
            elem: 'page',       //注意，这里的page是ID，不用加#号
            count: ${total},          //数据总数
            limit: 10,          //每页大小
            jump: function(obj, first){
                //obj包含了当前分页的所有参数
                var currPageNum = obj.curr;     // 当前页
                var pageSize = obj.limit;       // 每页大小

                //首次不执行
                if(!first){
                    //alert("当前页数："+currPageNum+"，每页数据大小："+pageSize);
                    $.ajax({
                        type:"get",
                        url:"${beidou }/study/list?currentPageNumber="+currPageNum+"&pageSize="+pageSize,
                        success:function(data) {
                            //alert(data);
                        }
                    });
                }
            }
        });
    });
</script>--%>
<script>
    $("#syncTaskStatus").click(function(){
        $.ajax({
            type: "get",
            url: "${beidou}/study/syncTaskStatus",
            success: function(data){
                //alert(JSON.stringify(data));
                if (data.code == 0){
                    layer.alert(data.message, {icon: 6});
                } else {
                    layer.alert('服务器异常', {icon: 5});
                }
            }
        });
    });
</script>
<script type="text/javascript">
	/* 给指定标签绑定点击事件 */
	/* $("#importStudy").click(function(){
	    alert("点击了");
	}); */
	
	  layui.use('upload', function(){
	  var upload = layui.upload;
	   
	  //点击导入按钮上传文件
	  var uploadInst = upload.render({
	    elem: '#importStudyPlan', 	   		//绑定元素
	    url: '${beidou}/study/import', 		//上传接口
	    field: "studyPlanExcel",       		//添加这个属性与后台名称保存一致
	    accept: 'file', 		   			//允许上传的文件类型(所有类型的文件)
	    done: function(res){
	      //上传完毕回调
            console.log(JSON.stringify(res));
	      if(res.code == 1){
	    	  layer.alert('成功导入'+res.data+'条学习计划(附件已上传至文件服务器：'+res.path+')', {icon: 6});
	      }else{
	    	  layer.alert('附件内容不符合接口要求：'+res.msg, {icon: 2});
	      }
	    },
	    error: function(){
	      //请求异常回调
	    	layer.alert('导入数据出现异常', {icon: 5});
	    }
	  });
	});
</script>
<script>
//JavaScript代码区域
layui.use('element', function(){
  var element = layui.element;
  
});
</script>
<script>
	  /* 时间组件 */
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

       /*计划-挂起*/
       function member_stop(obj,id){
          layer.confirm('确认要挂起吗？',function(){
              //alert(id);
              $.ajax({
                  type: "post",
                  url: "${beidou}/study/hang/"+id,
                  success: function(data){
                      if (data.data == 1) {
                          /*$(obj).attr('title','挂起');
                          $(obj).find('i').html('&#xe61f;');
                          $(obj).parents("tr").find(".td-status").find('span').
                          addClass('layui-btn layui-btn-radius layui-btn-xs layui-btn-disabled').html('已挂起');*/
                          layer.msg('计划已经挂起!',{icon: 1,time:1000});
                          window.parent.location.reload();//刷新父页面
                      }
                  }
              });
          });
      }
      /*计划-开启*/
      function member_turn_on(obj,id){
          layer.confirm('确认要重启吗？',function(){
              //alert(id);
              $.ajax({
                  type: "post",
                  url: "${beidou}/study/trunOn/"+id,
                  success: function(data){
                      if (data.data == 1) {
                          /*$(obj).attr('title','重启');
                          $(obj).find('i').html('&#xe616;');
                          $(obj).parents("tr").find(".td-status").find('span').
                          addClass('layui-btn layui-btn-radius layui-btn-xs layui-btn-danger').html('已重启');*/
                          layer.msg('重启计划，加油!',{icon: 6,time:1000});
                          window.parent.location.reload();//刷新父页面
                      }
                  }
              });
          });
      }

      /*用户-删除*/
      function member_del(obj,id){
          layer.confirm('确认要删除吗？',function(){
              //发异步删除数据
              //alert(id);
              $.ajax({
	  				type:"post",
	  				url:"${beidou}/study/delete/" + id,
	  				success:function(data) {
	  					//alert(data);
                        /*$(obj).parents("tr").remove();*/
	  				}
	  			});
              layer.msg('已删除!',{icon:1,time:1000});
              window.parent.location.reload();//刷新父页面
          });
      }



      /*批量删除*/
      function delAll () {
        var data = tableCheck.getData();
        layer.confirm('确认要批量删除吗？', function(){
            //捉到所有被选中的，发异步进行删除
            $.ajax({
                type: "post",
                url: "${beidou}/study/batchDelete?idListStr=" + data,
                success: function(data){
                    if(data.code == 0){
                        layer.msg('操作成功', {icon: 1});
                        window.parent.location.reload();//刷新父页面
                    }
                }
            });
        });
      }
</script>
</body>
</html>