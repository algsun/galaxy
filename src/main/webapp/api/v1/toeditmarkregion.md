[$PROFILE$]: extended

### <a name="查询标记区域"></a>查询标记区域

    get /proxima/{pictureId}/toEditMarkRegion

**说明**

    查询标记区域
    例如:http://localhost:8080/galaxy/api/v1/proxima/{pictureId}/toEditMarkRegion
    实体类路径:[com.microwise.proxima.bean.PictureBean][com.microwise.proxima.DVPlaceBean][com.microwise.proxima.bean.InfraredMarkRegionBean]

**查询标记区域参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
|  pictureId | string | 图片id  |

**返回**

    {
        "data": {
            "picture": {
                "analyzable": true,
                "dv": null,
                "id": "2eHs_YbYJ9eqGdUvFvzQ53",
                "name": "422826010_103002_774.jpg",
                "path": "422826010",
                "saveTime": "2013-06-03T00:00:00"
            },
            "dvPlace": {
                "coordinateX": -1,
                "coordinateY": -1,
                "createTime": "2014-08-06T15:03:23",
                "dvIp": "192.168.0.12",
                "dvType": 0,
                "enable": true,
                "ftp": null,
                "ftpHost": null,
                "ftpPassword": null,
                "ftpPort": 0,
                "ftpUser": null,
                "id": "04T559DEdec8WB5c-lrnUJ",
                "imageHeight": 480,
                "imageRealWidth": 0,
                "imageWidth": 640,
                "lastPictureUrl": null,
                "monitorPoint": null,
                "placeCode": "422826010",
                "placeInfo": null,
                "placeName": "6x红外摄像机",
                "realmap": null,
                "remark": "",
                "zone": null
            },
            "markRegions": [
                {
                    "dvPlace": null,
                    "id": "0dk55tUqp86EAiRhzbYjOo",
                    "name": "uu",
                    "positionX": 194,
                    "positionY": 271,
                    "regionHeight": 190,
                    "regionWidth": 144
                }
            ]
        },
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/proxima/{pictureId}/toEditMarkRegion"
        },
        "message": "编辑成功",
        "success": true
    }

**返回picture参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| id | String | ID |
| name | String | 图片名称 |
| path | String | 图片路径 |
| analyzable | boolean | 是否可以解析温度 |
| saveTime | Date | 保存时间 |


**返回dvPlace参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| coordinateX | String | 摄像机在区域平面图x轴坐标 |
| coordinateY | String | 摄像机在区域平面图y轴坐标 |
| createTime | Date | 摄像机添加时间 |
| dvIp | String | 摄像机ip |
| dvPort | String | 摄像机端口 |
| dvType | String | 摄像机类型 |
| enable | boolean | 是否启用 |
| ftp | ftl | ftlVO |
| ftpEnable | Date | 保存时间 |
| ftpHost | String | ftp地址 |
| ftpPassword | String | ftp密码 |
| ftpPort | String | ftp端口 |
| ftpUser | String | ftp用户名 |
| id | String | id |
| imageHeight | int | 图片高度 |
| imageRealWidth | int | 图片真实高度 |
| imageWidth | int | 图片宽度 |
| lastPictureUrl | String | 最后一张照片 for encke |
| monitorPoint | MonitorPointBean | 属于的监测点 |
| photographTime | Date | 拍照时间 |
| placeCode | String | 点位编号 |
| placeName | String | 点位名称 |
| realmap | String | 实景图 |
| remark | String | 备注 |
| zone | Zone | 所属区域 |

**返回markRegions参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| dvPlace | DVPlaceBean | 摄像机点位 |
| id | String | ID |
| name | String | 名称 |
| positionX | int | 区域左上角x |
| positionY | int | 区域左上角y |
| regionWidth | int | 区域宽度 |
| regionHeight | int | 区域高度 |
