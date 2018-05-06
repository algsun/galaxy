<#assign title="数据统计">
<#assign currentTopPrivilege = "saturn:statistics">
<#-- 当前权限标识 -->

<#macro head>
</#macro>

<#macro content>
<div class="container m-t-20">
    <ul class="nav nav-pills">
        <li><a href="statistics/repair">保护修复</a></li>
        <li class="active"><a href="statistics/result">专业成果</a></li>
    </ul>
    <div class="row">
        <div class="span6">
            <form class="form-inline" action="statistics/result" method="post" id="statistics_form">
                专业成果：
                <select name="year" id="year" style="width: 25%">
                    <option <#if year??><#if year == 2016>selected="selected"</#if></#if> value="2016">2016</option>
                    <option <#if year??><#if year == 2015>selected="selected"</#if></#if> value="2015">2015</option>
                    <option <#if year??><#if year == 2014>selected="selected"</#if></#if> value="2014">2014</option>
                    <option <#if year??><#if year == 2013>selected="selected"</#if></#if> value="2013">2013</option>
                    <option <#if year??><#if year == 2012>selected="selected"</#if></#if> value="2012">2012</option>
                    <option <#if year??><#if year == 2011>selected="selected"</#if></#if> value="2011">2011</option>
                    <option <#if year??><#if year == 2010>selected="selected"</#if></#if> value="2010">2010</option>
                    <option <#if year??><#if year == 2009>selected="selected"</#if></#if> value="2009">2009</option>
                    <option <#if year??><#if year == 2008>selected="selected"</#if></#if> value="2008">2008</option>
                    <option <#if year??><#if year == 2007>selected="selected"</#if></#if> value="2007">2007</option>
                    <option <#if year??><#if year == 2006>selected="selected"</#if></#if> value="2006">2006</option>
                </select>

                <select name="quarterNum" id="quarterNum" style="width: 25%">
                    <option value="">全年</option>
                    <option <#if quarterNum??><#if quarterNum == 1>selected="selected"</#if></#if> value="1">第一季度
                    </option>
                    <option <#if quarterNum??><#if quarterNum == 2>selected="selected"</#if></#if> value="2">第二季度
                    </option>
                    <option <#if quarterNum??><#if quarterNum == 3>selected="selected"</#if></#if> value="3">第三季度
                    </option>
                    <option <#if quarterNum??><#if quarterNum == 4>selected="selected"</#if></#if> value="4">第四季度
                    </option>
                </select>
                <input type="hidden" id="type" name="type" value="${type!}">
                <button type="submit" class="btn">统计</button>
            </form>

        </div>
        <div class="span6">
            <span id="maxSizeText" name="maxSizeText" style="color: #9c0000;font-size: 22px;"></span> &nbsp;
            <span id="maxSize" name="maxSize" style="color: #9c0000;font-size: 22px;"></span>
        </div>
    </div>
    <div class="row">
        <div class="span6">
            <div id="container" style="border: solid #dddddd 1px"></div>
        </div>
        <div class="span6">

            <table class="table table-bordered table-striped">
                <tr>
                    <th style="background-color: #00cad1">成果名称</th>
                    <th style="background-color: #00cad1">负责人</th>
                </tr>
                <#list affairs as affair>
                    <tr>
                        <td>${affair.title!}</td>
                        <td>${affair.incharge!}</td>
                    </tr>
                </#list>
            </table>
        </div>
    </div>
</div>
<div id="data" style="display: none;">
    [
    <#list affairList as affair>
        <#if affair_index!=0>
            ,
        </#if>
        <#if affair.type==1>
            {"name":"发表论文/著作"
        <#elseif affair.type ==2>
            {"name":"负责项目"
        <#elseif affair.type ==3>
            {"name":"其他服务"
        </#if>,"y":${affair.totalCount},"type":${affair.type} }
    </#list>
    ]
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>

<script type="text/javascript">
    $(function () {
        var data = $("#data").html();
        data = $.parseJSON(data);
        var size = [];
        if (data.length == 0) {
            art.dialog({
                title: "温馨提示",
                content: "暂无数据"
            });
            return;
        }
        var maxSizeText;
        var maxSize
        var type = $("#type").val();
        if (type == "") {
            for (var d in data) {
                var temp = data[d];
                size.push(temp.y)
            }

            maxSize = Math.max.apply(Math, size);

            for (var d in data) {
                var temp = data[d];
                if (temp.y == maxSize) {
                    maxSizeText = temp.name;
                    maxSize = temp.y
                }
            }
        } else {
            for (var d in data) {
                var temp = data[d];
                if (temp.type == type) {
                    maxSizeText = temp.name;
                    maxSize = temp.y
                }
            }
        }

        $("#maxSize").text(maxSize);
        $("#maxSizeText").text(maxSizeText);

        $('#container').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: '专业成果 统计'
            },
            tooltip: {
                formatter: function () {
                    return '<b>' + this.point.name + '</b>:' +
                            Highcharts.numberFormat(this.y, 0, ',');
                }
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        color: '#000000',
                        connectorColor: '#000000',
                        formatter: function () {
                            return '<b>' + this.point.name + '</b>:' +
                                    Highcharts.numberFormat(this.y, 0, ',');
                        }
                    },
                    events: {
                        click: function (e) {
                            $("#type").attr("value", e.point.type);
                            $("#statistics_form").submit();
                        }
                    }
                }
            },
            series: [
                {
                    type: 'pie',
                    name: 'Browser share',
                    data: data
                }
            ]
        });
    });
</script>

</#macro>