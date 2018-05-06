<#--
设备查询

@author li.jianfei
@date 2013-06-
@check @gaohui #4253 2013-06-20
-->

<#assign title>${locale.getStr("blueplanet.report.title")}</#assign>
<#include "/common/pages/common-tag.ftl">

<#macro head>

</#macro>


<#macro content>
<div class="row-fluid m-t-10">
    <div class="span12">
        <form class="form-inline well well-small" action="statistics/report" method="post">
            <label class="m-l-10">${locale.getStr("common.timeType")}</label>
            <label class="radio m-l-10">
                <input id="radioYear" name="dateType" type="radio" value="1" <@checked 1, dateType/>>
            ${locale.getStr("common.year")}
            </label>
            <label class="radio m-l-10">
                <input id="radioMonth" name="dateType" type="radio" value="2" <@checked 2,dateType/>>
            ${locale.getStr("common.month")}
            </label>
            <label class="radio m-l-10">
                <input id="radioWeek" name="dateType" type="radio" value="4" <@checked 4,dateType/>>
            ${locale.getStr("common.week")}
            </label>
            <label class="radio m-l-10">
                <input id="radioDay" name="dateType" type="radio" value="3" <@checked 3,dateType/>>
            ${locale.getStr("common.day")}
            </label>
            <label class="m-l-10">时间</label>
            <#local dateString="">
            <#if dateType==4>
                <input id="date" name="date" class="input-medium" type="text" value="${weekOfYear}">
            <#else>
                <#if dateType==1>
                    <#local dateString=date?string("yyyy")!>
                <#elseif dateType==2>
                    <#local dateString=date?string("yyyy-MM")!>
                <#elseif dateType==3>
                    <#local dateString=date?string("yyyy-MM-dd")!>
                </#if>
                <input id="date" name="date" class="input-medium" type="text" value="${dateString}">
            </#if>
            <input id="weekOfYear" type="hidden" value="${weekOfYear!}"/>
            <input type="hidden" name="week" id="week" value="${week?string("yyyy-MM-dd")!}"/>
            <img id="dateSel" src="../assets/my97-DatePicker/4.72/skin/datePicker.gif" width="16"
                 height="22">
            <button id="commit" class="btn">${locale.getStr("common.select")}</button>
            <span id="subscribe" class="btn pull-right">
                <#if weekReport>
                    ${locale.getStr("blueplanet.statistics.subscription")}
                <#else>
                    ${locale.getStr("blueplanet.statistics.unsubscription")}
                </#if></span>
        </form>

        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th>${locale.getStr("common.number")}</th>
                <th>${locale.getStr("common.monitoringIndicators")}</th>
                <th>${locale.getStr("blueplanet.statistics.maxValue")}</th>
                <th>${locale.getStr("blueplanet.statistics.minValue")}</th>
                <th>${locale.getStr("blueplanet.statistics.waveValue")}</th>
            </tr>
            </thead>
            <tbody>

                <#list reportVOList as report>
                <tr>
                    <td>${report_index + 1}</td>
                    <td>${report.sensorPhysicalName}(${report.units})</td>
                    <td>${report.maValue!"N/A"}</td>
                    <td>${report.minValue!"N/A"}</td>
                    <td>${report.waveValue!"N/A"}</td>
                </tr>
                </#list>

            </tbody>
        </table>

        <#if reportVOList?size == 0>
            <h4>${locale.getStr("common.noData")}</h4>
        </#if>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/moment/2.0.0/moment.min.js"></script>
    <@scriptTag "js/device/charts/date-format.js"/>
<script type="text/javascript">
    $(function () {

        $("#subscribe").click(function () {
            var $this = $(this);
            if ($this.text() == message.weeklyReport) {

                $.getJSON("statistics/report/addSubscribe.json", {subscribeType: 1}, function (success) {
                    if (success) {
                        $this.text(message.unsubscribe);
                    }
                });
            } else {

                $.getJSON("statistics/report/deleteSubscribe.json", {subscribeType: 1}, function (success) {
                    if (success) {
                        $this.text(message.weeklyReport);
                    }
                });
            }
        });
    });
    $(function () {
        //选择新的时间类型后 时间自动调整
        $("input[name='dateType']").change(function () {
            var $this = $(this);
            if (!$this.is(':checked')) {
                return;
            }

            var momentFormat;
            var my97Format;
            var dateType = $(this).val();
            switch (dateType) {
                case "1":
                    momentFormat = DateUtil.formats.YEAR;
                    my97Format = 'yyyy';
                    break;
                case "2":
                    momentFormat = DateUtil.formats.MONTH;
                    my97Format = 'yyyy-MM';
                    break;
                case "3":
                    momentFormat = DateUtil.formats.DAY;
                    my97Format = 'yyyy-MM-dd';
                    break;
                case "4":
                    momentFormat = DateUtil.formats.DAY
                    my97Format = 'yyyy-MM-dd';
                    break;
            }
            var $timeInput = $("#date");
            var value;
            if (dateType == "4") {
                value = moment().week();
                if ($("#weekOfYear").val() != "") {
                    value = $("#weekOfYear").val();
                }
            } else {
                value = DateUtil.formatDate(moment().add("d", -1).format("YYYY-MM-DD"), momentFormat);
                if ($timeInput.val().length > 2) {
                    value = DateUtil.formatDate($timeInput.val(), momentFormat);
                }
            }
            $timeInput.val(value);
            $("#dateSel").unbind("click");
            $("#dateSel").bind("click", function () {
                var elTmp;

                if (dateType == 4) {
                    elTmp = $("#week")[0];
                } else {
                    elTmp = $timeInput[0];
                }
                WdatePicker({
                    dateFmt: my97Format,
                    el: elTmp,
                    maxDate: '%y-%M-%d',
                    firstDayOfWeek: 1,
                    isShowWeek: function () {
                        if (dateType == 4) {
                            return true;
                        } else {
                            return false;
                        }
                    },
                    onpicked: function () {
                        if (dateType == 4) {
                            $timeInput.val($dp.cal.getP("W", "W"));
                            $dp.$("date").value = ($dp.cal.getP('W', 'W'));
                        }
                    }
                });
            });
        }).change();
    });
</script>
</#macro>

