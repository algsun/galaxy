## <a name="blackhole"></a> blackhole - 系统管理
### <a name="登录"></a>登录

    GET /login

**参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| username  | string | 用户名  |
| password  | string | 密码    |

**返回**

    {
        "data": {
            "isActiveUser": true,
            "logicGroupId": 1,
            "username": "高辉",
            "siteId": "31010101",
            "token": "c20436f07f2fd42ea7001537f9d82e89",
            "userId": 5,
            "logicGroupName": "元智测试站点",
            "logicGroups": [],
            "logicGroupType": 1
        },
        "message": "登录成功",
        "success": true
    }
