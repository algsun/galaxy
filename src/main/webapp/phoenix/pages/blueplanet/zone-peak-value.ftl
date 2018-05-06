<#--
区域稳定性对比
@author wanggeng
@date 2013-08-06
@check 2013-08-14 zhangpeng svn:4923
-->
<#assign title="区域监测指标峰值 - 数据分析">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:blueplanet:peakValue">

<#include "/common/pages/common-tag.ftl">
<#include  "../_common/helper.ftl">

<#macro head>
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
</style>
</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span10">
        <div class="title page-header">
            <h3>区域监测指标峰值</h3>
        </div>
    </div>
</div>
<div>
    <form id="zonePeakValueFrom" class="well form-inline well-small" action="blueplanet/peakValue" method="post">
        <input id="sensorId" type="hidden" name="sensorId" value="${sensorId!}"/>
      时间<input id="date" name="date" class="input-medium Wdate m-r-10" type="text" onfocus="WdatePicker({dateFmt:'yyyy', maxDate: '%y-%M-%d'})" value="${date?string("yyyy")!}">
        类型<select  name="type" class="input-medium">
        <option value="0" <#if type??><#if type==0> selected="selected" </#if></#if>>
            全部
        </option>
        <option value="1" <#if type??><#if type==1> selected="selected" </#if></#if>>
            室内
        </option>
        <option value="2" <#if type??><#if type==2> selected="selected" </#if></#if>>
            室外
        </option>
    </select>
        <input type="submit" id="query-windrose-chart" class="btn" value="查询">
    </form>
</div>

<div>
    <#if (sens?size>0)>
        <table class="table table-bordered table-center">
            <tbody>
            <tr>
                <#list sens as sen>
                    <#if sen_index%4 == 0>
                    </tr>
                    <tr>
                    </#if>
                    <td><span id=${(sen.sensorPhysicalid)!} class="cbox"></span>${(sen.cnName)!}（${(sen.units)!}）</td>
                </#list>
            <#--补齐table空td-->
                <#if !(sens?size%4 == 0)>
                    <#list 1..(4-sens?size%4) as i>
                        <td></td>
                    </#list>
                </#if>
            </tr>
            </tbody>
        </table>
    </#if>
</div>

<div id="container">
</div>
    <#if zoneNames != "">
    <div>
        <@alertMsg "${backup!}"/>
    </div>
    </#if>

</#macro>

<#macro script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>

    <@scriptTag "js/index/date-format.js"/>

<script type="text/javascript">

    var zoneNames = evalFmt("${zoneNames!}");
    var peakValueData = evalFmt("${peakValueData!}");
    var zoneNamesDc = evalFmt("${zoneNamesDc!}");
    var subTitle = "${formatTime!}";
    var sensCnName = '【${sensCnName!}】区域对比统计';

    $(function () {
        if (zoneNames) {
            //最频繁
            hc_barView("container", {xd: zoneNames, yd: peakValueData}, sensCnName);

        } else {
            $("#container").html("<h4 class='m-l-20'>无数据</h4>");
        }
    });

    //字符转JSON
    function evalFmt(obj) {
        if (obj) {
            return eval('(' + obj + ')');
        }
    }
    ;

    function hc_barView(id, data, sensCnName) {
        $('#' + id).highcharts({
            credits: {enabled: false},
            chart: {
                type: 'column'
            },
            title: {
                text: sensCnName
            },
            subtitle: {
                text: subTitle
            },
            xAxis: {
                categories: data.xd ,
                labels: {
                    x: 15,//调节x偏移
                    y: 25,//调节y偏移
                    rotation: -30//调节倾斜角度偏移
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: ""
                }
            },
            legend: {
                backgroundColor: '#FFFFFF',
                reversed: false,
                enabled: false
            },
            plotOptions: {
                bar: {
                    dataLabels: {
                        enabled: true
                    }
                }
            },
            series: [
                {
                    name: '峰值',
                    data: data.yd
                }
            ]
        });
    }
    ;

    $(document).ready(function () {
        $("#${sensorId}").attr("class", "cboxed");

        $(".cbox").click(function () {
            $("#sensorId").val($(this).attr("id"));
            $("#zonePeakValueFrom").submit();
        });
    });
</script>

</#macro>