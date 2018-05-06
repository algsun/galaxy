<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
</head>
<body>
<h2>欢迎进入 Galaxy </h2>

<a href="blackhole/logout.action">退出</a>

<%-- 跳转到系统管理 --%>
<%
    response.sendRedirect("blackhole/");
%>
</body>
</html>
