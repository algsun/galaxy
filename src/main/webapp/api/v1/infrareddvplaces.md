[$PROFILE$]: extended

### <a name="获取摄像机点位(红外)"></a>获取摄像机点位(红外)

    GET /proxima/{zoneId}/findInfraredDVPlacesByZoneId

**说明**

    获取摄像机点位
    例如:http://localhost:8080/galaxy/api/v1/proxima/a64e8e80-c9ee-4f7d-9f3a-a067a1488117/findInfraredDVPlacesByZoneId
    实体类路径:[com.microwise.proxima.bean.InfrareDVPlaceBean]
    如果查站点下所有摄像机点位传siteId即可

**红外图片浏览参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| zoneId  | string | 区域id  |

**返回**

    {
        "data": [
            {
                "coordinateX": -1,
                "coordinateY": -1,
                "createTime": "2014-07-30T18:37:24",
                "dvIp": "192.168.0.123",
                "dvType": 0,
                "enable": true,
                "ftp": null,
                "ftpHost": null,
                "ftpPassword": null,
                "ftpPort": 0,
                "ftpUser": null,
                "id": "3CLb5NKfd3OHCr8yYSnM2c",
                "imageHeight": 480,
                "imageRealWidth": 0,
                "imageWidth": 640,
                "lastPictureUrl": null,
                "monitorPoint": null,
                "placeCode": "422826010",
                "placeInfo": null,
                "placeName": "x6点位",
                "realmap": null,
                "remark": "",
                "zone": null
            }
        ],
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/proxima/6a58312c-a650-41ec-9e03-ea001fef6dd8/findInfraredDVPlacesByZoneId"
        },
        "message": "获取红外摄像机点位数据成功",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| coordinateX | String | 摄像机在区域平面图x轴坐标 |
| coordinateY | String | 摄像机在区域平面图y轴坐标 |
| createTime | Date | 摄像机添加时间 |
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
| placeCode | String | 点位编号 |
| placeName | String | 点位名称 |
| realmap | String | 实景图 |
| remark | String | 备注 |
| zone | Zone | 所属区域 |
