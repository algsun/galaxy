[$PROFILE$]: extended

### <a name="区域当前人员显示"></a>区域当前人员显示

    GET /api/v1/uma/{siteId}/{zoneId}/{queryType}/{hourInterval}/realtimeDetail

**说明**

    获取区域当前人员信息
    例如:http://localhost:8080/galaxy/api/v1/uma/10000203/628d7e89-c6ec-4756-9479-be5165bc72e7/0/0/realtimeDetail
    实体类路径:[com.microwise.uma.bean.ZoneBean]

**参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| siteId    | string | 站点ID  |
| zoneId    | string | 区域ID  |
| queryType | int    | 查询类型 (0 按规则统计，1 按位置统计)   |
| hourInterval  | int | 小时数 (按位置统计) |

**返回**

    {
        "data": [
            {
                "hasChildren": false,
                "hide": false,
                "level": 0,
                "numberOfPeople": 0,
                "occurrenceTime": "2014-09-04 10:33:03",
                "parentId": "628d7e89-c6ec-4756-9479-be5165bc72e7",
                "showSub": false,
                "timeStr": "23小时前",
                "userId": "6",
                "userName": "郭田",
                "zoneId": "a64e8e80-c9ee-4f7d-9f3a-a067a1488117",
                "zoneName": "元智展厅"
            },
            {
                "hasChildren": false,
                "hide": false,
                "level": 0,
                "numberOfPeople": 0,
                "occurrenceTime": "2014-09-04 10:33:03",
                "parentId": "628d7e89-c6ec-4756-9479-be5165bc72e7",
                "showSub": false,
                "timeStr": "23小时前",
                "userId": "10",
                "userName": "邓宏",
                "zoneId": "a64e8e80-c9ee-4f7d-9f3a-a067a1488117",
                "zoneName": "元智展厅"
            }
        ],
        "link": {
            "url": "http://192.168.0.184:8080/galaxy/api/v1/uma/10000203/628d7e89-c6ec-4756-9479-be5165bc72e7/0/0/userLocationDetails"
        },
        "message": "获取区域人员详情信息成功",
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
