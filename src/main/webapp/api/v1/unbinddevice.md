[$PROFILE$]: extended

###<a name="未绑定的设备列表"></a>未绑定的设备列表

    GET /unBindDevices

**说明**

    分页查询未绑定的设备
    例如:http://localhost:8080/galaxy/api/v1/unBindDevices?siteId=31010101&index=1&pageSize=10
    实体类路径:[com.microwise.blueplanet.bean.voDeviceVO]

**参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| siteId  | string | 站点编号  |
| index  | int | 起始页面  |
| pageSize  | int | 单页数据数目  |

**返回**

    {
        "data": [
            {
                "lqi": 0,
                "stamp": "2014-02-26T10:36:09",
                "createTime": null,
                "sn": 0,
                "nodeId": "3101010100005",
                "lowvoltage": -1,
                "interval_i": null,
                "nodeName": null,
                "rssi": 0,
                "nodeType": 7,
                "anomaly": -1,
                "zoneId": null
            },
            {
                "lqi": 21,
                "stamp": "2014-03-04T17:21:09",
                "createTime": null,
                "sn": 0,
                "nodeId": "3101010100010",
                "lowvoltage": 4,
                "interval_i": null,
                "nodeName": null,
                "rssi": -43,
                "nodeType": 2,
                "anomaly": -1,
                "zoneId": null
            },
            {
                "lqi": 10,
                "stamp": "2014-05-27T11:02:17",
                "createTime": null,
                "sn": 0,
                "nodeId": "3101010100200",
                "lowvoltage": -1,
                "interval_i": null,
                "nodeName": null,
                "rssi": -43,
                "nodeType": 5,
                "anomaly": -1,
                "zoneId": null
            },
            {
                "lqi": 10,
                "stamp": "2014-07-15T17:57:22",
                "createTime": null,
                "sn": 0,
                "nodeId": "3101010100315",
                "lowvoltage": 4,
                "interval_i": null,
                "nodeName": "315",
                "rssi": -43,
                "nodeType": 1,
                "anomaly": -1,
                "zoneId": null
            },
            {
                "lqi": 9,
                "stamp": "2014-07-15T17:57:26",
                "createTime": null,
                "sn": 0,
                "nodeId": "3101010100316",
                "lowvoltage": 3,
                "interval_i": null,
                "nodeName": null,
                "rssi": -45,
                "nodeType": 1,
                "anomaly": -1,
                "zoneId": null
            },
            {
                "lqi": 10,
                "stamp": "2014-07-15T17:56:49",
                "createTime": null,
                "sn": 0,
                "nodeId": "3101010100402",
                "lowvoltage": 3,
                "interval_i": null,
                "nodeName": null,
                "rssi": -55,
                "nodeType": 1,
                "anomaly": -1,
                "zoneId": null
            },
            {
                "lqi": 21,
                "stamp": "2014-06-30T10:40:06",
                "createTime": null,
                "sn": 123456,
                "nodeId": "3101010100501",
                "lowvoltage": 4,
                "interval_i": null,
                "nodeName": null,
                "rssi": -43,
                "nodeType": 1,
                "anomaly": -1,
                "zoneId": null
            },
            {
                "lqi": 0,
                "stamp": "2014-03-14T09:49:22",
                "createTime": null,
                "sn": 0,
                "nodeId": "3101010140001",
                "lowvoltage": -1,
                "interval_i": null,
                "nodeName": null,
                "rssi": 0,
                "nodeType": 7,
                "anomaly": -1,
                "zoneId": null
            },
            {
                "lqi": 14,
                "stamp": "2014-02-27T13:45:05",
                "createTime": null,
                "sn": 0,
                "nodeId": "3101010140062",
                "lowvoltage": -1,
                "interval_i": null,
                "nodeName": null,
                "rssi": -30,
                "nodeType": 1,
                "anomaly": -1,
                "zoneId": null
            }
        ],
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/unBindDevices?siteId=31010101&index=1&pageSize=10"
        },
        "message": "获取未绑定设备成功",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| nodeId  | String | 产品入网唯一标识  |
| nodeName  | String | 节点名称  |
| nodeType | int  | 1：节点 2：中继 3:节点-主模块(可控) 4:节点-从模块(可控) 7：网关 |
| zoneId   | String | 区域id  |
| createTime  | datetime | 节点创建时间或更新时间，与原add_time字段合并 (记录生成后不可修改)  |
| stamp  | datetime | 时间戳  |
| lqi  | int | 连接质量参数  |
| rssi     | int  | 接收信号强度  |
| lowvoltage  | float | 电压：-1、无电压值，其他的、电压值  |
| interval_i  | int | 工作周期  |
| sn  | int | 产品序列号  |
| anomaly  | int  | 设备状态：-1、超时, 0、正常, 1、低电压, 2、掉电  |
