[$PROFILE$]: extended

### <a name="区域设备数据"></a>区域设备数据

    GET /zone/{zoneId}/realtimeData

**说明**

    查询区域下所有设备的实时数据
    例如:http://localhost:8080/galaxy/api/v1/zone/2a2e7d5c-0c1f-472c-b6c9-30aad12d6c32/realtimeData
    实体类路径:[com.microwise.blueplanet.bean.vo.RealtimeDataVO]

**参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| zoneId  | string | 区域编号  |

**返回**

    {
        "data": [
            {
                "nodeId": "3101010100500",
                "nodeName": "李建飞专用，生人勿动",
                "sensorinfoMap": {
                    "32": {
                        "cnName": "湿度",
                        "sensorPhysicalValue": "62.6",
                        "sensorPhysicalid": 32,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "%"
                    },
                    "33": {
                        "cnName": "温度",
                        "sensorPhysicalValue": "-21.9",
                        "sensorPhysicalid": 33,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "℃"
                    },
                    "34": {
                        "cnName": "甲醛",
                        "sensorPhysicalValue": "5056",
                        "sensorPhysicalid": 34,
                        "showType": 0,
                        "stamp": null,
                        "state": 0,
                        "units": "ppm"
                    },
                    "42": {
                        "cnName": "紫外",
                        "sensorPhysicalValue": "16.42",
                        "sensorPhysicalid": 42,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "uw/c㎡"
                    },
                    "43": {
                        "cnName": "露点",
                        "sensorPhysicalValue": "0",
                        "sensorPhysicalid": 43,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "℃"
                    }
                },
                "zoneId": "2a2e7d5c-0c1f-472c-b6c9-30aad12d6c32"
            },
            {
                "nodeId": "3101010100401",
                "nodeName": null,
                "sensorinfoMap": {
                    "32": {
                        "cnName": "湿度",
                        "sensorPhysicalValue": "49.3",
                        "sensorPhysicalid": 32,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "%"
                    },
                    "33": {
                        "cnName": "温度",
                        "sensorPhysicalValue": "20",
                        "sensorPhysicalid": 33,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "℃"
                    },
                    "36": {
                        "cnName": "二氧化碳",
                        "sensorPhysicalValue": "1471",
                        "sensorPhysicalid": 36,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "ppm"
                    },
                    "43": {
                        "cnName": "露点",
                        "sensorPhysicalValue": "9",
                        "sensorPhysicalid": 43,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "℃"
                    }
                },
                "zoneId": "2a2e7d5c-0c1f-472c-b6c9-30aad12d6c32"
            }
        ],
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/zone/2a2e7d5c-0c1f-472c-b6c9-30aad12d6c32/realtimeData"
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

