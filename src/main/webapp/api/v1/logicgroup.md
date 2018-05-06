## <a name="blueplanet"></a> blueplanet - 环境监控
### <a name="站点列表"></a>站点列表

    GET /logicGroups

**说明**

    该接口可以传参数username，会返回该用户能访问到的所有的站点
    例如:http://localhost:8080/galaxy/api/v1/logicGroups?username=wang.geng@microwise-system.com
    也可以不传参数，则返回系统内所有的站点
    例如:http://localhost:8080/galaxy/api/v1/logicGroups
    实体类路径:[com.microwise.blackhole.bean.LogicGroup]

**参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| username  | string | 用户名(可空)  |

**返回**

    {
        "data": [
            {
                "username": "超级管理员",
                "userId": 1,
                "logicGroups": [
                    {
                        "logicGroupId": 1,
                        "siteId": "31010101",
                        "logicGroupName": "元智测试站点",
                        "logicGroups": [],
                        "logicGroupType": 1
                    },
                    {
                        "logicGroupId": 4,
                        "siteId": null,
                        "logicGroupName": "中心站点",
                        "logicGroups": [
                            {
                                "logicGroupId": 5,
                                "siteId": "11010101",
                                "logicGroupName": "故宫博物院",
                                "logicGroups": [],
                                "logicGroupType": 1
                            },
                            {
                                "logicGroupId": 6,
                                "siteId": "11010102",
                                "logicGroupName": "中国国家博物馆",
                                "logicGroups": [],
                                "logicGroupType": 1
                            }
                        ],
                        "logicGroupType": 1
                    },
                    {
                        "logicGroupId": 8,
                        "siteId": "61011501",
                        "logicGroupName": "秦始皇帝陵博物院",
                        "logicGroups": [],
                        "logicGroupType": 1
                    },
                    {
                        "logicGroupId": 9,
                        "siteId": null,
                        "logicGroupName": "研发部门系统联调",
                        "logicGroups": [],
                        "logicGroupType": 0
                    }
                ]
            }
        ],
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/logicGroups"
        },
        "message": "获取站点组列表成功",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| logicGroupId  | id | logicGroupId编号,站点组编号  |
| siteId  | String | 站点唯一标识  |
| logicGroupName | String  | 站点组名称 |
| logicGroupType   | int | 1本实例创建；2别的tomcat实例同步上来的  |
| logicGroups   | Object[] | 子站点集合  |
