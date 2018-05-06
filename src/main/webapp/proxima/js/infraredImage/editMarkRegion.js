/**
 * @author gao.hui
 * @date 2012-09-11
 * 依赖: jquery, underscore, artDialog, draw-rect
 */
$(function () {
    //全局的保存 标记区域
    var _markRegions = {};
    var hasMarkRegionByName = function (markRegionName) {
        return _.any(_markRegions, function (val, key) {
            return val.markRegionName === markRegionName;
        });
    };


    //添加标记区域到 dom
    var addMarkRegionInDom = (function () {
        return function (markRegionId, markRegionName, newRect) {
            //添加标记区域
            _markRegions[markRegionId] = {
                markRegionId: markRegionId,
                markRegionName: markRegionName,
                rect: newRect
            };
            //页面中代表标记区域的 dom
            var newMarkRegionDom = ['<li>',
                '<button type="button" class="close">×</button>',
                '<span>',
                markRegionName,
                '</span>',
                '</li>'].join('');
            var $newMarkRegionDom = $("#markRegions").append(newMarkRegionDom).children().last();
            $newMarkRegionDom.bind("mouseenter", function () { //鼠标进入, 矩形变红
                newRect.setColor('red');
            }).bind("mouseleave", function () { //鼠标进入, 矩形变白
                newRect.setColor('white');
            });
            //"x" 删除按钮被点击后，删除 dom 和 矩形
            $newMarkRegionDom.find('button').click(function () {
                art.dialog({
                    title: message.tips,
                    content: message.sureToDelete,
                    lock: true,
                    okValue: message.ok,
                    cancelValue: message.cancel,
                    ok: function () {
                        $.get('deleteMarkRegion.action', {markRegionId: markRegionId}, function (result) {
                            if (result.success) {
                                $newMarkRegionDom.remove(); // 删除右边的标记区域列表中的 dom
                                newRect.remove(); // 删除 playground 中的 rect

                                //删除标记区域
                                delete _markRegions[markRegionId];
                            }
                        });
                    },
                    cancel: function () {
                    }
                });
            });
        };
    })();

    var MSG = {
        closeable: false,
        showMsg: function (title) {
            var MSG = this;
            MSG.dialog = art.dialog({
                id: "info",
                title: title,
                cancel: false
            });
        },
        closeDialog: function () {
            var MSG = this;
            if (MSG.dialog) {
                MSG.dialog.close();
            }
        }
    }

    //添加标记区域
    var addMarkRegion = (function () {
        return function (pictureId, dvPlaceId, newRect) {
            var $addMarkRegion = $('#add-mark-region');
            var $input = $addMarkRegion.find('input');
            var $help = $addMarkRegion.find('p.help-block');
            //弹窗，输入标记区域名称
            art.dialog({
                title: message.addMarkRegion,
                content: $addMarkRegion[0],
                lock: true,
                okValue: message.save,
                ok: function () {
                    var dialog = this;
                    var markRegionName = $.trim($input.val());
                    //验证输入
                    if (markRegionName === '') {
                        $help.empty().append(message.cannotBeEmpty);
                        return false;
                    }
                    //如果名称已经存在
                    if (hasMarkRegionByName(markRegionName)) {
                        $help.empty().append(message.nameRepetition);
                        return false;
                    }

                    dialog.close();

                    MSG.showMsg(message.dataProcessing);
                    var newRectBox = newRect.box();
                    $.post('addMarkRegion.action', {
                        dvPlaceId: dvPlaceId,
                        pictureId: pictureId,
                        markName: markRegionName,
                        positionX: newRectBox.x.toFixed(0), //舍去小数, 不知道为什么，浏览器传过来的值有时候是 "xxx.5" ，后面有个 0.5
                        positionY: newRectBox.y.toFixed(0),
                        width: newRectBox.width,
                        height: newRectBox.height
                    }, function (result) {
                        MSG.closeDialog();
                        if (result.success) {
                            addMarkRegionInDom(result.markRegionId, markRegionName, newRect);

                            //清空输入框的值和提示内容
                            $input.val('');
                            $help.empty();
                        } else {
                            $help.empty().append(result.message);
                        }
                    });
                    return false;
                },
                cancelValue: message.cancel,
                cancel: function () {
                    newRect.remove(); // 删除矩形
                    //清空输入框的值和提示内容
                    $input.val('');
                    $help.empty();
                }
            });
            $addMarkRegion.find('input').focus();
        };


    })();

    (function () {
        var $editImage = $("#editedImage");
        //图片ID
        var pictureId = $editImage.attr("data-picture-id");
        //图片地址
        var imageSrc = $editImage.attr("data-image-src");
        //摄像机点位ID
        var dvPlaceId = $editImage.attr("data-dv-place-id");
        //检查图片原始大小, 同时设置 playground 的大小为图片的大小
        Images.naturalSize(imageSrc, function (width, height) {
            $editImage.css('background-image', 'url(' + imageSrc + ')');
            $editImage.width(width);
            $editImage.height(height);

            var playground = Painter.playground({
                //操场的id
                id: "#editedImage",
                //添加新矩形回调
                rectComplete: function (newRect) {
                    addMarkRegion(pictureId, dvPlaceId, newRect);
                    // 如果是正在编辑, 新画的矩形应该是可以修改的
                    newRect.resizable();
                }
            });
            playground.undrawable();

            //从页面加载数据到 playground, 和 标记区域列表
            $("#init-markRegions").children().each(function () {
                var $this = $(this);
                var markRegionId = $this.attr("data-id");
                var markRegionName = $this.attr("data-name");
                var positionX = parseInt($this.attr("data-position-x"));
                var positionY = parseInt($this.attr("data-position-y"));
                var width = parseInt($this.attr("data-width"));
                var height = parseInt($this.attr("data-height"));
                //画矩形
                var newRect = playground.paintRect(positionX, positionY, width, height);
                newRect.undrag();
                //添加到标记段到列表
                addMarkRegionInDom(markRegionId, markRegionName, newRect);
            });


            //保存按钮事件，保存标记区域
            (function () {
                //图片地址
                var imageSrc = $editImage.attr("data-image-src");
                //图片ID
                var pictureId = $editImage.attr("data-picture-id");
                //是否正在编辑标记区域
                var editingMarkRegion = false;
                $('#edit-button').click(function () {
                    editingMarkRegion = !editingMarkRegion;
                    if (editingMarkRegion) {
                        //启用 "保存" 按钮
                        $("#save-button").removeAttr('disabled');
                        //取消每个矩形的 "可拖" 和 "改变大小"
                        _.each(_markRegions, function (markRegion, key) {
                            var rect = markRegion.rect;
                            rect.dragable();
                            rect.resizable();
                        });
                        //可以画矩形
                        playground.drawable();
                    } else {
                        //禁用 "保存" 按钮
                        $("#save-button").attr('disabled', 'disabled');
                        _.each(_markRegions, function (markRegion, key) {
                            markRegion.rect.undrag().unresize();
                        });
                        //取消画矩形
                        playground.undrawable();
                    }
                });

                $("#save-button").click(function () {
                    var markRegionValues = [];
                    _.each(_markRegions, function (val, key) {
                        var box = val['rect'].box();
                        markRegionValues.push({
                            id: val['markRegionId'],
                            x: box.x,
                            y: box.y,
                            width: box.width,
                            height: box.height,
                            imageSrc: imageSrc,
                            pictureId: pictureId
                        });
                    });

                    //get markRegion positions
                    var markRegionsJson = JSON.stringify(markRegionValues);
                    MSG.showMsg(message.dataProcessing);
                    $.post('updateMarkRegion.action', {
                        dvPlaceId: dvPlaceId,
                        markRegionsJson: markRegionsJson
                    }, function (result) {
                        MSG.closeDialog();
                        if (result.success) {
                            art.dialog({time: 2000, title: message.tips, content: message.saveSuccess});
                        } else {
                            art.dialog({time: 2000, title: message.tips, content: message.saveFailed});
                        }
                    });
                });
            })();

            //鼠标悬停显示温度
            (function () {
                var temperatureTooltip = null;
                $("#editedImage").mousemove(function (event) {
                    var offset = $(this).offset();
                    var x = event.pageX - offset.left;
                    x = x.toFixed(0);
                    var y = event.pageY - offset.top;
                    y = y.toFixed(0);

                    clearTimeout(temperatureTooltip);
                    App.closeTooltip();
                    temperatureTooltip = setTimeout(function () {
                        App.tooltip(event.pageX, event.pageY, message.temperatureInformation, message.loading);
                        $.get('infraredImageTemperature.action', {
                            dvPlaceId: dvPlaceId,
                            pictureId: pictureId,
                            x: x,
                            y: y
                        }, function (result) {
                            if (result.success) {
                                if (!result.resolved) {
                                    App.tooltip(event.pageX, event.pageY, message.temperatureInformation, message.cannotBeResolved);
                                    return;
                                }

                                var content = '';
                                if (result.hasPicTemp) {
                                    //content = content + '平均温度: ' + result.picAvgTemp + '</br>';
                                    content = content + message.temperatureInCurrentPosition + ': ' + result.picCurrentTemp + '℃</br>';
                                }
                                if (result.hasRegionTemp) {
                                    content = content + message.maximumTemperature + ': ' + result.regionMaxTemp + '℃</br>';
                                    content = content + message.averageTemperature + ': ' + result.regionAvgTemp + '℃</br>';
                                    content = content + message.minimumTemperature + ': ' + result.regionMinTemp + '℃</br>';
                                }
                                if (!result.hasPicTemp && !result.hasRegionTemp) {
                                    content = message.noData;
                                }
                                App.tooltip(event.pageX, event.pageY, message.temperatureInformation, content);
                            } else {
                                App.tooltip(event.pageX, event.pageY, message.temperatureInformation, message.loadFailed);
                            }
                        });
                    }, 500);
                }).mouseleave(function () {
                    clearTimeout(temperatureTooltip);
                });
            })();
        });
    })();
});