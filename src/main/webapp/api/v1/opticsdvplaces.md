[$PROFILE$]: extended

### <a name="获取摄像机点位(光学)"></a>获取摄像机点位(光学)

    GET /proxima/{zoneId}/findOpticsDVPlacesByZoneId

**说明**

    获取摄像机点位
    例如:http://localhost:8080/galaxy/api/v1/proxima/a64e8e80-c9ee-4f7d-9f3a-a067a1488117/findOpticsDVPlacesByZoneId
    实体类路径:[com.microwise.proxima.bean.OpticsDVPlaceBean]
    如果查站点下所有摄像机点位传siteId即可

**光学图片浏览参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| zoneId  | string | 区域id  |

**返回**

    {
        "data": [
            {
                "attatchedFileNumber": 0,
                "capturePeriod": 0,
                "coordinateX": 528,
                "coordinateY": 836,
                "createTime": "2013-10-18T16:43:10",
                "dvHttpPort": 0,
                "dvIp": "192.168.0.18",
                "dvPort": 80,
                "dvType": 0,
                "enable": true,
                "ftp": null,
                "ftpEnable": false,
                "ftpHost": null,
                "ftpPassword": null,
                "ftpPort": 0,
                "ftpUser": null,
                "id": "1eMkwQsqJ0w9GsMPSEybFn",
                "imageHeight": 1080,
                "imageRealWidth": 0,
                "imageWidth": 1920,
                "ioDvRoute": 1,
                "ioIp": "",
                "ioLightRoute": 2,
                "ioOn": false,
                "ioPort": 10001,
                "lastPictureUrl": null,
                "lightOffTime": 5,
                "lightOn": true,
                "lightOnTime": 5,
                "monitorPoint": null,
                "photographTime": 1000,
                "placeCode": "100002030",
                "placeInfo": null,
                "placeName": "壁画",
                "realmap": "1eMkwQsqJ0w9GsMPSEybFnnull",
                "remark": "",
                "zone": null
            }
        ],
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/proxima/a64e8e80-c9ee-4f7d-9f3a-a067a1488117/findOpticsDVPlacesByZoneId"
        },
        "message": "获取摄像机点位数据成功",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| capturePeriod | int | 抓图时间间隔，单位为分钟 |
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
| ioDvRoute | int | 摄像机路数 |
| ioIp | String | 远程IO模块IP |
| ioLightRoute | Integer | 灯路数 |
| ioOn | boolean | 是否外部控制 |
| ioPort | String | 远程IO模块端口 |
| lastPictureUrl | String | 最后一张照片 for encke |
| lightOffTime | int | 拍照后开灯时间(单位毫秒) |
| lightOn | boolean | 是否开灯 |
| lightOnTime | int | 拍照前开灯时间 (单位毫秒) |
| monitorPoint | MonitorPointBean | 属于的监测点 |
| photographTime | Date | 拍照时间 |
| placeCode | String | 点位编号 |
| placeName | String | 点位名称 |
| realmap | String | 实景图 |
| remark | String | 备注 |
| zone | Zone | 所属区域 |
