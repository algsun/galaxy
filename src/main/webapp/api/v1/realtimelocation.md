[$PROFILE$]: extended

### <a name="实时人员分布"></a>实时人员分布

    GET /uma/{siteId}/{queryType}/{hourInterval}/realtimeLocations

**说明**

    按规则统计或者按位置统计人员实时分布情况
    例如:http://localhost:8080/galaxy/api/v1/uma/10000203/0/0/realtimeLocations
    实体类路径:[com.microwise.uma.bean.ZoneBean]

**参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| siteId    | string | 站点ID  |
| queryType | int    | 查询类型 (0 按规则统计，1 按位置统计)   |
| hourInterval  | int | 小时数 (按位置统计) |

**返回**

    {
        "data": [
            {
                "hasChildren": true,
                "hide": false,
                "level": 1,
                "numberOfPeople": 2,
                "occurrenceTime": "2014-09-04 10:33:03",
                "showSub": true,
                "subZoneList": [
                    {
                        "hasChildren": false,
                        "hide": false,
                        "level": 2,
                        "numberOfPeople": 2,
                        "occurrenceTime": "2014-09-04 10:33:03",
                        "parentId": "628d7e89-c6ec-4756-9479-be5165bc72e7",
                        "showSub": true,
                        "timeStr": "4小时前",
                        "userId": "10",
                        "userName": "邓宏",
                        "zoneId": "a64e8e80-c9ee-4f7d-9f3a-a067a1488117",
                        "zoneName": "元智展厅"
                    },
                    {
                        "hasChildren": false,
                        "hide": false,
                        "level": 2,
                        "numberOfPeople": 0,
                        "parentId": "628d7e89-c6ec-4756-9479-be5165bc72e7",
                        "showSub": true,
                        "zoneId": "c87d7b73-9fe7-4881-8256-6eec9c4dd9df",
                        "zoneName": "五楼走廊"
                    }
                ],
                "timeStr": "4小时前",
                "userId": "10",
                "userName": "邓宏",
                "zoneId": "628d7e89-c6ec-4756-9479-be5165bc72e7",
                "zoneName": "元智五楼"
            },
            {
                "hasChildren": false,
                "hide": false,
                "level": 2,
                "numberOfPeople": 2,
                "occurrenceTime": "2014-09-04 10:33:03",
                "parentId": "628d7e89-c6ec-4756-9479-be5165bc72e7",
                "showSub": true,
                "timeStr": "4小时前",
                "userId": "10",
                "userName": "邓宏",
                "zoneId": "a64e8e80-c9ee-4f7d-9f3a-a067a1488117",
                "zoneName": "元智展厅"
            }
        ],
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/uma/10000203/0/0/realtimeLocations"
        },
        "message": "获取实时人员分布统计信息成功",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| hasChildren | boolean | 是否有子区域：true 有 ，false 没有 |
| hide | boolean | 实时定位中是否隐藏：true 隐藏 ，false 不隐藏 |
| level | int | 区域级别 |
| numberOfPeople | int | 当前人员总数 |
| occurrenceTime | Date | 实时位置信息发生时间 |
| parentId | String | 父区域ID |
| showSub | boolean | 是否展开子区域：true:展开 false:不展开 |
| timeStr | String | 间隔时间 |
| userId | String | 用户ID |
| userName | String | 用户名 |
| zoneId | String | 区域编号 |
| zoneName | String | 区域名称 |
