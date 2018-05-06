/**
 * imageViewer.html 页面中的主要逻辑
 *
 * 依赖：imageViewer-refloat.js, imageViewer-status.js, imageViewer-lines-dom.js, imageViewer-line.js, imageViewer-ruler.js
 * @author gaohui
 * @date 2012-06-19
 */

// canvas container
function CanvasContainer(imageUrl, imageSrcWidth, imageSrcHeight, imageRealWidth) {
    var that = this;

    this.paperContainerWidth = 1040;
    this.paperContainerHeight = 635;
    this.paperWidth = 1000;
    this.paperHeight = 600;
    //标尺的偏移量
    var offset = 30;
    var OFFSET = 30;
    this.OFFSET = 30;
    this.paperOffset = offset;

    //图片地址
    this.imageUrl = imageUrl;
    //图片原始宽
    this.imageSrcWidth = imageSrcWidth;
    //图片原始高
    this.imageSrcHeight = imageSrcHeight;
    //图片所拍实景实际宽长度
    this.imageRealWidth = imageRealWidth;
    Status.updateImageRealWidth(imageRealWidth);

    this.paperContainer = Raphael("canvas", this.paperContainerWidth, this.paperContainerHeight);
    this.paper = Raphael(this.paperContainer.canvas, this.paperWidth, this.paperHeight);

    //init paperContainer
    (function (that) {
        var imageRuler = ImageRuler.create({
            imageSrcWidth:that.imageSrcWidth,
            imageSrcHeight:that.imageSrcHeight,
            imageRealWidth:that.imageRealWidth,
            paperWidth:that.paperWidth,
            paperHeight:that.paperHeight,
            paperContainer:that.paperContainer
        });
        that.imageRuler = imageRuler;
        that.updateImageRealWidth = function (imageRealWidth) {
            that.imageRealWidth = imageRealWidth;
            that.imageRuler.imageRealWidth = imageRealWidth;
        };
        that.drawRuler = function () {
            imageRuler.draw();
        };

        //清除标尺
        that.clearRuler = function () {
            imageRuler.clear();
        };

        that.drawRuler();

        //add background
        var bgRect = that.paperContainer.rect(offset, offset, that.paperWidth, that.paperHeight);
        bgRect.attr("stroke", "#DDD");
        bgRect.attr("fill", "#DDD");
        //move paper after bgRect, so paper will be above on bgRect.
        $(bgRect.node).after(that.paper.canvas);
    })(this);


    //init paper
    (function (that) {
        //set paper offset in paperContainer
        if (Raphael.svg) {
            that.paper.canvas.setAttributeNS(null, "x", offset);
            that.paper.canvas.setAttributeNS(null, "y", offset);
        }
        if (Raphael.vml) {
            $(that.paper.canvas).css({position:"relative", top:offset, left:offset});
        }
    })(this);

    this.paperViewBox = {x:0, y:0, width:this.paperWidth, height:this.paperHeight};
    this.updatePaperViewBox = function () {
        this.paper.setViewBox(this.paperViewBox.x, this.paperViewBox.y, this.paperViewBox.width, this.paperViewBox.height);
    };
    this.updatePaperViewBox2 = function (viewBox) {
        that.paperViewBox.x = viewBox.x;
        that.paperViewBox.y = viewBox.y;
        that.paperViewBox.width = viewBox.width;
        that.paperViewBox.height = viewBox.height;
        that.updatePaperViewBox();
    };
    this.updatePaperViewBoxXY = function (x, y) {
        that.paperViewBox.x = x;
        that.paperViewBox.y = y;
        that.updatePaperViewBox();
    };
    this.updatePaperViewBoxWithDelta = function (deltaX, deltaY) {
        var viewBox = that.paperViewBox;
        viewBox.x = viewBox.x + deltaX;
        viewBox.y = viewBox.y + deltaY;

        that.updatePaperViewBox();
    };
    this.movePaperTo = function (viewBoxX, viewBoxY) {
        that.updatePaperViewBoxXY(viewBoxX, viewBoxY);
        that.imageRuler.updateOffset(-1 * viewBoxX, -1 * viewBoxY);
    };
    this.movePaperWidthDelta = function (viewBoxDeltaX, viewBoxDeltaY) {
        that.updatePaperViewBoxWithDelta(viewBoxDeltaX, viewBoxDeltaY);
        that.imageRuler.updateOffset(-1 * that.paperViewBox.x, -1 * that.paperViewBox.y);
    };

    // 清理并重置
    this.reset = function () {
        var viewBox = that.paperViewBox;
        viewBox.x = 0;
        viewBox.y = 0;
        that.updatePaperViewBox2(viewBox);
        that.paper.clear();
        that.imageRuler.clear();
        that.imageRuler.draw();
        that.drawImage();
        LinesDom.clear();
    };

    //开始移动 paper
    this.movePaper = function () {
        var startPoint = null;
        var startViewBox = null;
        // start moving
        $(that.paper.canvas).mousedown(function (event) {
            startPoint = {
                x:event.pageX,
                y:event.pageY
            };
            startViewBox = _.clone(that.paperViewBox);
        });

        //moving
        $(that.paper.canvas).mousemove(function (event) {
            var x2 = event.pageX;
            var y2 = event.pageY;

            if (startPoint) {
                var deltaX = x2 - startPoint.x;
                var deltaY = y2 - startPoint.y;
                var viewBox = that.paperViewBox;
                viewBox.x = startViewBox.x - deltaX;
                viewBox.y = startViewBox.y - deltaY;
                that.movePaperTo(viewBox.x, viewBox.y);
                Status.updateImageCoordinate(viewBox.x * -1, viewBox.y * -1);
            }
        });

        //end moving
        $(that.paper.canvas).mouseup(function (event) {
            startPoint = null;
        });
    };

    //取消移动 paper
    this.unmovePaper = function () {
        $(that.paper.canvas).unbind("mousedown").unbind("mouseover").unbind("mouseup");
    };

    //开始画线
    this.drawLine = function () {
        /**
         * 如果是 vml 方式实现，那么很不幸 IE(6,7,8) 浏览器会有如下bug:
         * 当 paper.viewBox 离开原点后，比如 paper.setViewBox(50, 50, width, height)
         * 然后画线 paper.rect("M0,0L100,100")
         * 然后再改 viewBox 的 x/y, 那么 rect 就会多偏移画他的时候 viewBox 的 x/y(50/50),
         * 但 rect 的 path 还是 "M0,0L100,100".
         *
         *
         * 目前使用以下方式解决：
         * 每当要画东西的时候，先将 viewBox 的 x/y 改为 0/0, 然后再画,
         * 画好之后将 viewBox 改回画之前的值.
         */
        var newLine = null;
        var newLinePath = null;
        var newLineStartPoint = {};
        var newLineEndPoint = {};
        var viewBoxBeforeDrawing = null; //just for IE

        var pointRelativeToCanvas = function (canvasOffset, pageX, pageY) {
            var x = pageX - canvasOffset.left;
            var y = pageY - canvasOffset.top;
            return {x:x, y:y};
        };

        var updateNewLinePath = function (pageX, pageY) {
            if (newLine) {
                var imageOffset = $(that.image.node).offset();
                newLineEndPoint = pointRelativeToCanvas(imageOffset, pageX, pageY);
                newLinePath = Raphael.format("M{0},{1}L{2},{3}", newLineStartPoint.x, newLineStartPoint.y, newLineEndPoint.x, newLineEndPoint.y);
                newLine.attr("path", newLinePath);
            }
        };

        //line start
        $(that.paper.canvas).mousedown(function (event) {
            //如果是 TMD 的 IE 浏览器
            if (Raphael.vml) {
                viewBoxBeforeDrawing = _.clone(that.paperViewBox);
            }
            var imageOffset = $(that.image.node).offset();
            newLineStartPoint = pointRelativeToCanvas(imageOffset, event.pageX, event.pageY);
            newLinePath = Raphael.format("M{0},{1}L{0},{1}", newLineStartPoint.x, newLineStartPoint.y);
            newLine = that.paper.path(newLinePath);
            newLine.attr("stroke", "#fff");
        });
        //line move
        $(that.paper.canvas).mousemove(function (event) {
            if (newLine) {
                updateNewLinePath(event.pageX, event.pageY);
            }
        });
        //line end
        $(that.paper.canvas).mouseup(function (event) {
            //如果是 TMD 的 IE 浏览器, 那么对位置进行校正
            if (Raphael.vml) {
                //先将 viewBox 改为原点
                that.updatePaperViewBoxXY(0, 0);
                //然后将之前的线删掉
                newLine.remove();
                //然后重画这条线
                newLine = that.paper.path(newLinePath);
                newLine.attr("stroke", "#fff");
                //然后改 viewBox 改回去
                that.updatePaperViewBoxXY(viewBoxBeforeDrawing.x, viewBoxBeforeDrawing.y);
            }
            that.addNewLine(newLine, newLineStartPoint, newLineEndPoint);

            //clear status
            newLine = null;
            newLinePath = null;
            newLineStartPoint = {};
            newLineEndPoint = {};
            viewBoxBeforeDrawing = null;
        });

        //鼠标移动到边缘画布移动 starting
        that.paperMoveIntervals = {};
        that.overlayChecks = {};
        that.nearChecks = {};
        that.paperMovingMouse = {x:0, y:0};

        (function () {
            _.each(['Top', 'Right', 'Bottom', 'Left'], function (direction) {
                var lowCaseDirection = direction.toLowerCase();
                that.paperMoveIntervals[lowCaseDirection] = null;
                that.overlayChecks[lowCaseDirection] = eval("that.is" + direction + "Overlay");
                that.nearChecks[lowCaseDirection] = eval('that.isNearToContainer' + direction);
            });
        })();

        var checkMoveInterval = function (mouseX, mouseY, direction, deltaX, deltaY) {
            // direction 方向部分被遮盖，没有显示完全
            if (that.overlayChecks[direction]()) {
                //如果靠近 direction 方向的画布边缘
                if (that.nearChecks[direction](mouseX, mouseY)) {
                    //如果 moveInterval 不为空，则添加 移动 interval
                    if (that.paperMoveIntervals[direction] == null) {
                        that.paperMoveIntervals[direction] = setInterval(function () {
                            //如果移动到没有被遮盖时，停止移动，清除 移动 interval
                            if (!that.overlayChecks[direction]()) {
                                clearInterval(that.paperMoveIntervals[direction]);
                                that.paperMoveIntervals[direction] = null;
                                return;
                            }
                            //that.updatePaperViewBoxWithDelta(deltaX, deltaY);
                            that.movePaperWidthDelta(deltaX, deltaY);

                            updateNewLinePath(that.paperMovingMouse.pageX, that.paperMovingMouse.pageY);
                        }, 50);
                    }
                } else {
                    if (that.paperMoveIntervals[direction] != null) {
                        clearInterval(that.paperMoveIntervals[direction]);
                        that.paperMoveIntervals[direction] = null;
                    }
                }
            }
        };

        $(that.paper.canvas).mousemove(function (event) {
            var x = event.pageX;
            var y = event.pageY;
            that.paperMovingMouse.pageX = x;
            that.paperMovingMouse.pageY = y;
            checkMoveInterval(x, y, 'top', 0, -2);
            checkMoveInterval(x, y, 'right', 2, 0);
            checkMoveInterval(x, y, 'bottom', 0, 2);
            checkMoveInterval(x, y, 'left', -2, 0);
        });
        $(that.paper.canvas).mouseleave(function () {
            _.each(['top', 'right', 'bottom', 'left'], function (direction) {
                if (that.paperMoveIntervals[direction] != null) {
                    clearInterval(that.paperMoveIntervals[direction]);
                    that.paperMoveIntervals[direction] = null;
                }
            });
        });
        //ending
    };

    //添加线
    this.addNewLine = function (newLine, startPoint, endPoint) {
        var $markSegmentInputDiv = $("#mark-segment");
        var $markSegmentNameInput = $markSegmentInputDiv.find("input");
        art.dialog({
            title:"添加标记段",
            content:$("#mark-segment")[0],
            lock:true,
            okValue:"保存",
            cancelValue:"取消",
            ok:function () {
                var thisDialog = this;
                //check input
                var markSegmentName = $.trim($markSegmentNameInput.val());
                if (markSegmentName == "") {
                    $markSegmentNameInput.focus();
                    $markSegmentInputDiv.find(".help-block").empty().append("输入不能为空");
                    return false;
                }
                //创建新线的包装类实例
                var newWrapperLine = new Line(newLine, startPoint, endPoint, markSegmentName, that);
                //将线添加到右侧的标记段列表中
                //如果有添加线的回调，那么调用回调
                if (_.isFunction(that.addMarkSegmentListenern)) {
                    var onError = function (errorMsg) {
                        $markSegmentInputDiv.find(".help-block").empty().append(errorMsg);
                    };
                    var onSuccess = function () {
                        LinesDom.addLine(markSegmentName, newWrapperLine);
                        thisDialog.close();
                        $markSegmentNameInput.val("");
                        $markSegmentInputDiv.find(".help-block").empty();
                    };
                    var markSegmentValue = {markName:markSegmentName, positionX:startPoint.x, positionY:startPoint.y, positionX2:endPoint.x, positionY2:endPoint.y};
                    that.addMarkSegmentListenern(newWrapperLine, markSegmentValue, onSuccess, onError);
                }
                return false;
            }, cancel:function () {
                //clear input value and help message
                $markSegmentNameInput.val("");
                $markSegmentInputDiv.find(".help-block").empty();
                newLine.remove();
            }
        });
        $markSegmentNameInput.focus();
    };

    //通过 path 添加线
    this.addLineByHash = function (lineHash) {
        var startPoint = lineHash.startPoint;
        var endPoint = lineHash.endPoint;
        var newRawLine = that.paper.path("M{0},{1}L{2},{3}", startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        newRawLine.attr("stroke", "#FFF");
        var newWrapperLine = new Line(newRawLine, startPoint, endPoint, lineHash.markSegmentName, that);
        newWrapperLine.markSegmentPositionId = lineHash.markSegmentPositionId;
        newWrapperLine.markSegmentId = lineHash.markSegmentId;
        LinesDom.addLine(lineHash.markSegmentName, newWrapperLine);
    };

    //取消画线
    this.undrawLine = function () {
        //unbind events
        $(that.paper.canvas).unbind("mousedown").unbind("mousemove").unbind("mouseup");
    };

    //画图
    this.drawImage = (function () {
        return function () {
            that.image = that.paper.image(that.imageUrl, 0, 0, that.imageSrcWidth, that.imageSrcHeight);

            Status.updateImageCoordinate(0, 0);
            Status.updateImageSrcDimension(that.imageSrcWidth, that.imageSrcHeight);
        };
    })();

    //以 json 形式返回所有的线
    this.getLinesJSON = function () {
        var linesPath = new Array();
        _.each(LinesDom.lines, function (line) {
            linesPath.push(line.value());
        });
        return JSON.stringify(linesPath);
    };

    // 以 对象形式返回所有的线
    this.getLinesHash = function () {
        var linesHash = new Array();
        _.each(LinesDom.lines, function (line) {
            linesHash.push(line.value());
        });
        return linesHash;
    };

    // 加载线
    this.loadLines = function (lineHashes) {
        _.each(lineHashes, function (lineHash) {
            that.addLineByHash(lineHash);
        });
    };

    this.positionRelativeToContainer = function (mouseX, mouseY) {
        var paperContainerOffset = $(that.paperContainer.canvas).offset();
        var deltaX = mouseX - (paperContainerOffset.left + that.OFFSET);
        var deltaY = mouseY - (paperContainerOffset.top + that.OFFSET);
        Status.printLog("offset left:" + deltaX + ", top:" + deltaY);
        return {x:deltaX, y:deltaY };
    };
    this.NEAR_LIMIT = 60; //接近边缘的域值
    this.isNearToContainerTop = function (mouseX, mouseY) {
        var deltaPosition = that.positionRelativeToContainer(mouseX, mouseY);
        if (deltaPosition.y < that.NEAR_LIMIT) {
            return true;
        }
        return false;
    };
    this.isNearToContainerLeft = function (mouseX, mouseY) {
        var deltaPosition = that.positionRelativeToContainer(mouseX, mouseY);
        if (deltaPosition.x < that.NEAR_LIMIT) {
            return true;
        }
        return false;
    };
    this.isNearToContainerRight = function (mouseX, mouseY) {
        var deltaPosition = that.positionRelativeToContainer(mouseX, mouseY);
        if (deltaPosition.x > (that.paper.width - that.NEAR_LIMIT)) {
            return true;
        }
        return false;
    };
    this.isNearToContainerBottom = function (mouseX, mouseY) {
        var deltaPosition = that.positionRelativeToContainer(mouseX, mouseY);
        if (deltaPosition.y > (that.paper.height - that.NEAR_LIMIT)) {
            return true;
        }
        return false;
    };
    this.isTopOverlay = function () {
        return that.paperViewBox.y > 0;
    };
    this.isBottomOverlay = function () {
        var deltaY = that.imageSrcHeight - that.paperViewBox.y;
        return deltaY > that.paperHeight;
    };
    this.isLeftOverlay = function () {
        return that.paperViewBox.x > 0;
    };
    this.isRightOverlay = function () {
        var deltaX = that.imageSrcWidth - that.paperViewBox.x;
        return deltaX > that.paperWidth;
    };

    // 注册 "添加标记段" 监听器
    this.registerAddMarkSegmentListener = function (callback) {
        that.addMarkSegmentListenern = callback;
    };

    // 注册 "删除标记段" 监听器
    this.registerRemoveMarkSegmentListener = function (callback) {
        that.removeMarkSegmentListenern = callback;
        LinesDom.registerRemoveMarkSegmentListener(callback);
    };
}

