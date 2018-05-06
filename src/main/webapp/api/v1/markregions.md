[$PROFILE$]: extended

### <a name="获取标记区域"></a>获取标记区域

    GET /proxima/markRegions/{dvPlaceId}

**说明**

    获取标记区域
    例如:http://localhost:8080/galaxy/api/v1/proxima/markRegions/0jvmEBeyJebUcT8m76IaLY
    实体类路径:[com.microwise.proxima.bean.InfraredMarkRegionBean]

**光学图片浏览参数**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| dvPlaceId  | string | 摄像机点位  |

**返回**

    {
       "data": [
           {
               "id": "0xZk_Hn9RdrHr3X4gVOSRv",
               "name": "aaaa"
           },
           {
               "id": "2lV74UGvF8aFaqUn58BJmX",
               "name": "我的测试结果"
           },
           {
               "id": "3jqMGsa6B1lUogHYHU1PFB",
               "name": "aaa"
           }
       ],
       "message": "获取标记区域成功",
       "success": true
    }

**返回数据参数说明**

|   名称    |  类型  |   描述  |
|     -     |      - |    -    |
| id | String | 标记区域id |
| name | String | 标记区域名称 |
