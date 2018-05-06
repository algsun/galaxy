<#--
消息提示(配合 MessageAction 使用)

@author liuzhu
@date 2014-3-14
-->

<#--
如果没有 message ，则不会显示

参数:
marginTop: margin-top 会直接使用 m-t-{marginTop}， 默认为 0
marginBottom: margin-bottom 会直接使用 m-b-{marginBottom}, 默认为 0
-->
<#macro messageTooltip marginTop = 0, marginBottom = 0>
    <#if _message?? >
    <div class="row-fluid m-t-${marginTop} m-b-${marginBottom}">
        <div id="alert" class="span12">

        <#--使用 aJax 提示消息的位置用到 div#alert，所以 _message 放到下面判断，以保留此元素-->
            <#if _success>
                <div class="alert alert-success">
                    <a class="close" data-dismiss="alert">×</a>
                ${_message!}
                </div>
            <#else>
                <div class="alert alert-error">
                    <a class="close" data-dismiss="alert">×</a>
                ${_message!}
                </div>
            </#if>
        </div>
    </div>
    </#if>
</#macro>
