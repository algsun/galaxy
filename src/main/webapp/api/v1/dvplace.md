[$PROFILE$]: extended

### <a name="获取摄像机数据"></a>获取摄像机数据

    GET /proxima/{dvPlaceId}/findDVPlace

**说明**

    获取摄像机数据
    例如:http://localhost:8080/galaxy/api/v1/proxima/010L61a6Z34WkVi69Ywrzi/findDVPlace
    实体类路径:[com.microwise.proxima.bean.DVPlaceBean]

**光学图片浏览参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| dvPlaceId  | string | 摄像机id  |

**返回**

    {
        "data": {
            "attatchedFileNumber": 0,
            "capturePeriod": 0,
            "coordinateX": -1,
            "coordinateY": -1,
            "createTime": "2013-03-28T14:50:17",
            "dvHttpPort": 0,
            "dvIp": "192.168.0.1",
            "dvPort": 80,
            "dvType": 0,
            "enable": false,
            "ftp": null,
            "ftpEnable": false,
            "ftpHost": null,
            "ftpPassword": null,
            "ftpPort": 0,
            "ftpUser": null,
            "id": "010L61a6Z34WkVi69Ywrzi",
            "imageHeight": 0,
            "imageRealWidth": 10,
            "imageWidth": 0,
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
            "placeCode": "310101016",
            "placeInfo": null,
            "placeName": "添加测试后台服务",
            "realmap": null,
            "remark": "",
            "zone": null
        },
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/proxima/010L61a6Z34WkVi69Ywrzi/findDVPlace"
        },
        "message": "获取摄像机数据成功",
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
