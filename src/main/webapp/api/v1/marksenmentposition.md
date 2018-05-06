[$PROFILE$]: extended

### <a name="获取标记段位置信息"></a>获取标记段位置信息

    GET /proxima/findMarkSegmentPosition?markId={markId}&startDate={startDate}&endDate={endDate}"

**说明**

    根据摄像机点位id获取标记段
    例如:http://localhost:8080/galaxy/api/v1/proxima/findMarkSegmentPosition?markId=3_osAA_3ZczXcI6BU-5CD4&startDate=2013-08-23%2018:48:26&endDate=2014-08-23%2019:08:29%22
    实体类路径:[com.microwise.proxima.bean.MarkSegmentPositionBean]

**光学图片浏览参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| markId  | string | 标记段id  |
| startDate  | Date | 开始时间  |
| endDate  | Date | 结束时间  |

**返回**

    {
        "data": [
            {
                "id": "3g0mog3ENaIWoOeDhJi6-Y",
                "length": 0,
                "lengthDelta": 0.07,
                "markLength": 421.86,
                "markSegment": null,
                "picSaveTime": "2013-08-23T20:30:00",
                "picture": null,
                "positionX": 844,
                "positionX2": 423,
                "positionY": 417,
                "positionY2": 444,
                "qrCodeA": {
                    "point0": {
                        "x": 780,
                        "y": 500
                    },
                    "point1": {
                        "x": 762,
                        "y": 353
                    },
                    "point2": {
                        "x": 909,
                        "y": 335
                    }
                },
                "qrCodeB": {
                    "point0": {
                        "x": 349,
                        "y": 372
                    },
                    "point1": {
                        "x": 497,
                        "y": 370
                    },
                    "point2": {
                        "x": 497,
                        "y": 517
                    }
                },
                "updateTime": "2013-08-23T20:30:28"
            }
        ],
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/proxima/3_osAA_3ZczXcI6BU-5CD4/findMarkSegmentPosition"
        },
        "message": "获取标记段位置数据成功",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| id | String | ID |
| MarkSegmentBean | String | 标记名称 |
| PictureBean | DVPlaceBean | 摄像机点位 |
| picSaveTime | Date | 创建时间 |
| positionX | int | 第一个点坐标X |
| positionY | int | 第一个点坐标Y |
| positionX2 | int | 第二点坐标X |
| positionY2 | int | 第二个点坐标Y |
| updateTime | Date | 修改时间 |
| markLength | float |  两点长度，单位 毫米(mm) |
| qrCodeA | point | 二维码位置 |
| point1 | flot | xy坐标 |
| point2 | float | xy坐标(绝对值) |
| point3 | float | xy坐标(绝对值) |
