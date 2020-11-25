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
        <form class="layui-form layui-col-md12 x-so" action="${beidou }/read/list" method="get">
          <input class="layui-input" placeholder="阅读开始日期" name="begintime" id="start" autocomplete="off" 
          value="${query.begintime}">
          <input class="layui-input" placeholder="阅读结束日期" name="endtime" id="end" autocomplete="off"
          value="${query.endtime}">
          <input type="text" name="bookName"  placeholder="书名" class="layui-input" value="${query.bookName }">
          <input type="text" name="author"  placeholder="作者" class="layui-input" value="${query.author }">
          <button class="layui-btn"  lay-submit="" lay-filter="sreach"><i class="layui-icon">&#xe615;</i></button>
        </form>
      </div>
      <xblock>
        <button class="layui-btn layui-btn-danger" onclick="delAll()"><i class="layui-icon"></i>批量删除</button>
        <button class="layui-btn" onclick="x_admin_show('添加','${beidou}/read/toAddView')"><i class="layui-icon"></i>添加</button>
        <span class="x-right" style="line-height:40px">共有数据：${total } 条</span>
      </xblock>
      <table class="layui-table">
        <thead>
          <tr>
            <th>
              <div class="layui-unselect header layui-form-checkbox" lay-skin="primary"><i class="layui-icon">&#xe605;</i></div>
            </th>
            <th>序号</th>
            <th>书名</th>
              <th>类别</th>
            <th>作者</th>
            <th>阅读开始时间</th>
            <th>阅读结束时间</th>
            <th>阅读状态</th>
            <th>操作</th>
        </thead>
        <tbody>
        <c:forEach var="item" items="${list }" varStatus="num">
        	<tr>
	            <td>
	              <div class="layui-unselect layui-form-checkbox" lay-skin="primary" data-id='2'><i class="layui-icon">&#xe605;</i></div>
	            </td>
	            <td>${num.count }</td>
	            <td>${item.bookName }</td>
                <td>
                    <c:if test="${item.category == 1}">
                        养性
                    </c:if>
                    <c:if test="${item.category == 2}">
                        专业
                    </c:if>
                </td>
	            <td>${item.author }</td>
	            <td>${item.begintime }</td>
	            <td>${item.endtime }</td>
	            <td class="td-status">
	            	<c:if test="${item.readFlag == 1}">
		            	<span class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">在读</span>
		            </c:if>
		            <c:if test="${item.readFlag == 2}">
		            	<span class="layui-btn layui-btn-radius layui-btn-xs">读完</span>
		            </c:if>
		            <c:if test="${item.readFlag == 3}">
		            	<span class="layui-btn layui-btn-radius layui-btn-xs layui-btn-disabled">待读</span>
		            </c:if>
	            </td>
	            <td class="td-manage">
	              <a title="详情"  onclick="x_admin_show('详情','${beidou}/read/toDetailsView/${item.id }')" href="javascript:;">
	              	<i class="layui-icon">&#xe705;</i>
	              </a>
	              <a title="编辑"  onclick="x_admin_show('编辑','${beidou}/read/toEditView/${item.id }')" href="javascript:;">
	                <i class="layui-icon">&#xe642;</i>
	              </a>
	              <a title="删除" onclick="member_del(this,'${item.id}')" href="javascript:;">
	                <i class="layui-icon">&#xe640;</i>
	              </a>
	            </td>
	        </tr>
        </c:forEach>
        </tbody>
      </table>
      <div class="page">
        <div>
            <c:choose>
                <c:when test="${totalPage == 1}">
                    <span class="current">${totalPage}</span>
                </c:when>
                <c:when test="${totalPage == currPageNumber}">
                    <a class="prev" href="${beidou }/read/list?currentPageNumber=${currPageNumber-1}">上一页</a>
                </c:when>
                <c:when test="${currPageNumber<totalPage && currPageNumber>1}">
                    <a class="prev" href="${beidou }/read/list?currentPageNumber=1">首页</a>
                    <a class="prev" href="${beidou }/read/list?currentPageNumber=${currPageNumber-1}">上一页</a>
                    <a class="prev" href="${beidou }/read/list?currentPageNumber=${currPageNumber+1}">下一页</a>
                    <a class="prev" href="${beidou }/read/list?currentPageNumber=${totalPage}">尾页</a>
                </c:when>
                <c:otherwise>
                    <a class="prev" href="${beidou }/read/list?currentPageNumber=${currPageNumber+1}">下一页</a>
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

       /*用户-停用*/
      /* function member_stop(obj,id){
          layer.confirm('确认要停用吗？',function(index){

              if($(obj).attr('title')=='启用'){

                //发异步把用户状态进行更改
                $(obj).attr('title','停用')
                $(obj).find('i').html('&#xe62f;');

                $(obj).parents("tr").find(".td-status").find('span').addClass('layui-btn-disabled').html('已停用');
                layer.msg('已停用!',{icon: 5,time:1000});

              }else{
                $(obj).attr('title','启用')
                $(obj).find('i').html('&#xe601;');

                $(obj).parents("tr").find(".td-status").find('span').removeClass('layui-btn-disabled').html('已启用');
                layer.msg('已启用!',{icon: 5,time:1000});
              }
              
          });
      } */

      /*用户-删除*/
      function member_del(obj,id){
          layer.confirm('确认要删除吗？',function(){
              //发异步删除数据
              //alert(id);
              $.ajax({
	  				type:"post",
	  				url:"${beidou }/read/deleteById",
	  				data:{"id":id},
	  				success:function(data) {
	  					//alert(data);
	  				}
	  			});
              $(obj).parents("tr").remove();
              layer.msg('已删除!',{icon:1,time:1000});
              window.location.reload();//刷新父页面
          });
          
      }



      function delAll (argument) {

        var data = tableCheck.getData();
  
        layer.confirm('确认要删除吗？'+data,function(index){
            //捉到所有被选中的，发异步进行删除
            layer.msg('删除成功', {icon: 1});
            $(".layui-form-checked").not('.header').parents('tr').remove();
        });
      }
</script>
</body>
</html>