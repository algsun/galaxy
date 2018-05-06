/**
 * 依赖 : underscore
 * @constructor
 */
function ImageRuler() {
    var that = this;

    //图片原宽度
    that.imageSrcWidth = null;
    that.imageSrcHeight = null;
    //实景宽度
    that.imageRealWidth = null;

    //内容画布的宽度和高度
    that.paperWidth = null;
    that.paperHeight = null;
    that.paperContainer = null;

    //标尺中的所有的线(包括基线和刻度)
    that.horizontalLineSet = new Array(); //水平标尺中的刻度线和 label
    that.verticalLineSet = new Array(); //垂直标尺中的刻度线和 label

    // paper 在 paperContainer 中的偏移量
    var OFFSET = 30;
    //尺子与画布之前的空隙
    var GAP_BETWEEN_RULER_AND_PAPER = 5;

    //draw ruler
    this.draw = function () {
        //标尺有日前分两种刻度，单位长度的刻度和单位长度内的小刻度。
        //单位长度才显示 label, 程序自己检测合适的单位长度。
        //单位长度最小到 1cm(即 1cm 为单位长度)
        //小刻度目前将单位长度 10 等分

        var incrementCM = that.detectMinUnitIncrement(); //单位长度代表多少个 1cm
        //标尺的尺度单位长度
        var increment = incrementCM * (that.imageSrcWidth * 10) / that.imageRealWidth; //单位长度 代表的像素数

        //draw horizontal ruler/ 画水平标尺
        that.drawHorizontalRuler(increment, incrementCM);

        //draw vertical ruler/ 画垂直标尺
        that.drawVerticalRuler(increment, incrementCM);


        //画左上角，遮盖区域
        var widthOrHeight = OFFSET - GAP_BETWEEN_RULER_AND_PAPER + 1;
        var overlay = that.paperContainer.rect(0, 0, widthOrHeight, widthOrHeight);
        overlay.attr("fill", "#FFF");
        overlay.attr("stroke", "#FFF");
    };

    // clear ruler
    //清除标尺
    this.clear = function () {
        this.clearPieces(that.horizontalLineSet);
        this.clearPieces(that.verticalLineSet);
    };

    //消除数组中的所有元素(画布中)
    this.clearPieces = function (rulerPieceArray) {
        if (_.isArray(rulerPieceArray)) {
            _.each(rulerPieceArray, function (ele) {
                ele.remove();
            });
        }
    };

    //更新标尺的偏移量
    this.updateOffset = function (offsetX, offsetY) {
        Status.printLog("offset x:" + offsetX + ", y:" + offsetY);
        //return;

        _.each(that.horizontalLineSet, function (rulerLine) {
            rulerLine.updateOffset(offsetX, 0);
        });

        _.each(that.verticalLineSet, function (rulerLine) {
            rulerLine.updateOffset(0, offsetY);
        });
    };

    //检测最小的单位个数
    this.detectMinUnitIncrement = function () {
        var unitIncrement = 1;
        while (true) {
            var pixel = unitIncrement * (that.imageSrcWidth * 10) / that.imageRealWidth;
            //如果单位长度已经满足最小长度要求
            if (pixel >= 100) {
                break;
            }
            unitIncrement++;
        }
        return unitIncrement;
    };

    /**
     * 画水平方向的标尺
     * @param unitWidth 单位长度 代表的像素数
     * @param cmCount 单位长度 表示 1cm 的个数
     */
    this.drawHorizontalRuler = function (unitWidth, cmCount) {
        var increment = unitWidth;
        var incrementCM = cmCount;

        var units = Math.ceil(that.imageSrcWidth / increment); //向上取整
        var horizontalLinePositionY = OFFSET - GAP_BETWEEN_RULER_AND_PAPER; //水平基线的 y 坐标
        //水平基线
        //horizontalLine
        that.drawLine(OFFSET, horizontalLinePositionY, OFFSET + units * increment, horizontalLinePositionY, that.horizontalLineSet);
        //刻度和 label
        _.each(_.range(0, units), function (j) {
            var offsetX = OFFSET + j * increment;
            //单位刻度中的小刻度
            that.drawLittleHorizontal(offsetX, increment);
            //var unitLine = that.paperContainer.path("M{0},{1},L{0},0", offsetX, OFFSET - GAP_BETWEEN_RULER_AND_PAPER);
            //单位刻度
            that.drawLine(offsetX, 0, offsetX, horizontalLinePositionY, that.horizontalLineSet);
            //var unitText = that.paperContainer.text(offsetX + 15, 5, j * incrementCM + "cm");
            //单位 label
            that.drawText(offsetX + 15, 5, j * incrementCM + "cm", that.horizontalLineSet);
        });
    };

    /**
     * 画垂直方向的标尺
     * @param unitWidth 单位长度 代表的像素数
     * @param cmCount 单位长度 表示 1cm 的个数
     */
    this.drawVerticalRuler = function (unitWidth, cmCount) {
        var increment = unitWidth;
        var incrementCM = cmCount;

        var units = Math.ceil(that.imageSrcHeight / increment); //向上取整
        //垂直基线
        //verticalLine
        var verticalLinePositionX = OFFSET - GAP_BETWEEN_RULER_AND_PAPER;
        that.drawLine(verticalLinePositionX, OFFSET, verticalLinePositionX, OFFSET + units * increment, that.verticalLineSet);
        //刻度和 label
        _.each(_.range(0, units), function (i) {
            var offsetY = OFFSET + i * increment;
            //单位长度中的小刻度
            that.drawLittleVertical(offsetY, increment);
            //var unitLine = that.paperContainer.path("M0,{0}L{1},{0}", offsetY, OFFSET - GAP_BETWEEN_RULER_AND_PAPER);
            //单位刻度
            that.drawLine(0, offsetY, verticalLinePositionX, offsetY, that.verticalLineSet);
            //var unitText = that.paperContainer.text(8, offsetY + 15, i * incrementCM + "\ncm");
            //单位 label
            that.drawText(8, offsetY + 15, i * incrementCM + "\ncm", that.verticalLineSet);
        });
    };

    //画水平线上的小刻度
    this.drawLittleHorizontal = function (offsetX, unitWidth) {
        //单位刻度中要分的小刻度数量
        var number = 10;
        var littleWidth = unitWidth / number;
        _.each(_.range(1, number), function (index) {
            var x = offsetX + index * littleWidth;
            var startY = 15;
            if (index == 5) {  //如果是中间刻度, 长一点
                startY = 10;
            }
            that.drawLine(x, startY, x, 25, that.horizontalLineSet);
        });
    };

    //画垂直线的小刻度
    this.drawLittleVertical = function (offsetY, unitWidth) {
        //单位刻度中要分的小刻度数量
        var number = 10;
        var littleHeight = unitWidth / number;
        _.each(_.range(1, number), function (index) {
            var y = offsetY + index * littleHeight;
            var startX = 15;
            if (index == 5) {  //如果是中间刻度, 长一点
                startX = 10;
            }
            that.drawLine(startX, y, 25, y, that.verticalLineSet);
        });
    };

    //画线并将线加到数组中
    this.drawLine = function (startX, startY, endX, endY, pieceArray) {
        var rawLine = that.paperContainer.path("M{0},{1}L{2},{3}", startX, startY, endX, endY);
        var rulerLine = new RulerLine(rawLine, startX, startY, endX, endY);
        pieceArray.push(rulerLine);

        return rulerLine;
    };

    //画文本并将文本加到数组中
    this.drawText = function (x, y, text, pieceArray) {
        var rawText = that.paperContainer.text(x, y, text);
        var rulerText = new RulerText(rawText, x, y);
        pieceArray.push(rulerText);

        return rulerText;
    };

}

/**
 * ImageRuler#create instead of new#ImageRuler,
 * and this method is unique approach to create ImageRuler instance correctly.
 * example:
 * <pre>
 * var imageRuler = ImageRuler.create({
 *     imageSrcWidth : 1280,
 *     imageSrcHeight: 720,
 *     imageRealWidth : 41,
 *     paperWidth : 1000,
 *     paperHeight : 600,
 *     paperContainer : paperContainer
 * });
 * </pre>
 * @param params
 * @return {*}
 */
ImageRuler.create = function (params) {
    //TODO extract this method to ImageRuler constructor

    var imageRuler = new ImageRuler();
    imageRuler.imageSrcWidth = params.imageSrcWidth;
    imageRuler.imageSrcHeight = params.imageSrcHeight;
    imageRuler.imageRealWidth = params.imageRealWidth;
    imageRuler.paperWidth = params.paperWidth;
    imageRuler.paperHeight = params.paperHeight;
    imageRuler.paperContainer = params.paperContainer;

    return imageRuler;
};

//标尺上的线
function RulerLine(rawLine, startX, startY, endX, endY) {
    var that = this;

    that.rawLine = rawLine;
    that.startX = startX;
    that.startY = startY;
    that.endX = endX;
    that.endY = endY;

    //更新偏移量
    this.updateOffset = function (offsetX, offsetY) {
        var startX = offsetX + that.startX;
        var startY = offsetY + that.startY;
        var endX = offsetX + that.endX;
        var endY = offsetY + that.endY;
        var path = [
            'M', startX, ',', startY,
            'L', endX, ',', endY
        ].join('');
        that.rawLine.attr("path", path);
    };

    //删除元素
    this.remove = function () {
        that.rawLine.remove();
    };
}

//标尺上的文本
function RulerText(rawText, x, y) {
    var that = this;
    this.rawText = rawText;
    this.x = x;
    this.y = y;

    //更新偏移量
    this.updateOffset = function (offsetX, offsetY) {
        var x = offsetX + that.x;
        var y = offsetY + that.y;
        that.rawText.attr({x:x, y:y});
    };

    //删除元素
    this.remove = function () {
        that.rawText.remove();
    }
}