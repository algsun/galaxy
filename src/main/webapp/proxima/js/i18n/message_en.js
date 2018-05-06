/**
 * Created by lijianfei on 16/2/5.
 */
$.extend(message, {
    // common
    tips: "tips",
    noData: "no data",
    ok: "ok",
    cancel: "cancel",
    loading: "loading",
    sureToDelete: "Are you sure you want to delete it?",
    save: "save",
    saveSuccess: "save successful",
    saveFailed: "save failed",
    cannotBeEmpty: "cannot be empty",
    loadFailed: "load failed",
    all: "all",
    reset: "reset",
    zoomBy: "Zoom by 1:1",
    export: "export",
    stop: "stop",
    play: "play",

    // opticsImage\index.ftl
    cameraJCV: "JCV by camera",
    uploadJCV: "upload JCV",
    fileTypeLimit: "File types must be GIF, JPG, JPEG, or png",

    // editMarkRegion.js
    addMarkRegion: "add marker area",
    nameRepetition: "name repetition",
    dataProcessing: "data is processing,please waiting",
    temperatureInformation: "Temperature information",
    cannotBeResolved: "picture can not be resolved",
    temperatureInCurrentPosition: "temperature in current position",
    maximumTemperature: "regional maximum temperature",
    averageTemperature: "regional average temperature",
    minimumTemperature: "regional minimum temperature",

    // infraredChart\trendChart.js
    yAxisMaximumValue: "The maximum value of the Y axis can not be greater than 400, prompted 3 seconds after the automatic shutdown",
    yAxisMinimumValue: "Y axis minimum value can not be less than -100, tip 3 seconds, then shut down automatically",
    downCannotExceed: "down can not exceed the range of the original data",
    trendChart: "trend chart of temperature changed",

    // opticsChart/trendChart.js
    crackLength: "Crack length",
    variationRange: "Variation range",
    crackPosition: "Change chart of crack position",

    // markChangeChart.js
    markChangeChart: "Change chart of sign position",

    // list-dv-place.js
    JCVWidth: "The real picture width",
    pleaseInput: "Please input",
    positiveInteger: "Please input a positive integer",
    saveJCVWidthSuccess: "The real picture width saved successfully",
    saveJCVWidthFailed: "The real picture width saved failed",

    // dvPlace.js
    selectZone: "select the area",
    pleaseSelectZone: "Please select the area",
    cameraPositionRepetition: "The name of camera position is repetition",
    positionNameRequired: "positional name is required",
    pleaseSelectFTP: "Please choose a Ftp host",
    initFtp: "Firstly,please to the FTP management to initialize the FTP host",
    cameraIpRequired: "Camera IP is required",
    cameraIPFormatError: "Format of camera IP is error",
    cameraPortRequired: "Camera port is required",
    cameraPortError: "Camera port format is not correct",
    IOIPRequired: "The ip of IO module is required",
    IOIPFormatError: "The ip of IO module is error",
    IOPortRequired: "The port of IO module is required",
    IOPortError: "The port of IO module is error",
    timePointLimit: "Can only add up to 12 time points",
    startTimeRequired: "Starting time is required",
    endTimeLessThanStartTime: "The end time cannot be less than or equal to the starting time",
    endTimeRequired: "Over time is required",
    dailyCycleRequired: "Daily cycle parameter values can not be empty",
    dailyTimePointRequired: "Daily time point parameter values can not be empty",
    sevenDayPeriodRequired: "7 day period parameter values can not be empty",
    sevenDayTimePointRequired: "7 day time point parameter value can not be empty",
    sureToOperate: "Are you sure you are performing this operation?",

    // ftp-form.js
    ftpIpError: "Please fill in correct IP address",
    ftpPortError: "Please fill in  correct port(0-65535)",
    nameRequired: "Name is required",
    hostRequired: "Host is required",
    portRequired: "Port is required",
    userNameRequired: "User name is required",
    passwordRequired: "Password is required",
    connectTestSuccess: "Connection test is successful",
});