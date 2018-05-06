/**
 * 外展管理，实时数据
 *
 * @author
 * @time 2013-10-31 上午9:58
 */
$(function(){
    var exhibitionId = $("#exhibitionId").val();
    var url =  "exhibition/"+exhibitionId+"/getRealTimeDataUrl.json"
    setInterval(function () {
        REAL_TIME.refresh(url);
    },10000);
});

var REAL_TIME = {
    //刷新实时数据
    refresh: function (url) {
        var REAL_TIME = this;
        $.getJSON(url, function (data) {
            REAL_TIME.renderRealTimeDataView(data);
        });
    },
    renderRealTimeDataView: function (data) {
        var REAL_TIME = this;
        if (data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                var realtimeDataVO = data[i];
                var nodeId = realtimeDataVO.nodeId;
                var sensorinfoMap = realtimeDataVO.locationSensorInfoMap;
                var realDate = moment(realtimeDataVO.stamp, "YYYY-MM-DD HH:mm:ss");
                for (var sensorPhysicalid in sensorinfoMap) {
                    var realId = "#" + nodeId + "-" + sensorPhysicalid;
                    var newDate = moment($(realId).attr("stamp-time"), "YYYY-MM-DD HH:mm:ss");
                    if (typeof ($(realId).text()) != undefined) {
                        if($(realId)[0] ==undefined){
                            return;
                        }
                        var nowInnerText = $(realId)[0].innerText;
                        var realText = sensorinfoMap[sensorPhysicalid].sensorPhysicalValue + sensorinfoMap[sensorPhysicalid].units;
                        if ((realDate.isAfter(newDate)) && realText != nowInnerText) {
                            $(realId).text(realText);
                            //数据发生改变，颜色渐变, 由透明变成 lightgrenn ，然后再变成透明
                            $(realId).animate({backgroundColor: "#90ee90"}, 1000)
                                .animate({backgroundColor: "transparent"}, 3000, function () {
                                    $(realId).removeAttr('style');
                                });
                        }
                    }
                }
            }
        }
    }
};