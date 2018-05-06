//拖动事件
$(function () {
    //获取浏览器可见宽度
    var clientW = document.documentElement.clientWidth;
    //获取当前屏幕高度
    var clientH = window.screen.availHeight;
    var itemW = (clientW - 12 * 2 * 10) / 12;
    var itemH = (clientH - 100) / 6 - 8;
    var gridster = $(".gridster ul").gridster({
        widget_base_dimensions: [itemW, itemH],//116
        widget_margins: [10, 5],
        resize: {
            enabled: true,
            stop: function (e, ui, $widget) {
                var id = $widget.data("id");
                if (id != null) {
                    var divId = "#chart-" + id;
                    var itemType = $(divId).attr("itemtype");
                    //缩放的时候控制字体大小
                    setFontSize(id);
                }
            }
        }
    }).data('gridster');
    var operMark = $("#operMark").val();
    if (!(operMark == 'preview' || operMark == 'edit' || operMark == 'shortcut')) {
        art.dialog({
            id: "saveLayoutName",
            fixed: true,
            title: message.inputLayoutName,
            content: "<div><input type='text' name='layoutName' id='layoutNm'/></div><div><span style='color: red' id='layoutNameSpan'></span></div><div>*" + message.layoutNameEmpty + "</div>",
            okValue: message.ok,
            esc: true,
            lock: true,
            ok: function () {
                var $layout = $("#layoutNm");
                var $span = $("#layoutNameSpan");
                if (verificationLayoutName($layout, $span)) {
                    var uuid = $("#uuid").val();
                    var layoutName = $layout.val();
                    $.post("dataCenter/charts/addLayout/" + uuid, {layoutDescription: layoutName});
                    $("#layoutDescription").text(layoutName);
                    $("#layoutName").val(layoutName);
//                    $('#insert').removeAttr("disabled");
                } else {
                    return false;
                }
            },
            cancelValue: message.cancel,
            cancel: function () {
                window.location.href = "dataCenter/charts/listLayout";
            }
        });
    }
});

/**
 * 设置dom对象字体大小
 *
 * @param id 当前layoutId
 * @param titleSize 标题大小
 * @param detailSize 描述大小
 * @param detailLineHeight 描述行高
 * @param sensorValueSize 监测指标值大小
 * @param sensorCnNameSize 监测指标名称大小
 * @param sensorUnitsSize 监测指标单位大小
 */
function setDomObjectValue(id, titleSize, detailSize, detailLineHeight, sensorValueSize, sensorCnNameSize, sensorUnitsSize) {
    $("#title" + id).css({"fontSize": titleSize});
    $("#detail" + id).css({"fontSize": detailSize, "line-height": detailLineHeight});
    $("#sensor" + id).find(".sensorValue").css({"fontSize": sensorValueSize});
    $("#sensor" + id).find(".sensorCnName").css({"fontSize": sensorCnNameSize});
    $("#sensor" + id).find(".sensorUnits").css({"fontSize": sensorUnitsSize});
}

function setFontSize(id) {
    var clientW = document.documentElement.clientWidth;
//    setDomObjectValue(id, "35px", "25px", "25px", "20px", "17px", "17px");
    var detailSize = (clientW / 58).toFixed(0);
    var detailLineHeight = ((clientW / 58) + 5).toFixed(0);
    var sensorValueSize = (clientW / 58).toFixed(0);
    var sensorCnSize = (clientW / 96).toFixed(0);
    setDomObjectValue(id, "35px", detailSize + "px", detailLineHeight + "px", sensorValueSize + "px", sensorCnSize + "px", sensorCnSize + "px");

    //设置监测指标值、符号的行高
    if ($("#sensor" + id).find(".sensorCnNameUnits").length != 0) {
        var sensorCnNameHeight = $("#sensor" + id).find(".sensorCnNameUnits")[0].clientHeight;
        var sensorValueHeight = $("#sensor" + id).find(".sensorValueDiv")[0].clientHeight;
        // 2.5 1.5 经过反复测试得到的系数,并不是胡乱写的
        var CnNameHeight = sensorCnNameHeight / 2.8;
        var valueHeight = sensorValueHeight * 0.8;
        $("#sensor" + id).find(".sensorCnNameUnits").css({"line-height": CnNameHeight + "px"});
        $("#sensor" + id).find(".sensorValueDiv").css({"line-height": valueHeight + "px"});
    }

    $("#chart-" + id).find(".divContent").css({"height": "50%"});//54
}

//调用
$(function () {
    //页面加载完后执行js代码
    $("li[itemtype='1']").each(function () {
        var $slide = $(this);
        var layoutId = $slide.data("id");
        setFontSize(layoutId);

        //删除gridster中的自带元素，使控件不能自由拖动大小
        var spans = $slide.find("span");
        $.each(spans, function (index, span) {
            span.remove();
        });

    });
});

/*
 * ajax获取幻灯片数据
 * 参数一:幻灯片 片子id
 * 参数二:幻灯片divId
 * 参数三:index下标
 * 参数四:所有幻灯片集合
 */
function ajaxSetData(slideId, itemId, index, slideList) {
    var refresh = 1000 * 10;
    $.ajax({
        url: "dateCenter/refreshSlideShow/" + slideId,
        type: "get",
        success: function (result) {
            refresh = result.slideShow.refresh * 1000;
            //将数据set到dom元素
            $("#slideImage" + itemId).attr("src", result.slideShow.url);
            $("#title" + itemId).html(result.slideShow.title);
            $("#detail" + itemId).html(result.slideShow.detail);
            var stampTime = message.samplingTime + ":" + result.stampTime;
            $("#stamptime").html(stampTime);
            // for 循环遍历监测指标值,此处用到underScore前端js模板
            var slideData = [];
            for (var i = 0; i < result.nodeSensor.length; i++) {
                var nodeSensor = result.nodeSensor[i];
                var nodeSensorValue = "--";
                if (nodeSensor.state == 1) {
                    nodeSensorValue = nodeSensor.sensorPhysicalValue
                }
                var data = {
                    sensorValue: nodeSensorValue,
                    sensorImg: nodeSensor.sensorPhysicalId,
                    sensorCnName: nodeSensor.sensorPhysicalCnName,
                    sensorUnits: nodeSensor.units
                }
                slideData.push(data);
            }
            $("#sensor" + itemId).html(_.template($('#template').html(), {slideData: slideData}));
            setFontSize(itemId);
        },
        error: function () {
            message.serverOutOfContact;
        }
    });
    //获取每张幻灯片刷新的时间，单位：秒,setTimeout 是以毫秒最单位的，+0转化为秒单位

    setTimeout(function () { //唤醒一个幻灯片
        index = (index + 1) % slideList.length;
        ajaxSetData(slideList[index], itemId, index, slideList);
    }, refresh);
}

//初始化页面（编辑，预览）
var gridster;
$(function () {
    var screenHeight = window.screen.height;
    $("#container").height(screenHeight);
    gridster = $(".gridster > ul").gridster({
        widget_margins: [13, 10],
        widget_base_dimensions: [140, 140],
        min_cols: 6,
        resize: {
            enabled: true
        }
    }).data('gridster');
    var layoutId = $("#uuid").val();

    $.get('dataCenter/slideShow/' + layoutId, function (slides) {
        for (var itemId in slides) {
            ajaxSetData(slides[itemId][0], itemId, 0, slides[itemId]);
        }
    });
});

var weigits = $(".new.gs-w").length;
if (weigits > 0) {
    $("#program").attr("disabled", true);
} else if (weigits == 0) {
    $("#program").attr("disabled", false);
}
$('#program').click(function () {
    var weigits = $(".new.gs-w").length;
    if (weigits > 0) {
        $("#program").attr("disabled", true);
        return false;
    }
    var layoutId = $("#uuid").val();
    var gridster = $(".gridster ul").gridster().data('gridster');
    var dateTime = Date.parse(new Date()) + Math.ceil(Math.random() * (100 - 1) + 1);
    gridster.add_widget('<li class="new" id="chart-' + dateTime + '" name="' + dateTime + '"itemType=1 data-id="' + dateTime + '"><input  type="hidden" id="params" class="' + dateTime + '" ><i class="icon-remove" style="float: right" onclick="deleteDiv(' + dateTime + ');"></i><i class="icon-pencil" style="float: right;margin-right: 15px;" onclick="showSlideList(' + layoutId + ',' + dateTime + ')"></i><div style="height: 90%" id="' + dateTime + '"></div> </li>', 12, 6);
    var $items = $("#chart-" + dateTime);
    var jsonStr = getJsonString($items);
    $.post("dataCenter/charts/index/slide/" + layoutId + "/saveSlide.json", {jsonStr: jsonStr});

    location.href = "dataCenter/charts/index/slide/toSlideList/" + layoutId + "/" + dateTime;
    return false;
});

function showSlideList(uuid, layoutId) {
    location.href = "dataCenter/charts/index/slide/toSlideList/" + uuid + "/" + layoutId;
}

//删除div
function deleteDiv(thisDiv) {
    var weigits = $(".new.gs-w").length;
    if (weigits > 0) {
        $("#program").attr("disabled", true);
    } else if (weigits == 0) {
        $("#program").attr("disabled", false);
    }
    art.dialog({
        id: "delete",
        fixed: true,
        title: message.tips,
        content: message.sureToDelete,
        okValue: message.ok,
        ok: function () {
            var buttonId = "#" + thisDiv;
            var cleanInt = $(buttonId).parent().attr("data-fetchVar");
            //如果该div有绑定事件，那么清除事件
            if (cleanInt != null) {
                clearInterval(cleanInt);
            }
            gridster.remove_widget($(buttonId).parent());
            $.post("dataCenter/charts/" + thisDiv + "/deleteItem.json");
        },
        cancelValue: message.cancel,
        cancel: function () {
        }
    });
    return false;
}

//删除div
function deleteSlideDiv(layoutId, thisDiv) {
    $.post("dataCenter/charts/index/slide/hasChildSlide.json", {uuid: layoutId, itemId: thisDiv}, function (result) {
        if (result) {

            art.dialog({
                id: "deleteConfirm",
                fixed: true,
                title: message.tips,
                content: message.slideCannotBeDelete,
                cancelValue: message.ok,
                cancel: function () {
                }
            });

        } else {
            var buttonId = "#chart-" + thisDiv;
            gridster.remove_widget($(buttonId));
            $.post("dataCenter/charts/" + thisDiv + "/deleteItem.json", function (result) {
                if (result) {
                    $("#program").attr("disabled", false);
                }
            });
        }
    });
    return false;
}

//保存布局
$('#save').click(function () {
    var $items = $(".gs-w");
    var uuid = $("#uuid").val();
    var jsonStr = getJsonString($items);
    var $layoutName = $("#layoutName");
    var $span = $("#layoutDescSpan");
    if (verificationLayoutName($layoutName, $span)) {
        $.post("dataCenter/charts/addLayoutItems.json", {
            jsonStr: jsonStr,
            uuid: uuid,
            layoutDescription: $layoutName.val()
        }, function (result) {
            if (result.success) {
                art.dialog({
                    id: "save",
                    fixed: true,
                    title: message.tips,
                    content: message.saveSuccess,
                    okValue: message.ok,
                    ok: function () {
                    }
                });
                $("#layoutDescription").text(result.layoutDescription);
            }
        });
    }
});

function getJsonString($items) {
    var jsonStr = '{"jsonString":[';
    $.each($items, function (i, item) {
        var params = "{";
        var attrs = item.attributes;
        for (var j = 0; j < attrs.length; j++) {
            if (attrs[j].name == 'itemtype') {
                params = params + '"itemType":"' + attrs[j].value + '"';
                params = params + ',';
            }
            if (attrs[j].name == 'data-id') {
                params = params + '"id":"' + attrs[j].value + '"';
                params = params + ',';
            } else if (attrs[j].name == 'data-col') {
                params = params + '"data-col":"' + attrs[j].value + '"';
                params = params + ',';
            } else if (attrs[j].name == 'data-row') {
                params = params + '"data-row":"' + attrs[j].value + '"';
                params = params + ',';
            } else if (attrs[j].name == 'data-sizex') {
                params = params + '"data-sizex":"' + attrs[j].value + '"';
                params = params + ',';
            } else if (attrs[j].name == 'data-sizey') {
                params = params + '"data-sizey":"' + attrs[j].value + '"';
//                params = params + ',';
            }
        }
        if (i == $items.length - 1) {
            jsonStr = jsonStr + params + '}';
        } else if (i < $items.length - 1) {
            jsonStr = jsonStr + params + '},';
        }
    });

    jsonStr = jsonStr + ']}';
    return jsonStr;
}

//添加div块之前必须先输入名字
function verificationLayoutName($layoutName, $span) {
//    var flag = true;
    var reg1 = new RegExp("^[0-9a-zA-Z\u4e00-\u9fa5]*$");
    if (!$.trim($layoutName.val())) {
        $span.text(message.inputName);
        return false;
    } else if (!reg1.test($layoutName.val())) {
        $span.text(message.illegalCharacters);
        return false;
    } else {
        $span.text("");
        return true;
    }
}

//全屏之后删除span元素（不能缩放）
$(function () {
    var isFullScreen = $("#isFullScreen").val();
    if (isFullScreen == 1) {
        $("span").filter(".gs-resize-handle").remove();
    }
});

