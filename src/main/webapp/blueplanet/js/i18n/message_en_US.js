/**
 * Created by lijianfei on 16/2/5.
 */
$.extend(message, {
    // common
    tips: "tips",
    noData: "no data",
    ok: "ok",
    cancel: "cancel",
    default: "default",
    pleaseSelect: "Please select",
    sensor: "monitoring indicator",
    operation: "operation",
    delete: "delete",
    time: "time",
    show: "show",
    hide: "hide",
    loading: "loading",
    device: "equipment",
    location: "location",
    reset: "reset",
    zoomBy: "Zoom by 1:1",
    export: "export",
    subscribe: "subscribe",
    unsubscribe: "unsubscribe",
    subscribed: "subscribed",
    unit: "unit",
    node: "node",
    relay: "relay",
    masterModule: "master module",
    slaveModule: "slave module",
    controlModule: "control module",
    gateway: "gateway",
    all: "all",
    day: "day",
    week: "week",
    month: "month",
    year: "year",
    january: "January",
    february: "February",
    march: "March",
    april: "April",
    may: "May",
    june: "June",
    july: "July",
    august: "August",
    september: "September",
    october: "October",
    november: "November",
    december: "December",

    // realtime-data-util
    normal: "normal",
    lowVoltage: "low-voltage",
    powerDown: "power down",
    overtime: "overtime",
    unknown: "unknown",
    normalMode: "normal mode",
    inspection: "inspection pattern",
    nothing: "nothing",

    // site-zone-realtime-data
    noDevice: "There was no equipment",
    noSensor: "there is not the value of monitoring",

    // device-status
    deviceState: "The status of equipment",
    percent: "percent",

    // zone-stability
    regionalStability: "regional stability",
    stability: "stability",

    // day-light.js
    accumulatedLight: "accumulated light",

    // co2-concentration.js
    carbonDioxide: "CO₂",

    // temperature-avg.js
    dailyMeanTemperatureStatistics: "daily mean temperature statistics",
    dailyMeanTemperature: "daily mean temperature",
    areaDailyMeanTemperature: "daily mean temperature in area",

    // realtime-temperature.js
    realTimeTemperature: "Real time temperature",
    temperature: "temperature",

    // realtime-humidity.js
    realTimeHumidity: "Real time humidity",
    humidity: "humidity",

    // sensor-useInfo.js
    sensorCoverage: "Coverage rate of monitoring index",
    sensorCoverageNotes: "The number of times the monitoring index is used at the location point",
    times: "times",
    useTimes: "Use times",

    //zone-humidity.js
    humidityWaveRange: "Humidity fluctuation range",
    humidityWaveRangeNotes: "The maximum value, minimum value, average value of the area of the previous day",
    averageValue: "average value",

    // js\index.js
    maximumValue: "maximum value",
    minimumValue: "minimum value",

    // js/device/query-binding-devices.js
    actionDown: "the action had done in system",

    // js\device\delete-device.js
    inputVerificationCode: "please input verification code",
    deleteDeviceSuccess: "delete the equipment success",
    deleteDeviceFailed: "delete the equipment failed",
    verificationCodeError: "Verification code is not correct",

    // js\device\device-detail-edit.js
    selectZone: "Selection area",
    dutyCycle: "Duty cycle can not be empty",
    inputError: "Input error",
    numberInputError: "Please enter the correct number (integer or decimal)",
    saveFormulaSuccess: "Save the formula coefficient success",
    saveFormulaFailed: "failed to save the equation coefficients",
    restore: "restore default",
    restoreFormulaSuccess: "Restore default coefficients successfully",
    restoreFormulaFailed: "failed to restore the default coefficients",
    restoreAllFormulaSuccess: "Restore all default coefficients successfully",
    restoreAllFormulaFailed: "completely failed to restore the default coefficients",

    // js\2datepicker-form-validation.js
    startGreaterThanEnd: "Start time can not be greater than end time",

    // js\controlpanel\control-module-actions.js
    selectChildNode: "Please select a child node",
    lowGreaterThanHigh: "Low threshold can not be greater than high threshold",
    setConditionalReflection: "Set conditional reflection",
    calculate: "calculate",
    calculateOriginalValue: "Calculate the original value",
    calculationError: "Calculation error",
    setAction: "Set action",
    editAction: "Edit action",
    confirmChanges: "Confirm changes?",
    confirmDeletion: "Confirm deletion?",
    deleteFailed: "Delete failed",
    deleteSuccess: "Delete Success",
    canNotAdd: "There is an interval to perform the action, you can not add",
    setCondition: "Set conditions",
    on: "on",
    off: "off",
    dailyRegularExecution: "Daily regular execution",

    // js\zone\plan-image2.js
    selectLocation: "Please select the location you need to unbind (click on)",
    selectDevice: "Please select the device you need to cancel (click on)",
    operationFailed: "The operation failed, and you just failed to save the update successfully.",

    // js\threshold.js
    atLeastOne: "At least one monitoring indicator",
    selectSensor: "Please select the monitoring indicators",
    pleaseSetThreshold: "Please set the threshold",
    sureToDeleteThreshold: "Are you sure you want to delete all the threshold?",
    saveThenDelete: "Please save the data, and then delete!",
    deleteThresholdFailed: "Delete failed, at least keep a data",
    notEmpty: "Can not be empty",
    notifierCannotEmpty: "notifier the person can not be empty",
    selectMeasures: "Please select measures",
    alarmTypeCannotEmpty: "Alarm type can not be empty",
    conditionType: "condition type",
    targetValue: "target value",
    floatingValue: "floating value",
    range: "range",
    greaterThan: "greater than",
    lessThan: "less than",
    greaterEqual: "greater than or equal to",
    equalTo: "equal to",
    setThresholdSuccess: "Set the threshold successfully",
    setThresholdFailed: "Failed to set the threshold",
    inputNumber: "Please enter the Numbers",

    // js\zone\scalar-field.js
    maximumGreaterThanMinimum: "the maximum value must be greater than the minimum value",
    minimumLessThanMaximum: "the minimum value must be less than the maximum value",
    sampleValue: "sampling site equipment value",
    predictiveValue: "predictive value according to sampling site data",
    stop: "stop",
    play: "play",

    // js\zone\wind-field.js
    north: "North",

    // js\location\history-data.js
    selectStartTime: "please select start time",
    selectEndTime: "please select finish time",

    // js\location\charts\basic-chart.js
    noOptionalSensor: "No optional monitoring indicators",
    locationExists: "this location point had in graph",
    noComparison: "No comparison",
    selectLocationForContrast: "please choose a location for contrast",
    selectSensorForContrast: "please choose monitoring index for contrast",
    restoringChart: "Restoring chart",
    basicCurveChart: "basic curve chart",
    curveChart: "curve chart",
    curve: "curve",
    fluctuationRange: "fluctuation range",
    tooManyCharts: "too many charts on the line may lead to abnormal page",
    yAxisMinimumValue: "Y axis minimum value can not be less than -100, tip 3 seconds, then shut down automatically",
    downCannotExceed: "down can not exceed the range of the original data",

    // js\location\charts\windrose-chart.js
    frequency: "frequency",
    windSpeed: "wind speed",
    staticWindRate: "static wind rate",
    averageWindSpeed: "average wind speed",
    noWindRose: "The location where is no wind rose",

    // js/location/charts/rainfall-chart.js
    sumRainfall: "Cumulative rainfall sum",

    // js\device\charts\light-chart.js
    dragDevice: "drag the equipment here",
    sumLight: "Cumulative sum of light",
    equipmentSelected: "The equipment has been selected",
    noLight: "The location where is no light chart",

    // js\location\charts\average-compare.js
    meanComparisonCurve: "mean comparison curve",

    // js\alarm.js
    addMeasure: "add measure",
    editMeasure: "edit measure",
    inputMeasureDescription: "Please enter a description of the measure",
    sureToDelete: "Are you sure you want to delete it?",

    // dataCenter\charts\js\dataCenter-charts.js
    // index\dc-list-slide.ftl
    inputLayoutName: "Please enter layout name",
    layoutNameEmpty: "Enter the layout name, the layout name cannot be empty.",
    samplingTime: "sampling time",
    serverOutOfContact: "The server is out of contact, please check your network connection",
    slideCannotBeDelete: "The slide has a child slides cannot be deleted, please delete child slides!",
    saveSuccess: "The layout has been saved successfully",
    inputName: "Please enter a name",
    illegalCharacters: "Illegal characters. Please input again!",

    // dataCenter\charts\pages\index\dc-add-program.ftl
    inputTitle: "Please enter a title",
    upTo25Characters: "You can enter up to 25 characters",
    inputRemark: "Please enter a note",
    upTo200Characters: "You can enter up to 200 characters",
    selectPicture: "Please select a picture",

    // index\dc-list-slide.ftl
    sureToDeleteSubSlides: "Are you sure you want to delete all the sub slides of the slide?",
    pictureCannotBeEmpty: "Upload pictures can not be empty",
    fileTypeLimit: "File types must be GIF, JPG, JPEG, or png",

    // threedimensional\edit-dimensional.ftl
    // threedimensional\add-dimensional.ftl
    locationEmpty: "location can not be empty",
    modelCannotBeEmpty: "Add model cannot be empty",
    modelTypeLimit: "model file type must be obj, STL, VTK 3D model file format",
    modelSizeLimit: "Model size can not exceed 200M",
    modelExists: "The model already exists",

    // statistics\report.ftl
    weeklyReport: "Weekly report",

    // topo\loss-package.ftl
    lostRateStatistics: "Packet loss rate statistics",
    packetLostRate: "packet lost rate",
    estimated: "Estimated number of packages",
    actual: "Actual packet number",

    // topo\device-analyse.ftl
    deviceConnectLoads: "Equipment directly connected loads",
    number: "number",
    devicePoorSignal: "Poor signal quality of equipment",

    // topo\topo.ftl
    browserDoesNotSupport: "Sorry, the current browser does not support, recommend the use of ",
    signalIntensity: "signal intensity",
    linkQuality: "link quality",

    // location\charts\stock.ftl
    start: "start",
    end: "end",
    highest: "highest",
    lowest: "lowest",
    M5: "M5",
    M10: "M10",
    M30: "M30",
    index: "Index of monitoring index",

    // location\charts\water-flow.ftl
    daytime: "",
    selectTime: "Please select the time before the query",
    evaporationCapacity: "evaporation capacity",

    // location\manage\add-location.ftl
    pleaseSelectDevice: "Please select the device",
    inputLocationName: "please enter the name of location",
    notProperLng: "Longitude should between -180 and 180",
    notProperLat: "Dimension should between -90 and 90",
    locationNameExists: "the name of the location has already existed",
    uploaded: "uploaded",

    // location\manage\query-locations.ftl
    pleaseSelectZone: "please select the area",
    clear: "clear",
    pleaseSelectLocationToBind: "Please select the location point to be bound.",
    deleteLocationSuccess: "delete the location success",
    deleteLocationFailed: "delete the location failed",

    // location\index.ftl
    organicPollutant: "organic pollutant",
    inorganicPollutants: "inorganic pollutants",
    sulfurPollutant: "sulfur pollutant",

    // controlpanel\control-module-notification.ftl
    required: "required",

    // controlpanel\index.ftl
    commandCompletedSuccess: "The command completed successfully",
    commandExecutionFailed: "execution failed",
    commandSentSuccess: "Commands sent successfully",
    commandSentFailed: "Commands sent failed",
    error: "error",

    // device\manage\device-state.ftl
    chartCreateTime: "The time of creating a chart ",
    voltage: "voltage",

    // device\manage\device-detail-edit.ftl
    executeSuccess: "Execute successfully",
    executionFailed: "Execution failed",
    humidityEmpty: "Target humidity can not be empty",
    temperatureEmpty: "Target temperature can not be empty",
    setSuccess: "Set successfully",
    setFailed: "Setting failure",
    locationSuccess: "Successful location",
    locationFailed: "Location failure",
    zoomName:"No region",
    //patrol_device_warning.ftl
    patrolBeat: "Execution failed",
    rebootSuccess: "Successful reboot",
    rebootFailed: "Reboot failure"
});