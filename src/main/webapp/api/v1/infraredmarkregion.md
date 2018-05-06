[$PROFILE$]: extended

### <a name="添加标记区域"></a>添加标记区域

    post /proxima/addMarkRegion

**说明**

    添加标记区域
    例如:http://localhost:8080/galaxy/api/v1/proxima/addMarkRegion
    result = flag?true:false

**光学图片浏览参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| dvPlaceId  | string | 站点id  |
| pictureId  | string | 图片id  |
| markName  | string | 标记名称  |
| positionX  | float | 左上角 x 位置 |
| positionY  | float | 左上角 x 位置  |
| width  | int | 宽度 |
| height  | int | 宽度 |

**返回**

    {
        "data": {
            "flag": true
        },
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/proxima/addMarkRegion"
        },
        "message": "添加红外摄像机标记区域成功",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| markRegionId | String | 标记区域id |
| flag | boolean | 成功，失败 |
