<div class="modal hide fade" id="patrolModal" style="width: 350px; margin-top: 150px;margin-left: -150px;">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3>风险警告!</h3>
    </div>
    <div class="modal-body">
        <p class="red f-bold" style="font-size: 18px;"> 此操作存在风险，是否继续？ </p>
            <span class="control-label" style="width: 50px;">
            验证码
                <input type="text" class="input-mini m-t-5" autocomplete="off" id="patrolVerifyCode" name="patrolVerifyCode">
                <img id="patrolVerifyCodeImage" src="verifyCode.action?name=patrol&amp;t=${.now?time}" title="点击刷新">
                <a id="refreshVerifyCodeImagePatrol" href="#">看不清</a>
            </span>


        <p class="help-block red m-t-10" id="patrolVerifyMessage"></p>
    </div>
    <div class="modal-footer">
        <a id="closePatrol" class="btn">关闭</a>
        <a id="Patrol"  data-href="" class="btn btn-primary">确定</a>
    </div>
</div>