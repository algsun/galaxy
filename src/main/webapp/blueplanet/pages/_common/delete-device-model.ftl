<div class="modal hide fade" id="deleteDeviceModal" style="width: 350px; margin-top: 150px;margin-left: -150px;">
    <#--TODO 国际化-->
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3>温馨提示</h3>
    </div>
    <div class="modal-body">
        <p class="red f-bold" style="font-size: 18px;">确认要删除设备吗？ </p>
        <p class="red f-bold" style="font-size: 10px;">注意：删除主模块时，会同时删除从模块</p>
            <span class="control-label" style="width: 50px;">
            验证码
                <input type="text" class="input-mini m-t-5" autocomplete="off" id="verifyCode" name="verifyCode">
                <img id="verifyCodeImage" src="verifyCode.action?name=deleteDevice&amp;t=${.now?time}" title="点击刷新">
                <a id="refreshVerifyCodeImageButton" href="#">看不清</a>
            </span>


        <p class="help-block red m-t-10" id="verifyMessage"></p>
    </div>
    <div class="modal-footer">
        <a id="closeDevice" class="btn">关闭</a>
        <a id="deleteConfirm" class="btn btn-primary">确定</a>
    </div>
</div>