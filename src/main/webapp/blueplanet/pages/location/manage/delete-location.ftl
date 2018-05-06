<div class="modal hide fade" id="deleteLocationModal" style="width: 350px; margin-top: 150px;margin-left: -150px;">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3>${locale.getStr("common.tips")}</h3>
    </div>
    <div class="modal-body">
        <p class="red f-bold" style="font-size: 18px;"> ${locale.getStr("blueplanet.location.deleting")} </p>
            <span class="control-label" style="width: 50px;">
            ${locale.getStr("blueplanet.location.verificationCode")}
                <input type="text" class="input-mini m-t-5" autocomplete="off" id="verifyCode" name="verifyCode">
                <img id="verifyCodeImage" src="verifyCode.action?name=deleteLocation&amp;t=${.now?time}" title="${locale.getStr("blueplanet.location.refresh")}">
                <a id="refreshVerifyCodeImageButton" href="#">${locale.getStr("blueplanet.location.noClear")}</a>
            </span>


        <p class="help-block red m-t-10" id="verifyMessage"></p>
    </div>
    <div class="modal-footer">
        <a id="closeLocation" class="btn">${locale.getStr("common.close")}</a>
        <a id="deleteConfirm" class="btn btn-primary">${locale.getStr("common.yes")}</a>
    </div>
</div>