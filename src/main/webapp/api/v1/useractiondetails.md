[$PROFILE$]: extended

###<a name="行为规则详情"></a>行为规则详情

GET /uma/{ruleId}/{dateType}/{index}/userActionDetails

**说明**

    查询行为规则详情
    例如:http://localhost:8080/galaxy/api/v1/uma/14/1/1/userActionDetails?date=2014
    实体类路径:[com.microwise.uma.bean.UserCensusActionBean]
              [com.microwise.uma.bean.UserActionBean]
              [com.microwise.uma.bean.PersonBean]
              [com.microwise.uma.bean.RuleBean]

**参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| ruleId    | string | 规则编号  |
| dateType | int    | 时间类型 (1 年,2 月,3 日)  |
| index  | int | 分页页码 |

**返回**

    {
        "data": {
            "pageCount": 18,
            "userActionList": [
                {
                    "backAction": {
                        "id": 667,
                        "occurrenceTime": "2014-02-28 16:22:50",
                        "person": {
                            "id": 10,
                            "personName": "邓宏"
                        },
                        "rule": {
                            "id": 16,
                            "ruleName": "出--智慧博物馆",
                            "type": 4
                        },
                        "type": 4
                    },
                    "duration": 12,
                    "goAction": {
                        "id": 667,
                        "occurrenceTime": "2014-02-28 16:22:37",
                        "person": {
                            "id": 10,
                            "personName": "邓宏"
                        },
                        "rule": {
                            "id": 15,
                            "ruleName": "进--智慧博物馆",
                            "type": 3
                        },
                        "type": 3
                    },
                    "id": 667,
                    "person": {
                        "id": 10,
                        "personName": "邓宏"
                    },
                    "rule": {
                        "id": 14,
                        "ruleName": "进出--智慧博物馆",
                        "type": 2
                    },
                    "type": 2
                }
            ]
        },
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/uma/14/1/1/userActionDetails"
        },
        "message": "查询往返行为规则详情成功",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| pageCount | int | 分页页数 |
| userActionList | List | 用户行为集合 |
| goAction | UserActionBean | 用户行为实体类 |
| backAction | UserActionBean | 用户行为实体类 |
| id | int | 区域级别 |
| occurrenceTime | Date | 发生时间 |
| person | PersonBean | 匹配的人实体类 |
| id (person) | int | 人员ID |
| personName | String | 人员名称 |
| rule | RuleBean | 行为规则实体类 |
| id(rule) | String | 规则编号 |
| ruleName | String | 规则名称 |
| duration | long | 停留时间(秒) |
| type | int | 类型 (1.单程, 2.往返, 3.往, 4. 返) |
