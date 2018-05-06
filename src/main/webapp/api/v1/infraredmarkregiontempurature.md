[$PROFILE$]: extended

### <a name="当前点数据"></a>当前点数据

    post /proxima/findInfraredImageTemperature

**说明**

    标记区域数据
    例如:http://localhost:8080/galaxy/api/v1/proxima/findInfraredImageTemperature

**标记区域数据**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| dvPlaceId  | string | 摄像机id  |
| pictureId  | string | 图片id  |
| positionX  | float | 左上角 x 位置 |
| positionY  | float | 左上角 x 位置  |

**返回**

    {
        "data": {
            "regionAvgTemp": 21.9,
            "regionMinTemp": 21.8,
            "regionMaxTemp": 22.1,
            "hasRegionTemp": true,
            "picCurrentTemp": 21.9,
            "hasPicTemp": true
        },
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/proxima/findInfraredImageTemperature"
        },
        "message": "获取红外摄相机温度信息成功",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| hasPicTemp | boolean | 是否有当前温度 |
| picCurrentTemp | float | 当前温度 |
| hasRegionTemp | boolean | 是否有标记区域信息 |
| regionMinTemp | float | 标记区域最小值 |
| regionMaxTemp | float | 标记区域最大值 |
| regionAvgTemp | float | 标记区域平均值 |
