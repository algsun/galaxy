[$PROFILE$]: extended

### <a name="区域列表tree"></a>区域列表(树型)

    GET /zoneTree

**说明**

    参数为站点编号，不可空
    返回的json数据中区域全树形结构，表明了区域之间的层级关系
    例如:http://localhost:8080/galaxy/api/v1/zoneTree?siteId=61011501
    实体类路径:[com.microwise.blueplanet.bean.vo.ZoneVO]

**参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| siteId  | string | 站点编号  |

**返回**

    {
        "data": [
            {
                "parentId": null,
                "zones": [
                    {
                        "parentId": "546dcc09-e0c0-468d-9f4f-d51253df0bf0",
                        "zones": [
                            {
                                "parentId": "12a8cf2c-2d53-4e32-a362-572108fa4ae9",
                                "zones": [
                                    {
                                        "parentId": "15323ac9-ed27-4b2f-ba10-3bcff47c0470",
                                        "zones": [
                                            {
                                                "parentId": "e62f1a96-cb11-471d-8b43-78061091a985",
                                                "zones": [],
                                                "zoneName": "4B",
                                                "zoneId": "7aaef1cb-e693-4c75-98a3-05eb5f9aff4e"
                                            }
                                        ],
                                        "zoneName": "01",
                                        "zoneId": "e62f1a96-cb11-471d-8b43-78061091a985"
                                    }
                                ],
                                "zoneName": "T54",
                                "zoneId": "15323ac9-ed27-4b2f-ba10-3bcff47c0470"
                            },
                            {
                                "parentId": "12a8cf2c-2d53-4e32-a362-572108fa4ae9",
                                "zones": [],
                                "zoneName": "皇帝尸体",
                                "zoneId": "0354c26c-3b0c-4350-9a05-28e9b802e45e"
                            }
                        ],
                        "zoneName": "一号坑一号墓穴",
                        "zoneId": "12a8cf2c-2d53-4e32-a362-572108fa4ae9"
                    },
                    {
                        "parentId": "546dcc09-e0c0-468d-9f4f-d51253df0bf0",
                        "zones": [],
                        "zoneName": "一号坑二号墓穴",
                        "zoneId": "fbbbb78f-586d-46e1-ba0c-e6283feec4f4"
                    }
                ],
                "zoneName": "一号坑",
                "zoneId": "546dcc09-e0c0-468d-9f4f-d51253df0bf0"
            },
            {
                "parentId": null,
                "zones": [],
                "zoneName": "三号坑",
                "zoneId": "3de6c1b2-d6ab-48f6-8345-1c6d856c4c6d"
            },
            {
                "parentId": null,
                "zones": [],
                "zoneName": "二号坑",
                "zoneId": "21939ae4-5e06-4566-897a-bf6179fc8405"
            }
        ],
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/zoneTree?siteId=61011501"
        },
        "message": "获取区域列表成功",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| parentId  | String | 父区域ID  |
| zoneName  | String | 区域名称  |
| zoneId  | String | 区域ID  |
| zones | Object[] | 子区域集合 |
