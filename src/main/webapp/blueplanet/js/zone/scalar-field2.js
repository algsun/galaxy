/***
 *
 * 标量场 js
 *
 * @author: xu.yuexi
 * @time: 2014.10.22
 */

$(function () {
    SIDE_LENGTH = 10;// 小区域 边长
    xyValues = [];
    intervalId = null;   // 全局变量

    paper.install(window);

    //  加载默认 标量场
    initScalarFieldDataAndDrawCanvas();

});

// ajax 获得标量场 数据  并 绘制标量场
function initScalarFieldDataAndDrawCanvas() {
    // 判断浏览器是否 支持 canvas
    if (!isCanvasSupported()) {
        showNotSupportCanvas(); // 显示浏览器 不支持 元素
        return;
    }

    // 判断是否上传 区域平面图
    if (!$("#zone-Image").text()) {
        showNoImage();
        return;
    }
    // 获得参数 数据
    var zoneId = $("#zone-id").text();
    var sensorPhysicalId = $("#sensorPhysicalId").val();
    // 组织 获得 标量场数据 url
    var scalarFieldCanvasId = "scalarFieldCanvas";
    var date = $("#date").val();
    var scalarFieldDataUrl = "scalar-field2.json";
    $.get(scalarFieldDataUrl, {date: date, sensorPhysicalId: sensorPhysicalId, zoneId: zoneId}, function (paramData) {
            var data = paramData.data;
            var sensorinfo = paramData.sensorinfo;
            if (data == null || data.length == 0) {
                // 无 数据
                showNoDateElement();
                $("#" + scalarFieldCanvasId).remove();
            } else {
                // 判断 设备是否在 区域平面图上 部署
                var afterFilterData = filterData(data);
                if (afterFilterData.length == 0) {
                    showImageNoDevice();
                    return;
                }
                // 显示区域平面图
                showHasDataElement();
                // 获得页面与 绘制 标量场有关的参数
                window.imageNaturalSize($("#planImage").attr("src"), function (width, height) {
                        var params = {
                            data: afterFilterData,
                            maxHue: 360,
                            minHue: 200,
                            opacity: 0.6,
                            width: width,
                            height: height,
                            scalarFieldCanvasId: scalarFieldCanvasId,
                            units: sensorinfo.units,
                            sensorPrecision: sensorinfo.sensorPrecision,
                            maxValue: getMaxValue(afterFilterData),
                            minValue: getMinValue(afterFilterData)
                        };
                        // 清空 paper  中 缓存 数据
                        paper.remove();
                        xyValues = [];
                        //  设置 hasData div 宽度
                        $("#hasData").width(width + 100);
                        //   创建 canvas
                        createCanvas($("#paperjs"), width, height, params.scalarFieldCanvasId);
                        // 给 canvas 添加鼠标 移动和 离开 事件
                        addMouseEventToCanvas(params.scalarFieldCanvasId, width, height);
                        // 绘制 value 值和 hue 对应关系
                        drawMappingCanvas(height, params);
                        // 绘制 标量场
                        drawScalarFiled(params);
                    }
                )
                ;
            }
        }
    )
    ;
}

// 绘制 value 值 和hue 色彩 对应 canvas
function drawMappingCanvas(height, params) {
    // 获得 canvas
    var $canvas = $("#valueHueMapping");
    $canvas.attr("height", height - 40);  // 设置 高度
    // 设置 最大值和最小值
    $("#maxValue").text(params.maxValue + params.units);
    $("#minValue").text(params.minValue + params.units);
    paper.setup($canvas[0]);
    var topLeft = [0, 0];
    var bottomRight = {width: 20, height: params.height - 40};
    var path = new Path.Rectangle({
        topLeft: topLeft,
        bottomRight: bottomRight,
        fillColor: {
            gradient: {
                stops: [new Color({hue: params.maxHue, saturation: 1, brightness: 1}), new Color({
                    hue: params.minHue,
                    saturation: 1,
                    brightness: 1
                })]
            },
            origin: topLeft,
            destination: bottomRight
        }
    });
    path.opacity = params.opacity;
    paper.view.draw();
}

// 绘制 标量场
function drawScalarFiled(params) {
    // 获得canvas 元素
    var canvas = document.getElementById(params.scalarFieldCanvasId);
    paper.setup(canvas);
    // 根据 区域平面图  按 n 像素为一个小区域划分 进行着色  （x,y）代表 点 在平面图位置
    for (var x = 0; x < params.width; x = x + SIDE_LENGTH) {
        for (var y = 0; y < params.height; y = y + SIDE_LENGTH) {
            var isDevice = true;
            // 判断 小区域中是否 包含设备
            var value = getDeviceValue(x, y, params.data, SIDE_LENGTH);
            if (value == null) {
                isDevice = false;
                //计算当前点  数据值
                value = getScalarFiledValue(params.data, x, y, params.sensorPrecision);
            }
            // 将 value 值 对应 xy 进行 存储
            xyValues.push({
                x: x,
                y: y,
                isDevice: isDevice,
                value: value + params.units
            });

            // 计算当前点 代表的颜色, 默认为白色  t
            var fillColor = "white";

            //TODO  简化 if
            if (!((value - params.maxValue) > 0 || (value - params.minValue) < 0)) {
                // get方法 优化参数
                fillColor = new Color({ hue: getScalarFiledHue(value, params), saturation: 1, brightness: 1 });
            }
            // 绘制 小区域
            var path = new Path.Rectangle([x, y], [SIDE_LENGTH, SIDE_LENGTH]);
            path.fillColor = fillColor;
            path.selected = false;
            path.opacity = params.opacity;
            if (isDevice) {
                path.strokeColor = 'white';
            }
        }
    }
    paper.view.draw();
}

// 创建 canvas
function createCanvas(parent, width, height, canvasId) {
    $("#" + canvasId).remove();// 删除 之前元素 后创建新的元素
    var canvas = $("<canvas></canvas>");
    canvas.attr({id: canvasId, width: width, height: height, class: "scalarFieldFloat"});
    parent.append(canvas);
    return canvas;
}

// 为canvas 添加鼠标 移动和 离开事件
function addMouseEventToCanvas(canvasId, width, height) {
    // 为 scalarFieldCanvas 添加 鼠标移动事件
    var tool = new Tool(canvasId);
    tool.onMouseMove = function (event) {
        // 获得 鼠标 指针 针对  canvas 的 xy
        var offsetX = event.point.x;
        var offsetY = event.point.y;
        var xyValue = getXYValue(offsetX, offsetY, SIDE_LENGTH, xyValues);
        // 获得 鼠标 指针针对  页面 的 xy
        var pageX = event.event.pageX;
        var pageY = event.event.pageY;
        if (offsetX >= 0 && offsetX <= width && offsetY >= 0 && offsetY <= height) {
            Tooltip.close();
            var text;
            if (xyValue.isDevice) {
                text = "采样点设备值：" + xyValue.value;
            } else {
                text = "根据采样点数据预估值：" + xyValue.value;
            }
            Tooltip.show(pageX, pageY, null, "<i>" + text + "</i>");
        }
    };
    $("#" + canvasId).mouseleave(function (event) {
        Tooltip.close();
    });
}

// 判断 xy点 小区域范围内是否有设备 如果找到包含的设备将设备 value 值返回，如果没有 返回null
function getDeviceValue(x, y, scalarFileData, side_length) {
    for (var i = 0; i < scalarFileData.length; i++) {
        var value = scalarFileData[i].data.sensorPhysicalValue;
        var deviceX = scalarFileData[i].device.coordinateX;
        var deviceY = scalarFileData[i].device.coordinateY;
        // 如果 这个点和设备点 x，y 距离 同时小于 矩形变成，
        if (x <= deviceX && deviceX < (x + side_length) && y <= deviceY && deviceY < y + side_length) {
            return value;
        }
    }
    return null;
}

// 过滤 数据
function filterData(scalarFileData) {
    var afterFilterData = new Array();
    for (var i = 0; i < scalarFileData.length; i++) {
        var deviceX = scalarFileData[i].location.coordinateX;
        var deviceY = scalarFileData[i].location.coordinateY;
        if (deviceX != -1 && deviceY != -1) {
            afterFilterData.push(scalarFileData[i]);
        }
    }
    return afterFilterData;
}

// 计算 标量值 对应的颜色
function getScalarFiledHue(value, params) {
    // 如果最大值和最小值相等，说明所有点温度相等
    if (params.maxValue == params.minValue) {
        return params.maxHue;
    }
    var hue = params.maxHue - ((params.maxHue - params.minHue) / (params.maxValue - params.minValue)) * (params.maxValue - value);
    return hue.toFixed(2);
}

// 计算 最大 值
function getMaxValue(scalarFileData) {
    var maxValue = $("#maxValueInput").val();
    if (maxValue != null && maxValue != "") {
        return maxValue;
    }
    // 假设 第一个 为 最大value
    maxValue = scalarFileData[0].data.sensorPhysicalValue;
    for (var i = 1; i < scalarFileData.length; i++) {
        var value = scalarFileData[i].data.sensorPhysicalValue;
        if (parseFloat(maxValue) < parseFloat(value)) {
            maxValue = value;
        }
    }
    //$("#maxValueInput").val(maxValue);
    return maxValue;
}
// 计算最小值
function getMinValue(scalarFileData) {
    var minValue = $("#minValueInput").val();
    if (minValue != null && minValue != "") {
        return minValue;
    }
    // 假设 第一个为最小 value 值
    minValue = scalarFileData[0].data.sensorPhysicalValue;
    for (var i = 1; i < scalarFileData.length; i++) {
        var value = scalarFileData[i].data.sensorPhysicalValue;
        if (parseFloat(minValue) > parseFloat(value)) {
            minValue = value;
        }
    }
    // $("#minValueInput").val(minValue);
    return minValue;
}

// 计算 x，y 点 代表的 值
function getScalarFiledValue(scalarFiledData, x, y, positions) {
    // 权值和
    var wSum = 0;
    // 权值 与实际值成绩 和
    var wvSum = 0;
    for (var i = 0; i < scalarFiledData.length; i++) {
        // 获得 实际监测点 数据
        var deviceData = scalarFiledData[i];
        var value = deviceData.data.sensorPhysicalValue;
        var deviceX = deviceData.device.coordinateX;
        var deviceY = deviceData.device.coordinateY;
        // 计算实际点与 （x,y）点之间的实际距离
        // var d = Math.pow(((x - deviceX) * (x - deviceX) + (y - deviceY) * (y - deviceY)), 0.5);
        // 计算实际点 与（x,y）点之间的权值
        var w = Math.pow(((x - deviceX) * (x - deviceX) + (y - deviceY) * (y - deviceY)), -1);
        // 累加权值和
        wSum = wSum + w;
        // 计算权值 * 实际数据  并累加
        wvSum = wvSum + w * value;
    }
    return (wvSum / wSum).toFixed(positions);
}

// 根据 xy 坐标点 从 xyValues全局数组变量中 匹配 对应的 value 值（带有单位）
function getXYValue(x, y, side_length, xyValues) {
    for (var i = 0; i < xyValues.length; i++) {
        var xyValue = xyValues[i];
        if (xyValue.x + side_length >= x && x >= xyValue.x && xyValue.y + side_length >= y && y >= xyValue.y) {
            return xyValue;
        }
    }
    return null;
}

// 显示 浏览器 不支持 canvas
function showNotSupportCanvas() {
    hideAllDataElement();
    $("#not_support_canvas").show();
}
// 显示无数据 元素
function showNoDateElement() {
    hideAllDataElement();
    $("#noData").show();
}

// 显示区域平面图
function showHasDataElement() {
    hideAllDataElement();
    $("#hasData").show();
}
// 显示 noImage
function showNoImage() {
    hideAllDataElement();
    $("#noImage").show();
}

// 显示 imageNoDevice
function showImageNoDevice() {
    hideAllDataElement();
    $("#imageNoDevice").show();
}

// 隐藏 所有 页面 显示 元素
function hideAllDataElement() {
    $("#hasData").hide();
    $("#noData").hide();
    $("#noImage").hide();
    $("#imageNoDevice").hide();
    $("#not_support_canvas").hide();
}





