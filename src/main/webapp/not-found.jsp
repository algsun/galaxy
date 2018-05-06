<%--
404 页面

@author gaohui
@date 2012-12-14
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <title>该页面不存在</title>
    <link rel="shortcut icon" type="image/x-icon" href="favicon.ico"/>
    <link rel="stylesheet" href="assets/bootstrap/2.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/common-css/0.1.2-beta/common.css">
</head>
<body>

<div class="navbar navbar-inverse navbar-static-top">
    <div class="navbar-inner yahei-font">
        <div class="container">
            <div class="brand p-v-0" style="width:120px; padding-top:2px; padding-bottom: 2px;">
                <span class="t-d-none" style="color:#4799ee;">
                    <img src="common/images/galaxy-logo.png" class="subsystem-head-icon" style="width: 35px; height: 35px;">
                    银河
                </span>
            </div>
        </div>
    </div>
</div>


<div class="container m-t-10">
    <div class="span12 p-b-20" style="background: url('common/images/body-bg.png');">
        <div class="alert m-b-0"><h3>亲，该页面不存在。</h3></div>
        <div class="t-a-c" style="background: url('common/images/body-bg.png');">

            <img src="common/images/404-c.png" style="width:300px; height:300px;" alt="404">
        </div>
        <button class="btn m-l-20" onclick="javascript: window.history.back();"><i class="icon-arrow-left"></i> 返回</button>
        <a class="btn m-l-20" href="."><i class="icon-home"></i> 首页</a>
    </div>
</div>
</body>
</html>