[$PROFILE$]: extended

### <a name="历史数据"></a>历史数据

    GET /devices/historyData

**说明**

    分页查询设备的实时数据
    例如:http://localhost:8080/galaxy/api/v1/devices/historyData?deviceId=3101010100501&starttime=2014-02-20%2015:48:26&endtime=2014-02-20%2016:08:29&index=1&pageSize=2
    实体类路径:[com.microwise.blueplanet.bean.vo.HistoryDataVO]

**参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| deviceId  | string | 设备编号  |
| starttime  | Date | 起始时间  |
| endtime  | Date | 结束时间  |
| index  | int | 当前页  |
| pageSize  | int | 单页数据数目  |

**返回**

    {
        "data": [
            {
                "stamp": "2014-02-20T16:08:29",
                "sensorinfoMap": {
                    "32": {
                        "cnName": "湿度",
                        "sensorPhysicalValue": "40",
                        "sensorPhysicalid": 32,
                        "showType": 0,
                        "stamp": "2014-02-20T16:08:29",
                        "state": 1,
                        "units": "%"
                    },
                    "33": {
                        "cnName": "温度",
                        "sensorPhysicalValue": "19.9",
                        "sensorPhysicalid": 33,
                        "showType": 0,
                        "stamp": "2014-02-20T16:08:29",
                        "state": 1,
                        "units": "℃"
                    },
                    "42": {
                        "cnName": "紫外",
                        "sensorPhysicalValue": "1.15",
                        "sensorPhysicalid": 42,
                        "showType": 0,
                        "stamp": "2014-02-20T16:08:29",
                        "state": 1,
                        "units": "uw/c㎡"
                    },
                    "43": {
                        "cnName": "露点",
                        "sensorPhysicalValue": "5.9",
                        "sensorPhysicalid": 43,
                        "showType": 0,
                        "stamp": "2014-02-20T16:08:29",
                        "state": 1,
                        "units": "℃"
                    }
                }
            },
            {
                "stamp": "2014-02-20T16:03:28",
                "sensorinfoMap": {
                    "32": {
                        "cnName": "湿度",
                        "sensorPhysicalValue": "39.8",
                        "sensorPhysicalid": 32,
                        "showType": 0,
                        "stamp": "2014-02-20T16:03:28",
                        "state": 1,
                        "units": "%"
                    },
                    "33": {
                        "cnName": "温度",
                        "sensorPhysicalValue": "19.9",
                        "sensorPhysicalid": 33,
                        "showType": 0,
                        "stamp": "2014-02-20T16:03:28",
                        "state": 1,
                        "units": "℃"
                    },
                    "42": {
                        "cnName": "紫外",
                        "sensorPhysicalValue": "1.28",
                        "sensorPhysicalid": 42,
                        "showType": 0,
                        "stamp": "2014-02-20T16:03:28",
                        "state": 1,
                        "units": "uw/c㎡"
                    },
                    "43": {
                        "cnName": "露点",
                        "sensorPhysicalValue": "5.8",
                        "sensorPhysicalid": 43,
                        "showType": 0,
                        "stamp": "2014-02-20T16:03:28",
                        "state": 1,
                        "units": "℃"
                    }
                }
            }
        ],
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/devices/historyData?deviceId=3101010100501&starttime=Thu Feb 20 15:48:26 CST 2014&endtime=Thu Feb 20 16:08:29 CST 2014&index=1&pageSize=2"
        },
        "message": "获取历史数据成功",
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
