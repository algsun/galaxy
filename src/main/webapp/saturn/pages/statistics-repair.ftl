<#assign title="数据统计">
<#assign currentTopPrivilege = "saturn:statistics">
<#-- 当前权限标识 -->

<#macro head>
<style type="text/css">
    img {
        width: 80px;
        height: 60px;
    }

    .table td {
        line-height: 60px;
        text-align: center;
        border-top: 1px solid #ddd;

    }

    .td-background {
        background-color: #EFF3FB;
    }

</style>
</#macro>

<#macro content>
<input type="hidden" id="pictureUrl" value="${pictureUrl!}"/>

<div class="container m-t-20">

    <ul class="nav nav-pills">
        <li class="active"><a href="statistics/repair">保护修复</a></li>
        <li><a href="statistics/result">专业成果</a></li>
    </ul>

    <div class="row">
        <div class="span6">
            <form class="form-inline" id="relic_chart">
                按材质统计：
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
                <input type="hidden" id="textureNameHidden"/>
                <button type="button" id="relicSubmitBtn" class="btn">统计</button>
            </form>

        </div>
        <div class="span6">
            <span id="textureName" name="textureName" style="color: #9c0000;font-size: 22px;"></span> &nbsp;
            <span id="textureSize" name="textureSize" style="color: #9c0000;font-size: 22px;"></span>
        </div>
    </div>
    <div class="row">
        <div class="span6">
            <div id="relicChart" style="border: solid #dddddd 1px;"></div>
        </div>
        <div class="span6" id="relicTableDatas" style=" overflow-y: auto ;height: 400px; ">

        </div>
    </div>

    <div class="row" style="border-top: dashed 1px #CCCCCC;margin-top: 20px">
        <div class="span6 m-t-20">
            <form class="form-inline" id="user_chart">
                按人员统计：
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
                <input type="hidden" id="userId" name="userId" value="${userId!}">
                <input type="hidden" id="userNameHidden"/>
                <button type="button" id="userSubmitBtn" class="btn">统计</button>
            </form>

        </div>
        <div class="span6 m-t-20">
            <span id="userName" name="userName" style="color: #9c0000;font-size: 22px;"></span> &nbsp;
            <span id="userSize" name="userSize" style="color: #9c0000;font-size: 22px;"></span>
        </div>
    </div>
    <div class="row">
        <div class="span6">
            <div id="userChart" style="border: solid #dddddd 1px;"></div>
        </div>
        <div class="span6" id="userTableDatas" style=" overflow-y: auto ;height: 400px; ">

        </div>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/underscore/1.4.4/underscore-min.js"></script>
<script type="text/javascript" src="js/relic-statistics.js"></script>
<script type="text/javascript" src="js/user-statistics.js"></script>

<script type="text/template" id="relic_template">
    <table class="table table-bordered " style="text-align: center">
        <tr>
            <th style="background-color: #00cad1">文物名称</th>
            <th style="background-color: #00cad1">负责人</th>
            <th style="background-color: #00cad1">图片</th>
        </tr>
        <% _.each(relicTableDatas, function (item) { %>
        <tr>
            <td><%= item.relicName%></td>
            <td><%= item.owner%></td>
            <td>
                <a href="<%= item.pic1Name%>" target="_blank">
                    <img src='<%= item.pic1Name%>' width="80px" max-height="50px;">
                </a>
            </td>
        </tr>
        <% }); %>
    </table>
</script>

<script type="text/template" id="user_template">
    <table class="table table-bordered " style="text-align: center">
        <tr>
            <th style="background-color: #00cad1">文物名称</th>
            <th style="background-color: #00cad1">负责人</th>
            <th style="background-color: #00cad1">图片</th>
        </tr>
        <% _.each(userTableDatas, function (item) { %>
        <tr>
            <td><%= item.relicName%></td>
            <td><%= item.owner%></td>
            <td>
                <a href="<%= item.pic1Name%>" target="_blank">
                    <img src='<%= item.pic1Name%>' width="80px" max-height="50px;">
                </a>
            </td>
        </tr>
        <% }); %>
    </table>
</script>

</#macro>