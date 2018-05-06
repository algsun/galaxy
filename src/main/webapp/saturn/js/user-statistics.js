$(function () {

    //人员统计table
    var userTable = function () {
        var formData = $("#user_chart").serialize();
        $.get("statistics/repair/user_table", formData, function (result) {
            if(result.length==0){
                $("#userTableDatas").html("");
                $("#userName").text("");
                $("#userSize").text("");
                return;
            }
            var pictureUrl = $("#pictureUrl").attr("value");
            $("#userName").text($("#userNameHidden").attr("value"));
            $("#userSize").text(result.length);
            var userTableDatas = [];
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
                userTableDatas.push(tr);
            }
            $("#userTableDatas").html(_.template($('#user_template').html(), {userTableDatas: userTableDatas}));
        });
    }



    //人员修复图表
    var userChart = function (chartDatas) {
        $('#userChart').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            credits:{
                enabled:false
            },
            title: {
                text: '<span style="color: #9c0000;font-weight: bolder;">保护修复 按人员统计</span>'
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
                            $("#userId").attr("value", e.point.userId);
                            $("#userNameHidden").attr("value", e.point.name);
                            userTable();
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


    //初始化人员统计图表
    var userInit = function () {
        var formData = $("#user_chart").serialize();
        $.get("statistics/repair/user_chart", formData, function (result) {
            var chartDatas = [];
            for (var data in result) {
                var chartObj = {};
                var obj = result[data];
                chartObj.name = obj.owner.value;
                chartObj.userId = obj.ownerId.value;
                chartObj.y = obj.amount;
                chartDatas.push(chartObj);
            }
            //图表
            userChart(chartDatas);

            if(chartDatas.length==0){
                $("#userTableDatas").html("");
                $("#userName").text("");
                $("#userSize").text("");
                return;
            }
            //图表对应的table
            $("#userId").attr("value", chartDatas[0].userId);
            $("#userNameHidden").attr("value", chartDatas[0].name);
            userTable();
        });
    };



    //人员 统计按钮事件
    $("#userSubmitBtn").click(function () {
        userInit();
    });

    //人员 初始化
    userInit();
});