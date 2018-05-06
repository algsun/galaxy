<#--
外展管理，车辆设备信息配置

@author wang.geng
@date 2013-09-27
-->
<#assign title="车辆信息配置 - 外展管理">
<#-- 当前权限标识 -->

<#include "/common/pages/common-tag.ftl">
<#include  "../_common/helper.ftl">
<#macro head>
<link rel="stylesheet" href="../assets/select2/3.3.1/select2.css">
<style type="text/css">
    .table thead {
        background-color: #ececec;
    }

    .edit-content {
        float: left;
        width: 100%;
        display: none;
    }
</style>
</#macro>
<#macro content>

<div class="row">
    <div class="span12">
        <fieldset>
            <legend>
                <a class="go-back" href="queryExhibitionStateList/exhibition/${exhibitionId}" title="返回">
                    <i class="mw-icon-prev"></i>外展配置
                </a>
            </legend>
        </fieldset>
    </div>
</div>
<div class="tabbable m-t-30">
    <ul class="nav nav-tabs">
        <li class="active"><a href="#tab1" data-toggle="tab">车辆配置</a></li>
        <li><a href="#tab2" data-toggle="tab">其它配置</a></li>
    </ul>
    <div class="tab-content">
        <div class="tab-pane active" id="tab1">
            <#if !exhibitionStateVO??>
                <div class="m-b-20">
                    <a class="btn btn-large btn-info" style="padding-top: 6px;padding-bottom: 6px;"
                       href="exhibition/toAddCar/${exhibitionId}">新建车辆</a>
                </div>
            </#if>
            <div class="carList">
                <#if carVOList?? && (carVOList?size>0)>
                    <#list carVOList as carVO>
                        <table class="table table-bordered table-striped table-center">
                            <tr>
                                <td>
                                    <fieldset>
                                        <legend>
                                        ${carVO.plateNumber}
                                            <#if !exhibitionStateVO??>
                                                <div class="f-r">
                                                    <a class="btn btn-success btn-mini" title="修改"
                                                       href="exhibition/toEditCarInfo/${exhibitionId}/${carVO.id}"><i
                                                            class="icon-pencil icon-white"></i>编辑</a>
                                                    <button class="btnEnd btn btn-danger btn-mini" title="删除"
                                                            onclick="deleteClick(${carVO.id},${exhibitionId})" href="#">
                                                        <i
                                                                class="icon-trash icon-white"></i>删除
                                                    </button>
                                                </div>
                                            </#if>
                                        </legend>
                                    </fieldset>
                                    <div class="span5">
                                        <div>
                                            <span><strong>责任人:</strong></span>${carVO.director}
                                        </div>
                                        <div>
                                            <span><strong>责任人联系方式:</strong></span>${carVO.directorPhone}
                                        </div>
                                    </div>
                                    <div class="span5">
                                        <div>
                                            <span><strong>摄像机设备:</strong></span>
                                            <#if carVO.devicePOList?? && (carVO.devicePOList?size>0)>
                                                <#list carVO.devicePOList as devicePO>
                                                    <div class="m-l-40">
                                                    ${devicePO.dvName!devicePO.deviceId}
                                                    </div>
                                                </#list>
                                            </#if>
                                        </div>
                                        <div>
                                            <span><strong>位置点:</strong></span>
                                            <#if carVO.locationVOs?? && (carVO.locationVOs?size gt 0)>
                                                <#list carVO.locationVOs as location>
                                                    <div class="m-l-40">
                                                        <span title="${location.locationName}">${location.locationName}</span>
                                                    </div>
                                                </#list>
                                            </#if>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </#list>
                </#if>
            </div>
        </div>
        <div class="tab-pane" id="tab2">
            <div class="userConfig">
                <table class="table table-bordered table-striped table-center">
                    <tr>
                        <td>
                            <fieldset>
                                <legend>
                                    人员配置
                                    <#if !exhibitionStateVO??>
                                        <div class="f-r">
                                            <a class="row-edit btn btn-success btn-mini" title="修改"><i
                                                    class="icon-pencil icon-white"></i>编辑</a>
                                        </div>
                                    </#if>
                                </legend>
                            </fieldset>
                            <div class="m-v-10 m-h-5 f-l">
                                <div class="row-content m-t-10 p-b-20">
                                    <div class="edit-content" id="edit-content-user">
                                        <div>
                                            <form class="form-inline" action="exhibition/editUser/${exhibitionId}"
                                                  method="post">
                                                <div>
                                                    <@selectOption userList alarmUserList/>

                                                    <button class="primary-edit btn btn-small btn-primary"
                                                            type="submit">
                                                        保存
                                                    </button>
                                                    <span class="btn btn-small cancel-edit-user">取消</span>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="view-content" id="view-content-user">
                                        <div class="p-l-20">
                                            <#if alarmUserList?? && (alarmUserList?size>0)>
                                                <#list alarmUserList as user>
                                                    <div>
                                                    ${user.userName}
                                                    </div>
                                                </#list>
                                            </#if>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="alarmRangeConfig">
                <table class="table table-bordered table-striped table-center">
                    <tr>
                        <td>
                            <fieldset>
                                <legend>
                                    报警范围配置
                                    <#if !exhibitionStateVO??>
                                        <div class="f-r">
                                            <a class="row-edit btn btn-success btn-mini" title="修改"><i
                                                    class="icon-pencil icon-white"></i>编辑</a>
                                        </div>
                                    </#if>
                                </legend>
                            </fieldset>
                            <div class="m-v-10 m-h-5 f-l">
                                <div class="row-content m-t-10 p-b-20">
                                    <div class="edit-content" id="edit-content-range">
                                        <div>
                                            <form class="form-inline" action="exhibition/editAlarmRange/${exhibitionId}"
                                                  method="post">
                                                <label>报警范围:</label>
                                                <input id="alarmRange" class="input-xxlarge" type="text"
                                                       name="alarmRange"
                                                       value="${alarmRange}"/>
                                                <button class="alarmRange-btn primary-edit btn btn-small btn-primary"
                                                        type="submit">
                                                    保存
                                                </button>
                                                <span class="btn btn-small cancel-edit-range">取消</span>

                                                <div>
                                                    <span class="help-inline red" id="alarmRange-text"></span>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="view-content" id="view-content-range">
                                        <div class="p-l-20">
                                            <span>${alarmRange}公里</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>

</#macro>

<#macro script>
<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
<script type="text/javascript" src="../common/js/form-check.js"></script>
<script type="text/javascript">
    $(function () {

        //JS验证
        (function () {
            $(document).on('click', '.alarmRange-btn', function () {
                var $alarmRange = $("#alarmRange");
                var $alarmRangetext = $("#alarmRange-text");
                var reg = new RegExp("^(?:1|[1-9][0-9]?|100)$");
                if (!reg.test($alarmRange.val())) {
                    App.helpError($alarmRangetext, "报警范围必须为1-100的整数");
                    return false;
                } else {
                    App.helpOk($alarmRangetext, '');
                    return true;
                }
            });
        })();

        $("#userIds").select2();
        (function () {
            $(document).on('click', '.row-edit', function () {
                var $this = $(this);
                openEdit($this);
            });
            $(document).on('click', '.cancel-edit-user', function () {
                var $edit = $("#edit-content-user");
                var $view = $("#view-content-user");
                $edit.css("display", "none");
                $view.css("display", "block");
            });
            $(document).on('click', '.cancel-edit-range', function () {
                var $edit = $("#edit-content-range");
                var $view = $("#view-content-range");
                $edit.css("display", "none");
                $view.css("display", "block");
            });
        })();

        var openEdit = function ($this) {
            var $edit = $this.parent().parent().parent().parent().find('.edit-content');
            var $view = $this.parent().parent().parent().parent().find('.view-content');
            $edit.toggle();
            $view.toggle();
        };
    });
    var deleteClick = function (carId, exhibitionId) {
        art.dialog({
            title: '提示信息',
            content: "是否确认删除车辆？",
            okValue: "确定",
            cancelValue: '取消',
            ok: function () {
                window.location.href = "exhibition/deleteCar/" + exhibitionId + "/" + carId;
            },
            cancel: function () {

            }
        });
    };
</script>
</#macro>
<#macro selectOption userList alarmUserList>
<select multiple='multiple' style="width: 300px;margin-bottom: 10px"
        name="userIds" id="userIds">
    <#list userList as user>
        <#assign flag = "true">
        <#list alarmUserList as alarmUser>
            <#if alarmUser.id == user.id>
                <#assign flag = "false">
            </#if>
        </#list>
        <option value="${user.id}"
                <#if flag == "false">selected="true"</#if>>${user.userName}</option>
    </#list>
</select>
</#macro>