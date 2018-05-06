[$PROFILE$]: extended

## <a name="orion"></a> orion - 藏品管理
### <a name="获取出入库列表"></a>获取出入库列表

    GET /api/orion/outEvent/{siteId}/{userId}

**参数**

| 名称    |  类型  | 描述     |
|   -     |   -    |    -     |
| siteId  | string | 站点ID   |
| userId  | string | 用户ID   |

**返回**

    {
        "success": true,
        "message": "获取出入库列表成功",
        "link": null,
        "data": [
            {
                "id": "10000203170801171232",
                "taskId": "2010",
                "siteId": "10000203",
                "useFor": "外出借展",
                "beginDate": "2017-08-01",
                "endDate": "2017-08-31",
                "applicant": "李白",
                "state": 0,
                "remark": "",
                "eventType": 1,
                "outBound": true,
                "eventState": "出库单核对",
                "actions": [
                    {
                        "text": "核对出库单",
                        "url": "api/orion/stockOutCheck/10000203170801171232/3",
                        "type": "get",
                        "parameters": null
                    }
                ]
            }
        ]
    }



### <a name="出入库单核对"></a>出入库单核对

    POST /api/orion/changeStatus/{eventId}/{taskId}/{userId}/{state}

**参数**

| 名称    |  类型  | 描述       |
|   -     |    -   |    -       |
| eventId | string | 事件ID     |
| taskId  | string | 任务ID     |
| userId  | int    | 用户ID     |
| state   | int    | 状态 (1 通过， 2 不通过) |

**返回**

    {
        "success": true,
        "message": "出入库单核对成功",
        "link": null,
        "data": null
    }



### <a name="签收"></a>签收

    GET /api/orion/claimTask/{taskId}/{userId}

**参数**

| 名称    |  类型  | 描述     |
|   -     |      - |    -     |
| taskId  | string | 任务ID     |
| userId  | int    | 用户ID     |


**返回**

    {
        "success": true,
        "message": "签收成功",
        "link": null,
        "data": null
    }

### <a name="审批"></a>审批

    POST /api/orion/complete

**参数**

| 名称    |  类型  | 描述     |
|   -     |      - |    -     |
| taskId  | string | 任务ID     |
| type   | string | 审批类型 馆内审批 internalVerifyApproved， 馆外审批 externalVerifyApproved |
| value  | boolean | 通过 true ，不通过 false     |


**返回**

    {
        "success": true,
        "message": "操作成功",
        "link": null,
        "data": null
    }


### <a name="库房确认"></a>库房确认

    GET /api/orion/doConfirmStockOut/{eventId}/{taskId}/{userId}

**参数**

| 名称    |  类型  | 描述     |
|   -     |      - |    -     |
| eventId | string | 事件ID     |
| taskId  | string | 任务ID     |
| userId  | int    | 用户ID     |


**返回**

    {
        "success": true,
        "message": "签收成功",
        "link": null,
        "data": null
    }

### <a name="取消申请"></a>取消申请

    GET /api/orion/cancelStockOut/{eventId}/{taskId}/{userId}

**参数**

| 名称    |  类型  | 描述     |
|   -     |      - |    -     |
| eventId | string | 事件ID     |
| taskId  | string | 任务ID     |
| userId  | int    | 用户ID     |


**返回**

    {
        "success": true,
        "message": "取消出库申请成功",
        "link": null,
        "data": null
    }

### <a name="回库"></a>回库

    GET /api/orion/returnStockOut/{eventId}/{userId}

**参数**

| 名称    |  类型  | 描述     |
|   -     |   -    |    -     |
| eventId | string | 事件ID     |
| userId  | int    | 用户ID     |


**返回**

    {
        "success": true,
        "message": "回库成功",
        "link": null,
        "data": null
    }