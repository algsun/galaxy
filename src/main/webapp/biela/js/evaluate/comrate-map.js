function option0 (name,provinceName,units) {
    var color = eColorMap[name];
    var option = {
        title : {
            text: '说明：图表中的数据来源于达标率',
            textStyle:{
                color:'#ccc'
            }
        },
        tooltip : {
            trigger: 'axis',
            formatter: function (params) {
            }
        },
        toolbox: {
            show : true,
            //orient : 'vertical',
            x: 'right',
            //y: 'center',
            feature : {
                mark : {show: true},
//                dataView : {show: true, readOnly: true},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        dataRange: {
            min : data[name + 'Min'],
            max : data[name + 'Max'],
            calculable : true,
            itemWidth: 35,
            itemHeight:60,
            text:['高','低'],
            color:['lightgreen','yellow','orange','red','purple','maroon']
//            color: ['maroon','purple','red','orange','yellow','lightgreen']
        },
        series : [
            {
                type: 'map',
                mapType: provinceName,
                mapLocation: {
                    //x:'left'
                },
                hoverable: false,
                roam:true,
                itemStyle:{
                    //normal:{label:{show:true}}
                },
                tooltip: {
                    trigger: 'item',
                    formatter: function (v) {
                        var value = v[2];
                        if(value=="-"){
                            value = "暂无数据"
                        }else{
                            value = (Math.abs(value))+units;
                        }
                        return  v[1] + ' : ' + value;
                    }
                },
                data : [],
                markPoint: {
                    symbolSize: 8,       // 标注大小，半宽（半径）参数，当图形为方向或菱形则总宽度为symbolSize * 2
                    itemStyle: {
                        normal: {
                            borderColor: '#87cefa',
                            borderWidth: 1,            // 标注边线线宽，单位px，默认为1
                            label: {
                                show: false
                            }
                        },
                        emphasis: {
                            borderColor: '#1e90ff',
                            borderWidth: 5,
                            label: {
                                show: false
                            }
                        }
                    },
                    data : data[name]
                },
                geoCoord : data.geoCoord
            }
        ]
    };
    return option;
}