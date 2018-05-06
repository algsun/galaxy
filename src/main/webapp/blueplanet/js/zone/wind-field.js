/***
 * 风场 js
 *
 * @author: xu.baoji
 * @time: 2013.10.24
 * @check @gaohui 2013-11-06 6429
 */

$(function () {
    PATH_LENGTH = 40;   // 线段长度
    ARROW_LENGTH = 10;  // 箭头长度
    ARROW_ANGLE = 35;    // 箭头角度
    WIND_FIELD_CANVAS = "windFieldCanvas";

    intervalId = null;   // 全局变量
    paper.install(window);

    initWindFieldDataAndDrawCanvas();

    // 为 查询button 添加 click 事件
    $("button").click(function () {
        stopScalarFieldPlay();
        initWindFieldDataAndDrawCanvas();
    });
    (function () {
        // 为 向上 向下 绑定 事件
        var previousOrNex = function (isNext) {
            return function () {
                stopScalarFieldPlay();
                var nowDate = getUpdateDate(isNext);
                $("#date").val(nowDate);
                initWindFieldDataAndDrawCanvas();
            };
        };
        $("#up").click(previousOrNex(true));
        $("#next").click(previousOrNex(false));
    })();

    // 为播放 和停止 按钮添加 事件
    $("#play-stop").click(function () {
        var isPlay = $("#play-stop").attr("isPlay");
        changePlayOrStopElement(isPlay);
        if (isPlay == 1) {
            intervalId = startScalarFieldPlay();
        } else {
            stopScalarFieldPlay();
        }
    });

});

// ajax 获得标量场 数据  并 绘制标量场
function initWindFieldDataAndDrawCanvas() {
    // 判断浏览器是否 支持 canvas
    if (!isCanvasSupported()) {
        showNotSupportCanvas(); // 显示浏览器 不支持 元素
        return;
    }
    // 判断  快速向下 向上 按钮 是否可以点击
    isNextDisabled();
    isUpDisabled();
    // 判断是否上传 区域平面图
    if ($("#zone-Image").text() == "") {
        showNoImage();
        return;
    }
    // 获得参数 数据
    var zoneId = $("#zone-id").text();
    // 组织 获得 标量场数据 url
    var scalarFieldDataUrl = "zone/" + zoneId + "/wind-field-data.json";
    $.get(scalarFieldDataUrl, {date: $("#date").val()}, function (data) {
        if (data == null || data.length == 0) {
            // 无 数据
            showNoDateElement();
            $("#" + WIND_FIELD_CANVAS).remove();
        } else {
            // 判断 设备是否在 区域平面图上 部署
            var afterFilterData = filterData(data.data);
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
                    width: width,
                    height: height
                };
                // 清空 paper  中 缓存 数据
                paper.remove();
                //   创建 canvas
                createCanvas($("#paperjs"), width, height, WIND_FIELD_CANVAS);
                // 绘制 风场
                drawWindFiled(params);
            });
        }
    });
}

// 绘制 标量场
function drawWindFiled(params) {
    // 获得canvas 元素
    var canvas = document.getElementById(WIND_FIELD_CANVAS);
    paper.setup(canvas);
    for (var i = 0; i < params.data.length; i++) {
        var windFiledData = params.data[i];
        var windValue = new Number(windFiledData.data.sensorPhysicalValue);
        var startPoint = new Point(windFiledData.location.coordinateX, windFiledData.location.coordinateY);

        var style = {
            fillColor: "white",
            strokeColor: "black",
            strokeWidth: 1.5
        };
        var style1 = {
            fillColor: "white",
            strokeColor: "white",
            strokeWidth: 6
        }

        // 绘制 地图 指北针
        drawCompass(new Point(40, 60), 60);
        // 绘制 设备点
        if (windFiledData.data.sensorPhysicalid == 65) {
            var circle1 = new Path.Circle(startPoint, 4);
            circle1.style = style1;
            var circle = new Path.Circle(startPoint, 4);
            circle.style = style;
        } else if (windFiledData.data.sensorPhysicalid == 48) {
            var path1 = new Path.Rectangle(new Point(startPoint.x - 4, startPoint.y - 4), new Point(startPoint.x + 4, startPoint.y + 4));
            path1.style = style1;
            var path = new Path.Rectangle(new Point(startPoint.x - 4, startPoint.y - 4), new Point(startPoint.x + 4, startPoint.y + 4));
            path.style = style;
        }
        // 根据 采样点 数据绘制 箭头
        var endPoint = getEndPoint(startPoint, windValue, PATH_LENGTH);
        var path1 = new Path(startPoint, endPoint);
        path1.style = style1;
        var arrowPath1 = getArrowPath(endPoint, windValue, ARROW_ANGLE, ARROW_LENGTH)
        arrowPath1.style = style1;
        var arrowPath = getArrowPath(endPoint, windValue, ARROW_ANGLE, ARROW_LENGTH)
        arrowPath.style = style;
        var path = new Path(startPoint, endPoint);
        path.style = style;

    }
    paper.view.draw();
}

// 绘制 方向 指标 正上方 为 北方
function drawCompass(point, length) {
    var textN = new PointText(new Point(point.x, point.y - 0.5 * length));
    textN.content = message.north + '（N）';
    textN.style = {
        fontSize: 20,
        fillColor: 'red'
    };
    var arrowPath = getArrowPath(new Point(point.x, point.y - 0.5 * length), 0, 45, 15);
    var pathNS = new Path(new Point(point.x, point.y - 0.5 * length), new Point(point.x, point.y + 0.5 * length));
    var pathWE = new Path(new Point(point.x - 0.5 * length, point.y), new Point(point.x + 0.5 * length, point.y));
    new Group([arrowPath, pathNS, pathWE]).style = {
        fillColor: "red",
        strokeColor: "red",
        strokeWidth: 1.5
    }
}

// 获得 结束点
function getEndPoint(startPoint, angle, length) {
    return new Point(startPoint.x + Math.sin(angle * 2 * Math.PI / 360) * length, startPoint.y - Math.cos(angle * 2 * Math.PI / 360) * length);
}

// 获得箭头 path
function getArrowPath(endPoint, angle, arrowAngle, arrowLength) {
    var fistPoint = new Point(endPoint.x - Math.sin((angle + arrowAngle) * 2 * Math.PI / 360) * arrowLength, endPoint.y + Math.cos((angle + arrowAngle) * 2 * Math.PI / 360) * arrowLength);
    var lastPoint = new Point(endPoint.x + Math.sin((angle + 180 - arrowAngle) * 2 * Math.PI / 360) * arrowLength, endPoint.y - Math.cos((angle + 180 - arrowAngle) * 2 * Math.PI / 360) * arrowLength);
    return new Path(fistPoint, endPoint, lastPoint);
}

// 创建 canvas
function createCanvas(parent, width, height, canvasId) {
    $("#" + canvasId).remove();// 删除 之前元素 后创建新的元素
    var canvas = $("<canvas></canvas>");
    canvas.attr({id: canvasId, width: width, height: height, class: "scalarFieldFloat"});
    parent.append(canvas);
    return canvas;
}

// 过滤 数据
function filterData(scalarFileData) {
    var afterFilterData = [];
    if (scalarFileData) {
        for (var i = 0; i < scalarFileData.length; i++) {
            var locationX = scalarFileData[i].location.coordinateX;
            var locationY = scalarFileData[i].location.coordinateY;
            if (locationX != -1 && locationY != -1) {
                afterFilterData.push(scalarFileData[i]);
            }
        }
    }
    return afterFilterData;
}

// 计算 修改后的时间
function getUpdateDate(isUp) {
    var myDate = $("#date").val();  // 页面my97 控件 当前日期
    var minute = $("#minute").val(); //  要加减的分钟
    var maxDate = moment().format("YYYY-MM-DD HH:mm:00");  // 最大日期
    var minDate = moment("1970-01-01 00:00:00"); // 最小日期
    var updateDate;
    if (isUp) {
        updateDate = moment(myDate).subtract("m", minute).format("YYYY-MM-DD HH:mm:ss");   // 页面日期减去 minute后
    } else {
        updateDate = moment(myDate).add("m", minute).format("YYYY-MM-DD HH:mm:ss");   // 页面日期 加上 minute 后
    }
    if (maxDate <= updateDate) {
        updateDate = maxDate;
    }
    if (updateDate <= minDate) {
        updateDate = minDate;
    }
    return updateDate;
}

// 判断 快速向上 是否 可以点击
function isUpDisabled() {
    var myDate = $("#date").val();  // 页面my97 控件 当前日期
    var minDate = moment("1970-01-01 00:00:00"); // 最小日期
    if (myDate <= minDate) {   // 设置 向上不可点击
        $("#up").addClass("disabled");
    } else {
        $("#up").removeClass("disabled");
    }
}

// 判断 快速向下 和播放  是否可以 点击
function isNextDisabled() {
    var myDate = $("#date").val();  // 页面my97 控件 当前日期
    var maxDate = moment().format("YYYY-MM-DD HH:mm:00");  // 最大日期
    if (maxDate <= myDate) {   // 如果 最大 时间 小于 addDate 设置向下 不可 点击
        $("#next").addClass("disabled");
        $("#play-stop").addClass("disabled");
    } else {
        $("#next").removeClass("disabled");
        $("#play-stop").removeClass("disabled");
    }
}

// 停止 播放
function stopScalarFieldPlay() {
    changePlayOrStopElement(0);
    if (intervalId != null) {
        window.clearInterval(intervalId);
    }
}

// 播放
function startScalarFieldPlay() {
    var intervalId0 = window.setInterval(function () {
        var maxDate = moment().format("YYYY-MM-DD HH:mm:00");  // 最大日期
        var nowDate = getUpdateDate(false);
        if (maxDate <= nowDate) {  // 如果 最大时间小于等于 当前选择时间 结束播放
            changePlayOrStopElement(1);
            stopScalarFieldPlay(intervalId);
        }
        $("#date").val(nowDate);
        initWindFieldDataAndDrawCanvas();
    }, 2 * 1000)
    return intervalId0;
}

// 改变 播放 暂停 元素
function changePlayOrStopElement(isPlay) {
    if (isPlay == 1) {
        $("#play-stop").attr("isPlay", 0).attr("title", message.stop);
        $("#playOrStopP").text(message.stop);
        $("#playOrStopI").removeClass("icon-play").addClass("icon-stop");
    } else {
        $("#play-stop").attr("isPlay", 1).attr("title", message.play);
        $("#playOrStopP").text(message.play);
        $("#playOrStopI").removeClass("icon-stop").addClass("icon-play");
    }
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