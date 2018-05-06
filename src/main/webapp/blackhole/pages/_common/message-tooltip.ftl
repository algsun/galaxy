<#--
消息提示(配合 MessageAction 使用)

@author gaohui
@date 2012-11-19
-->
<div class="row">
    <div id="alert" class="span12">

    <#--使用 aJax 提示消息的位置用到 div#alert，所以 _message 放到下面判断，以保留此元素-->
    <#if _message?? >
        <div class="alert alert-<#if _success>success<#else>error</#if>">
            <a class="close" data-dismiss="alert">×</a>
        ${_message!}
        </div>
    </#if>
    </div>
</div>
