[$PROFILE$]: extended

### <a name="获取标记区域数据"></a>获取标记区域数据

    GET /proxima/markRegionData/{dvPlaceId}

**说明**

    获取标记区域
    例如:http://localhost:8080/galaxy/api/v1/proxima/markRegionData/0jvmEBeyJebUcT8m76IaLY

**光学图片浏览参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| dvPlaceId  | string | 摄像机点位  |
| markRegionId  | string | 标记区域id  |
| startDate  | Date | 开始时间  |
| endDate  | Date | 结束时间  |

**返回**

    {
        "data": [
            {
                "temperaturedata": [
                    {
                        "time": 1407294480000,
                        "value": 10.6
                    },
                    {
                        "time": 1407384480000,
                        "value": 10.4
                    },
                    {
                        "time": 1407474480000,
                        "value": 24.5
                    },
                    {
                        "time": 1407564480000,
                        "value": 23.6
                    },
                    {
                        "time": 1407654480000,
                        "value": 23.9
                    },
                    {
                        "time": 1407744480000,
                        "value": 10.6
                    },
                    {
                        "time": 1407834480000,
                        "value": 10.4
                    },
                    {
                        "time": 1407924480000,
                        "value": 23.7
                    },
                    {
                        "time": 1408014480000,
                        "value": 24.2
                    },
                    {
                        "time": 1408104480000,
                        "value": 24.4
                    },
                    {
                        "time": 1408194480000,
                        "value": 24.4
                    }
                ],
                "name": "最高温度"
            },
            {
                "temperaturedata": [
                    {
                        "time": 1407294480000,
                        "value": 0
                    },
                    {
                        "time": 1407384480000,
                        "value": 0
                    },
                    {
                        "time": 1407474480000,
                        "value": 22.6
                    },
                    {
                        "time": 1407564480000,
                        "value": 21.7
                    },
                    {
                        "time": 1407654480000,
                        "value": 22
                    },
                    {
                        "time": 1407744480000,
                        "value": 0
                    },
                    {
                        "time": 1407834480000,
                        "value": 0
                    },
                    {
                        "time": 1407924480000,
                        "value": 21.8
                    },
                    {
                        "time": 1408014480000,
                        "value": 22.3
                    },
                    {
                        "time": 1408104480000,
                        "value": 22.5
                    },
                    {
                        "time": 1408194480000,
                        "value": 22.6
                    }
                ],
                "name": "平均温度"
            },
            {
                "temperaturedata": [
                    {
                        "time": 1407294480000,
                        "value": 0
                    },
                    {
                        "time": 1407384480000,
                        "value": 0
                    },
                    {
                        "time": 1407474480000,
                        "value": 20.4
                    },
                    {
                        "time": 1407564480000,
                        "value": 19.5
                    },
                    {
                        "time": 1407654480000,
                        "value": 19.8
                    },
                    {
                        "time": 1407744480000,
                        "value": 0
                    },
                    {
                        "time": 1407834480000,
                        "value": 0
                    },
                    {
                        "time": 1407924480000,
                        "value": 19.6
                    },
                    {
                        "time": 1408014480000,
                        "value": 20.1
                    },
                    {
                        "time": 1408104480000,
                        "value": 20.2
                    },
                    {
                        "time": 1408194480000,
                        "value": 20.4
                    }
                ],
                "name": "最低温度"
            }
        ],
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/proxima/markRegionData/0jvmEBeyJebUcT8m76IaLY"
        },
        "message": "获取标记区域数据成功",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| map[0] | Map<String,Object> | 最高温度 |
| map[1] | Map<String,Object> | 平均温度 |
| map[2] | Map<String,Object> | 最低温度 |
| time | long | 时间戳 |
| value | String | 数据 |
