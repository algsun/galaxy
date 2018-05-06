[$PROFILE$]: extended

### <a name="获取有摄像机的区域(红外)"></a>获取有摄像机的区域(红外)

    GET /proxima/{siteId}/findInfraredDVPlacesZoneBySiteId

**说明**

    根据站点id获取有摄像机的区域
    例如:http://localhost:8080/galaxy/api/v1/proxima/42282601/findInfraredDVPlacesZoneBySiteId
    实体类路径:[com.microwise.proxima.bean.Zone]

**红外图片浏览参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| siteId  | string | 站点id  |

**返回**

    {
        "data": [
            {
                "dataVersion": 0,
                "id": "6a58312c-a650-41ec-9e03-ea001fef6dd8",
                "name": "x6",
                "parent": null,
                "planImage": null,
                "site": null
            }
        ],
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/proxima/42282601/findInfraredDVPlacesZoneBySiteId"
        },
        "message": "获取红外摄像机点区域数据成功",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| id | int | ID |
| name | String | 区域名称 |
| planImage | String | 实景图 |
| dataVersion | long | 数据同步版本 |
| parent | Zone | 父级区域 |
| site | Site | 所属站点 |

