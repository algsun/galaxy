/**
 * Created by lijianfei on 16/2/5.
 */
$.extend(message, {
    // common
    tips: "友情提示",
    noData: "暂无数据",
    ok: "确定",
    cancel: "取消",
    default: "默认",
    pleaseSelect: "请选择",
    sensor: "监测指标",
    operation: "操作",
    delete: "删除",
    time: "时间",
    show: "显示",
    hide: "隐藏",
    loading: "正在加载",
    device: "设备",
    location: "位置点",
    reset: "重置",
    zoomBy: "按1:1缩放",
    export: "导出图片",
    subscribe: "订阅",
    unsubscribe: "取消订阅",
    subscribed: "已订阅",
    unit: "单位",
    node: "节点",
    relay: "中继",
    masterModule: "主模块",
    slaveModule: "从模块",
    controlModule: "控制模块",
    gateway: "网关",
    all: "全部",
    day: "日",
    week: "周",
    month: "月",
    year: "年",
    january: "1月",
    february: "2月",
    march: "3月",
    april: "4月",
    may: "5月",
    june: "6月",
    july: "7月",
    august: "8月",
    september: "9月",
    october: "10月",
    november: "11月",
    december: "12月",

    // realtime-data-util
    normal: "正常",
    lowVoltage: "低电",
    powerDown: "掉电",
    overtime: "超时",
    unknown: "未知",
    normalMode: "正常模式",
    inspection: "巡检模式",
    nothing: "暂无",

    // site-zone-realtime-data
    noDevice: "无设备",
    noSensor: "已无选中的监测指标",

    // device-status
    deviceState: "设备状况",
    percent: "百分比",

    // zone-stability
    regionalStability: "区域稳定性",
    stability: "稳定性",

    // day-light.js
    accumulatedLight: "累计光照",

    // co2-concentration.js
    carbonDioxide: "二氧化碳",

    // temperature-avg.js
    dailyMeanTemperatureStatistics: "温度日均值统计",
    dailyMeanTemperature: "日均温度",
    areaDailyMeanTemperature: "区域温度日均值",

    // realtime-temperature.js
    realTimeTemperature: "实时温度",
    temperature: "温度",

    // realtime-humidity.js
    realTimeHumidity: "实时湿度",
    humidity: "湿度",

    // sensor-useInfo.js
    sensorCoverage: "监测指标覆盖率",
    sensorCoverageNotes: "监测指标在位置点被使用的次数",
    times: "次",
    useTimes: "使用次数",

    //zone-humidity.js
    humidityWaveRange: "湿度波动范围",
    humidityWaveRangeNotes: "前一天区域的湿度最大值、最小值、平均值",
    averageValue: "均值",

    // js\index.js
    maximumValue: "最高值",
    minimumValue: "最低值",

    // js/device/query-binding-devices.js
    actionDown: "系统已执行此过操作",

    // js\device\delete-device.js
    inputVerificationCode: "请输入验证码",
    deleteDeviceSuccess: "删除设备成功",
    deleteDeviceFailed: "删除设备失败",
    verificationCodeError: "验证码不正确",

    // js\device\device-detail-edit.js
    selectZone: "选择区域",
    dutyCycle: "工作周期不能为空",
    inputError: "输入错误",
    numberInputError: "请输入正确的数字(整数或者小数)",
    saveFormulaSuccess: "保存公式系数成功",
    saveFormulaFailed: "保存公式系数失败",
    restore: "恢复默认",
    restoreFormulaSuccess: "恢复默认系数成功",
    restoreFormulaFailed: "恢复默认系数失败",
    restoreAllFormulaSuccess: "全部恢复默认系数成功",
    restoreAllFormulaFailed: "全部恢复默认系数失败",

    // js\2datepicker-form-validation.js
    startGreaterThanEnd: "开始时间不能大于结束时间",

    // js\controlpanel\control-module-actions.js
    selectChildNode: "请选择子节点",
    lowGreaterThanHigh: "低阈值不能大于高阈值",
    setConditionalReflection: "设置条件反射",
    calculate: "计算",
    calculateOriginalValue: "计算原始值",
    calculationError: "计算错误",
    setAction: "设置动作",
    editAction: "编辑动作",
    confirmChanges: "确认修改?",
    confirmDeletion: "确认删除?",
    deleteFailed: "删除失败",
    deleteSuccess: "删除成功",
    canNotAdd: "已经有了间隔执行动作，您不能再添加",
    setCondition: "设置条件",
    on: "开",
    off: "关",
    dailyRegularExecution: "每日定时执行",

    // js\zone\plan-image2.js
    selectLocation: "请先选中需要解除的位置点(点击选中)",
    selectDevice: "请先选中需要取消显示的设备(点击选中)",
    operationFailed: "操作失败,您刚才的更新未保存成功！",

    // js\threshold.js
    atLeastOne: "至少选择一种监测指标",
    selectSensor: "请先选择监测指标",
    pleaseSetThreshold: "请先设置阈值",
    sureToDeleteThreshold: "确定要删除所有设置阈值吗?",
    saveThenDelete: "请先保存数据，再删除！",
    deleteThresholdFailed: "删除失败，至少保留一条数据",
    notEmpty: "不能为空",
    notifierCannotEmpty: "通知人不能为空",
    selectMeasures: "请选择措施",
    alarmTypeCannotEmpty: "报警类型不能为空",
    conditionType: "条件类型",
    targetValue: "目标值",
    floatingValue: "浮动值",
    range: "范围",
    greaterThan: "大于",
    lessThan: "小于",
    greaterEqual: "大于等于",
    equalTo: "等于",
    setThresholdSuccess: "设置阈值成功",
    setThresholdFailed: "设置阈值失败",
    inputNumber: "请输入数字",

    // js\zone\scalar-field.js
    maximumGreaterThanMinimum: "最大值必须大于最小值",
    minimumLessThanMaximum: "最小值必须小于最大值",
    sampleValue: "采样点设备值",
    predictiveValue: "根据采样点数据预估值",
    stop: "停止",
    play: "播放",

    // js\zone\wind-field.js
    north: "北",

    // js\location\history-data.js
    selectStartTime: "请选择开始时间",
    selectEndTime: "请选择结束时间",

    // js\location\charts\basic-chart.js
    noOptionalSensor: "没有可选监测指标",
    locationExists: "该位置点已存在图表中",
    noComparison: "暂无对比",
    selectLocationForContrast: "请选择对比位置点",
    selectSensorForContrast: "请选择对比监测指标",
    restoringChart: "正在还原图表",
    basicCurveChart: "基础曲线图",
    curveChart: "曲线图",
    curve: "曲线",
    fluctuationRange: "浮动范围",
    tooManyCharts: "图表上线过多可能会导致页面异常",
    yAxisMinimumValue: "Y轴最小值不能小于-100,提示3秒后自动关闭",
    downCannotExceed: "下调不能超过原数据范围",

    // js\location\charts\windrose-chart.js
    frequency: "频率",
    windSpeed: "风速",
    staticWindRate: "静风率",
    averageWindSpeed: "平均风速",
    noWindRose: "该位置点无风向玫瑰图",

    // js/location/charts/rainfall-chart.js
    sumRainfall: "累计降雨量总和",

    // js\device\charts\light-chart.js
    dragDevice: "将设备拖入此处",
    sumLight: "累计光照总和",
    equipmentSelected: "该设备已选择",
    noLight: "该设备无累计光照图",

    // js\location\charts\average-compare.js
    meanComparisonCurve: "均值比较曲线图",

    // js\alarm.js
    addMeasure: "添加措施",
    editMeasure: "修改措施",
    inputMeasureDescription: "请输入措施描述",
    sureToDelete: "确定要删除吗?",

    // dataCenter\charts\js\dataCenter-charts.js
    // index\dc-list-slide.ftl
    inputLayoutName: "请输入布局名称",
    layoutNameEmpty: "请输入布局名称，布局名称不能为空",
    samplingTime: "采样时间",
    serverOutOfContact: "服务器失联,请检查网络连接",
    slideCannotBeDelete: "该幻灯片有子幻灯片无法删除，请先删除子幻灯片！",
    saveSuccess: "布局已保存成功",
    inputName: ":请输入名称",
    illegalCharacters: "非法字符，请重新输入！",

    // dataCenter\charts\pages\index\dc-add-program.ftl
    inputTitle: "请输入标题",
    upTo25Characters: "最多可输入25个字",
    inputRemark: "请输入备注",
    upTo200Characters: "最多可输入200个字",
    selectPicture: "请选择图片",

    // index\dc-list-slide.ftl
    sureToDeleteSubSlides: "确定要删除该幻灯片的所有子幻灯片吗?",
    pictureCannotBeEmpty: "上传图片不能为空",
    fileTypeLimit: "文件类型必须是gif、jpg、jpeg或png",

    // threedimensional\edit-dimensional.ftl
    // threedimensional\add-dimensional.ftl
    locationEmpty: "位置点不能为空",
    modelCannotBeEmpty: "添加模型不能为空",
    modelTypeLimit: "模型文件类型必须是obj,stl,vtk三维模型文件格式",
    modelSizeLimit: "模型大小不能超过200M",
    modelExists: "该模型已经存在",

    // statistics\report.ftl
    weeklyReport: "订阅周报表",

    // topo\loss-package.ftl
    lostRateStatistics: "丢包率统计",
    packetLostRate: "丢包率",
    estimated: "预计包数",
    actual: "实际包数",

    // topo\device-analyse.ftl
    deviceConnectLoads: "设备直连负载量",
    number: "个数",
    devicePoorSignal: "设备信号质量差",

    // topo\topo.ftl
    browserDoesNotSupport: "抱歉，当前浏览器不支持，推荐使用",
    signalIntensity: "信号强度",
    linkQuality: "链路质量",

    // location\charts\stock.ftl
    start: "开始",
    end: "结束",
    highest: "最高",
    lowest: "最低",
    M5: "5日均线",
    M10: "10日均线",
    M30: "30日均线",
    index: "监测指标指数",

    // location\charts\water-flow.ftl
    daytime: "号",
    selectTime: "请选择时间",
    evaporationCapacity: "蒸发量",

    // location\manage\add-location.ftl
    pleaseSelectDevice: "请选择设备",
    inputLocationName: "请输入位置点名称",
    locationNameExists: "位置点名称已存在",
    notProperLng: "经度应在-180到180度之间",
    notProperLat: "纬度应在-90到90度之间",
    uploaded: "上传成功",

    // location\manage\query-locations.ftl
    pleaseSelectZone: "请选择区域",
    clear: "清空",
    pleaseSelectLocationToBind: "请选择待绑定的位置点",
    deleteLocationSuccess: "删除位置点成功",
    deleteLocationFailed: "删除位置点失败",

    // location\index.ftl
    organicPollutant: "有机污染物",
    inorganicPollutants: "无机污染物",
    sulfurPollutant: "含硫污染物",

    // controlpanel\control-module-notification.ftl
    required: "必选",

    // controlpanel\index.ftl
    commandCompletedSuccess: "命令执行成功",
    commandExecutionFailed: "执行失败",
    commandSentSuccess: "命令发送成功",
    commandSentFailed: "命令发送失败",
    error: "错误",

    // device\manage\device-state.ftl
    chartCreateTime: "创建图标用时",
    voltage: "电压",

    // device\manage\device-detail-edit.ftl
    executeSuccess: "执行成功",
    executionFailed: "执行失败",
    humidityEmpty: "目标湿度不能为空",
    temperatureEmpty: "目标温度不能为空",
    setSuccess: "设置成功",
    setFailed: "设置失败",
    locationSuccess: "定位成功",
    locationFailed: "定位失败",
    lowerLimitGreaterThanUpperLimit: "下限不能大于或等于上限",
    zoomName:"暂无区域",
    patrolBeat: "发送失败",
    rebootSuccess: "重启成功",
    rebootFailed: "重启失败",

    //offline
    verification:"添加失败，所有输入框均为必须，且为数字类型。"
});