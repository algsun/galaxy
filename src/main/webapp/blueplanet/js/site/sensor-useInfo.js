$(function () {
    $.getJSON("summarize/sensorUsedInfo.json", function (returnData) {
        var $sensorUsed = '[';

        $.each(returnData, function (i, value) {
            $sensorUsed = $sensorUsed + '[' + '"' + value.sensorName + '"' + ',' + value.usedCount + '],';
        });
        if ($sensorUsed.length > 1) {
            $sensorUsed = $sensorUsed.substring(0, $sensorUsed.length - 1) + ']';
            $sensorUsed = $.parseJSON($sensorUsed);

                $('#container4').highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    },
                    title: {
                        text: message.sensorCoverage
                    },
                    credits: {
                        enabled: false
                    },
                    subtitle: {
                        text: '（' + message.sensorCoverageNotes +'）'
                    },
                    tooltip: {
                        formatter: function () {
                            return '<b>' + this.point.name + '</b>: ' + Highcharts.numberFormat(this.percentage, 1) + '% (' +
                                Highcharts.numberFormat(this.y, 0) + ' ' +message.times +')';
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
                                format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                            }
                        }
                    },
                    series: [{
                        type: 'pie',
                        name: message.useTimes,
                        //data: [
                        //    ['Firefox',3600],
                        //    ['IE',36],
                        //    ['Chrome',24],
                        //    ['Safari',16],
                        //    ['Opera',2],
                        //    ['Others',1]
                        //]
                        data: $sensorUsed
                    }]
                });
        }
    });
});