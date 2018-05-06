[$PROFILE$]: extended

### <a name="保存标记区域"></a>保存标记区域

    post /proxima/saveMarkRegion

**说明**

    保存标记区域
    例如:http://localhost:8080/galaxy/api/v1/proxima/saveMarkRegion
    result = flag?true:false

**保存标记区域参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
|  id | string | 标记区域id  |
|  x | int | 区域右上角x  |
|  y | int | 区域有上角y  |
|  width | int | 标记区域高  |
|  height | int | 标记区域宽  |
说明：参数格式为
[
    {
       id: markRegionId,
       x: positionX,
       y: positionY,
       width: regionWidth,
       height: regionHeight
    }
]

**返回**

    {
        "data": {
            "flag": true
        },
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/proxima/saveMarkRegion"
        },
        "message": "保存成功",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| flag | boolean | 成功，失败 |
