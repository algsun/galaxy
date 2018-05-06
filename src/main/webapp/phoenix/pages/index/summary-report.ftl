<#--
综合报告
@author li.jianfei
@date 2013-07-25
@check @duan.qixin 2013年7月29日 #4734
-->
<#assign title="综合报告 - 数据分析">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:index:summaryReport">

<#include  "../_common/helper.ftl">
<#include "/common/pages/common-tag.ftl">

<#macro head>
<style type="text/css" xmlns="http://www.w3.org/1999/html">
    .m-t-100 {
        margin-top: 100px;
    }

    h4 {
        color: dimgray;
    }

    .large-font {
        font-size: 1.5em;
    }

    .reportContent {
        line-height: 2.5em;
    }
</style>

</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span10">
        <div class="title page-header">
            <h3>综合报告</h3>
        </div>
    </div>
</div>
<div class="m-b-10">
    <form class="form-inline well well-small" action="index/summaryReport" method="post">
        <#include "../_common/date-year-month-control.ftl">
        <button id="commit" type="submit" class="btn">查询</button>
        <span id="subscribe" class="btn pull-right" title="每月1日早晨发送邮件"><#if monthReport>取消订阅<#else>订阅</#if></span>
        <abbr class="pull-right m-t-5 m-r-10" for="subscribe" title="每月1日早晨发送邮件">
            <#if monthReport>
                已订阅<i class="icon-question-sign"></i>
            </#if>
        </abbr>
    </form>
</div>


    <#if zoneDataList??>
    <#--区域对比统计结论-->
        <#if (zoneDataList?size>0)>
        <div class="row-fluid m-t-100">
            <div class="span12">
                <div class="row-fluid yahei-font">
                    <div class="span2">
                        <img src="images/summaryReport/zoneConstrast.png">
                    </div>
                    <div class="offset1 span9">
                        <h4>区域对比统计</h4>
                        <blockquote class="reportContent">
                            <h5>室内</h5>
                            <#list indoorZoneDataList as zoneDataMap>
                                <#list zoneDataMap?keys as sensorInfo>
                                    <#assign zoneDatas=zoneDataMap.get(sensorInfo)>
                                    <span class="bold text-error large-font">${sensorInfo.cnName}</span>最高的区域是<span
                                        class="bold text-error large-font">${zoneDatas[0].zoneName}</span>
                                    <#if zoneDatas[0].maxTime?? >发生时刻是${zoneDatas[0].maxTime!}</#if>
                                    <br/>
                                ${sensorInfo.cnName}最低的区域是<span
                                        class="bold text-error large-font">${zoneDatas[zoneDatas?size-1].zoneName}</span>
                                    <#if zoneDatas[zoneDatas?size-1].minTime?? >
                                        发生时刻是${zoneDatas[zoneDatas?size-1].minTime!}</#if>
                                    <br/>
                                </#list>
                            </#list>
                            <hr>
                            <h5>室外</h5>
                            <#list outdoorZoneDataList as zoneDataMap>
                                <#list zoneDataMap?keys as sensorInfo>
                                    <#assign zoneDatas=zoneDataMap.get(sensorInfo)>
                                    <span class="bold text-error large-font">${sensorInfo.cnName}</span>最高的区域是<span
                                        class="bold text-error large-font">${zoneDatas[0].zoneName}</span>
                                    <#if zoneDatas[0].maxTime?? >发生时刻是${zoneDatas[0].maxTime!}</#if>
                                    <br/>
                                ${sensorInfo.cnName}最低的区域是<span
                                        class="bold text-error large-font">${zoneDatas[zoneDatas?size-1].zoneName}</span>
                                    <#if zoneDatas[zoneDatas?size-1].minTime?? >
                                        发生时刻是${zoneDatas[zoneDatas?size-1].minTime!}</#if>
                                    <br/>
                                </#list>
                            </#list>
                            <small><a href="zoneContrast.action" class="muted">更多</a></small>
                        </blockquote>
                    </div>
                </div>
            </div>
        </div>
        </#if>
    </#if>


<#--文物出库排名结论-->
    <#if relicFrequencyByCondition??>
    <div class="row-fluid m-t-100">
        <div class="span12">
            <div class="row-fluid yahei-font">
                <div class="span2">
                    <img src="images/summaryReport/chuku.png">
                </div>
                <div class="offset1 span9">
                    <h4>文物出库排名</h4>
                    <blockquote
                            class="reportContent"><#if dateType==1>${date?string("yyyy年")}<#else>${date?string("yyyy年MM月")}</#if>
                        出库次数最多的文物是<span
                                class="bold text-error large-font">${relicFrequencyByCondition.relicName}
                            (${relicFrequencyByCondition.relicTotalCode})</span>，
                        总计出库次数最多的文物是<span class="bold text-error large-font">${relicFrequencyOfAll.relicName}
                            (${relicFrequencyOfAll.relicTotalCode})</span>。
                        <small><a href="orion/relic-out-ranking" class="muted">更多</a></small>
                    </blockquote>
                </div>
            </div>
        </div>
    </div>
    </#if>


<#--出库事件统计结论-->
    <#if outEventStats?? && outEventStats.yearSum?? >
    <div class="row-fluid m-t-100">
        <div class="span12">
            <div class="row-fluid yahei-font">
                <div class="span2">
                    <img src="images/summaryReport/relicOut.png">
                </div>
                <div class="offset1 span9">
                    <h4>出库事件统计</h4>
                    <blockquote
                            class="reportContent"><#if dateType==1>${date?string("yyyy年")}<#else>${date?string("yyyy年MM月")}</#if>
                        共有<span
                                class="bold text-error large-font">${outEventStats.yearCount}</span>次文物出库，总计<span
                                class="bold text-error large-font">${outEventStats.yearSum!0}</span>件。
                        <small><a href="outEvent.action" class="muted">更多</a></small>
                    </blockquote>
                </div>
            </div>
        </div>
    </div>
    </#if>


<#--各属性饼状图结论-->
    <#if (relicCount>0)>
    <div class="row-fluid m-t-100">
        <div class="span12">
            <div class="row-fluid yahei-font">
                <div class="span2">
                    <img src="images/summaryReport/relicStat.png">
                </div>
                <div class="offset1 span9">
                    <h4>文物信息统计</h4>
                    <blockquote class="reportContent">文物总数 <span
                            class="bold text-error large-font">${relicCount?c}</span>
                        件。<br/>
                        时代中“<span id="era"
                                  class="bold text-error large-font">${relicBasicStats.eraStat.pieData[0][0]}</span>”占比最高（<span
                                id="eraPercent"
                                class="bold text-error large-font">${relicBasicStats.eraStat.pieData[0][1]}</span>%），<br/>
                        级别中“<span id="level"
                                  class="bold text-error large-font">${relicBasicStats.levelStat.pieData[0][0]}</span>”占比最高（<span
                                id="levelPercent"
                                class="bold text-error large-font">${relicBasicStats.levelStat.pieData[0][1]}</span>%），<br/>
                        质地中“<span id="texture"
                                  class="bold text-error large-font">${relicBasicStats.textureStat.pieData[0][0]}</span>”占比最高（<span
                                id="texturePercent"
                                class="bold text-error large-font">${relicBasicStats.textureStat.pieData[0][1]}</span>%），<br/>
                        区域中“<span id="zone"
                                  class="bold text-error large-font">${relicBasicStats.zoneStat.pieData[0][0]!"无库房位次"}</span>”占比最高（<span
                                id="zonePercent"
                                class="bold text-error large-font">${relicBasicStats.zoneStat.pieData[0][1]}</span>%）。
                        <small><a href="orion/property-pie-chart" class="muted">更多</a></small>
                    </blockquote>
                </div>
            </div>
        </div>
    </div>
    </#if>


<#--区域活动分布结论-->
    <#if userDistribution.hasData>
    <div class="row-fluid m-t-100">
        <div class="span12">
            <div class="row-fluid yahei-font">
                <div class="span2">
                    <img src="images/summaryReport/userDistribution.png">
                </div>
                <div class="offset1 span9">
                    <h4>区域活动分布</h4>
                    <blockquote class="reportContent">活动密度最高区域：<span id="maxActiveArea"
                                                                     class="bold text-error large-font">${userDistribution.maxActiveArea}</span>(<span
                            id="maxActiveAreaCount"
                            class="bold text-error large-font">${userDistribution.maxActiveAreaCount}</span>次)
                        <br/>
                        活动最频繁的时段：<span id="maxActiveTime"
                                       class="bold text-error large-font">${userDistribution.maxActiveTime}</span>点(<span
                                id="maxActiveTimeCount"
                                class="bold text-error large-font">${userDistribution.maxActiveTimeCount}</span>次)
                        <br/>
                        <br/>
                        其中<span id="maxActiveTimeRange"
                                class="bold text-error large-font"> ${userDistribution.maxActiveTime}</span>点间 <span
                                id="maxActiveTimeArea"
                                class="bold text-error large-font">${userDistribution.maxActiveTimeArea}</span>
                        人员活动最为密集(<span
                                id="maxActiveTimeAreaCount"
                                class="bold text-error large-font">${userDistribution.maxActiveTimeAreaCount}</span>次)
                        <small><a href="uma/userDistribution" class="muted">更多</a></small>
                    </blockquote>
                </div>
            </div>
        </div>
    </div>
    </#if>


<#--人员停留情况结论-->
    <#if userStopover.hasData>
    <div class="row-fluid m-t-100">
        <div class="span12">
            <div class="row-fluid yahei-font">
                <div class="span2">
                    <img src="images/summaryReport/stopover.png">
                </div>
                <div class="offset1 span9">
                    <h4>人员停留情况</h4>
                    <blockquote class="reportContent">停留总时间最长的位置是<span id="inZoneName"
                                                                       class="bold text-error large-font">${userStopover.inZoneName}</span>，总计<span
                            id="maxInTime"
                            class="bold text-error large-font">${userStopover.maxInTime}</span>小时
                        <br/>
                        平均停留时间最长的位置是<span id="avgZoneName"
                                          class="bold text-error large-font">${userStopover.avgZoneName}</span>，时长为<span
                                id="maxAvgTime" class="bold text-error large-font">${userStopover.maxAvgTime}</span>小时
                        <br/>
                        <br/>
                        <span id="importantZoneName"
                              class="bold text-error large-font">${userStopover.inZoneName}</span>是重点管理区域呀。
                        <small><a href="uma/userStopover" class="muted">更多</a></small>
                    </blockquote>
                </div>
            </div>
        </div>
    </div>
    </#if>

<#--人员活动频率结论-->
    <#if highFrequencyUsers??>
        <#if (highFrequencyUsers?size>0)>
        <div class="row-fluid m-t-100">
            <div class="span12">
                <div class="row-fluid yahei-font">
                    <div class="span2">
                        <img src="images/summaryReport/userStat.png">
                    </div>
                    <div class="offset1 span9">
                        <h4>人员活动频率</h4>
                        <blockquote class="reportContent">活动比较频繁的人员有<span
                                class="bold text-error large-font">
                            <#list highFrequencyUsers as user >
                            <#if user_has_next>
                            ${user.userName + "，"}
                            <#else>
                            ${user.userName}
                            </#if>
                        </#list>
                    </span>等人。
                            <small><a href="uma/userStat" class="muted">更多</a></small>
                        </blockquote>
                    </div>
                </div>
            </div>
        </div>
        </#if>
    </#if>


<#--规则触发频率结论-->
    <#if highFrequencyAction??>
    <div class="row-fluid m-t-100">
        <div class="span12">
            <div class="row-fluid yahei-font">
                <div class="span2">
                    <img src="images/summaryReport/userAction.png">
                </div>
                <div class="offset1 span9">
                    <h4>规则触发频率</h4>
                    <blockquote>触发最频繁的规则是<span
                            class="bold text-error large-font">${highFrequencyAction.actionName}</span>。
                        <small><a href="uma/userActionStat" class="muted">更多</a></small>
                    </blockquote>
                </div>
            </div>
        </div>
    </div>
    </#if>
</#macro>

<#macro script>

<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/modules/exporting.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>

    <@scriptTag "js/index/date-format.js"/>
    <@scriptTag "js/date-year-month-control.js"/>
<script type="text/javascript">
    $(function () {
        $("#subscribe").click(function () {
            var $this = $(this);
            if ($this.text() == "订阅") {

                $.getJSON("../blueplanet/statistics/report/addSubscribe.json", {subscribeType: 2}, function (success) {
                    if (success) {
                        $this.next().html("已订阅<i class='icon-question-sign'></i>");
                        $this.text("取消订阅");
                    }
                });
            } else {

                $.getJSON("../blueplanet/statistics/report/deleteSubscribe.json", {subscribeType: 2}, function (success) {
                    if (success) {
                        $this.next().empty();
                        $this.text("订阅");
                    }
                });
            }
        });
    });
</script>
</#macro>