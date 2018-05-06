[$PROFILE$]: extended

### <a name="区域概览"></a>区域概览

    GET /zone/{siteId}/{zoneId}/index

**说明**

    查询区域概览数据
    例如:http://localhost:8080/galaxy/api/v1/site/31010101/8005d4fd-df15-4029-906c-f5d2babc841b/index
    实体类路径:[com.microwise.blueplanet.bean.vo.NodeSensorVO]

**参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| siteId  | string | 站点id  |
| zoneId  | string | 区域id  |

**返回**

    {
      "data": {
          "rightData": {
              "bigValue":null,
              "cnName":null,
              "enName":null,
              "node":true,
              "nodeId":null,
              "nodeName":"节点100",
              "sensorPhysicalValueList": [
                  {
                      "bigValue": null,
                      "cnName": "湿度",
                      "enName": "HUM",
                      "node": false,
                      "nodeId": "3101010100100",
                      "nodeName": null,
                      "nodeType": 1,
                      "sensorPhysicalCnName": null,
                      "sensorPhysicalId": 32,
                      "sensorPhysicalValue": "44.3",
                      "sensorPhysicalValueMap": null,
                      "smallValue": null,
                      "stamp": "2014-07-21T09:37:49",
                      "state": 0,
                      "units": "%"
                  },
                  {
                      "bigValue": null,
                      "cnName": "温度",
                      "enName": "TMT",
                      "node": false,
                      "nodeId": "3101010100100",
                      "nodeName": null,
                      "nodeType": 1,
                      "sensorPhysicalCnName": null,
                      "sensorPhysicalId": 33,
                      "sensorPhysicalValue": "27.1",
                      "sensorPhysicalValueMap": null,
                      "smallValue": null,
                      "stamp": "2014-07-21T09:37:49",
                      "state": 0,
                      "units": "℃"
                  }
              ]
          },
          "leftData": [
              {
                  "bigValue": null,
                  "cnName": null,
                  "enName": null,
                  "node": false,
                  "nodeId": null,
                  "nodeName": null,
                  "nodeType": 0,
                  "sensorPhysicalCnName": "湿度",
                  "sensorPhysicalId": 32,
                  "sensorPhysicalValue": "45",
                  "sensorPhysicalValueMap": {
                    "1405785600000": {
                        "bigValue": "87",
                        "cnName": null,
                        "enName": null,
                        "node": false,
                        "nodeId": null,
                        "nodeName": null,
                        "nodeType": 0,
                        "sensorPhysicalCnName": null,
                        "sensorPhysicalId": null,
                        "sensorPhysicalValue": null,
                        "sensorPhysicalValueMap": null,
                        "smallValue": "33",
                        "stamp": "2014-07-20T00:00:00",
                        "state": 0,
                        "units": null }
                  },
                  "smallValue": null,
                  "stamp": null,
                  "state": 0,
                  "units": "%"
              },
              {
                  "bigValue": null,
                  "cnName": null,
                  "enName": null,
                  "node": false,
                  "nodeId": null,
                  "nodeName": null,
                  "nodeType": 0,
                  "sensorPhysicalCnName": "温度",
                  "sensorPhysicalId": 33,
                  "sensorPhysicalValue": "27.5",
                  "sensorPhysicalValueMap": {
                    "1405699200000": {
                        "bigValue": "40",
                        "cnName": null,
                        "enName": null,
                        "node": false,
                        "nodeId": null,
                        "nodeName": null,
                        "nodeType": 0,
                        "sensorPhysicalCnName": null,
                        "sensorPhysicalId": null,
                        "sensorPhysicalValue": null,
                        "sensorPhysicalValueMap": null,
                        "smallValue": "11",
                        "stamp": "2014-07-19T00:00:00",
                        "state": 0,
                        "units": null }
                  },
                  "smallValue": null,
                  "stamp": null,
                  "state": 0,
                  "units": "℃"
              }
          ]
      },
      "link": {
          "url": "http://localhost:8080/galaxy/api/v1/site/31010101/index"
      },
      "message": "获取站点概览数据成功",
      "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| nodeId | String | 设备id  |
| sensorPhysicalId | Integer | 传感量标示  |
| sensorPhysicalCnName | String | 传感量中文名
| sensorPhysicalValue | String | 传感量值  |
| stamp | Date | 数据采样时间 |
| units | String | 单位 |
| enName | String | 监测指标英文名 |
| cnName | String | 监测指标中文名 |
| nodeName | String | 设备名称 |
| bigValue | String | 监测指标最大值 |
| smallValue | String | 监测指标最大值 |
| sensorPhysicalValueList | List<NodeSensorVO> | 监测指标集合 |
| state | int | 0：采样失败 0xFFFF为采样失败  1：采样正常  2：低于低阈值  3：超过高阈值  4：空数据（前台暂不处理）|
| nodeType | int | 设备类型 |
| isNode | boolean | 是否是节点 |

