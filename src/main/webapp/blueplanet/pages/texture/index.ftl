<#assign title="质地阈值">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:monitor:topo">
<#macro head>

<link rel="stylesheet" href="../assets/select2/3.3.1/select2.css">
    <#include "../_common/common-css.ftl">
<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>

<style type="text/css">
    .cbox {
        width: 30px;
        height: 18px;
        display: inline-block;
        background-color: #CCC;
        border-radius: 5px;
        margin-right: 5px;
        cursor: pointer;
    }

    .cboxed {
        width: 30px;
        height: 18px;
        display: inline-block;
        background-color: blue;
        border-radius: 5px;
        margin-right: 5px;
    }

    [v-cloak] {
        display: none;
    }
</style>
</#macro>

<#macro content>
<div class="container-fluid">
    <#if _message?? >
        <div class="row-fluid">
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
    <div class="row-fluid">
        <div class="span12">
            <h4 style="text-align: center;" class="m-t">质地阈值设置${_message!} </h4>
            <table class="table table-bordered">
            <tr>
                <#list textures as texture>
                    <#if texture_index!=0 && texture_index %10 == 0>
                    </tr>
                    </#if>
                    <td style="width: 10%;<#if texture.setting>background-color:#BFEFFF</#if>  ">
                        <span id="${texture.id}" class="cbox"></span> ${texture.name}
                    </td>
                </#list>
                <#list 1..textures?size%10 as i>
                    <td style="width: 10%"></td>
                </#list>
                </tr>
            </table>
        </div>

        <div class="row-fluid">
            <div class="span6" id="vue${textureId}">
                <fieldset>
                    <legend>阈值条件</legend>
                    <table class="table table-striped">
                        <tr>
                            <th>指标</th>
                            <th>正常范围</th>
                            <th>操作</th>
                        </tr>
                        <tr v-for="textureThreshold in textureThresholds" v-cloak>
                            <td>{{textureThreshold.sensorName}}</td>
                            <td v-show="textureThreshold.conditionType==1">
                                {{textureThreshold.target-textureThreshold.floating}} --
                                {{textureThreshold.target+textureThreshold.floating}}
                            </td>
                            <td v-show="textureThreshold.conditionType==2"> > {{textureThreshold.target}}</td>
                            <td v-show="textureThreshold.conditionType==3"> < {{textureThreshold.target}}</td>
                            <td v-show="textureThreshold.conditionType==4"> <={{textureThreshold.target}}</td>
                            <td v-show="textureThreshold.conditionType==5"> >={{textureThreshold.target}}</td>
                            <td>
                                <button class="btn btn-mini btn-info update"
                                        data-sensorid="{{textureThreshold.sensorPhysicalId}}"
                                        data-conditionType="{{textureThreshold.conditionType}}"
                                        data-target="{{textureThreshold.target}}"
                                        data-floating="{{textureThreshold.floating}}"
                                        data-id="{{textureThreshold.id}}">
                                    编辑
                                </button>
                                <button class="btn btn-mini btn-danger delete"
                                        data-id="{{textureThreshold.id}}"
                                        data-sensorid="{{textureThreshold.sensorPhysicalId}}">删除
                                </button>
                            </td>
                        </tr>
                    </table>
                </fieldset>
            </div>
            <div class="span6">
                <form class="form-horizontal" id="form${textureId}">
                    <input type="hidden" name="textureThresholdVO.textureId" id="textureId" value="${textureId}"/>
                    <input name="flag" id="flag" type="hidden" value=""/>
                    <input type="hidden" id="id" value=""/>
                    <fieldset>
                        <legend>阈值生成器</legend>

                        <div class="control-group">
                            <label class="control-label">质地</label>

                            <div class="controls">
                                <input type="text" value="${texture.name}" readonly="readonly">
                            </div>
                        </div>


                        <div class="control-group">
                            <label class="control-label">监测指标</label>

                            <div class="controls">
                                <select name="textureThresholdVO.sensorPhysicalId" id="sensorId"
                                        class="sensorIds" style="width: 57%">
                                    <#list sensorinfos as sensorinfo>
                                        <option value="${sensorinfo.sensorPhysicalid?c}">${sensorinfo.cnName}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label">条件</label>

                            <div class="controls">
                                <select name="textureThresholdVO.conditionType"
                                        id="conditionType"
                                        class="conditionType">
                                    <option value="1">范围</option>
                                    <option value="2">大于</option>
                                    <option value="3">小于</option>
                                    <option value="4">大于等于</option>
                                    <option value="5">小于等于</option>
                                </select>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label">目标</label>

                            <div class="controls">
                                <input type="number" min="1" max="99999999" id="target"
                                       name="textureThresholdVO.target">
                                <span style="color: red"></span>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label">浮动</label>

                            <div class="controls">
                                <input type="number" min="1" max="99999999" id="floating"
                                       name="textureThresholdVO.floating">
                                <span style="color: red"></span>
                            </div>
                        </div>

                        <div class="control-group">
                            <div class="controls">
                                <label class="control-label"></label>
                                <input type="button" class="btn btn-primary insert"
                                       value="生成"/>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<script type="text/javascript" src="../assets/vue/1.0/vue.js"></script>
<script type="text/javascript" src="../assets/vue/1.0/vue-resource.js"></script>
<script type="text/javascript">
    $(function () {
        var textureId = $("#textureId").val();
        $("#" + textureId).attr("class", "cboxed");
        $(".cbox").click(function () {
            var $this = $(this);
            var id = $this.attr("id");
            window.location.href = "texture-threshold?textureId=" + id
        });

        $(".sensorIds").select2();

        var readyFun = function () {
            var vueModel = "#vue" + textureId;
            new Vue({
                el: vueModel,
                data: {
                    textureThresholds: ''
                },
                ready: function () {
                    //处理离线数据位置点监测指标
                    this.$http.get('findTextureThresholds', {'textureId': textureId},function (data) {
                        //直接将data set到 sensors中
                        this.$set('textureThresholds', data)
                    }).error(function (data, status, request) {
                                // handle error
                            });
                }
            })
        }

        readyFun();
        $(".insert").click(function () {

            var conditionType = $("#conditionType").val();
            var target = $("#target").val();
            var floating = $("#floating").val();
            if(conditionType ==1){
               if(target == ""){
                   $("#target").next().text("该字段必选。");
                   return;
               }else{
                   $("#target").next().text("");
               }

               if(floating == ""){
                   $("#floating").next().text("该字段必选。");
                   return;
               }else{
                   $("#floating").next().text("");
               }
            }else{
                if(target == ""){
                    $("#target").next().text("该字段必选。");
                    return;
                }else{
                    $("#target").next().text("");
                }
            }

            var textureId = $("#textureId").val();
            var str = $("#form" + textureId).serialize();
            var flag = $("#form" + textureId + " #flag").val();
            var url = "insert-texture-threshold?" + str;
            if (flag == "") {
                var isExits = "isExist-texture-threshold?" + str;
                $.get(isExits, function (result) {
                    if (result) {
                        $.get(url, function (data) {
                            if (data) {
                                window.location.href = "texture-threshold?textureId=" + textureId;
                            }
                        });
                    } else {
                        art.dialog({
                            id: 'info',
                            title: message.tips,
                            content: "已存在，请重新选择。"
                        });
                    }
                });
            } else {
                var deleteUrl = "delete-texture-threshold";
                var id = $("#form" + textureId + " #id").val();
                $.get(deleteUrl, {'id': id}, function (result) {
                    if (result) {
                        $.get(url, function (data) {
                            if (data) {
                                window.location.href = "texture-threshold?textureId=" + textureId;
                            }
                        });
                    }
                });
            }
        });

        $(".delete").live("click", function () {
            var $this = $(this);
            var id = $this.data("id");
            var sensorId = $this.data("sensorid");
            var textureId = $("#textureId").val();
            var url = "delete-texture-threshold";
            $.get(url, {'id': id, 'sensorId': sensorId, "textureId": textureId}, function (result) {
                if (result) {
                    var textureId = $("#textureId").val();
                    window.location.href = "texture-threshold?textureId=" + textureId;
                }
            });
        });

        $(".update").live("click", function () {
            var $this = $(this);
            var sensorId = $this.data("sensorid");
            var conditionType = $this.data("conditiontype");
            var target = $this.data("target");
            var floating = $this.data("floating");
            var id = $this.data("id");
            var textureId = $("#textureId").val();
            $("#form" + textureId + " #sensorId").select2("val", sensorId);
            $("#form" + textureId + " #conditionType").val(conditionType);
            $("#form" + textureId + " #target").val(target);
            var $floatInput = $("#form" + textureId + " #floating");
            if (conditionType == 1) {
                $floatInput.removeAttr("readonly");
                $floatInput.val(floating);
            } else {
                $floatInput.attr("readonly", "readonly");
                $floatInput.val("");
            }
            $("#form" + textureId + " #id").val(id);
            $("#form" + textureId + " #flag").val("true");
        });

        $(".conditionType").change(function () {
            var $this = $(this);
            var conditionId = $this.val();
            var $input = $this.parent().parent().next().next().find("input");
            if (conditionId != 1) {
                $input.attr("readonly", "readonly");
                $input.val("");
            } else {
                $input.removeAttr("readonly");
            }
        });
    });
</script>
</#macro>


