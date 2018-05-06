[$PROFILE$]: extended

### <a name="区域列表line"></a>区域列表(线型)

    GET /zoneLine

**说明**

    参数为站点编号，不可空
    返回的json数据中区域全为平级显示，没有父子关系
    例如:http://localhost:8080/galaxy/api/v1/zoneLine?siteId=61011501
    实体类路径:[com.microwise.blueplanet.bean.vo.ZoneVO]

**参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| siteId  | string | 站点编号  |

**返回**

    {
            "data": [
                {
                    "position": 1,
                    "parentId": "7f94f7a9-1989-4661-b6a6-9a5fa8a450e7",
                    "coordinateX": 0,
                    "planImage": "074958bc-f072-480b-a0d7-24ade58f7e45.jpg",
                    "coordinateY": 0,
                    "zoneName": "保管部一层",
                    "zoneId": "074958bc-f072-480b-a0d7-24ade58f7e45"
                },
                {
                    "position": 1,
                    "parentId": "074958bc-f072-480b-a0d7-24ade58f7e45",
                    "coordinateX": 0,
                    "planImage": null,
                    "coordinateY": 0,
                    "zoneName": "门卫入口",
                    "zoneId": "21f77019-68bb-47f2-8819-75094e8c3c07"
                },
                {
                    "position": 1,
                    "parentId": "51d155f9-02e4-4e74-8acd-2ef1d2d2d519",
                    "coordinateX": 0,
                    "planImage": null,
                    "coordinateY": 0,
                    "zoneName": "陶器库房",
                    "zoneId": "314e6ed9-7264-492c-88dd-efe29fa82ae9"
                },
                {
                    "position": 1,
                    "parentId": "074958bc-f072-480b-a0d7-24ade58f7e45",
                    "coordinateX": 0,
                    "planImage": "3e3d527f-96ab-4a4f-a078-4c77083ebaaa.png",
                    "coordinateY": 0,
                    "zoneName": "文物修复三室",
                    "zoneId": "3e3d527f-96ab-4a4f-a078-4c77083ebaaa"
                },
                {
                    "position": 1,
                    "parentId": "cbb6eac8-712b-43b2-84d4-376cd0755e8a",
                    "coordinateX": 0,
                    "planImage": "49b07928-0425-45a7-86c9-50346619c5ed.jpg",
                    "coordinateY": 0,
                    "zoneName": "陈列厅地下一层",
                    "zoneId": "49b07928-0425-45a7-86c9-50346619c5ed"
                },
                {
                    "position": 1,
                    "parentId": "7f94f7a9-1989-4661-b6a6-9a5fa8a450e7",
                    "coordinateX": 0,
                    "planImage": "51d155f9-02e4-4e74-8acd-2ef1d2d2d519.jpg",
                    "coordinateY": 0,
                    "zoneName": "保管部地下室",
                    "zoneId": "51d155f9-02e4-4e74-8acd-2ef1d2d2d519"
                },
                {
                    "position": 1,
                    "parentId": "074958bc-f072-480b-a0d7-24ade58f7e45",
                    "coordinateX": 0,
                    "planImage": null,
                    "coordinateY": 0,
                    "zoneName": "文物工作室",
                    "zoneId": "60d1cf04-8903-4b9b-92f4-322b575b9968"
                },
                {
                    "position": 1,
                    "parentId": "074958bc-f072-480b-a0d7-24ade58f7e45",
                    "coordinateX": 0,
                    "planImage": null,
                    "coordinateY": 0,
                    "zoneName": "文物修复二室",
                    "zoneId": "732b7a6b-0986-4a10-a510-e614e4eab650"
                },
                {
                    "position": 1,
                    "parentId": null,
                    "coordinateX": 0,
                    "planImage": null,
                    "coordinateY": 0,
                    "zoneName": "文物保藏部、保护部",
                    "zoneId": "7f94f7a9-1989-4661-b6a6-9a5fa8a450e7"
                },
                {
                    "position": 2,
                    "parentId": "e3bba3e0-39de-4d9d-86df-ad9bbee61f01",
                    "coordinateX": 0,
                    "planImage": "80522c34-4c14-411b-b539-e2b4b35bce45.jpg",
                    "coordinateY": 0,
                    "zoneName": "南门阙遗址",
                    "zoneId": "80522c34-4c14-411b-b539-e2b4b35bce45"
                },
                {
                    "position": 1,
                    "parentId": null,
                    "coordinateX": 0,
                    "planImage": "88f4fd8c-9bda-4573-ae00-249ed3549368.png",
                    "coordinateY": 0,
                    "zoneName": "秦陵兵马俑三号坑",
                    "zoneId": "88f4fd8c-9bda-4573-ae00-249ed3549368"
                },
                {
                    "position": 1,
                    "parentId": "cbb6eac8-712b-43b2-84d4-376cd0755e8a",
                    "coordinateX": 0,
                    "planImage": "8d8663b2-f6a7-4625-b92b-3642d5a17b8c.jpg",
                    "coordinateY": 0,
                    "zoneName": "陈列厅一层",
                    "zoneId": "8d8663b2-f6a7-4625-b92b-3642d5a17b8c"
                },
                {
                    "position": 1,
                    "parentId": "51d155f9-02e4-4e74-8acd-2ef1d2d2d519",
                    "coordinateX": 0,
                    "planImage": "afed3a75-30c2-41a5-9c10-0721ef957182.png",
                    "coordinateY": 0,
                    "zoneName": "纸质库房",
                    "zoneId": "afed3a75-30c2-41a5-9c10-0721ef957182"
                },
                {
                    "position": 1,
                    "parentId": null,
                    "coordinateX": 0,
                    "planImage": "cbb6eac8-712b-43b2-84d4-376cd0755e8a.jpg",
                    "coordinateY": 0,
                    "zoneName": "秦始皇帝陵文物陈列厅",
                    "zoneId": "cbb6eac8-712b-43b2-84d4-376cd0755e8a"
                },
                {
                    "position": 1,
                    "parentId": "074958bc-f072-480b-a0d7-24ade58f7e45",
                    "coordinateX": 0,
                    "planImage": "d461f060-dae4-4713-8241-5e536f05604b.png",
                    "coordinateY": 0,
                    "zoneName": "铜器库房",
                    "zoneId": "d461f060-dae4-4713-8241-5e536f05604b"
                },
                {
                    "position": 1,
                    "parentId": null,
                    "coordinateX": 0,
                    "planImage": "d4676ccd-a0cc-4f0d-8061-85d9a41fed72.png",
                    "coordinateY": 0,
                    "zoneName": "秦陵兵马俑二号坑",
                    "zoneId": "d4676ccd-a0cc-4f0d-8061-85d9a41fed72"
                },
                {
                    "position": 1,
                    "parentId": null,
                    "coordinateX": 0,
                    "planImage": "e1c73a09-09a5-4829-aece-47efc95eb5fa.jpg",
                    "coordinateY": 0,
                    "zoneName": "秦陵兵马俑一号坑",
                    "zoneId": "e1c73a09-09a5-4829-aece-47efc95eb5fa"
                },
                {
                    "position": 2,
                    "parentId": null,
                    "coordinateX": 0,
                    "planImage": "e3bba3e0-39de-4d9d-86df-ad9bbee61f01.jpg",
                    "coordinateY": 0,
                    "zoneName": "秦始皇帝陵陵区-丽山园",
                    "zoneId": "e3bba3e0-39de-4d9d-86df-ad9bbee61f01"
                },
                {
                    "position": 1,
                    "parentId": "e1c73a09-09a5-4829-aece-47efc95eb5fa",
                    "coordinateX": 0,
                    "planImage": null,
                    "coordinateY": 0,
                    "zoneName": "一号坑后修复区",
                    "zoneId": "e69d6f27-8efd-432b-978a-7bab7fbc854e"
                },
                {
                    "position": 2,
                    "parentId": "e3bba3e0-39de-4d9d-86df-ad9bbee61f01",
                    "coordinateX": 0,
                    "planImage": "eea8c567-c96e-4afa-92fc-f8fdc1446250.png",
                    "coordinateY": 0,
                    "zoneName": "K9901陪葬坑",
                    "zoneId": "eea8c567-c96e-4afa-92fc-f8fdc1446250"
                },
                {
                    "position": 2,
                    "parentId": "e3bba3e0-39de-4d9d-86df-ad9bbee61f01",
                    "coordinateX": 0,
                    "planImage": "f11d155d-d5f0-4091-91cf-5eec78787501.png",
                    "coordinateY": 0,
                    "zoneName": "K0006陪葬坑",
                    "zoneId": "f11d155d-d5f0-4091-91cf-5eec78787501"
                },
                {
                    "position": 1,
                    "parentId": "49b07928-0425-45a7-86c9-50346619c5ed",
                    "coordinateX": 0,
                    "planImage": null,
                    "coordinateY": 0,
                    "zoneName": "精品库",
                    "zoneId": "f5d02f84-36bb-4739-aa99-64dccbcea92a"
                },
                {
                    "position": 1,
                    "parentId": "074958bc-f072-480b-a0d7-24ade58f7e45",
                    "coordinateX": 0,
                    "planImage": null,
                    "coordinateY": 0,
                    "zoneName": "文物修复一室",
                    "zoneId": "ffe434da-28c3-4359-a372-94138a2e7727"
                }
            ],
            "link": {
                "url": "http://localhost:8080/galaxy/api/v1/zoneLine?siteId=61011501"
            },
            "message": "获取区域列表成功",
            "success": true
        }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| zoneName  | String | 区域名称  |
| zoneId  | String | 区域ID  |
| parentId  | String | 父区域ID  |
