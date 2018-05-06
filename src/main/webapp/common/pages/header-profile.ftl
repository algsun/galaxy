<#--
导航栏右上角，个人信息块

@author gaohui
@date 2013-05-21
-->
<#macro headerProfile currentSubsystem>
<li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#" data-hover="dropdown">
        ${Session.currentUser.userName}
        <span class="caret"></span>
    </a>
    <ul class="dropdown-menu">
        <#-- 匿名用户(没有个人信息与修改密码) -->
        <#if !security.isAnonymity()>
        <li><a href="../blackhole/toHome.action?forward=/blackhole/updateProfile.action"><i class="icon-user"></i>个人信息</a></li>
        <li><a href="../blackhole/toHome.action?forward=/blackhole/changePassword.action"><i class="icon-lock"></i>修改密码</a></li>
        <li id="qrcode-profile" data-username="${Session.currentUser.email}"><a href="javascript: void(0);"><i class="icon-qrcode"></i>我的二维码</a></li>
        </#if>

        <li><a href="../blackhole/logout.action?from=${currentSubsystem}"><i class="icon-off"></i>退出</a></li>
    </ul>
</li>
</#macro>