[$PROFILE$]: extended

### <a name="删除标记区域"></a>删除标记区域

    get /proxima/{markRegionId}/deleteMarkRegion

**说明**

    删除标记区域
    例如:http://localhost:8080/galaxy/api/v1/proxima/{markRegionId}/deleteMarkRegion
    result = flag?true:false

**删除标记区域参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
|  markRegionId | string | 标记区域id  |

**返回**

    {
        "data": {
            "flag": true
        },
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/proxima/deleteMarkRegion"
        },
        "message": "删除标记区域",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| flag | boolean | 成功，失败 |
