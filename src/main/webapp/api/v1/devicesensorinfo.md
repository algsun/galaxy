[$PROFILE$]: extended

### <a name="设备监测指标"></a>设备监测指标

    GET /device/{deviceId}/findSensorInfo

**说明**

    查询设备监测指标
    例如:http://localhost:8080/galaxy/api/v1/device/3101010100100/findSensorInfo
            List<SensorinfoVO> sensorInfoVOs = deviceService.findSensorinfo(deviceId);
    实体类路径:[com.microwise.blueplanet.bean.vo.SensorinfoVO]

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
| id | Integer | id |
| sensorPhysicalid | Integer | 传感量标识  |
| enName | String | 传感量缩写 |
| cnName | String | 监测量中文名 |
| sensorPrecision | Integer | 传感量精度 |
| units | String | 计量单位 |
| positions | int | 显示位 |
| showType | Integer | 0默认，1风向类； |
| maxValue | float | 上限 |
| minValue | float | 下限 |
| isActive | Integer | 是否有效 1：有效 0：无效 |
