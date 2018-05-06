[$PROFILE$]: extended

## <a name="halley"></a> halley - 哈雷
### <a name="获取外展列表"></a>获取外展列表

    GET /exibitions

**参数**

| 名称    |  类型  | 描述     |
|   -     |      - |    -     |
| siteId  | string | 站点ID   |

**返回**

    {
      "success": true
      "message": "调用成功"
      "data":[
         "exhibitionId": 1234
         ...
      ]
    }



### <a name="更新外展状态"></a>更新外展状态

    POST /exhibitions

**参数**

| 名称    |  类型  | 描述     |
|   -     |      - |    -     |
| exhibitionId  | number | 外展ID   |
| state   | number | 外展状态     |
| operator   | number | 操作人     |

**返回**

    {
      "success": true
      "message": "调用成功"
    }



### <a name="查询外展车辆及相关设备"></a>查询外展车辆及相关设备

    GET /exhibitions/{exhibitionId}/cars

**参数**

| 名称    |  类型  | 描述     |
|   -     |      - |    -     |
| exhibitionId  | number | 外展ID   |


**返回**

    {
      "success": true
      "message": "调用成功"
      "data": [
        {
          "id": 1,
          "plateNumber": "陕A12345",
          ...
        }
      ]
    }