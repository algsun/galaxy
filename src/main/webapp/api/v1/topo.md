[$PROFILE$]: extended

### <a name="拓扑数据"></a>拓扑数据

    GET /topo/nodeId

**说明**

    网络拓扑数据查询，与该节点相关的网络拓扑信息
    参数为节点ID，不可空
    例如:http://localhost:8080/galaxy/api/v1/topo/6101150100052
    实体类路径:[com.microwise.blueplanet.bean.vo.TopoViewVO]

**返回**

    {
        "data": [
            {
                "toponode": [
                    {
                        "actualCount": 0,
                        "anomaly": -1,
                        "childrenCount": 0,
                        "control": 1,
                        "createTime": "2014-02-26T12:52:59",
                        "expectedCount": 0,
                        "interval": 300,
                        "loseRate": 0,
                        "lqi": 0,
                        "nodeId": "6101150100001",
                        "nodeName": null,
                        "nodeType": 7,
                        "nodeVersion": 3,
                        "parentIp": 1,
                        "parentIpStr": null,
                        "rssi": 0,
                        "selfIp": 1,
                        "stamp": null,
                        "zoomId": "0",
                        "zoomName": null
                    },
                    {
                        "actualCount": 0,
                        "anomaly": -1,
                        "childrenCount": 0,
                        "control": 1,
                        "createTime": "2014-05-10T00:04:33",
                        "expectedCount": 0,
                        "interval": 600,
                        "loseRate": 0,
                        "lqi": 18,
                        "nodeId": "6101150100052",
                        "nodeName": null,
                        "nodeType": 1,
                        "nodeVersion": 3,
                        "parentIp": 1,
                        "parentIpStr": null,
                        "rssi": -72,
                        "selfIp": 52,
                        "stamp": null,
                        "zoomId": "0",
                        "zoomName": null
                    }
                ],
                "topoline": [
                    {
                        "source": "6101150100001",
                        "color": "green",
                        "target": "6101150100052"
                    }
                ]
            }
        ],
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/topo/6101150100052"
        },
        "message": "获取拓扑数据成功",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| actualCount | int | 实际包数 |
| anomaly | int | 设备状态：-1、超时, 0、正常, 1、低电压, 2、掉电 |
| childrenCount | int | 子设备数量 |
| control | int | 是否可控，0:可控 1:不可控 |
| createTime | Date | 设备创建时间 |
| expectedCount | int | 预计包数 |
| interval | int | 间隔周期 |
| loseRate | float | 丢包率 |
| lqi | int | 连接质量参数 |
| nodeId | String | 监测点编号 |
| nodeName | String | 监测点名称 |
| nodeType | int | 设备类型 |
| nodeVersion | int | 节点协议版本号 |
| parentIp | int | 父节点号 |
| parentIpStr | String | 父节点号(5位字符串显示) |
| rssi | int | 接收信号强度 |
| selfIp | int | 自身节点号 |
| stamp | Date | 时间戳 |
| zoomId | String | 区域编号 |
| zoomName | String | 区域名称 |
