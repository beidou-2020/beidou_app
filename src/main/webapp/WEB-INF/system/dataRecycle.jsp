<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
        <%--学习计划数据回收列表--%>
        <div class="x-body">
            <xblock>
                <button class="layui-btn layui-btn-danger" onclick="delAll()"><i class="layui-icon"> </i>批量恢复</button>
                <span class="x-right" style="line-height:40px">共有数据：${studyTotal } 条</span>
            </xblock>
            <table id="studyList" class="layui-table">
                <thead>
                <tr>
                    <th>
                        <div class="layui-unselect header layui-form-checkbox" lay-skin="primary"><i class="layui-icon">&#xe605;</i>
                        </div>
                    </th>
                    <th>序号</th>
                    <th>主题</th>
                    <th>计划终止时间</th>
                    <th>删除时间</th>
                    <th>删除人</th>
                    <th>操作</th>
                </thead>
                <tbody>
                <c:forEach var="item" items="${studyList }" varStatus="num">
                    <tr>
                        <td>
                            <div class="layui-unselect layui-form-checkbox" lay-skin="primary" data-id='${item.id}'><i
                                    class="layui-icon">&#xe605;</i></div>
                        </td>
                        <td>${num.count }</td>
                        <td>${item.title }</td>
                        <td>${item.planEndtime }</td>
                        <td>${item.removetime }</td>
                        <td>${item.removeUser }</td>
                        <td class="td-manage">
                            <a title="恢复" onclick="member_del(this,'${item.id}')" href="javascript:;">
                                <i class="layui-icon">&#xe609;</i>
                            </a>
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
                        <c:when test="${studyTotalPage == 1}">
                            <span class="current">${studyTotalPage}</span>
                        </c:when>
                        <c:when test="${totalPage == currPageNumber}">
                            <a class="prev" href="${beidou }/recycle/dataRecycle?currentPageNumber=${studyCurrPage-1}">上一页</a>
                        </c:when>
                        <c:when test="${currPageNumber<totalPage && currPageNumber>1}">
                            <a class="prev" href="${beidou }/recycle/dataRecycle?currentPageNumber=1">首页</a>
                            <a class="prev" href="${beidou }/recycle/dataRecycle?currentPageNumber=${studyCurrPage-1}">上一页</a>
                            <a class="prev" href="${beidou }/recycle/dataRecycle?currentPageNumber=${studyCurrPage+1}">下一页</a>
                            <a class="prev" href="${beidou }/recycle/dataRecycle?currentPageNumber=${studyTotalPage}">尾页</a>
                        </c:when>
                        <c:otherwise>
                            <a class="prev" href="${beidou }/recycle/dataRecycle?currentPageNumber=${studyCurrPage+1}">下一页</a>
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
    layui.use('element', function () {
        var element = layui.element;
    });
</script>

</body>
</html>