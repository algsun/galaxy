<#--银河页面头部(用于没有用户登录的情况)

@author gaohui
@date 2012-10-26
-->
<div class="navbar navbar-inverse navbar-static-top">
    <div class="navbar-inner yahei-font">
        <div class="container">
            <div class="brand p-v-0" style="width:500px; padding-top:5px; padding-bottom: 5px;">
                <span class="t-d-none" style="color:#4799ee; line-height: 31px; font-size:1.3em;">
                    <img src="../common/images/logo/${localeUI.getStr("app.logo")}" class="subsystem-head-icon"
                         style="width: 30px; height: 30px;">
                <#--${localeUI.getStr("app.name")}-->
                ${locale.getStr("blackhole.login.galaxy")}
                </span>
            </div>
            <div class="brand p-v-0" style="width:300px; padding-top:5px;font-size: 15px;">
                <div style="color:white; line-height: 31px;float: right;">
                <#if locale.getLanguage()=='zh_CN'>
                    <a href="setting.action?language=en_US" style="color: white">Switch to English</a>
                <#else>
                    <a href="setting.action?language=zh_CN" style="color: white">切换为中文</a>
                </#if>
                </div>
            </div>
            <ul class="nav pull-right">
                <li class="divider-vertical"></li>
                <li><a href="login.action"><i
                        class="icon-upload icon-white"></i>${locale.getStr("blackhole.login.login")}</a></li>
            </ul>
        </div>
    </div>
</div>

