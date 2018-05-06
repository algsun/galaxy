[$PROFILE$]: extended

### <a name="获取摄像机图片"></a>获取摄像机图片

    GET /proxima/findPictures?dvPlaceId={dvPlaceId}&startDate={startDate}&endDate={endDate}

**说明**

    获取摄像机图片
    例如:http://localhost:8080/galaxy/api/v1/proxima/findPictures?dvPlaceId=1eMkwQsqJ0w9GsMPSEybFn&startDate=2014-07-27%2007:48:26&endDate=2014-07-28%2016:08:29%22
    实体类路径:[com.microwise.proxima.bean.Pictures]

**图片浏览参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| dvPlaceId  | string | 摄像机点位id  |
| startDate  | Date | 开始时间  |
| endDate  | Date | 结束时间  |

**返回**

    {
        "data": [
            {
                "analyzable": true,
                "dv": null,
                "id": "3hDPB43CJ1DbFbKuD2kPuL",
                "name": "192.168.0.18_01_20140728223833_TIMING.jpg",
                "path": "100002030",
                "saveTime": "2014-07-28T14:44:00"
            },
            {
                "analyzable": true,
                "dv": null,
                "id": "3hCiItoKZ3QrtrHprHjsf2",
                "name": "192.168.0.18_01_20140728181620_TIMING.jpg",
                "path": "100002030",
                "saveTime": "2014-07-28T10:22:00"
            },
            {
                "analyzable": true,
                "dv": null,
                "id": "3qFQGBNa90kXc_99qxSgk7",
                "name": "192.168.0.18_01_20140728141620_TIMING.jpg",
                "path": "100002030",
                "saveTime": "2014-07-28T06:22:00"
            },
            {
                "analyzable": true,
                "dv": null,
                "id": "2QLAwuKqh9RqGYhN7Qb5t-",
                "name": "192.168.0.18_01_20140728101620_TIMING.jpg",
                "path": "100002030",
                "saveTime": "2014-07-28T02:22:00"
            },
            {
                "analyzable": true,
                "dv": null,
                "id": "1OdMRpM8p0za_80Kb85C5H",
                "name": "192.168.0.18_01_20140728061620_TIMING.jpg",
                "path": "100002030",
                "saveTime": "2014-07-27T22:22:00"
            },
            {
                "analyzable": true,
                "dv": null,
                "id": "1ayYwZGHx5ZpV34ERK54e_",
                "name": "192.168.0.18_01_20140728021620_TIMING.jpg",
                "path": "100002030",
                "saveTime": "2014-07-27T18:22:00"
            },
            {
                "analyzable": true,
                "dv": null,
                "id": "3UNFged054hE-xvOwx-zqO",
                "name": "192.168.0.18_01_20140727221620_TIMING.jpg",
                "path": "100002030",
                "saveTime": "2014-07-27T14:22:00"
            }
        ],
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/proxima/findPictures?dvPlaceId=1eMkwQsqJ0w9GsMPSEybFn&startDate=2014-07-27%2007:48:26&endDate=2014-07-28%2016:08:29"
        },
        "message": "获取摄像机图片数据成功",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| id | String | ID |
| name | String | 图片名称 |
| path | String | 图片路径 |
| analyzable | boolean | 是否可以解析温度 |
| saveTime | Date | 保存时间 |
