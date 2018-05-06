/**
 * 线
 * @param rawLine
 * @param canvasContainer
 * @constructor
 */
function Line(rawLine, startPoint, endPoint, markName, canvasContainer) {
    var that = this;
    this.rawLine = rawLine;
    this.startPoint = startPoint; // point A
    this.endPoint = endPoint; // point B
    this.markName = markName;
    this.paper = canvasContainer.paper;
    this.viewBox = canvasContainer.paperViewBox;
    //是否选中
    this.selected = false;
    //选中锁
    this.selectedLock = false;

    this.rawLinePath = function () {
        return Raphael.format("M{0},{1}L{2},{3}", that.startPoint.x, that.startPoint.y, that.endPoint.x, that.endPoint.y);
    };

    //返回 hash
    this.value = function () {
        return {
            markSegmentId: that.markSegmentId,
            markSegmentName:that.markName,
            markSegmentPositionId:that.markSegmentPositionId,
            positionX:that.startPoint.x,
            positionY:that.startPoint.y,
            positionX2:that.endPoint.x,
            positionY2:that.endPoint.y
        };
    };

    //重画线
    this.reRender = function () {
        that.rawLine.attr("path", that.rawLinePath());
        that.renderSelection();
    };

    //更新 points, 并且重画
    this.updatePointsAndRender = function (startPoint, endPoint) {
        that.startPoint = _.clone(startPoint);
        that.endPoint = _.clone(endPoint);
        that.reRender();
    };
    //设置选中
    this.setSelected = function () {
        if (that.selectedLock) {
            return;
        }
        if (that.selected) {
            return;
        }
        that.renderSelection();

        var srcBBox = that.rawLine.getBBox();
        var length = Math.sqrt(srcBBox.width * srcBBox.width + srcBBox.height * srcBBox.height);
        var realLength = (length * canvasContainer.imageRealWidth) / canvasContainer.imageSrcWidth;
        realLength = realLength.toFixed(2);
        Status.info("长度 " + realLength + "mm");

        that.selected = true;
    };

    // 画选中区
    this.renderSelection = function () {
        var bBox = _.clone(that.rawLine.getBBox());
        //在 IE 中, Element.getBBox() 的结果会加上 viewBox 的偏移量, 所以要把它减掉
        if (Raphael.vml) {
            bBox.x = bBox.x + that.viewBox.x;
            bBox.y = bBox.y + that.viewBox.y;
        }

        //如果 bBox 的 width 或者 height 过小，那么设置加大 20
        if (bBox.width < 20) {
            bBox.x = bBox.x - 10;
            bBox.width = bBox.width + 20;
        } else {
            bBox.x = bBox.x - 10;
            bBox.width = bBox.width + 20;
        }
        if (bBox.height < 20) {
            bBox.y = bBox.y - 10;
            bBox.height = bBox.height + 20
        } else {
            bBox.y = bBox.y - 10;
            bBox.height = bBox.height + 20
        }
        //如果不为 null, 那么先将选中区从画布删除
        if (that.selection != null) {
            that.selection.remove();
        }
        var selection = that.paper.rect(bBox.x, bBox.y, bBox.width, bBox.height);
        selection.attr("stroke-width", 1);
        selection.attr("stroke-dasharray", "6,8");
        selection.attr("stroke-linecap", "round");
        selection.attr("stroke-linejoin", "round");
        selection.animate({stroke:"red"}, 200);
        that.selection = selection;
    };

    //取消选中
    this.unsetSelected = function () {
        if (that.selectedLock) {
            return;
        }
        if (that.selected) {
            that.selection.remove();
            that.selection = null;
            that.selected = false;
        }
    };

    this.forceSelected = function (selected) {
        that.selectedLock = false;
        if (selected) {
            that.setSelected();
            that.selectedLock = true;
        } else {
            that.unsetSelected();
            that.selectedLock = false;
        }
    };
    //从 canvas 中把自己删除
    this.remove = function () {
        that.rawLine.remove();
        that.selectedLock = false;
        that.unsetSelected();
    };
}

