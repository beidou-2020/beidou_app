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
	              <div class="layui-unselect layui-form-checkbox" lay-skin="primary" data-id='${item.id}'><i class="layui-icon">&#xe605;</i></div>
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
		            	<span class="layui-btn layui-btn-radius layui-btn-xs layui-btn-primary">读完</span>
		            </c:if>
		            <c:if test="${item.readFlag == 3}">
		            	<span class="layui-btn layui-btn-radius layui-btn-xs layui-btn-disabled">待读</span>
		            </c:if>
                    <c:if test="${item.readFlag == 4}">
                        <span class="layui-btn layui-btn-radius layui-btn-xs layui-btn-warm">暂停</span>
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
                        <%--只能暂停在读的--%>
                    <c:if test="${item.readFlag == 1}">
                        <a onclick="member_stop(this,'${item.id}')" href="javascript:;"  title="暂停">
                            <i class="layui-icon">&#xe616;</i>
                        </a>
                    </c:if>
                        <%--只能开始暂停和待读状态的阅读信息--%>
                    <c:if test="${item.readFlag == 4 || item.readFlag == 3}">
                        <a onclick="member_turn_on(this,'${item.id}')" href="javascript:;"  title="开始">
                            <i class="layui-icon">&#xe61f;</i>
                        </a>
                    </c:if>
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

      /*阅读-暂停*/
      function member_stop(obj,id){
          layer.confirm('确认要暂停阅读吗？',function(){
              //alert(id);
              $.ajax({
                  type: "post",
                  url: "${beidou}/read/timeOut/"+id,
                  success: function(data){
                      if (data.data == 1) {
                          layer.msg('阅读已经暂停!',{icon: 1,time:3000});
                          window.parent.location.reload();//刷新父页面
                      }
                  }
              });
          });
      }
      /*阅读-开始*/
      function member_turn_on(obj,id){
          layer.confirm('确认要重新开始阅读吗？',function(){
              //alert(id);
              $.ajax({
                  type: "post",
                  url: "${beidou}/read/restart/"+id,
                  success: function(data){
                      if (data.data == 1) {
                          layer.msg('欢迎回来，加油!',{icon: 6,time:3000});
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
	  				url:"${beidou }/read/deleteById",
	  				data:{"id":id},
	  				success:function(data) {

	  				}
	  			});
              layer.msg('已删除!',{icon:1,time:1000});
              window.location.reload();//刷新父页面
          });
          
      }

      /*批量删除阅读信息*/
      function delAll (argument) {
        var data = tableCheck.getData();
        layer.confirm('确认要批量删除吗？',function(){
            $.ajax({
                type: "post",
                url: "${beidou}/read/batchDelete?idListStr=" + data,
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