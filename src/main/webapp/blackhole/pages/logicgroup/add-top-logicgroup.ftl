<#--
添顶级加站点

@author gaohui
@time  2012-12-05
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl" >
    <title>添加站点 - 系统管理</title>
<#include "../_common/common-css.ftl" >

    <style type="text/css">
        .i-w-270 {
            width: 270px;
        }
    </style>
</head>
<body>

<#include "../_common/header-for-superman.ftl">
<div id="gcontent" class="container">

<#--消息-->
<#include "../_common/message-tooltip.ftl">

    <div class="row">
        <div class="span12">
            <form class="form-horizontal" action="doAddTopLogicGroup.action" method="post">
                <fieldset>
                    <legend>添加站点</legend>
                    <div class="row">
                        <div class="span8">
                            <div class="">
                                <div id="base-site" class="control-group">
                                    <label class="control-label bold"><em class="required">*</em>基层站点</label>

                                    <div class="controls">
                                        <label class="radio inline-block">
                                            <input type="radio" name="baseSite" value="true">是
                                        </label>
                                        <label class="radio m-l-20 inline-block">
                                            <input checked="checked" type="radio" name="baseSite" value="false">否
                                        </label>
                                    </div>
                                </div>
                                <div id="site-name" class="control-group">
                                    <label class="control-label bold"><em class="required">*</em>站点名称</label>

                                    <div class="controls">
                                        <input name="logicGroupName" type="text" class="required i-w-270">
                                    <span class="inline-block msg m-l-10 red"
                                          style="font-size: 80%"></span>
                                    </div>
                                </div>
                                <div id="site-manager-email" class="control-group">
                                    <label class="control-label bold"><em class="required">*</em>管理员邮箱</label>

                                    <div class="controls">
                                        <input type="text" name="managerEmail" class="required i-w-270"
                                               value="${managerEmail!}" maxlength="50">
                                    <span class="inline-block msg m-l-10 red"
                                          style="font-size: 80%"></span>
                                    </div>
                                </div>
                            </div>
                            <div id="address-area" class="hide">
                                <div class="control-group">
                                    <div class="controls">
                                        <div class="gray" style="font-size:80%;">
                                            选择地区帮助您尽快找到绑定站点。
                                            <br/>
                                            我们建议您至少选择省份。
                                        </div>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">省/直辖市</label>

                                    <div class="controls">
                                        <select id="province"></select>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">市/区</label>

                                    <div class="controls">
                                        <select id="city"></select>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">区/县</label>

                                    <div class="controls">
                                        <select id="zone"></select>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <div class="controls">
                                        <div class="gray" style="font-size:80%;">
                                            如果需要绑定基层站点，那将不能输入站点名称。
                                            <br/>
                                            当选择基层站点的时候，添加的站点名称就为当前选择的绑定站点名称。
                                        </div>
                                    </div>
                                </div>
                                <div id="site" class="control-group">
                                    <label class="control-label bold"><em class="required">*</em>绑定站点</label>

                                    <div class="controls">
                                        <select class="i-w-270" name="siteId">
                                            <option value="0">请选择</option>
                                        </select>
                                    <span class="inline-block msg m-l-10 red"
                                          style="font-size: 80%"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="">
                                <div class="control-group">
                                    <div class="controls">
                                        <button id="logicgroup-submit" class="btn btn-primary" type="submit">保存</button>
                                        <a class="btn" href="chooseLogicGroup.action">返回</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="span4 muted">
                            <span>添加站点流程：</span>
                            <ol>
                                <li>
                                    <span class="bold">添加站点</span>
                                    <ul>
                                        <li>添加站点必须同时指定站点管理员</li>
                                        <li>以邮箱指定站点管理员</li>
                                        <li>系统会自动向该邮箱发激活邮件</li>
                                    </ul>
                                </li>
                                <li class="m-t-20">
                                    <span class="bold">站点管理员激活</span>

                                    <p>站点管理员登录邮箱,激活！</p>
                                </li>
                                <li class="m-t-20">
                                    <span class="bold">完成</span>
                                </li>
                            </ol>

                            <div class="m-t-30">描述</div>
                            <ol>
                                <li>基层站点：需要绑定系统中已编排的站点。</li>
                                <li>非基层站点：用来组织站点层次关系。</li>
                            </ol>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="js/logicgroup.js"></script>
<script type="text/javascript" src="js/site-area.js"></script>
<#include "../_common/last-resources.ftl">
</body>
</html>