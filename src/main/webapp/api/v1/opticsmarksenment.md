[$PROFILE$]: extended

### <a name="获取摄像机点位标记段"></a>获取摄像机点位标记段

    GET /proxima/{dvPlaceId}/findMarkSegment

**说明**

    根据摄像机点位id获取标记段
    例如:http://localhost:8080/galaxy/api/v1/proxima/3AhvdAWW5eMoibZkt-FbxY/findMarkSegment
    实体类路径:[com.microwise.proxima.bean.MarkSegmentBean]

**光学图片浏览参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| dvPlaceId  | string | 摄像机点位id  |

**返回**

    {
        "data": [
            {
                "cancel": false,
                "cancelTime": null,
                "createTime": "2013-08-06T09:45:04",
                "dvPlace": null,
                "id": "1AhvdAWW5eMoibZkt-Fbx1",
                "name": "中瀛鑫标记段1",
                "textA": null,
                "textB": null
            },
            {
                "cancel": false,
                "cancelTime": null,
                "createTime": "2013-08-06T09:46:07",
                "dvPlace": null,
                "id": "2AhvdAWW5eMoibZkt-Fbx2",
                "name": "中瀛鑫标记段2",
                "textA": null,
                "textB": null
            }
        ],
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/proxima/3AhvdAWW5eMoibZkt-FbxY/findMarkSegment"
        },
        "message": "获取标记段数据成功",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| id | String | ID |
| name | String | 标记名称 |
| dvPlace | DVPlaceBean | 摄像机点位 |
| createTime | Date | 创建时间 |
| cancelTime | Date | 注销时间 |
| cancel | Site | 是否注销 |
