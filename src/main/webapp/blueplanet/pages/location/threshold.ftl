<#--
位置点阈值设置

@author liuzhu
@date 2014-6-26
-->
<#assign title=locale.getStr("blueplanet.location.threshold")>
<#include "/common/pages/common-tag.ftl">


<#macro head>
<link rel="stylesheet" href="../assets/select2/3.3.1/select2.css">
<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
<link href="../assets/font-awesome/3.2.1/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<style>
    [v-cloak] {
        display: none;
    }
    .errSpan{
        font-size: 15px;
        color: #FF0000;
    }
</style>
</#macro>


<#assign tabIndex = 15>
<#include "../device/device-helper-b4.ftl">
<#macro content>
<div id="locationVue" class="row-fluid m-t-1">
    <input name="locationId" id="locationId" type="hidden" value="${locationId}"/>
    <div class="col-md-6">
        <fieldset>
            <legend>阈值条件</legend>
            <table class="table table-striped table-sm">
                <thead>
                <tr>
                    <th>监测指标</th>
                    <th>正常范围</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="threshold in thresholds" v-cloak>
                    <td>{{threshold.sensorName}}({{threshold.sensorUnit}})</td>
                    <td v-show="threshold.conditionType==1">
                        {{threshold.target-threshold.floating}} --
                        {{threshold.target+threshold.floating}}
                    </td>
                    <td v-show="threshold.conditionType==2"> > {{threshold.target}}</td>
                    <td v-show="threshold.conditionType==3"> < {{threshold.target}}</td>
                    <td v-show="threshold.conditionType==4"> <={{threshold.target}}</td>
                    <td v-show="threshold.conditionType==5"> >={{threshold.target}}</td>
                    <td>
                        <button class="btn btn-sm btn-info update"
                                data-sensor-id="{{threshold.sensorPhysicalId}}"
                                data-condition-type="{{threshold.conditionType}}"
                                data-target="{{threshold.target}}"
                                data-floating="{{threshold.floating}}"
                                data-id="{{threshold.id}}">
                            编辑
                        </button>
                        <button class="btn btn-sm btn-danger delete"
                                data-sensor-id="{{threshold.sensorPhysicalId}}">删除
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </fieldset>
    </div>
    <div class="col-md-6">
        <form class="form-horizontal" id="conditionForm">
            <input name="flag" id="flag" type="hidden" value=""/>
            <input type="hidden" id="id" value=""/>
            <fieldset>
                <legend>阈值生成器</legend>
                <div class="form-group row">
                    <label for="sensorId" class="col-sm-3 form-control-label">监测指标</label>
                    <div class="col-sm-6">
                        <select name="threshold.sensorPhysicalId" id="sensorId"
                                class="sensorIds c-select form-control">
                            <#list _sensorinfoesOfLocation as sensorinfo>
                                <option value="${sensorinfo.sensorPhysicalid?c}">${sensorinfo.cnName}</option>
                            </#list>
                        </select>
                    </div>
                </div>

                <div class="form-group row">
                    <label for="conditionType" class="col-sm-3 form-control-label">条件</label>

                    <div class="col-sm-6">
                        <select name="threshold.conditionType"
                                id="conditionType"
                                v-model="conditionType"
                                class="threshold.conditionType c-select form-control">
                            <option value="1">范围</option>
                            <option value="2">大于</option>
                            <option value="3">小于</option>
                            <option value="4">大于等于</option>
                            <option value="5">小于等于</option>
                        </select>
                    </div>
                </div>

                <div class="form-group row">
                    <label for="target" class="col-sm-3 form-control-label">目标</label>
                    <div class="col-sm-6">
                        <input type="number" min="1" max="99999999" id="target" name="threshold.target"
                               class="form-control">
                    </div>
                    <span id="targetSpan" class="errSpan"></span>
                </div>

                <div class="form-group row">
                    <label for="floating" class="col-sm-3 form-control-label">浮动</label>

                    <div class="col-sm-6">
                        <input type="number" min="1" max="99999999" id="floating" name="threshold.floating"
                               class="form-control">
                    </div>
                    <span id="floatingSpan" class="errSpan"></span>
                </div>
                <input type="hidden" name="threshold.locationId" value="${locationId}">
                <input type="hidden" name="threshold.thresholdType" value="0">

                <div class="form-group row">
                    <div class="col-sm-offset-3 col-sm-7">
                        <input id="btnSave" type="button" class="btn btn-primary"
                               value="生成"/>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
</div>
</#macro>



<#macro script>
<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<script type="text/javascript" src="../assets/vue/1.0/vue.js"></script>
<script type="text/javascript" src="../assets/vue/1.0/vue-resource.js"></script>
<script type="text/javascript" src="js/pnotify.js"></script>
<script type="text/javascript">
    $(function () {
        Vue.http.options.emulateHTTP = true;
        Vue.http.options.emulateJSON = true;
        $(".condition").change(function () {
            var $this = $(this);
            var conditionId = $this.val();
            var $input = $this.parent().parent().next().next().find("input");
            if (conditionId != 1) {
                $input.attr("readonly", "readonly");
            } else {
                $input.removeAttr("readonly");
            }
        });

        var locationId = $("#locationId").val();
        new Vue({
            el: "#locationVue",
            data: {
                thresholds: [],
                threshold: {},
                conditionType: 1
            },
            methods: {
                conditionChange: function () {
                    this.conditionType = $("#conditionType").val();
                }
            },
            ready: function () {
                //处理离线数据位置点监测指标
                this.$http.get('location/' + locationId + '/thresholds', function (data) {
                    //直接将data set到 sensors中
                    this.$set('thresholds', data)
                }).error(function (data, status, request) {
                    // handle error
                });
            }
        });


        $("#btnSave").click(function () {

            var conditionType = $("#conditionType").val();
            var target = $("#target").val();
            var floating = $("#floating").val();
            if(conditionType ==1){
                if(target == ""){
                    $("#targetSpan").text("该字段必选。");
                    return;
                }else{
                    $("#targetSpan").text("");
                }

                if(floating == ""){
                    $("#floatingSpan").text("该字段必选。");
                    return;
                }else{
                    $("#floatingSpan").text("");
                }
            }else{
                if(target == ""){
                    $("#targetSpan").text("该字段必选。");
                    return;
                }else{
                    $("#targetSpan").text("");
                }
            }

            var str = $("#conditionForm").serialize();
            var flag = $("#conditionForm #flag").val();
            var url = "location/thresholds/add?" + str;
            if (flag == "") {
                var isExits = "location/thresholds/exists?" + str;
                $.get(isExits, function (result) {
                    if (!result) {
                        art.dialog({
                            id: 'info',
                            title: message.tips,
                            content: "已存在，请重新选择。"
                        });
                        return;
                    }
                });
            }
                var deleteUrl = "location/thresholds/delete";
                var sensorId = $("#conditionForm #sensorId").val();
                $.get(deleteUrl, {'locationId': locationId, 'sensorId': sensorId}, function (result) {
                    if (result) {
                        $.get(url, function (data) {
                            if (data) {
                                window.location.href = "location/" + locationId + "/threshold";
                            }
                        });
                    }
                });
        });

        $(document).on("click", ".delete", function () {
            var $this = $(this);
            var sensorId = $this.data("sensorId");
            var url = "location/thresholds/delete";
            $.get(url, {'locationId': locationId, 'sensorId': sensorId}, function (result) {
                if (result) {
                    art.dialog({
                        id: 'info',
                        title: message.tips,
                        content: "删除成功"
                    });
                    $this.parent().parent().remove();
                }
            });
        });

        $(document).on("click", ".update", function () {
            var $this = $(this);
            var sensorId = $this.data("sensorId");
            var conditionType = $this.data("conditionType");
            var target = $this.data("target");
            var floating = $this.data("floating");
            var id = $this.data("id");
            var textureId = $("#textureId").val();
            $("#conditionForm #sensorId").val(sensorId);
            $("#conditionForm #conditionType").val(conditionType);
            $("#conditionType").change();
            $("#conditionForm #target").val(target);
            $("#conditionForm #floating").val(floating);
            $("#conditionForm #id").val(id);
            $("#conditionForm #flag").val("true");
        });
    });
</script>
<script type="text/javascript">
    var BluePlanet = App("blueplanet");
    BluePlanet.zoneLocationPath.redirectWhenLocationChange(function (location) {
        return 'location/' + location.id + "/threshold";
    });
</script>
</#macro>
