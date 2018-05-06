[$PROFILE$]: extended

### <a name="设备实时数据"></a>设备实时数据

    GET /device/{deviceId}/{count}/realtimeDatas

**说明**

    查询站点概览数据
    例如:http://localhost:8080/galaxy/api/v1/device/3101010100100/12/realtimeDatas
         List<RealtimeDataVO> realtimeDatas = deviceService.findRealtimeData(deviceId, 12);
    实体类路径:[com.microwise.blueplanet.bean.vo.RealtimeDataVO]

**参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| deviceId  | string | 设备id  |
| count  | int | 最近多少包数据  |

**返回**

    {
       "data": {
           "realtimeDatas": [
               {
                   "anomaly": 0,
                   "deviceMode": 0,
                   "lowvoltage": 4,
                   "lqi": 8,
                   "nodeId": "3101010100100",
                   "nodeName": "100节点",
                   "rssi": -21,
                   "sensorinfoMap": {
                       "32": {
                           "cnName": "湿度",
                           "sensorPhysicalValue": "41.4",
                           "sensorPhysicalid": 32,
                           "showType": 0,
                           "stamp": "2014-07-23T09:20:54",
                           "state": 1,
                           "units": "%"
                       },
                       "33": {
                           "cnName": "温度",
                           "sensorPhysicalValue": "31",
                           "sensorPhysicalid": 33,
                           "showType": 0,
                           "stamp": "2014-07-23T09:20:54",
                           "state": 1,
                           "units": "℃"
                       },
                       "36": {
                           "cnName": "二氧化碳",
                           "sensorPhysicalValue": "697",
                           "sensorPhysicalid": 36,
                           "showType": 0,
                           "stamp": "2014-07-23T09:20:54",
                           "state": 1,
                           "units": "ppm"
                       },
                       "43": {
                           "cnName": "露点",
                           "sensorPhysicalValue": "16.3",
                           "sensorPhysicalid": 43,
                           "showType": 0,
                           "stamp": "2014-07-23T09:20:54",
                           "state": 1,
                           "units": "℃"
                       }
                   },
                   "stamp": "2014-07-23T09:20:54",
                   "zoneId": null,
                   "zoneName": null
               }
           ]
       },
       "link": {
           "url": "http://localhost:8080/galaxy/api/v1/device/3101010100100/1/realtimeDatas"
       },
       "message": "获取设备数据成功",
       "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| nodeId | String | 设备id  |
| nodeName | String | 设备名称 |
| zoneId | String | 区域id |
| zoneName | String | 区域名称 |
| lowvoltage | Float | 0：正常 1：低电压 2：掉电 Y=x/10(实际电压，保留小数点1位) |
| anomaly | int | 0:正常 1:异常 |
| deviceMode | int | 0:正常模式 1:巡检模式 |
| rssi | Integer | 设备接收信号强度 |
| lqi | Integer | 设备链路质量 |
| stamp | Date | 数据采样时间 |
| sensorinfoMap | <Integer,DeviceDataVO> | 设备下监测指标的实时数据 |
