$(function () {
    //文物保护修复table
    var relicTable = function () {
        var formData = $("#relic_chart").serialize();
        $.get("statistics/repair/relic_table", formData, function (result) {
            if(result.length==0){
                $("#relicTableDatas").html("");
                $("#textureName").text("");
                $("#textureSize").text("");
                return;
            }
            var pictureUrl = $("#pictureUrl").attr("value");
            $("#textureName").text($("#textureNameHidden").attr("value"));
            $("#textureSize").text(result.length);
            var relicTableDatas = [];
            for (var data in result) {
                var dataObj = result[data];
                var owner = dataObj.owner.value;
                var pic1Name = pictureUrl + dataObj.pic1Name.value;
                var relicName = dataObj.relicName.value;

                var tr = {
                    owner: owner,
                    pic1Name: pic1Name,
                    relicName: relicName
                }
                relicTableDatas.push(tr);
            }
            $("#relicTableDatas").html(_.template($('#relic_template').html(), {relicTableDatas: relicTableDatas}));
        });
    };

    //文物保护修复图表
    var relicChart = function (chartDatas) {
        $('#relicChart').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            credits:{
                enabled:false
            },
            title: {
                text: '<span style="color: #9c0000;font-weight: bolder;">保护修复 按材质统计</span>'
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
                        formatter: function () {
                            return '<b>' + this.point.name + '</b>:' +
                                Highcharts.numberFormat(this.y, 0, ',');
                        }
                    },
                    events: {
                        click: function (e) {
                            $("#type").attr("value", e.point.type);
                            $("#textureNameHidden").attr("value", e.point.name);
                            relicTable();
                        }
                    }
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            series: [
                {
                    type: 'pie',
                    name: 'Browser share',
                    data: chartDatas
                }
            ]
        });
    };

    //初始化文物修复图表
    var relicInit = function () {
        var formData = $("#relic_chart").serialize();
        $.get("statistics/repair/relic_chart", formData, function (result) {
            var chartDatas = [];
            for (var data in result) {
                var chartObj = {};
                var obj = result[data];
                chartObj.name = obj.relicMaterialName.value;
                chartObj.type = obj.relicMaterial.value;
                chartObj.y = obj.amount;
                chartDatas.push(chartObj);
            }
            //图表
            relicChart(chartDatas);

            if(chartDatas.length==0){
                $("#relicTableDatas").html("");
                $("#textureName").text("");
                $("#textureSize").text("");
                return;
            }
            //图表对应的table
            $("#type").attr("value", chartDatas[0].type);
            $("#textureNameHidden").attr("value", chartDatas[0].name);
            relicTable();
        });
    };

    //文物修复 统计按钮事件
    $("#relicSubmitBtn").click(function () {
        relicInit();
    });

    //文物修复 初始化
    relicInit();

});