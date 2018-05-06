[$PROFILE$]: extended

### <a name="设备状态"></a>设备状态

    GET /device/{deviceId}/findDevice

**说明**

    查询设备状态
    例如:http://localhost:8080/galaxy/api/v1/device/3101010100100/findDevice
            DeviceVO device = deviceService.findDeviceById(deviceId);
    实体类路径:[com.microwise.blueplanet.bean.vo.DeviceVO]

**参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| deviceId  | string | 设备id  |

**返回**

    {
    "data": {
        "sensorInfoVOs": [
            {
                "cnName": "湿度",
                "enName": "HUM",
                "id": null,
                "isActive": null,
                "maxValue": 0,
                "minValue": 0,
                "positions": 0,
                "sensorPhysicalid": 32,
                "sensorPrecision": 1,
                "showType": 0,
                "units": "%"
            },
            {
                "cnName": "温度",
                "enName": "TMT",
                "id": null,
                "isActive": null,
                "maxValue": 0,
                "minValue": 0,
                "positions": 1,
                "sensorPhysicalid": 33,
                "sensorPrecision": 1,
                "showType": 0,
                "units": "℃"
            },
            {
                "cnName": "二氧化碳",
                "enName": "CO2",
                "id": null,
                "isActive": null,
                "maxValue": 0,
                "minValue": 0,
                "positions": 4,
                "sensorPhysicalid": 36,
                "sensorPrecision": 0,
                "showType": 0,
                "units": "ppm"
            },
            {
                "cnName": "光照",
                "enName": "LUX",
                "id": null,
                "isActive": null,
                "maxValue": 0,
                "minValue": 0,
                "positions": 9,
                "sensorPhysicalid": 41,
                "sensorPrecision": 2,
                "showType": 0,
                "units": "lux"
            },
            {
                "cnName": "露点",
                "enName": "TD",
                "id": null,
                "isActive": null,
                "maxValue": 0,
                "minValue": 0,
                "positions": 11,
                "sensorPhysicalid": 43,
                "sensorPrecision": 1,
                "showType": 0,
                "units": "℃"
            },
            {
                "cnName": "降雨量",
                "enName": "RB",
                "id": null,
                "isActive": null,
                "maxValue": 0,
                "minValue": 0,
                "positions": 15,
                "sensorPhysicalid": 47,
                "sensorPrecision": 2,
                "showType": 0,
                "units": "mm"
            },
            {
                "cnName": "蒸发量",
                "enName": "EVAP",
                "id": null,
                "isActive": null,
                "maxValue": 0,
                "minValue": 0,
                "positions": 47,
                "sensorPhysicalid": 80,
                "sensorPrecision": 1,
                "showType": 0,
                "units": "mm"
            },
            {
                "cnName": "VOC-高灵敏度",
                "enName": "VOC_HS",
                "id": null,
                "isActive": null,
                "maxValue": 0,
                "minValue": 0,
                "positions": 50,
                "sensorPhysicalid": 83,
                "sensorPrecision": 0,
                "showType": 0,
                "units": "ppb"
            }
        ]
    },
    "link": {
        "url": "http://localhost:8080/galaxy/api/v1/device/3101010100100/findSensorInfo"
    },
    "message": "获取设备监测指标信息成功",
    "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| anomaly | Integer | 设备工作状态0：正常 1：异常 |
| deviceMode | Integer | 设备工作模式0：正常模式 1：巡检模式  |
| slaveModuleList | List<DeviceVO> | 主模块下的从模块列表 |
| parentIP | String | 父节点ip |
| currentIP | String | 当前节点ip |
| interval | Integer | 设备工作周期，单位：秒 |
| notControl | boolean | 是否不可控：true:不可控 false:可控 |
| coordinateX | float | 在父区域平面图的x坐标 |
| coordinateY | float | 在父区域平面图的y坐标 |
| nodeVersion | int | 节点协议版本号 |
| localDevice | boolean | 是否为当前区域的设备 |
| createTime | Date | 节点创建时间 |
| sn | int | 产品序列号 |
| dataVersion | Long | 数据版本 |
| deviceImage | int | 设备图片 |
| lowvoltage | int | 产品序列号 |
| lqi | int | 产品序列号 |
| nodeName | String | 设备名称 |
| nodeType | int | 设备类型 1：节点 2：中继 3：主模块 4：从模块 5：控制模块 7：网关 |
| parentIP |  | 产品序列号 |
| realtimeData | List<RealtimeData> | 设备实时数据 |
| roomid | String | 区域id |
| rssi | Integer | 设备接收信号强度 |
| lqi | Integer | 设备链路质量 |
| sensorPhysicalidList | List<SensorInfo> | 设备监测指标 |
| stamp | Date | 时间戳 |
| x | Integer | x轴坐标 |
| y | Integer | y轴坐标 |
| z | Integer | z轴坐标 |
| zoneId | String | 区域id |
| zoneName | String | 区域名称 |
