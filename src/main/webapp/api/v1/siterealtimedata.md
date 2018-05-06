[$PROFILE$]: extended

### <a name="站点设备数据"></a>站点设备数据

    GET /site/{siteId}/realtimeData

**说明**

    查询站点下所有设备的实时数据
    例如:http://localhost:8080/galaxy/api/v1/site/61011501/realtimeData
    实体类路径:[com.microwise.blueplanet.bean.vo.RealtimeDataVO]

**参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| siteId  | string | 站点编号  |

**返回**

    {
        "data": [
            {
                "lqi": null,
                "nodeId": "6101150100052",
                "nodeName": null,
                "rssi": null,
                "sensorinfoMap": {
                    "32": {
                        "cnName": "湿度",
                        "sensorPhysicalValue": "45.8",
                        "sensorPhysicalid": 32,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "%"
                    },
                    "33": {
                        "cnName": "温度",
                        "sensorPhysicalValue": "24.3",
                        "sensorPhysicalid": 33,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "℃"
                    },
                    "43": {
                        "cnName": "露点",
                        "sensorPhysicalValue": "11.9",
                        "sensorPhysicalid": 43,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "℃"
                    }
                },
                "zoneId": "0"
            },
            {
                "lqi": null,
                "nodeId": "6101150100053",
                "nodeName": null,
                "rssi": null,
                "sensorinfoMap": {
                    "32": {
                        "cnName": "湿度",
                        "sensorPhysicalValue": "45.9",
                        "sensorPhysicalid": 32,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "%"
                    },
                    "33": {
                        "cnName": "温度",
                        "sensorPhysicalValue": "23.9",
                        "sensorPhysicalid": 33,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "℃"
                    },
                    "43": {
                        "cnName": "露点",
                        "sensorPhysicalValue": "11.5",
                        "sensorPhysicalid": 43,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "℃"
                    }
                },
                "zoneId": "0"
            },
            {
                "lqi": null,
                "nodeId": "6101150100055",
                "nodeName": null,
                "rssi": null,
                "sensorinfoMap": {
                    "32": {
                        "cnName": "湿度",
                        "sensorPhysicalValue": "47.6",
                        "sensorPhysicalid": 32,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "%"
                    },
                    "33": {
                        "cnName": "温度",
                        "sensorPhysicalValue": "25.2",
                        "sensorPhysicalid": 33,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "℃"
                    },
                    "41": {
                        "cnName": "光照",
                        "sensorPhysicalValue": "131.11",
                        "sensorPhysicalid": 41,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "lux"
                    },
                    "42": {
                        "cnName": "紫外",
                        "sensorPhysicalValue": "0.9",
                        "sensorPhysicalid": 42,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "uw/c㎡"
                    },
                    "43": {
                        "cnName": "露点",
                        "sensorPhysicalValue": "13.2",
                        "sensorPhysicalid": 43,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "℃"
                    }
                },
                "zoneId": "0"
            },
            {
                "lqi": null,
                "nodeId": "6101150100001",
                "nodeName": null,
                "rssi": null,
                "sensorinfoMap": {
                    "12283": {
                        "cnName": "航向",
                        "sensorPhysicalValue": "50.4",
                        "sensorPhysicalid": 12283,
                        "showType": 2,
                        "stamp": null,
                        "state": 1,
                        "units": "°"
                    },
                    "12284": {
                        "cnName": "速率",
                        "sensorPhysicalValue": "0.08",
                        "sensorPhysicalid": 12284,
                        "showType": 2,
                        "stamp": null,
                        "state": 1,
                        "units": "km/h"
                    },
                    "12285": {
                        "cnName": "海拔",
                        "sensorPhysicalValue": "455.3",
                        "sensorPhysicalid": 12285,
                        "showType": 2,
                        "stamp": null,
                        "state": 1,
                        "units": "m"
                    },
                    "12286": {
                        "cnName": "纬度",
                        "sensorPhysicalValue": "34.19721",
                        "sensorPhysicalid": 12286,
                        "showType": 2,
                        "stamp": null,
                        "state": 1,
                        "units": "°"
                    },
                    "12287": {
                        "cnName": "经度",
                        "sensorPhysicalValue": "108.85517",
                        "sensorPhysicalid": 12287,
                        "showType": 2,
                        "stamp": null,
                        "state": 1,
                        "units": "°"
                    }
                },
                "zoneId": "0"
            },
            {
                "lqi": null,
                "nodeId": "6101150100054",
                "nodeName": null,
                "rssi": null,
                "sensorinfoMap": {
                    "32": {
                        "cnName": "湿度",
                        "sensorPhysicalValue": "47.8",
                        "sensorPhysicalid": 32,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "%"
                    },
                    "33": {
                        "cnName": "温度",
                        "sensorPhysicalValue": "24.5",
                        "sensorPhysicalid": 33,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "℃"
                    },
                    "43": {
                        "cnName": "露点",
                        "sensorPhysicalValue": "12.7",
                        "sensorPhysicalid": 43,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "℃"
                    }
                },
                "zoneId": "0"
            },
            {
                "lqi": null,
                "nodeId": "6101150100056",
                "nodeName": null,
                "rssi": null,
                "sensorinfoMap": {
                    "32": {
                        "cnName": "湿度",
                        "sensorPhysicalValue": "45.8",
                        "sensorPhysicalid": 32,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "%"
                    },
                    "33": {
                        "cnName": "温度",
                        "sensorPhysicalValue": "24.8",
                        "sensorPhysicalid": 33,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "℃"
                    },
                    "36": {
                        "cnName": "二氧化碳",
                        "sensorPhysicalValue": "906",
                        "sensorPhysicalid": 36,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "ppm"
                    },
                    "43": {
                        "cnName": "露点",
                        "sensorPhysicalValue": "12.3",
                        "sensorPhysicalid": 43,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "℃"
                    }
                },
                "zoneId": "0"
            },
            {
                "lqi": null,
                "nodeId": "6101150100051",
                "nodeName": null,
                "rssi": null,
                "sensorinfoMap": {
                    "32": {
                        "cnName": "湿度",
                        "sensorPhysicalValue": "39.3",
                        "sensorPhysicalid": 32,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "%"
                    },
                    "33": {
                        "cnName": "温度",
                        "sensorPhysicalValue": "28.3",
                        "sensorPhysicalid": 33,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "℃"
                    },
                    "43": {
                        "cnName": "露点",
                        "sensorPhysicalValue": "13.1",
                        "sensorPhysicalid": 43,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "℃"
                    }
                },
                "zoneId": "0"
            },
            {
                "lqi": null,
                "nodeId": "6101150100060",
                "nodeName": null,
                "rssi": null,
                "sensorinfoMap": {
                    "87": {
                        "cnName": "加速度",
                        "sensorPhysicalValue": "0.07",
                        "sensorPhysicalid": 87,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "g"
                    },
                    "88": {
                        "cnName": "震动",
                        "sensorPhysicalValue": "-0.94",
                        "sensorPhysicalid": 88,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "g"
                    },
                    "89": {
                        "cnName": "开关量",
                        "sensorPhysicalValue": "0",
                        "sensorPhysicalid": 89,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": " "
                    }
                },
                "zoneId": "0"
            }
        ],
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/site/61011501/realtimeData"
        },
        "message": "获取实时数据记录成功",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| nodeId  | String | 产品入网唯一标识  |
| nodeName  | String | 节点名称  |
| cnName | String | 监测量中文名  |
| sensorPhysicalValue | String | 传感量值  |
| sensorPhysicalid | int  | 传感量标识 |
| showType | int | 0 默认; 1 风向类；该字段用于呈现判断，风向类在实时数据、历史数据中需要展示为方向标识，而在图表中需要具体数值，考虑扩展性，加入此标识; 2 GPS 类;  |
| stamp | datetime | 数据采样时间戳（实时数据显示时采用一组数据中时间最大值） |
| state | int | 0：采样失败 0xFFFF为采样失败  1：采样正常  2：低于低阈值  3：超过高阈值  4：空数据（前台暂不处理）  |
| units | String | 计量单位  |
| zoneId   | String | 区域id  |







