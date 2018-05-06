<#--
历史数据导出
-->
<#assign title=locale.getStr("blueplanet.location.historicalDataTitle")>
<#macro head>
</#macro>
<#macro content>
<form id="historyDataForm" class="well well-small form-inline m-b-10" action="history-data"
      method="post">
    <input type="hidden" value="${siteId}" name="siteId">
    <label>${locale.getStr("common.startDate")}</label>
    <input class="input-medium Wdate" id="start-time" type="text" name="startTime"
           onfocus="var endTime=$dp.$('end-time');WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(){endTime.focus();},maxDate:'#F{$dp.$D(\'end-time\')}'})"
           value="${startTime?string("yyyy-MM-dd HH:mm:ss")!}"/>
    <label>${locale.getStr("common.endDate")}</label>
    <input class="input-medium Wdate" id="end-time" type="text" name="endTime"
           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\'start-time\')}', maxDate:'%y-%M-{%d} 23:59:59'})"
           value="${endTime?string("yyyy-MM-dd HH:mm:ss")!}"/>
    <label>${locale.getStr("common.zone")}</label>
    <select class="input-small" name="zoneId" id="zoneSelect">
        <option>全部</option>
        <#list zoneVOList as zone >
            <option value="${zone.zoneId}">${zone.zoneName}</option>
        </#list>
    </select>
    <label>${locale.getStr("blueplanet.location.LocationName")}</label>
    <select class="input-small" name="locationId" id="locationSelect">
        <option>全部</option>
    </select>
    <#if security.isPermitted("blueplanet:manage:historyDataExport")>
        <span id="exportButton" class="btn m-l-20">
                <i class="icon-download-alt"></i>${locale.getStr("blueplanet.location.exportExcel")}
            </span>
    </#if>
</form>
</#macro>


<#macro script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script>
    $(function () {
        $("#zoneSelect").change(function () {
            var zoneId = $(this).val();
            var url = "locations?zoneId=" + zoneId;
            $("#locationSelect option").not(":first").remove();
            $.get(url, function (locations) {
                for (var i = 0; i < locations.length; i++) {
                    $("#locationSelect").append("<option value=" + locations[i].id + ">" + locations[i].locationName + "</option>");
                }
            });
        });


        // 导出按钮点击
        $("#exportButton").click(function () {
            var param = $("#historyDataForm").serialize();
            // 导出等待页面链接
            //var url = "history-data-export-wait?" + param;
            /*var basePath = $("base").attr("href");
            if (basePath) {
                url = basePath + url;
            }*/
            // 打开等待页面
            window.open("history-data-export-wait-begin", "blank", "height=400, width=400");
            $.get("history-data-export-wait.json?" + param, function (result) {
                var url = "history-data-export-wait?count=" + result.total + "&locationCount=" +result.locationCount+"&"+param;
                window.open(url, "blank", "height=400, width=400");
            });
        });
    });


</script>
</#macro>