[$PROFILE$]: extended

### <a name="实时数据"></a>实时数据

    GET /devices/{deviceId}/realtimeData

**说明**

    查询设备的实时数据
    例如:http://localhost:8080/galaxy/api/v1/devices/3101010100501/realtimeData
    实体类路径:[com.microwise.blueplanet.bean.vo.RealtimeDataVO]

**参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| deviceId  | string | 设备编号  |

**返回**

    {
        "data": [
            {
                "lqi": null,
                "nodeId": "3101010100501",
                "nodeName": null,
                "rssi": null,
                "sensorinfoMap": {
                    "32": {
                        "cnName": "湿度",
                        "sensorPhysicalValue": "47.3",
                        "sensorPhysicalid": 32,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "%"
                    },
                    "33": {
                        "cnName": "温度",
                        "sensorPhysicalValue": "-20.7",
                        "sensorPhysicalid": 33,
                        "showType": 0,
                        "stamp": null,
                        "state": 1,
                        "units": "℃"
                    },
                    "34": {
                        "cnName": "甲醛",
                        "sensorPhysicalValue": "6113",
                        "sensorPhysicalid": 34,
                        "showType": 0,
                        "stamp": null,
                        "state": 0,
                        "units": "ppm"
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
                "zoneId": "0"
            }
        ],
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/devices/3101010100501/realtimeData"
        },
        "message": "获取实时数据成功",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| cnName | String | 监测量中文名  |
| units | String | 计量单位  |
| sensorPhysicalValue | String | 传感量值  |
| sensorPhysicalid | int  | 传感量标识 |
| showType | int | 0 默认; 1 风向类；该字段用于呈现判断，风向类在实时数据、历史数据中需要展示为方向标识，而在图表中需要具体数值，考虑扩展性，加入此标识; 2 GPS 类;  |
| stamp | datetime | 数据采样时间戳（实时数据显示时采用一组数据中时间最大值） |
| state | int | 0：采样失败 0xFFFF为采样失败  1：采样正常  2：低于低阈值  3：超过高阈值  4：空数据（前台暂不处理）  |
