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
                <button class="layui-btn layui-btn-danger"><i class="layui-icon"> </i>学习计划</button>
                <span class="x-right" style="line-height:40px">共有数据：${studyTotal } 条</span>
            </xblock>
            <table id="studyList" class="layui-table">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>主题</th>
                    <th>计划开始时间</th>
                    <th>计划终止时间</th>
                    <th>删除时间</th>
                    <th>删除人</th>
                    <th>操作</th>
                </thead>
                <tbody>
                <c:forEach var="item" items="${studyList }" varStatus="num">
                    <tr>
                        <td>${num.count }</td>
                        <td>${item.title }</td>
                        <td>${item.planBegintime } </td>
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
        </div>

        <%--读书计划数据回收列表--%>
        <div class="x-body">
            <xblock>
                <button class="layui-btn layui-btn-danger"><i class="layui-icon"> </i>阅读计划</button>
                <span class="x-right" style="line-height:40px">共有数据：${readTotal } 条</span>
            </xblock>
            <table class="layui-table">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>书名</th>
                    <th>作者</th>
                    <th>阅读状态</th>
                    <th>阅读开始时间</th>
                    <th>阅读结束时间</th>
                    <th>删除时间</th>
                    <th>删除人</th>
                    <th>操作</th>
                </thead>
                <tbody>
                <c:forEach var="item" items="${readList }" varStatus="num">
                    <tr>
                        <td>${num.count }</td>
                        <td>${item.bookName }</td>
                        <td>${item.author }</td>
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
                        <td>${item.begintime }</td>
                        <td>${item.endtime }</td>
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