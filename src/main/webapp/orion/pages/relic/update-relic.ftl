<#--
文物编辑

@author Wang yunlong
@time  13-5-15  上午9:03
@author wang.geng
@date 2013-06-04
@check @gaohui #4057 2013-06-05
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>编辑文物信息 - 资产管理</title>

<#include "../_common/common-css.ftl">
<#include  "/common/pages/common-tag.ftl">
<#-- TODO 样式问题 -->
    <style type="text/css">
        .relic-row {
            /* TODO 不建议写死宽度 */
            width: 780px;
        }

        .row-title {
            width: 100%;
            overflow: hidden;
            border-bottom: solid 1px #ddd;
        }

        .row-content {
            width: 100%;
            float: left;
        }

        .edit-content, .view-content {
            float: left;
        }

        .view-content-ele, .edit-content-ele {
            float: left;
            width: 320px;
            margin-bottom: 10px;
            margin-left: 20px;
        }

        .edit-btn {
            width: 100%;
            float: left;
        }

        .ele-name {
            float: left;
            width: 80px;
            text-align: right;
        }

        .view-content-ele .ele-value, .edit-content-ele .ele-input {
            float: left;
            margin-left: 10px;
            width: 210px;
        }

        .ele-value {
            font-weight: bold;
        }

        .row-edit {
            margin-top: 10px;
            font-size: 0.9em;
        }

        /* TODO 删除之，无用。和使用此 class 的元素 @gaohui 2013-06-04 */
        .line {
            /*
            background-color: #ddd;
            height: 1px;
            margin: 4px 0;
            width: 100%;
            */
        }

        .list ul {
            margin: 0;
        }

        .list ul li {
            font-size: 14px;
            margin-top: 5px;
        }

        a {
            cursor: pointer;
        }

        /* TODO 样式没用了 @gaohui 2013-06-04 */
        /*.row-name a {*/
        /*color: #000000;*/
        /*cursor: default;*/
        /*text-decoration: none;*/
        /*}*/

        .edit-content {
            float: left;
            width: 100%;
            display: none;
        }

        textarea {
            margin: 4px 0;
            padding: 0;
        }

        .jump {
            height: 44px;
        }

        /*list-ele ==========================start=======================*/
        .list-ele {
            float: left;
        }

        .list-row {
            float: left;
        }

        .list-row-inputs {
            width: 700px;
            float: left;
        }

        .input-date, .input-person {
            float: left;
            width: 300px;
            margin: 0 10px;
        }

        .input-content {
            float: left;
            width: 600px;
            margin: 0 10px;
        }

        .input-textarea-container {
            float: left;
            width: 520px;
        }

        .input-name, .value-name {
            float: left;
            width: 60px;
            text-align: right;
            font-weight: bold;
            margin: 4px 4px 4px 0;
        }

        .value-person, .value-date, .value-content {
            float: left;
            margin: 4px 4px 4px 0;
        }

        .input-content.input-name {
            margin-top: 24px;
        }

        .list-row-textarea {
            width: 540px;
            height: 50px;
        }

        .list-row-delete, .list-row-plus {
            margin-top: 90px;
        }

        /*list-ele ==========================end====================*/

        /*text===============================start============================*/
        .content-textarea {
            width: 100%;
        }

        /*text===============================end============================*/

        /*image============================start=============================*/

        .image-ele {
            float: left;
            width: 100%;
        }

        .image-i {
            background-color: #ddd;
            float: left;
            width: 150px;
            height: 150px;
            margin: 3px;
        }

        .image-i img {
        }

        .image-plus {
            cursor: pointer;
        }

        .image-plus img {
            width: 64px;
            height: 64px;
            margin: 43px;
        }

        .delete-container {
            float: left;
            width: 100%;
            opacity: 0.5;
            background-color: #eee;
        }

        .delete-container > i {
            cursor: pointer;
        }

        /*image============================end=============================*/

        .fileupload-box {
            position: relative;
            text-align: center;
        }

        .fileupload-txt {
            height: 22px;
            border: 1px solid #cdcdcd;
            width: 260px;
        }

        .fileupload-btn {
            background-color: #FFF;
            border: 1px solid #CDCDCD;
            height: 24px;
            width: 90px;
        }

        .fileupload-file {
            position: absolute;
            top: 0;
            right: 320px;
            height: 24px;
            filter: alpha(opacity:0);
            opacity: 0;
            width: 260px;
        }
        .image-link {
            cursor: -webkit-zoom-in;
            cursor: -moz-zoom-in;
            cursor: zoom-in;
        }
    </style>
    <link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/magnificpopup/magnific-popup.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet"
          href="../assets/jquery-ui/1.9.2/css/jquery-ui.css"
          type="text/css"/>
</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "orion:culturalRelic">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

    <fieldset>
        <legend>
            <a class="go-back"
               href="queryRelic.action?index=${relicIndex}&totalCodeAsCondition=${totalCodeAsCondition!}&name=${name!}&catalogCode=${catalogCode!}&typeCode=${typeCode!}&labelId=${labelId!}&zoneId=${zoneId!}&eraId=${eraId!}&levelId=${levelId!}&textureId=${textureId!}&state=${state!}"
               title="返回">
                <i class="mw-icon-prev"></i>编辑
            </a>
        </legend>
    </fieldset>

    <div class="row">
        <div class="span12"><h3>${relic.name}${messageInfo!}</h3></div>
    </div>
    <div class="row">
        <div class="span10">
            <div class="row-base-info"></div>
            <div class="row-archives-info">

                <div class="relic-row m-v-10 m-h-5">
                    <div id="alert"></div>
                    <div class="row-title">
                        <label class="control-label" for="">
                            <h4 class="f-l">自定义标签</h4>
                            <a class="row-edit f-r" id="editRelicLabel" data-type="">
                                <i class="icon-edit"></i>
                                <span>编辑本段</span>
                            </a>
                        </label>
                    </div>
                    <div class="line"></div>
                    <div class="controls">
                    <#list relicLabels as relicLabel>
                        <label class="btn btn-small m-r-20 m-t-10 m-b-10" name="${relicLabel.name}">
                        ${relicLabel.name}
                            <i data-action-id="1" data-action-type="0" class="icon-remove"></i>
                        </label>
                    </#list>
                        <input id='relicTagText' name='relic.tag' class='m-r-20 m-t-10 m-b-10' style="display: none;"/>
                    </div>
                    <div class="line"></div>

                    <div id="0" class="jump"></div>
                    <div class="row-title">
                        <h4 class="f-l">基本信息</h4>
                        <a class="row-edit f-r" data-type="">
                            <i class="icon-edit"></i>
                            <span>编辑本段</span>
                        </a>
                    </div>
                    <div class="line"></div>
                    <div class="row-content m-t-20">
                        <div class="view-content m-t-20">
                            <div class="row">
                                <div class="span12">
                                    <div class="span5">
                                    <@viewEle "总登记号:" relic.totalCode/>
                                    </div>
                                    <div class="span5">
                                    <@viewEle "编目号:" relic.catalogCode/>
                                    </div>
                                </div>
                                <div class="span12">
                                    <div class="span5">
                                    <@viewEle "分类号:" relic.typeCode/>
                                    </div>
                                    <div class="span5">
                                    <@viewEle "名称:" relic.name/>
                                    </div>
                                </div>
                                <div class="span12">
                                    <div class="span5">
                                    <@viewEle "时代:" relic.era.name/>
                                    </div>
                                    <div class="span5">
                                    <@viewEle "件数:" relic.count?c/>
                                    </div>
                                </div>
                                <div class="span12">
                                    <div class="span5">
                                    <@viewEle "级别:" relic.level.name/>
                                    </div>
                                    <div class="span5">
                                    <@viewEle "文物质地:" relic.texture.name/>
                                    </div>
                                </div>
                                <div class="span12">
                                    <div class="span5">
                                    <#if relic.zone??>
                        <@viewEle "库房位次:"  relic.zone.name/>
                    <#else>
                                        <@viewEle "库房位次:"  "(暂无)"/>
                                    </#if>
                                    </div>
                                    <div class="span5">
                                    <#if relic.state == 0>
                        <@viewEle "文物状态:" "在库"/>
                    <#elseif relic.state == 1>
                                        <@viewEle "文物状态:" "待出库"/>
                                    <#elseif relic.state == 2>
                                        <@viewEle "文物状态:" "出库"/>
                                    </#if>
                                    </div>
                                </div>

                                <div class="span12">
                                    <div class="span5">
                                    <#if relic.hasTag == false>
                            <@viewEle "电子标签:" "无"/>
                        <#elseif relic.hasTag == true>
                                        <@viewEle "电子标签:" "有"/>
                                    </#if>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="edit-content m-t-20">
                            <form id="role-form" action="toSaveBaseInfoRelic.action" method="post">
                                <input type="hidden" name="index" value="${relicIndex}"/>
                                <input type="hidden" name="relicId" value="${relic.id?c}"/>

                                <div class="span10">
                                    <div class="span5">
                                    <@editEle "总登记号:" "totalCode" relic.totalCode />
                                    </div>
                                    <div class="span5 m-l-0">
                                    <@editEle "编目号:" "catalogCode" relic.catalogCode false/>
                                    </div>
                                </div>
                                <div class="span10">
                                    <div class="span5">
                                    <@editEle "分类号:" "typeCode" relic.typeCode/>
                                    </div>
                                    <div class="span5 m-l-0">
                                    <@editEle "名称:" "name" relic.name/>
                                    </div>
                                </div>
                                <div class="span10">
                                    <div class="span5 m-l-10">
                                    <@selectOption "时代:" "era" listEra relic.era.name relic.era.id/>
                                    </div>
                                    <div class="span5 m-l-10">
                                    <@editEle "件数:" "count" relic.count?c/>
                                    </div>
                                </div>
                                <div class="span10" style="height: 70px">
                                    <div class="span5 m-l-10">
                                    <@selectOption "级别:" "level" listLevel relic.level.name relic.level.id/>
                                    </div>
                                    <div class="span5 m-l-0">
                                    <@selectOption "文物质地:" "texture" listTexture relic.texture.name relic.texture.id/>
                                    </div>
                                </div>
                                <div class="span10">
                                    <div class="span5">
                        <span class="inline-block span1" style="width: 80px;text-align: right">
                            <label class="inline-block" for="zoneName">库房位次:</label>
                                </span>
                                        <span class="inline-block span3 m-l-10">
                            <input id="zoneId" type="hidden" name="zoneId"
                                   value="<#if relic.zone??>${relic.zone.id!}</#if>">
                            <input class="inline-block" id="zoneName" type="text" name="zoneName"
                                   value="<#if relic.zone??>${relic.zone.name!}<#else>(暂无)</#if>"
                                   style="width: 205px">
                            <span class="help-inline red"></span>
                        </span>
                                    </div>
                                    <div class="span5 m-l-0">
                                    <#if relic.state == 0>
                        <@editEle "文物状态:" "state" "在库" true/>
                     <#elseif relic.state == 1>
                                        <@editEle "文物状态:" "state" "待出库" true/>
                                    <#elseif relic.state == 2>
                                        <@editEle "文物状态:" "state" "出库" true/>
                                    </#if>
                                    </div>
                                </div>
                                <div class="span10">
                                    <div class="span5">
                                    <#if relic.hasTag ==false>
                         <@editEle "电子标签:" "hasTag" "无" true/>
                     <#elseif relic.hasTag ==true>
                                        <@editEle "电子标签:" "hasTag" "有" true/>
                                    </#if>
                                    </div>
                                </div>
                                <div class="edit-btn offset1 m-t-20">
                                    <button class="primary-edit btn btn-small btn-primary" type="submit">保存</button>
                                    <span class="cancel-edit btn btn-small">取消</span>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            <#-- 扩展属性 -->
            <#assign index = 0>
            <#if propertyDictionaryList?size != 0>
                <#list propertyDictionaryList as property>
                    <#if property["propertyType"] ==3 >
                        <#assign index = index+1>
                        <@archivesRow index 'new' property/>
                    <#else>
                        <@archivesRow -1 'new' property/>
                    </#if>
                </#list>
            </#if>
            <#-- 文物现状 -->
            <@editTemplate "31" "文物现状">
                <div class="view-content relic-row">
                    <table class="table table-bordered table-striped table-center" style="word-break:break-all; word-wrap:break-all;">
                        <thead>
                        <tr>
                            <th style="width: 50px;">序号</th>
                            <th style="width: 560px;">现状描述</th>
                            <th style="width: 100px;">现状日期</th>
                            <th style="width: 70px;">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                            <#if relic.statusQuos?size != 0>
                                <#list relic.statusQuos as statusquo>
                                <tr>
                                    <td>${statusquo_index+1}</td>
                                    <td style="text-align: left">${statusquo.quoInfo}</td>
                                    <td>${statusquo.quoDate?string("yyyy-MM-dd")}</td>
                                    <td>
                                        <button class="btn btn-mini delete-statusquo" value="${statusquo.id?c}"
                                                type="button">删除
                                        </button>
                                    </td>
                                </tr>
                                </#list>
                            </#if>
                        </tbody>
                    </table>
                </div>
                <div class="edit-content ">
                    <form action="addStatusquoRecordInfo.action" method="post">
                        <input type="hidden" name="index" value="${relicIndex}"/>
                        <input type="hidden" name="relicId" value="${relic.id?c}"/>
                        <input type="hidden" name="pageNum" value="27"/>

                        <div class="span3">
                            <@editEle "现状描述" "quoInfo" />
                            <div class="edit-content-ele">
                                <div class="ele-name">
                                    <label>现状日期</label>
                                </div>
                                <div class="ele-input">
                                    <input class="Wdate" type="text" name="quoDate"
                                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd '})"/>
                                    <span class="help-inline red"></span>
                                </div>
                            </div>
                            <button class="quoinfo-edit btn btn-small btn-primary" type="submit">保存</button>
                            <span class="btn btn-small cancel-edit">取消</span>
                        </div>
                    </form>
                </div>
            </@editTemplate>
            <#-- 鉴定记录 -->
            <@editTemplate "32" "鉴定记录">
                <div class="view-content relic-row">
                    <table class="table table-bordered table-striped table-center" style="word-break:break-all; word-wrap:break-all;">
                        <thead>
                        <tr>
                            <th style="width: 50px;">序号</th>
                            <th style="width: 360px;">鉴定意见</th>
                            <th style="width: 200px;">鉴定人</th>
                            <th style="width: 100px;">鉴定时间</th>
                            <th style="width: 70px;">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                            <#if relic.appraisals?size != 0>
                                <#list relic.appraisals as appraisal>
                                <tr>
                                    <td>${appraisal_index+1}</td>
                                    <td style="text-align: left;">${appraisal.expertOpinion}</td>
                                    <td>${appraisal.examiner}</td>
                                    <td>${appraisal.appraisalDate?string("yyyy-MM-dd")}</td>
                                    <td>
                                        <button class="btn btn-mini delete-appraisal" value="${appraisal.id?c}"
                                                type="button">删除
                                        </button>
                                    </td>
                                </tr>
                                </#list>
                            </#if>
                        </tbody>
                    </table>
                </div>
                <div class="edit-content ">
                    <form action="addAppraisalRecordInfo.action" method="post">
                        <input type="hidden" name="index" value="${relicIndex}"/>
                        <input type="hidden" name="relicId" value="${relic.id?c}"/>
                        <input type="hidden" name="pageNum" value="28"/>

                        <div class="span3">
                            <@editEle "鉴定意见" "expertOpinion" />
                            <@editEle "鉴定人" "examiner" />
                            <div class="edit-content-ele">
                                <div class="ele-name">
                                    <label>鉴定时间</label>
                                </div>
                                <div class="ele-input">
                                    <input class="Wdate" type="text" name="appraisalDate"
                                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                                    <span class="help-inline red"></span>
                                </div>
                            </div>
                            <button class="experopinion-edit btn btn-small btn-primary" type="submit">保存</button>
                            <span class="btn btn-small cancel-edit">取消</span>
                        </div>
                    </form>
                </div>
            </@editTemplate>
            <#-- 修复记录 -->
            <@editTemplate "33" "修复记录">
                <div class="view-content relic-row">
                    <table class="table table-bordered table-striped table-center" style="word-break:break-all; word-wrap:break-all;">
                        <thead>
                        <tr>
                            <th style="width:50px;">序号</th>
                            <th style="width:430px;">修复详情</th>
                            <th style="width:200px;">承制人</th>
                            <th style="width:100px;">修复日期</th>
                        </tr>
                        </thead>
                        <tbody>
                            <#if relic.restores?size != 0>
                                <#list relic.restores as restore>
                                <tr>
                                    <td>${restore_index+1}</td>
                                    <td style="text-align: left">${restore.restoreInfo}</td>
                                    <td>${restore.restorers}</td>
                                    <td>${restore.restoreDate?string("yyyy-MM-dd")}</td>
                                </tr>
                                </#list>
                            </#if>
                        </tbody>
                    </table>
                </div>
                <div class="edit-content ">
                    <form action="addRestoreRecordInfo.action" method="post">
                        <input type="hidden" name="index" value="${relicIndex}"/>
                        <input type="hidden" name="relicId" value="${relic.id?c}"/>
                        <input type="hidden" name="pageNum" value="29"/>

                        <div class="span3">
                            <@editEle "修复详情" "restoreInfo" />
                            <@editEle "承制人" "restorers" />
                            <div class="edit-content-ele">
                                <div class="ele-name">
                                    <label>修复日期</label>
                                </div>
                                <div class="ele-input">
                                    <input class="Wdate" type="text" name="restoreDate"
                                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                                    <span class="help-inline red"></span>
                                </div>
                            </div>
                            <button class="restore-edit btn btn-small btn-primary" type="submit">保存</button>
                            <span class="btn btn-small cancel-edit">取消</span>
                        </div>
                    </form>
                </div>
            </@editTemplate>
            <#-- 事故记录 -->
            <@editTemplate "34" "事故记录">
                <div class="view-content relic-row">
                    <table class="table table-bordered table-striped table-center" style="word-break:break-all; word-wrap:break-all;">
                        <thead>
                        <tr>
                            <th style="width: 50px;">序号</th>
                            <th style="width: 630px;">事故详情</th>
                            <th style="width: 100px;">事故记录日期</th>
                        </tr>
                        </thead>
                        <tbody>
                            <#if relic.accidents?size != 0>
                                <#list relic.accidents as accident>
                                <tr>
                                    <td>${accident_index+1}</td>
                                    <td style="text-align: left">${accident.accidentInfo}</td>
                                    <td>${accident.accidentDate?string("yyyy-MM-dd")}</td>
                                </tr>
                                </#list>
                            </#if>
                        </tbody>
                    </table>
                </div>
                <div class="edit-content ">
                    <form action="addAccidentRecordInfo.action" method="post">
                        <input type="hidden" name="index" value="${relicIndex}"/>
                        <input type="hidden" name="relicId" value="${relic.id?c}"/>
                        <input type="hidden" name="pageNum" value="30"/>

                        <div class="span3">
                            <@editEle "事故详情" "accidentInfo" />
                            <div class="edit-content-ele">
                                <div class="ele-name">
                                    <label>事故日期</label>
                                </div>
                                <div class="ele-input">
                                    <input class="Wdate" type="text" name="accidentDate"
                                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                                    <span class="help-inline red"></span>
                                </div>
                            </div>
                            <button class="accident-edit btn btn-small btn-primary" type="submit">保存</button>
                            <span class="btn btn-small cancel-edit">取消</span>
                        </div>
                    </form>
                </div>
            </@editTemplate>
            <#-- 流传经历 -->
            <@editTemplate "35" "流传经历">
                <div class="view-content relic-row">
                    <table class="table table-bordered table-striped table-center" style="word-break:break-all; word-wrap:break-all;">
                        <thead>
                        <tr>
                            <th style="width: 50px;">序号</th>
                            <th style="width: 630px;">流传经历描述</th>
                            <th style="width: 100px;">流传日期</th>
                        </tr>
                        </thead>
                        <tbody>
                            <#if relic.roves?size != 0>
                                <#list relic.roves as rove>
                                <tr>
                                    <td>${rove_index+1}</td>
                                    <td style="text-align: left">${rove.roveInfo}</td>
                                    <td>${rove.roveDate?string("yyyy-MM-dd")}</td>
                                </tr>
                                </#list>
                            </#if>
                        </tbody>
                    </table>
                </div>
                <div class="edit-content ">
                    <form action="addRoveInfo.action" method="post">
                        <input type="hidden" name="index" value="${relicIndex}"/>
                        <input type="hidden" name="relicId" value="${relic.id?c}"/>
                        <input type="hidden" name="pageNum" value="31"/>

                        <div class="span3">
                            <@editEle "流传经历" "roveInfo" "" false 2/>
                            <div class="edit-content-ele">
                                <div class="ele-name">
                                    <label>流传日期</label>
                                </div>
                                <div class="ele-input">
                                    <input class="Wdate" type="text" name="roveDate"
                                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                                    <span class="help-inline red"></span>
                                </div>
                            </div>
                            <button class="rove-edit btn btn-small btn-primary" type="submit">保存</button>
                            <span class="btn btn-small cancel-edit">取消</span>
                        </div>
                    </form>
                </div>
            </@editTemplate>
            <#-- 照片 -->
            <@editTemplate "36" "照片">
                <div class="view-content ">
                    <@imageEleView relic.photos "${picturesBasePath}/${siteId!}/${relic.id?c}/" "photos"/>
                </div>
                <div class="edit-content ">
                    <@imageEleEdit relic.photos "${picturesBasePath}/${siteId!}/${relic.id?c}/" "photo" "274423" "32"/>
                </div>
            </@editTemplate>
            <#-- 拓片 -->
            <@editTemplate "37" "拓片">
                <div class="view-content ">
                    <@imageEleView relic.rubbings "${picturesBasePath}/${siteId!}/${relic.id?c}/" "rubbings"/>
                </div>
                <div class="edit-content ">
                    <@imageEleEdit relic.rubbings "${picturesBasePath}/${siteId!}/${relic.id?c}/" "rubbing" "274424" "33"/>
                </div>
            </@editTemplate>
            <#-- 铭文题跋 -->
            <@editTemplate "38" "铭文题跋">
                <div class="view-content ">
                    <@imageEleView relic.inscriptions "${picturesBasePath}/${siteId!}/${relic.id?c}/" "inscription"/>
                </div>
                <div class="edit-content ">
                    <@imageEleEdit relic.inscriptions "${picturesBasePath}/${siteId!}/${relic.id?c}/" "inscription" "274425" "34"/>
                </div>
            </@editTemplate>
            <#-- 挂接文档 -->

            <@editTemplate "39" "挂接文档">
                <table class="table table-bordered table-striped table-center" style="word-break:break-all; word-wrap:break-all;">
                    <thead>
                    <th style="width: 50px;">序号</th>
                    <th style="width: 530px;">文档名称</th>
                    <th style="width: 100px;">文档上传日期</th>
                    <th style="width: 100px">操作</th>
                    </thead>
                    <tbody>
                        <#if relic.attachments?size != 0>
                            <#list relic.attachments as attachment>
                            <tr>
                                <td>${attachment_index+1}</td>
                                <td style="text-align: left">${attachment.path}</td>
                                <td>${attachment.attachmentDate?string("yyyy-MM-dd")}</td>
                                <td><a class="btn btn-mini btn-danger"
                                       href="doDeleteFile.action?index=${relicIndex}&relicId=${relic.id?c}&pageNum=35&attachmentId=${attachment.id}"><i
                                        class="icon-stop icon-white"></i>删除</a>
                                </td>
                            </tr>
                            </#list>
                        </#if>
                    </tbody>
                </table>
                <form action="uploadFile.action" method="post"
                      enctype="multipart/form-data" name="uploadfileform">
                    <input type="hidden" name="index" value="${relicIndex}"/>
                    <input type="hidden" name="relicId" value="${relic.id?c}"/>
                    <input type="hidden" name="pageNum" value="35"/>

                    <div class="fileupload-box m-t-20" id="fileupload-box">
                        <input type='button' class='fileupload-btn m-b-10' value='选择文件'/>
                        <input type='text' name='hideFileName' id='textfield' class='fileupload-txt m-b-0'
                               readonly="true"/>
                        <input type="file" name="srcUploadFile" class="fileupload-file" id="fileField" size="28"
                               onchange="document.getElementById('textfield').value=this.value"/>

                        <div><span class="help-inline red" id="uploadfilebtn"></span>
                        </div>
                        <div><span class="help-inline red">*单个挂接文档上传不能超过5M</span>
                        </div>
                        <button class="upload-file-btn btn btn-small btn-primary f-r" type="submit"
                                id="upload-file-btn">上传
                        </button>
                    </div>
                </form>
            </@editTemplate>
            </div>
        </div>
        <div class="span2">
            <div class="list p-5 m-l-40" style="position: fixed;overflow:auto; height:450px;width: 120px; top: 100px;">
                <ul>
                <#if propertyDictionaryList?size != 0>
                    <#list propertyDictionaryList as property>
                        <@jumpProperty property relic.id?c/>
                    </#list>
                </#if>
                <@jumpRecord relic.id?c/>
                </ul>
            </div>
        </div>
    </div>
</div>

<div class="hide">
    <div id="zoneTreeDialog" class="span4" style="height:400px;overflow:auto">
        <div id="zoneTree" class="ztree"></div>
        <p class="help-block m-t-10 red"></p>
    </div>
</div>
<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/magnificpopup/jquery.magnific-popup.min.js"></script>
<script type="text/javascript" src="../assets/kinkeditor/4.1.7/kindeditor-min.js"></script>
<script type="text/javascript" src="../assets/kinkeditor/4.1.7/lang/zh_CN.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../common/js/form-check.js"></script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>

<script type="text/javascript"
        src="../assets/jquery-ui/1.9.2/js/jquery-ui.custom.min.js"></script>
<script type="text/javascript" src="../orion/js/editRelicLabel.js"></script>
<@scriptTag "../common/js/util.js"/>
<@scriptTag "js/pnotify.js"/>

<script type="text/javascript">
    $(function () {
        //图片全屏
        $('.with-caption').magnificPopup({
            type: 'image',
            loseBtnInside: false,
            mainClass: 'mfp-with-zoom mfp-img-mobile',
            image : {
                verticalFit: true,
                titleSrc: function(item){
                    var pinItURL = "https://pinterest.com/pin/create/button/";
                    pinItURL += '?url=' + 'http://dimsemenov.com/plugins/magnific-popup/';
                    pinItURL += '&media=' + item.el.attr('href');
                    return ' &middot; <a class="pin-it" href="'+pinItURL+'" target="_blank"><img src="https://assets.pinterest.com/images/pidgets/pin_it_button.png" /></a>';
                }
            },
            gallery:{enabled:true}
        });

        $(".delete-statusquo").click(function () {
            var $this = $(this);
            art.dialog({
                id: "mess",
                fixed: true,
                title: "温馨提示",
                content: "确定要删除吗？",
                okValue: "确定",
                ok: function () {
                    var statusquoId = parseInt($this.val());
                    $.get("deleteStatusQuo", {"statusQuoId": statusquoId}, function (result) {
                        window.pnotifyInit();
                        if (result.success) {
                            $this.parent().parent().remove();
                            window.pnotify("删除文物现状成功!", "success");
                        } else {
                            window.pnotify("删除文物现状失败!", "error");
                        }
                    });
                },
                cancelValue: "取消",
                cancel: function () {
                }
            });
        });

        $(".delete-appraisal").click(function () {
            var $this = $(this);
            art.dialog({
                id: "mess",
                fixed: true,
                title: "温馨提示",
                content: "确定要删除吗？",
                okValue: "确定",
                ok: function () {
                    var appraisalId = parseInt($this.val());
                    $.get("deleteAppraisal", {"appraisalId": appraisalId}, function (result) {
                        window.pnotifyInit();
                        if (result.success) {
                            $this.parent().parent().remove();
                            window.pnotify("删除鉴定记录成功!", "success");
                        } else {
                            window.pnotify("删除鉴定记录失败!", "error");
                        }
                    });
                },
                cancelValue: "取消",
                cancel: function () {
                }
            });
        });
    });
</script>

<#--your js-->
<script type="text/javascript">
    $(function () {
        (function () {
        <#if _message??>
            <#if _success>
                window.pnotifyInit();
                window.pnotify("${_message}", "success");
            <#else>
                window.pnotifyInit();
                window.pnotify("${_message}", "error");
            </#if>
        </#if>
        })();

        //自动补全
        var availableLabels = [
        <#list allLabels as lab>
            "${lab.name}",
        </#list>];

        editRelicLabel(${relic.id?c}, availableLabels);

        //编辑和展示切换
        (function () {
            var $base = $("base");
            $base.attr("href", $base.attr("href") + "toUpdateRelic.action");
            $(document).on('click', '.row-edit', function () {
                var $this = $(this);
                openEdit($this);
            });
            $(document).on('click', '.upload-file-btn', function () {
                var flag = true;
                var $spantext = $("#uploadfilebtn");
                var $uploadfilebtn = $("#fileField");
                var file = $uploadfilebtn.val();
                if (file == "") {
                    App.helpError($spantext, "上传挂载文件不能为空");
                    flag = false;
                } else if (file != "" && !/.(gif|jpg|jpeg|png|doc|docx|xls|xlsx|pptx|ppt|txt|zip|rar)$/.test(file.toLowerCase())) {
                    App.helpError($spantext, "文件类型必须是图片,office文档或压缩包");
                    flag = false;
                } else {
                    App.helpOk($spantext, '');
                }
                return flag;
            });
            $(document).on('click', '.photo-edit', function () {
                var flag = true;
                var $spantext = $("#uploadphoto");
                var $imageInput = $("#imageInputphoto");
                var file = $imageInput.val();
                if (file == "") {
                    App.helpError($spantext, "上传图片不能为空");
                    flag = false;
                } else if (!/.(gif|jpg|jpeg|png)$/.test(file.toLowerCase())) {
                    App.helpError($spantext, "文件类型必须是gif、jpg、jpeg或png");
                    flag = false;
                } else {
                    App.helpOk($spantext, '');
                }

                return flag;
            });
            $(document).on('click', '.rubbing-edit', function () {
                var flag = true;
                var $spantext = $("#uploadrubbing");
                var $imageInput = $("#imageInputrubbing");
                var file = $imageInput.val();
                if (file == "") {
                    App.helpError($spantext, "上传图片不能为空");
                    flag = false;
                } else if (!/.(gif|jpg|jpeg|png)$/.test(file.toLowerCase())) {
                    App.helpError($spantext, "文件类型必须是gif、jpg、jpeg或png");
                    flag = false;
                } else {
                    App.helpOk($spantext, '')
                }
                return flag;
            });
            $(document).on('click', '.inscription-edit', function () {
                var flag = true;
                var $spantext = $("#uploadinscription");
                var $imageInput = $("#imageInputinscription");
                var file = $imageInput.val();
                if (file == "") {
                    App.helpError($spantext, "上传图片不能为空");
                    flag = false;
                } else if (!/.(gif|jpg|jpeg|png)$/.test(file.toLowerCase())) {
                    App.helpError($spantext, "文件类型必须是gif、jpg、jpeg或png");
                    flag = false;
                } else {
                    App.helpOk($spantext, '')
                }

                var $inscriptiontext = $("#inscriptiontext");
                var $fileImagetext = $("input[name='fileImagetext']");
                if (!$.trim($fileImagetext.val())) {
                    App.helpError($inscriptiontext, "铭文题跋不能为空")
                    flag = false;
                } else {
                    App.helpOk($inscriptiontext, '');
                }
                return flag;
            });
            $(document).on('click', '.quoinfo-edit', function () {//文物现状表单验证
                var $this = $(this);
                var $quoInfo = $("input[name='quoInfo']");
                var $quoDate = $("input[name='quoDate']");

                var flag1 = verifyIsNull($quoInfo, "现状描述不能为空");
                var flag2 = verifyIsNull($quoDate, "现状日期不能为空");

                if (flag1 && flag2) {
                    openEdit($this);
                } else {
                    return false;
                }
            });
            $(document).on('click', '.experopinion-edit', function () {//鉴定记录表单验证
                var $this = $(this);
                var $expertOpinion = $("input[name='expertOpinion']");
                var $examiner = $("input[name='examiner']");
                var $appraisalDate = $("input[name='appraisalDate']");

                var flag1 = verifyIsNull($expertOpinion, "鉴定意见不能为空");
                var flag2 = verifyIsNull($examiner, "鉴定人不能为空");
                var flag3 = verifyIsNull($appraisalDate, "鉴定日期不能为空");

                if (flag1 && flag2 && flag3) {
                    openEdit($this);
                } else {
                    return false;
                }
            });
            $(document).on('click', '.restore-edit', function () {//修复记录表单验证
                var $this = $(this);
                var $restoreInfo = $("input[name='restoreInfo']");
                var $restorers = $("input[name='restorers']");
                var $restoreDate = $("input[name='restoreDate']");

                var flag1 = verifyIsNull($restoreInfo, "修复详情不能为空");
                var flag2 = verifyIsNull($restorers, "承制人不能为空");
                var flag3 = verifyIsNull($restoreDate, "修复日期不能为空");

                if (flag1 && flag2 && flag3) {
                    openEdit($this);
                } else {
                    return false;
                }
            });
            $(document).on('click', '.accident-edit', function () {//事故记录表单验证
                var $this = $(this);
                var $accidentInfo = $("input[name='accidentInfo']");
                var $accidentDate = $("input[name='accidentDate']");

                var flag1 = verifyIsNull($accidentInfo, "事故信息不能为空");
                var flag2 = verifyIsNull($accidentDate, "事故日期不能为空");

                if (flag1 && flag2) {
                    openEdit($this);
                } else {
                    return false;
                }
            });
            $(document).on('click', '.rove-edit', function () {//流传经历表单验证
                var $this = $(this);
                var $roveInfo = $("textarea[name='roveInfo']");
                var $roveDate = $("input[name='roveDate']");
                var flag1 = verifyIsNull($roveInfo, "流传经历描述不能为空");
                var flag2 = verifyIsNull($roveDate, "流传日期不能为空");
                if (flag1 && flag2) {
                    openEdit($this);
                } else {
                    return false;
                }
            });

            $(document).on('click', '.extendProperty', function () {
                var $this = $(this);
                var index = $this.data("index");
                var editors = KindEditor.instances;
                var editor = editors[index - 1];
                var $extendProperty = $this.prev().prev().prev();
                if (editor) {
                    $extendProperty.val(editor.text());
                }
                var flag = verifyIsNull($extendProperty, "该属性不能为空");
                if ($this.attr("name") == "简述") {
                    App.helpOk($extendProperty.next(), '');
                    flag = true;
                }
                if (flag) {
                    openEdit($this);
                } else {
                    return false;
                }
            });
            $(document).on('click', '.primary-edit', function () {
                var $this = $(this);
                var $name = $("input[name='name']");
                var $totalCode = $("input[name='totalCode']");
                var $count = $("input[name='count']");
                var $zoneName = $("input[name='zoneName']");

                var flag1 = true;

                var flag2 = verifyIsNull($name, "文物名称不能为空");
                totalCodeFlag = false;
                checkTotalCode($totalCode);
                var flag3 = totalCodeFlag;
                var flag4 = verifyIsNull($zoneName, "库房位次不能为空");
                var reg = new RegExp("^[0-9]*$");
                if (!$.trim($count.val())) {
                    App.helpError($count.next(), "文物件数不能为空");
                    flag1 = false;
                } else if (!reg.test($count.val())) {
                    App.helpError($count.next(), "文物件数必须为数字");
                    flag1 = false;
                } else if ($count.val() < 1) {
                    App.helpError($count.next(), "文物件数必须为正整数");
                    flag1 = false;
                } else {
                    App.helpOk($count.next(), '');
                }

                if (flag1 && flag2 && flag3 && flag4) {
                    openEdit($this);
                } else {
                    return false;
                }
            });

            // “取消按钮”取消编辑
            $(document).on('click', '.cancel-edit', function () {
                var $relicRow = $(this).parents('.relic-row');
                openEdit2($relicRow);
            });
            var openEdit = function ($this) {
                var $edit = $this.parent().parent().find('.edit-content');
                var $view = $this.parent().parent().find('.view-content');
                $edit.toggle();
                $view.toggle();
            };

            var openEdit2 = function ($relicRow) {
                var $edit = $relicRow.find('.edit-content');
                var $view = $relicRow.find('.view-content');
                $edit.toggle();
                $view.toggle();
            };

            var verifyIsNull = function ($this, content) {
                if (!$.trim($this.val())) {
                    App.helpError($this.next(), content);
                    return false;
                } else {
                    App.helpOk($this.next(), '');
                    return true;
                }
            };

            var totalCodeFlag = false;
            var checkTotalCode = function ($this) {
                var totalCode = $this.val();
                var dataMess = $this.attr("data-mess");
                if ((totalCode.trim().length < 1) || (totalCode == dataMess)) {
                    App.helpOk($this.next(), '');
                    totalCodeFlag = true;
                    return true;
                }

                $.ajax({
                    url: 'validateTotalCode',
                    data: {'totalCode': totalCode},
                    cache: false,
                    async: false,
                    type: "Get",
                    dataType: 'json',
                    success: function (result) {
                        if (result.data == "true") {
                            App.helpOk($this.next(), '');
                            totalCodeFlag = true;
                        } else if (result.data == "false") {
                            App.helpError($this.next(), "总登记号已经存在");
                            totalCodeFlag = false;
                        } else {
                        }
                    }
                });
            };
        })();
        //list
        (function () {
            $(".edit-content").on("click", ".list-row-plus", function () {
                var $this = $(this);
                if (!$this.siblings(".list-row-delete")[0]) {
                    $this.before("<span class='list-row-delete btn btn-mini'><i class='icon-remove'></i></span>");
                }
                var $listRow = $this.parent().clone();
                var $listEle = $this.parent().parent();
                $listRow.find(".list-row-textarea").val("");
                $listEle.append($listRow);
                $this.remove();
            });
            $(".edit-content").on("click", ".list-row-delete", function () {
                var $this = $(this);
                var $listRow = $this.parent();
                var $listEle = $this.parent().parent();
                var eleCount = $listEle.children().length;
                if ($listEle.children().index($listRow) == eleCount - 1) {
                    $listEle.children().eq(eleCount - 2).append("<span class='list-row-plus btn btn-mini'><i class='icon-plus'></i></span>")
                }
                $listRow.remove();
                //如果删除后就剩一个元素，则不能在被删除
                if ($listEle.children().length == 1) {
                    $listEle.children().find(".list-row-delete").remove();
                }
            });
        })();
        //文本
        (function () {
            var editor;
            KindEditor.ready(function (K) {
                editor = K.create('.content-textarea', {
                    resizeType: 0,
                    allowPreviewEmoticons: true,
                    items: [
                        'undo', '|', 'redo', '|', 'justifyleft', '|',
                        'justifycenter', '|', 'justifyright', '|',
                        'justifyfull', '|', 'indent', '|', 'outdent', '|',
                        'formatblock', '|', 'fontname', '|', 'fontsize', '|',
                        'forcolor', '|', 'forecolor', '|', 'hilitecolor', '|', 'bold', '|',
                        'italic', '|', 'underline', '|', 'strikethrough', '|',
                        'removeformat', '|', 'link', '|', 'unlink', '|', 'lineheight'
                    ]
                });
            });
        })();
        //image
        (function () {
            $(".edit-content").on("click", ".image-plus", function () {
                var $this = $(this);
            });
            $(".myCarousel").carousel();
            $(".edit-content").on("click", ".image-delete", function () {
                var $this = $(this);
                $this.parent().parent().remove();
            });
        })();

        //区域
        (function () {
            // ztree 树配置
            var setting = {
                view: {
                    showLine: false
                },
                async: {
                    enable: true,
                    url: '../blackhole/zone/children.json',
                    autoParam: ["id=zoneId"]
                }
            };

            var showDialog = function ($zoneInput, zoneTree) {
                var $help = $("#zoneTreeDialog .help-block");
                art.dialog({
                    id: "zoneTreeDialog",
                    title: "选择区域",
                    content: $("#zoneTreeDialog")[0],
                    fixed: true,
                    okValue: "确定",
                    ok: function () {
                        var nodes = zoneTree.getSelectedNodes();
                        if (nodes.length == 0) {
                            $help.empty().append("请选择区域");
                            return false;
                        }
                        var node = nodes[0];
                        $zoneInput.val(node.name);
                        $("#zoneId").val(node.id);
                    },
                    cancelValue: "取消",
                    cancel: function () {
                        $help.empty();
                    },
                    button: [
                        {
                            value: "清空",
                            callback: function () {
                                $zoneInput.val('');
                                $("#zoneId").val('');
                            }
                        }
                    ]
                });
            };

            // 区域输入框获取焦点时
            $("#zoneName").focus(function () {
                var $this = $(this);
                $.getJSON("../blackhole/zone/children.json", {zoneId: ""}, function (result) {
                    // 初始化树
                    $.fn.zTree.init($('#zoneTree'), setting, result);
                    var zoneTree = $.fn.zTree.getZTreeObj("zoneTree");

                    // 初始化弹出框
                    showDialog($this, zoneTree);
                });
            });

        })();


        // 收藏单位 change
        (function () {
            var institutionId = $("#institutionValue").text();
            if (institutionId != "") {
                $.post("institution/institutions/" + institutionId, function (institution) {
                    $("#institutionValue").text(institution.name);
                });
            }

            var institutionRoomId = $("#storehouseValue").text();
            if (institutionRoomId != "") {
                $.post("institutionRoom/institutionRooms/" + institutionRoomId, function (institutionRoom) {
                    $("#storehouseValue").text(institutionRoom.roomName);
                });
            }

            // 收藏单位初始化
            (function initInstitution() {
                var $institution = $("#institution");
                var options = "<option value=''>请选择</option>";
                $.post("institution/institutions.json", function (institutions) {
                    if (institutions != null && institutions.length > 0) {
                        var selected = $institution.siblings('#propertyValue').val();
                        for (var i = 0; i < institutions.length; i++) {
                            if (selected == institutions[i].id) {
                                options += "<option value='" + institutions[i].id + "' selected='selected'>" + institutions[i].name + "</option>";
                            } else {
                                options += "<option value='" + institutions[i].id + "'>" + institutions[i].name + "</option>";
                            }
                        }
                    }
                    $institution.html(options);
                    $institution.change();
                });
            })();

            // 库房 和 收藏单位 二级联动
            $("#institution").change(function () {
                var $this = $(this);
                var institutionId = $this.val();
                if (institutionId == "") institutionId = $this.siblings('#propertyValue').val();
                if (institutionId == "") return;
                var options = "<option value=''>请选择</option>";
                $.post("institution/institutions/" + institutionId + "/storehouses.json", function (storehouses) {
                    if (storehouses != null && storehouses.length > 0) {
                        var selected = $("#storehouse").siblings('#propertyValue').val();
                        for (var i = 0; i < storehouses.length; i++) {
                            if (selected == storehouses[i].id) {
                                options += "<option value='" + storehouses[i].id + "' selected='selected'>" + storehouses[i].roomName + "</option>";
                            } else {
                                options += "<option value='" + storehouses[i].id + "'>" + storehouses[i].roomName + "</option>";
                            }
                        }
                    }
                    $("#storehouse").html(options);
                });
            });
        })();

    });
</script>

</body>
</html>
<#macro archivesRow index type property >
<div class="relic-row m-v-10 m-h-5 f-l">
    <div id="${property.orderNum}"></div>
    <div class="row-title">
        <h5 class="f-l m-b-5">${property.cnName}</h5>
        <a class="row-edit f-r" data-type="${type}">
            <i class="icon-edit"></i>
            <span>编辑本段</span>
        </a>
    </div>
    <div class="line"></div>
    <div class="row-content m-t-10 p-b-20">
        <div class="edit-content">
            <div>
                <form class="form-inline" action="toSavePropertyInfoRelic.action" method="post">
                    <label>${property.cnName}</label>
                    <input type="hidden" name="relicId" value="${relic.id?c}"/>
                    <input type="hidden" name="index" value="${relicIndex}"/>
                    <input type="hidden" name="propertyId" value="${property.propertyId}"/>
                    <input type="hidden" name="pageNum" value="${property.orderNum-1}"/>
                    <input type="hidden" id="propertyValue" value="${property.propertyValue}"/>

                    <#if property.propertyType==4>
                        <select class="extendProperty" name="propertyValue"
                                id="${property.enName}">
                        </select>
                    <#-- textarea -->
                    <#elseif property.propertyType == 3>
                        <textarea class="extendProperty content-textarea" name="propertyValue"
                                  style="width: 100%; height: 240px;">${property.propertyValue}</textarea>

                    <#-- date -->
                    <#elseif property.propertyType == 2>
                        <input class="extendProperty Wdate input-small" type="text" name="propertyValue"
                               onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${property.propertyValue}"/>

                    <#-- text -->
                    <#else>
                        <input class="extendProperty input-xxlarge input-small" type="text" name="propertyValue"
                               value="${property.propertyValue}"/>
                    </#if>
                    <div><span class="help-inline red"></span>
                    </div>
                    <input type="hidden" value="${property.relicProId!}" name="relicPropertyId"/>
                    <button class="extendProperty btn btn-small btn-primary" <#if property.cnName == "简述">
                            name="${property.cnName}" </#if> data-index="${index}" type="submit">保存
                    </button>
                    <span class="btn btn-small cancel-edit">取消</span>
                </form>
            </div>
        </div>
        <div class="view-content">
            <div class="p-l-20">
                <span id="${property.enName}Value">${property.propertyValue}</span>
            </div>
        </div>
    </div>
</div>
</#macro>
<#--image-->
<#macro imageEleView photoes path type="">
    <#if type == "inscription">
    <div>
    ${inscriptionDescription!}
    </div>
    </#if>
<div class="image-ele">
    <#if photoes?size != 0>
        <div id="myCarousel-${type}" class="carousel">
            <div class="carousel-inner">
                <#list photoes as photo>
                    <div class="item <#if photo_index==0>active</#if>" align="center">
                        <table>
                            <tr>
                                <td style="text-align: center;height: 350px;width: 780px">
                                    <a href="${path}${photo.path!}" class="with-caption image-link">
                                        <img src="${path}${photo.path!}"
                                             style="max-width: 780px;max-height: 350px;margin-top: 10px"/>
                                    </a>
                                    <#if type == "inscription">
                                        <div>
                                            <span>铭文题跋：${photo.info!}</span>
                                        </div>
                                    </#if>
                                </td>
                            </tr>
                        </table>
                    </div>
                </#list>
            </div>
            <a class="left carousel-control" href="?totalCode=${relic.totalCode}#myCarousel-${type}"
               data-slide="prev" style="top:50%;">‹</a>
            <a class="right carousel-control" href="?totalCode=${relic.totalCode}#myCarousel-${type}" data-slide="next"
               style="top:50%;">›</a>
        </div>
    </#if>
</div>

</#macro>
<#macro imageEleEdit photos path type="" modalId=0 pageNum=0>
    <#if type == "inscription">
    <div>
        <form action="addInscriptionDescription.action">
            <input type="hidden" name="index" value="${relicIndex}"/>
            <input type="hidden" value="${relic.id?c}" name="relicId"/>
            <input type="hidden" value="${pageNum}" name="pageNum"/>
            <textarea name="inscriptionDescription" id="" cols="30" rows="5"
                      style="width: 100%">${inscriptionDescription!}</textarea>
            <button class="btn btn-small btn-primary" type="submit">保存</button>
        </form>
    </div>
    </#if>
<div class="image-ele">
    <#list photos as photo>
        <div class="image-i" style="background-image: url(${path}${photo.path!});">
            <div class="delete-container">
                <a href="deletePhoto.action?index=${relicIndex}&type=${type}&relicId=${relic.id?c}&pageNum=${pageNum}&photoId=${photo.id?c}"><i
                        class="f-r icon-remove image-delete" title="删除"></i></a>
            </div>
        </div>
    </#list>

    <div class="image-i image-plus" title="添加图片">
        <a id="modal-${modalId}" href="#modal-container-${modalId}" role="button" class="btn" data-toggle="modal">
            <img src="images/icon/image-plus.png" alt=""/></a>
    </div>
    <div id="modal-container-${modalId}" class="modal hide fade" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <form action="uploadPhoto.action"
              method="post" enctype="multipart/form-data">
            <input type="hidden" name="index" value="${relicIndex}"/>
            <input type="hidden" value="${relic.id?c}" name="relicId"/>
            <input type="hidden" value="${pageNum}" name="pageNum"/>
            <input type="hidden" name="photoType" value="${type}"/>

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3 id="myModalLabel">
                    图片上传...
                </h3>
            </div>
            <div class="modal-body">
                <div class="edit-content-ele m-l-0">
                    <div class="ele-name">
                        <label>路径：</label>
                    </div>
                    <div class="ele-input">
                        <input type="file" name="fileImage" id="imageInput${type}" size="28"/>
                        <span class="help-inline red" id="upload${type}"></span>
                    </div>
                </div>
                <#if type == "inscription">
                    <div class="edit-content-ele m-l-0">
                        <div class="ele-name">
                            <label>铭文题跋：</label>
                        </div>
                        <div class="ele-input">
                            <input type="text" name="fileImagetext" id="imagetextInput" value=""/>
                            <span class="help-inline red" id="${type}text"></span>
                        </div>

                    </div>
                </#if>
            </div>
            <div class="modal-footer">
                <button class="${type}-edit btn btn-primary">上传</button>
                <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
            </div>
        </form>
    </div>
</div>
</#macro>

<#--基本信息 属性展示和编辑-->
<#macro viewEle name value="">
<div class="m-l-0">
    <div class="ele-name span2">
        <label>${name}</label>
    </div>
    <div class="ele-value span3">
        <span>${value}</span>
    </div>
</div>
</#macro>


<#macro editEle name enName value="" readOnly=false eleType=1>
<div class="edit-content-ele m-l-0">
    <div class="ele-name">
        <label>${name}</label>
    </div>
    <div class="ele-input">
        <#if eleType==1>
            <input type="text" name="${enName}" value="${value}" <#if enName == "totalCode">data-mess="${value}"</#if>
                   <#if readOnly >readOnly</#if>/>
            <span class="help-inline red"></span>
        <#elseif eleType==2>
            <textarea name="${enName}"
                      <#if enName == "totalCode">data-mess="${value}"</#if>
                      style="width: 100%; height: 100px;" <#if readOnly >readOnly</#if>>${value}</textarea>
            <span class="help-inline red"></span>
        </#if>
    </div>
</div>
</#macro>


<#macro selectOption name enName objList default="" defaultId=0>
<span class="inline-block span1" style="width:90px">
    <label style="text-align: right">${name}</label>
</span>
<span class="inline-block span3 m-l-10">
    <select style="width: 220px" name="${enName}" id="${enName}">
        <#if objList?size != 0>
            <#list objList as obj>
                <option value="${obj.id}" <#if obj.id==defaultId>selected="selected"</#if>>${obj.name}</option>
            </#list>
        </#if>
    </select>
</span>
</#macro>

<#macro jumpProperty property totalCode = "">
    <#assign url = basePath()+"/orion/toUpdateRelic.action?index=${relicIndex}&relicId=">
<li>
    <a href="${url}${relic.id?c}#${property.orderNum-1}">${property.cnName}</a>
</li>
</#macro>

<#macro editTemplate jumpId titleName>
<div class="relic-row m-v-10 m-h-5 f-l">
    <div class="row-title" id="${jumpId}">
        <h4 class="f-l m-b-5">${titleName}</h4>
        <a class="row-edit f-r">
            <i class="icon-edit"></i>
            <span>编辑本段</span>
        </a>
    </div>
    <div class="line"></div>
    <div class="row-content m-t-10">
        <#nested>
    </div>
</div>
</#macro>

<#macro jumpRecord relicId = "">
    <#assign url = basePath()+"/orion/toUpdateRelic.action?index=${relicIndex}&relicId=">
<li>
    <a href="${url}${relicId}#30">文物现状</a>
</li>
<li>
    <a href="${url}${relicId}#31">鉴定记录</a>
</li>
<li>
    <a href="${url}${relicId}#32">修复记录</a>
</li
<li>
    <a href="${url}${relicId}#33">事故记录</a>
</li>
<li>
    <a href="${url}${relicId}#34">流传经历</a>
</li>
<li>
    <a href="${url}${relicId}#35">照片</a>
</li>
<li>
    <a href="${url}${relicId}#36">拓片</a>
</li>
<li>
    <a href="${url}${relicId}#37">铭文题跋</a>
</li>
<li>
    <a href="${url}${relicId}#38">挂接文档</a>
</li>
</#macro>
