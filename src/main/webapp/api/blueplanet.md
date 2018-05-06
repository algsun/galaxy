[$PROFILE$]: extended

## <a name="blueplanet"></a> blueplanet - 环境监测
### <a name="根据设备编号查询实时数据"></a>

    GET /devices/{deviceId}/realtimeData

**参数**

| 名称    |  类型  | 描述     |
|   -     |      - |    -     |
| deviceId| string | 设备编号 |

**返回**

       {
           "data": {
               "anomaly": -1,
               "deviceMode": 0,
               "lowvoltage": 3,
               "lqi": null,
               "nodeId": "1000020400103",
               "nodeName": null,
               "rssi": null,
               "sensorinfoMap": {
                   "68": {
                       "cnName": "5TE土壤温度",
                       "sensorPhysicalValue": "0",
                       "sensorPhysicalid": 68,
                       "showType": 0,
                       "stamp": null,
                       "state": 0,
                       "units": "℃"
                   },
                   "69": {
                       "cnName": "5TE容积含水率",
                       "sensorPhysicalValue": "0",
                       "sensorPhysicalid": 69,
                       "showType": 0,
                       "stamp": null,
                       "state": 0,
                       "units": "%m³/m³"
                   }
               },
               "stamp": "2013-06-21T15:37:26",
               "zoneId": "b2bd382d-fc85-4292-b69d-a3c7f5ed4390",
               "zoneName": null
               },
           "message": "获取实时数据记录成功",
           "success": true
       }

### <a name="根据区域编号查询实时数据"></a>

    GET /zones/{zoneId}/realtimeData

**参数**

| 名称    |  类型  | 描述     |
|   -     |      - |    -     |
| zoneId  | string | 区域编号 |

**返回**

    {
        "data": [
                {
                    "coordinateX": -1,
                    "coordinateY": -1,
                    "dataVersion": 0,
                    "devices":
                         [
                            {
                               "anomaly": -1,
                               "deviceMode": 0,
                               "lowvoltage": 3,
                               "lqi": null,
                               "nodeId": "1000020400103",
                               "nodeName": null,
                               "rssi": null,
                               "sensorinfoMap": {
                                   "68": {
                                       "cnName": "5TE土壤温度",
                                       "sensorPhysicalValue": "0",
                                       "sensorPhysicalid": 68,
                                       "showType": 0,
                                       "stamp": null,
                                       "state": 0,
                                       "units": "℃"
                                   },
                                   "69": {
                                       "cnName": "5TE容积含水率",
                                       "sensorPhysicalValue": "0",
                                       "sensorPhysicalid": 69,
                                       "showType": 0,
                                       "stamp": null,
                                       "state": 0,
                                       "units": "%m³/m³"
                                   }
                               },
                               "stamp": "2013-06-21T15:37:26",
                               "zoneId": "b2bd382d-fc85-4292-b69d-a3c7f5ed4390",
                               "zoneName": null
                               }
                         ],
                    "parentId": null,
                    "planImage": null,
                    "siteId": "31010101",
                    "zoneId": "2a2e7d5c-0c1f-472c-b6c9-30aad12d6c32",
                    "zoneName": "7x",
                    "zones": null
                }
            ],
        "message": "获取实时数据记录成功",
        "success": true
    }


### <a name="根据站点编号查询实时数据"></a>

    GET /sites/{siteId}/realtimeData

**参数**

| 名称    |  类型  | 描述     |
|   -     |      - |    -     |
| siteId  | string | 站点编号 |

**返回**

    {
        "data": [
                {
                    "coordinateX": -1,
                    "coordinateY": -1,
                    "dataVersion": 0,
                    "devices":
                         [
                            {
                               "anomaly": -1,
                               "deviceMode": 0,
                               "lowvoltage": 3,
                               "lqi": null,
                               "nodeId": "1000020400103",
                               "nodeName": null,
                               "rssi": null,
                               "sensorinfoMap": {
                                   "68": {
                                       "cnName": "5TE土壤温度",
                                       "sensorPhysicalValue": "0",
                                       "sensorPhysicalid": 68,
                                       "showType": 0,
                                       "stamp": null,
                                       "state": 0,
                                       "units": "℃"
                                   },
                                   "69": {
                                       "cnName": "5TE容积含水率",
                                       "sensorPhysicalValue": "0",
                                       "sensorPhysicalid": 69,
                                       "showType": 0,
                                       "stamp": null,
                                       "state": 0,
                                       "units": "%m³/m³"
                                   }
                               },
                               "stamp": "2013-06-21T15:37:26",
                               "zoneId": "b2bd382d-fc85-4292-b69d-a3c7f5ed4390",
                               "zoneName": null
                               }
                         ],
                    "parentId": null,
                    "planImage": null,
                    "siteId": "31010101",
                    "zoneId": "2a2e7d5c-0c1f-472c-b6c9-30aad12d6c32",
                    "zoneName": "7x",
                    "zones": null
                }
            ],
        "message": "获取实时数据记录成功",
        "success": true
    }