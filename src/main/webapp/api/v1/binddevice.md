[$PROFILE$]: extended

### <a name="绑定的设备列表"></a>绑定的设备列表

    GET /bindDevices

**说明**

    分页查询未绑定的设备
    例如:http://localhost:8080/galaxy/api/v1/bindDevices?siteId=11010101&zoneId=13433711-8b48-47a4-85c4-d69dcb4efda0&index=1&pageSize=10
    实体类路径:[com.microwise.blueplanet.bean.voDeviceVO]

**参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| siteId  | string | 站点编号  |
| zoneId  | string | 区域id  |
| index  | int | 起始页面  |
| pageSize  | int | 单页数据数目  |

**返回**

    {
        "data": [
            {
                "lqi": 21,
                "stamp": "2014-03-04T17:40:41",
                "createTime": null,
                "sn": 0,
                "nodeId": "1101010100012",
                "lowvoltage": 4,
                "interval_i": null,
                "nodeName": null,
                "rssi": -43,
                "nodeType": 1,
                "anomaly": -1,
                "zoneId": "13433711-8b48-47a4-85c4-d69dcb4efda0"
            },
            {
                "lqi": 21,
                "stamp": "2014-03-04T17:43:41",
                "createTime": null,
                "sn": 0,
                "nodeId": "1101010100011",
                "lowvoltage": 4,
                "interval_i": null,
                "nodeName": null,
                "rssi": -43,
                "nodeType": 1,
                "anomaly": -1,
                "zoneId": "13433711-8b48-47a4-85c4-d69dcb4efda0"
            }
        ],
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/bindDevices?siteId=11010101&zoneId=13433711-8b48-47a4-85c4-d69dcb4efda0&index=1&pageSize=10"
        },
        "message": "获取绑定设备成功",
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
| anomaly  | int  | 设备状态：-1、超时, 0、正常, 1、低电压, 2、掉电  |
| sn  | int | 产品序列号  |




