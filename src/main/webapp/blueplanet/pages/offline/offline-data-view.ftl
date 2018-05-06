<#--
离线位置点数据视图页面

@author liuzhu
@date 2016-4-14
-->
<#assign title>${locale.getStr("blueplanet.offline.title")}</#assign>
<#-- 当前权限标识 -->

<#include "/common/pages/common-tag.ftl">

<#macro head>
<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
<style>
    [v-cloak] {
        display: none;
    }
</style>
</#macro>

<#macro content>
<div class="container-fluid">
    <input name="locationId" id="locationId" value="${locationId}" type="hidden"/>

    <div class="row-fluid">
        <div class="span12" id="offlineData">
            <div class="row-fluid">
                <div class="span12" style="text-align: center;">
                    <i class="icon-arrow-left f-l" onclick="window.history.back()" style=""></i>
                    <span style="text-align: center;font-size: 22px;">
                    ${location.locationName}
                    </span>
                </div>
            </div>
            <table class="table table-bordered table-striped">
                <thead>
                <tr>
                    <th>${locale.getStr("common.number")}</th>
                    <th v-for="sensor in sensors" v-cloak>
                        {{sensor.cnName}}({{sensor.units}})
                    </th>
                    <th>${locale.getStr("common.time")}</th>
                    <th>${locale.getStr("common.operating")}</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td></td>
                    <td v-for="sensor in sensors" v-cloak>
                        <input style="width: 100px;" type="text" id="{{sensor.sensorPhysicalid}}"
                               data-sensor="{{sensor.sensorPhysicalid}}" data-toggle="popover"
                               data-placement="bottom" data-content="请输入数字！"
                               title="" data-original-title="温馨提示"/>
                    </td>
                    <td>
                        <input id="stamp" class="input-medium Wdate" type="text"
                               onfocus="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                               value="" v-model="stamp"
                               data-toggle="popover"
                               data-placement="bottom" data-content="请选择时间！"
                               title="" data-original-title="温馨提示"/>
                    </td>
                    <td>
                        <button class="btn btn-primary btn-mini" v-on:click="add">
                            <i class="icon-plus"></i>
                        </button>
                    </td>
                </tr>

                <tr v-for="locationSensor in locationSensors | orderBy 'date' -1" v-cloak>
                    <td>{{ $index+1 }}</td>
                    <td v-for="sensor in sensors">
                        {{ locationSensor | locationSensorValueFilter sensor}}
                    </td>
                    </td>
                    <td>{{locationSensor.date}}</td>
                    <td>
                        <button class="btn btn-danger btn-mini" v-on:click="remove($index,locationSensor.date)">
                            <i class="icon-remove"></i>
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/jquery-form-plugin/3.19/jquery.form.js"></script>
<script type="text/javascript" src="../assets/vue/1.0/vue.js"></script>
<script type="text/javascript" src="../assets/vue/1.0/vue-resource.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
    <@scriptTag "js/pnotify.js"/>
<script type="text/javascript">
    $(function () {
        //vue过滤器，输出监测指标值
        Vue.filter('locationSensorValueFilter', function (locationSensor, sensor) {
            var sensor = locationSensor[sensor.sensorPhysicalid];
            if (typeof(sensor) != "undefined" && sensor != null) {
                if (sensor.state == 1) {
                    return sensor.sensorValue;
                } else {
                    return "--"
                }
            } else {
                return "";
            }
        });

        var vue = new Vue({
            // TODO
            el: '#offlineData',
            data: {
                locationSensors: [],
                sensors: [],
                stamp: ""
            },
            // 在 `methods` 对象中定义方法
            methods: {

                //添加按钮事件
                add: function () {
                    //操作后台数据库添加数据
                    var locationSensors = [];
                    var locationId = $("#locationId").val();
                    //组织json数据
                    for (var i = 0; i < this.sensors.length; i++) {
                        var sensorId = "#" + this.sensors[i].sensorPhysicalid;
                        var locationSensor = {};
                        locationSensor.locationId = locationId;
                        locationSensor.sensorPhysicalid = $(sensorId).data("sensor");
                        locationSensor.sensorValue = $(sensorId).val().trim();
                        locationSensor.stamp = this.stamp;
                        locationSensor.state = 1;
                        //验证
                        if (locationSensor.sensorValue == "" || isNaN(locationSensor.sensorValue)) {
                            $(sensorId).popover('toggle');
                            return;
                        } else {
                            $(sensorId).popover('destroy');
                        }
                        if (locationSensor.sensorValue != "") {
                            locationSensors.push(locationSensor);
                        }
                    }
                    if (this.stamp.trim() == "") {
                        $("#stamp").popover('toggle');
                        return;
                    } else {
                        $("#stamp").popover('destroy');
                    }

                    //ajax insert离线位置点监测指标数据
                    var locationSensorJson = JSON.stringify(locationSensors);
                    console.log(locationSensorJson);
                    this.$http.get('offline/insert.json', {locationSensorJson: locationSensorJson},function (data) {
                        window.location.reload();
                    }).error(function (data, status, request) {
                                // handle error
                                window.pnotify("添加失败，所有输入框均为必须，且为数字类型。", "error");
                            })
                },
                remove: function (index, date) {
                    //todo删除数据用的数据,如果删除成功则继续删除表格中的数据
                    var locationId = $("#locationId").val();
                    this.$http.get('offline/deleteOfflineData.json', {"locationId": locationId, 'date': date},function (data) {
                        //直接将data set到 sensors中
                        if (data) {
                            window.pnotify("删除成功", "info");
                            this.locationSensors.splice(index, 1)
                        }
                    }).error(function (data, status, request) {
                                //handle error
                                window.pnotify("删除失败", "error");
                            });
                }
            },
            ready: function () {
                //处理离线数据位置点监测指标
                var locationId = $("#locationId").val();
                this.$http.get('offline/getSensors.json', {'locationId': locationId},function (data) {
                    //直接将data set到 sensors中
                    this.$set('sensors', data)
                }).error(function (data, status, request) {
                            // handle error
                        });

                //处理离线位置点数据
                this.$http.get('offline/getOfflineData.json', {'locationId': locationId},function (locationDataList) {
                    var locationSensors = [];
                    //循环遍历处理数据
                    for (var data in locationDataList) {
                        var location = {};
                        //格式化时间
                        var stamp = moment(locationDataList[data].stamp);
                        var date = stamp.format("YYYY-MM-DD HH:mm:ss")
                        location["date"] = date;
                        //组织监测指标数据 格式为  { TMT: '{sensorValue:32,state:1}', HUM: "{sensorValue:12,state:1}", date: "2016-4-12 00:00:00" },
                        var sensors = locationDataList[data].locationDataVOs;
                        for (var sensor in sensors) {
                            var sensorTemp = {};
                            sensorTemp["sensorValue"] = sensors[sensor].sensorPhysicalValue;
                            sensorTemp["state"] = sensors[sensor].state;
                            location[sensors[sensor].sensorPhysicalid] = sensorTemp;
                        }
                        locationSensors.push(location);
                    }
                    this.$set('locationSensors', locationSensors);
                }).error(function (data, status, request) {
                            // handle error
                        })
            }
        });
    });
</script>
</#macro>

