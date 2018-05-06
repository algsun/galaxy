[$PROFILE$]: extended

### <a name="人员信息"></a>人员信息

    GET /uma/{logicGroupId}/users

**说明**

    查询人员信息
    例如:http://localhost:8080/galaxy/api/v1/uma/2/users
    实体类路径:[com.microwise.uma.bean.PersonBean]

**参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| logicGroupId   | int | logicGroupId |

**返回**

    {
        "data": [
            {
                "id": 3,
                "personName": "产品展示管理员"
            },
            {
                "id": 6,
                "personName": "郭田"
            },
            {
                "id": 10,
                "personName": "邓宏"
            },
            {
                "id": 11,
                "personName": "关林霞"
            }
        ],
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/uma/2/users"
        },
        "message": "获取人员信息成功",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| id | int | 人员编号 |
| personName | String | 人员名称 |

[$PROFILE$]: extended

### <a name="人员行为查询"></a>人员行为查询

    GET /uma/{userId}/{order}/{currentPage}/userRules

**说明**

    查询人员行为信息
    例如:http://localhost:8080/galaxy/api/v1/uma/10/0/1/userRules?startDate=2014-01-01&endDate=2014-09-02
    实体类路径:[com.microwise.uma.bean.AbstractUserActionBean]
               [com.microwise.uma.bean.UserActionBean]

**参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| userId   | int | 用户ID |
| order   | int | 排序规则  0 升序  1 降序 |
| currentPage   | int | 分页页码 |
| startDate   | Date | 开始时间 |
| endDate   | Date | 结束时间 |

**返回**

    {
        "data": {
            "pageCount": 17,
            "staffRuleList": [
                {
                    "backAction": {
                        "id": 841,
                        "occurrenceTime": "2014-08-15 14:47:46",
                        "person": {
                            "id": 10,
                            "personName": "邓宏"
                        },
                        "rule": {
                            "enable": false,
                            "id": 16,
                            "ruleName": "出--智慧博物馆",
                            "type": 4
                        },
                        "type": 4
                    },
                    "duration": 923,
                    "goAction": {
                        "id": 841,
                        "occurrenceTime": "2014-08-15 14:32:23",
                        "person": {
                            "id": 10,
                            "personName": "邓宏"
                        },
                        "rule": {
                            "enable": false,
                            "id": 15,
                            "ruleName": "进--智慧博物馆",
                            "type": 3
                        },
                        "type": 3
                    },
                    "id": 841,
                    "person": {
                        "id": 10,
                        "personName": "邓宏"
                    },
                    "rule": {
                        "enable": false,
                        "id": 14,
                        "ruleName": "进出--智慧博物馆",
                        "type": 2
                    },
                    "type": 2
                }
            ]
        },
        "link": {
            "url": "http://localhost:8080/galaxy/api/v1/uma/10/0/1/userRules"
        },
        "message": "获取实时人员分布统计信息成功",
        "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| pageCount | int | 分页页数 |
| staffRuleList | List | 用户行为规则集合 |
| goAction | UserActionBean | 用户行为实体类 |
| backAction | UserActionBean | 用户行为实体类 |
| id | int | 区域级别 |
| occurrenceTime | Date | 发生时间 |
| person | PersonBean | 匹配的人实体类 |
| id (person) | int | 人员ID |
| personName | String | 人员名称 |
| rule | RuleBean | 行为规则实体类 |
| enable | boolean | 规则是否启用(true 启用, false 停用) |
| id(rule) | String | 规则编号 |
| ruleName | String | 规则名称 |
| duration | long | 停留时间(秒) |
| type | int | 类型 (1.单程, 2.往返, 3.往, 4. 返) |
