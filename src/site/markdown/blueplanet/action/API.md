# action API

此文档包含 blueplanet 业务系统下的 action.
默认后缀表示 ajax 返回值的类型, 例如 `xxxx.json` 表示 json 格式的返回值, 或者 `xxx.xml`.
以下所有 action 请求地址前面都有 `/blueplanet`.

# 公共模块
***

## /zone-device-tree.json

描述: 当前站点下的站点区域设备树(ztree)

类: app/ZoneDeviceTreeAction

## /zone-device-path.json?deviceId

描述：概览导航，设备的路径

参数：deviceId 设备id

类：app/ZoneDevicePathAction

## /zone-device-path.json?zoneId

描述：概览导航, 区域路径

参数：zoneId 区域id

类：app/ZoneDevicePathAction


# site (站点)

***

## /site

描述: 站点概览

类: site/SiteAction

## /network-topo

描述: 网络拓扑图

类: site/SiteAction

## /site/sensorinfes.json

描述: 当前站点下的可用的监测指标

类: site/SensorinfoOfSiteAction

## /site/realtime-data

描述： 站点实时数据

类：site/RealTimeDataAction

## /site/realtime-data.json

描述：获取站点实时数据

参数: `Integer[] sensorPhysicalIds`(可选)

类: site/GetRealtimeDataAction , site/GetFilterRealTimeDataAction


# zone (区域)
***

## /zone/{zoneId}

描述: 区域概览页面

类: zone/IndexAction

## /zone/{zoneId}/sensorinfoes.json

描述:当前区域可用的监测指标

类：zone/GetZoneSensorinfoesAction

## /zone/{zoneId}/realtime-data

描述:区域实时数据

类: zone/ZoneRealTimeDataAction

## /zone/{zoneId}/realtime-data.json

描述:当前区域实时数据

参数: `sensorPhysicalIds:List<Integer>` (可选) 监测指标过虑

类：zone/ZoneRealTimeDataAction

## /zone/{zoneId}/manage

描述：区域管理

参数：zoneId 区域id

类：zone/manager/ManagerIndexAction

## /zone/{zoneId}/delete.json

描述:删除区域

参数：zoneId 区域id

类：zone/manager/DeleteZoneAction

## /zone/add.json

描述:添加区域

参数:zoneName 区域名称

类：zone/manager/AddZoneAction

## /zone/move.json

描述：移动区域

参数：zoneId 区域id,newParentId 区域新上级区域id

类：zone/manager/MoveZoneAction

## /zone/update.json

描述：更新区域信息

参数：zoneName 区域名称，planImage 区域实景图

类:zone/manager/UpdateZoneAction

## /zone/children.json

描述：获取区域的直接下级区域

参数：zoneId 区域id

类：zone/manager/GetZoneChildrenAction

# device (设备)
***

## /devices

描述: 设备管理 -> 已绑定设备查询

类: device/manage/QueryBindingAction

## /devices?unbinding

描述：设备管理 -> 未绑定设备查询

类：device/manage/QueryUnBindingAction

## /device/{deviceId}/detail

描述: 设备详情

类: device/manage/DeviceDetailAction

## /device/{deviceId}/detail  POST

描述：修改设备信息（名称，所属区域）

类 device/DeviceDetailEditAction

## /device/{deviceId}/detail/edit

描述：跳转到编辑设备页面

类：device/DeviceDetailEditAction

## /device/{deviceId}

描述:设备概览

类: device/IndexAction

## /device/{deviceId}.json GET

描述：设备json信息

类：deivce/DeviceAction

## /device/{deviceId}/realtime-data

描述: 设备实时数据

类: device/DeviceRealtimeDataNewAction

## /device/{deviceId}/realtime-data.json

描述：设备实时数据

类：device/RefreshRealtimeDataAction

## /device/{deviceId}/realtime-chart

描述：设备实时图形

类：device/RealtimeChartAction

## /device/{deviceId}/history-data

描述：设备历史数据

类：device/HistoryDataAction

## /device/{deviceId}/history-data-count.json

描述：设备历史数据数目

类：device/HistoryDataExportAction

## /history-data-export.action

描述：设备历史数据导出

类：device/HistoryDataExport.action

## /device/{deviceId}/waiting-for-exorpt

描述：设备历史数据导出等待页面

类：device/HistoryDataExportWaitingAction

## /device/{deviceId}/sensorinfoes.json

描述：获取设备下激活的监测指标

类：device/GetDeviceSensorinfoesAction

## /device/{deviceId}/sensorinfo/{sensorPhisicalId}.json  POST

描述：添加或者修改自定义指标公式系数

参数：params 自定义系数

类：device/manage/SaveOrUpdateCoefficientAction

## /device/{deviceId}/sensorinfo/{sensorPhisicalId}.json?reset

描述：恢复默认指标公式系数

类：device/manage/ResetCoefficientAction

## /device/{deviceId}/sensorinfoes/reset-all.json

描述：恢复所有默认监测指标公式系数

类：device/manage/ResetCoefficientAction

## /device/{deivceId}/unbind.json

描述：设备解除绑定

类：device/manage/UnbindDeviceAction

## /device/bind.json

描述：绑定设备

参数：

* zoneId:String  所属区域ID(必需)
* deviceIds:String[]  设备ID集合(必需)

类：device/manage/BindDeviceActino

## /device/{deviceId}/rainfall-chart

描述：初始化降雨量图页面

类：device/chart/RainfallChartAction

## /device/{deviceId}/rainfall-chart.json

描述：请求设备监测指标的图形数据

参数：

* dateType  时间类型(1-年; 2-月; 3-日)
* date  根据时间类型指定的日期
* sensorIds  监测指标数组

类：device/chart/RainfallChartAction

## /device/{deviceId}/light-chart

描述：初始化累积光照图页面

类：/device/chart/LightChartAction

## /device/{deviceId}/light-chart.json

描述：请求设备累积光照图型数据

参数：

* dateType  时间类型(1-年; 2-月; 3-日)
* date  根据时间类型指定的日期

类：/device/chart/LightChartAction

## /device/{deviceId}/basic-chart

描述：基础曲线图

参数：

* device 设备id

类：/device/chart/BasicChart2Action

## /device/{deviceId}/basic-chart.json

描述：设备基础曲线图数据获取

参数：

* deviceId 设备id
* startTime 起始时间
* endTime 截止时间
* sensorinfoes[] 监测指标

类 /device/chart/GetBasicChartDataActioin

## /device/{deviceId}/windrose-chart

描述：风向玫瑰图页面跳转

参数：

* deviceId 设备id
* timeType 时间类型
* time 时间

类：/device/chart/WindroseChartAction
