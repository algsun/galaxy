<#--
新闻添加

@author gaohui
@date 2013-05-08
-->

<!DOCTYPE html>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>添加新闻 - 系统管理</title>
<#include "../_common/common-css.ftl">

    <link rel="stylesheet" href="../assets/kinkeditor/4.1.7/themes/default/default.css"/>
</head>
<body>
<!-- 导航栏 -->
<#--当前一级权限ID-->
<#assign currentTopPrivilege = "blackhole:post">
<#include "../_common/header.ftl" >

<div id="gcontent" class="container">

<#include "../_common/sub-navs.ftl">
<@subNavs "blackhole:post:add"></@subNavs>

<#include "../_common/message-tooltip.ftl" >

    <div class="row">
        <div class="span12">
            <form id="post-form" class="form-horizontal" action="doAddPost.action" method="post">
                <fieldset>
                    <legend>添加新闻</legend>
                    <div class="control-group">
                        <div class="control-label">
                            <label>可见</label>
                        </div>
                        <div class="controls">
                            <label class="radio inline">
                                <input type="radio" name="scope" value="1" checked="checked"/> 公共
                            </label>
                            <label class="radio inline">
                                <input type="radio" name="scope" value="2"/> 内部
                            </label>

                            <p class="help-block" style="color:#aaa;">
                                公共新闻会出现在登录页面，内部新闻只有登录后可见
                            </p>
                        </div>
                    </div>

                    <div class="control-group">
                        <div class="control-label">
                            <label><em class="required">*</em>创建时间</label>
                        </div>
                        <div class="controls">
                            <div>
                                <input class="input-medium Wdate"  type="text" id="createDate" name="createDate"
                                         onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${createDate?string("yyyy-MM-dd HH:mm:ss")!}"/>
                                <span class="help-inline red"></span>
                            </div>
                        </div>
                    </div>

                    <div class="control-group">
                        <div class="control-label">
                            <label><em class="required">*</em>标题</label>
                        </div>
                        <div class="controls">
                            <input class="input-xxlarge" type="text" name="title" maxlength="50"/>
                            <span class="help-inline red"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <div class="control-label">
                            <label><em class="required">*</em>内容</label>
                        </div>
                        <div class="controls">
                            <textarea class="input-xxlarge" rows="10" id="content" name="content"></textarea>

                            <p class="help-block red"></p>
                        </div>
                    </div>

                    <div class="control-group">
                        <div class="control-label"></div>
                        <div class="controls">
                            <button class="btn btn-primary" type="submit">保存</button>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>

<!-- 页面底部 -->
<#include "../_common/footer.ftl" >

<#--公共JS-->
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/kinkeditor/4.1.7/kindeditor-min.js"></script>
<script type="text/javascript" src="../assets/kinkeditor/4.1.7/lang/zh_CN.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<#--<script type="text/javascript" src="js/post/kindeditor-config.js"></script>-->

<script type="text/javascript" src="../common/js/form-check.js"></script>
<script type="text/javascript" src="js/post/post.js"></script>

<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>