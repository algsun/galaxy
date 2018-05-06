[$PROFILE$]: extended

###<a name="行为统计"></a>行为统计

    GET /uma/{siteId}/{dateType}/behaviorRules

**说明**

    获取行为统计信息
    例如:http://localhost:8080/galaxy/api/v1/uma/10000203/1/behaviorRules?date=2014&ruleName=进出--智慧博物馆
    实体类路径:[com.microwise.uma.bean.RuleStatBean]
              [com.microwise.uma.bean.RuleBean]

**参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| siteId    | string | 站点ID  |
| dateType | int    | 时间类型 (1 年,2 月,3 日)  |
| date  | Date | 时间 |
| ruleName  | string | 规则名称 |

**返回**

    {
        "data": [
            {
                "childRuleStats": [
                    {
                        "count": 214,
                        "id": 15,
                        "parentId": 14,
                        "ruleName": "进--智慧博物馆",
                        "type": 3
                    },
                    {
                        "count": 214,
                        "id": 16,
                        "parentId": 14,
                        "ruleName": "出--智慧博物馆",
                        "type": 4
                    }
                ],
                "count": 176,
                "id": 14,
                "parentId": 0,
                "ruleName": "进出--智慧博物馆",
                "type": 2
            }
        ],
        "link": {
            "url": "http://192.168.0.184:8080/galaxy/api/v1/uma/10000203/1/behaviorRules"
        },
        "message": "获取行为统计信息成功",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| childRuleStats | RuleStatBean | 行为规则统计实体类 |
| count | List | 用户行为集合 |
| id | int | 规则编号 |
| parentId | UserActionBean | 用户行为实体类 |
| ruleName | String | 规则名称 |
| type | int | 类型 (1.单程, 2.往返, 3.往, 4. 返) |
